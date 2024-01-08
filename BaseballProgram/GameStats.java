import java.time.LocalDate;
import java.util.ArrayList; 
import java.util.List;

public class GameStats
{
    // create variables
    private final LocalDate date;
    private final List<Integer> atBatResults;
    private int runs;
    private int rbis;
    private int totalBases;
    private int atBats;
    private int hitByPitch;
    private int sacrificeFlies;

    //constructor to hold initial values
    public GameStats(LocalDate date)
    {
        this.date = date;
        this.atBatResults = new ArrayList<>();
        this.runs = 0;
        this.rbis = 0;
        this.totalBases = 0;
        this.atBats = 0;
        this.hitByPitch = 0;
        this.sacrificeFlies = 0;
    }

    // logic for picking at bat result
    public void addAtBatResult(int result, int rbi)
    {
        atBatResults.add(result);
        rbis += rbi;

        switch (result) {
            case 0: // Walk
                break;
            case 1: // Single
                totalBases += 1;
                atBats++;
                break;
            case 2: // Double
                totalBases += 2;
                atBats++;
                break;
            case 3: // Triple
                totalBases += 3;
                atBats++;
                break;
            case 4: // Home run
                totalBases += 4;
                atBats++;
                runs++;
                break;
            case 5: // Sacrifice Bunt
                sacrificeFlies++;
                break;
            case 6: // Strikeout
                atBats++;
                break;
            case 7: // Ground out
                atBats++;
                break;
            case 8: // Flyout
                atBats++;
                break;
            case 9: // Line Out
                atBats++;
                break;
            case 10: // Hit by pitch
                hitByPitch++;
                break;
            default:
                break;
        }
    }

    // increments the total number of runs
    public void addRun()
    {
        runs++;
    }

    // calculates batting average
    public double getBattingAverage()
    {
        return atBats > 0 ? (double) (int) atBatResults.stream().filter(result -> result >= 1 && result <= 4).count() / atBats : 0.0;
    }

    // calculates on base percentage
    public double getOnBasePercentage()
    {
        int plateAppearances = atBatResults.size() + hitByPitch + sacrificeFlies;
        return plateAppearances > 0 ? (double) (int) atBatResults.stream().filter(result -> result ==
                0 || (result >= 1 && result <= 4)).count() + (double) hitByPitch / plateAppearances : 0.0;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public List<Integer> getAtBatResults()
    {
        return new ArrayList<>(atBatResults); // Return a copy of the list to prevent external modification
    }

    public int getRuns()
    {
        return runs;
    }

    public int getRbis()
    {
        return rbis;
    }

    public void setRuns(int runs)
    {
        this.runs = runs;
    }

    public void setRbis(int rbis)
    {
        this.rbis = rbis;
    }

    public int getAtBats()
    {
        return atBats;
    }

    public int getHitByPitch()
    {
        return hitByPitch;
    }

    public int getSacrificeFlies()
    {
        return sacrificeFlies;
    }
}
