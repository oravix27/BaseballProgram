import java.util.ArrayList;
import java.util.List; 

public class Player
{
    private final String name;
    private final List<GameStats> gameStatsList;

    public Player(String name)
    {
        this.name = name;
        this.gameStatsList = new ArrayList<>();
    }

    public void addGameStats(GameStats stats)
    {
        gameStatsList.add(stats);
    }

    public void clearStats()
    {
        this.gameStatsList.clear();
    }

    public String getName()
    {
        return name;
    }

    public List<GameStats> getGameStatsList()
    {
        return gameStatsList;
    }
}
