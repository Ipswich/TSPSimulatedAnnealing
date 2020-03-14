import java.util.ArrayList;
import java.util.Collections;

public class Tour{

  private ArrayList<City> tour = new ArrayList<City>();
  private int tourLength = 0;

/*
---------CONSTRUCTOR METHODS----------
*/

  //Creates a new empty (null) tour of the same size as the citiesList
  public Tour(){
    for(int i = 0; i < SimulatedAnnealingTSP.getSize(); i++){
      tour.add(null);
    }
  }

  //Creates/copies a tour from an existing tour
  /*
  UNCHECKED WARNING SUPPRESSED - No reason it should be given any other type
  of ArrayList. This thing isn't THAT complicated. Compiler freaks because
  it can't determine the type of object contained in the passed ArrayList
  beyond the fact that it's an Object.
  */
  @SuppressWarnings("unchecked")
  public Tour(ArrayList<City> data){
    this.tour = (ArrayList<City>) data.clone();
  }

/*
---------GETTERS----------
*/

  //Returns the ArrayList that defines the tour.
  public ArrayList<City> getTour(){
    return this.tour;
  }

/*
---------SETTERS----------
*/

  public void setCityByIndex(City city, int index){
    this.tour.set(index, city);
    //Order of cities has changed, reset tour length.
    this.tourLength = 0;
  }

/*
---------UTILITY METHODS----------
*/

//Calculates the length of the tour by iterating through the list of cities and
//summing the distances between each stop.
public int calculateTourLength(){
  if(this.tourLength == 0){
    int result = 0;
    int numberOfCities = tour.size();
    for(int i = 0; i < numberOfCities; i++){
      City from = this.tour.get(i);
      City to;
      if(i+1 < numberOfCities){
        to = this.tour.get(i+1);
      } else {
        to = this.tour.get(0);
      }
      result += from.calculateDistanceTo(to);
    }
    this.tourLength = result;
  }
  return this.tourLength;
}

//Swaps cities at two randomly generated indices.
public void swapCities(){

  //Randomly select two tour indices
  int index1 = (int) (this.tour.size() * Math.random());
  int index2 = (int) (this.tour.size() * Math.random());

  City a = tour.get(index1);
  City b = tour.get(index2);

  this.tour.set(index1, b);
  this.tour.set(index2, a);
}

public void swapNeighborCities(){

  //Randomly select two tour indices
  int index1 = (int) (this.tour.size() * Math.random());
  int index2 = index1 + 1;
  if (index1 == this.tour.size() - 1){
    index2 = 0;
  }

  City a = tour.get(index1);
  City b = tour.get(index2);

  this.tour.set(index1, b);
  this.tour.set(index2, a);
}

}
