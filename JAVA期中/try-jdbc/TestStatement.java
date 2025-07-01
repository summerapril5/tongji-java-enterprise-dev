package test;

import java.sql.*;

public class TestStatement {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://[address]:[port]/[db]";
    static final String USER = "[username]";
    static final String PASS = "[password]";

    public static void main(String[] args) {

        Connection conn = null;
        Statement stmt = null;
        String sql = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database.");

            stmt = conn.createStatement();

            sql = "SELECT student_id, name, hometown FROM t_student";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("student_id");
                String name = rs.getString("name");
                String hometown = rs.getString("hometown");

                System.out.print("ID: " + id);
                System.out.print(" / Name: " + name);
                System.out.println(" / Name: " + hometown);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
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
