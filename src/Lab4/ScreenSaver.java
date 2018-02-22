package Lab4;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class ScreenSaver extends JPanel implements ActionListener, MouseWheelListener {

  private int xPoint[] = new int[60]; //array of x-coordinates
  private int yPoint[] = new int[60]; //array of y-coordinates
  private int numOfPoints = 0; //self-explanatory
  
  private int wheelMoves = 0; //used to measure the mouse wheel "clicks" directly
  
  private int radius = 10; //radius from center of shape
  
  private double delay = 1000; //delay in miliseconds (1000ms = 1s)
  private Timer timer = null; //timer object for delay
  
  private double centerX=-1; //center x-coordinate
  private double centerY=-1; //center y-coordinate
  
  //the following two fields are for adding color to the drawing, because why not?
  private Random randomColor = new Random(); //random number generator to determine color
  private Color rainbow; //color object to set color of the drawing
  
  //constructor
  public ScreenSaver() {
    this.addMouseWheelListener(this); //adds the mouseWheelListener to the ScreenSaver object
    timer = new Timer(1000, this); //initializes the timer with delay 1000ms and this listener
    timer.start(); //starts timer
    } 
  
  @Override
  public void mouseWheelMoved(MouseWheelEvent e) {
    
    wheelMoves = e.getWheelRotation(); //gets it's value from the mouse wheel's movement
    
    //when wheelMoves goes under 0 (scroll mouse wheel down), the delay is increased by 20ms
    if (wheelMoves < 0) {
      delay += 20; 
    } else { //when mouse wheel scrolls up, delay is decreased by 20ms
      delay -= 20;
    }
    
    //in case the delay goes under 0
    if (delay < 0) {
      delay = 0;
    } else if (delay > 5000) { //in case the delay goes over 5 seconds
      delay = 5000;
    } else {
      //do nothing
    }
    
    //prints out the delay for your convenience
    System.out.printf("Current delay: %.2f seconds\n", delay/1000);
    timer.setDelay((int) delay); //sets the delay for timer object
    timer.restart(); //restarts it so that the new time starts from 0
  }

  //is called at the end of every timer
  @Override
  public void actionPerformed(ActionEvent e) {
    //calls add a point
    addAPoint();
    //generates a new random color for rainbow object
    rainbow = new Color(randomColor.nextInt(255), randomColor.nextInt(255), randomColor.nextInt(255));
    // updating the panel
    repaint();
  }

  //adds new points for drawPolyLine
  private void addAPoint() {

    // getting the center of the screen
    if (centerX < 0)
      centerX = getSize().getWidth() / 2;
    if (centerY < 0)
      centerY = getSize().getHeight() / 2;

    // finding the coordinates of the new point
    double x = centerX + Math.cos(numOfPoints * Math.PI / 3) * radius;
    double y = centerY + Math.sin(numOfPoints * Math.PI / 3) * radius;

    // saving the point for display
    this.xPoint[numOfPoints] = (int) x;
    this.yPoint[numOfPoints] = (int) y;

    
    //increases numOfPoints by 1; takes the mod of numOfPoints so that it never goes past 59
    numOfPoints = (numOfPoints+1)%60;
    
    //if numOfPoints is 59, we reached the end of the shape, so reset the radius
    if (numOfPoints == 59) {
      radius = 10;
    } else {
      radius += 3; //otherwise, increase the raidus by 3
    }
    
  }

  //paints the actual line
  public void paintComponent(Graphics g){
    g.setColor(rainbow); //sets the color of g object to rainbow
    g.clearRect(0,0,(int)getSize().getWidth(),(int)getSize().getHeight()); //clears an area for our drawing
    g.drawPolyline(xPoint,yPoint,numOfPoints); //draws the lines
  }
  
}
