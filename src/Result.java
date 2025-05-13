import java.time.LocalDate;
import java.time.LocalTime;

public class Result {

    // ATTRIBUTTER
    private Dicipline dicipline;
    LocalTime time;             // muligvis bare int
    LocalDate date;
    Member member;

    // METODER
    public String toString() {
        return ", Tid: " + time + ", Dato: " + date;
    }

    public Dicipline getDicipline() {
        return dicipline;
    }

    public void setDicipline(Dicipline dicipline) {
        this.dicipline = dicipline;
    }
}
