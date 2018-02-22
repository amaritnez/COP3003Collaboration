package HW3;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel {

  // arraylist where we store each new drawing created
  private static ArrayList<Drawing> Shapes = new ArrayList<Drawing>();
  
  // a Drawing object for the current drawing being modified
  private Drawing currentShape = new Drawing("Red", "Rectangle", false);

  int startX = 0; //local storage for the starting xCord
  int startY = 0; //local storage for the starting yCord
  int endX = 0; //local storage for the ending xCord
  int endY = 0; //local storage for the ending yCord

  private boolean doneDrawing = false; //boolean to determine whether the user is done drawing or not

  private PaintHandler painter = new PaintHandler(); //paintHandler, where all the drawing happens

  public Canvas() {
    /* gui stuff */
    setOpaque(true);
    addMouseListener(painter);
    addMouseMotionListener(painter);
  }

  //paint method to draw stuff
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.clearRect(0, 0, (int) getSize().getWidth(), (int) getSize().getWidth()); //clear an area for our drawing
    
    if (doneDrawing == false) {
      //sets the color of the graphic to the color of the current drawing
      g.setColor(currentShape.getShapeColor()); 
      
      //a switch statement which tries to get the shape of the current drawing based on its name
      switch (currentShape.getShape()) {

        case "Rectangle":
          //if the shape is unfilled, then draw an unfilled rectangle
          if (currentShape.isFilled() == false) {
            g.drawRect(currentShape.getStartX(), currentShape.getStartY(), currentShape.getShapeWidth(),
                currentShape.getShapeHeight());
          } else { //otherwise, draw a filled rectangle
            g.fillRect(currentShape.getStartX(), currentShape.getStartY(), currentShape.getShapeWidth(),
                currentShape.getShapeHeight());
          }
          break;

        case "Oval":
          //similar logic for oval
          if (currentShape.isFilled() == false) {
            g.drawOval(currentShape.getStartX(), currentShape.getStartY(), currentShape.getShapeWidth(),
                currentShape.getShapeHeight());
          } else {
            g.fillOval(currentShape.getStartX(), currentShape.getStartY(), currentShape.getShapeWidth(),
                currentShape.getShapeHeight());
          }
          break;

        case "Edge":
          //there's no fill/unfilled for edge, so just draw it
          g.drawLine(currentShape.getStartX(), currentShape.getStartY(), currentShape.getEndX(),
              currentShape.getEndY());
          break;

        default:
          System.out.println("Somethings wrong..."); //Something is wrong if it gets here...
          System.exit(1);
      }
    }

    //a for loop which goes through the arraylist of drawings, so no drawing gets left out
    for (Drawing currentDrawing : Shapes) {
      //change the graphic to the color of the current drawing in the loop
      g.setColor(currentDrawing.getShapeColor()); 

      //same thing: a switch statement for the name of the current drawing, to determine which shape to draw
      switch (currentDrawing.getShape()) {

        case "Rectangle":
          if (currentDrawing.isFilled() == false) {
            g.drawRect(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getShapeWidth(),
                currentDrawing.getShapeHeight());
          } else {
            g.fillRect(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getShapeWidth(),
                currentDrawing.getShapeHeight());
          }
          break;

        case "Oval":
          if (currentDrawing.isFilled() == false) {
            g.drawOval(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getShapeWidth(),
                currentDrawing.getShapeHeight());
          } else {
            g.fillOval(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getShapeWidth(),
                currentDrawing.getShapeHeight());
          }
          break;

        case "Edge":
          g.drawLine(currentDrawing.getStartX(), currentDrawing.getStartY(), currentDrawing.getEndX(),
              currentDrawing.getEndY());
          break;

        default:
          System.out.println("Somethings wrong...");
      }

      repaint(); //repaint so we can see the drawings
    }
  }

  // called when the undo button is pressed
  public void undoDrawing() {
    try {
      Shapes.remove(Shapes.size() - 1); //remove the latter most object in the arraylist
    } catch (Exception e) {
      //in case you try to break my program by clicking undo when nothing's left
      System.out.println("No drawings left"); 
    }
  }

  // called when the clear button is pressed
  public void clearDrawing() {
    Shapes.clear(); //clear the whole arrayList
  }

  /*
   * the painthandler; this is where the bulk of the program is. All the logic,
   * drawing, moving, and personal suffering goes down here
   */
  private class PaintHandler implements MouseListener, MouseMotionListener {

    int xCord = 0; //local storage for the xCord
    int yCord = 0; //local storage for the yCord

    int oldStartX = 0; //local storage for the old starting point
    int oldStartY = 0; //local storage for the old starting point in the yCord

    boolean attemptMoving = false; //boolean for if a move was attempted
    boolean isMoving = false; //boolean for if a move was successful

    int drawingCount = 0; //counter to know which drawing in the arraylist we're talking about

    @Override
    public void mousePressed(MouseEvent e) {
      //when you click LMB aka you want to draw
      if (e.getButton() == MouseEvent.BUTTON1) {
        doneDrawing = false; //you haven't finished drawing yet since you just started!
        attemptMoving = false; //and you're not moving the drawing cause you're drawing it!
        isMoving = false;

        //updates currentShape by creating a new drawing object
        currentShape = new Drawing(ButtonPanel.getColor(), ButtonPanel.getShape(), ButtonPanel.getFilled());

        startX = 0;
        startY = 0;
        startX = e.getX(); //stores startX to the xCord of the mouse click
        oldStartX = startX; //and saves it to the oldStartX for later use
        startY = e.getY(); //similarly for the yCord
        oldStartY = startY;
        //sets startX, startY. Also sets endX and endY just for initialization sake
        currentShape.setStartX(startX);
        currentShape.setStartY(startY);
        currentShape.setEndX(startX);
        currentShape.setEndY(startY);

        //when you click RMB aka you want to move a drawing
      } else if (e.getButton() == MouseEvent.BUTTON3) {

        attemptMoving = true; //you are attempting to move
        isMoving = false; //but you're not moving quite yet
        //initialization; just incase the move fails, we're not referencing and actaul object in the arraylist
        drawingCount = -1; 
        
        //booleans for whether you clicked on the object or ot
        boolean xTrue;
        boolean yTrue;

        //loop through the arraylist for each drawing
        for (Drawing currentDrawing : Shapes) {
          xTrue = false;
          yTrue = false;
          
          if (currentDrawing.getShape().equals("Edge")) {
            
            //checks whether the click is within an imaginary rectangle enclosing the line
            if (currentDrawing.getStartX() < currentDrawing.getEndX()) {
              xTrue = (currentDrawing.getStartX() * 0.9 <= e.getX() && e.getX() <= currentDrawing.getEndX() * 1.1);
            } else {
              xTrue = (currentDrawing.getStartX() * 1.1 >= e.getX() && e.getX() >= currentDrawing.getEndX() * 0.9);
            }
            if (currentDrawing.getStartY() < currentDrawing.getEndY()) {
              yTrue = (currentDrawing.getStartY() * 0.9 <= e.getY() && e.getY() <= currentDrawing.getEndY() * 1.1);
            } else {
              yTrue = (currentDrawing.getStartY() * 1.1 >= e.getY() && e.getY() >= currentDrawing.getEndY() * 0.9);
            }
            
            /*checks if the slope between the click and a point on the line are similar.
             * The if statements are for vertical or horizontal lines, as the math gets sensitive for
             * very high or low values
             */
            double slope = ((double) currentDrawing.getEndY() - (double) currentDrawing.getStartY())
                / ((double) currentDrawing.getEndX() - (double) currentDrawing.getStartX());
            if (Math.abs(slope) > 50 || slope == Double.POSITIVE_INFINITY || slope == Double.NEGATIVE_INFINITY) {
              slope = 51;
            } else if (Math.abs(slope) < 0.1 || slope == 0) {
              slope = 0.9;
            }

            double testSlope = ((double) e.getY() - (double) currentDrawing.getStartY())
                / ((double) e.getX() - (double) currentDrawing.getStartX());
            if (Math.abs(testSlope) > 50 || testSlope == Double.POSITIVE_INFINITY || testSlope == Double.NEGATIVE_INFINITY) {
              testSlope = 51;
            } else if (Math.abs(testSlope) < 0.1 || testSlope == 0) {
              testSlope = 0.9;
            }
            
            //a margin of error, because the user likely isn't pixel-perfect
            double error = Math.abs(0.15 * slope); 

            //if the percent difference is small enough, and it's within the rectangle, it was clicked on!
            if ((Math.abs((slope - testSlope) / slope) <= error) && (xTrue == true && yTrue == true)) {
              isMoving = true;
              drawingCount = Shapes.indexOf(currentDrawing);
            }
            
          } else {
            //if it's a filled shape, just check if the click is within the imaginary (or real) rectangle
            if (currentDrawing.isFilled() == true) {
              xTrue = (e.getX() >= currentDrawing.getStartX() && e.getX() <= currentDrawing.getEndX());
              yTrue = (e.getY() >= currentDrawing.getStartY() && e.getY() <= currentDrawing.getEndY());
              if (xTrue == true && yTrue == true) {
                isMoving = true;
                drawingCount = Shapes.indexOf(currentDrawing);
              }

            } else {
              //if unfilled, things get a little tricky-er
              if (currentDrawing.getShape().equals("Rectangle")) {

                /*at this point, it's 11:53 pm and the assignment is about to close. I don't have anymore 
                 * time to comment out the rest of the program. I hope the remainder of the program isn't
                 * too hard to follow. -Anonymous person
                 */
                
                
                xTrue = (((e.getX() >= currentDrawing.getStartX() - 10 && e.getX() <= currentDrawing.getEndX() + 10)
                    && (e.getY() >= currentDrawing.getStartY() - 10 && e.getY() <= currentDrawing.getStartY() + 10))
                    || (e.getX() >= currentDrawing.getStartX() - 10 && e.getX() <= currentDrawing.getEndX() + 10
                        && e.getY() >= currentDrawing.getEndY() - 10 && e.getY() <= currentDrawing.getEndY() + 10));

                yTrue = ((e.getY() >= currentDrawing.getStartY() - 10 && e.getY() <= currentDrawing.getEndY()
                    && e.getX() >= currentDrawing.getStartX() - 10 && e.getX() <= currentDrawing.getStartX() + 10)
                    || (e.getY() >= currentDrawing.getStartY() - 10 && e.getY() <= currentDrawing.getEndY()
                        && e.getX() >= currentDrawing.getEndX() - 10 && e.getX() <= currentDrawing.getEndX() + 10));

                if (xTrue == true || yTrue == true) {
                  isMoving = true;
                  System.out.println("rect confirmed!");
                  drawingCount = Shapes.indexOf(currentDrawing);
                  System.out.println(drawingCount);
                }
              } else {
                
                double xRad = (double)currentDrawing.getShapeWidth() / 2;
                double yRad = (double)currentDrawing.getShapeHeight() / 2;
                
                double xCen = currentDrawing.getStartX() + xRad;
                double yCen = currentDrawing.getStartY() + yRad;
                
                
                for (double par = 0; par < 360; par++) {
                  //System.out.println("T = " + par);
                  double parRad = Math.toRadians(par);
                  //System.out.println("T(rad) = " + parRad);
                  double xCord = (xRad * Math.cos(parRad)) + xCen;
                  xTrue = (xCord * 0.95 <= e.getX()  && e.getX() <= xCord * 1.05);
                  
                  double yCord = (yRad * Math.sin(parRad)) + yCen;
                  yTrue = (yCord * 0.95 <= e.getY() && e.getY() <= yCord * 1.05);
                  
                  //System.out.println("(" + xCord + ", " + yCord + ")");
                  if (xTrue == true && yTrue == true) {
                    System.out.println("Coordinate match!");
                    isMoving = true;
                    drawingCount = Shapes.indexOf(currentDrawing);
                    break;
                  }
                }
                
                System.out.println("xRad " + xRad + " yRad " + yRad);
                System.out.println("xCen " + xCen + " yCen " + yCen);
                
              }
            }
          }
        }

      } else {
        // do nothing
      }

    }

    @Override
    public void mouseDragged(MouseEvent e) {
      mouseMoved(e);

      if (isMoving == false && attemptMoving == false) {
        xCord = 0;
        yCord = 0;
        if (currentShape.getShape().equals("Edge")) {
          xCord = e.getX();
          currentShape.setEndX(xCord);
          currentShape.setShapeWidth(xCord - currentShape.getStartX());
          yCord = e.getY();
          currentShape.setEndY(yCord);
          currentShape.setShapeHeight(yCord - currentShape.getStartY());
        } else {
          xCord = e.getX() - oldStartX;
          currentShape.setEndX(e.getX());
          if (xCord < 0) {
            currentShape.setEndX(oldStartX);
            startX = e.getX();
            xCord = oldStartX - startX;
            currentShape.setStartX(startX);
          }
          yCord = e.getY() - oldStartY;
          currentShape.setEndY(e.getY());
          if (yCord < 0) {
            currentShape.setEndY(oldStartY);
            startY = e.getY();
            yCord = oldStartY - startY;
            currentShape.setStartY(startY);
          }
          currentShape.setShapeWidth(xCord);
          currentShape.setShapeHeight(yCord);
        }

        repaint();
      } else if (isMoving == true && attemptMoving == true) {
        startX = e.getX();
        startY = e.getY();
        xCord = Shapes.get(drawingCount).getShapeWidth();
        yCord = Shapes.get(drawingCount).getShapeHeight();
        try {
          Shapes.get(drawingCount).setStartX(startX);
          Shapes.get(drawingCount).setStartY(startY);
          Shapes.get(drawingCount).setEndX(startX + xCord);
          Shapes.get(drawingCount).setEndY(startY + yCord);
        } catch (Exception excep) {
          System.out.println("Moving nothing!");
        }

        repaint();
      }

      // System.out.println("" + endX + "," + endY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
      System.out.println("Released!");

      if (isMoving == false && attemptMoving == false) {
        Shapes.add(currentShape);
        doneDrawing = true;
      }

      // endX = 0;
      // endY = 0;
      /*
       * endX = e.getX() - startX; if (endX < 0) { endX = startX - e.getX();
       * startX = e.getX(); } endY = e.getY() - startY; if (endY < 0) { endY =
       * startY - e.getY(); startY = e.getY(); } repaint();
       * 
       */
      System.out.println("" + e.getX() + "," + e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
      xCord = e.getX();
      yCord = e.getY();
      Paint.position.setText("(" + xCord + ", " + yCord + ")");
    }

    @Override
    public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
      // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent arg0) {
      // TODO Auto-generated method stub
      Paint.position.setText("Mouse is out of Canvas!");
    }

  }

}
