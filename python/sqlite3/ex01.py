import sqlite3
import os

# sqlite 플러그인 : sqlite
# CTRL + SHIFT + P : SQLite 검색

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, "student.db")
TXT_PATH = os.path.join(BASE_DIR, "student.txt")

conn = sqlite3.connect(DB_PATH)
cur = conn.cursor()

cur.execute(
    """
    CREATE TABLE IF NOT EXISTS students (
        id      INTEGER PRIMARY KEY AUTOINCREMENT,
        name    TEXT    NOT NULL,
        age     INTEGER NOT NULL,
        grade   TEXT
    )
"""
)

students = [
    ("홍길동", 20, "2학년"),
    ("김철수", 22, "3학년"),
    ("이영희", 19, "1학년"),
    ("박민준", 21, "2학년"),
    ("최수아", 23, "4학년"),
]

cur.executemany(
    """
    INSERT OR IGNORE INTO students (name, age, grade) VALUES (?, ?, ?)
""",
    students,
)

# MYSQL : 오토 커밋 / Oracle, sqlite3 : 수동 커밋
conn.commit()

cur.execute("SELECT * FROM students")
rows = cur.fetchall()

print(f"{'ID':<5} {'이름':<10} {'나이':<6} {'학년'}")
print("-" * 30)
for row in rows:
    print(f"{row[0]:<5} {row[1]:<10} {row[2]:<6} {row[3]}")

conn.close()
