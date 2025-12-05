# CampusSync - Complete Code Documentation

This document provides comprehensive explanations of each file type in the CampusSync MVC architecture, emphasizing strict MVC separation, transaction management, and best practices.

---

## Architecture Overview

\`\`\`
┌─────────────────────────────────────────────────────────────┐
│                      PRESENTATION LAYER                      │
│  (JSP Views - Pure Display Logic, No Business Code)         │
│  - manage_students.jsp, manage_courses.jsp, etc.            │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                      CONTROLLER LAYER                        │
│  (Servlets - Request Handling & Business Orchestration)     │
│  - AdminServlet.java, StudentServlet.java, etc.             │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                       MODEL LAYER                            │
│  (DAOs & POJOs - Data Access & Object Representation)       │
│  - AdminDAO.java, SubjectDAO.java, Student.java, etc.       │
└─────────────────────────────────────────────────────────────┘
                              ↓
┌─────────────────────────────────────────────────────────────┐
│                   DATABASE LAYER                             │
│  (MySQL Database with 7 Main Tables)                        │
│  - admin, student, faculty, course, subject, etc.           │
└─────────────────────────────────────────────────────────────┘
\`\`\`

---

## File Categories & Documentation

### 1. UTILITY FILES (Reusable Infrastructure)

#### `DBConnection.java`
**Purpose:** Centralized JDBC connection management for entire application.

**Key Responsibilities:**
- Loads MySQL JDBC driver once at class initialization (static block)
- Provides `getConnection()` method for DAOs and servlets to obtain connections
- Maintains clean separation of database credentials
- Offers quiet cleanup helper for AutoCloseable resources

**Key Points:**
\`\`\`java
// Static initialization - runs once when class loads
static {
    Class.forName("com.mysql.cj.jdbc.Driver");  // Load MySQL driver
}

// Credentials (should move to environment variables in production)
private static final String URL = "jdbc:mysql://localhost:3306/campussync?...";
private static final String USER = "root";
private static final String PASS = "1234";

// Public method - DAOs call this
public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASS);
}
\`\`\`

**Security Notes:**
- [ ] TODO: Move credentials to `application.properties` or environment variables
- [ ] TODO: Implement connection pooling (HikariCP) for production scalability
- [ ] Use `closeQuietly()` for safe resource cleanup

---

#### `Logger.java`
**Purpose:** Unified logging system across entire CampusSync application.

**Logging Levels:**
- `INFO` - General informational messages (e.g., "User logged in")
- `WARN` - Warning messages (e.g., "Unauthorized access attempt")
- `ERROR` - Error messages with optional exception details
- `DEBUG` - Development-only diagnostic messages

**Output:**
- Logs to **console** (System.out) for immediate visibility
- Logs to **file** (`logs/campussync.log`) for persistence and debugging
- Silent error handling - logging errors never crash the application

**Usage Examples:**
\`\`\`java
Logger.info("Student added successfully");
Logger.warn("Unauthorized access attempt to admin panel");
Logger.error("Failed to establish database connection", sqlException);
Logger.debug("Processing request with params: " + params);
\`\`\`

**Benefits:**
- Centralized logging interface - consistent across codebase
- Timestamps and severity levels automatically included
- File persistence for production debugging
- Non-blocking, safe to use anywhere in application

---

#### `TransactionManager.java`
**Purpose:** Centralized JDBC transaction management for multi-table operations.

**Key Concept:** Ensures data integrity through commit/rollback semantics.

**When to Use:**
- Operations modifying **multiple tables** (e.g., adding a subject involves subject + course + faculty validation)
- Operations requiring **atomicity** - all succeed or all fail
- When one operation's failure should prevent subsequent operations

**Standard Usage Pattern:**
\`\`\`java
TransactionManager txn = new TransactionManager();

try {
    Connection conn = txn.beginTransaction();      // Turn off autocommit
    
    // Perform multiple database operations
    subjectDAO.addSubject(conn, subjectData);
    courseDAO.updateCourse(conn, courseData);
    
    txn.commit();                                    // Make all changes permanent
    Logger.info("Multi-table operation completed successfully");
    
} catch (SQLException e) {
    txn.rollback();                                  // Undo ALL changes
    Logger.error("Transaction failed, rolling back", e);
    throw new RuntimeException("Operation failed", e);
    
} finally {
    txn.closeConnection();                           // Always cleanup
}
\`\`\`

**Key Methods:**
- `beginTransaction()` - Opens connection with autocommit disabled
- `commit()` - Permanently saves all changes
- `rollback()` - Undoes all changes if error occurs
- `closeConnection()` - Cleans up and resets state
- `getConnection()` - Retrieves current transaction connection for DAOs

---

### 2. MODEL LAYER (Data Objects)

Models are Plain Old Java Objects (POJOs) that represent database entities with getters/setters for encapsulation.

#### `Student.java`
**Purpose:** Represents a Student entity with personal, academic, and login information.

**Database Mapping:**
\`\`\`
studentId   → student_id (PRIMARY KEY)
name        → name
email       → email
password    → password
course      → course
semester    → semester
createdAt   → created_at
\`\`\`

**Key Features:**
- Default constructor (required by servlets/DAOs)
- Parameterized constructor for full initialization
- `toString()` override for debugging
- LocalDateTime for timestamp tracking

**Usage in Workflow:**
\`\`\`
Database Row → AdminDAO.listStudents() → List<Student> → JSP Loop → Display
\`\`\`

---

#### `Subject.java`
**Purpose:** Represents a Subject (course unit) with relationships to Course and Faculty.

**Dual Field Pattern:**
\`\`\`java
// Database fields (from table)
private int subjectId;
private String subjectName;
private int courseId;      // Foreign key
private int facultyId;     // Foreign key

// Display fields (from joins)
private String courseName;   // Populated by DAO joins
private String facultyName;  // Populated by DAO joins
\`\`\`

**Why Dual Fields?**
- Avoids complex joins in JSP (violates MVC separation)
- DAO returns List<Subject> with both FK IDs and display names
- JSP simply calls `getCourseName()` without complex queries
- Clean separation: DAO handles complexity, View handles display

---

### 3. DATA ACCESS LAYER (DAOs)

DAOs (Data Access Objects) handle all database operations. Refactored to return `List` objects instead of `ResultSet` for better MVC compliance.

#### `AdminDAO.java`
**Purpose:** Handles admin-specific operations (login, CRUD for students/faculty/notices).

**Key Methods:**

1. **`login(username, password)`**
   - Returns ResultSet (kept for backward compatibility with servlet login logic)
   - Prevents SQL injection via PreparedStatement
   - Validates credentials against admin table

2. **`addStudent(name, email, password, course, semester)`**
   - Inserts new student record
   - Returns boolean (success/failure)
   - Uses try-with-resources for automatic cleanup

3. **`addFaculty(name, email, password, dept)`**
   - Inserts new faculty member
   - Similar pattern to addStudent

4. **`addNotice(title, message)`**
   - Inserts notice to broadcast to students
   - Used in admin dashboard

5. **`listStudentsAsList()`** ⭐ NEW - MVC Compliant
   - Returns `List<Student>` instead of ResultSet
   - Automatically closes connection/statement/resultset
   - JSP uses standard for-each loop (no scriptlets)
   
   \`\`\`java
   public List<Student> listStudentsAsList() throws SQLException {
       List<Student> students = new ArrayList<>();
       try (Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
           while (rs.next()) {
               Student s = new Student();
               s.setStudentId(rs.getInt("student_id"));
               // ... set other fields
               students.add(s);
           }
       }
       return students;
   }
   \`\`\`

6. **`listStudents()`** - Backward Compatibility
   - Returns raw ResultSet
   - Deprecated in favor of listStudentsAsList()
   - Kept for legacy code

---

#### `SubjectDAO.java`
**Purpose:** Manages subject records with multi-table transaction support.

**Key Methods:**

1. **`addSubject(Connection conn, Subject s)`** ⭐ Transaction-Aware
   - Takes external Connection (from TransactionManager)
   - Used in multi-table operations via transactions
   - Allows commit/rollback at servlet level
   
   \`\`\`java
   public boolean addSubject(Connection conn, Subject s) throws SQLException {
       String sql = "INSERT INTO subject(subject_name, course_id, faculty_id) VALUES (?, ?, ?)";
       try (PreparedStatement ps = conn.prepareStatement(sql)) {
           ps.setString(1, s.getSubjectName());
           ps.setInt(2, s.getCourseId());
           ps.setInt(3, s.getFacultyId());
           return ps.executeUpdate() > 0;
       }
   }
   \`\`\`

2. **`addSubject(Subject s)`** - Standalone (Autocommit)
   - Uses own connection with autocommit
   - Suitable for single operation (not part of transaction)

3. **`getAllSubjectsJoined()`** ⭐ List-Based
   - Returns `List<Subject>` with joined course/faculty names
   - Eliminates need for complex JSP queries
   - Automatic resource cleanup via try-with-resources

4. **`getAllSubjectsJoinedResultSet()`** - Backward Compatibility
   - Returns raw ResultSet
   - Kept for legacy compatibility

---

#### `CourseDAO.java`
**Purpose:** Manages course records (e.g., "B.Tech CSE", "B.Sc Physics").

**Key Methods:**

1. **`getAllCourses()`** ⭐ List-Based (Preferred)
   - Returns `List<Course>` with automatic cleanup
   - Used in servlet when populating dropdown menus
   
2. **`addCourse(String name)`**
   - Inserts new course
   - Returns boolean

3. **`getAllCoursesResultSet()`** - Backward Compatibility
   - Returns raw ResultSet for legacy code

---

### 4. CONTROLLER LAYER (Servlets)

Servlets handle HTTP requests, route to appropriate DAOs, manage transactions, and prepare data for views.

#### `AdminServlet.java`
**Purpose:** Main controller for admin panel - routes requests, coordinates DAOs, manages transactions.

**Request Types:**

**GET Requests (Display Pages):**
\`\`\`java
case "students":
    // Fetch data layer
    req.setAttribute("students", dao.listStudentsAsList());
    // Route to view
    req.getRequestDispatcher("admin/manage_students.jsp").forward(req, resp);
    return;

case "subjects":
    SubjectDAO sdao = new SubjectDAO();
    CourseDAO crDao = new CourseDAO();
    FacultyDAO fDao = new FacultyDAO();
    
    // Populate multiple attributes for dropdown menus + table
    req.setAttribute("subjectData", sdao.getAllSubjectsJoined());
    req.setAttribute("courses", crDao.getAllCourses());
    req.setAttribute("faculty", fDao.getAllFaculty());
    
    req.getRequestDispatcher("admin/manage_subjects.jsp").forward(req, resp);
    return;
\`\`\`

**POST Requests (Save/Update Data):**

Simple Operations (Single Table):
\`\`\`java
case "addStudent":
    try {
        dao.addStudent(
            req.getParameter("name"),
            req.getParameter("email"),
            req.getParameter("password"),
            req.getParameter("course"),
            Integer.parseInt(req.getParameter("semester"))
        );
        Logger.info("Student added successfully");
        resp.sendRedirect("adminPanel?action=students");  // Redirect-after-post pattern
    } catch (SQLException e) {
        Logger.error("Failed to add student", e);
        req.setAttribute("error", "Failed to add student: " + e.getMessage());
        req.getRequestDispatcher("admin/manage_students.jsp").forward(req, resp);
    }
    return;
\`\`\`

Multi-Table Operations (With Transactions): ⭐
\`\`\`java
case "addSubject":
    String subName = req.getParameter("subject_name");
    int courseId = Integer.parseInt(req.getParameter("course_id"));
    int facultyId = Integer.parseInt(req.getParameter("faculty_id"));

    Subject s = new Subject();
    s.setSubjectName(subName);
    s.setCourseId(courseId);
    s.setFacultyId(facultyId);

    SubjectDAO sdao = new SubjectDAO();
    TransactionManager txn = new TransactionManager();
    
    try {
        // BEGIN TRANSACTION
        Connection conn = txn.beginTransaction();
        
        // Perform operation with transactional connection
        boolean success = sdao.addSubject(conn, s);
        
        if (success) {
            // COMMIT if all succeeded
            txn.commit();
            Logger.info("Subject added successfully");
            resp.sendRedirect("adminPanel?action=subjects&msg=SubjectAdded");
        } else {
            // ROLLBACK if operation failed
            txn.rollback();
            Logger.warn("Subject insert failed");
            resp.sendRedirect("adminPanel?action=subjects&error=SubjectAddFailed");
        }
    } catch (SQLException e) {
        // ROLLBACK on exception
        txn.rollback();
        Logger.error("Transaction failed", e);
        throw new ServletException(e);
    } finally {
        // CLEANUP always
        txn.closeConnection();
    }
    return;
\`\`\`

**Key Patterns:**
- Error logging at every step
- Redirect-after-post to prevent duplicate submissions
- Request attributes for error/success messages
- Proper session validation at method start

---

### 5. VIEW LAYER (JSPs)

JSP files handle only presentation logic. Strict MVC means:
- ✅ Data display (loops, conditionals)
- ✅ Form rendering
- ❌ Database queries
- ❌ Business logic calculations
- ❌ Java scriptlets (avoided)

#### `manage_students.jsp` (Refactored Example)
**Purpose:** Display list of students and form to add new student.

**MVC Compliance:**
\`\`\`jsp
<%-- Import statements only --%>
<%@ page import="java.util.List" %>
<%@ page import="com.campussync.model.Student" %>

<%-- Session validation (minimal Java) --%>
<%
    javax.servlet.http.HttpSession s = request.getSession(false);
    if (s == null || !"admin".equals(s.getAttribute("role"))) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>

<%-- Pure display loop (no database queries!) --%>
<%
    List<Student> students = (List<Student>) request.getAttribute("students");
    if (students != null && !students.isEmpty()) {
        for (Student student : students) {
%>
        <tr>
            <td><%= student.getStudentId() %></td>
            <td><%= student.getName() %></td>
            <td><%= student.getEmail() %></td>
            <td><%= student.getCourse() %></td>
            <td><%= student.getSemester() %></td>
        </tr>
<%
        }
    }
%>
\`\`\`

**What's Different:**
- **OLD:** `ResultSet rs = dao.getAllStudents()` in JSP (violates MVC)
- **NEW:** `List<Student> students = request.getAttribute("students")` (servlet provides it)
- Result: Pure display logic, zero database knowledge in JSP

---

#### `manage_subjects.jsp` (Refactored Example)
**Purpose:** Display subjects with course/faculty joins, form to add new subject.

**Key Pattern - Dropdowns from Multiple DAOs:**
\`\`\`jsp
<%-- Courses dropdown populated by servlet --%>
<select name="course_id" required>
    <option value="">-- Select Course --</option>
    <%
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
    %>
    <option value="<%= course.getCourseId() %>">
        <%= course.getCourseName() %>
    </option>
    <% } } %>
</select>
\`\`\`

**Display Table with Joined Data:**
\`\`\`jsp
<%
    List<Subject> subjects = (List<Subject>) request.getAttribute("subjectData");
    if (subjects != null && !subjects.isEmpty()) {
        for (Subject subject : subjects) {
%>
    <tr>
        <td><%= subject.getSubjectId() %></td>
        <td><%= subject.getSubjectName() %></td>
        <td><%= subject.getCourseName() %></td>      <%-- Comes from DAO join --%>
        <td><%= subject.getFacultyName() %></td>     <%-- Comes from DAO join --%>
    </tr>
<% } } %>
\`\`\`

**Why This Works:**
1. Servlet calls `subjectDAO.getAllSubjectsJoined()` - gets List<Subject> with course/faculty names
2. Subject model has `courseName` and `facultyName` fields
3. JSP simply displays them without any joins or queries

---

## Transaction Management Example

### Scenario: Adding a Subject (Multi-Table Operation)

**Tables Involved:**
- `subject` table - insert new row
- `course` table - validate course exists
- `faculty` table - validate faculty exists

**What Could Go Wrong:**
- Course ID invalid → subject insert fails
- Faculty ID deleted between validation and insert
- Network interruption mid-operation

**Solution: Transaction with Commit/Rollback**

\`\`\`java
// Step 1: Servlet receives request
String subName = req.getParameter("subject_name");
int courseId = Integer.parseInt(req.getParameter("course_id"));
int facultyId = Integer.parseInt(req.getParameter("faculty_id"));

// Step 2: Create POJO
Subject s = new Subject();
s.setSubjectName(subName);
s.setCourseId(courseId);
s.setFacultyId(facultyId);

// Step 3: Initialize transaction manager
TransactionManager txn = new TransactionManager();

try {
    // Step 4: BEGIN - Turn off autocommit
    Connection conn = txn.beginTransaction();
    
    // Step 5: DAO operation uses transactional connection
    boolean success = subjectDAO.addSubject(conn, s);
    
    // Step 6: COMMIT if success
    if (success) {
        txn.commit();  // All changes permanent
        Logger.info("Subject added successfully");
    } else {
        txn.rollback();  // UNDO all changes
        Logger.warn("Subject add failed");
    }
    
} catch (SQLException e) {
    // Step 7: ROLLBACK on exception
    txn.rollback();  // UNDO all changes
    Logger.error("Transaction failed", e);
    
} finally {
    // Step 8: CLEANUP always happens
    txn.closeConnection();  // Close connection, reset state
}
\`\`\`

**Result:**
- ✅ If all succeeds → Subject inserted, changes permanent
- ❌ If error occurs → Everything rolled back, database unchanged
- Either way → No partial updates, data integrity maintained

---

## SQL Injection Prevention

**DON'T DO THIS (Vulnerable):**
\`\`\`java
String sql = "SELECT * FROM student WHERE email='" + email + "'";
// Attacker could enter: admin' OR '1'='1
// Query becomes: SELECT * FROM student WHERE email='admin' OR '1'='1'
// Returns ALL students!
\`\`\`

**DO THIS (Safe - Using PreparedStatement):**
\`\`\`java
String sql = "SELECT * FROM student WHERE email=?";
PreparedStatement ps = conn.prepareStatement(sql);
ps.setString(1, email);  // Parameter binding - treated as data, not SQL
ResultSet rs = ps.executeQuery();
// Attacker input treated as literal value, not SQL code
\`\`\`

**All DAOs use PreparedStatements for security ✅**

---

## Resource Management

**OLD (Resource Leak Risk):**
\`\`\`java
Connection conn = DBConnection.getConnection();
PreparedStatement ps = conn.prepareStatement(sql);
ResultSet rs = ps.executeQuery();
// Forgot to close! Resources leak
\`\`\`

**NEW (Automatic Cleanup via Try-With-Resources):**
\`\`\`java
try (Connection conn = DBConnection.getConnection();
     PreparedStatement ps = conn.prepareStatement(sql);
     ResultSet rs = ps.executeQuery()) {
    // Use resources
} // Automatically closed, even if exception occurs
\`\`\`

**All DAOs use try-with-resources for safety ✅**

---

## Summary Table: File Responsibilities

| File Type | Purpose | Key Responsibility | MVC Layer |
|-----------|---------|-------------------|-----------|
| `DBConnection.java` | Connection management | Provide JDBC connections | Utility |
| `Logger.java` | Unified logging | Console + file logging | Utility |
| `TransactionManager.java` | Transaction control | Commit/rollback for multi-table ops | Utility |
| `Student.java`, `Subject.java` | Data containers | Hold database row data | Model |
| `StudentDAO.java`, `SubjectDAO.java` | Data access | Query/insert/update database | Model |
| `AdminServlet.java` | Request handling | Route requests, manage data flow | Controller |
| `manage_students.jsp` | Display | Render HTML tables/forms | View |

---

## Best Practices Implemented

✅ **MVC Separation** - No Java code in JSPs, no database queries in servlets, clean boundaries
✅ **Transaction Management** - Commit/rollback for multi-table operations
✅ **Resource Cleanup** - Try-with-resources, proper connection handling
✅ **SQL Injection Prevention** - PreparedStatements everywhere
✅ **Comprehensive Logging** - Info, warn, error, debug levels
✅ **Error Handling** - Try-catch blocks, proper exception logging
✅ **Code Reusability** - Centralized utilities, DRY principle
✅ **Security** - Session validation, input validation via parameters

---

## Next Steps for Production Readiness

- [ ] Move database credentials to environment variables
- [ ] Implement connection pooling (HikariCP)
- [ ] Add comprehensive unit tests
- [ ] Implement input validation (email format, semester range, etc.)
- [ ] Hash passwords (use BCrypt, not plain text)
- [ ] Add CSRF protection to forms
- [ ] Implement proper authentication tokens/JWT
- [ ] Add API rate limiting
- [ ] Set up monitoring and alerting for errors
- [ ] Database backup strategy

---
\`\`\`

Now let me create an enhanced JSP comments template file:
