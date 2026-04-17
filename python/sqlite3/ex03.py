import csv
import os
import sqlite3


BASE_DIR = os.path.dirname(os.path.abspath(__file__))
DB_PATH = os.path.join(BASE_DIR, "student.db")
CSV_PATH = os.path.join(BASE_DIR, "student.csv")


def export_students_to_csv(db_path, csv_path):
	conn = sqlite3.connect(db_path)
	cur = conn.cursor()

	cur.execute("SELECT * FROM students")
	rows = cur.fetchall()
	headers = [desc[0] for desc in cur.description]

	with open(csv_path, "w", newline="", encoding="utf-8-sig") as f:
		writer = csv.writer(f)
		writer.writerow(headers)
		writer.writerows(rows)

	conn.close()
	print(f"CSV 저장 완료: {csv_path}")
	print(f"총 {len(rows)}건 저장")


if __name__ == "__main__":
	export_students_to_csv(DB_PATH, CSV_PATH)
