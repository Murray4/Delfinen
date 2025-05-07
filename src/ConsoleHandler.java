import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {}
    Scanner scanner = new Scanner(System.in);

    // METODER (- main)
    public static void run() {}

    public static void mainMenu() {}

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
                mainMenu(scanner);
                break;
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

        }

    }

        public static String trainerMenuText() {
        return "===Træner Menu===" + """
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
                mainMenu(scanner);
                break;
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

    }



    public static void memberMenu(Scanner scanner) {
        System.out.println(memberMenuTekst());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:

        }
    }

    public static String memberMenuTekst() {
        return """
                1. Register Member
                2. Edit Member
                3. Search Member
                0. Tilbage.
                """;
    }

}
