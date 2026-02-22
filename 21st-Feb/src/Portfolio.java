import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
import java.util.concurrent.atomic.*;
public class Portfolio
{
    private ConcurrentHashMap<Integer, ConcurrentHashMap<String, AtomicInteger>> portfolio;

    public Portfolio()
    {
        this.portfolio = new ConcurrentHashMap<>();
    }

    public Map<Integer, Map<String, Integer>> getPortfolio() {
        Map<Integer, Map<String, Integer>> snapshot = new HashMap<>();

        portfolio.forEach((accountId, symbolMap) -> {
            Map<String, Integer> innerCopy = new HashMap<>();
            symbolMap.forEach((symbol, qty) -> {
                innerCopy.put(symbol, qty.get());
            });
            snapshot.put(accountId, innerCopy);
        });

        return snapshot;
    }

    public void updatePosition(Trade trade)
    {
        int id = trade.getAccountId();
        String symbol = trade.getSymbol();
        String decision = trade.getSide().toLowerCase();

        //Update atomically and also create if not there
        ConcurrentHashMap<String, AtomicInteger> holdings = portfolio.computeIfAbsent(id, k->new ConcurrentHashMap<>());

        AtomicInteger symbolQuantity = holdings.computeIfAbsent(symbol, k-> new AtomicInteger(0));

        if(decision.equals("buy"))
        {
            symbolQuantity.addAndGet(trade.getQuantity());
        }

        else {
            symbolQuantity.updateAndGet(current ->{
                int newQty = current - trade.getQuantity();
                return Math.max(newQty, 0);
            });
        }

    }
}