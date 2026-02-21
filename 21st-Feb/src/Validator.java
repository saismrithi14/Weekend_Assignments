import java.util.*;
import java.time.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.*;

public class Validator
{
    private static Set<Integer> set = ConcurrentHashMap.newKeySet();
    public static boolean isValid(Trade trade)
    {
        return isValidTradeId(trade.getTradeId())
                && isValidAccountId(trade.getAccountId())
                && isValidQuantity(trade.getQuantity())
                && isValidPrice(trade.getPrice())
                && isValidSide(trade.getSide())
                && isPastOrToday(trade.getTimestamp());
    }

    private static boolean isValidTradeId(int id)
    {
        return set.add(id);
    }

    private static boolean isValidAccountId(int id)
    {
        return id > 0;
    }

    private static  boolean isValidQuantity(int quantity)
    {
        return quantity >= 0;
    }

    private static boolean isValidPrice(double price)
    {
        return price > 0.0;
    }

    private static boolean isValidSide(String side)
    {
        String s = side.toLowerCase();
        return s.equals("buy") || s.equals("sell");
    }

    private static boolean isPastOrToday(Instant timestamp)
    {
        ZoneId zone = ZoneId.systemDefault();
        LocalDate tradeDate = timestamp.atZone(zone).toLocalDate();
        LocalDate today = LocalDate.now();
        return !tradeDate.isAfter(today);
    }

}