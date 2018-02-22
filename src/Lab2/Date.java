package Lab2;

public class Date {
  
  private int year;
  private int month;
  private int day;
  
  public Date() {
    this.readData(); //simply collects data from readData method
  }

  /* reads values for dates. this method is private so that it may only be called from constructor,
     * independently from the Student-chain */
  private void readData() {
    System.out.print("\tPlease input the month: ");
    month = Utilities.input.nextInt();
    
    System.out.print("\tPlease input the day: ");
    day = Utilities.input.nextInt();
    
    System.out.print("\tPlease input the year: ");
    year = Utilities.input.nextInt();
  }

  /* prints the date m/d/y and nothing else. This is normally concatenated with other sentences*/
  public void print() {
    System.out.println(month + "/" + day + "/" + year);
  }

}
