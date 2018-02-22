package HW3;
import java.awt.Color;

public class Drawing {
  
  Color realShapeColor;
  String shapeColor;
  
  String shapeType;
  
  boolean filled;
  
  int startX = 0;
  int startY = 0;
  int endX = 0;
  int endY = 0;
  int shapeWidth = 0;
  int shapeHeight = 0;
  
  public Drawing(String color, String shape, boolean fillness) {
    try {
      if (color.equals("Red")) {
        System.out.println("RED");
        realShapeColor = Color.RED;
      } else if (color.equals("Blue")) {
        System.out.println("BLU");
        realShapeColor = Color.BLUE;
      } else if (color.equals("Green")) {
        System.out.println("GRE");
        realShapeColor = Color.GREEN;
      } else {
        System.out.println("BLAAAA");
        realShapeColor = Color.BLACK;
      }
      
      if (shape.equals("Rectangle")) {
        System.out.println("RECT");
        shapeType = "Rectangle";
      } else if (shape.equals("Oval")) {
        System.out.println("OVA");
        shapeType = "Oval";
      } else {
        System.out.println("LIN");
        shapeType = "Edge";
      }
      
      if (fillness == true) {
        filled = true;
      } else {
        filled = false;
      }
    } catch (Exception e) {
      System.out.println("initializing");
    }
  }
  
  
  public String getShape () {
    return shapeType;
  }

  public Color getShapeColor() {
    return realShapeColor;
  }

  public int getStartX() {
    return startX;
  }


  public void setStartX(int startX) {
    this.startX = startX;
  }


  public int getStartY() {
    return startY;
  }


  public void setStartY(int startY) {
    this.startY = startY;
  }


  public int getEndX() {
    return endX;
  }


  public void setEndX(int endX) {
    this.endX = endX;
  }


  public int getEndY() {
    return endY;
  }


  public void setEndY(int endY) {
    this.endY = endY;
  }


  public int getShapeWidth() {
    return shapeWidth;
  }


  public void setShapeWidth(int shapeWidth) {
    this.shapeWidth = shapeWidth;
  }


  public int getShapeHeight() {
    return shapeHeight;
  }


  public void setShapeHeight(int shapeHeight) {
    this.shapeHeight = shapeHeight;
  }


  public boolean isFilled() {
    return filled;
  }


  public void setFilled(boolean filled) {
    this.filled = filled;
  }

  
  

  
  
}
