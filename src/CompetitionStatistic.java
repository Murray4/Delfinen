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


    // TODO: TROUBLESHOOT!! --- VIRKER IKKE!
    public static void getResultsByDiscipline(Scanner scanner) {
        System.out.println("Vælg disciplin:");
        Dicipline[] discipliner = Dicipline.values();
        for (int i = 0; i < discipliner.length; i++) {
            System.out.println((i + 1) + ". " + discipliner[i]);
        }
        int valg = scanner.nextInt();
        scanner.nextLine();
        Dicipline valgt = discipliner[valg - 1];

        System.out.println("Resultater i disciplinen: " + valgt);
        boolean found = false;

        for (Member m : MemberController.getMemberList()) {
            for (CompetitionResult r : m.getCompetitionResult()) {
                if (r.dicipline == valgt) {
                    System.out.println("Navn: " + m.getMemberName() +
                            " | Tid: " + r.time +
                            " | Placering: " + r.rank +
                            " | Stævne: " + r.eventName);
                    found = true;
                }
            }
        }

        if (!found) {
            System.out.println("Ingen resultater fundet for denne disciplin.");
        }
    }

    public static void getTopFiveTotal() {

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
}

