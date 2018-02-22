package Lab6;

public class ReactionArea {

  public int waitingHydrogen = 0; //number of hydrogen waiting
  public int waitingOxygen = 0; //number of oxygen waiting
  public int waterFormed = 0; /* number of water formed. 
   * Will subtract 1 from waitingHydrogen and 2 from waitingOxygen when created */
  
  public synchronized void increWHydrogen(int i) throws InterruptedException {
    //While more than 5 hydrogen are present, stop production and wait
    while (waitingHydrogen > 5) {
      wait(); //release the lock on reaction
    }
    
    waitingHydrogen++; //add one more hydrogen to our reaction
    System.out.println("The " + i + "th Hydrogen atom was added.");
    notifyAll(); //alert the waiting threads that we're done; release the lock on reaction
  }
  
  public synchronized void increWOxygen(int i) throws InterruptedException {
    //While more than 2 oxygen are present, stop production and wait
    while (waitingOxygen > 2) {
      wait();
    }
    
    waitingOxygen++; //add one more oxygen to reaction
    System.out.println("The " + i + "th Oxygen atom was added.");
    notifyAll();
    
  }
  
  public synchronized void react() throws InterruptedException {
    //We need 1 oxygen and 2 hydrogen to make water; if we don't have that much, wait
    while (waitingOxygen < 1 || waitingHydrogen < 2) {
      wait();
    }
    
    waitingOxygen--; //consume the one oxygen
    waitingHydrogen-= 2; //consume the two hydrogen
    System.out.println("The " + waterFormed + "th water molecule was formed.");
    waterFormed++; //generate a new water
    notifyAll();
  }
  
  public static void main(String args[]) {
    
    ReactionArea reaction = new ReactionArea(); //create a reaction object for our buffer
    
    //create the threads
    Thread hydrogenGen = new Thread(new Hydrogen(reaction));
    Thread oxygenGen = new Thread(new Oxygen(reaction));
    Thread waterGen = new Thread(new Reactor(reaction));
    
    //and begin!
    hydrogenGen.start();
    oxygenGen.start();
    waterGen.start();
    
  }

}
