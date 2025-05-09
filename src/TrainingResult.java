import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result {

    // ATTRIBUTTER
    String comment;
    Dicipline dicipline;
    LocalTime time;             // muligvis bare int
    LocalDate date;
    Member member;

    // METODER
    public String toString() {
        return ", Tid:" + time + ", Dato: " + date;
    }

    public static TrainingResult createTrainingResult(Dicipline discipline, LocalTime time, LocalDate date, String comment, Member member) {
        TrainingResult result = new TrainingResult();
        result.dicipline = discipline;
        result.time = time;
        result.date = date;
        result.comment = comment;
        result.member = member;
        return result;
    }
}
