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
        System.out.println("Enter String (textToMorse): ");
        String text = sc.nextLine();
        System.out.println(Translator.textToMorse(text));
        String morse = Translator.textToMorse(text);
        try {
            MorseAudio.playMorse(morse, 20, 700, 0.8f, () -> false);
        } catch (Exception e) {
            System.out.println("Playback error: " + e.getMessage());
        }
        sc.close();
    }

    public static byte[] generateTone(int durationMs, int frequency, int sampleRate, float volume) {
        int samples = durationMs * sampleRate / 1000;
        byte[] buffer = new byte[samples];
        int rampSamples = Math.min(samples / 2, (int)(sampleRate * 0.008));
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
        return buffer;
    }

    public static byte[] generateSilence(int durationMs, int sampleRate) {
        return new byte[durationMs * sampleRate / 1000];
    }

    public static void playMorse(String morseString, int wpm, int frequency, float volume, BooleanSupplier shouldStop) throws Exception {
        int unit = 1200 / wpm;
        int sampleRate = 44100;

        // Pre‑calculate total size
        int totalSamples = 0;
        for (char symbol : morseString.toCharArray()) {
            switch (symbol) {
                case '.': totalSamples += unit * 2 * sampleRate / 1000; break;
                case '-': totalSamples += unit * 4 * sampleRate / 1000; break;
                case ' ': totalSamples += unit * 2 * sampleRate / 1000; break;
                case '/': totalSamples += unit * 6 * sampleRate / 1000; break;
            }
        }

        byte[] fullAudio = new byte[totalSamples];
        int pos = 0;
        for (char symbol : morseString.toCharArray()) {
            byte[] chunk;
            switch (symbol) {
                case '.':
                    chunk = generateTone(unit, frequency, sampleRate, volume);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    chunk = generateSilence(unit, sampleRate);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    break;
                case '-':
                    chunk = generateTone(unit * 3, frequency, sampleRate, volume);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    chunk = generateSilence(unit, sampleRate);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    break;
                case ' ':
                    chunk = generateSilence(unit * 2, sampleRate);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    break;
                case '/':
                    chunk = generateSilence(unit * 6, sampleRate);
                    System.arraycopy(chunk, 0, fullAudio, pos, chunk.length); pos += chunk.length;
                    break;
            }
        }

        AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
        try (SourceDataLine line = AudioSystem.getSourceDataLine(format)) {
            line.open(format);
            line.start();
            int chunkSize = sampleRate / 10;
            int written = 0;
            while (written < pos) {
                if (shouldStop.getAsBoolean()) {
                    line.stop();
                    line.flush();
                    break;
                }
                int remaining = pos - written;
                int toWrite = Math.min(chunkSize, remaining);
                line.write(fullAudio, written, toWrite);
                written += toWrite;
            }
            if (!shouldStop.getAsBoolean()) {
                line.drain();
            }
        } catch (LineUnavailableException e) {
            JOptionPane.showMessageDialog(null, "Audio error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}