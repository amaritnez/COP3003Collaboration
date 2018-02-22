package Lab3;
import java.util.Scanner;

public class Letter implements Package {
  
  private double numOfPages; //number of pages in the letter

  /*The cost is 5x the number of pages, and is divided by 100 since the cost is in cents,
   * not dollars; is also auto-formatted by the testingdriver*/
  @Override
  public double cost() {
    return (5 * numOfPages)/100;
  }

  @Override
  public void input(Scanner scanner) {
    System.out.print("Please input the number of pages for the letter (pgs) :");
    numOfPages = scanner.nextDouble();
  }

}
