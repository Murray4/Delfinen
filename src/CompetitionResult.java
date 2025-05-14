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

    public void setCompetition() {
        this.competition = competition;
    }

    public void setMember() {
        this.swimmer = swimmer;
    }

    public void setResult() {
        this.result = result;
    }

    public void setRank() {
        this.rank = rank;
    }

    public String toString() {
        return "Medlem: " + swimmer.getMemberName() + "ID: " + swimmer.getMemberID() +
                "Resultater: " + "\n" +
                result;
    }
}
