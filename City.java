public class City{
  private int identifier;
  private int XCoordinate;
  private int YCoordinate;

  public City(int identity, int x, int y){
    this.identifier = identity;
    this.XCoordinate = x;
    this.YCoordinate = y;
  }

  public int calculateDistanceTo(City a){
    double xValues = Math.pow((this.getXCoordinate() - a.getXCoordinate()), 2);
    double yValues = Math.pow((this.getYCoordinate() - a.getYCoordinate()), 2);
    long result = Math.round(Math.sqrt(xValues + yValues));
    return Math.toIntExact(result);
  }

  public int getIdentity(){
    return this.identifier;
  }

  public int getXCoordinate(){
    return this.XCoordinate;
  }

  public int getYCoordinate(){
    return this.YCoordinate;
  }

  @Override
  public String toString(){
    String s = "Identifier: " + this.identifier + ", Coordinates: (" + this.XCoordinate + ", " + this.YCoordinate + ")";
    return s;
  }

}
