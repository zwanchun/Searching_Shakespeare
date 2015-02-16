// This class builds an index for the input corpus.
package searching_shakespeare;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class ShakesEngine {
  
  // Read lines from the given file.
  public static ArrayList<String> readLinesFromFile(String path_to_file) 
      throws IOException {
    try (Scanner scanner = new Scanner(new File(path_to_file))) {
      
      // Create an ArrayList of String to store the lines. 
    	ArrayList<String> lines =new ArrayList<String>();

     
      // Read each line in the file in a loop using scanner's hasNextLine() and
      // nextLine() method. Add them to the ArrayList you created.
    	while(scanner.hasNextLine()){
    		lines.add(scanner.nextLine());
    	}
    		

      // Return the ArrayList.
    	return lines;

    }
  }

  // Preprocess each line -- remove punctuations.
  public static ArrayList<String> preprocessLines(ArrayList<String> lines) {
    ArrayList<String> processed_lines = new ArrayList<String>();
    for (String line : lines) {
      // Replace all the punctuation with empty strings.
      processed_lines.add(line.replaceAll("\\p{Punct}", "").toLowerCase());
    }
    return processed_lines;
  }
  
  // Build an index structrure on given lines. The index structure is a map from
  // words to indices of lines that contains the word. This function takes an
  // ArrayList of lines from the input and returns a hashmap. The keys of the
  // hashmap are unique words; the value for each key is an ArrayList whose
  // elements are the line numbers where the key occurs. At the end, the size of
  // each ArrayList should equal to the number of occurences of the key.
  public static HashMap<String, ArrayList<Integer>> buildIndex(ArrayList<String> lines) {
    
    // Create a hashmap whose key type is String and Value type is ArrayList of integer.
	  HashMap<String,ArrayList<Integer>> index=new HashMap<String, ArrayList<Integer>>();

    // Index each line.
    int line_num = 0;
    for (String line : lines) {
      // Create a scanner for this line;
      Scanner scanner = new Scanner(line);
      
     
      // Index each word by adding the line index to the ArrayList associated
      // to the word. The first time we see a word, we create an ArrayList for
      // it.
      while (scanner.hasNext()) {
        String word = scanner.next();

       
        // Create a new ArrayList if this is the first time we see this word.
        // You can use containsKey() method to check if a key exists.
        if(!(index.containsKey(word))){
      
        	ArrayList<Integer> line_numbers=new ArrayList<Integer>();
        	index.put(word, line_numbers);
        }
        
        // Add current line number to the ArrayList associated to this word.
        index.get(word).add(line_num);
      }
      // Increment the line number.
      ++line_num;
    }
    return index;
  }

  // The main method.
  public static void main(String[] args) throws IOException {
    // Check the total number of arguments.
    if (args.length != 2) {
      System.out.println("Please provide path_to_file and query word.");
      return;
    }
    // Parse arguments.
    String path_to_file = args[0];
    String query = args[1].toLowerCase(); 

    // Build index structure for the file.

    // Read all the lines in the given file
    ArrayList<String> original_lines = readLinesFromFile(path_to_file);

    // Preprocess each line.
    ArrayList<String> lines = preprocessLines(original_lines);

    // Build the index.
    HashMap<String, ArrayList<Integer>> index = buildIndex(lines);
   
    // Search for the query and print out results.
    // Check if the query is in the index. 
    if (index.containsKey(query)) {
      
      
      // Get the Arraylist corresponding to the query from the hashmap and name
      // it "retreived_lines"
    	ArrayList<Integer> result=new ArrayList<Integer>();
    	result=index.get(query);

      
      // Print out how many times the query word appeared in the text.
        System.out.println(result.size());
      
      //Print out each line which has the query word. Remember we have the
      // original lines stored in the arraylist "original_lines".
        for(int i=0;i<result.size();i++){
        	System.out.println("line "+result.get(i)+": "+ original_lines.get(result.get(i)));
        }
    } 
    else {
      System.out.format("%s appeared 0 times:%n", query);
    }
  }
}
