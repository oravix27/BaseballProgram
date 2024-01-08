import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    // declares constant variables
    private static final String DELIMITER = ",";
    private static final String EXTENSION = ".csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    // saves data to a CSV file
    public static void savePlayerData(Player player)
    {
        // filename becomes the player's name with spaces replaced by underscores
        String filename = player.getName().replaceAll(" ", "_") + EXTENSION;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename)))
        {
            // iterate over each game's stats
            for (GameStats stats : player.getGameStatsList())
            {
                StringBuilder sb = new StringBuilder();

                // format the date
                sb.append(stats.getDate().format(DATE_FORMATTER));

                // append results, separated by the delimiter - ','
                for (int result : stats.getAtBatResults())
                {
                    sb.append(DELIMITER).append(result);
                }
                sb.append(DELIMITER).append(stats.getRuns());
                sb.append(DELIMITER).append(stats.getRbis());

                // write the StringBuilder content to the file and add a new line after each game's stats
                writer.write(sb.toString());
                writer.newLine();
            }
        }
        catch (IOException e)
        {
            // error handling
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // reads player data from CSV file
    public static Player readPlayerData(String name)
    {
        // filename is still the player's name with spaces replaced by underscores
        String filename = name.replaceAll(" ", "_") + EXTENSION;
        // list to hold game stats read from the file
        List<GameStats> gameStatsList = new ArrayList<>();
        // File object for the filename
        File file = new File(filename);

        // check if the file exists before trying to read it
        if (file.exists())
        {
            try (BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String line;

                // read each line from the file
                while ((line = reader.readLine()) != null)
                {
                    // split the line into parts using the delimiter
                    String[] data = line.split(DELIMITER);

                    // parse the date
                    LocalDate date = LocalDate.parse(data[0], DATE_FORMATTER);

                    // create a new GameStats object
                    GameStats stats = new GameStats(date);

                    // parse each at-bat result and add it to the game stats
                    for (int i = 1; i < data.length - 2; i++)
                    {
                        stats.addAtBatResult(Integer.parseInt(data[i]), 0); // Assumes 0 RBIs for each at-bat result
                    }

                    // set the runs and RBIs
                    stats.setRuns(Integer.parseInt(data[data.length - 2]));
                    stats.setRbis(Integer.parseInt(data[data.length - 1]));

                    // add the game stats to the list
                    gameStatsList.add(stats);
                }
            }
            catch (IOException e)
            {
                // error handling
                System.out.println("Error reading file: " + e.getMessage());
                return null;
            }
        }

        // create a new Player object and add gameStats to it
        Player player = new Player(name);
        player.getGameStatsList().addAll(gameStatsList);

        // return the player object with the added game stats
        return player;
    }
}
