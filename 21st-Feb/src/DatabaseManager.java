import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseManager
{
    private static final String connection_string = "jdbc:postgresql://localhost:5432/TradeDB";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(connection_string, USER, PASSWORD);
    }

}