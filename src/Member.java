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


    public Member(String memberName, Membership membership, boolean isActive, String email,
                  int phoneNumber, Boolean isCompetitionSwimmer, boolean hasPayed, boolean isSenior,
                  Map<Dicipline, TrainingResult> trainingResult, ArrayList<CompetitionResult> competitionResult,
                  int memberPrice, int memberID) {
        this.memberName = memberName;
        this.membership = membership;
        this.isActive = isActive;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isCompetitionSwimmer = isCompetitionSwimmer;
        this.hasPayed = hasPayed;
        this.isSenior = isSenior;
        this.trainingResult = trainingResult;
        this.competitionResult = competitionResult;
        this.memberPrice = memberPrice;
        this.memberID = memberID;
    }


    // METODER
    @Override
    public String toString() {
            return "Member{" +
                    "memberName='" + memberName + '\'' +
                    ", membership=" + membership +
                    ", isActive=" + isActive +
                    ", email='" + email + '\'' +
                    ", phoneNumber=" + phoneNumber +
                    ", isCompetitionSwimmer=" + isCompetitionSwimmer +
                    ", hasPayed=" + hasPayed +
                    ", isSenior=" + isSenior +
                    ", trainingResult=" + trainingResult +
                    ", competitionResult=" + competitionResult +
                    ", memberPrice=" + memberPrice +
                    ", memberID=" + memberID +
                    '}';
        }
    }

}
