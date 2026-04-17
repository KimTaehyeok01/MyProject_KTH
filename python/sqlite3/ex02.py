# sqlite3 CRUD 예제
# CRUD = Create(추가), Read(조회), Update(수정), Delete(삭제) - 데이터베이스의 4가지 기본 기능

import sqlite3  # 파이썬에 기본으로 내장된 SQLite 데이터베이스 라이브러리
import os       # 파일 경로를 다루기 위한 라이브러리

# 이 파이썬 파일이 있는 폴더 경로를 구합니다.
# __file__ 은 현재 파이썬 파일 자신의 경로를 의미합니다.
BASE_DIR = os.path.dirname(os.path.abspath(__file__))

# 데이터베이스 파일(student.db)의 전체 경로를 만듭니다.
# 예: C:\MyProject\python\sqlite3\student.db
DB_PATH  = os.path.join(BASE_DIR, "student.db")

# 데이터베이스 파일에 연결합니다. 파일이 없으면 자동으로 새로 만들어집니다.
conn = sqlite3.connect(DB_PATH)

# 커서(cursor)는 SQL 명령을 실행하는 도구입니다. 마치 마우스 커서처럼 DB 안을 가리키는 역할을 합니다.
cur  = conn.cursor()

# 테이블을 만드는 SQL 명령을 실행합니다.
# CREATE TABLE IF NOT EXISTS = 테이블이 없을 때만 새로 만든다 (이미 있으면 무시)
# id    : 자동으로 1씩 증가하는 고유 번호 (PRIMARY KEY AUTOINCREMENT)
# name  : 문자열(TEXT), 반드시 값이 있어야 함(NOT NULL)
# age   : 정수(INTEGER), 반드시 값이 있어야 함
# grade : 문자열(TEXT), 값이 없어도 됨
cur.execute("""
    CREATE TABLE IF NOT EXISTS students (
        id    INTEGER PRIMARY KEY AUTOINCREMENT,
        name  TEXT    NOT NULL,
        age   INTEGER NOT NULL,
        grade TEXT
    )
""")

# commit() 은 변경 사항을 데이터베이스에 실제로 저장(확정)하는 명령입니다.
# commit() 을 하지 않으면 변경 내용이 저장되지 않습니다.
conn.commit()


# 학생 전체 목록을 화면에 출력하는 함수
def print_all():
    # SELECT * FROM students = students 테이블의 모든 행(데이터)을 가져오는 SQL
    cur.execute("SELECT * FROM students")

    # fetchall() 은 조회된 모든 행을 리스트로 가져옵니다.
    rows = cur.fetchall()

    # 표 형식으로 헤더(제목 줄)를 출력합니다.
    # f"..." 는 f-string으로, 중괄호 안에 변수나 식을 넣을 수 있습니다.
    # :<5 는 왼쪽 정렬로 5칸 너비를 확보한다는 뜻입니다 (표를 보기 좋게 정렬).
    print(f"\n{'ID':<5} {'이름':<10} {'나이':<6} {'학년'}")
    print("-" * 30)  # "-" 문자를 30번 반복해서 구분선을 그립니다.

    # rows 리스트를 하나씩 꺼내서 출력합니다.
    # row[0]=id, row[1]=name, row[2]=age, row[3]=grade 순서입니다.
    for row in rows:
        print(f"{row[0]:<5} {row[1]:<10} {row[2]:<6} {row[3]}")


# CREATE: 새 학생 데이터를 데이터베이스에 추가하는 함수
def create(name, age, grade):
    # INSERT INTO = 테이블에 새 행을 삽입하는 SQL
    # ? 는 자리 표시자(placeholder)로, 두 번째 인자의 튜플 값으로 순서대로 채워집니다.
    # 직접 값을 문자열로 넣지 않고 ? 를 쓰는 이유는 SQL 인젝션 공격을 방지하기 위해서입니다.
    cur.execute(
        "INSERT INTO students (name, age, grade) VALUES (?, ?, ?)",
        (name, age, grade),
    )
    conn.commit()  # 변경 사항을 저장합니다.
    print(f"[추가] {name} ({age}세, {grade})")


# READ: 특정 ID의 학생 정보를 조회하는 함수
def read(student_id):
    # WHERE id = ? 는 id가 일치하는 행만 가져오는 조건입니다.
    # (student_id,) 처럼 값이 하나일 때도 튜플로 만들기 위해 쉼표를 붙입니다.
    cur.execute("SELECT * FROM students WHERE id = ?", (student_id,))

    # fetchone() 은 조회된 행 중 첫 번째 하나만 가져옵니다. 없으면 None을 반환합니다.
    row = cur.fetchone()

    if row:  # row 가 None 이 아니면 (즉, 데이터가 존재하면)
        print(f"[조회] ID={row[0]}  이름={row[1]}  나이={row[2]}  학년={row[3]}")
    else:    # 해당 ID의 학생이 없으면
        print(f"[조회] ID={student_id} 에 해당하는 학생이 없습니다.")


# UPDATE: 특정 ID의 학생 정보를 수정하는 함수
def update(student_id, age, grade):
    # SET age = ?, grade = ? 는 수정할 컬럼과 값을 지정합니다.
    # WHERE id = ? 로 어떤 학생의 정보를 바꿀지 지정합니다.
    cur.execute(
        "UPDATE students SET age = ?, grade = ? WHERE id = ?",
        (age, grade, student_id),
    )
    conn.commit()  # 변경 사항을 저장합니다.
    print(f"[수정] ID={student_id}  나이→{age}  학년→{grade}")


# DELETE: 특정 ID의 학생 데이터를 삭제하는 함수
def delete(student_id):
    # DELETE FROM students WHERE id = ? = 해당 id의 행을 테이블에서 삭제합니다.
    cur.execute("DELETE FROM students WHERE id = ?", (student_id,))
    conn.commit()  # 변경 사항을 저장합니다.
    print(f"[삭제] ID={student_id}")


# --- 실행 예시 ---

# 학생 3명을 데이터베이스에 추가합니다.
create("홍길동", 20, "2학년")
create("김철수", 22, "3학년")
create("이영희", 19, "1학년")

# 현재 전체 학생 목록을 출력합니다.
print_all()

# ID가 1인 학생(홍길동)의 정보를 조회합니다.
read(1)

# ID가 1인 학생의 나이를 21, 학년을 3학년으로 수정합니다.
update(1, 21, "3학년")
print_all()  # 수정 후 전체 목록 확인

# ID가 2인 학생(김철수)을 삭제합니다.
delete(2)
print_all()  # 삭제 후 전체 목록 확인

# 데이터베이스 연결을 종료합니다. 작업이 끝나면 항상 닫아야 합니다.
conn.close()
