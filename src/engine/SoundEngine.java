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

	/**
	 * The path to the sound clips
	 */
	private static final String SOUNDS_PATH = "/data/sounds/";

	/**
	 * A hash of cached sound clips.
	 */
	HashMap<String, Clip> clipCache;

	public SoundEngine() {
		clipCache = new HashMap<String, Clip>();
	}

	/**
	 * Plays the sound clip with the given name.
	 * 
	 * @param name
	 *            The name of the sound clip.
	 */
	public void playSound(String name) {
		getClip(name).start();
	}

	/**
	 * Gets a sound clip. Either from the cache or load it from the disk.
	 * 
	 * @param name
	 *            The name of the sound clip.
	 * @return A sound clip.
	 */
	private Clip getClip(String name) {

		Clip clip;
		if ((clip = clipCache.get(name)) == null) {
			clip = loadClip(name);
		}

		return clip;
	}

	/**
	 * Load a sound clip from the disk.
	 * 
	 * @param name
	 *            The name of the sound clip to load.
	 * @return A sound clip.
	 */
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
			InputStream in = this.getClass().getResourceAsStream(
					SOUNDS_PATH + name);
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
