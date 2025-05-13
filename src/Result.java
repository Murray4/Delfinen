import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Result {

    // ATTRIBUTTER
    Dicipline dicipline;
    LocalTime time;             // muligvis bare int
    LocalDate date;
    Member member;

    // METODER
    public String toString() {
        return ", Tid: " + time + ", Dato: " + date;
    }
}
