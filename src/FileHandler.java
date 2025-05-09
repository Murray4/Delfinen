import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
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
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) { // false = overskriv filen
            for (Member medlem : MemberController.MemberList) {
                writer.write(medlem.toString() + System.lineSeparator());
            }
            System.out.println(Farver.GREEN + "MedlemsListe opdateret!" + Farver.RESET);
        } catch (IOException e) {
            System.err.println(Farver.RED + "Fejl ved skrivning til fil: " + e.getMessage() + Farver.RESET);
        }
    }


    public static void readFile(String fileName) {
        try {
            File fil = new File(fileName);
            Scanner myReader = new Scanner(fil);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] words = data.split(",");
                System.out.println(words);
            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
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

    public static ArrayList<Member> indlæsMedlemmerFraFil(String fileName) {
        try {
            List<String> linjer = Files.readAllLines(Paths.get(fileName));
            Member medlem = null;

            for (String linje : linjer) {
                // Ny medlem starter
                if (linje.startsWith("[ID =")) {
                    if (medlem != null) MemberController.MemberList.add(medlem);
                    medlem = new Member();

                    // ID og navn
                    Pattern p = Pattern.compile("\\[ID = (\\d+)] , \\[MedlemsNavn = (.*)]");
                    Matcher m = p.matcher(linje);
                    if (m.find()) {
                        medlem.setMemberID(Integer.parseInt(m.group(1)));
                        medlem.setMemberName(m.group(2));
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
                    Matcher matcher = Pattern.compile("\\[Træningsresultater = \\[(.*?)]").matcher(linje);
                    if (matcher.find()) {
                        String data = matcher.group(1); // fx: 2025-05-09 - CRAWL - 01:10 - God kommentar
                        Map<Dicipline, TrainingResult> resultMap = new HashMap<>();

                        String[] resultater = data.split(", (?=\\d{4}-\\d{2}-\\d{2})"); // split på dato-start
                        for (String entry : resultater) {
                            try {
                                String[] parts = entry.trim().split(" - ", 4);
                                if (parts.length >= 3) {
                                    LocalDate dato = LocalDate.parse(parts[0].trim());
                                    Dicipline disciplin = Dicipline.valueOf(parts[1].trim().toUpperCase());
                                    LocalTime tid = LocalTime.parse("00:" + parts[2].trim());
                                    String kommentar = (parts.length == 4) ? parts[3].trim() : "";

                                    TrainingResult tr = TrainingResult.createTrainingResult(disciplin, tid, dato, kommentar, medlem);
                                    resultMap.put(disciplin, tr);
                                }
                            } catch (Exception e) {
                                System.out.println("Kunne ikke parse træningsresultat: " + entry);
                            }
                        }

                        medlem.setTrainingResult(resultMap);
                    }
                }

                // TODO: Konkurrenceresultater kan tilføjes her
            }

            if (medlem != null) MemberController.MemberList.add(medlem); // Sidste medlem

        } catch (IOException e) {
            System.out.println("Fejl ved læsning: " + e.getMessage());
        }

        return MemberController.MemberList;
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
}

