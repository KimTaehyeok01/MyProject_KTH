from typing import Annotated

import uvicorn
from fastapi import FastAPI, HTTPException, Path, Response, status
from fastapi.responses import RedirectResponse
from pydantic import BaseModel, Field, StringConstraints


app = FastAPI(
    title="Student REST API",
    description="In-memory student CRUD API",
    version="1.0.0",
)

StudentId = Annotated[str, StringConstraints(pattern=r"^\d{4}$")]


class StudentBase(BaseModel):
    name: str = Field(min_length=1, description="학생 이름")
    grade: int = Field(ge=1, description="학년")
    age: int = Field(ge=1, description="나이")


class StudentCreate(StudentBase):
    student_id: StudentId


class StudentUpdate(StudentBase):
    pass


class StudentPatch(BaseModel):
    name: str | None = Field(default=None, min_length=1)
    grade: int | None = Field(default=None, ge=1)
    age: int | None = Field(default=None, ge=1)


class Student(StudentBase):
    student_id: StudentId


# In-memory DB (student_id -> student row)
db: dict[str, dict] = {
    "1001": {"name": "Kim Minsu", "grade": 1, "age": 20},
    "1002": {"name": "Lee Jiyun", "grade": 2, "age": 21},
    "1003": {"name": "Park Junho", "grade": 3, "age": 22},
}


@app.get("/", include_in_schema=False)
def root():
    return RedirectResponse(url="/docs")


@app.get("/students", response_model=list[Student])
def list_students():
    return [{"student_id": student_id, **row} for student_id, row in db.items()]


@app.post("/students", response_model=Student, status_code=status.HTTP_201_CREATED)
def create_student(payload: StudentCreate):
    if payload.student_id in db:
        raise HTTPException(status_code=409, detail="Student already exists")
    db[payload.student_id] = {
        "name": payload.name,
        "grade": payload.grade,
        "age": payload.age,
    }
    return {"student_id": payload.student_id, **db[payload.student_id]}


@app.get("/students/{student_id}", response_model=Student)
def get_student(
    student_id: str = Path(..., pattern=r"^\d{4}$", description="학번 4자리"),
):
    row = db.get(student_id)
    if row is None:
        raise HTTPException(status_code=404, detail="Student not found")
    return {"student_id": student_id, **row}


@app.put("/students/{student_id}", response_model=Student)
def update_student(
    payload: StudentUpdate,
    student_id: str = Path(..., pattern=r"^\d{4}$", description="학번 4자리"),
):
    if student_id not in db:
        raise HTTPException(status_code=404, detail="Student not found")
    db[student_id] = {
        "name": payload.name,
        "grade": payload.grade,
        "age": payload.age,
    }
    return {"student_id": student_id, **db[student_id]}


@app.patch("/students/{student_id}", response_model=Student)
def patch_student(
    payload: StudentPatch,
    student_id: str = Path(..., pattern=r"^\d{4}$", description="학번 4자리"),
):
    if student_id not in db:
        raise HTTPException(status_code=404, detail="Student not found")
    updates = payload.model_dump(exclude_none=True)
    if not updates:
        raise HTTPException(status_code=400, detail="At least one field is required")
    db[student_id].update(updates)
    return {"student_id": student_id, **db[student_id]}


@app.delete("/students/{student_id}", status_code=status.HTTP_204_NO_CONTENT)
def delete_student(
    student_id: str = Path(..., pattern=r"^\d{4}$", description="학번 4자리"),
):
    if student_id not in db:
        raise HTTPException(status_code=404, detail="Student not found")
    del db[student_id]
    return Response(status_code=status.HTTP_204_NO_CONTENT)


if __name__ == "__main__":
    uvicorn.run(app, host="127.0.0.1", port=8000)
