NOTES:
  Written for Project Group 54, OSU CS325 Winter 2020.
  Heavily Inspired by:
    https://en.wikipedia.org/wiki/Simulated_annealing
    https://mathworld.wolfram.com/SimulatedAnnealing.html
    https://www.geeksforgeeks.org/simulated-annealing/
    http://www.theprojectspot.com/tutorial-post/simulated-annealing-algorithm-for-beginners/6
    https://www.baeldung.com/java-simulated-annealing-for-traveling-salesman

SimulatedAnnealingTSP outputs a file with the same name as the input file with
".tour" appended to the end of it. This file is placed in the
same directory as the java file.

To compile:
  javac SimulatedAnnealingTSP.java City.java Tour.java

To run:
  java SimulatedAnnealingTSP <input file>

  Example:
    java SimulatedAnnealingTSP CS325TSP_Files/tsp_example_1.txt
  Output file will be:
    ./tsp_example_1.txt.tour
