import java.nio.file.*;
import java.io.*;
import java.util.*;
import java.time.Instant;
public class Main {
    public static void main(String[] args)  {
        List<Trade> trade_objects = FileReader();
        List<Trade> valid_trade = trade_objects.stream().filter(Validator::isValid).toList();
        System.out.println();// Space for visibility

    }

    public static Path getPath()
    {
        return Path.of("..","trades.csv");
    }

    public static String readFile(Path input_path) throws IOException
    {
        String content = Files.readString(input_path);
        return content;
    }

    public static List<Trade> parseFile(String content)
    {
        List<Trade> trade_objects = new ArrayList<>();
        String[] lines = content.split("\n");
        for(int i = 1; i < lines.length; i++)
        {
            String [] fields = lines[i].split(",");
            Trade trade = new Trade(
                    Integer.parseInt(fields[0]),
                    Integer.parseInt(fields[1]),
                    fields[2],
                    Integer.parseInt(fields[3]),
                    Double.parseDouble(fields[4]),
                    fields[5],
                    Instant.parse(fields[6].trim() + "Z"));
            trade_objects.add(trade);
        }

        return trade_objects;
    }

    public static List<Trade> FileReader()
    {
        Path input_path = getPath();
        try {
            String content = readFile(input_path);
            List<Trade> trade_objects;
            trade_objects = parseFile(content);
            System.out.println("Final trade objects list: " + trade_objects);
            return trade_objects;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        return null; // I needed to return something but the trade objects would be out
                     // of scope
    }

}