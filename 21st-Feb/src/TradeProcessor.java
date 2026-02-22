import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TradeProcessor
{
    private final TradeRepository repo;
    private final Portfolio portfolio;
    private ExecutorService executor;

    public TradeProcessor(TradeRepository repo, Portfolio portfolio, int threadCount)
    {
        this.repo =repo;
        this.portfolio = portfolio;
        this.executor = Executors.newFixedThreadPool(threadCount);
    }

    private void process_trades(Trade trade)
    {
        repo.save(trade);
        portfolio.updatePosition(trade);
        System.out.println(Thread.currentThread().getName() + " is working on trade with id: " + trade.getTradeId());
    }

    public void processTrades(List<Trade> trades)
    {
        for(Trade trade: trades)
        {
            executor.submit(()->process_trades(trade));
        }
    }

    public void shutdown()
    {
        executor.shutdown();
        try
        {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }

        catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }




}