"""
DB 연동:
- Supabase(PostgreSQL): 환경 변수 DATABASE_URL (같은 폴더의 .env 에 두면 자동 로드)
- 로컬 SQLite: DATABASE_URL 이 없으면 aiosqlite + student.db (기존과 동일)
"""
import html
import os
from contextlib import asynccontextmanager
from pathlib import Path
from typing import Any

import aiosqlite
import asyncpg
import uvicorn
from dotenv import load_dotenv
from fastapi import FastAPI, HTTPException, Query, Request, status
from fastapi.responses import HTMLResponse
from fastapi.templating import Jinja2Templates
from pydantic import BaseModel, Field

load_dotenv(Path(__file__).resolve().parent / ".env")

_TEMPLATES_DIR = Path(__file__).resolve().parent / "templates"
templates = Jinja2Templates(directory=str(_TEMPLATES_DIR))

_CREATE_SQLITE = """
CREATE TABLE IF NOT EXISTS student (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    grade INTEGER NOT NULL,
    age INTEGER NOT NULL
)
"""

_CREATE_POSTGRES = """
CREATE TABLE IF NOT EXISTS student (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    grade INTEGER NOT NULL,
    age INTEGER NOT NULL
)
"""

_SAMPLE_STUDENTS: tuple[tuple[str, int, int], ...] = (
    ("김민수", 2, 17),
    ("이지윤", 3, 18),
    ("박준호", 1, 16),
    ("최서연", 2, 17),
)


class StudentCreate(BaseModel):
    name: str = Field(min_length=1, max_length=100)
    grade: int = Field(ge=1, le=12)
    age: int = Field(ge=1, le=120)


class StudentUpdate(StudentCreate):
    pass


class StudentResponse(StudentCreate):
    id: int


def _row_to_student(row: Any) -> StudentResponse:
    return StudentResponse(id=row[0], name=row[1], grade=row[2], age=row[3])


def _use_postgres() -> bool:
    url = os.getenv("DATABASE_URL", "").strip()
    return bool(url and url.startswith(("postgresql://", "postgres://")))


async def _query_students_sqlite(db: aiosqlite.Connection, q: str | None) -> list[StudentResponse]:
    term = (q or "").strip()
    if not term:
        sql = "SELECT id, name, grade, age FROM student ORDER BY id"
        params: tuple[Any, ...] = ()
    elif term.isdigit():
        sid = int(term)
        like = f"%{term}%"
        sql = (
            "SELECT id, name, grade, age FROM student "
            "WHERE id = ? OR LOWER(name) LIKE LOWER(?) ORDER BY id"
        )
        params = (sid, like)
    else:
        like = f"%{term}%"
        sql = (
            "SELECT id, name, grade, age FROM student "
            "WHERE LOWER(name) LIKE LOWER(?) ORDER BY id"
        )
        params = (like,)
    async with db.execute(sql, params) as cursor:
        rows = await cursor.fetchall()
    return [_row_to_student(row) for row in rows]


async def _query_students_pg(conn: asyncpg.Connection, q: str | None) -> list[StudentResponse]:
    term = (q or "").strip()
    if not term:
        rows = await conn.fetch("SELECT id, name, grade, age FROM student ORDER BY id")
    elif term.isdigit():
        sid = int(term)
        like = f"%{term}%"
        rows = await conn.fetch(
            "SELECT id, name, grade, age FROM student "
            "WHERE id = $1 OR LOWER(name) LIKE LOWER($2) ORDER BY id",
            sid,
            like,
        )
    else:
        like = f"%{term}%"
        rows = await conn.fetch(
            "SELECT id, name, grade, age FROM student "
            "WHERE LOWER(name) LIKE LOWER($1) ORDER BY id",
            like,
        )
    return [_row_to_student(tuple(r)) for r in rows]


async def _seed_sample_if_empty_sqlite(db: aiosqlite.Connection) -> None:
    async with db.execute("SELECT COUNT(*) FROM student") as cursor:
        row = await cursor.fetchone()
    if row is None or row[0] > 0:
        return
    await db.executemany(
        "INSERT INTO student (name, grade, age) VALUES (?, ?, ?)",
        _SAMPLE_STUDENTS,
    )
    await db.commit()


