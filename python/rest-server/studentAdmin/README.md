# StudentAdmin (Spring Boot Proxy for Python CRUD)

This project is a Spring Boot web server (port 8080) that provides a Thymeleaf admin UI and proxies student CRUD requests to a Python REST server (port 8000).

## Architecture

Browser <-> Spring Boot (8080) <-> Python API Server (8000)

- Browser calls only Spring endpoints.
- Spring proxies requests to Python `/students` endpoints.
- Data persistence depends on the Python server implementation.

## Tech Stack

- Java 21 (Gradle toolchain)
- Spring Boot 3.3.5
- Spring Web + Thymeleaf
- Gradle (no Maven)

## Project Goal

Provide an admin web page for:

- List all students (GET)
- Read one student (GET by id)
- Create a student (POST)
- Update a student (PUT)
- Delete a student (DELETE)

## Spring Endpoints

### Page

- `GET /` : admin UI (Thymeleaf)

### Proxy API (used by UI)

- `GET /api/students` : list students (`q` query optional)
- `GET /api/students/{studentId}` : read one student
- `POST /api/students` : create
- `PUT /api/students/{studentId}` : update
- `DELETE /api/students/{studentId}` : delete

### Generic proxy API (legacy/test)

- `GET /api/python/health`
- `POST /api/python/proxy`

## Python Server Requirements

Spring forwards to `python.server.base-url` in `src/main/resources/application.properties`.

Default:

- `python.server.base-url=http://localhost:8000`

Your Python server at port 8000 must expose compatible endpoints:

- `GET /students`
- `GET /students/{studentId}`
- `POST /students`
- `PUT /students/{studentId}`
- `DELETE /students/{studentId}`

### Body format note

Depending on which Python app is running, create payload can differ:

- Some apps require `student_id` in POST body.
- Some apps auto-generate id and do not require `student_id`.

## Run Guide (Windows)

### 1) Start Python server first (port 8000)

Example:

```powershell
cd c:\GitHub\MyProject_KTH\python\rest-server
python fastapi-server.py
```

### 2) Start Spring server (port 8080)

Run from this project directory:

```powershell
cd c:\GitHub\MyProject_KTH\python\rest-server\studentAdmin
.\gradlew.bat bootRun --no-daemon
```

### 3) Open page

- http://localhost:8080/

## Build and Test

```powershell
cd c:\GitHub\MyProject_KTH\python\rest-server\studentAdmin
.\gradlew.bat test
```

## Troubleshooting

### `Port 8080 was already in use`

Stop the process using 8080, or change Spring port in:

- `src/main/resources/application.properties`

### POST returns 422 from Python

Usually means Python did not accept/parse request body for that endpoint schema.

Check:

- Correct endpoint path (`/students` vs other paths)
- Required fields for the running Python app
- JSON format and content type

### Requests fail when running `bootRun`

Make sure `bootRun` is executed inside:

- `c:\GitHub\MyProject_KTH\python\rest-server\studentAdmin`

## Key Files

- `build.gradle`
- `src/main/resources/templates/index.html`
- `src/main/java/com/study/studentAdmin/controller/StudentCrudProxyController.java`
- `src/main/java/com/study/studentAdmin/service/PythonProxyService.java`
- `src/main/resources/application.properties`
