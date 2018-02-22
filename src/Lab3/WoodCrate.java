package Lab3;

public class WoodCrate extends Crate {

  @Override
  public double cost() {
    return weight * 1.4;//the cost is 1.4x the weight, and is auto-formatted in TestingDriver
  }

}
