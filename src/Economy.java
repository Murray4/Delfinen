import java.time.format.DateTimeFormatter;
import java.util.*;

public class Economy {

    //METODER
    public static void calculateExpectedYearlyIncome() {
        int indkomst = 0;
        for (Member m : MemberController.MemberList) {
            indkomst += m.getMemberPrice();
        }
        System.out.println(Farver.GREEN + "Antal Medlemmer: " + MemberController.MemberList.size() + "\nDen forventede årlige indkomst fra medlemskaber er: " + "\n" + indkomst + " kr" + Farver.RESET);
    }

    public static void registerPayment(Scanner scanner) {
        DateTimeFormatter DKformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");       // Standard format for LocalDate er (yyyy-MM-dd) dette ændrer formattet til (dd-MM-yyyy)

        System.out.println("Indtast medlems ID, på det medlem du vil finde: \n");
        int id = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;

        for (Member m : MemberController.MemberList) {
            if (m.getMemberID() == id) {
                System.out.println("\n" +
                        Farver.GREEN + "Fundet medlem: \n" + Farver.RESET +
                        "Navn:          " + m.getMemberName() + "\n" +
                        "Fødselsdag:    " + m.getBirthDate().format(DKformat));

                System.out.println("\nKorrekt medlem?" + Farver.GREEN + "(J/N): \n" + Farver.RESET);

                String answer = scanner.nextLine().trim();

                if (answer.equalsIgnoreCase("J")) {
                    System.out.println("Har personen betalt? (J/N)\n");
                    String Payed = scanner.nextLine().trim();

                    if (Payed.equalsIgnoreCase("J")) {
                        m.setHasPayed(true);
                        System.out.println(Farver.GREEN + "Betaling registreret." + Farver.RESET);
                    } else if (Payed.equalsIgnoreCase("N")) {
                        m.setHasPayed(false);
                        System.out.println(Farver.ORANGE + "Betaling registreret som IKKE BETALT." + Farver.RESET);
                    } else {
                        System.out.println(Farver.RED + "Ugyldigt svar. - Betaling blev ikke ændret." + Farver.RESET);
                    }

                    found = true;
                    break;
                } else {
                    System.out.println("Svar ugyldigt..");
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println(Farver.RED + "Ingen medlem funder med det indtastet ID" + Farver.RESET);
            ConsoleHandler.economyMenu(scanner);
        }
    }

    public static void printOutstandingFeesReport() {
        int udeståendeBeløb = 0;
        System.out.println("\n" + Farver.ORANGE + "Klubben udestående:\n" + Farver.RESET);

        for (Member m : MemberController.MemberList) {
            if (!m.getHasPayed()) {
                udeståendeBeløb += m.getMemberPrice();
                System.out.printf("ID: %-3d  %-20s  Mangler at betale: %6d Kr%n",
                        m.getMemberID(),
                        m.getMemberName(),
                        m.getMemberPrice()
                );

            } else {}
        }
        System.out.println(Farver.ORANGE + "Udestående i alt: " + Farver.RED + udeståendeBeløb + " Kr" + Farver.RESET);
    }

    //UNITTEST PÅ calculateExpectedYearlyIncome()

    // Da man ikke kan lave UNITTEST på en metode der ikke returnerer noget,
    // kopierer vi metoden og laver en der returner en integer.

    public static int getExpectedYearlyIncome() {
        int indkomst = 0;
        for (Member m : MemberController.MemberList) {
            indkomst += m.getMemberPrice();
        }
        return indkomst;
    }




}
