package Lab5;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

class FileStats{
	private Scanner input; //scanner where will assign our file
	//list of all words in the file (duplicates included)
	private ArrayList <String> wordList=new ArrayList<String>(); 
	//set of all words in the file (duplicates not included)
	private HashSet <String> wordSet=new HashSet<String>(); 
	//list of all the non-duplicate words in the file, but as Entry objects with individual frequencies and words
	private ArrayList <Entry<String>> entryList=new ArrayList<Entry<String>>(); //list 

	private class Entry <T> implements Comparable<Entry<T>>{
		public T word; //the string of the current entry
		public int frequency; //the frequency of the current entry
		public Entry(T word, int f){
			this.word=word;
			frequency=f;
		}
		
		//compares the frequency of the current object to whatever entry is passed in; is auto-called by sort
		public int compareTo(Entry<T> e){
      
		  int frequency2 = e.frequency; //frequency of the entry being compared
		  
		  if (this.frequency > frequency2) {
		    return 1; //shows frequency of the this.word is higher
		  } else if (this.frequency < frequency2) {
		    return -1; //shows frequency of the word being compared to is higher
		  } else {
		    return 0; //shows both words have the same frequency
		  }
      
		}
	}

	public FileStats(String path) {
	  
	  String line; //the current line of text
	  String word; //the current word in the line

		/* open the file, named path */
	  
	  /* checks if the file exists; if it doesn't, end the program*/
	  try {
	    input = new Scanner(new File(path)); //sets the scanner to the file determined by the path
	  } catch (FileNotFoundException e) {
	    System.out.println("Error opening file");
	    System.exit(1);
	  }
	  
	  /* reads through the file, and throws an exception once the whole file is read*/
	  try {
	    
	    /* while lines still exist, loop through the file */
      while ((line = input.nextLine()) != null) {
        /* initialize tokenizer to the current "line" string; the string argument is used to determine which chars
         * to consider as delimiters; that is, when to stop reading a word and start reading a new word
         * The false arg is used to not count said delimiters in the string
        */
        StringTokenizer st = new StringTokenizer(line," \n\t\r\f.,;",false); 
        while (st.hasMoreTokens()) { //checks if more strings remain in the line
          word = st.nextToken(); //assign word to the next token
          word = word.toLowerCase(); //lower case the word, so we don't have to deal with capitalization
          wordList.add(word); //adds the current tokenized string to the list
          wordSet.add(word); //adds the current tokenized string to the set
          
          //System.out.println(word); for debugging purposes; prints out the text file
        }
	    }
	    
	  } catch (NoSuchElementException e) {
	    //once it reaches here, no lines remain
	    //so just don't do anything
	  }

		count(); //calls count once wordList and wordSet are fully established
	}

	/*
	 * This method is supposed to
	 * 1. find the frequency of each word in the file.
	 * 2. display the four most frequent words and their frequencies.
	 */
	private void count() {
	  Entry currentEntry; //entry object based on the current word used
	  int count; //used to count frequency
	  Iterator <String> wordIterator = wordSet.iterator(); //iterator to iterate through wordSet
	  while (wordIterator.hasNext()) {
	    //creates a string, which is assigned to the current word that the iterator points to
	    String currentWord = wordIterator.next(); 
	    count = Collections.frequency(wordList, currentWord); //gets the frequency of the word from wordList
	    
	    //creates an entry object with the word and count assigned above
	    currentEntry = new Entry(currentWord,count);
	    entryList.add(currentEntry); //adds the current entry to the entry list
	  }
	  
	  /*sorts entryList. We call reverseOrder since we want it in descending order*/
	  Collections.sort(entryList, Collections.reverseOrder());
	  
	  /*prints out the four most common words */
    for (int i = 0; i < 4; i++) {
      System.out.println(entryList.get(i).word + " appears " + entryList.get(i).frequency + " time(s).");
    }
	}
}

public class FileIO {
	public static void main(String args[]) throws IOException{
	  /*!!!!IMPORTANT!!!!! README!!!!!
	   * I moved the text file to the src folder so that I can zip just the source code and not the 
	   * whole project. If for whatever reason, the file isn't opening, just 
	   * remove the "src/" in the parameter and move the txt.file to the project folder
	   */
		FileStats fs=new FileStats("src/Data/basketball.txt"); //creates a fileStat object with the basketBall file
	}
}
