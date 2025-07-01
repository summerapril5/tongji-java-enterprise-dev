package test;

import java.sql.*;

public class TestPreparedStatement {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://[address]:[port]/[db]";
    static final String USER = "[username]";
    static final String PASS = "[password]";

    public static void main(String[] args) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database.");

            sql = "SELECT student_id, name FROM t_student where hometown = ?";

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, "CHINA");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");

                System.out.print("ID: " + id);
                System.out.println(" / Name: " + name);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null)
                    pstmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

        System.out.println("Completed.");
    }
}
