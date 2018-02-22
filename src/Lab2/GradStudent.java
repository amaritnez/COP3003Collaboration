package Lab2;

public class GradStudent extends Student {
  
  private String researchTopic;
  private String advisor;
  
  public GradStudent() {
    super(); //calls student's constructor to initialize those values
    this.readData(); //initializes the values from GradStudent
  }
  
  private void readData() {
    
    /* again, gender causes different sentences to print out */
    if (this.gender == 'm') {
      System.out.print("Please input his research topic: ");
      researchTopic = Utilities.input.nextLine();
      
      System.out.print("Please input his research advisor: ");
      advisor = Utilities.input.nextLine();
      
    } else {
      System.out.print("Please input her research topic: ");
      researchTopic = Utilities.input.nextLine();
      
      System.out.print("Please input her research advisor: ");
      advisor = Utilities.input.nextLine();
    }
  }

  /* prints out the data from GradStudent */
  public void print() {
    super.print(); //calls Student's print, since we can't print them from here
    if (this.gender == 'm') {
      System.out.println("His research topic is " + researchTopic + ".");
      System.out.println("His advisor is " + advisor + ".");
    } else {
      System.out.println("Her research topic is " + researchTopic + ".");
      System.out.println("Her advisor is " + advisor + ".");
    }
  }

}
