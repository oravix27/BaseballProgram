import java.time.LocalDate; 
import java.util.Scanner;

public class BaseballProgram
{
    public static void main(String[] args)
    {
        // setting up input
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        // while loop to keep program running until exit is called
        while (running)
        {
            // input player name
            System.out.print("Enter Player's Name: ");
            String name = scanner.nextLine();

            // checks for existing file
            Player player = FileHandler.readPlayerData(name);

            if (player == null)
            {
                player = new Player(name);
            }

            // user is prompted to pick an option
            System.out.println("Choose an option:");
            System.out.println("1. Enter new game stats");
            System.out.println("2. View stats for all games");
            System.out.println("3. View total stats");
            System.out.println("4. Clear stats for this player");
            System.out.println("5. Exit program");
            int choice = scanner.nextInt();
            scanner.nextLine();

            // handling of option selected
            switch (choice) {
                case 1:
                    enterNewGameStats(player, scanner);
                    FileHandler.savePlayerData(player);
                    break;
                case 2:
                    displayAllGameStats(player);
                    break;
                case 3:
                    displayTotalStats(player);
                    break;
                case 4:
                    clearPlayerStats(player);
                    FileHandler.savePlayerData(player);
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void enterNewGameStats(Player player, Scanner scanner)
    {
        // user prompted to enter various bits of information of the game
        System.out.print("Enter the date of the game (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        GameStats gameStats = new GameStats(date);

        System.out.print("How many plate appearances did the batter have? ");
        int plateAppearances = scanner.nextInt();

        for (int i = 0; i < plateAppearances; i++)
        {
            System.out.print("----------\nResult of plate appearance #" + (i + 1) + " \n----------\n(0 for walk, 1 for single," +
                    "2 for double, 3 for triple,\n4 for home run, 5 for sacrifice bunt, " +
                    "6 for strikeout, 7 for ground out, \n8 for flyout, " +
                    "9 for lineout., 10 for HBP, 11 for SF): \n");
            int result = scanner.nextInt();

            int rbi = 0;
            if (result >= 1 && result <= 4)
            {
                System.out.print("How many RBIs for this hit? ");
                rbi = scanner.nextInt();
            }
            gameStats.addAtBatResult(result, rbi);

            System.out.print("Did the batter score a run? (yes/no): ");
            if (scanner.next().trim().equalsIgnoreCase("yes"))
            {
                gameStats.addRun();
            }
        }

        // stats saved using gameStats class
        player.addGameStats(gameStats);
        System.out.println("Stats added for the game on " + date);
    }

    // display game by game stats for the player
    private static void displayAllGameStats(Player player)
    {
        for (GameStats stats : player.getGameStatsList())
        {
            System.out.println("Date: " + stats.getDate());
            System.out.println("At-Bat Results: " + stats.getAtBatResults());
            System.out.println("Runs: " + stats.getRuns());
            System.out.println("RBIs: " + stats.getRbis());
            System.out.println("Batting Average: " + stats.getBattingAverage());
            System.out.println("On-Base Percentage: " + stats.getOnBasePercentage());
            System.out.println();
        }
    }

    // display all stats for the player
    private static void displayTotalStats(Player player)
    {
        int totalHits = 0;
        int totalWalks = 0;
        int totalStrikeouts = 0;
        int totalHitByPitch = 0;
        int totalSacrificeFlies = 0;
        int totalAtBats = 0;
        int totalRuns = 0;
        int totalRbis = 0;

        for (GameStats stats : player.getGameStatsList())
        {
            totalAtBats += stats.getAtBats();
            totalRuns += stats.getRuns();
            totalRbis += stats.getRbis();
            totalHitByPitch += stats.getHitByPitch();
            totalSacrificeFlies += stats.getSacrificeFlies();

            for (int result : stats.getAtBatResults())
            {
                if (result == 0) totalWalks++;
                else if (result >= 1 && result <= 4) totalHits++;  // 1 to 4 are hits
                else if (result == 6) totalStrikeouts++;          // 6 is a strikeout
            }
        }

        double battingAverage = totalAtBats > 0 ? (double) totalHits / totalAtBats : 0.0;
        int totalBases = totalAtBats + totalWalks + totalHitByPitch + totalSacrificeFlies;
        double onBasePercentage = totalBases > 0 ? (double) (totalHits + totalWalks + totalHitByPitch) / totalBases : 0.0;

        System.out.println("Total Stats for " + player.getName() + ":");
        System.out.println("Total Hits: " + totalHits);
        System.out.println("Total Walks: " + totalWalks);
        System.out.println("Total Strikeouts: " + totalStrikeouts);
        System.out.println("Total Runs: " + totalRuns);
        System.out.println("Total RBIs: " + totalRbis);
        System.out.println("Batting Average: " + battingAverage);
        System.out.println("On-Base Percentage: " + onBasePercentage);
    }

    // clear all stats for the player
    private static void clearPlayerStats(Player player)
    {
        player.clearStats();
        System.out.println("All statistics cleared for " + player.getName());
    }
}
