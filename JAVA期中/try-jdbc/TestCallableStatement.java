package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestCallableStatement {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://[address]:[port]/[db]";
    static final String USER = "[username]";
    static final String PASS = "[password]";

    public static void main(String[] args) {

        Connection conn = null;
        CallableStatement pstmt = null;
        String sql = null;

        try {
            Class.forName(JDBC_DRIVER);

            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to the database.");

            sql = "{call p_compute_amount_by_region(?,?)}";

            pstmt = conn.prepareCall(sql);

            pstmt.setString(1, "China");
            pstmt.registerOutParameter(2, java.sql.Types.INTEGER);

            pstmt.execute();

            System.out.println(pstmt.getInt(2));

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
