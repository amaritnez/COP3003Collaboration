package Lab2;

public class Student {
  
  private String name;
  private int ssn;
  private int numOfCourses;
  private Date birthDate;
  protected char gender;
  
  public Student() {
    this.readData(); //calls readData; allows for the user to enter values
  }

  /* the method is private so that it can only be called from the constructor */
  private void readData() {

    /* the following collects values for Student */

    System.out.print("Please input the name: ");
    name = Utilities.input.nextLine();

    System.out.print("Please input the ssn: ");
    ssn = Utilities.input.nextInt();

    System.out.print("Male/Female (m/f): ");
    gender = Utilities.input.next().charAt(0);
    /* since .next returns a string, we use .charAt to get the single char we need */

    /* The sentences we print out change slightly depending on the gender */
    if (gender == 'm') {

      System.out.print("How many courses is he taking: ");
      numOfCourses = Utilities.input.nextInt();

      System.out.println("Please input his birthdate:");
      this.birthDate = new Date(); //calls Date constructor, which handles those values
      Utilities.input.nextLine(); //done to help with formatting lines
      
    } else {

      System.out.print("How many courses is she taking: ");
      numOfCourses = Utilities.input.nextInt();

      System.out.println("Please input her birthdate:");
      this.birthDate = new Date();
      Utilities.input.nextLine();
    }
  }

  /* prints the data from Student */
  public void print() {
    /* similarly, the sentences vary slightly based on gender */
    if (gender == 'm') {
      
      System.out.println("The information you input was");
      System.out.println(name + "'s ssn is " + ssn + ".");
      System.out.println("He is taking " + numOfCourses + " courses.");
      System.out.print("His birth date is ");//since Date's values are private, we can't print them here
      birthDate.print(); //date has it's own print method, to print the numbers
      
    } else {
      
      System.out.println("The information you input was");
      System.out.println(name + "'s ssn is " + ssn + ".");
      System.out.println("She is taking " + numOfCourses + " courses.");
      System.out.print("Her birth date is ");
      birthDate.print();
    }
  }

}
