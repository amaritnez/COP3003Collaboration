package HW4;
import java.io.*;
import java.util.*;

class FileStats{
	private Scanner input; //scanner for the initial file
	private ArrayList <String> wordList=new ArrayList<String>(); //list of words in basketball
	private HashSet <String> wordSet=new HashSet<String>(); //dup-protected set of words in basketball
	private ArrayList <Entry<String>> entryList=new ArrayList<Entry<String>>(); //list of words w/ freq. and word
	private Map <String, Character> dictionary=new HashMap<String, Character>(); //initial dictionary

	private class Entry <T> implements Comparable<Entry<T>>{
		public T word; //the string of the current entry
		public int frequency; //the frequency of the current entry
		public Entry(T word, int f){
			this.word=word;
			frequency=f;
		}
		public int compareTo(Entry<T> e){
      int frequency2 = e.frequency; // frequency of the entry being compared

      if (this.frequency > frequency2) {
        return 1; // shows frequency of the this.word is higher
      } else if (this.frequency < frequency2) {
        return -1; // shows frequency of the word being compared to is higher
      } else {
        return 0; // shows both words have the same frequency
      }
		}
	}

	public FileStats(String path) {
	  
	  String line;
	  String word;

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

		count();
	}

	/*
	 * This method is supposed to
	 * 1. find the frequency of each word in the file.
	 * 2. display the four most frequent words and their frequencies.
	 * 3. construct the dictionary that four key-value pairs. The keys
	 *    are the most frequent words and the values are the characters,
	 *    used to represent the words.
	 */
	private void count() {
		/* insert your code here */
	  
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
    
    /* special symbols for the most frequent words.
     * # is for the most frequent
     * $ is for the second most frequent
     * * is for third, and % for fourth
     */
    char[] specialSymbols =  {'#', '$', '*', '%'};
    
    /*prints out the four most common words, and assigns them to a special char */
    for (int i = 0; i < 4; i++) {
      System.out.println(entryList.get(i).word + " appears " + entryList.get(i).frequency + " time(s).");
      dictionary.put(entryList.get(i).word, specialSymbols[i]);
    }
	}

	//returns the dictionary
	public Map<String, Character> getDictionary(){
		return dictionary;
	}
	
	//reverses the map that is passed in, and returns the reversed map
	@SuppressWarnings("null")
  public Map<Character, String> reverseDictionary(Map <String, Character> initial) {
	  Map <Character, String> reversed = new HashMap<Character, String>();
	  for (int i = 0; i < 4; i++) {
		  reversed.put((Character) initial.get(entryList.get(i).word), entryList.get(i).word);
		}
    return reversed;
	  
	}
	
}

class FileCompressor{
	public static void compress(String src, String dest,
			Map<String, Character> dictionary){

	  Scanner secondInput = null; //new input for source
	  String sLine; //source line
	  String sWord; //source word
	  
	  
	  
	  FileWriter fWriter = null; //our filewriter to pass into our buffwriter
    try {
      fWriter = new FileWriter(dest); //the path is what is passed in
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
	  
    BufferedWriter writer = new BufferedWriter(fWriter); //our bufferedwriter
    
		/* insert your code here */
	  try {
	    secondInput = new Scanner (new File(src)); //sets the new scanner to the source file
	  } catch (Exception e) {
	    System.out.println("Error opening file");
	    System.exit(1);
	  }
	  
	  try {
	    
	    while ((sLine = secondInput.nextLine()) != null) { //while there are still lines to read
	      
        /*
         * set up a new tokenizer. This time around we set the bool to true
         * since we want to include punctuation this time around
         */
	      StringTokenizer stT = new StringTokenizer(sLine, " \n\t\r\f.,;", true);
	      while (stT.hasMoreTokens()) { //while there are still words in the line
	        
	        sWord = stT.nextToken(); //assign the new word
	        sWord = sWord.toLowerCase(); //lower case the word for my own sanity
	        if (dictionary.containsKey(sWord)) {
            /*
             * if a word matches a word in the dictionary, write the value that
             * word maps to
             */
            writer.write(dictionary.get(sWord));
          } else {
            writer.write(sWord); //otherwise, just write the word
          }
	      }
	      writer.write("\r\n"); //once a line is used up, start a new paragraph in the file
	    }
	    
	  } catch (Exception e) {
      /*
       * because java, we need to flush inside another try-catch. The flush is
       * so that our buffwriter actually writes to the file
       */
	    try {
        writer.close();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
	  }

	}

	public static void decompress(String src, String dest,
			Map<Character, String> reversed){

		/* insert your code here */
	  Scanner thirdInput = null; //new input for source
    String tLine; //source line
    String tWord; //source word
    
    
    
    FileWriter fWriter = null; //our new filewriter to pass into our buffwriter
    try {
      fWriter = new FileWriter(dest); //the path is what is passed in
    } catch (IOException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    
    BufferedWriter tWriter = new BufferedWriter(fWriter); //our second bufferedwriter
    
    /* insert your code here */
    try {
      thirdInput = new Scanner (new File(src)); //sets the new scanner to the source file
    } catch (Exception e) {
      System.out.println("Error opening file");
      System.exit(1);
    }
    
    try {
      
      while ((tLine = thirdInput.nextLine()) != null) { //while there are still lines to read
        
        /*
         * set up a new tokenizer. This time around we set the bool to true
         * since we want to include punctuation this time around
         */
        StringTokenizer stTh = new StringTokenizer(tLine, " \n\t\r\f.,;", true);
        while (stTh.hasMoreTokens()) { //while there are still words in the line
          
          tWord = stTh.nextToken(); //assign the new word
          tWord = tWord.toLowerCase(); //lower case the word for my own sanity
          if (reversed.containsKey(tWord.charAt(0))) {
            /*
             * if a word matches a word in the dictionary, write the value that
             * word maps to
             */
            tWriter.write(reversed.get(tWord.charAt(0)));
          } else {
            tWriter.write(tWord); //otherwise, just write the word
          }
        }
        tWriter.write("\r\n"); //once a line is used up, start a new paragraph in the file
      }
      
    } catch (Exception e) {
      /*
       * because java, we need to flush inside another try-catch. The flush is
       * so that our buffwriter actually writes to the file
       */
      try {
        tWriter.close();
      } catch (IOException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
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
	  
		FileStats fs=new FileStats("src/Data/basketball.txt");
		System.out.println(fs.getDictionary()); //print the dictionary in the console so you know what is what

		Map <String, Character> m1=fs.getDictionary();
		FileCompressor.compress("src/Data/basketball.txt", "src/Data/compressed.txt",m1); //compress the file

		/* create another dictionary for decompress and name it m2 */
		Map <Character, String> m2 = fs.reverseDictionary(m1); //m2 is just m1, but reversed
		
		//decompress the file
		FileCompressor.decompress("src/Data/compressed.txt", "src/Data/decompressed.txt",m2);
	}
}