async def _seed_sample_if_empty_pg(conn: asyncpg.Connection) -> None:
    n = await conn.fetchval("SELECT COUNT(*)::bigint FROM student")
    if n and n > 0:
        return
    await conn.executemany(
        "INSERT INTO student (name, grade, age) VALUES ($1, $2, $3)",
        list(_SAMPLE_STUDENTS),
    )


@asynccontextmanager
async def lifespan(app: FastAPI):
    if _use_postgres():
        dsn = os.getenv("DATABASE_URL", "").strip()
        # Transaction pooler(6543)는 prepared statement 비호환 → statement_cache_size=0 권장
        pool = await asyncpg.create_pool(
            dsn, min_size=1, max_size=10, statement_cache_size=0
        )
        app.state.pool = pool
        app.state.db_kind = "pg"
        async with pool.acquire() as conn:
            await conn.execute(_CREATE_POSTGRES)
            await _seed_sample_if_empty_pg(conn)
    else:
        db_path = os.getenv("SQLITE_DB_PATH", str(Path(__file__).resolve().parent / "student.db"))
        app.state.sqlite_path = str(Path(db_path).resolve())
        db = await aiosqlite.connect(db_path)
        app.state.db = db
        app.state.db_kind = "sqlite"
        await db.execute(_CREATE_SQLITE)
        await db.commit()
        await _seed_sample_if_empty_sqlite(db)

    yield

    if getattr(app.state, "db_kind", "") == "pg":
        await app.state.pool.close()
    else:
        await app.state.db.close()


app = FastAPI(title="Student REST API", version="1.0.0", lifespan=lifespan)


def _db_badge(request: Request) -> str:
    return "PostgreSQL · Supabase" if getattr(request.app.state, "db_kind", "") == "pg" else "SQLite · 로컬"


def _health_hint(request: Request) -> str:
    return "PostgreSQL 버전" if getattr(request.app.state, "db_kind", "") == "pg" else "SQLite 경로·버전"


@app.get("/", response_class=HTMLResponse)
async def app_page(request: Request) -> HTMLResponse:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            rows = await conn.fetch("SELECT id, name, grade, age FROM student ORDER BY id")
        students = [_row_to_student(tuple(r)) for r in rows]
    else:
        db: aiosqlite.Connection = request.app.state.db
        async with db.execute(
            "SELECT id, name, grade, age FROM student ORDER BY id"
        ) as cursor:
            rows = await cursor.fetchall()
        students = [_row_to_student(row) for row in rows]
    return templates.TemplateResponse(
        request=request,
        name="index.html",
        context={
            "students": students,
            "db_badge": _db_badge(request),
            "health_hint": _health_hint(request),
        },
    )


async def _health_payload(request: Request) -> dict[str, str]:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            ver = await conn.fetchval("SELECT version()")
        ver_short = (ver or "")[:120]
        return {
            "status": "ok",
            "database": "postgresql",
            "driver": "asyncpg",
            "server_version": ver_short,
            "hint": "Supabase는 DATABASE_URL(Postgres 연결 문자열)로 연결됩니다.",
        }
    db: aiosqlite.Connection = request.app.state.db
    async with db.execute("SELECT sqlite_version()") as cur:
        row = await cur.fetchone()
    ver = row[0] if row else "unknown"
    return {
        "status": "ok",
        "database": "sqlite",
        "driver": "aiosqlite",
        "sqlite_version": ver,
        "db_file": getattr(request.app.state, "sqlite_path", ""),
    }


@app.get("/health", response_model=None)
async def health(request: Request) -> HTMLResponse | dict[str, str]:
    data = await _health_payload(request)
    accept = (request.headers.get("accept") or "").lower()
    if "text/html" not in accept:
        return data
    rows = "".join(
        f"<tr><th>{html.escape(k)}</th><td>{html.escape(str(v))}</td></tr>"
        for k, v in data.items()
    )
    body = f"""<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <title>헬스 체크</title>
  <style>
    body {{ font-family: system-ui, sans-serif; max-width: 52rem; margin: 2rem auto; padding: 0 1rem; }}
    h1 {{ font-size: 1.25rem; }}
    table {{ border-collapse: collapse; width: 100%; }}
    th, td {{ border: 1px solid #ccc; padding: 0.5rem 0.75rem; text-align: left; vertical-align: top; }}
    th {{ background: #f4f4f4; width: 11rem; }}
    code {{ font-size: 0.85em; word-break: break-all; }}
    a {{ color: #2563eb; }}
  </style>
</head>
<body>
  <h1>서버 / DB 상태</h1>
  <p><a href="/">← 학생 관리 화면으로</a> · JSON만 필요하면 <code>curl</code> 또는 Accept 헤더 없이 요청하세요.</p>
  <table>{rows}</table>
</body>
</html>"""
    return HTMLResponse(content=body)


