import urllib.request
import json
import urllib.error
import random
import datetime

def request(method, url, data=None, token=None):
    req = urllib.request.Request(url, method=method)
    if data is not None:
        req.add_header('Content-Type', 'application/json')
        req.data = json.dumps(data).encode('utf-8')
    if token:
        req.add_header('Authorization', 'Bearer ' + token)
    try:
        res = urllib.request.urlopen(req)
        body = res.read().decode('utf-8')
        return res.status, json.loads(body) if body else {}
    except urllib.error.HTTPError as e:
        body = e.read().decode('utf-8')
        try:
            parsed_body = json.loads(body)
        except:
            parsed_body = body
        return e.code, parsed_body

passed_tests = 0
failed_tests = 0
report_lines = []

def log(message):
    print(message)
    report_lines.append(message)

def log_md(message):
    report_lines.append(message)

def run_test(name, expected_status, method, url, data=None, token=None):
    global passed_tests, failed_tests
    print(f"Running test: {name}...")
    
    log_md(f"### {name}")
    log_md(f"- **Method:** `{method}`")
    log_md(f"- **URL:** `{url}`")
    log_md(f"- **Auth:** `{'Bearer Token' if token else 'None'}`")
    
    if data:
        log_md("- **Input Payload:**")
        log_md("```json\n" + json.dumps(data, indent=2) + "\n```")
    else:
        log_md("- **Input Payload:** `[No Body]`")
        
    status, body = request(method, url, data, token)
    
    log_md(f"- **Expected Status:** `{expected_status}`")
    log_md(f"- **Actual Status:** `{status}`")
    
    if body:
        log_md("- **Output Body:**")
        log_md("```json\n" + json.dumps(body, indent=2) + "\n```")
    else:
        log_md("- **Output Body:** `[Empty]`")
        
    if status == expected_status:
        log_md("\n**✅ RESULT: PASS**")
        passed_tests += 1
        return body
    else:
        log_md("\n**❌ RESULT: FAIL**")
        failed_tests += 1
        return body
    
    log_md("---\n")

BASE_URL = "http://localhost:8080"
EMAIL = f"e2e_report_{random.randint(10000,99999)}@test.com"
PASSWORD = "Password123!"

log_md(f"# LoreKeeper API Integration Test Report")
log_md(f"*Generated on: {datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')}*\n")

# 1. AUTHENTICATION & VALIDATION
log_md("## 1. Authentication & Validation\n")
run_test("Register - Missing Email", 400, "POST", f"{BASE_URL}/auth/register", {"password": PASSWORD})
run_test("Register - Invalid Email", 400, "POST", f"{BASE_URL}/auth/register", {"email": "not-an-email", "password": PASSWORD})
run_test("Register - Short Password", 400, "POST", f"{BASE_URL}/auth/register", {"email": EMAIL, "password": "short"})
body = run_test("Register - Success", 201, "POST", f"{BASE_URL}/auth/register", {"email": EMAIL, "password": PASSWORD})
TOKEN = body.get("token")

run_test("Login - Missing Password", 400, "POST", f"{BASE_URL}/auth/login", {"email": EMAIL})
run_test("Login - Wrong Password", 401, "POST", f"{BASE_URL}/auth/login", {"email": EMAIL, "password": "WrongPassword!"})
run_test("Login - Success", 200, "POST", f"{BASE_URL}/auth/login", {"email": EMAIL, "password": PASSWORD})

# 2. USER ENDPOINTS
log_md("## 2. User Endpoints\n")
run_test("Get Profile - No Token (Unauthorized)", 403, "GET", f"{BASE_URL}/users/me")
run_test("Get Profile - Success", 200, "GET", f"{BASE_URL}/users/me", token=TOKEN)

# 3. BOOK CATALOG ENDPOINTS
log_md("## 3. Book Catalog\n")
run_test("Get All Books - Success (Public)", 200, "GET", f"{BASE_URL}/books")
run_test("Create Book - Missing Title", 400, "POST", f"{BASE_URL}/books", {"author": "Author", "format": "BOOK"}, token=TOKEN)
run_test("Create Book - Negative Pages", 400, "POST", f"{BASE_URL}/books", {"title": "Title", "author": "Author", "format": "BOOK", "totalPages": -10}, token=TOKEN)

book_data = {
    "title": "E2E Test Book",
    "author": "Test Author",
    "format": "BOOK",
    "totalPages": 300
}
body = run_test("Create Book - Success", 201, "POST", f"{BASE_URL}/books", book_data, token=TOKEN)
BOOK_ID = body.get("id")

run_test("Get Book By ID", 200, "GET", f"{BASE_URL}/books/{BOOK_ID}")

update_book_data = {
    "title": "E2E Test Book (Updated)",
    "author": "Test Author",
    "format": "BOOK",
    "totalPages": 350
}
run_test("Update Book - Success", 200, "PUT", f"{BASE_URL}/books/{BOOK_ID}", update_book_data, token=TOKEN)

# 4. USER LIBRARY (TRACKING)
log_md("## 4. User Library\n")
run_test("Track Book - Missing Status", 400, "POST", f"{BASE_URL}/users/me/library", {"bookId": BOOK_ID}, token=TOKEN)
run_test("Track Book - Rating > 5", 400, "POST", f"{BASE_URL}/users/me/library", {"bookId": BOOK_ID, "status": "READING", "rating": 10}, token=TOKEN)

track_data = {
    "bookId": BOOK_ID,
    "status": "WANT_TO_READ",
    "rating": 5,
    "currentPage": 0,
    "currentChapter": 0,
    "isFavorite": False
}
body = run_test("Track Book - Success", 201, "POST", f"{BASE_URL}/users/me/library", track_data, token=TOKEN)
LIB_ID = body.get("id")

run_test("Get My Library - Success", 200, "GET", f"{BASE_URL}/users/me/library", token=TOKEN)
run_test("Update Tracking - Negative Page", 400, "PATCH", f"{BASE_URL}/users/me/library/{LIB_ID}", {"currentPage": -5}, token=TOKEN)
run_test("Update Tracking - Success", 200, "PATCH", f"{BASE_URL}/users/me/library/{LIB_ID}", {"status": "READING", "currentPage": 50}, token=TOKEN)
run_test("Delete Tracked Book - Success", 204, "DELETE", f"{BASE_URL}/users/me/library/{LIB_ID}", token=TOKEN)

# 5. OPEN LIBRARY PROXY
log_md("## 5. Open Library Proxy\n")
run_test("Search Open Library", 200, "GET", f"{BASE_URL}/open-library/works?title=Dune")

run_test("Cleanup - Delete Book Catalog Entry", 204, "DELETE", f"{BASE_URL}/books/{BOOK_ID}", token=TOKEN)

log_md(f"\n## Summary\n**{passed_tests} PASSED** | **{failed_tests} FAILED**\n")

# Write to file
import os
PROJECT_ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
REPORT_FILE = os.path.join(PROJECT_ROOT, "tmp", "api_test_report.md")

# Ensure tmp directory exists
os.makedirs(os.path.dirname(REPORT_FILE), exist_ok=True)

with open(REPORT_FILE, "w", encoding="utf-8") as f:
    f.write("\n".join(report_lines))

print(f"\n==================================================")
print(f"  TEST RUN COMPLETE: {passed_tests} PASSED | {failed_tests} FAILED")
print(f"  Report generated at: {REPORT_FILE}")
print(f"==================================================")
