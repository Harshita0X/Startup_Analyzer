import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String pass = "harshita";
        System.out.println("Attempting to connect to MySQL...");
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            System.out.println("SUCCESS: Connected to MySQL!");
        } catch (SQLException e) {
            System.out.println("FAILURE: Could not connect to MySQL.");
            e.printStackTrace();
        }
    }
}
