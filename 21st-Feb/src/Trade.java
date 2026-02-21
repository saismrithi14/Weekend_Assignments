import java.util.*;
import java.time.Instant;
public class Trade
{
    private int tradeId;
    private int accountId;
    private String symbol;
    private int quantity;
    private double price;
    private String side;
    private Instant timestamp;


    public Trade(int tradeId, int accountId, String symbol, int quantity, double price, String side, Instant timestamp)
    {
        this.tradeId = tradeId;
        this.accountId = accountId;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.side = side;
        this.timestamp = timestamp;
    }

    @Override
    public String toString()
    {

        return Integer.toString(tradeId) + Integer.toString(accountId);
    }

    public int getTradeId()
    {
        return this.tradeId;
    }

    public int getAccountId()
    {
        return this.accountId;
    }

    public String getSymbol()
    {
        return this.symbol;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public double getPrice()
    {
        return this.price;
    }

    public String getSide()
    {
        return this.side;
    }

    public Instant getTimestamp()
    {
        return this.timestamp;
    }


}