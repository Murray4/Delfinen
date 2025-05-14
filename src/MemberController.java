import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.LocalTime;

public class MemberController {

    // ATTRIBUTTER
    public static ArrayList<Member> MemberList = new ArrayList<>();


    // METODER
    public static void searchByFilter(Scanner scanner) {

        int id = -1;
        String name = null;
        Membership memberShip = null;
        Boolean isActive = null;  //BOOLEAN med stort 'B' for at vi kan sætte den til Null
        List<Member> resultatListe = new ArrayList<>();   //ArrayList til at gemme vores resultat.


// input - ID

        while (true) {
            System.out.println(Farver.CYAN + "\nSøg på et ID " + Farver.RESET + " (eller tryk ENTER for at gå videre): \n");
            String inputID = scanner.nextLine().trim();

            if (inputID.isEmpty()) {
      // Hvis input er tomt, gemmes der ikke noget og vi går videre.
            } else {
                try {
                    id = Integer.parseInt(inputID);
                    System.out.println(Farver.GREEN + "Du indtastede " + id + "\n" + Farver.RESET);
                    break;
                } catch (NumberFormatException e) {
                    ConsoleHandler.inputFejl("input.", "Skal være et ID-nummer\n");
                }
            }

// input - navn
            System.out.println(Farver.CYAN + "Søg på et navn " + Farver.RESET + " (eller tryk ENTER for at gå videre): \n");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
        // Hvis input er tomt, gemmes der ikke noget og vi går videre.
            } else {
                System.out.println(Farver.GREEN + "Du indtastede " + name + ". \n" + Farver.RESET);
            }

// input is senior
            System.out.println(Farver.CYAN + "Vil du søge på seniorsvømmere?  J/N " + Farver.RESET + " (eller tryk ENTER for at gå videre): \n");
            String inputSenior = scanner.nextLine().trim();

            if (inputSenior.isEmpty()) {
        // Hvis input er tomt, gemmes der ikke noget og vi går videre.
            } else if (inputSenior.equalsIgnoreCase("J")) {
                memberShip = Membership.SENIORSVØMMER;
                System.out.println(Farver.GREEN + "Du søger på Senior-medlemmere\n" + Farver.RESET);
            } else if (inputSenior.equalsIgnoreCase("N")) {
                memberShip = Membership.UNGDOMSSVØMMER;
                System.out.println(Farver.GREEN + "Du søger på Junior-medlemmere\n" + Farver.RESET);
            } else {
                ConsoleHandler.inputFejl("input.", "Skal være et 'J' eller 'N' eller ENTER");
            }

// alder 60+ ?
            System.out.println(Farver.CYAN + "Vil du søge på medlemmere der er 60+ år?  J/N " + Farver.RESET + " (eller tryk ENTER for at gå videre): \n");
            String inputOver60 = scanner.nextLine().trim();

            if (inputOver60.isEmpty()) {

            } else if (inputOver60.equalsIgnoreCase("J")) {
                memberShip = Membership.SENIORSVØMMER_60_PLUS;
                System.out.println(Farver.GREEN + "Du søger på medlemmere over 60 år\n" + Farver.RESET);

            } else if (inputOver60.equalsIgnoreCase("N")) {
                System.out.println(Farver.GREEN + "Du søger på medlemmere under 60 år\n" + Farver.RESET);

            } else {
                ConsoleHandler.inputFejl("input.", "Skal være et 'J' eller 'N' eller ENTER");
            }

// Aktivt medlemsskab?
            System.out.println(Farver.CYAN + "Vil du søge på aktive medlemmere?  J/N" + Farver.RESET + "(eller tryk ENTER for at gå videre): \n");
            String inputIsActive = scanner.nextLine().trim();

            if (inputIsActive.isEmpty()) {

                break;
            } else if (inputIsActive.equalsIgnoreCase("J")) {
                isActive = true;
                System.out.println(Farver.GREEN + "Du søger på aktive medlemmere\n" + Farver.RESET);
                break;
            } else if (inputIsActive.equalsIgnoreCase("N")) {
                isActive = false;
                System.out.println(Farver.GREEN + "Du søger på passive medlemmere\n" + Farver.RESET);
                break;
            } else {
                ConsoleHandler.inputFejl("input.", "Skal være et 'J' eller 'N' eller ENTER");
            }

        }

  // Vi looper MemberListen igennem.
        for (Member m : MemberList) {
            if (id != -1 && m.getMemberID() != id) {   //Hvis ikke dette er tilfældet, så springer vi over.
                continue;
            }
            if (name != null && !m.getMemberName().toLowerCase().contains(name.toLowerCase())) {
                continue;
            }
            if (memberShip != null && memberShip != m.getMembership()) {
                continue;
            }
            if (isActive != null && m.getIsActive() != isActive) {
                continue;
            }

    // Vi tilføjer det fremsøgte til en arrayliste så vi kan printe den nummerisk.
            resultatListe.add(m);

        }


        System.out.println(Farver.CYAN + "\nListe over søgte medlemmer: \n" + Farver.RESET);
        System.out.printf("%-4s %-20s %-10s %-20s%n", "#", "Navn", "ID", "Medlemsskab\n" +
                Farver.CYAN + "------------------------------------------------------" + Farver.RESET);

