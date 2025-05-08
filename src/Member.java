import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Member {

    // ATTRIBUTER
    String memberName;
    LocalDate birthDate;
    Membership membership;
    boolean isActive;
    String email;
    String phoneNumber;
    Boolean isCompetitionSwimmer;
    boolean hasPayed;
    boolean isSenior;
    Map<Dicipline, TrainingResult> trainingResult;
    ArrayList<CompetitionResult> competitionResult;
    int memberPrice;
    int memberID;


    public Member(String memberName, LocalDate birthDate, Membership membership, boolean isActive, String email,
                  String phoneNumber, Boolean isCompetitionSwimmer, boolean hasPayed, boolean isSenior,
                  Map<Dicipline, TrainingResult> trainingResult, ArrayList<CompetitionResult> competitionResult,
                  int memberPrice, int memberID) {

        this.memberName = memberName;
        this.birthDate = birthDate;
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

    public Member() {}


    // GETTERS OG SETTERS
    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getBirthDate()  {
        return birthDate;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean active) {
        isActive = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsCompetitionSwimmer() {
        return isCompetitionSwimmer;
    }

    public void setIsCompetitionSwimmer(Boolean isCompetitionSwimmer) {
        this.isCompetitionSwimmer = isCompetitionSwimmer;
    }

    public boolean getHasPayed() {
        return hasPayed;
    }

    public void setHasPayed(boolean hasPayed) {
        this.hasPayed = hasPayed;
    }

    public boolean isSenior() {
        return isSenior;
    }

    public void setSenior(boolean senior) {
        isSenior = senior;
    }

    public Map<Dicipline, TrainingResult> getTrainingResult() {
        return trainingResult;
    }

    public void setTrainingResult(Map<Dicipline, TrainingResult> trainingResult) {
        this.trainingResult = trainingResult;
    }

    public ArrayList<CompetitionResult> getCompetitionResult() {
        return competitionResult;
    }

    public void setCompetitionResult(ArrayList<CompetitionResult> competitionResult) {
        this.competitionResult = competitionResult;
    }

    public int getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(int memberPrice) {
        this.memberPrice = memberPrice;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
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
