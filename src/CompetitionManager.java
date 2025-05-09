import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionManager {
    // ATTRIBUTTER
    private static ArrayList<Competition> competitions = new ArrayList<>();

    // METODER
    public static void createCompetition(Scanner scanner) {
        Competition competition = new Competition();

        System.out.println("Enter competition name: ");
        String name = scanner.nextLine();
        competition.setName(name);

        System.out.println("Enter city: ");
        String city = scanner.nextLine();
        competition.setCity(city);

        System.out.println("Enter date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput);
        competition.setDate(date);

        // Call the regular createCompetition method
        competitions.add(competition);
        System.out.println(Farver.GREEN + "Competition created: " + competition + Farver.RESET);
    }

    public static void editCompetition() {
        // Logic to edit competition (to be implemented)
        System.out.println("Editing competition (not implemented yet)");
    }

    public static void showCompetition() {
        System.out.println("All competitions:");
        for (Competition c : competitions) {
            System.out.println(c);
        }
    }
}