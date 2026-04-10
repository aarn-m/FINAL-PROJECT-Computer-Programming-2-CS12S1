package morse;

import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

public class MorseAudio {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		// Ask user for text input
		System.out.println("Enter String (textToMorse): ");
		String text = sc.nextLine();

		// Print out the Morse code translated from text
		System.out.println(Translator.textToMorse(text));

		String morse = Translator.textToMorse(text);

		try {
			MorseAudio.playMorse(morse, 20, 700, 0.8f); // 20 WPM, 700 Hz, 80% volume
		} catch (LineUnavailableException e) {
			System.out.println("Audio device unavailable: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (Exception e) {
			System.out.println("Playback error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		sc.close();
	}

	public static byte[] generateTone(int durationMs, int frequency, int sampleRate, float volume) {
		// volume: 0.0f (silent) to 1.0f (max)
		int samples = durationMs * sampleRate / 1000;
		byte[] buffer = new byte[samples];
		for (int i = 0; i < samples; i++) {
			double angle = 2.0 * Math.PI * i * frequency / sampleRate;
			buffer[i] = (byte) (Math.sin(angle) * 127 * volume);
		}
		return buffer;
	}

	public static byte[] generateSilence(int durationMs, int sampleRate) {
		return new byte[durationMs * sampleRate / 1000];
	}

	public static void playBytes(byte[] audio, int sampleRate) throws Exception {
		AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
		SourceDataLine line = AudioSystem.getSourceDataLine(format);
		line.open(format);
		line.start();
		line.write(audio, 0, audio.length);
		line.drain();
		line.close();
	}

	public static void playMorse(String morseString, int wpm, int frequency, float volume) throws Exception {
		try {
			int unit = 1200 / wpm; // ms per unit
			int sampleRate = 44100;

			for (int i = 0; i < morseString.length(); i++) {
				char symbol = morseString.charAt(i);
				byte[] audio;

				switch (symbol) {
					case '.':
						audio = generateTone(unit, frequency, sampleRate, volume);      // 1 unit on
						playBytes(audio, sampleRate);
						playBytes(generateSilence(unit, sampleRate), sampleRate);        // 1 unit gap
						break;
					case '-':
						audio = generateTone(unit * 3, frequency, sampleRate, volume);  // 3 units on
						playBytes(audio, sampleRate);
						playBytes(generateSilence(unit, sampleRate), sampleRate);        // 1 unit gap
						break;
					case ' ':
						// inter-letter gap = 3 units, but 1 unit already added after last symbol
						playBytes(generateSilence(unit * 2, sampleRate), sampleRate);
						break;
					case '/':
						// word gap = 7 units, 1 unit already added after last symbol
						playBytes(generateSilence(unit * 6, sampleRate), sampleRate);
						break;
					default:
						break;
				}
			}
		} catch (LineUnavailableException e) {
			System.out.println("Audio device unavailable: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
	}
}