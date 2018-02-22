package Lab6;

public class Oxygen implements Runnable {

  ReactionArea buff;

  public Oxygen(ReactionArea buff){
    this.buff=buff; //use the reactionArea provided to react in
    }

  public void run() {
    //produce a total of 10 oxygen
    for (int i = 0; i < 10; i++) {
      try {
        buff.increWOxygen(i);
        Thread.sleep(200); //wait 200ms between each addition
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }
  
}