@app.get("/students", response_model=list[StudentResponse])
async def get_students(
    request: Request,
    q: str | None = Query(
        default=None,
        description="이름 부분 일치 검색. 값이 숫자만이면 ID 일치 또는 이름에 해당 숫자 포함",
    ),
) -> list[StudentResponse]:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            return await _query_students_pg(conn, q)
    return await _query_students_sqlite(request.app.state.db, q)


@app.get("/students/{student_id}", response_model=StudentResponse)
async def get_student(student_id: int, request: Request) -> StudentResponse:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            row = await conn.fetchrow(
                "SELECT id, name, grade, age FROM student WHERE id = $1",
                student_id,
            )
        if row is None:
            raise HTTPException(status_code=404, detail="Student not found")
        return _row_to_student(tuple(row))
    db: aiosqlite.Connection = request.app.state.db
    async with db.execute(
        "SELECT id, name, grade, age FROM student WHERE id = ?",
        (student_id,),
    ) as cursor:
        row = await cursor.fetchone()
    if row is None:
        raise HTTPException(status_code=404, detail="Student not found")
    return _row_to_student(row)


@app.post(
    "/students",
    response_model=StudentResponse,
    status_code=status.HTTP_201_CREATED,
)
async def create_student(payload: StudentCreate, request: Request) -> StudentResponse:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            row = await conn.fetchrow(
                """
                INSERT INTO student (name, grade, age)
                VALUES ($1, $2, $3)
                RETURNING id, name, grade, age
                """,
                payload.name,
                payload.grade,
                payload.age,
            )
        if row is None:
            raise HTTPException(status_code=500, detail="Insert returned no row")
        return _row_to_student(tuple(row))
    db: aiosqlite.Connection = request.app.state.db
    cursor = await db.execute(
        """
        INSERT INTO student (name, grade, age)
        VALUES (?, ?, ?)
        RETURNING id, name, grade, age
        """,
        (payload.name, payload.grade, payload.age),
    )
    row = await cursor.fetchone()
    await db.commit()
    return _row_to_student(row)


@app.put("/students/{student_id}", response_model=StudentResponse)
async def update_student(
    student_id: int, payload: StudentUpdate, request: Request
) -> StudentResponse:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            row = await conn.fetchrow(
                """
                UPDATE student
                SET name = $1, grade = $2, age = $3
                WHERE id = $4
                RETURNING id, name, grade, age
                """,
                payload.name,
                payload.grade,
                payload.age,
                student_id,
            )
        if row is None:
            raise HTTPException(status_code=404, detail="Student not found")
        return _row_to_student(tuple(row))
    db: aiosqlite.Connection = request.app.state.db
    cursor = await db.execute(
        """
        UPDATE student
        SET name = ?, grade = ?, age = ?
        WHERE id = ?
        RETURNING id, name, grade, age
        """,
        (payload.name, payload.grade, payload.age, student_id),
    )
    row = await cursor.fetchone()
    if row is None:
        raise HTTPException(status_code=404, detail="Student not found")
    await db.commit()
    return _row_to_student(row)


@app.delete("/students/{student_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_student(student_id: int, request: Request) -> None:
    if getattr(request.app.state, "db_kind", "") == "pg":
        async with request.app.state.pool.acquire() as conn:
            row = await conn.fetchrow(
                "DELETE FROM student WHERE id = $1 RETURNING id",
                student_id,
            )
        if row is None:
            raise HTTPException(status_code=404, detail="Student not found")
        return
    db: aiosqlite.Connection = request.app.state.db
    cursor = await db.execute(
        "DELETE FROM student WHERE id = ?",
        (student_id,),
    )
    if cursor.rowcount == 0:
        raise HTTPException(status_code=404, detail="Student not found")
    await db.commit()


if __name__ == "__main__":
    uvicorn.run("main:app", host="127.0.0.1", port=8000, reload=True)
