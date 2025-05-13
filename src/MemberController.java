import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;
import java.time.LocalTime;

public class MemberController {

    // ATTRIBUTTER
    public static ArrayList<Member> MemberList = new ArrayList<>();


    // METODER
    public static void searchByFilter(Scanner scanner) {
    }

    public static ArrayList<Member> getMemberList() {
        return MemberList;
    }

    public static void registerNewMember(Scanner scanner) {
        System.out.println(Farver.MAGENTA + "Register New Member" + Farver.RESET);
        Member x = new Member();
        scanner.nextLine();

        // Navn
        String navn = "";
        while (true) {
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
        x.setAlder(alder);

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
            System.out.println("Er medlem konkurrencesvømmer? (J/N): ");
            String konksvøm = scanner.nextLine();
            if (konksvøm.equalsIgnoreCase("J")) {
                x.setIsCompetitionSwimmer(true);
                break;
            } else if(konksvøm.equalsIgnoreCase("N")) {
                x.setIsCompetitionSwimmer(false);
                break;
            } else {
                ConsoleHandler.inputFejl("input", "Skriv J eller N");
            }
        }

        // hasPayed
        while (true) {
            System.out.println("Betaler medlemmet nu? (J/N): ");
            String betaling = scanner.nextLine();
            if (betaling.equalsIgnoreCase("J")) {
                x.setHasPayed(true);
                break;
            } else if (betaling.equalsIgnoreCase("N")){
                x.setHasPayed(false);
                break;
            } else {
                ConsoleHandler.inputFejl("input", "Skriv J eller N");
            }
        }

        x.setMemberID(FileHandler.readFileForID("MedlemsListe.txt"));


        MemberList.add(x);
        System.out.println(Farver.GREEN + "\nNyt Medlem oprettet:\n" + Farver.RESET + x);
        FileHandler.writeToFile("MedlemsListe.txt", MemberController.MemberList);
        ConsoleHandler.memberMenu(scanner);
    }

    public static void cancelMembershio() {
    }

    public static void showListOfCompetetiveSwimmers() {
    }

