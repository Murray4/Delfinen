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
    public LocalTime time;

    // METODER
    public String toString() {
        return "TEST";
    }
}
