import re
from typing import Dict

from flask import Flask, jsonify, render_template_string, request


app = Flask(__name__)

# In-memory DB (student_id -> student row)
db: Dict[str, dict] = {
    "1001": {"name": "Kim Minsu", "grade": 1, "age": 20},
    "1002": {"name": "Lee Jiyun", "grade": 2, "age": 21},
    "1003": {"name": "Park Junho", "grade": 3, "age": 22},
}
STUDENT_ID_PATTERN = re.compile(r"^\d{4}$")
INDEX_HTML = """
<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Student CRUD</title>
  <style>
    body { font-family: Arial, sans-serif; margin: 0; background: #f8f9fb; }
    .container { max-width: 760px; margin: 24px auto; background: #fff; padding: 20px; border: 1px solid #e5e7eb; border-radius: 10px; }
    h1, h2 { margin-bottom: 8px; }
    form { margin: 8px 0 16px; padding: 12px; border: 1px solid #ddd; width: 420px; }
    label { display: inline-block; width: 80px; margin-bottom: 8px; }
    input { width: 280px; padding: 4px; margin-bottom: 8px; }
    button { padding: 6px 10px; margin-right: 6px; }
    table { border-collapse: collapse; margin-top: 12px; width: 700px; }
    th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
    #message { margin: 10px 0; font-weight: bold; }
  </style>
</head>
<body>
  <div class="container">
  <h1>Student REST API + HTML CRUD</h1>
  <p>API endpoint: <code>/students</code></p>
  <div id="message"></div>

  <h2>학생 등록</h2>
  <form id="createForm">
    <label>학번</label><input id="create_id" maxlength="4" placeholder="4 digits" required /><br />
    <label>이름</label><input id="create_name" required /><br />
    <label>학년</label><input id="create_grade" type="number" min="1" required /><br />
    <label>나이</label><input id="create_age" type="number" min="1" required /><br />
    <button type="submit">등록</button>
  </form>

  <h2>학생 수정</h2>
  <form id="updateForm">
    <label>학번</label><input id="update_id" maxlength="4" required /><br />
    <label>이름</label><input id="update_name" required /><br />
    <label>학년</label><input id="update_grade" type="number" min="1" required /><br />
    <label>나이</label><input id="update_age" type="number" min="1" required /><br />
    <button type="submit">수정</button>
  </form>

  <h2>학생 목록</h2>
  <button id="refreshBtn">새로고침</button>
  <table>
    <thead>
      <tr><th>학번</th><th>이름</th><th>학년</th><th>나이</th><th>삭제</th></tr>
    </thead>
    <tbody id="studentsBody"></tbody>
  </table>

  <script>
    const messageEl = document.getElementById("message");
    const studentsBody = document.getElementById("studentsBody");

    function setMessage(text, ok = true) {
      messageEl.textContent = text;
      messageEl.style.color = ok ? "green" : "red";
    }

    async function refreshStudents() {
      const res = await fetch("/students");
      const data = await res.json();
      studentsBody.innerHTML = "";
      data.forEach((s) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
          <td>${s.student_id}</td>
          <td>${s.name}</td>
          <td>${s.grade}</td>
          <td>${s.age}</td>
          <td><button onclick="deleteStudent('${s.student_id}')">삭제</button></td>
        `;
        studentsBody.appendChild(tr);
      });
    }

    async function deleteStudent(studentId) {
      const res = await fetch(`/students/${studentId}`, { method: "DELETE" });
      if (res.status === 204) {
        setMessage("삭제 완료");
        await refreshStudents();
        return;
      }
      const err = await res.json();
      setMessage(err.error || "삭제 실패", false);
    }

    document.getElementById("createForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const payload = {
        student_id: document.getElementById("create_id").value,
        name: document.getElementById("create_name").value,
        grade: Number(document.getElementById("create_grade").value),
        age: Number(document.getElementById("create_age").value),
      };
      const res = await fetch("/students", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (res.ok) {
        setMessage("등록 완료");
        e.target.reset();
        await refreshStudents();
        return;
      }
      const err = await res.json();
      setMessage(err.error || "등록 실패", false);
    });

    document.getElementById("updateForm").addEventListener("submit", async (e) => {
      e.preventDefault();
      const studentId = document.getElementById("update_id").value;
      const payload = {
        name: document.getElementById("update_name").value,
        grade: Number(document.getElementById("update_grade").value),
        age: Number(document.getElementById("update_age").value),
      };
      const res = await fetch(`/students/${studentId}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (res.ok) {
        setMessage("수정 완료");
        await refreshStudents();
        return;
      }
      const err = await res.json();
      setMessage(err.error || "수정 실패", false);
    });

    document.getElementById("refreshBtn").addEventListener("click", refreshStudents);
    refreshStudents();
  </script>
  </div>
</body>
</html>
"""


