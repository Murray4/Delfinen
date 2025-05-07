import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberController {

    // ATTRIBUTTER
    ArrayList<Member> MemberList;


    // METODER
    public static void searchByFilter(Scanner scanner) {}

    public static void registerNewMember(Scanner scanner) {
        System.out.println(Farver.MAGENTA + "Register New Member" + Farver.RESET);

        System.out.println("Navn: ");
        String navn = scanner.nextLine();

        System.out.println("Fødselsdato: ");
        String føds = scanner.nextLine();
        LocalDate fødselsdato = LocalDate.parse(føds);

        System.out.println("Email: ");
        String email = scanner.nextLine();

        System.out.println("Telefonnummer: ");
        String telefonNummer = scanner.nextLine();


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
