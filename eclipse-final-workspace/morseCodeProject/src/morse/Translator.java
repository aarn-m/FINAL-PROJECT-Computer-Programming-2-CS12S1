package morse;

public class Translator {

	public Translator() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		MorseMap morseMap = new MorseMap();
		
		System.out.println(morseMap.textToMorse);
		System.out.println(morseMap.textToMorse.get('A'));
		
		System.out.println(morseMap.morseToText);
		System.out.println(morseMap.morseToText.get("..--.."));
		
		
		

	}


}
