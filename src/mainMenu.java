import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
        mainMenu();
    }

    public static void mainMenu() {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("=== Hovedmenu ===");
            System.out.println("1. Medlemsmenu");
            System.out.println("2. Økonomimenu");
            System.out.println("3. Trænermenu");
            System.out.println("0. Afslut");
            System.out.print("Vælg en mulighed: ");

            choice = input.nextInt();

            switch (choice) {
                case 1:
                    memberMenu();
                    break;
                case 2:
                    economyMenu();
                    break;
                case 3:
                    trainerMenu();
                    break;
                case 0:
                    System.out.println("Afslutter programmet...");
                    break;
                default:
                    System.out.println("Ugyldigt valg. Prøv igen.");
            }

        } while (choice != 0);

        input.close();
    }

    public static void memberMenu() {
        System.out.println("--- Medlemsmenu ---");

    public static void economyMenu() {
        System.out.println("--- Økonomimenu ---");
    }

    public static void trainerMenu() {
        System.out.println("--- Trænermenu ---");
    }
}
