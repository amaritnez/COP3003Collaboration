package Lab3;

public class MetalCrate extends Crate {

  @Override
  public double cost() {
    return weight * 1.3; //the cost is 1.3x the weight, and is auto-formatted in TestingDriver
  }
  
}
