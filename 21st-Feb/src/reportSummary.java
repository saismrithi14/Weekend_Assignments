import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class reportSummary {
    private Portfolio portfolio;
    private List<Trade> trade_requests;
    private final Path output_path = Path.of("..", "report.txt");

    public reportSummary(Portfolio portfolio, List<Trade> trade_requests) {
        this.portfolio = portfolio;
        this.trade_requests = trade_requests;
    }

    private void holdingsPerAccount() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Summary of Trade requests\n\n");
            sb.append("Holding per account\n");
            sb.append("AccountId|Symbol|Quantity\n");
            Map<Integer, Map<String, Integer>> map = portfolio.getPortfolio();
            for (Map.Entry<Integer, Map<String, Integer>> entry : map.entrySet()) {
                Integer accountId = entry.getKey();
                Map<String, Integer> accountValue = entry.getValue();
                //iterating over the mini hashmap
                for (Map.Entry<String, Integer> accountEntry : accountValue.entrySet()) {
                    String symbol = accountEntry.getKey();
                    Integer value = accountEntry.getValue();

                    sb.append(accountId).append("|").append(symbol).append("|").append(value).append("\n");
                }
            }

            Files.writeString(output_path, sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void totalTradesPerAccount() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("Total trades per account\n");
            sb.append("Account id|Number of trades\n");
            Map<Integer, Long> trade_mapped = trade_requests.stream().collect(Collectors.groupingBy(Trade::getAccountId, Collectors.counting()));
            for (Map.Entry<Integer, Long> entry : trade_mapped.entrySet()) {
                Integer account_id = entry.getKey();
                Long count = entry.getValue();
                sb.append(account_id).append("|").append(count).append("\n");
            }

            Files.writeString(output_path, sb.toString(), StandardOpenOption.APPEND);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void totalQuantityPerSymbol()
    {
        StringBuilder sb = new StringBuilder();
        try
        {
            sb.append("\n\nTotal quantity held per symbol\n");
            sb.append("Symbol|Quantity\n");
            Map<String, Integer> symbol_mapped = trade_requests.stream().collect(Collectors.groupingBy(Trade::getSymbol, Collectors.summingInt(Trade::getQuantity)));
            for(Map.Entry<String, Integer> entry: symbol_mapped.entrySet())
            {
                String symbol = entry.getKey();
                Integer sum = entry.getValue();
                sb.append(symbol).append("|").append(sum).append("\n");
            }

            Files.writeString(output_path, sb.toString(), StandardOpenOption.APPEND);
        }

        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void generateReport()
    {
        holdingsPerAccount();
        totalTradesPerAccount();
        totalQuantityPerSymbol();
    }


}