    public static void showTrainingResults(Scanner scanner) {
        System.out.print("Indtast medlems-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member valgt = null;
        for (Member m : MemberList) {
            if (m.getMemberID() == id) {
                valgt = m;
                break;
            }
        }

        if (valgt == null) {
            System.out.println("Medlem ikke fundet!");
            return;
        }

        Map<Dicipline, TrainingResult> resultater = valgt.getTrainingResult();
        if (resultater == null || resultater.isEmpty()) {
            System.out.println("Ingen træningsresultater fundet.");
            return;
        }

        System.out.println("Træningsresultater for " + valgt.getMemberName() + ":");
        for (Map.Entry<Dicipline, TrainingResult> entry : resultater.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void addTrainingResults(Scanner scanner) {

        System.out.print("Indtast medlems-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member valgt = null;for (Member m : MemberList) {
            if (m.getMemberID() == id) {
                valgt = m;
                break;
            }
        }


        if (valgt == null) {
            System.out.println("Medlem ikke fundet!");
            return;
        }

        System.out.println("Vælg disciplin:");
        Dicipline[] discipliner = Dicipline.values();
        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }
        int disciplinValg = scanner.nextInt();
        scanner.nextLine();
        Dicipline valgtDisciplin = discipliner[disciplinValg - 1];


        System.out.print("Tid (mm:ss.SSS): ");
        String tidInput = scanner.nextLine();

        // Split på punktum
        String[] parts = tidInput.split("\\.");
        String[] minSek = parts[0].split(":");

        long minutter = Long.parseLong(minSek[0]);
        long sekunder = Long.parseLong(minSek[1]);
        long millisekunder = Long.parseLong(parts[1]);

        Duration tid = Duration.ofMinutes(minutter)
                .plusSeconds(sekunder)
                .plusMillis(millisekunder);

        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");       // Standard format for LocalDate er (yyyy-MM-dd) dette ændrer formattet til (dd-MM-yyyy)
        System.out.print("Dato (dd-MM-yyyy): ");
        LocalDate dato = LocalDate.parse(scanner.nextLine(),format);

        System.out.print("Kommentar: ");
        String kommentar = scanner.nextLine();

        TrainingResult nytResultat = TrainingResult.createTrainingResult(valgtDisciplin, tid, dato, kommentar, valgt);
        valgt.getTrainingResult().put(valgtDisciplin, nytResultat);

        System.out.println("Træningsresultat tilføjet!");
    }


    public static void pauseMember(Member m, Scanner scanner) {

        System.out.print("\nSkal medlemmet være aktiv? (J/N): \n");

        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("J")) {

            m.setIsActive(true);
            System.out.println(Farver.GREEN + "Medlem er nu aktiv.\n" + Farver.RESET);
            ConsoleHandler.memberMenu(scanner);
        } else if (choice.equalsIgnoreCase("N")) {
            m.setIsActive(false);
            System.out.println(Farver.GREEN + "Medlem er nu passiv.\n" + Farver.RESET);
            ConsoleHandler.memberMenu(scanner);
        } else {
            ConsoleHandler.inputFejl("valg", "skriv J eller N\n");
            editMember(scanner);
        }
    }

    public static void editMember(Scanner scanner) {

        System.out.print("Indtast ID på medlemmet du vil redigere:\n ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member choiceMember = null;

        for (Member m : MemberList) {
            if (m.getMemberID() == id) {
                choiceMember = m;
                break;
            }
        }

        if (choiceMember == null) {
            System.out.println(Farver.RED + "Medlem med ID, " + id + ", blev ikke fundet." + Farver.RESET);
            return;
        }
        System.out.println("\nDu har valgt " + choiceMember.getMemberName());


        while (true) {
            System.out.println("\nHvad ønsker du at ændre?\n");
            System.out.println("1. Aktivt / Passivt medlemskab");
            System.out.println("2. Konkurrencesvømmer status");
            System.out.println("0. Tilbage");

            System.out.print("\nVælg en mulighed: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    pauseMember(choiceMember, scanner);
                    break;
                case "2":
                    isCompetetive(choiceMember, scanner);
                    break;
                case "0":
                    ConsoleHandler.memberMenu(scanner);
                    break;
                default:
                    ConsoleHandler.inputFejl("Valg", "Indtast 1, 2 eller 0\n");
                    editMember(scanner);
            }
        }
    }
    public static void registerCompetitionResult(Scanner scanner) {
        System.out.print("Indtast medlems-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Member valgt = null;
        for (Member m : MemberList) {
            if (m.getMemberID() == id) {
                valgt = m;
                break;
            }
        }

        if (valgt == null) {
            System.out.println("Medlem ikke fundet!");
            return;
        }

        System.out.print("Indtast stævnenavn: ");
        String staevne = scanner.nextLine();

        System.out.println("Vælg disciplin:");
        Dicipline[] discipliner = Dicipline.values();
        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }
        int disciplinValg = scanner.nextInt();
        scanner.nextLine();
        Dicipline valgtDisciplin = discipliner[disciplinValg - 1];

        System.out.print("Tid (mm:ss): ");
        String tidInput = scanner.nextLine();
        LocalTime tid = LocalTime.parse("00:" + tidInput); // fx "00:01:32" for 1 minut 32 sekunder

        System.out.print("Placering (1, 2, 3, ...): ");
        int placering = scanner.nextInt();
        scanner.nextLine();

        CompetitionResult result = new CompetitionResult();
        result.dicipline = valgtDisciplin;
        result.time = tid;
        result.rank = placering;
        result.member = valgt;
        result.eventName = staevne;

        valgt.getCompetitionResult().add(result);

        System.out.println(Farver.GREEN + "Konkurrenceresultat registreret!" + Farver.RESET);
    }


    public static void isCompetetive(Member m, Scanner scanner) {

        System.out.print("Er medlemmet konkurrencesvømmer? (J/N): \n");

        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("J")) {
            m.setIsCompetitionSwimmer(true);
            System.out.println(Farver.GREEN + "Medlem er nu konkurrencesvømmer.\n" + Farver.RESET);
            ConsoleHandler.memberMenu(scanner);
        } else if (choice.equalsIgnoreCase("N")) {
            m.setIsCompetitionSwimmer(false);
            System.out.println(Farver.ORANGE + "Medlem er nu IKKE konkurrencesvømmer.\n" + Farver.RESET);
            ConsoleHandler.memberMenu(scanner);
        } else {
            ConsoleHandler.inputFejl("valg", "Ugyldigt input – skriv J eller N\n");
            ConsoleHandler.memberMenu(scanner);
        }
    }


    public static void addMember() {
    }
}
