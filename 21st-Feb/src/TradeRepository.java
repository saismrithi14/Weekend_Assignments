import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TradeRepository
{
    private static final String query = "INSERT INTO Trade VALUES(?,?,?,?,?,?,?) ON CONFLICT(trade_id) DO NOTHING";

    public void save(Trade trade)
    {

        try(Connection conn = DatabaseManager.getConnection())
        {

            try(PreparedStatement insert_query = conn.prepareStatement(query))
            {
                insert_query.setInt(1,trade.getTradeId());
                insert_query.setInt(2,trade.getAccountId());
                insert_query.setString(3,trade.getSymbol());
                insert_query.setInt(4,trade.getQuantity());
                insert_query.setDouble(5,trade.getPrice());
                insert_query.setString(6,trade.getSide());
                insert_query.setTimestamp(7,java.sql.Timestamp.from(trade.getTimestamp()));
                int rows_affected = insert_query.executeUpdate();




            }

            catch(SQLException e)
            {
                System.out.println("An error occured while inserting");
                e.printStackTrace();
            }
        }


        catch(SQLException se)
        {
            System.out.println("An exception occured");
            se.printStackTrace();
        }
    }
}

