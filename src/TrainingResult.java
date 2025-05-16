import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result {

    // ATTRIBUTTER
    String comment;
    Dicipline discipline;
    Duration time;
    LocalDate date;
    Member member;

    // METODER
    public String toString() {
        return "Tid:" + time + ", Dato: " + date;
    }

    public Duration getTid() {
        return time;
    }

    public LocalDate getDato() {
        return date;
    }

    public Member getMember() {
        return member;
    }

    public Dicipline getDiscipline() {
        return discipline;
    }

    public static TrainingResult createTrainingResult(Dicipline discipline, Duration time, LocalDate date, String comment, Member member) {
        TrainingResult result = new TrainingResult();
        result.discipline = discipline;
        result.time = time;
        result.date = date;
        result.comment = comment;
        result.member = member;
        return result;
    }
}
