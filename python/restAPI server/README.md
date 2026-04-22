# Student REST API (FastAPI + Turso)

Simple REST API server for a `student` table:

- `name`
- `grade`
- `age`

## 1) Install

```powershell
cd "C:\GitHub\MyProject_KTH\python\restAPI server"
python -m venv .venv
.\.venv\Scripts\Activate.ps1
pip install -r requirements.txt
```

## 2) Connect Turso

You already created a DB. Next, get URL and token:

```powershell
turso db show <DB_NAME> --url
turso db tokens create <DB_NAME>
```

Then create `.env`:

```env
TURSO_DATABASE_URL=libsql://...turso.io
TURSO_AUTH_TOKEN=...
```

## 3) Run server

```powershell
uvicorn main:app --reload
```

Docs:

- Swagger UI: `http://127.0.0.1:8000/docs`
- ReDoc: `http://127.0.0.1:8000/redoc`

## 4) REST endpoints

- `GET /health`
- `GET /students`
- `GET /students/{student_id}`
- `POST /students`
- `PUT /students/{student_id}`
- `DELETE /students/{student_id}`

### Example request body (`POST`, `PUT`)

```json
{
  "name": "Kim",
  "grade": 2,
  "age": 16
}
```

## Notes

- Table is auto-created at startup:
  `student(id, name, grade, age)`.
