import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CompetitionManager {
    // ATTRIBUTTER
    public static ArrayList<Competition> competitions = new ArrayList<>();

    //Getter

    public static List<Competition> getCompetitions() {
        return competitions; // competitions skal være en statisk liste i klassen
    }

    public static void setCompetitions(List<Competition> newCompetitions) {
        competitions = (ArrayList<Competition>) newCompetitions;
    }

    // METODER
    public static void createCompetition(Scanner scanner) {
        DateTimeFormatter DKformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        Competition competition = new Competition();

        System.out.println("Konkurrencenavn: ");
        String name = scanner.nextLine();
        competition.setName(name);

        System.out.println("By: ");
        String city = scanner.nextLine();
        competition.setCity(city);

        System.out.println("Dato for stævne (dd-MM-yyyy): ");
        String dateInput = scanner.nextLine();
        LocalDate date = LocalDate.parse(dateInput, DKformat);
        competition.setDate(date);

        // Call the regular createCompetition method
        competitions.add(competition);
        System.out.println(Farver.GREEN + "Konkurrence oprettet: \n" + Farver.RESET + competition);
    }

    public static void addCompetition(Competition competition) {
        competitions.add(competition);
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
        System.out.println(Farver.GOLD + "\nKonkurrencer: " + Farver.RESET); //displaying the competitions

        // Henter konkurrencer fra listen i CompetitionManager
        List<Competition> competitions = CompetitionManager.getCompetitions();

        // checking if there are competitions in the list
        if (competitions == null || competitions.isEmpty()) {
            System.out.println("Ingen konkurrencer at vise");
            return;
        }

        // now we loop through each competition in the list
        for (int i = 0; i < competitions.size(); i++) {
            Competition c = competitions.get(i);

            // Udskriver konkurrenceinfo med formatering
            System.out.println("=".repeat(90));
            System.out.printf("Stævne: %-31s Sted: %-15s Dato: %s\n",
                    c.getName(),
                    c.getCity(),
                    c.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            System.out.println(Farver.GOLD + "-".repeat(90) + Farver.RESET);
            System.out.printf("%-7s %-20s %-10s %-21s %-10s\n", "Plads", "Navn", "ID", "Disciplin", "Tid");
            System.out.println("-".repeat(90));

            // Henter resultater for konkurrencen
            List<CompetitionResult> results = c.getResults();
            if (results != null && !results.isEmpty()) {
                // printer hver svømmers resultat
                for (CompetitionResult result : results) {
                    System.out.printf("%-7d %-20s %-10d %-21s %-10s\n",
                            result.getRank(),
                            result.getSwimmer().getMemberName(),
                            result.getSwimmer().getMemberID(),
                            result.getDicipline().toString(),
                            FileHandler.formatDuration(result.getTime()));
                }
            } else {
                System.out.println("Ingen resultater.");
            }

            System.out.println(Farver.GOLD + "=".repeat(90) + Farver.RESET);
            System.out.println(); // ekstra linje mellem konkurrencer
        }
    }







}