package Lab3;
import java.util.Scanner;

public abstract class Crate implements Package {
  
  protected double weight; //protected so that it may be used in it's subclasses

  /*Gets input for weights of MetalCrate and WoodCrate objects*/
  @Override
  public void input(Scanner scanner) {
    /*getClass().getSimpleName() is used to get the name of the class the object that uses this method*/
    System.out.print("Please input the weight for the " + getClass().getSimpleName() + " (lbs) :");
    weight = scanner.nextDouble();
  }

}
