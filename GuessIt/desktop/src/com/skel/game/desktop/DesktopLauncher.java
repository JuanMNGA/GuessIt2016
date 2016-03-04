package com.skel.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skel.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "GuessIt: Language Trainer";
		config.height = 800;
		config.width = 480;
		config.resizable = false;
		new LwjglApplication(new MainGame(), config);
	}
}
