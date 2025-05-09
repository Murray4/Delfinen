import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingResult extends Result {

    // ATTRIBUTTER
    String comment;

    // METODER
    public String toString() {
        return  ""; // Du kan opdatere det senere
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
