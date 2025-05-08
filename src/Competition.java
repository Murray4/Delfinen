import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Competition {

    //ATTRIBUTER
    String name;
    String city;
    LocalDate date;
    List<CompetitionResult> results;

  //CONSTRUCTOR
  public Competition(String name, String city, LocalDate date){
      this.name = name;
      this.city = city;
      this.date = date;
      this.results = new ArrayList<>();
  }

  //GETTERS
public String getName(){
      return name;
}

public String getCity(){
      return city;
}
public LocalDate getDate(){
      return date;
}
public List<CompetitionResult> getResults() {
        return results;
}


  //SETTERS
public void setName(String name){
this.name = name;
}

public void setCity(String city) {
this.city = city;
}

public void setDate (LocalDate date) {
this.date = date;
}
public void setResults(List<CompetitionResult> results) {
    this.results = results;
}

    // Add a single result
    // public void addResult(CompetitionResult result) {
    // this.results.add(result);


    // METODER
@Override
    public String toString() {
    return "Competition{" +
            "name='" + name + '\'' +
            ", city='" + city + '\'' +
            ", date=" + date +
            ", results=" + results +
            '}';
    }
}
