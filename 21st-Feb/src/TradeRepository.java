import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TradeRepository
{
    private static final String select = "SELECT 1 FROM Trade WHERE trade_id = ?";
    private static final String query = "INSERT INTO Trade VALUES(?,?,?,?,?,?,?)";

    public void save(Trade trade)
    {

        try(Connection conn = DatabaseManager.getConnection())
        {
            try(PreparedStatement ps = conn.prepareStatement(select))
            {
                ps.setInt(1, trade.getTradeId());
                try(ResultSet rs = ps.executeQuery())
                {
                    if(rs.next())
                    {
                        System.out.println("Trade Id: " + trade.getTradeId() + " already exists");
                        return;
                    }
                }
            }

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
                System.out.println("Row has been updated successfully");


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

