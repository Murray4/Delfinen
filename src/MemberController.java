import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberController {

    // ATTRIBUTTER
    public static ArrayList<Member> MemberList = new ArrayList<>();


    // METODER
    public static void searchByFilter(Scanner scanner) {}

    public static void registerNewMember(Scanner scanner) {
        System.out.println(Farver.MAGENTA + "Register New Member" + Farver.RESET);
        Member x = new Member();
        scanner.nextLine();

        // Navn
        System.out.println("Navn: ");
        String navn = scanner.nextLine();
        x.setMemberName(navn);

        // Fødselsdato
        System.out.println("Fødselsdato: (dd-MM-yyyy");
        String føds = scanner.nextLine();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fødselsdato = LocalDate.parse(føds, format);
        x.setBirthDate(fødselsdato);

        // Alders_udregning
        LocalDate iDag = LocalDate.now();
        Period periode = Period.between(fødselsdato, iDag);
        int alder = periode.getYears();

        // Email
        System.out.println("Email: ");
        String email = scanner.nextLine();
        x.setEmail(email);

        // TelefonNummer
        System.out.println("Telefonnummer: ");
        String telefonnummer = scanner.nextLine();
        x.setPhoneNumber(telefonnummer);

        // Aktiv vs. Passiv
        System.out.println("Er medlem aktiv eller passiv? (A/P): ");
        String aktiv = scanner.nextLine();
        if (aktiv.equalsIgnoreCase("A")) {
            x.setIsActive(true);
        } else {
            x.setIsActive(false);
        }

        if (!x.getIsActive()) {
            x.setMemberPrice(500);
        } else if (alder < 18) {
            x.setMembership(Membership.UNGDOMSSVØMMER);
            x.setMemberPrice(1000);
        } else if (alder <= 60) {
            x.setMembership(Membership.SENIORSVØMMER);
            x.setMemberPrice(1600);
        } else {
            x.setMembership(Membership.SENIORSVØMMER_60_PLUS);
            x.setIsSenior(true);
            x.setMemberPrice((int)(1600*0.75));
        }

        // isCompetetive
        System.out.println("Er medlem konkurrencesvømmer? (Y/N): ");
        String konksvøm = scanner.nextLine();
        if (konksvøm.equalsIgnoreCase("Y")) {
            x.setIsCompetitionSwimmer(true);
        } else {
            x.setIsCompetitionSwimmer(false);
        }

        // hasPayed
        System.out.println("Betaler medlemmet nu? (Y/N): ");
        String betaling = scanner.nextLine();
        if (betaling.equalsIgnoreCase("Y")) {
            x.setHasPayed(true);
        } else {
            x.setHasPayed(false);
        }

        // TODO: IS_SENIOR // Skal vurderes ud fra alder

        // TODO: MEMBERPRICE // Skal udregnes ud fra alder

        // TODO: MemberID // ud fra fil
        x.setMemberID(FileHandler.readFileForID("MedlemsListe.txt"));

        FileHandler.writeToFile(x.toString(), "MedlemsListe.txt");
        MemberList.add(x);
        System.out.println(Farver.GREEN + "\nNyt Medlem oprettet:\n" + Farver.RESET + x);

    }

    public static void cancelMembershio() {}

    public static void showListOfCompetetiveSwimmers() {}

    public static void showTrainingResults() {}

    public static void addTrainingResults() {

    }

    public static void pauseMember() {}

    public static void editMember(Scanner scanner) {}

    public static void isCompetetive() {}

    public static void addMember() {}


}
