import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Competition {

    //ATTRIBUTER
    String name;
    String city;
    LocalDate date;
    ArrayList<CompetitionResult> results;

    public Competition() {
        this.results = new ArrayList<>();
    }

    //GETTERS
    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDate() {
        return date;
    }

    public ArrayList<CompetitionResult> getResults() {
        return results;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setResults(ArrayList<CompetitionResult> results) {
         this.results = results;
    }

    public void addResults(CompetitionResult result) {
        results.add(result);
    }

    // METODER
    @Override
    public String toString() {
        return "Konkurrencenavn: " + name + " - Dato: " + date + " - By: " + city + "\n" +
                "Resultater: \n" + results;
    }
}
