import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandler {

    //METODER
    public static void createFile(String fileName) {
        try {
            File fil = new File(fileName);
            if (fil.createNewFile()) {
                System.out.println("File created: " + fil.getName());
            } else {
                System.out.println(fileName + " already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static int readFileForID(String fileName) {
        Pattern pattern = Pattern.compile("\\[ID\\s*=\\s*(\\d+)]");
        int maxID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String linje;
            while ((linje = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(linje);
                if (matcher.find()) {
                    int ID = Integer.parseInt(matcher.group(1));
                    if (ID > maxID) {
                        maxID = ID;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int nextID = maxID + 1;
        return nextID;
    }

    public static void writeToMemberlistFile(String fileName, ArrayList<Member> medlemmer) {
        DateTimeFormatter DKformat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            for (Member medlem : medlemmer) {

                // Tjek for null-fødselsdato
                String fødselsdatoStr = (medlem.getBirthDate() != null)
                        ? medlem.getBirthDate().format(DKformat)
                        : "Ukendt";

                String alderStr = (medlem.getBirthDate() != null)
                        ? String.valueOf(medlem.getAlder())
                        : "Ukendt";

                // Sætter memberprice ud fra alderen, så hvis alderen opdateres, opdateres prisen også (i filen)
                if (medlem.getAlder() < 18 && medlem.getIsActive()) {
                    medlem.setMemberPrice(1000);
                } else if (medlem.getAlder() <= 60 && medlem.getIsActive()) {
                    medlem.setMemberPrice(1600);
                } else if (!medlem.getIsActive()) {
                    medlem.setMemberPrice(500);
                } else {
                    medlem.setIsSenior(true);
                    medlem.setMemberPrice((int) (1600 * 0.75));
                }

                writer.write("[ID = " + medlem.getMemberID() + "] , [MedlemsNavn = " + medlem.getMemberName() +
                        "], [Fødselsdato = " + fødselsdatoStr + "], [Alder = " + alderStr + "]\n");

                writer.write("[Medlemskab = " + medlem.getMembership() + "], [Aktiv = " + medlem.getIsActive() +
                        "], [Medlemspris = " + medlem.getMemberPrice() + "], [Email = " + medlem.getEmail() +
                        "], [Telefonnummer = " + medlem.getPhoneNumber() + "], [Betalt = " + medlem.getHasPayed() +
                        "], [Senior = " + medlem.getIsSenior() + "], [Konkurrencesvømmer = " + medlem.getIsCompetitionSwimmer() + "]\n");

                // Træningsresultater
                Map<Dicipline, TrainingResult> resultater = medlem.getTrainingResult();
                if (resultater != null && !resultater.isEmpty()) {
                    List<String> entries = new ArrayList<>();
                    for (Map.Entry<Dicipline, TrainingResult> entry : resultater.entrySet()) {
                        Dicipline disciplin = entry.getKey();
                        TrainingResult tr = entry.getValue();
                        String tidStr = formatDuration(tr.getTid());
                        entries.add("{" + disciplin.name() + "=, Tid:" + tidStr + ", Dato: " + tr.getDato() + "}");
                    }
                    writer.write("[Træningsresultater = " + String.join(", ", entries) + "]\n");
                } else {
                    writer.write("[Træningsresultater = []]\n");
                }

                writer.write("[Konkurrenceresultater = []]\n\n");
            }
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }

    public static void writeToCompetitionFile(String fileName, List<Competition> competitions) {
        if (competitions == null || competitions.isEmpty()) {
            System.out.println("Ingen konkurrencer at gemme – fil ikke overskrevet.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            for (Competition c : competitions) {
                String name = (c.getName() != null) ? c.getName() : "Ukendt";
                String city = (c.getCity() != null) ? c.getCity() : "Ukendt";
                String date = (c.getDate() != null) ? c.getDate().format(formatter) : "01-01-2000";

                writer.println("Konkurrencenavn: " + name + "    By: " + city + "    Dato: " + date);

                List<CompetitionResult> results = c.getResults();
                if (results == null || results.isEmpty()) {
                    writer.println("Ingen resultater.");
                } else {
                    for (CompetitionResult result : results) {
                        writer.println("Medlem: " + result.getSwimmer().getMemberName() +
                                "    ID: " + result.getSwimmer().getMemberID());
                        writer.println("Disciplin: " + result.getDicipline() +
                                "    Tid: " + formatDuration(result.getTime()) +
                                "    Placering: " + result.getRank());
                        writer.println();
                    }
                }

                writer.println(); // mellemrum mellem konkurrencer
            }

            System.out.println("Konkurrencer gemt i " + fileName);

        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }

    public static ArrayList<Member> indlæsMedlemmerFraFil(String fileName) {
        try {
            List<String> linjer = Files.readAllLines(Paths.get(fileName));
            Member medlem = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            for (String linje : linjer) {
                // Ny medlem starter
                if (linje.startsWith("[ID =")) {
                    if (medlem != null) MemberController.MemberList.add(medlem);
                    medlem = new Member();

                    // ID, navn, fødselsdato og alder (hvis inkluderet)
                    Pattern p = Pattern.compile("\\[ID = (\\d+)] , \\[MedlemsNavn = (.*?)\\](?:, \\[Fødselsdato = (\\d{2}-\\d{2}-\\d{4})])?(?:, \\[Alder = (\\d{1,3})])?");
                    Matcher m = p.matcher(linje);
                    if (m.find()) {
                        medlem.setMemberID(Integer.parseInt(m.group(1)));
                        medlem.setMemberName(m.group(2));

                        if (m.group(3) != null) {
                            LocalDate fødselsdato = LocalDate.parse(m.group(3), formatter);
                            medlem.setBirthDate(fødselsdato);
                            medlem.setAlder(Period.between(fødselsdato, LocalDate.now()).getYears());
                        }
                    }
                }

                // Attributter
                else if (linje.contains("[Medlemskab =")) {
                    medlem.setMembership(parseMembership(linje));
                    medlem.setIsActive(linje.contains("[Aktiv = true]"));
                    medlem.setMemberPrice(parseIntFromLine(linje, "Medlemspris"));
                    medlem.setEmail(parseStringFromLine(linje, "Email"));
                    medlem.setPhoneNumber(parseStringFromLine(linje, "Telefonnummer"));
                    medlem.setHasPayed(linje.contains("[Betalt = true]"));
                    medlem.setIsSenior(linje.contains("[Senior = true]"));
                    medlem.setIsCompetitionSwimmer(linje.contains("[Konkurrencesvømmer = true]"));
                }

                // Træningsresultater
                else if (linje.startsWith("[Træningsresultater =")) {
                    Pattern p = Pattern.compile("\\{(\\w+)=, Tid:(\\d{2}:\\d{2}\\.\\d{3}), Dato: (\\d{4}-\\d{2}-\\d{2})}");
                    Matcher m = p.matcher(linje);
                    Map<Dicipline, TrainingResult> resultMap = new HashMap<>();

                    while (m.find()) {
                        try {
                            Dicipline disciplin = Dicipline.valueOf(m.group(1).trim().toUpperCase());

                            String tidStr = m.group(2).trim();
                            String[] dele = tidStr.split("\\.");
                            String[] minSek = dele[0].split(":");

                            long minutter = Long.parseLong(minSek[0]);
                            long sekunder = Long.parseLong(minSek[1]);
                            long millisekunder = Long.parseLong(dele[1]);

                            Duration tid = Duration.ofMinutes(minutter)
                                    .plusSeconds(sekunder)
                                    .plusMillis(millisekunder);

                            LocalDate dato = LocalDate.parse(m.group(3).trim());
                            String kommentar = "";

                            TrainingResult tr = TrainingResult.createTrainingResult(disciplin, tid, dato, kommentar, medlem);
                            resultMap.put(disciplin, tr);
                        } catch (Exception e) {
                            System.out.println("Kunne ikke parse træningsresultat: " + linje);
                        }
                    }

                    medlem.setTrainingResult(resultMap);
                }

                // Konkurrenceresultater kan tilføjes her senere
            }

            if (medlem != null) MemberController.MemberList.add(medlem);

        } catch (IOException e) {
            System.out.println("Fejl ved læsning: " + e.getMessage());
        }

        return MemberController.MemberList;
    }

    public static ArrayList<Competition> indlæsKonkurrencerFraFil(String fileName) {
        ArrayList<Competition> konkurrencer = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            List<String> linjer = Files.readAllLines(Paths.get(fileName));
            Competition konkurrence = null;
            ArrayList<CompetitionResult> resultater = null;

            for (int i = 0; i < linjer.size(); i++) {
                String linje = linjer.get(i);

                if (linje.startsWith("Konkurrencenavn:")) {
                    // Gem forrige konkurrence, hvis den eksisterer
                    if (konkurrence != null) {
                        konkurrence.setResults(resultater);
                        konkurrencer.add(konkurrence);
                    }

                    konkurrence = new Competition();
                    resultater = new ArrayList<>();

                    // Robust parsing med split
                    String[] dele = linje.split(" {2,}"); // split ved 2 eller flere mellemrum

                    String name = dele[0].split(": ")[1];
                    String city = dele[1].split(": ")[1];
                    String dateStr = dele[2].split(": ")[1];

                    konkurrence.setName(name.trim());
                    konkurrence.setCity(city.trim());
                    konkurrence.setDate(LocalDate.parse(dateStr.trim(), formatter));
                }

                else if (linje.startsWith("Medlem:")) {
                    Pattern pattern = Pattern.compile("Medlem: (.*?)\\s+ID: (\\d+)");
                    Matcher matcher = pattern.matcher(linje);
                    if (matcher.find()) {
                        String medlemNavn = matcher.group(1).trim();
                        int medlemID = Integer.parseInt(matcher.group(2).trim());

                        Member medlem = findMemberByID(medlemID);
                        if (medlem == null) {
                            System.out.println("Kunne ikke finde medlem med ID: " + medlemID);
                            continue;
                        }

                        if (i + 1 < linjer.size()) {
                            String resultatLinje = linjer.get(i + 1);
                            Pattern resultatPattern = Pattern.compile("Disciplin: (\\w+)\\s+Tid: (\\d{2}:\\d{2}\\.\\d{3})\\s+Placering: (\\d+)");
                            Matcher resMatcher = resultatPattern.matcher(resultatLinje);
                            if (resMatcher.find()) {
                                Dicipline disciplin = Dicipline.valueOf(resMatcher.group(1).trim());
                                Duration tid = parseDuration(resMatcher.group(2).trim());
                                int rank = Integer.parseInt(resMatcher.group(3).trim());

                                CompetitionResult resultat = new CompetitionResult();
                                resultat.setDicipline(disciplin);
                                resultat.setTime(tid);
                                resultat.setRank(rank);
                                resultat.setSwimmer(medlem);

                                resultater.add(resultat);
                                resultater.add(resultat); // Tilføjer CompetitionResult til resultatet
                            }
                        }
                    }
                }
            }

            // Tilføj sidste konkurrence
            // Sikkerhed, gemmer sidste konkurrence, hvis det er en
            if (konkurrence != null) {
                konkurrence.setResults(resultater);
                konkurrencer.add(konkurrence);
            }

        } catch (IOException e) {
            System.out.println("Fejl ved indlæsning af konkurrencer: " + e.getMessage());
        }

        return konkurrencer;
    }

    private static String parseStringFromLine(String linje, String nøgle) {
        Pattern p = Pattern.compile("\\[" + nøgle + " = ([^\\]]+)]");
        Matcher m = p.matcher(linje);
        return m.find() ? m.group(1).replace("'", "") : null;
    }

    private static int parseIntFromLine(String linje, String nøgle) {
        String værdi = parseStringFromLine(linje, nøgle);
        return værdi != null ? Integer.parseInt(værdi) : 0;
    }

    private static Membership parseMembership(String linje) {
        String værdi = parseStringFromLine(linje, "Medlemskab");
        return (værdi != null && !værdi.equals("null")) ? Membership.valueOf(værdi) : null;
    }

    public static String formatDuration(Duration tid) {
        long totalMillis = tid.toMillis();
        long minutter = totalMillis / 60000;
        long sekunder = (totalMillis % 60000) / 1000;
        long millisekunder = totalMillis % 1000;
        return String.format("%02d:%02d.%03d", minutter, sekunder, millisekunder);
    }

    private static Member findMemberByID(int id) {
        for (Member m : MemberController.MemberList) {
            if (m.getMemberID() == id) return m;
        }
        return null;
    }

    private static Duration parseDuration(String tidStr) {
        String[] dele = tidStr.split("\\.");
        String[] minSek = dele[0].split(":");

        long minutter = Long.parseLong(minSek[0]);
        long sekunder = Long.parseLong(minSek[1]);
        long millisekunder = Long.parseLong(dele[1]);

        return Duration.ofMinutes(minutter)
                .plusSeconds(sekunder)
                .plusMillis(millisekunder);
    }
}

