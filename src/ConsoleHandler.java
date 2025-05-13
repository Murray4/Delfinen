import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        run(scanner);
    }

    // METODER (- main)
    public static void run(Scanner scanner) {
        FileHandler.createFile("MedlemsListe.txt");
        FileHandler.indlæsMedlemmerFraFil("MedlemsListe.txt");
        delfinLogo();
        mainMenu(scanner);
    }

    public static void mainMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println(Farver.GREEN + "\n---------------------------------------\n" +
                    "=== Hovedmenu ===" + Farver.RESET);
            System.out.println("  1. Medlems-menu    \uD83D\uDC64");
            System.out.println("  2. Økonomi-menu    \uD83D\uDCB0");
            System.out.println("  3. Træner-menu     \uD83C\uDFCA");
            System.out.println("  0. Afslut          ❌");
            System.out.print("\nVælg en mulighed: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    memberMenu(scanner);
                    break;
                case 2:
                    economyMenu(scanner);
                    break;
                case 3:
                    trainerMenu(scanner);
                    break;
                case 0:
                    scanner.close();
                    FileHandler.writeToFile("MedlemsListe.txt", MemberController.MemberList);
                    System.out.println(Farver.GREEN + "MedlemsListe opdateret" + Farver.RESET);
                    System.out.println(Farver.RED + "Afslutter programmet..." + Farver.RESET);
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

        } while (choice != 0);

        scanner.close();
    }

    public static void economyMenu(Scanner scanner) {

        System.out.println(economyMenuText());
        System.out.print("Vælg en mulighed: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Economy.printOutstandingFeesReport();
                break;
            case 2:
                Economy.calculateExpectedYearlyIncome();
                break;
            case 3:
                Economy.registerPayment(scanner);
                break;
            case 0:
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                economyMenu(scanner);
        }
    }

    public static String economyMenuText() {
        return Farver.ORANGE + "\n---------------------------------------\n" +
                "=== Økonomi-menu ===" + Farver.RESET + "\n" + """
                  1. Se klubbens udestående.    🔴
                  2. Se forventet indkomst.     📈
                  3. Registrer betaling.        ✅
                  0. Tilbage.                   🔙
                """;
    }

    public static void trainerMenu(Scanner scanner) {

        System.out.println(trainerMenuText());
        System.out.print("Vælg en mulighed: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                CompetitionStatistic.getResultsForCompetitionSwimmer(scanner);
                break;
            case 2:
                CompetitionStatistic.getTopFiveTotal();
                break;
            case 3:
                competitionMenu(scanner);
                break;
            case 4:
                MemberController.addTrainingResults(scanner);
                break;
            case 5:
                MemberController.registerCompetitionResult(scanner);
                break;
            case 6:
                MemberController.showTrainingResults(scanner);
                break;
            case 7:
                CompetitionStatistic.getResultsByDiscipline(scanner);
                break;
            case 8:
                MemberController.showListOfCompetitionSwimmers(scanner);
                break;
            case 0:
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                trainerMenu(scanner);
        }
    }

    public static String trainerMenuText() {
        return Farver.GOLD + "\n---------------------------------------\n" +
                "=== Træner-menu ===\n" + Farver.RESET + """
          1. Top5 - Konkurrencesvømmere       🥇
          2. Top5 - Alle svømmere             🧢
          3. Konkurrencer                     🏆
          4. Tilføj træningsresultat          📋
          5. Registrér konkurrenceresultat    📝
          6. Vis træningsresultater           📊
          7. Vis resultater efter disciplin   🧭
          8. Vis alle konkurrencesvømmere     🏊
          0. Tilbage                          🔙
        """;
    }

    public static Dicipline askForDicipline(Scanner scanner) {
        System.out.println("Vælg en disciplin:");
        Dicipline[] discipliner = Dicipline.values();

        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }

        System.out.print("Indtast nummer: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= discipliner.length) {
            return discipliner[choice - 1];
        } else {
            System.out.println("Ugyldigt valg. Prøv igen.\n");
            return askForDicipline(scanner);
        }
    }


    // TODO: EditCompetition + CreateCompetition VIRKER IKKE!!
    public static void competitionMenu(Scanner scanner) {

        System.out.println(competitionMenuText());
        System.out.print("Vælg en mulighed: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                CompetitionManager.showCompetition();
                break;
            case 2:
                // TODO: VIRKER IKKE!!
                CompetitionManager.createCompetition(scanner);
                break;
            case 3:
                // TODO: VIRKER IKKE!!
                CompetitionManager.editCompetition(scanner);
                break;
            case 0:
                trainerMenu(scanner);
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
        }

    }

    public static String competitionMenuText() {
        return Farver.ORANGE + "\n---------------------------------------\n" +
                "===Konkurrence-menu===" + Farver.RESET + "\n" +
                """
                  1. Vis konkurrencer.      🗂️
                  2. Tilføj konkurrence.    ➕
                  3. Rediger konkurrence.   🏅
                  0. Tilbage.               🔙
                """;
    }

    public static void memberMenu(Scanner scanner) {
        System.out.println(memberMenuTekst());
        System.out.print("Vælg en mulighed: ");

        while (true) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    MemberController.registerNewMember(scanner);
                    break;
                case 2:
                    MemberController.editMember(scanner);
                    break;
                case 3:
                    MemberController.searchByFilter(scanner);
                    break;
                case 4:
                    for (Member m : MemberController.MemberList) {
                        System.out.println(m);}
                    memberMenu(scanner);
                    break;
                case 0:
                    mainMenu(scanner);
                    break;

                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
                    memberMenu(scanner);
            }
        }
    }

    public static String memberMenuTekst() {
        return Farver.CYAN + "\n---------------------------------------\n" +
                "=== Medlems-menu ===" + Farver.RESET + "\n" +
                """
                          1. Registrer Nyt Medlem   ➕
                          2. Rediger Medlem         ✏️
                          3. Søg På Medlem          🔍  
                          4. Vis Medlemsliste       📄
                          0. Tilbage                🔙
                        """;
    }

    public static void inputFejl(String enhed, String forklaring) {
        System.out.println(Farver.RED + "Ugyldig " + enhed + " prøv igen. " + forklaring + Farver.RESET);
    }

    public static void delfinLogo() {
        System.out.println(Farver.CYAN + """
                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    ~                                                                                   ~
                    ~                              _\\`.___              ___,"/_                         ~
                    ~                           ,'`,-__.-.``=._    _.=``,-.__-.`'.                      ~
                    ~                          /,--'-..,7-)/-`"    "'-\\(-7,..-'--.\\                     ~
                    ~                        ,"`.         '            `         ,'".                   ~
                    ~                                                                                   ~
                    ~                                                                                   ~
                    ~      _______   _______  __       _______  __  .__   __.  _______ .__   __.        ~
                    ~     |       \\ |   ____||  |     |   ____||  | |  \\ |  | |   ____||  \\ |  |        ~
                    ~     |  .--.  ||  |__   |  |     |  |__   |  | |   \\|  | |  |__   |   \\|  |        ~
                    ~     |  |  |  ||   __|  |  |     |   __|  |  | |  . `  | |   __|  |  . `  |        ~
                    ~     |  '--'  ||  |____ |  `----.|  |     |  | |  |\\   | |  |____ |  |\\   |        ~
                    ~     |_______/ |_______||_______||__|     |__| |__| \\__| |_______||__| \\__|        ~
                    ~                                                                                   ~
                    ~                                                                                   ~
                    ~                                                                                   ~
                    ~                                                                                   ~
                    ~                                                                                   ~
                    ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    """ + Farver.RESET);
    }
}