        int i = 1;
        for (Member m : resultatListe) {
            System.out.printf("%-4d %-20s %-10d %-20s%n",
                    i,
                    m.getMemberName(),
                    m.getMemberID(),
                    m.getMembership().toString().replace("_", " "));
            i++;
        }

        ConsoleHandler.memberMenu(scanner);
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


    public static void cancelMembership(Member m, Scanner scanner) {

        System.out.println("\nVil du afmelde medlemmet? (J/N): \n");

        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("J")) {

            System.out.println("Er du helt sikker? Medlemmet slettes fra databasen: J/N\n");

            String choice2 = scanner.nextLine().trim();

            if (choice2.equalsIgnoreCase("J")) {

                System.out.println(Farver.RED + m.getMemberName() + " med ID: " + m.getMemberID() + ", er nu slettet" + Farver.RESET);
                MemberList.remove(m);

            } else if (choice2.equalsIgnoreCase("N")) {
                System.out.println(Farver.GREEN + m.getMemberName() + " med ID: " + m.getMemberID() + ", blev IKKE slettet");
                ConsoleHandler.memberMenu(scanner);
            } else {
                ConsoleHandler.inputFejl("valg", "Tast J eller N\n");
            }
        } else if (choice.equalsIgnoreCase("N")) {
            System.out.println(Farver.GREEN + m.getMemberName() + " med ID: " + m.getMemberID() + ", blev IKKE slettet");
            ConsoleHandler.memberMenu(scanner);
        } else {
            ConsoleHandler.inputFejl("valg", "Tast J eller N\n");
        }
        }


    public static void showListOfCompetitionSwimmers(Scanner scanner) {

        System.out.println(Farver.GOLD + "\nListe over konkurrencesvømmere:\n" + Farver.RESET);

        boolean found = false;

        for (Member m : MemberList) {
            if (m.isCompetitionSwimmer()) {
                found = true;
                System.out.printf("%-20s ID: %d%n", m.getMemberName(), m.getMemberID());
            }
        }

        if (!found) {
            System.out.println("\nDer er ingen konkurrencesvømmere tilføjet\n");
        }

        System.out.println("\n  1. Tilbage  🔙");

        int choice = scanner.nextInt();

        if (choice == 1) {
            ConsoleHandler.trainerMenu(scanner);
        } else {
            ConsoleHandler.inputFejl("valg", "Tast 1\n");
        }
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
            System.out.println("  1. Aktivt / Passivt medlemskab");
            System.out.println("  2. Konkurrencesvømmer status");
            System.out.println("  3. Slet medlem");
            System.out.println("  0. Tilbage");

            System.out.print("\nVælg en mulighed: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    pauseMember(choiceMember, scanner);
                    break;
                case "2":
                    isCompetetive(choiceMember, scanner);
                    break;
                case "3":
                    cancelMembership(choiceMember, scanner);
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

    public static void addCompetitionResult(Scanner scanner) {

        // Konkurrencesvømmer
        List<Member> competitiveMembers = new ArrayList<>();
        for (Member m : MemberList) {
            if (m.getIsCompetitionSwimmer()) {
                competitiveMembers.add(m);
            }
        }

        if (competitiveMembers.isEmpty()) {
            System.out.println("Ingen konkurrencesvømmere fundet.");
            return;
        }

        System.out.println("\nVælg en konkurrencesvømmer:");
        for (int i = 0; i < competitiveMembers.size(); i++) {
            System.out.printf("%d. %s (ID: %d)%n", i + 1, competitiveMembers.get(i).getMemberName(), competitiveMembers.get(i).getMemberID());
        }

        int swimmerChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        Member selectedSwimmer = competitiveMembers.get(swimmerChoice);
        CompetitionSwimmer swimmer = (CompetitionSwimmer) selectedSwimmer;

        // Vælg konkurrence
        List<Competition> competitions = CompetitionManager.getCompetitions();
        if (competitions.isEmpty()) {
            System.out.println("Ingen konkurrencer fundet.");
            return;
        }

        System.out.println("\nVælg konkurrence:");
        for (int i = 0; i < competitions.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, competitions.get(i).getName(), competitions.get(i).getCity());
        }

        int compChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        Competition competition = competitions.get(compChoice);

        // disciplin
        Dicipline[] discipliner = Dicipline.values();
        System.out.println("\nVælg disciplin:");
        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }
        int discChoice = scanner.nextInt() - 1;
        scanner.nextLine();
        Dicipline valgtDisciplin = discipliner[discChoice];

        // Tid
        System.out.print("Tid (sekunder.millisekunder, fx 52.34): ");
        double seconds = scanner.nextDouble();
        scanner.nextLine();
        int wholeSeconds = (int) seconds;
        int millis = (int) ((seconds - wholeSeconds) * 1000);
        LocalTime tid = LocalTime.of(0, 0, wholeSeconds, millis * 1_000_000);

        // Placering
        System.out.print("Placering: ");
        int rank = scanner.nextInt();
        scanner.nextLine();

        // Opret resultat
        CompetitionResult result = new CompetitionResult();
        result.dicipline = valgtDisciplin;
        result.time = tid;
        result.rank = rank;
        result.eventName = competition.getName();
        result.swimmer = selectedSwimmer;
        result.competition = competition;

        // Tilføj resultat via interface
        swimmer.registerCompetitionResult(result);
        System.out.println("Resultat tilføjet.");
    }


}
