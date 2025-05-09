import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class MemberController {

    // ATTRIBUTTER
    public static ArrayList<Member> MemberList = new ArrayList<>();


    // METODER
    public static void searchByFilter(Scanner scanner) {
    }

    public static void registerNewMember(Scanner scanner) {
        System.out.println(Farver.MAGENTA + "Register New Member" + Farver.RESET);
        Member x = new Member();
        scanner.nextLine();

        // Navn
        String navn = "";
        while(true) {
            System.out.println("Navn: ");
            navn = scanner.nextLine().trim();
            if (navn.matches("[A-Za-zÆØÅæøå ]+")) {     // "[A-Za-zÆØÅæøå ]+" "regex" <-- tjekker at der kun er bogstaver og mellemrum
                break;
            } else {
                ConsoleHandler.inputFejl("navn", "Navn må kun indeholde bogstaver");
            }
        }
        x.setMemberName(navn);

        // Fødselsdato
        LocalDate fødselsdato = null;
        while (fødselsdato == null) {
            System.out.println("Fødselsdato: (dd-MM-yyyy)");
            String føds = scanner.nextLine();
            try {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");       // Standard format for LocalDate er (yyyy-MM-dd) dette ændrer formattet til (dd-MM-yyyy)
                fødselsdato = LocalDate.parse(føds, format);
            } catch (DateTimeParseException e) {
                ConsoleHandler.inputFejl("fødselsdato", "Forkert format! (dd-MM-yyyy)");
            }
        }
        x.setBirthDate(fødselsdato);

        // Alders_udregning
        LocalDate iDag = LocalDate.now();
        Period periode = Period.between(fødselsdato, iDag);
        int alder = periode.getYears();

        // Email
        String email = "";
        while (true) {
            System.out.println("email: ");
            email = scanner.nextLine().trim();
            if (email.matches(".+@.+\\..+")) {          // ".+@.+\\..+" "regex" <-- tjekker at der er . @ og at der er tekst før og efter @ og efter punktum
                break;
            } else {
                ConsoleHandler.inputFejl("Email", "Forkert email format");
            }
        }
        x.setEmail(email);

        // TelefonNummer
        String telefonnummer = "";
        while (true) {
            System.out.println("Telefonnummer: ");
            telefonnummer = scanner.nextLine();
            if (telefonnummer.matches("\\d{8}")) {     // "\\d{8}" "regex" <-- tjekker at det kun er cifre og der skal være 8 af dem
                break;
            } else {
                ConsoleHandler.inputFejl("telefonnummer", "Telefonnummer må kun indeholde tal");
            }
        }
        x.setPhoneNumber(telefonnummer);

        // Aktiv vs. Passiv
        while (true) {
            System.out.println("Er medlem aktiv eller passiv? (A/P): ");
            String aktiv = scanner.nextLine();
            if (aktiv.equalsIgnoreCase("A")) {
                x.setIsActive(true);
                break;
            } else if (aktiv.equalsIgnoreCase("P")) {
                x.setIsActive(false);
                break;
            } else {
                ConsoleHandler.inputFejl("input", "Skriv A eller P");
            }
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
            x.setMemberPrice((int)(1600 * 0.75));
        }

        // isCompetitive
        while (true) {
            System.out.println("Er medlem konkurrencesvømmer? (Y/N): ");
            String konksvøm = scanner.nextLine();
            if (konksvøm.equalsIgnoreCase("Y")) {
                x.setIsCompetitionSwimmer(true);
                break;
            } else if(konksvøm.equalsIgnoreCase("N")) {
                x.setIsCompetitionSwimmer(false);
                break;
            } else {
                ConsoleHandler.inputFejl("input", "Skriv Y eller N");
            }
        }

        // hasPayed
        while (true) {
            System.out.println("Betaler medlemmet nu? (Y/N): ");
            String betaling = scanner.nextLine();
            if (betaling.equalsIgnoreCase("Y")) {
                x.setHasPayed(true);
                break;
            } else if (betaling.equalsIgnoreCase("N")){
                x.setHasPayed(false);
            } else {
                ConsoleHandler.inputFejl("input", "Skriv Y eller N");
            }
        }

        x.setMemberID(FileHandler.readFileForID("MedlemsListe.txt"));

        FileHandler.writeToFile(x.toString(), "MedlemsListe.txt");
        MemberList.add(x);
        System.out.println(Farver.GREEN + "\nNyt Medlem oprettet:\n" + Farver.RESET + x);

    }

    public static void cancelMembershio() {
    }

    public static void showListOfCompetetiveSwimmers() {
    }

    public static void showTrainingResults() {
    }

    public static void addTrainingResults() {

    }

    public static void pauseMember() {
    }

    public static void editMember(Scanner scanner) {
    }

    public static void isCompetetive() {
    }

    public static void addMember() {
    }


}
