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
		String cleanText = text.toUpperCase().replaceAll(MorseMap.allowedTextPattern, "").strip().replaceAll("\\s+", " ");
		
		// If user gave empty input (\n) then stop and return an empty string
		if (cleanText.isEmpty()) 
		{
		    return "";
		}
		
		// Convert the user input text into a 'character array'
	    char[] textToCharArray = cleanText.toCharArray();
	    
		// Store result string with StringBuilder
		StringBuilder translationSB = new StringBuilder();
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'charArray'. 
	    for (int i = 0; i < textToCharArray.length; i++)
	    {
    		// Get the translation of the current character
		    // by using the textToMorse map, if it doesn't exist then put '#' instead
	    	// >> MorseMap.textToMorse.getOrDefault(textToCharArray[i], "#")
	    	
	    	// Append to the 'translationSB' object each translated string with spaces in between
	    	translationSB.append(MorseMap.textToMorse.getOrDefault(textToCharArray[i], "#")).append(" ");
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
	    
		// Store result string with StringBuilder
		StringBuilder translationSB = new StringBuilder();
	    
	    // Initialize the 'translatedArray' by looping through the whole
	    // 'morseCodeSplitBySpacing'. 
	    for (int i = 0; i < morseCodeSplitBySpacing.length; i++)
	    {
	    	// Get the translation of the current character
		    // by using the morseToText map, if it doesn't exist then put '#' instead
	    	// >> MorseMap.morseToText.getOrDefault(morseCodeSplitBySpacing[i], '#')
	  
	    	// Append each character in 'translatedArray' in the 'translationSB' object
	    	translationSB.append(MorseMap.morseToText.getOrDefault(morseCodeSplitBySpacing[i], '#'));
	    }
		
	    // Return the translated string
	    return translationSB.toString().strip();
	}
}
