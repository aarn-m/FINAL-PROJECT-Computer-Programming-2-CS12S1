package morse;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomWordGenerator {

	public RandomWordGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//        System.out.println(getRandomWordMedium());
//        System.out.println(getRandomWordShort());
	}
	
	private static final String FILE_NAME_MEDIUM = "google-10000-english-usa-no-swears-medium.txt";
	private static final String FILE_NAME_SHORT = "google-10000-english-usa-no-swears-short.txt";
    private static List<String> mediumWords = new ArrayList<>();
    private static List<String> shortWords = new ArrayList<>();
    private static final Random rand = new Random();
    
    public static void loadMediumWords() {
        if (!mediumWords.isEmpty()) return; // already loaded

		// Read from a list of the 10,000 most common English words in order of frequency, 
		// as determined by n-gram frequency analysis of the Google's Trillion Word Corpus.
		// https://github.com/first20hours/google-10000-english
        
        // Read each word from the file and add that to the ArrayList words
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME_MEDIUM))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                mediumWords.add(line.trim());
            }
        } catch (IOException e) {
            System.out.println("Error reading: " + FILE_NAME_MEDIUM);
            System.exit(1);
        }
    }
    
    public static void loadShortWords() {
    	if (!shortWords.isEmpty()) return; // already loaded
    	
    	// Read from a list of the 10,000 most common English words in order of frequency, 
    	// as determined by n-gram frequency analysis of the Google's Trillion Word Corpus.
    	// https://github.com/first20hours/google-10000-english
    	
    	// Read each word from the file and add that to the ArrayList words
    	try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME_SHORT))) 
    	{
    		String line;
    		while ((line = br.readLine()) != null) 
    		{
    			if (line.trim().length() == 4 || line.trim().length() == 3)
    			{
        			shortWords.add(line.trim());
    			}
    		}
    	} catch (IOException e) {
    		System.out.println("Error reading: " + FILE_NAME_SHORT);
    		System.exit(2);
    	}
    }

    public static String getRandomWordMedium() {
    	loadMediumWords();
//    	System.out.println("Size of mediumWords list: " + mediumWords.size());
        return mediumWords.get(rand.nextInt(mediumWords.size()));
    }
    
    public static String getRandomWordShort() {
    	loadShortWords();
//    	System.out.println("Size of shortWords list: " + shortWords.size());
    	return shortWords.get(rand.nextInt(shortWords.size()));
    }

}
