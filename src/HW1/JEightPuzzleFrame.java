package HW1;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class JEightPuzzleFrame extends JFrame {

  private static String linkToImage; //used to save pathofImage in class scope
  private ButtonHandler puzzleButton; //handler for the puzzle buttons

  private static int startX = 0; //starting x-coordinate for drawing image
  private static int startY = 0; //starting y-coordinate for drawing image

  private static int oWidth = 0; //used to store the original width of image
  private static int oHeight = 0; //used to store the original height of image

  private static double width = 0; //double variant of width used to calculate 1/3 of width
  private static double height = 0; //double variant of height used to calculate 1/3 of height

  private JButton[] puzzlePiece = new JButton[8]; //array of the puzzle buttons
  private JPanel emptyPanel = new JPanel(); //empty panel used for the non-puzzle piece
  private int[][] board = {
      { -1, 0, 1 }, 
      { 4, 5, 2 }, 
      { 3, 6, 7 }
  }; //the board values; -1 is for the empty panel
  private int[][] newBoard; //a new board, used once we start moving pieces around
  private int[][] solution = {
      { 0, 1, 2 },
      { 3, 4, 5 },
      { 6, 7, -1 }
  }; //solution board; this is used to store the "correct" positions

  
  /* the following was extracted by the IconButtonFrame.java class provided by Dr. Guo */
  private Icon extractIcon(String path) {
    // reads the image into a BufferedImage object
    BufferedImage image = null;
    try {
      image = ImageIO.read(new File(path));
      oWidth = image.getWidth();
      oHeight = image.getHeight();
      width = oWidth / 3.0;
      height = oHeight / 3.0;
    } catch (IOException e) {
      System.err.println("Image not found");
      System.exit(1);
    }

    // allocates another BufferedImage object whose size is
    // the same as the one of the wanted icon
    BufferedImage part = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_4BYTE_ABGR);

    // copies the data from "image" to "part"
    try {
      for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
          part.setRGB(x, y, image.getRGB(x + startX, y + startY));
        }
      }
      throw new Exception();
      /*when the height exceeds the width, an exception is thrown. Otherwise, an 
       * exception is thrown at the end of the loop. In the catch, startX is increased
       * for the next image part.
       */
    } catch (Exception e) {
      startX += (int) width;
      if (startX >= image.getWidth() - 1) { //if startX exceeds the actual width, we increase startY by the height
        startX = 0;
        startY += (int) height;
      }
    }

    // creates an icon whose content is already in "part"
    ImageIcon icon = new ImageIcon();
    icon.setImage(part);

    // returns to the caller
    return icon;
  }
  //creates the puzzle images
  public void createImage(ButtonHandler button, String pathOfImage) {
    /* loops through the button array, assigning parts of our image to each button */
    for (int i = 0; i < puzzlePiece.length; i++) {
      if (i == 8) {
        add(emptyPanel);
      } else {
        Icon puzzle = extractIcon((pathOfImage));
        puzzlePiece[i] = new JButton(puzzle);
        puzzlePiece[i].setSize(oWidth, oHeight);
        puzzlePiece[i].addActionListener(button);
      }
    }
    startX = 0; //sets the startX and startY to 0 due to the static nature of these variables for future boards
    startY = 0;

    //adds the puzzle pieces based on their ordered determined in "board"
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        if (board[row][col] == -1) {
          add(emptyPanel);
        } else {
          add(puzzlePiece[board[row][col]]);
        }
      }
    }
  }

  public JEightPuzzleFrame(String title, String pathOfImage) {
    super(title);
    setLayout(new GridLayout(3, 3));
    linkToImage = pathOfImage;

    puzzleButton = new ButtonHandler();
    createImage(puzzleButton, pathOfImage);
  }

  private class ButtonHandler implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
      int count = 0; //acts as both a counter for the loop and a means to identify puzzle pieces
      while (event.getSource() != puzzlePiece[count]) {
        count++;
      }
      //System.out.println(count);
      int xCord = 0; //xCord in relation to board
      int yCord = 0; //yCord in relation to board

      //searches for our piece in board
      for (int row = 0; row < board.length; row++) {
        for (int col = 0; col < board[row].length; col++) {
          if (board[row][col] == count) {
            yCord = row;
            xCord = col;
            break;
          }
        }
      }
      checkSurroundings(yCord, xCord); //finds the empty panel in relation to the current position
      /* performed once the puzzle is solved */
      if (checkSolution()) {
        //System.out.println("SUCCESS!!!!!!!!!!");
        /* pops up to alert the user they completed the puzzle */
        JOptionPane.showMessageDialog(JEightPuzzleFrame.this,
            String.format("Congratulations! You solved the puzzle!"));
        int[][] evenNewerBoard = {
            {4, 6, -1},
            {7, 5, 1},
            {2, 0, 3}
        };
        board = evenNewerBoard; //assigns our new board to board for recreation purposes
        createNewBoard(0, 0, 4);
        getContentPane().validate(); //updates the board
      }
    }
  }

  //used to find the empty panel
  private void checkSurroundings(int yCord, int xCord) {
    int direction = 0; //self-explanatory; 0 is right, 1 up, 2 left, 3 down, 4 none
    while (direction < 4) {//loops through the switch, trying each direction
      try {

        switch (direction) {

          case 0:
            if (board[yCord][xCord + 1] == -1) { //checks for the empty panel based on the current position
              //System.out.println("Right");
              
              /* creates a new board with the location of the empty panel and current
               * puzzle piece in mind.
               */
              createNewBoard(xCord + 1, yCord, board[yCord][xCord]);
              direction = 4; //at this point we found the empty panel, so set to 4
              break;
            }

          case 1:
            if (board[yCord - 1][xCord] == -1) {
              //System.out.println("Up");
              createNewBoard(xCord, yCord - 1, board[yCord][xCord]);
              direction = 4;
              break;
            }

          case 2:
            if (board[yCord][xCord - 1] == -1) {
              //System.out.println("Left");
              createNewBoard(xCord - 1, yCord, board[yCord][xCord]);
              direction = 4;
              break;
            }

          case 3:
            if (board[yCord + 1][xCord] == -1) {
              //System.out.println("Down");
              createNewBoard(xCord, yCord + 1, board[yCord][xCord]);
              direction = 4;
              break;
            }

          default: //resorts to default if we don't meet any cases i.e. the empty panel isn't near
            direction = 4;
            break;
        }
        throw new Exception(); //if the current case doesn't full-fill the if statement, go to the catch
      } catch (Exception e) {//used if the if-statement throws an exception, or is false
        direction++;
      }
    }
  }
  
  //used in creating new boards
  private void createNewBoard(int xCord, int yCord, int selectPiece) {
    newBoard = new int[3][3]; //self-explanatory; creates a new board
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        /* in the location of the empty panel, replace it with the selected
         * puzzle piece in the new board
         */
        if (row == yCord && col == xCord) {
          newBoard[row][col] = selectPiece;
        } else if (board[row][col] == selectPiece) {
          /* in the location of the selected puzzle piece, replace with the
           * empty panel in the new board
           */
          newBoard[row][col] = -1;
        } else {
          /* Otherwise, just copy the piece from the old board to the new */
          newBoard[row][col] = board[row][col];
        }
      }
    }
    
    /*
    for (int a = 0; a < newBoard.length; a++) {
      for (int b = 0; b < newBoard[a].length; b++) {
        System.out.println(newBoard[a][b]);
      }
    }
    */

    //starts to remove the buttons from our container
    for (int c = 0; c < puzzlePiece.length; c++) {
      remove(puzzlePiece[c]);
    }
    remove(emptyPanel);

    board = newBoard; //assigns the board to our values in new board
    newBoard = null; //and marks new board for garbage collection

    createImage(puzzleButton, linkToImage); //creates a new image based on the new board
    getContentPane().validate(); //updates the board
    
  }
  //checks if the board is right
  private boolean checkSolution() {
    boolean success = false;
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[row].length; col++) {
        if (board[row][col] != solution[row][col]) {
          return success; //if the current board and solution don't match up, return false
        }
      }
    }
    success = true;
    return success; //otherwise, return true
  }

  //main method; does main method stuff
  public static void main(String args[]) {
    //In the constructor, the image used can be change by changing "FGCU_logo.png" to the appropriate image
    JEightPuzzleFrame puzzleFrame = new JEightPuzzleFrame("Eight Puzzle", "src/Data/FGCU_logo.png");
    puzzleFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    puzzleFrame.setResizable(false);
    puzzleFrame.setSize(oWidth,oHeight); //sets size of the frame base on the size of the image
    puzzleFrame.setVisible(true);
  }

}
