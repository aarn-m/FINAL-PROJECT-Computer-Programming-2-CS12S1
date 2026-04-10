package morse;

import java.util.Map;
import static java.util.Map.entry;
import java.util.HashMap;
import java.util.Collections;

public class MorseMap {

	//	Map and HashMap in Java - Full Tutorial 
	//	https://youtu.be/H62Jfv1DJlU?si=uZ5pj8ieU7ygKPlk
	
		// Store the textToMorse translations in an immutable map
	    public static final Map<Character, String> textToMorse = Map.ofEntries(
	    		
			// Letters
			entry('A', ".-"),
			entry('B', "-..."),
			entry('C', "-.-."),
			entry('D', "-.."),
			entry('E', "."),
			entry('F', "..-."),
			entry('G', "--."),
			entry('H', "...."),
			entry('I', ".."),
			entry('J', ".---"),
			entry('K', "-.-"),
			entry('L', ".-.."),
			entry('M', "--"),
			entry('N', "-."),
			entry('O', "---"),
			entry('P', ".--."),
			entry('Q', "--.-"),
			entry('R', ".-."),
			entry('S', "..."),
			entry('T', "-"),
			entry('U', "..-"),
			entry('V', "...-"),
			entry('W', ".--"),
			entry('X', "-..-"),
			entry('Y', "-.--"),
			entry('Z', "--.."),
			
			// Numbers
			entry('0', "-----"),
			entry('1', ".----"),
			entry('2', "..---"),
			entry('3', "...--"),
			entry('4', "....-"),
			entry('5', "....."),
			entry('6', "-...."),
			entry('7', "--..."),
			entry('8', "---.."),
			entry('9', "----."),
			
			// Special Characters
			entry(' ', "/"),
			entry('.', ".-.-.-"),
			entry(',', "--..--"),
			entry('?', "..--.."),
			entry('\'', ".----."),
			entry('(', "-.--."),
			entry(')', "-.--.-"),
			entry(':', "---..."),
			entry('+', ".-.-."),
			entry('-', "-....-"),
			entry('"', ".-..-."),
			entry('@', ".--.-."),
			entry('!', "-.-.--")
		);
    
    // Store the translations in reverse in an immutable map
    // (For Morse to Text)
	    // Map where to store morseToText
	    public static final Map<String, Character> morseToText;
	    
	    static 
	    {
		    // Create temporary map to hold the reverse
		    Map<String, Character> tempMorseToText = new HashMap<>();
			   
		    // Loop through the original 'textToMorse' map and input their key-value pairs in the temporary map
	        for (Map.Entry<Character, String> entry : textToMorse.entrySet()) {
	        	tempMorseToText.put(entry.getValue(), entry.getKey());
	        }
		    
		    // Store the temporary map in this final unmodifiable map
		    morseToText = Collections.unmodifiableMap(tempMorseToText);
	    }

	   
}
