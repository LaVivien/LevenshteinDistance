import java.util.*;     // For Maps and Sets
import java.io.*;       // For the file

/**
 * This is the driver class for the Find Levenshtein Distance program.
 * This program will load the dictionary file, find the two target words
 * that the program is working to find a path from, 
 * and the give those to the solver class that the students will need to 
 * write.   
 */
public class Main
{
   private Map<Integer,Set<String>> masterDictionary;
 
   /**
    * Main program that creates an instance of the LevenDistanceFinder 
    * program and runs a copy.
    */ 
   public static void main(String[] args)
   {
      Main program = new Main();
      program.run();

   }
   
   /**
    * This method runs the main program instance, and kicks off the user
    * input and data file collection routines.
    */
    
   public void run()
   {
       masterDictionary = createMap("dictionary.txt");

       Set<String> smallDictionary;
       WordPair path;   
       path = getStartingWords();
       smallDictionary = getSmallDict(path, masterDictionary);
            
       // This is the line that creates the student
       // Generated code and gives it the necessary methods     
       LevenshteinFinder myMain = new LevenshteinFinder(path.to, path.from, smallDictionary);
    
       // From the student class, we ask the class for the 
       // distance that it found, and the path that goes with it.
       int dist = myMain.getDistance();
       String thePath = myMain.getPath();
       
       // Print out the results
       System.out.println("The distance between your words is " + dist);
       System.out.println("The path between your words is : " + thePath);
   }
   
   // Private method, so no java doc,
   // But this method takes the large dictionary and returns the 
   // particular set that the user needs based upon the size of the inputs
   private Set<String> getSmallDict(WordPair p, Map<Integer,Set<String>> master)
   {
      return master.get(p.to.length() );
   }
   
   
   // Private method
   // This method is a replacement for Scanner.next() that has a side
   // benefit of checking that the word is in the dictionary.
   // Note it also forces a lower case onto the word
   private String getWordInDictionary(Scanner x)
   {
      System.out.print("--->");
      String w = x.next().toLowerCase();
      while (!masterDictionary.get(w.length()).contains(w) )
      {
        System.out.println("Please type in a word in the dictionary");
        System.out.print("--->");
        w = x.next().toLowerCase();
      }
      return w.toLowerCase();
   }
   
   // Private Method
   // Returns a small data structure that contains the two words
   // that are of interest.  The word to start from, and the word
   // to end with.
   private WordPair getStartingWords()
   {
      Scanner keyboard = new Scanner(System.in);
      WordPair x = new WordPair();
      System.out.println("* Welcome to the Levenshtein Levenshtein Edit distance solver   *");
      System.out.println("* Please type in two words of the same size that will be used   *");
      System.out.println("* To find a path between them.                                  *");
      System.out.println("*****************************************************************");
      System.out.println();
      System.out.println("What word would you like to start with?");
      x.from = getWordInDictionary(keyboard);
      System.out.println("What word would you like to end with?");
      x.to = getWordInDictionary(keyboard);
      
      while (x.from.length() != x.to.length())
      {
         System.out.println("******");
         System.out.println("Please make sure the two words are the same size!"); 
         System.out.println("What word would you like to start with?");
         x.from = getWordInDictionary(keyboard);
         System.out.println("What word would you like to end with?");
         x.to = getWordInDictionary(keyboard);
      }
      return x;      
   }

   // Private Method
   // This method opens up the data file, and reads all the words from
   // the dictionary file and sorts them into sets of the correct size.
   // From this dictionary the final group of dictionary words is found.   
   private Map<Integer,Set<String>> createMap(String fname)
   {
      File mainDictionary = new File(fname);
      Scanner fileRead = null;
      try{
         fileRead = new Scanner(mainDictionary);
      }
      catch (Exception e)
      {  
         System.out.println("Dictionary File not found.  Please locate file");
         System.out.println("    Error -- " + e);
         System.exit(-1);
      }
      
      Map<Integer,Set<String>> wordGroups = new TreeMap<>();
      while (fileRead.hasNext())
      {
         String w = fileRead.next();
         int size = w.length();
         if (!wordGroups.containsKey(size))
            wordGroups.put(size,new TreeSet<String>());
         wordGroups.get(size).add(w);   
      }

      return wordGroups;
   }
   
   // This is a private class that is acting like a struct from c++
   // used to hold a pair of words. 
   private class WordPair
   {
      public String from;
      public String to;
   }
}