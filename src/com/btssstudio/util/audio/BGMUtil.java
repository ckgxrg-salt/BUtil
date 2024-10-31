package com.btssstudio.util.audio;

import java.applet.AudioClip;

public class BGMUtil {
	private static AudioClip bgm;
	public static void setBGM(AudioClip a) {
		bgm = a;
	}
	public static void play() {
		play(false);
	}
	public static void play(boolean loop) {
		if(loop) {
			bgm.loop();
		}else {
			bgm.play();
		}
	}
	public static void stop() {
		bgm.stop();
	}
	public static AudioClip getBGM() {
		return bgm;
	}
}
