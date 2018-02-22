package Lab3;
import java.util.Random;
import java.util.Scanner;

/* default testingdriver; code provided by lab3.pdf */
public class TestingDriver {
  
  public static Package load_a_package(Scanner scanner) {
    Package pack = null;
    int rand = (new Random()).nextInt(4);
    switch (rand) {
      case 0:
        pack = new Box();
        break;
      case 1:
        pack = new Letter();
        break;
      case 2:
        pack = new MetalCrate();
        break;
      case 3:
        pack = new WoodCrate();
        break;
    }
    pack.input(scanner);
    return pack;
  }

  public static void main(String args[]) {
    Scanner scanner = new Scanner(System.in);
    Package pack = null;
    for (int i = 0; i < 5; i++) {
      System.out.printf("\n**** package %d ****\n", i);
      pack = load_a_package(scanner);
      System.out.printf("The cost of this package is $%.2f\n", pack.cost());
    }
    scanner.close();
  }

}
