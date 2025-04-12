package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.StudentAttendanceDto;
import org.example.util.AttendanceNameUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/attendance")
public class StudentAttendanceServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupFilter = req.getParameter("group");

        List<StudentAttendanceDto> list = getStudentsFromDB(groupFilter);
        List<String> groups = getGroupsFromDB();

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<style>  table { width: 50%; border-collapse: collapse; margin: 20px 0; font-size: 18px; text-align: left; } th, td { border: 1px solid black; padding: 8px; } th { background-color: #f2f2f2; }</style>");
        out.println("<body>");
        out.println("<h2>Посещение лекций</h2>");

        out.println("<form action='/ServletPractice/attendance' method='POST'>");
        out.println("ФИО: <input type='text' name='name' required><br>");
        out.println("Группа: <input type='text' name='groupName' required><br>");
        out.println("Посетил: <select name='isAttended'><option value='true'>Да</option><option value='false'>Нет</option></select><br>");
        out.println("<input type='submit' value='Добавить'>");
        out.println("</form>");

        // Фильтр по группе
        out.println("<form action='/ServletPractice/attendance' method='GET'>");
        out.println("Группа: <select name='group'>");
        out.println("<option value=''>Все</option>");
        for (String group : groups) {
            out.println("<option value='" + group + "'" + (group.equals(groupFilter) ? " selected" : "") + ">" + group + "</option>");
        }
        out.println("</select>");
        out.println("<input type='submit' value='Фильтровать'>");
        out.println("</form>");

        out.println("<table>");
        out.println("    <tr><th>ФИО</th><th>Группа</th><th>Посетил</th><th>Действие</th></tr>");

        if (list.isEmpty()) {
            out.println("</table>");
            out.println("<h1>Нет данных в таблице</h1>");
        } else {
            for (StudentAttendanceDto studentAttendanceDto : list) {
                out.println("<tr>");
                out.println("<td>" + studentAttendanceDto.getName() + "</td>");
                out.println("<td>" + studentAttendanceDto.getGroupName() + "</td>");
                out.println("<td>" + AttendanceNameUtil.fromBooleanToString(studentAttendanceDto.isAttended()) + "</td>");
                out.println("<td><a href='/ServletPractice/attendance/delete?id=" + studentAttendanceDto.getName() + "'>Удалить</a></td>");
                out.println("</tr>");
            }
        }
        out.println("</table>");
        out.println("</body>");
        out.println("</html>");
    }

    private List<StudentAttendanceDto> getStudentsFromDB(String groupFilter) {
        String sql = "SELECT * FROM students";
        if (groupFilter != null && !groupFilter.isEmpty()) {
            sql += " WHERE group_name = ?";
        }

        List<StudentAttendanceDto> result = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (groupFilter != null && !groupFilter.isEmpty()) {
                stmt.setString(1, groupFilter);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    StudentAttendanceDto dto = StudentAttendanceDto.builder()
                            .name(rs.getString("name"))
                            .groupName(rs.getString("group_name"))
                            .isAttended(rs.getBoolean("is_attended"))
                            .build();
                    result.add(dto);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<String> getGroupsFromDB() {
        List<String> groups = new ArrayList<>();
        String sql = "SELECT DISTINCT group_name FROM students";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                groups.add(rs.getString("group_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String groupName = req.getParameter("groupName");
        boolean isAttended = Boolean.parseBoolean(req.getParameter("isAttended"));

        // Добавляем группу в таблицу groups, если она ещё не существует
        addGroupIfNotExists(groupName);

        // Сохраняем студента
        saveStudentToDB(name, groupName, isAttended);

        resp.sendRedirect("/ServletPractice/attendance");
    }

    private void addGroupIfNotExists(String groupName) {
        String checkGroupSql = "SELECT COUNT(*) FROM groups WHERE group_name = ?";
        String insertGroupSql = "INSERT INTO groups (group_name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement checkStmt = conn.prepareStatement(checkGroupSql)) {

            checkStmt.setString(1, groupName);
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) == 0) {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertGroupSql)) {
                        insertStmt.setString(1, groupName);
                        insertStmt.executeUpdate();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveStudentToDB(String name, String groupName, boolean isAttended) {
        String insertStudentSql = "INSERT INTO students (name, group_name, is_attended) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertStudentSql)) {

            stmt.setString(1, name);
            stmt.setString(2, groupName);
            stmt.setBoolean(3, isAttended);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Обработчик удаления студента
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String studentName = req.getParameter("id");
        if (studentName != null) {
            deleteStudentFromDB(studentName);
        }
        resp.sendRedirect("/ServletPractice/attendance");
    }

    private void deleteStudentFromDB(String studentName) {
        String deleteStudentSql = "DELETE FROM students WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(deleteStudentSql)) {

            stmt.setString(1, studentName);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
