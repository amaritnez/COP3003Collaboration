package Lab6;

public class Reactor implements Runnable {

  ReactionArea buff;

  public Reactor(ReactionArea buff){
    this.buff=buff;
    }

  public void run() {
   
    try {
      Thread.sleep(2000); // sleep for 2 seconds first
      //produce a total of 10 water
      for (int i = 0; i < 10; i++) {
        buff.react();
        Thread.sleep(50); //wait 50ms between each consumption; means it will consume faster than can produce
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.exit(1);
    }
  }

}
