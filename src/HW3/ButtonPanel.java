package HW3;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ButtonPanel extends JPanel {
  
  //array of all the color choices' names
  static String[] colors = {
      "Red",
      "Green",
      "Blue",
      "Black",
  };

  //array of all the shape choices' names
  static String[] shapes = {
      "Rectangle",
      "Oval",
      "Edge"
  };
  
  private static String currentShape = shapes[0]; //by default, assume the shape is a Rectangle
  private static String currentColor = colors[0]; //by default, assume the color is red
  private static boolean isFilled = false; //by default, assume the shape is unfilled

  private JButton undo = new JButton("Undo"); //the undo button
  private JButton clear = new JButton("Clear"); //the clear button
  
  private static JComboBox colorChoice = new JComboBox(colors); //Combo box for the colors
  private static JComboBox shapeChoice = new JComboBox(shapes); //Combo box for the shapes
  private JCheckBox filled = new JCheckBox("Filled"); //Checkbox for determining fill-ness
  
  private ButtonHandler optionSelect = new ButtonHandler(); //Buttonhandler for our buttons
  
  public ButtonPanel() {
    
    /* more general gui stuff */
    setLayout(new FlowLayout());
    
    undo.addActionListener(optionSelect);
    clear.addActionListener(optionSelect);
    add(undo);
    add(clear);
    colorChoice.addItemListener(optionSelect);
    shapeChoice.addItemListener(optionSelect);
    add(colorChoice);
    add(shapeChoice);
    filled.setHorizontalTextPosition(SwingConstants.LEFT);
    filled.addItemListener(optionSelect);
    add(filled);
    
  }
  
  
  private class ButtonHandler implements ItemListener, ActionListener {

    //called for the comboboxes and the checkbox
    @Override
    public void itemStateChanged(ItemEvent ie) {
      if (ie.getSource() == shapeChoice) {
        currentShape = ie.getItem().toString(); //change the current shape to the shape chosen
        repaint();
      } else if (ie.getSource() == colorChoice) {
        currentColor = ie.getItem().toString(); //change the current color to the color chosen
        repaint();
      } else if (ie.getSource() == filled) {
        if (filled.isSelected()) {
          isFilled = true; //if selected, enable is fill
        } else {
          isFilled = false; //don't otherwise
        }
      } else {
        //do nothing
      }
    }

    //used for the buttons
    @Override
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == undo) {
        (new Canvas()).undoDrawing(); //calls the undo drawing method in canvas
      } else if (e.getSource() == clear) {
        (new Canvas()).clearDrawing(); //calls the clear method in canvas
      }
    }
  }

  /* getters for the different statuses */
  public static String getShape () {
    return currentShape;
  }
  
  public static String getColor () {
    return currentColor;
  }
  
  public static boolean getFilled () {
    return isFilled;
  }
}
