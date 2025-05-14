import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CompetitionResult extends Result {

    // ATTRIBUTTER
    Competition competition;
    Member swimmer;
    ArrayList<Result> result;
    int rank;

    public Dicipline dicipline;
    public String eventName;
    public Duration time;

    // METODER
    CompetitionResult() {}

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }

    public void setSwimmer(Member swimmer) {
        this.swimmer = swimmer;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setTime(Duration time) {
        this.time = time;
    }
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public ArrayList<Result> getResult() {
        return result;
    }
    public Duration getTime() {
        return time;
    }
    public Member getSwimmer() {
        return swimmer;
    }
    public int getRank() {
        return rank;
    }

    public String toString() {
        return "Medlem: " + swimmer.getMemberName() + "      ID: " + swimmer.getMemberID() + "      " +
                "Rank: " + rank + "      " + "Disciplin: " + dicipline + "      Tid: " + FileHandler.formatDuration(time) + "\n";
    }
}
