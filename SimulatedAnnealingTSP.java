import java.lang.Math;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class SimulatedAnnealingTSP {

  //INITIAL ANNEALING VALUES
  static double temperature = 100000;
  static double coolingRate = .9999;

  //Create arrayList for passed cities
  private static ArrayList<City> citiesList = new ArrayList<City>();

  public static int getSize(){
    return citiesList.size();
  }

  public static City getCityByIndex(int index){
    return citiesList.get(index);
  }

///////////////////////////////////////////////////////////////////

  public static void main(String[] args){
    //Argument number check
    if(args.length != 1){
      System.out.println("Invalid number of arguments! Expected 1, received " + args.length + ".");
      return;
    }
    //Reading file stuff
    String path = args[0];
    //Try to parse cities into the arrayList
    try{
      FileInputStream fs = new FileInputStream(path);
      Scanner scan = new Scanner(fs);
      //Break apart each line into integers, create cities and add to the citiesList
      while(scan.hasNext()){
        if(scan.hasNextInt()){
          int i = scan.nextInt();
          int x = scan.nextInt();
          int y = scan.nextInt();
          citiesList.add(new City(i, x, y));
        }
        else
          scan.next();
      }
      fs.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }

    //Setup for annealing
    int problemSize = citiesList.size();
    Tour currentTour = new Tour();
    //Generate inital tour from citiesList
    for(int i = 0; i < citiesList.size(); i++){
      currentTour.setCityByIndex(citiesList.get(i), i);
    }

    //Annealing the crap out of it.
    Tour bestTour = new Tour(currentTour.getTour());
    long startTime = System.nanoTime();

    while (temperature > 1){
      //Create mutation of currentTour.
      Tour newTour = new Tour(currentTour.getTour());
      newTour.swapCities();

      //Calculate energies (distances) of two tours to compare.
      int currentEnergy = currentTour.calculateTourLength();
      int newEnergy = newTour.calculateTourLength();

      //Stochastically decide if we should accept the new mutation.
      double val;
      if(currentEnergy > newEnergy){
        val = 1;
      } else {
        val = Math.exp((currentEnergy - newEnergy) / temperature);
      }
      if (val > Math.random()) {
        currentTour = new Tour(newTour.getTour());
      }

      //Calculate distances to compare to the best tour and update if necessary.
      if(currentTour.calculateTourLength() < bestTour.calculateTourLength()){
        bestTour = new Tour(currentTour.getTour());
      }
      //Update temperature
      temperature = temperature * coolingRate;
    }
    long endTime = System.nanoTime();
    //Algorithm run time
    long duration = (endTime - startTime)/1000000000;

    //Output stuff.
    System.out.println("Length: " + bestTour.calculateTourLength());
    System.out.println("Time: " + duration + " seconds");

    String outputPath = path + ".SimulatedAnnealingTour";
    try{
      PrintWriter output = new PrintWriter(outputPath, "UTF-8");
      output.println(bestTour.calculateTourLength());
      bestTour.getTour().forEach(
        (var) -> output.println(var.getIdentity())
      );
      output.close();
    }
    catch(IOException e){
      e.printStackTrace();
    }
  }
}
