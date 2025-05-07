import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
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
                    economyMenu();
                    break;
                case 3:
                    trainerMenu();
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

    public static void economyMenu() {
    }

    public static void trainerMenu() {
    }


    public static void memberMenu(Scanner scanner) {
        System.out.println(memberMenuTekst());
        int choice = scanner.nextInt();
        boolean y = true;

        while (y) {
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
            }
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
