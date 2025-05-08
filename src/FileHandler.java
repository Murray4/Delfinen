import java.io.*;
import java.util.Arrays;
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
        int maxID = -1;

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

        int nextID = maxID+1;
        return nextID;
    }


    public static void sortFile(String path) {
        File fil = new File(path);

    }
}
