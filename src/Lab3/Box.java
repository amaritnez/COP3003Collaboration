package Lab3;
import java.util.Scanner;

public class Box implements Package {
  
  private double weight; //weight of the box; is private as nothing else needs to access it

  @Override
  public double cost() {
    return weight * 1.2; //the cost is 1.2x the weight, and is auto-formatted in TestingDriver
  }

  @Override
  public void input(Scanner scanner) {
    System.out.print("Please input the weight for the Box (lbs) : ");
    weight = scanner.nextDouble();
  }
  
}
