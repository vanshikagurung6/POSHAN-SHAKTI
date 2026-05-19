import java.sql.Connection;
import java.sql.DriverManager;


public class DBConnection {

    private static final String DB_URL  = "jdbc:mysql://localhost:3306/poshan_shakti?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";

  
    private static final String DB_PASS = System.getenv("DB_PASS") != null
                                        ? System.getenv("DB_PASS")
                                        : "admin123";  

    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            System.err.println("DB Connection Failed: " + e.getMessage());
            e.printStackTrace();
        }
        return con;
    }
}
