package utils;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

public class SoundManager {
	public SoundManager() {
		
	}
	
	public void playRunAmok() {
        String soundFilePath = "src\\main\\java\\resources\\sounds\\run_amok.wav";
        new Thread(() -> playSoundAsync(soundFilePath, true)).start();
	}
	
	public void playRandomExplosion() {
        int randomInt = 1+ (int)(Math.random()*9);
        String soundFilePath = "src\\main\\java\\resources\\sounds\\explosion0" + randomInt+ ".wav";
        new Thread(() -> playSoundAsync(soundFilePath, false)).start();
	}
	
	public void playGunshot() {
		String soundFilePath = "src\\main\\java\\resources\\sounds\\pistol.wav";
        new Thread(() -> playSoundAsync(soundFilePath, false)).start();
	}
	
	/*	playSoundAsync()
	 * 	Create a new thread and play a sound file on that new thread.
	 *  @Param: soundFilePath - The relative file path of the sound to play.
	 *  @Param: infiniteLoop - If set true, the method will always restart when the sound file is done playing.
	 */
	
	private static void playSoundAsync(String soundFilePath, boolean infiniteLoop) {
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
                    if(infiniteLoop) {
                    	playSoundAsync(soundFilePath, infiniteLoop);
                    }
                }
            });

            // Start playing asynchronously
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
