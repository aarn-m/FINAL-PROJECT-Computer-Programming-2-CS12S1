package morse;

import java.util.Scanner;

public class Translator {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		// Ask user for text input
		System.out.println("Enter String (textToMorse): ");
		String text = sc.nextLine();

		// Print out the Morse code translated from text
		System.out.println(Translator.textToMorse(text));

		
		// Ask user for MorseCode input
		System.out.println();
		System.out.println("Enter String (morseToText): ");
		String morseCode = sc.nextLine();
		
		// Print out the text translated from Morse code
		System.out.println(Translator.morseToText(morseCode));
		
		sc.close();
	}

	// Method to translate from Text to MorseCode
	// Returns a string array of MorseCode
	public static String textToMorse(String text)
	{
	    // Clean the user input text
		String cleanText = text.toUpperCase().replaceAll("[^A-Z0-9 .,?'():+\\-\"@!]", "").strip().replaceAll("\\s+", " ");
		
		// If user gave empty input (\n) then stop and return an empty string
		if (cleanText.isEmpty()) 
		{
		    return "";
		}
		
		// Convert the user input text into a 'character array'
	    char[] textToCharArray = cleanText.toCharArray();
		
	    // Allocate an array where the translated text will be stored in
	    String[] translatedArray = new String[textToCharArray.length];
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'charArray'. Get the translation of the current character
	    // by using the textToMorse map, if it doesn't exist then put '#' instead
	    for (int i = 0; i < textToCharArray.length; i++)
	    {
	    	translatedArray[i] = MorseMap.textToMorse.getOrDefault(textToCharArray[i], "#");
	    }
	    
		// Store result string with StringBuilder
		StringBuilder translationSB = new StringBuilder();
		
		// Append to the 'translationSB' object each string in 'translatedArray' with spaces in between
		for (String s : translatedArray) {
			translationSB.append(s).append(" ");
		}
		
	    // Return the translated string
	    return translationSB.toString().strip();
	}
	
	// Method to translate from MorseCode to Text
	// Returns a char array of Text
	public static String morseToText(String morseCode)
	{
		// Clean user input, make sure only [./- ] are only allowed,  
		// remove trailing and leading white spaces, make sure '/' are split as ' / '
		String cleanMorseCode = morseCode.replaceAll("[^./\\- ]", "").strip().replaceAll("\\s*/\\s*", " / ").replaceAll("\\s+", " ");
	    
		// If user gave empty input (\n) then stop and return an empty string
		if (cleanMorseCode.isEmpty()) 
		{
		    return "";
		}
		
	    // Convert the user input MorseCode into a 'string array'
	    // by splitting up the text by whitespace
		// (Splitting the MorseCode by letter)
		String[] morseCodeSplitBySpacing = cleanMorseCode.split("\\s+");
	    
	    // Allocate a character array where the translated text will be stored in
	    char[] translatedArray = new char[morseCodeSplitBySpacing.length];
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'morseCodeSplitBySpacing'. Get the translation of the current character
	    // by using the morseToText map, if it doesn't exist then put '#' instead
	    for (int i = 0; i < morseCodeSplitBySpacing.length; i++)
	    {
	    	translatedArray[i] = MorseMap.morseToText.getOrDefault(morseCodeSplitBySpacing[i], '#');
	    }
	    
		// Store result string with StringBuilder
		StringBuilder translationSB = new StringBuilder();
		
		// Append each character in 'translatedArray' in the 'translationSB' object
		for (char c : translatedArray) {
			translationSB.append(c);
		}
		
	    // Return the translated string
	    return translationSB.toString().strip();
	}
}
