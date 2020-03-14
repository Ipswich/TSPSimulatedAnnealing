import java.lang.Math;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class SimulatedAnnealingTSP {



  //COOLING FUNCTIONS
  private static double boltzmann(double t, long i){
    return t / Math.log(i);
  }

  private static double standard(double t, double rate){
    return t * rate;
  }

  //Create arrayList for passed cities
  private static ArrayList<City> citiesList = new ArrayList<City>();

  public static int getSize(){
    return citiesList.size();
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
    Path input = Paths.get(path);

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

//////////Setup for annealing//////////

    //INITIAL ANNEALING VALUES
    double startTemperature = 1000000;
    double finishTemperature = .01;
    double coolingRate = .999999;
    int iterationsPerTemperature = 1;
    long seed = 2;

    //Generate inital tour from citiesList
    Tour currentTour = new Tour();
    for(int i = 0; i < citiesList.size(); i++){
      currentTour.setCityByIndex(citiesList.get(i), i);
    }

    //Randomize
    Collections.shuffle(currentTour.getTour(), new Random(seed));

    Tour bestTour = new Tour(currentTour.getTour());
    long startTime = System.nanoTime();
    long pass = 0;
    double currentTemperature = startTemperature;

/////////ANNEALING LOOP///////////
    System.out.println("Initial length: " + currentTour.calculateTourLength());
    while (currentTemperature > finishTemperature){
      pass++;
      //Iterate per temperature
      for (int i = 0; i < iterationsPerTemperature; i++) {
        //Create mutation of currentTour.
        Tour newTour = new Tour(currentTour.getTour());
        newTour.swapCities();

        //Calculate energies (distances) of two tours to compare.
        int currentEnergy = currentTour.calculateTourLength();
        int newEnergy = newTour.calculateTourLength();

        //If new tour is shorter than old tour, accept, else, calculate acceptance probability.
        double val;
        if(currentEnergy > newEnergy){
          val = 1;
        } else {
          val = Math.exp(-(newEnergy - currentEnergy) / currentTemperature);
        }

        //Stochastically decide if we should accept the new mutation.
        if(val > Math.random()) {
          currentTour = new Tour(newTour.getTour());
        }

        //Calculate distances to compare to the best tour and update if necessary.
        if(currentTour.calculateTourLength() < bestTour.calculateTourLength()){
          bestTour = new Tour(currentTour.getTour());
        }
      }
      // Update temperature
      // currentTemperature = boltzmann(temperature, pass);
      currentTemperature = standard(currentTemperature, coolingRate);
    }
//////////END OF LOOP/////////

    //Algorithm run time
    long endTime = System.nanoTime();
    long duration = (endTime - startTime)/1000000000;

    //Output stuff.
    System.out.println("Length: " + bestTour.calculateTourLength());
    System.out.println("Time: " + duration + " seconds");

    String outputPath = input.getFileName() + ".tour";
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
