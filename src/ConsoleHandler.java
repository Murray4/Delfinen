import java.util.Scanner;

public class ConsoleHandler {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        memberMenu(scanner);
    }
    // METODER (- main)
    public static void run() {}

    public static void mainMenu() {}

    public static void economyMenu() {}

    public static void trainerMenu() {}


    public static void memberMenu(Scanner scanner) {
        System.out.println(memberMenuTekst());

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                MemberController.registerNewMember(scanner);
                break;
            case 2:
                break;
            case 3:
                break;
        }
    }

    public static String memberMenuTekst() {
        return Farver.MAGENTA + "Member Menu" + Farver.RESET + "\n" +
                """
                1. Register Member
                2. Edit Member
                3. Search Member
                """;
    }

}
