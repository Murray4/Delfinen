import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class CompetitionManager {
    // ATTRIBUTTER
    private ArrayList<Competition> competitions = new ArrayList<>();

    // METODER
    public void createCompetition(String name, String city, LocalDate date) {
        Competition competition = new Competition(name, city, date);
        competitions.add(competition);
        System.out.println("Competition created: " + competition);
    }


    public void createCompetition(Scanner scanner) {
        System.out.println("Enter competition name: ");
        String name = scanner.nextLine();

        System.out.println("Enter city: ");
        String city = scanner.nextLine();

        System.out.println("Enter date (YYYY-MM-DD): ");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput);

        // Call the regular createCompetition method
        createCompetition(name, city, date);
    }

    public void editCompetition() {
        // Logic to edit competition (to be implemented)
        System.out.println("Editing competition (not implemented yet)");
    }

    public void showCompetition() {
        System.out.println("All competitions:");
        for (Competition c : competitions) {
            System.out.println(c);
        }
    }
}