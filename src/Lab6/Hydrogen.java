package Lab6;

public class Hydrogen implements Runnable{
  
  ReactionArea buff;

  public Hydrogen(ReactionArea buff){
    this.buff=buff; //use the reactionArea object provided to react in
    }

  public void run() {
    //produce a total of 20 hydrogen
    for (int i = 0; i < 20; i++) {
      try {
        buff.increWHydrogen(i);
        Thread.sleep(100); //wait 100ms between each addition
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
  }

}
