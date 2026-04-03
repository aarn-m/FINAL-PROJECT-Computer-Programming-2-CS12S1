package morse;

import java.util.Scanner;

public class Translator {

	public Translator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// Ask user for text input
	    Scanner sc = new Scanner(System.in);

	    System.out.println("Enter String (textToMorse): ");
	    String text = sc.nextLine();
	   
	    // Initialize string array by translating textToMorse 
	    String[] morseTranslatedFromText = textToMorse(text);
	    
	    // Print the array with spaces in between
	    for (String s : morseTranslatedFromText)
	    {
	    	System.out.print(s + " ");
	    }
	    
		// Ask user for MorseCode input
	    System.out.println("\n");
	    System.out.println("Enter String (morseToText): ");
	    String morseCode = sc.nextLine();
	    
	    // Initialize char array by translating morseToText 
	    char[] textTranslatedFromMorse = morseToText(morseCode);
	    
	    // Print the text translated from morse
	    for (char c : textTranslatedFromMorse)
	    {
	    	System.out.print(c);
	    }
	    
	    
	    sc.close();
	}

	// Method to translate from Text to MorseCode
	// Returns a string array of MorseCode
	public static String[] textToMorse(String text)
	{
		// Create MorseMap object
		MorseMap morseMap = new MorseMap();
		
	    // Clean and convert the user input text into a 'character array'
		String cleanText = text.toUpperCase().replaceAll("[^A-Z0-9 .,?'():+\\-\"@!]", "").strip().replaceAll("\\s+", " ");
	    char[] textToCharArray = cleanText.toCharArray();
		
	    // Allocate an array where the translated text will be stored in
	    String[] translatedArray = new String[textToCharArray.length];
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'charArray'. Get the translation of the current character
	    // by using the textToMorse map
	    for (int i = 0; i < textToCharArray.length; i++)
	    {
	    	translatedArray[i] = morseMap.textToMorse.get(textToCharArray[i]);
	    }
	    
	    // Return the translated array
	    return translatedArray;
	}
	
	// Method to translate from MorseCode to Text
	// Returns a char array of Text
	public static char[] morseToText(String morseCode)
	{
		// Create MorseMap object
		MorseMap morseMap = new MorseMap();
		
	    // Clean and convert the user input MorseCode into a 'string array'
	    // by splitting up the text by whitespace
		// (Splitting the MorseCode by letter)
		String cleanMorseCode = morseCode.replaceAll("[^./\\- ]", "").strip().replaceAll("\\s*/\\s*", " / ").replaceAll("\\s+", " ");
	    // If user gave empty input (\n)
		if (cleanMorseCode.isEmpty()) {
		    return new char[0];
		}
		String[] morseCodeSplitBySpacing = cleanMorseCode.split("\\s+");
	    
	    // Allocate a character array where the translated text will be stored in
	    char[] translatedArray = new char[morseCodeSplitBySpacing.length];
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'morseCodeSplitBySpacing'. Get the translation of the current character
	    // by using the morseToText map
	    for (int i = 0; i < morseCodeSplitBySpacing.length; i++)
	    {
	    	translatedArray[i] = morseMap.morseToText.get(morseCodeSplitBySpacing[i]);
	    }
	    
	    // Return the translated array
	    return translatedArray;
	}
}
