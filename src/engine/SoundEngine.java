package engine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEngine {
	
	private static final String SOUNDS_PATH = "/data/sounds/";
	
	HashMap<String, Clip> clips;
	
	public SoundEngine() {
		clips = new HashMap<String, Clip>();
	}
	
	public void playSound(String name) {
		getClip(name).start();
	}
	
	private Clip getClip(String name) {
		
		Clip clip;
		if ((clip = clips.get(name)) == null) {
			clip = loadClip(name);
		}
		
		return clip;
	}
	
	private Clip loadClip(String name) {
		Clip clip;
		try {
			clip = AudioSystem.getClip();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
			System.exit(1);
			return null;
		}
		
		AudioInputStream ais;
		try {
			InputStream in = this.getClass().getResourceAsStream(SOUNDS_PATH + name);
			ais = AudioSystem.getAudioInputStream(in);
		} catch (UnsupportedAudioFileException | IOException e1) {
			e1.printStackTrace();
			System.exit(1);
			return null;
		}
		
		try {
			clip.open(ais);
		} catch (LineUnavailableException | IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return clip;
	}
}
