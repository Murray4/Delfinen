import java.util.ArrayList;
import java.util.Map;

public class Member {

    // ATTRIBUTER
    String memberName;
    Membership membership;
    boolean isActive;
    String email;
    int phoneNumber;
    Boolean isCompetitionSwimmer;
    boolean hasPayed;
    boolean isSenior;
    Map<Dicipline, TrainingResult> trainingResult;
    ArrayList<CompetitionResult> competitionResult;
    int memberPrice;
    int memberID;


    // METODER
    public String toString() {
        return "TEST";
    }

}
