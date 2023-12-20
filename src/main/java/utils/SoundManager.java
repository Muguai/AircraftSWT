package utils;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class SoundManager {
	public void playRunAmok() {
        String soundFilePath = "src\\main\\java\\resources\\sounds\\run_amok.wav";
        new Thread(() -> playSoundAsync(soundFilePath)).start();
	}
	
	private static void playSoundAsync(String soundFilePath) {
        try {
            System.out.println("Trying to play sound...");

            File soundFile = new File(soundFilePath);
            URL soundUrl = soundFile.toURI().toURL();

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundUrl);
            if (audioInputStream == null) {
                System.err.println("Failed to get audio input stream for: " + soundFilePath);
                return;
            }

            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            // Add a listener to mark sound as finished when it completes
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    System.out.println("Sound stopped.");
                    clip.close();
                }
            });

            // Start playing asynchronously
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
