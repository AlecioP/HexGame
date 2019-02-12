package view.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundsProvider {

	private SoundsProvider() {}

	private static SoundsProvider instance = null;

	public static synchronized SoundsProvider getInstance() {
		if(SoundsProvider.instance==null) {
			SoundsProvider.instance = new SoundsProvider();
			SoundsProvider.instance.init();
		}

		return SoundsProvider.instance;
	}

	/**
	 * Standard Singleton(SI)
	 */

	private boolean soundOn;
	private File[] audios;
	private AudioInputStream[] streams;
	private Clip[] clips;

	private static final int soundCounter = 3;

	public static final int index_menu = 0;
	public static final int index_wow = 1;
	public static final int index_ugh = 2;

	private void init() {
		soundOn=false;

		audios = new File[soundCounter];
		streams = new AudioInputStream[soundCounter];
		clips = new Clip[soundCounter];

		String pathToSounds = "data"+File.separatorChar+"sounds"+File.separatorChar;

		audios[index_menu]            = new File(pathToSounds+"menu.wav");
		audios[index_wow] = new File(pathToSounds+"wow.wav");
		audios[index_ugh] = new File(pathToSounds+"mario-ugh.wav");


		try {
			streams[index_menu]            = AudioSystem.getAudioInputStream(audios[index_menu]);
			streams[index_wow] = AudioSystem.getAudioInputStream(audios[index_wow]);
			streams[index_ugh] = AudioSystem.getAudioInputStream(audios[index_ugh]);
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	public void startSound(int index, boolean loop) {
		if(soundOn) {
			index = Math.abs(index%soundCounter);
			try {
				if(clips[index]==null) {
					if(loop) {
						Clip clip = AudioSystem.getClip();
						clip.open(streams[index]);
						clip.loop(Clip.LOOP_CONTINUOUSLY);
						clip.start();
						clips[index] = clip;
					}else {
						Clip clip = AudioSystem.getClip();
						clip.open(streams[index]);
						clip.start();
						clips[index] = clip;
					}
				}else {
					clips[index].setMicrosecondPosition(0);
					clips[index].start();
				}

			}catch(Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}

	public void stopSound(int index) {
		index = Math.abs(index%soundCounter);
		try {
			if(clips[index]!=null)
				clips[index].stop();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private void stopAllPlaying() {
		for(int i=0;i<soundCounter;i++)
			if(clips[i]!=null)
				clips[i].stop();
	}

	public void soundButtonClicked() {
		if(soundOn) {
			stopAllPlaying();
			soundOn=false;
		}else {
			stopAllPlaying();
			soundOn=true;
			startSound(index_menu, true);
		}
	}

	public boolean isSoundOn() {
		return soundOn;
	}
}
