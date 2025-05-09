import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
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

    public static void writeToFile(String tekstTilFil, String fileName) {
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            myWriter.write(tekstTilFil);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred. unable to write to file!");
            e.printStackTrace();
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

    public static void tilføjTræningsResultatTilFil(String fileName, int søgtID, TrainingResult resultat) {
        try {
            List<String> linjer = Files.readAllLines(Paths.get(fileName));
            List<String> nyeLinjer = new ArrayList<>();

            boolean korrektID = false;

            for (String linje : linjer) {
                if (linje.matches("\\[ID\\s*=\\s*" + søgtID + "]\\s*,.*")) {
                    korrektID = true;
                    nyeLinjer.add(linje);
                    continue;
                }

                if (korrektID && linje.contains("[Træningsresultater =")) {
                    String nyLinje;
                    if (linje.contains("null")) {
                        nyLinje = linje.replace("[Træningsresultater = null]",
                                "[Træningsresultater = [" + resultat + "]]");
                    } else {
                        nyLinje = linje.replaceAll(
                                "\\[Træningsresultater = \\[(.*?)\\]\\]",
                                "[Træningsresultater = [$1, " + resultat + "]]"
                        );
                    }
                    nyeLinjer.add(nyLinje);
                    korrektID = false;
                } else {
                    nyeLinjer.add(linje);
                }
            }

            Files.write(Paths.get(fileName), nyeLinjer);
        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af fil: " + e.getMessage());
        }
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

                // Anden linje med attributter
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

                // Træningsresultater og konkurrencer kan udbygges her senere
            }

            if (medlem != null) MemberController.MemberList.add(medlem); // Tilføj sidste

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

