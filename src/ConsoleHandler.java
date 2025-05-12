import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
        FileHandler.createFile("MedlemsListe.txt");
        FileHandler.indlæsMedlemmerFraFil("MedlemsListe.txt");
        System.out.print(MemberController.MemberList);
        Scanner scanner = new Scanner(System.in);
        run(scanner);

    }

    // METODER (- main)
    public static void run(Scanner scanner) {
        mainMenu(scanner);
    }

    public static void mainMenu(Scanner scanner) {
        int choice;

        do {
            System.out.println("\n=== Hovedmenu ===");
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
                CompetitionStatistic.getResultsForCompetitionSwimmer(scanner);
                break;
            case 2:
                Dicipline diciplineChoice = askForDicipline(scanner);
                CompetitionStatistic.getTopFive(diciplineChoice);
                break;
            case 3:
                competitionMenu(scanner);
                break;
            case 4:
                MemberController.addTrainingResults(scanner);
                break;
            case 0:
                break;

            default:
                System.out.println("Ugyldigt valg. Prøv igen.");
                trainerMenu(scanner);
        }
    }

    public static String trainerMenuText() {
        return "=== Træner Menu ===\n" + """
                1. Konkurrencesvømmere
                2. Top5
                3. Konkurrencer
                4. Tilføj træningsresultat
                0. Tilbage
                """;
    }

    public static Dicipline askForDicipline(Scanner scanner) {
        System.out.println("Vælg en disciplin:");
        Dicipline[] discipliner = Dicipline.values();

        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }

        System.out.print("Indtast nummer: ");
        int choice  = scanner.nextInt();
        scanner.nextLine();

        if (choice  > 0 && choice  <= discipliner.length) {
            return discipliner[choice - 1];
        } else {
            System.out.println("Ugyldigt valg. Prøv igen.\n");
            return askForDicipline(scanner);
        }
    }

 public static void competitionMenu(Scanner scanner) {

        System.out.println(competitionMenuText());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                CompetitionManager.showCompetition();
                break;
            case 2:
                CompetitionManager.createCompetition(scanner);
                break;
            case 3:
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
        return "===Konkurrence Menu===" + """
                1. Vis konkurrencer.
                2. Tilføj konkurrence.
                3. Rediger konkurrence.
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
                mainMenu(scanner);
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

    public static void inputFejl(String enhed, String forklaring) {
        System.out.println(Farver.RED + "Ugyldig " + enhed + " prøv igen. " + forklaring + Farver.RESET);
    }
}


