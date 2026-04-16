package morse;

import java.util.Scanner;
import java.util.function.BooleanSupplier;

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
			// Pass () -> false so audio always plays fully when run standalone
			MorseAudio.playMorse(morse, 20, 440, 0.5f, () -> false);
		} catch (LineUnavailableException e) {
			System.out.println("Audio device unavailable: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			sc.close();
			return;
		} catch (Exception e) {
			System.out.println("Playback error: " + e.getMessage());
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			sc.close();
			return;
		}

		sc.close();
	}

	public static void playMorse(String morseString, int wpm, int frequency, float volume, BooleanSupplier shouldStop)
			throws Exception {
		int unit = 1200 / wpm;
		int sampleRate = 44100;

		AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
		try (SourceDataLine line = AudioSystem.getSourceDataLine(format)) {
			// Open and start the line ONCE before the loop
			// symbols
			line.open(format, sampleRate); // sampleRate = 1 second internal buffer
			line.start();

			for (char symbol : morseString.toCharArray()) {
				if (shouldStop.getAsBoolean()) {
					line.stop();
					line.flush();
					break;
				}

				switch (symbol) {
				case '.':
					writeTone(line, unit, frequency, sampleRate, volume);
					writeSilence(line, unit, sampleRate);
					break;
				case '-':
					writeTone(line, unit * 3, frequency, sampleRate, volume);
					writeSilence(line, unit, sampleRate);
					break;
				case ' ':
					writeSilence(line, unit * 2, sampleRate);
					break;
				case '/':
					writeSilence(line, unit * 6, sampleRate);
					break;
				}
			}

			if (!shouldStop.getAsBoolean()) {
				line.drain();
			}
		} catch (LineUnavailableException e) {
			JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Writes directly to the line instead of returning a buffer
	private static void writeTone(SourceDataLine line, int durationMs, int frequency, int sampleRate, float volume) {
		int samples = durationMs * sampleRate / 1000;
		byte[] buffer = new byte[samples];
		int rampSamples = Math.min(samples / 2, (int) (sampleRate * 0.008));

		for (int i = 0; i < samples; i++) {
			double angle = 2.0 * Math.PI * i * frequency / sampleRate;
			double sample = Math.sin(angle) * 127 * volume;

			if (i < rampSamples) {
				sample *= (double) i / rampSamples;
			} else if (i >= samples - rampSamples) {
				sample *= (double) (samples - i) / rampSamples;
			}

			buffer[i] = (byte) sample;
		}

		line.write(buffer, 0, samples);
	}

	private static void writeSilence(SourceDataLine line, int durationMs, int sampleRate) {
		int samples = durationMs * sampleRate / 1000;
		line.write(new byte[samples], 0, samples);
	}
}