import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberController {

    // ATTRIBUTTER
    ArrayList<Member> MemberList;


    // METODER
    public static void searchByFilter(Scanner scanner) {}

    public static void registerNewMember(Scanner scanner) {
        System.out.println(Farver.MAGENTA + "Register New Member" + Farver.RESET);
        Member x = new Member();
        scanner.nextLine();

        System.out.println("Navn: ");
        String navn = scanner.nextLine();
        x.setMemberName(navn);

        System.out.println("Fødselsdato: (dd-MM-yyyy");
        String føds = scanner.nextLine();

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate fødselsdato = LocalDate.parse(føds, format);
        x.setBirthDate(fødselsdato);

        System.out.println("Email: ");
        String email = scanner.nextLine();
        x.setEmail(email);

        System.out.println("Telefonnummer: ");
        String telefonnummer = scanner.nextLine();
        x.setPhoneNumber(telefonnummer);

        // MEMBERSHIP // Skal udregnes ud fra alder

        System.out.println("Er medlem aktiv eller passiv? (A/P): ");
        String aktiv = scanner.nextLine();
        if (aktiv.equalsIgnoreCase("A")) {
            x.setIsActive(true);
        } else {
            x.setIsActive(false);
        }

        System.out.println("Er medlem konkurrencesvømmer? (Y/N): ");
        String konksvøm = scanner.nextLine();
        if (konksvøm.equalsIgnoreCase("Y")) {
            x.setIsCompetitionSwimmer(true);
        } else {
            x.setIsCompetitionSwimmer(false);
        }

        System.out.println("Betaler medlemmet nu? (Y/N): ");
        String betaling = scanner.nextLine();
        if (betaling.equalsIgnoreCase("Y")) {
            x.setHasPayed(true);
        } else {
            x.setHasPayed(false);
        }

        // IS_SENIOR // Skal vurderes ud fra alder

        // MEMBERPRICE // Skal udregnes ud fra alder

        System.out.println(Farver.GREEN + "\nNyt Medlem oprettet:\n" + Farver.RESET + x);

    }

    public static void cancelMembershio() {}

    public static void showListOfCompetetiveSwimmers() {}

    public static void showTrainingResults() {}

    public static void addTrainingResults() {}

    public static void pauseMember() {}

    public static void editMember(Scanner scanner) {}

    public static void isCompetetive() {}

    public static void addMember() {}


}
