import java.time.LocalDate;
import java.util.*;

public class CompetitionManager {
    // ATTRIBUTTER
    private static ArrayList<Competition> competitions = new ArrayList<>();

    //Getter

    public static List<Competition> getCompetitions() {
        return competitions; // competitions skal være en statisk liste i klassen
    }


    // METODER
    public static void createCompetition(Scanner scanner) {
        Competition competition = new Competition();
        scanner.nextLine();

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


    public static void editCompetition(Scanner scanner) {
        // checking if the competition list is empty
        if (competitions.isEmpty()) {
            System.out.println("No competitions to edit.");
            return;
        }
// displaying the list of available competitions to edit
        System.out.println("Select a competition to edit: ");
        for (int i = 0; i < competitions.size(); i++) {
            System.out.println((i + 1) + ". " + competitions.get(i));
        }
//the user have to choose the competition by entering its index number
        System.out.println("Enter the number of the competition you want to edit:  ");
        int index;
        try {
            index = Integer.parseInt(scanner.nextLine()) - 1; // Convert user input to index
            if (index < 0 || index >= competitions.size()) {
                System.out.println("Invalid number. Try again!");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
        // getting the selected from the user competition
        Competition comp = competitions.get(index);

        // EDIT: add/change name
        System.out.println("Enter new name (leave blank to keep '" + comp.getName() + "'):");
        String name = scanner.nextLine();
        if (!name.isBlank()) {
            comp.setName(name);
        }

        //EDIT: add/change city
        System.out.println("Enter new city (leave blank to keep '" + comp.getCity() + "'):");
        String city = scanner.nextLine();
        if (!city.isBlank()) {
            comp.setCity(city);
        }

        //EDIT: add/change date
        System.out.println("Enter new date (YYYY-MM-DD, leave blank to keep '" + comp.getDate() + "'):");
        String dateInput = scanner.nextLine();
        if (!dateInput.isBlank()) {
            try {
                LocalDate date = LocalDate.parse(dateInput);
                comp.setDate(date);
            } catch (Exception e) {
                System.out.println("Invalid date format. Keeping original date.");
            }
        }
        //Update confirmation
        System.out.println(Farver.GREEN + "Competition updated: " + comp + Farver.RESET);
    }


    public static void showCompetition() {
            // Logic to edit competition (to be implemented)
            System.out.println("Active competitions: "); //displaying the competitions
            // checking if there are competitions in the list
            if (competitions.isEmpty()) {
                System.out.println("There are no available competitions!");
                return;
            }
            // now we loop through each competition in the list
            for (int i = 0; i< competitions.size(); i++) {
                //printing each competition with index number from 1.
                System.out.println((i+1) + "."+ competitions.get(i));
            }
    }



}