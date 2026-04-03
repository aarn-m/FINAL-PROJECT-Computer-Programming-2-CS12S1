# FINAL PROJECT - Computer Programming 2 CS12S1 
Java final project for Computer Programming 2. 

## Group 5 CS12S1
- Fernando Jr. Castillo
- Kristan Jairus De Castro
- Humphrey Ohwen Gaffud
- Aaron James Moleta
- Jeshmond Riche Sarmiento

### Sample Usage of `Translator.java`
```java
		// Ask user for text input
	    Scanner sc = new Scanner(System.in);

	    System.out.println("Enter String (textToMorse): ");
	    String text = sc.nextLine();
	   
		// Initialize string array by translating textToMorse 
		String[] morseTranslatedFromText = Translator.textToMorse(text);
		
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
		char[] textTranslatedFromMorse = Translator.morseToText(morseCode);
		
		// Print the text translated from morse
		for (char c : textTranslatedFromMorse)
		{
			System.out.print(c);
		}

//	OUTPUT:
//	Enter String (textToMorse): 
//	Lorem ipsum dolor.
//	.-.. --- .-. . -- / .. .--. ... ..- -- / -.. --- .-.. --- .-. .-.-.- 
//
//	Enter String (morseToText): 
//	-- --- .-. ... . / -.-. --- -.. . 
//	MORSE CODE
```
