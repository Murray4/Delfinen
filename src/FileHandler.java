import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
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
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred");
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, ArrayList<Member> medlemmer) {
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

    public static String sebber() {
        return """
                ++*++****+*******************+++++**********************************************+++++++++++++++++++++++++++++++++++++++++++++++++++
                ++++++***************************=++=+*********************************************++++++++++++++++++++++++++++++++++++++++++++++++
                ++++*+*******************************=+*+**************+=--==::::::::-::-*************+++++++++++++++++++++++++++++++++++++++++++++
                ++***++**********************************++++*******++-::,,:::::::::,,,:-::-+***********+++++++++++++++++++++++++++++++++++++++++++
                ++++*****************************************++++++=:::::::::::::::,,,,,,,::::-************++++++++++++++++++++++++++++++++++++++++
                ++*********************************************+=:::::::::,,::::::::::,,,,,:::::+*************+++++++++++++++++++++++++++++++++++++
                +++****************************************+*+=-::::,,,,,,,,,:::::::::,,,,,,,,:::+**************+++++++++++++++++++++++++++++++++++
                +*****************************************++==-::,,,,,,,,,,,,:::::::::,,,,,,,,,:-:-**************++++++++++++++++++++++++++++++++++
                ******************************************+=-:::,,,,,,::,,,,,,,,,:::::::::,,,,:::-:-**************+++++++++++++++++++++++++++++++++
                ******************************************==-::,,,,,,:::::::::-:::---::::::,,,:::=--+*************+++++++++++++++++++++++++++++++++
                ******************************************+=:::,,,,::::::--==+***++==-:::::,,,::=----+**********+*+++++++++++++++++++++++++++++++++
                *+***************************************+--:::,,::::--=++******88*+==--::::,,,=::::-+-*********+++++++++++++++++++++++++++++++++++
                ******************************************-::::,::::=+++****88888888+=----:::,:--:::::+:********+++++++++++++++++++++++++++++++++++
                ******************************************--:::::---------=++*8888888+----:,,:+:-::::::-:********++++++++++++++++++++++++++++++++++
                ******************************************+-:::::---:::::--==+*8888888=--:,,,:-=:::=-:::-=******+++++++++++++++++++++++++++++++++++
                +******************************************+==-::-==--::::-=++**88#888=-:,,,::::::==-:::::********+++++++++++++++++++++++++++++++++
                *********************************************+:,:-=+=-::::==--=+888#88=-:,,,::-:::=--:::::*******++++++++++++++++++++++++++++++++++
                **********************************************:::+88+=-::-=+****88##88+=:,,,:::,,:--::::::********+++++++++++++++++++++++++++++++++
                **********************************************+:+8##8*=--=++***88###88*=:,,,:::,,:-----:::-*******+++++++++++++++++++++++++++++++++
                ***********************************************+8####8**++++**8888888**=,,,,,:::::::::::-::*********+++++++++++++++++++++++++++++++
                ************************************************8####88*++++***888888*+=,,,,,,,,,:::---:::+*********+++++++++++++++++++++++++++++++
                **********************************************+*8#8888**++++***8888***+=:,,,,,,,,:::::::::+**********++++++++++++++++++++++++++++++
                *********************************************++*888888*+++++****88***++=:,,,,,,,,:::::::::+***********+++++++++++++++++++++++++++++
                *********************************************+=++-:,=**+++*****88*****++-,,,,,,,,,:::::::-***********++++++++++++++++++++++++++++++
                **********************************************+------****+************++=:,,,,,,,,,::::::**+***********++++++++++++++++++++++++++++
                *********************************************+*-:::--====**************+=-:,,,,,,:,:::::***+*********++++++++++++++++++++++++++++++
                *********************************************+*+::-==++=-=+*******8****++=::,,:,=::::*****************+++++++++++++++++++++++++++++
                *********************************************=**---::::::-=++*++****++++==------::==:*******+*********+++++++++++++++++++++++++++++
                *****************************88**************+**+--==++++=-==+++++++=+++=-----==+***:*******+********++++++++++++++++++++++++++++++
                ******************************8#*************+***--:--====---=+++=====-----=====+***:*****************+++++++++++++++++++++++++++++
                *++**************************=*88************+***+-:::--------=====----========++**+=********+********+++++++++++++++++++++++++++++
                ******************************=*88****************-:::::------------=====++++++****:*********+*******++++++++++++++++++++++++++++++
                ******************************+=*88***************+:::::::::--:-------==+++********:**********+******++++++++++++++++++++++++++++++
                +******+***********************++*88**********+***+::::::::::--------==++***88*888*:**********+******++++++++++++++++++++++++++++++
                +*+**************##8************=*+88**************-::::::::::-:::-=++++***8888888*-=8#*******+******++++++++++++++++++++++++++++++
                ++**++++********+++**88**********=+*8++*************+:::::::::::-=+++*****888888888:*+@@@%#8***+*****++++++++++++++++++++++++++++++
                +++***************-=++*888*******+=8*88*********************----=++*******888888888-8@@@@@%@@@@@@@%8****+++++++++++++++++++++++++++
                ++++**++***********+--=+****8****+=-+88*********************---=++*********88888888-@@@@@%@@@@@@@@@@@%@@%8****++++++++++++++++++--=
                +++++++***************+-=+***88***+=+*88********************=---==+*******8888888%@-@@@@@@@@@@@@%%@@%###%%%%@@@%*+++++++++++++++-:-
                ++++++*+*****************--=+**888*****8#*****************++-----==+******88888%%@@-@@@@@@@@@%@@@@@%%%%@@@%@@@@@@%+++++++++++++===+
                +++++++++++****************--=++*88888*888***+*******+++++++:---===+*****888#%%%%@@-@@@@@@%@@@@@@@%%%@@@%@@@@@@@@@@*++++++++++=++++
                +++++++++++******************=-==+88##888#88******++++++++++:----==++***8%%%%%%%%%%-@@@@%@@@@@@@%%%@@@%@@@@@@@@@@@@@*++++++++=+++++
                +++++++++++********************--=*88888888888***++++++++++++:---===*###%%%%%%%%%%%=@@%%@@@@@@@%%%@@@%@@@@%@@@@@@@@@%++++++++++=+++
                ++++++++++++++****************+====+*8888888888**+++++*++++++++***888###%%%%%%%##%@=%%%@@@@@@@%#@@@%@@@@%@@@@@@@@@@@@*+++++++++=++*
                ++++++++++++++****++********===+88****8888888888**++++++++++++++***88##%%%%%%%#%%%@+%%%@@@@@@%#@@@%@@@%%@%%@@@@@@@@@@@++++++++=+++*
                +++++++++++++++++**********=---=*******88888##888**++++++++******88####%%%%#%#%@%%%+#%@@@@%%%#%@%%@@%%@%%@@@@@@@@@@@@@8++++++=+++++
                +++++++++++++++++++++*******----=**++***88888####88*++++++++**88**8#######8%#%@@%%#+%@@@%%%%#%@%#@@%@%%@@@@@@@@@@@@@@@@+++==+++++++
                +++++++++++++++++++++*********+-=++++++**88888####88****+++++***8888###8888##%@@%#8*#@%%%%%#%@%#@@%%#%@@@@@@@@@@@@@@@@@#+++++++++++
                +++++++++++++++++++++++******+++===+++++**88888###888***********888####88*888%@@%#888%#%%%#%@@#%@@%#%@@@@@@%@@@@@@@@@@@@++++++++=++
                +++++++++++++++++++++++++****=+++++++++****888#####888**********88####88*88*#%@%##8%=#%%%##%@##@@%#%@@@@@@%@@@@@@@@@@@@@++++++++==+
                +++++++++++++++++++++++++****+-==++++*********8##8888888********8#####8**8**#@@%####-%%#8#%@@8%@#8%%%@%%%@@@@@@@@@@@@@@@8====+++==+
                ++++++++++++++++++++++++++****+---==++**********88888#@@@@******8####88*****#@@%##88-%#88##@#8%##%%%%%%%@@@@@@@@@@@@@@@@%==========
                +++++++++++++++++++++++++++*****=--==+++***888***88%@@@@@@@#****#####888****%@@@%#8*:#88%##@8#%#%%%%%%@@@@@@@@@@@@@@@@@@%::========
                +++++++++++++++++++++++++++++*****=--==+++++*****%@@@@@@@@@@@**8####8888***8%@@%###*:**#%8#@8%#%%%%%@@@@@@@@@@@@@@@@@@@@%--========
                +++++++++++++++++++++++++++++++++***---=====+***%%%%@@@@@@@@@@%8####8888***8%@@%###*-=*%#*%##8#%##%@@@@@@@@@@@@@@@@@@@@@#:::=======
                ++++++++++++++++++++++++++++++++++++*+=-----=*8%%%%%@@@%@@@@@@@@####8******8%@@%###*+-8%88@8%####%@@@@@@@@@@@@@@@@@@@@@@#:::-======
                +++++++++++++++++++++++++++++++++++******----#%%##%%@%%@@@@@@@@@@@@@8****+*8%@@%##8*+:8#*#%###8#%@@@@@@@@@@@@@@@@@@@@@@@%:::-======
                
                """;

    }
}

