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




