package HW3;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Paint extends JFrame {

  ButtonPanel options = new ButtonPanel(); //creates the options panel

  Canvas canvas = new Canvas(); //creates the canvas, where all the drawing happens

  //creates the status bar, showing the coordiantes of the mouse, relative to canvas
  public static JLabel position = new JLabel("(" + Integer.toString(0) + "," + Integer.toString(0) + ")");

  public Paint() {
    super("Paint");
    setLayout(new BorderLayout());
    add(canvas, BorderLayout.CENTER); //the canvas is located in the center

    add(position, BorderLayout.SOUTH); //the position in the south

    add(options, BorderLayout.NORTH); //and the options in the north

  }

  public static void main(String args[]) {

    /* basic gui initializing stuff */
    Paint paintObj = new Paint();
    paintObj.setDefaultCloseOperation(EXIT_ON_CLOSE);
    paintObj.setSize(800, 600);
    paintObj.setResizable(true);
    paintObj.setVisible(true);

  }

}
