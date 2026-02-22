import java.util.concurrent.ConcurrentHashMap;
import java.util.*;
public class Portfolio
{
    private Map<Integer, Map<String, Integer>> portfolio;

    public Portfolio()
    {
        this.portfolio = new ConcurrentHashMap<>();
    }

    public Map<Integer, Map<String, Integer>> getPortfolio()
    {
        return this.portfolio;
    }

    public void updatePosition(Trade trade)
    {
        int id = trade.getAccountId();
        String symbol = trade.getSymbol();
        String decision = trade.getSide().toLowerCase();

        if(decision.equals("buy"))
        {
            if(!portfolio.containsKey(id))
            {
                portfolio.put(id,new ConcurrentHashMap<>());
                portfolio.get(id).put(symbol, trade.getQuantity());
            }

            else
            {
                if(!portfolio.get(id).containsKey(symbol))
                {
                    portfolio.get(id).put(symbol, trade.getQuantity());
                }

                else
                {
                    int count = portfolio.get(id).get(symbol);
                    count = count + trade.getQuantity();
                    portfolio.get(id).put(symbol, count);
                }
            }
        }

        else
        {
            if(portfolio.containsKey(id) && portfolio.get(id).containsKey(symbol))
            {
                int count = portfolio.get(id).get(symbol);
                int new_count = count - trade.getQuantity();
                if(new_count >= 0)
                {
                    portfolio.get(id).put(symbol, new_count);
                }
            }
        }

    }
}