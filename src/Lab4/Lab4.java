package Lab4;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//ScreenSaver constructor and main method provided by Dr. Guo to test against
public class Lab4 extends JFrame {

  public Lab4() {
    super("Lab 4");
    ScreenSaver saver = new ScreenSaver();
    setLayout(new BorderLayout());
    add(saver, BorderLayout.CENTER);
  }

  public static void main(String args[]) {
    Lab4 lab4 = new Lab4();
    lab4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    lab4.setSize(600, 600);
    lab4.setResizable(true);
    lab4.setVisible(true);
  }
}
