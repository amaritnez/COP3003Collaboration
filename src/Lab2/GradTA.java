package Lab2;
public class GradTA extends GradStudent {
  
  private String taCourse;
  private String taInstructor;
  private Date hireDate;
  
  public GradTA() {
    super(); //calls GradStudent's constructor, which in turn calls Student's constructor
    this.readData(); //reads data from GradTA alone
  }
  
  private void readData() {
    
    if (this.gender == 'm') {
      
     System.out.print("Please input his TA course: ");
     taCourse = Utilities.input.nextLine();
     
     System.out.print("Please input his TA instructor: ");
     taInstructor = Utilities.input.nextLine();
     
     System.out.println("Please input his hire date: ");
     this.hireDate = new Date(); //calls Date constructor to handle values for hireDate
     System.out.println(); //an empty line, since at this point all values have been collected
     
    } else {
      
      System.out.print("Please input her TA course: ");
      taCourse = Utilities.input.nextLine();
      
      System.out.print("Please input her TA instructor: ");
      taInstructor = Utilities.input.nextLine();
      
      System.out.println("Please input her hire date: ");
      this.hireDate = new Date();
      System.out.println();
      
    }
  }
  
  public void print() {
    super.print(); //calls GradStudent's print since we can't print those values from here
    if (this.gender == 'm') {
      System.out.println("His TA course is " + taCourse + ".");
      System.out.println("His TA instructor is " + taInstructor + ".");
      System.out.print("His hire date is ");
      hireDate.print();//calls hireDate's print since we can't print those values from here either
    } else {
      System.out.println("Her TA course is " + taCourse + ".");
      System.out.println("Her TA instructor is " + taInstructor + ".");
      System.out.print("Her hire date is ");
      hireDate.print();
    }
  }

}
