import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result {

    // ATTRIBUTTER
    String newLine;
    String comment;
    Dicipline dicipline;
    Duration time;             // muligvis bare int
    LocalDate date;
    Member member;

    // METODER
    public String toString() {
        return "Tid:" + time + ", Dato: " + date + "\n";
    }

    public static TrainingResult createTrainingResult(String newLine, Dicipline discipline, Duration time, LocalDate date, String comment, Member member) {
        TrainingResult result = new TrainingResult();
        result.newLine = newLine;
        result.dicipline = discipline;
        result.time = time;
        result.date = date;
        result.comment = comment;
        result.member = member;
        return result;
    }
}
