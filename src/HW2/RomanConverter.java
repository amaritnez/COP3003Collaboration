package HW2;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class RomanConverter extends JFrame {
  
  JLabel arabicLabel = new JLabel("Arabic Numural"); //label for arabic side
  JLabel romanLabel = new JLabel("Roman Numural"); //label for roman side
  
  JTextField arabicText = new JTextField(); //arabic text field
  JTextField romanText = new JTextField(); //roman text field

  //constructor for gui
  public RomanConverter() {
    super("Arabic <--> Roman");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(300, 100);
    this.setResizable(false);
    this.setVisible(true);
    setLayout(new GridLayout(2,2));
    
    //creates the main keylistener for the gui, then adds it to our text fields
    ConverterHandler conversion = new ConverterHandler(); 
    arabicText.addKeyListener(conversion);
    romanText.addKeyListener(conversion);
    
    //adds our labels and text fields to the gui
    this.add(arabicLabel);
    this.add(arabicText);
    this.add(romanLabel);
    this.add(romanText);
    
  }

  //the main handler for our gui
  private class ConverterHandler implements KeyListener {
    
    String romanBefore = new String(); //the saved string from the roman field
    String romanAfter = new String(); //the newly created string from the roman field
    
    boolean invalid = false; //determines whether an input is valid or not
    boolean isEmpty = true; //determines whether the field is empty or not
    
    char text[]; //char array of the entered text
    
    int arabicValue = 0; //the saved arabic value
    int arabicBefore = 0; //the newly created arabic value

    @Override
    public void keyPressed(KeyEvent event) {
      //does nothing, but we have to implement this method anyways
    }

    //bread and butter of the keylistener: is what makes the conversions possible
    @Override
    public void keyReleased(KeyEvent event) {
      //handler for the arabicText
      if (event.getSource() == arabicText) {
        /* debugging purposes only
        System.out.println();
        System.out.println("Before: " + arabicValue);
        */
        
        arabicValue = 0; //defaults value to 0
        
        /* takes the integer value of the text. If valid, it's converted. If
         * not, an exception is thrown
         */
        try {
          arabicValue = Integer.valueOf(arabicText.getText());
          if (arabicValue <= 0 || (arabicText.getText().charAt(0) == '0') || arabicValue > 3999) {
            throw new Exception(); //if it's less than 0, has a leading 0, or is greater than 3999
          }
          invalid = false; //if it made it this far, then it must be a valid number
          isEmpty = false; //and it must not be empty
          arabicBefore = arabicValue;
        } catch (Exception e) {
          invalid = true; //if an exception is caught, the text must be invalid
          if (arabicText.getText().equals("")) {
            isEmpty = true; //if an exception is thrown because the field is empty, then this is used
          }
          if (isEmpty == true) { //if the field was made empty, or was still empty after an invalid value
            arabicText.setText(""); //sets the arabic text to nothing because we entered nothing
            romanText.setText(""); //same for the roman text
          } else {
            //otherwise, just change the field to the previous valid number
            arabicText.setText(Integer.toString(arabicBefore));
          }
        }
        
        //System.out.println("After: " + arabicValue); more debuggin stuff
        
        //if the number is valid, convert it to it's roman counterpart, and set it to the roman text
        if (invalid == false) {
          romanBefore = romanFormat(arabicValue);
          romanText.setText(romanBefore);
        }
        
        
        //handler for the roman text
      } else {
        
        /* debugging stuff for roman text, for your convience
        System.out.println();
        System.out.println("Before: " + romanBefore);
        */
        
        //gets the newly entered text and sets our string to reference it
        romanAfter = romanText.getText();
        
        text = romanAfter.toCharArray(); //converts our string to a char array
        arabicValue = 0; //sets arabic value to 0 by default
        invalid = false; //similar reasoning for invalid
        
        //goes through each char in our array and sees what number to use
        for (int i = 0; i < text.length; i++) {

          char letter = text[i]; //reference for a specific char in our array

          //different actions for any possible char we could get
          switch (letter) {

            case 'i':
              //follow through to the capital I

            case 'I':
              arabicValue += 1; //add one to our arabic value because I is 1
              break;

            case 'v':
              //follow through to the capital V

            case 'V':
              //if our V is preceeded by an I, then it's actually 4
              if (i != 0 && (text[i - 1] == 'i' || text[i - 1] == 'I')) {
                arabicValue--; //subtract one to take into account the I before it
                arabicValue += 4; //then add 4 to arab value
              } else {
                arabicValue += 5; //otherwise, if it's a normal V, just add 5
              }
              break;

            case 'x':
              //follow through for capital X

            case 'X':
              if (i != 0 && (text[i - 1] == 'i' || text[i - 1] == 'I')) {
                arabicValue--; //similarly, if there's an I before it, this number is 9
                arabicValue += 9;
              } else {
                arabicValue += 10; //otherwise, it's 10
              }
              break;

            case 'l':
              //follow through for capital L

            case 'L':
              if (i != 0 && (text[i - 1] == 'x' || text[i - 1] == 'X')) {
                arabicValue-=10; //same reasoning, but subtract ten since we're look at previous X
                arabicValue += 40; //then add 40
              } else {
                arabicValue += 50; //otherwise it's 50
              }
              break;

            case 'c':
              //follow through for capital C

            case 'C':
              if (i != 0 && (text[i - 1] == 'x' || text[i - 1] == 'X')) {
                arabicValue-=10; //same reasoning
                arabicValue += 90;
              } else {
                arabicValue += 100;
              }
              break;

            case 'd':
              //follow through for D

            case 'D':
              if (i != 0 && (text[i - 1] == 'c' || text[i - 1] == 'C')) {
                arabicValue-=100;
                arabicValue += 400;
              } else {
                arabicValue += 500;
              }
              break;

            case 'm':

            case 'M':
              if (i != 0 && (text[i - 1] == 'c' || text[i - 1] == 'C')) {
                arabicValue-=100;
                arabicValue += 900;
              } else {
                arabicValue += 1000;
              }
              break;

            default: //if it reaches the default case, then it's not a valid char
              invalid = true; //so then set invalid to true
              break;
          }
        }
        
        //System.out.println("The total value is: " + arabicValue); additional debugging
        
        if (arabicValue > 3999) {
          invalid = true; //in case our roman numeral converts to being greater than 3999, make it invalid
        }
        
        if (event.getKeyCode() == KeyEvent.VK_LEFT || event.getKeyCode() == KeyEvent.VK_RIGHT) {
          //the way the code is set up, left and right arrow keys are considered invalid.
          //this makes sure they are considered valid, but don't actually change anything
          invalid = false;
        }
        
        if (invalid == true) {
          //if the numeral is invalid, change the text to the previously valid text
          romanText.setText(romanBefore);
          //System.out.println("After: " + romanBefore); debugging stuff
        } else {
          isEmpty = false; //if it's valid, it has to be not empty
          //System.out.println("After: " + romanAfter);
          arabicBefore = arabicValue; //changes our saved number to the now confirmed valid number
          arabicText.setText(Integer.toString(arabicValue)); //changes the arabic text to the accepted value
          romanAfter = romanFormat(arabicValue); //format the roman number to match proper rules
          romanBefore = String.valueOf(romanAfter); //changes our saved string to our now accepted string
          
          if (event.getKeyCode() != KeyEvent.VK_LEFT && event.getKeyCode() != KeyEvent.VK_RIGHT) {
            /*if our valid event isn't just moving the insert position around via
             *arrow keys, then this just changes the roman text to the formatted string
             *created via romanFormat so that the text follows roman rules, even if the user doesn't
             */
            romanText.setText(romanBefore);
          }
        }
        
        //if the text is empty, set isEmpty to true, and adjust the arabic text accordingly
        if (romanText.getText().equals("")) {
          isEmpty = true;
          arabicText.setText("");
        }
        
      }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
      //does nothing, but we need to implement this method anyway, so yeah....
    }
    
    //formats the passed number into a valid roman numeral. That numeral is then returned as a string
    public String romanFormat(int value) {
      //simple placeholder names for the specific roman values
      final int I = 1;
      final int V = 5;
      final int X = 10;
      final int L = 50;
      final int C = 100;
      final int D = 500;
      final int M = 1000;
      
      //counter for the amount of times a specific char appears
      int numeralCount = 0;
      
      //stringbuilder which we use to edit our formatted numeral
      StringBuilder formatNumeral = new StringBuilder();
      
      
      //divides value by M. Due to int division, we can ignore the remainder and focus on the quotient
      numeralCount = value / M ;
      
      for (int a = 0; a < numeralCount; a++) {
        formatNumeral.append("M"); //for every m, add an "M" to the stringbuilder
      }
      
      
      numeralCount = value % M; //Value mod M; returns the remainder
      numeralCount = numeralCount / C; //then divide the remainder by C to get the number of 100s via int division
      
      //depending on the number of 100s encounter, add a certain amount of "C"s
      switch (numeralCount) {
        
        case 1:
          formatNumeral.append("C");
          break;
          
        case 2:
          formatNumeral.append("CC");
          break;
          
        case 3:
          formatNumeral.append("CCC");
          break;
          
        case 4:
          formatNumeral.append("CD");
          break;
          
        case 5:
          formatNumeral.append("D");
          break;
          
        case 6:
          formatNumeral.append("DC");
          break;
          
        case 7:
          formatNumeral.append("DCC");
          break;
          
        case 8:
          formatNumeral.append("DCCC");
          break;
          
        case 9:
          formatNumeral.append("CM");
          break;
          
        default:
          //this is for when no 100s are present i.e. think a number like 3010
          break;
      }
      
      //we do a similar process for 10; take the value mod C (100), then int divide the remainder by 10
      numeralCount = value % C;
      numeralCount = numeralCount / X;
      
      //similarly, we add different char combinations based on the number of 10s encountered
      switch (numeralCount) {
        
        case 1:
          formatNumeral.append("X");
          break;
          
        case 2:
          formatNumeral.append("XX");
          break;
          
        case 3:
          formatNumeral.append("XXX");
          break;
          
        case 4:
          formatNumeral.append("XL");
          break;
          
        case 5:
          formatNumeral.append("L");
          break;
          
        case 6:
          formatNumeral.append("LX");
          break;
          
        case 7:
          formatNumeral.append("LXX");
          break;
          
        case 8:
          formatNumeral.append("LXXX");
          break;
          
        case 9:
          formatNumeral.append("XC");
          break;
          
        default:
          break;
      }
      
      //for number of 1s, we just do value mod X (10). The remainder is the number of 1s
      numeralCount = value % X;
      
      //again switch for different number of 1s encountered
      switch (numeralCount) {
        
        case 1:
          formatNumeral.append("I");
          break;
          
        case 2:
          formatNumeral.append("II");
          break;
          
        case 3:
          formatNumeral.append("III");
          break;
          
        case 4:
          formatNumeral.append("IV");
          break;
          
        case 5:
          formatNumeral.append("V");
          break;
          
        case 6:
          formatNumeral.append("VI");
          break;
          
        case 7:
          formatNumeral.append("VII");
          break;
          
        case 8:
          formatNumeral.append("VIII");
          break;
          
        case 9:
          formatNumeral.append("IX");
          break;
          
        default:
          break;
      }
      //returns our now formatted roman numeral
      return formatNumeral.toString();
      
    }
  }
  
  //a lonely main method to do main method stuff....mainly
  public static void main(String args[]) {
    RomanConverter hw2 = new RomanConverter();
  }
  
}
