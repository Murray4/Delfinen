import java.util.*;

public class CompetitionStatistic {
    Dicipline crawl = Dicipline.CRAWL;
    Dicipline butterfly = Dicipline.BUTTERFLY;
    Dicipline rygcrawl = Dicipline.RYGCRAWL;
    Dicipline bryst = Dicipline.BRYST;

    // METODER
    public static void getTopFive(Dicipline diciplineChoice) {

        //TODO: Top fem resultater i hver disciplin for konkurrencesvømmere
    }

    public static void getTopFiveTotal() {

        Map<Dicipline, List<TrainingResult>> resultaterPrDisciplin = new HashMap<>();

        // Det her loop fylder vores nye HashMAP ^^ op med resultaterne fra getTrainingResult
        for (Member m : MemberController.MemberList) {
            Map<Dicipline, TrainingResult> resultater = m.getTrainingResult();
            if (resultater != null) {
                for (TrainingResult tr : resultater.values()) {
                    Dicipline disciplin = tr.getDicipline();
                    resultaterPrDisciplin
                            .computeIfAbsent(disciplin, k -> new ArrayList<>())
                            .add(tr);
                }
            }
        }

        // Sorterer og viser os top 5 per disciplin
        for (Map.Entry<Dicipline, List<TrainingResult>> entry : resultaterPrDisciplin.entrySet()) {
            Dicipline disciplin = entry.getKey();
            List<TrainingResult> resultater = entry.getValue();

            // Sortér på tid
            resultater.sort(Comparator.comparing(TrainingResult::getTid));

            System.out.println("\t" + Farver.CYAN + "Top 5 for disciplin: " + disciplin + Farver.RESET);
            for (int i = 0; i < Math.min(5, resultater.size()); i++) {
                TrainingResult tr = resultater.get(i);
                System.out.println("  " + (i + 1) + ". " + tr.getMember().getMemberName().trim() + "\t\t ID: " + tr.getMember().getMemberID() +
                        " - Tid: " + FileHandler.formatDuration(tr.getTid()) +
                        " - Dato: " + tr.getDato());
            }
            System.out.println();
        }
        }

    public static void getResultsForCompetitionSwimmer(Scanner scanner) {

        //TODO: Top fem resultater for hver Disciplin for alle svømmere

    }
}
