import java.util.*;

public class CompetitionStatistic {

    // METODER
    public static void getResultsByDiscipline(Scanner scanner) {
        System.out.println("Vælg disciplin:");
        Dicipline[] discipliner = Dicipline.values();
        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }

        int valg = scanner.nextInt();
        scanner.nextLine();
        Dicipline valgt = discipliner[valg - 1];

        System.out.println("\nResultater for disciplin: " + valgt);
        System.out.printf("  %-3s %-20s %-6s %-10s %-12s\n", "#", "Navn", "ID", "Tid", "Dato");
        System.out.println("  -------------------------------------------------------");

        int placering = 1;
        boolean found = false;

        for (Member m : MemberController.getMemberList()) {
            if (m.getIsCompetitionSwimmer()) {
                Map<Dicipline, TrainingResult> resultater = m.getTrainingResult();
                if (resultater != null && resultater.containsKey(valgt)) {
                    TrainingResult r = resultater.get(valgt);
                    System.out.printf("  %-3d %-20s %-6d %-10s %-12s\n",
                            placering++,
                            m.getMemberName(),
                            m.getMemberID(),
                            FileHandler.formatDuration(r.getTid()),
                            r.getDato());
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Ingen træningsresultater fundet for denne disciplin.");
        }
    }

    public static void getTopFiveTotal() {
        // Top 5 træningsresultater for alle svømmere

        Map<Dicipline, List<TrainingResult>> resultaterPrDisciplin = new HashMap<>();

        // Det her loop fylder vores nye HashMAP ^^ op med resultaterne fra getTrainingResult
        for (Member m : MemberController.MemberList) {
            if (m.getIsActive()) {
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
        }


        // Sorterer og viser os top 5 per disciplin
        for (
                Map.Entry<Dicipline, List<TrainingResult>> entry : resultaterPrDisciplin.entrySet()) {
            Dicipline disciplin = entry.getKey();
            List<TrainingResult> resultater = entry.getValue();

            // Sortér på tid
            resultater.sort(Comparator.comparing(TrainingResult::getTid));

            System.out.println(Farver.GOLD + "\nTop 5 for disciplin: " + disciplin + Farver.RESET);

            System.out.printf("  %-3s %-20s %-6s %-20s%n", "#", "Navn", "ID", "Tid");
            System.out.println("  " + "-".repeat(45)); // Justér længde efter behov

            for (int i = 0; i < Math.min(5, resultater.size()); i++) {
                TrainingResult tr = resultater.get(i);
                String navn = tr.getMember().getMemberName().trim();
                String id = String.valueOf(tr.getMember().getMemberID());
                String tid = FileHandler.formatDuration(tr.getTid()); // Justér denne linje efter dit medlemskabsfelt

                System.out.printf("  %-3d %-20s %-6s %-20s%n", (i + 1), navn, id, tid);
            }
        }
    }

    public static void getResultsForCompetitionSwimmer(Scanner scanner) {
        //Top fem resultater for hver Disciplin for Konkurrencesvømmere

        Map<Dicipline, List<TrainingResult>> resultaterPrDisciplinForKonk = new HashMap<>();

        // Det her loop fylder vores nye HashMAP ^^ op med resultaterne fra getTrainingResult
        for (Member m : MemberController.MemberList) {
            if (m.getIsCompetitionSwimmer()) {
                Map<Dicipline, TrainingResult> resultater = m.getTrainingResult();
                if (resultater != null) {
                    for (TrainingResult tr : resultater.values()) {
                        Dicipline disciplin = tr.getDicipline();
                        resultaterPrDisciplinForKonk
                                .computeIfAbsent(disciplin, k -> new ArrayList<>())
                                .add(tr);
                    }
                }
            }
        }

        // Sorterer og viser os top 5 per disciplin
        for (Map.Entry<Dicipline, List<TrainingResult>> entry : resultaterPrDisciplinForKonk.entrySet()) {
            Dicipline disciplin = entry.getKey();
            List<TrainingResult> resultater = entry.getValue();

            // Sortér på tid
            resultater.sort(Comparator.comparing(TrainingResult::getTid));

            System.out.println(Farver.GOLD + "\nTop 5 for disciplin: " + disciplin + Farver.RESET);
            System.out.printf("  %-3s %-20s %-6s %-10s %-12s%n", "#", "Navn", "ID", "Tid", "Dato");
            System.out.println("  " + "-".repeat(55)); // Til pynt som adskillelse

            for (int i = 0; i < Math.min(5, resultater.size()); i++) {
                TrainingResult tr = resultater.get(i);
                String navn = tr.getMember().getMemberName().trim();
                String id = String.valueOf(tr.getMember().getMemberID());
                String tid = FileHandler.formatDuration(tr.getTid());
                String dato = String.valueOf(tr.getDato());

                System.out.printf("  %-3d %-20s %-6s %-10s %-12s%n", (i + 1), navn, id, tid, dato);
            }

            System.out.println();
        }
    }

}

