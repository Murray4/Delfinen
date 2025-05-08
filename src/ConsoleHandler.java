import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
        FileHandler.createFile("MedlemsListe.txt");
        Scanner scanner = new Scanner(System.in);
        run(scanner);
        int ID = 0;

    }

    // METODER (- main)
    public static void run(Scanner scanner) {
        mainMenu(scanner);
    }

    public static void mainMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("=== Hovedmenu ===");
            System.out.println("1. Medlemsmenu");
            System.out.println("2. Økonomimenu");
            System.out.println("3. Trænermenu");
            System.out.println("0. Afslut");
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
                    System.out.println(Farver.RED + "Afslutter programmet..." + Farver.RESET);
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

        } while (choice != 0);

        scanner.close();
    }

    public static void economyMenu(Scanner scanner) {

        System.out.println(economyMenuText());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Economy.printOutstandingFeesReport();
                break;
            case 2:
                Economy.calculateExpectedYearlyIncome();
                break;
            case 3:
                Economy.registerPayment();
                break;
            case 0:
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                economyMenu(scanner);
        }
    }

    public static String economyMenuText() {
        return """
                1. Se klubbens udestående.
                2. Se forventet indkomst.
                3. Registrer betaling.
                0. Tilbage.
                """;
    }

    public static void trainerMenu(Scanner scanner) {

        System.out.println(trainerMenuText());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Economy.printOutstandingFeesReport();
                break;
            case 2:
                Economy.calculateExpectedYearlyIncome();
                break;
            case 0:
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                trainerMenu(scanner);
        }
    }

    public static String trainerMenuText() {
        return "=== Træner Menu ===" + """
                1. Konkurrencesvømmere
                2. Konkurrencer
                0. Tilbage
                """;
    }

    public static void competitionMenu(Scanner scanner) {

        System.out.println(competitionMenuText());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                CompetitionManager.showCompetition();
                break;
            case 2:
                CompetitionManager.createCompetition();
                break;
            case 3:
                CompetitionManager.editCompetition();
                break;
            case 0:
                trainerMenu(scanner);
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
        }

    }

    public static String competitionMenuText() {
        return "===Konkurrence Menu===" + """
                1. Vis konkurrencer.
                2. Tilføj konkurrence.
                3. Rediger konkurrence.
                0. Tilbage.
                """;
    }

    public static void competitiveSwimmerMenu(Scanner scanner) {

        System.out.println(competitionSwimmerMenuText());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                CompetitionResult.top5();
                break;
            case 2:
                CompetitionSwimmer.getResultsByDicipline();
                break;
            case 3:
                CompetitionSwimmer.registerResult();
            case 0:
                trainerMenu(scanner);
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                break;

        }

    }

    public static String competitionSwimmerMenuText() {
        return "===Konkurrencesvømmer Menu===" + """
                1. Top 5.
                2. Vis resultat for disciplin.
                3. Tilføj Resultat.
                0. Tilbage.
                """;
    }


    public static void memberMenu(Scanner scanner) {
        System.out.println(memberMenuTekst());
        int choice = scanner.nextInt();
        boolean y = true;

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
            case 0:
                y = false;
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                memberMenu(scanner);


        }
    }

    public static String memberMenuTekst() {
        return Farver.CYAN + "=== Member Menu ===" + Farver.RESET + "\n" +
                """
                          1. Registrer Nyt Medlem
                          2. Rediger Medlem
                          3. Søg På Medlem
                          0. Tilbage
                        """;
    }
}


