# FINAL PROJECT - Computer Programming 2 CS12S1 
A Morse code translator with a minigame. A final project for Computer Programming 2 made in Java. The minigame is similar to wordle but you solve it by listening to the Morse code audio.

"Design and Development of a Gamified Morse Code Translator and Learning Application Using Java GUI"

## Group 5 CS12S1
Made by first year students from Technological Institute of the Philippines - Manila. 
- Moleta, Aaron James T.
- Sarmiento, Jeshmond Riche V.
- Gaffud, Humphrey Ohwen S.
- De Castro, Kristan Jairus M.
- Castillo, Fernando Jr. L.

### Sample Usage of `Translator.java`
```java
Scanner sc = new Scanner(System.in);

// Ask user for text input
System.out.println("Enter String (textToMorse): ");
String text = sc.nextLine();

// Print out the Morse code translated from text
System.out.println(Translator.textToMorse(text));


// Ask user for MorseCode input
System.out.println("\n");
System.out.println("Enter String (morseToText): ");
String morseCode = sc.nextLine();

// Print out the text translated from Morse code
System.out.println(Translator.morseToText(morseCode));

sc.close();

//	OUTPUT:
//	Enter String (textToMorse): 
//	Lorem ipsum dolor.
//	.-.. --- .-. . -- / .. .--. ... ..- -- / -.. --- .-.. --- .-. .-.-.- 
//
//	Enter String (morseToText): 
//	-- --- .-. ... . / -.-. --- -.. . 
//	MORSE CODE
```

### Sample Usage of `RandomWordGenerator.java`
```java
System.out.println(RandomWordGenerator.getRandomWordMedium());
System.out.println(RandomWordGenerator.getRandomWordShort());

//	OUTPUT:
//	mineral
//	part
```




