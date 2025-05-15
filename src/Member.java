import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member implements CompetitionSwimmer {

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
    int alder;


    public Member() {
        this.trainingResult = new HashMap<>();
        this.competitionResult = new ArrayList<>();
    }

    public boolean isCompetitionSwimmer() {
        return isCompetitionSwimmer;
    }

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

    public boolean getIsSenior() {
        return isSenior;
    }

    public void setIsSenior(boolean senior) {
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

    public int getAlder() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void setAlder(int alder) {
         this.alder = alder;
    }

    @Override
    public String toString() {
        DateTimeFormatter DKformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");       // Standard format for LocalDate er (yyyy-MM-dd) dette ændrer formattet til (dd-MM-yyyy)

        return "\n" + "[ID = " + memberID + "] , [MedlemsNavn = " + memberName + "] , [Fødselsdato: " + birthDate.format(DKformat) + "]"+ ", [Alder: " + getAlder() + "]" + "\n" +
                "[Medlemskab = " + membership + "]" +
                ", [Aktiv = " + isActive + "]" +
                ", [Medlemspris = " + memberPrice + "]" +
                ", [Email = " + email + '\'' + "]" +
                ", [Telefonnummer = " + phoneNumber + "]" +
                ", [Betalt = " + hasPayed + "]" +
                ", [Senior = " + isSenior + "]" +
                ", [Konkurrencesvømmer = " + isCompetitionSwimmer + "]" + "\n" +
                "[Træningsresultater = " + trainingResult + "]" +
                "[Konkurrenceresultater = " + competitionResult + "]\n";
    }

    @Override
    public void getResultsByDicipline() {

    }

    @Override
    public void getDiciplines() {

    }

    @Override
    public void registerCompetitionResult(CompetitionResult result) {

    }

    @Override
    public void isSenior() {

    }

    @Override
    public void getName() {

    }
}