def _validate_student_id(student_id: str) -> bool:
    return bool(STUDENT_ID_PATTERN.match(student_id))


def _validate_payload(payload: dict, require_id: bool) -> str | None:
    required_fields = ["name", "grade", "age"]
    if require_id:
        required_fields = ["student_id", *required_fields]

    for field in required_fields:
        if field not in payload:
            return f"Missing field: {field}"

    if require_id and not _validate_student_id(str(payload["student_id"])):
        return "student_id must be exactly 4 digits"

    if not isinstance(payload["name"], str) or not payload["name"].strip():
        return "name must be a non-empty string"

    if not isinstance(payload["grade"], int) or payload["grade"] < 1:
        return "grade must be an integer >= 1"

    if not isinstance(payload["age"], int) or payload["age"] < 1:
        return "age must be an integer >= 1"

    return None


def _validate_patch_payload(payload: dict) -> str | None:
    allowed_fields = {"name", "grade", "age"}
    if not payload:
        return "At least one field is required"

    unknown_fields = set(payload.keys()) - allowed_fields
    if unknown_fields:
        return f"Unknown field: {sorted(unknown_fields)[0]}"

    if "name" in payload and (
        not isinstance(payload["name"], str) or not payload["name"].strip()
    ):
        return "name must be a non-empty string"

    if "grade" in payload and (
        not isinstance(payload["grade"], int) or payload["grade"] < 1
    ):
        return "grade must be an integer >= 1"

    if "age" in payload and (not isinstance(payload["age"], int) or payload["age"] < 1):
        return "age must be an integer >= 1"

    return None


@app.get("/")
def index():
    return render_template_string(INDEX_HTML)


@app.get("/health")
def health():
    return jsonify({"message": "student rest api is running"}), 200


@app.get("/students")
def list_students():
    return jsonify([{"student_id": sid, **row} for sid, row in db.items()]), 200


@app.post("/students")
def create_student():
    payload = request.get_json(silent=True) or {}
    error = _validate_payload(payload, require_id=True)
    if error:
        return jsonify({"error": error}), 400

    student_id = payload["student_id"]
    if student_id in db:
        return jsonify({"error": "Student already exists"}), 409

    db[student_id] = {
        "name": payload["name"],
        "grade": payload["grade"],
        "age": payload["age"],
    }
    return jsonify({"student_id": student_id, **db[student_id]}), 201


@app.get("/students/<student_id>")
def get_student(student_id: str):
    if not _validate_student_id(student_id):
        return jsonify({"error": "student_id must be exactly 4 digits"}), 400

    row = db.get(student_id)
    if row is None:
        return jsonify({"error": "Student not found"}), 404

    return jsonify({"student_id": student_id, **row}), 200


@app.put("/students/<student_id>")
def update_student(student_id: str):
    if not _validate_student_id(student_id):
        return jsonify({"error": "student_id must be exactly 4 digits"}), 400

    if student_id not in db:
        return jsonify({"error": "Student not found"}), 404

    payload = request.get_json(silent=True) or {}
    error = _validate_payload(payload, require_id=False)
    if error:
        return jsonify({"error": error}), 400

    db[student_id] = {
        "name": payload["name"],
        "grade": payload["grade"],
        "age": payload["age"],
    }
    return jsonify({"student_id": student_id, **db[student_id]}), 200


@app.patch("/students/<student_id>")
def patch_student(student_id: str):
    if not _validate_student_id(student_id):
        return jsonify({"error": "student_id must be exactly 4 digits"}), 400

    if student_id not in db:
        return jsonify({"error": "Student not found"}), 404

    payload = request.get_json(silent=True) or {}
    error = _validate_patch_payload(payload)
    if error:
        return jsonify({"error": error}), 400

    db[student_id].update(payload)
    return jsonify({"student_id": student_id, **db[student_id]}), 200


@app.delete("/students/<student_id>")
def delete_student(student_id: str):
    if not _validate_student_id(student_id):
        return jsonify({"error": "student_id must be exactly 4 digits"}), 400

    if student_id not in db:
        return jsonify({"error": "Student not found"}), 404

    del db[student_id]
    return "", 204


if __name__ == "__main__":
    app.run(host="127.0.0.1", port=8000, debug=True)
