package com.piculi.tetris;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.piculi.tetris.TetrisApplication;

import static com.piculi.tetris.constants.GameConstants.SCREEN_HEIGHT;
import static com.piculi.tetris.constants.GameConstants.SCREEN_WIDTH;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(SCREEN_WIDTH,SCREEN_HEIGHT);
		config.setTitle("Tetris");
		new Lwjgl3Application(new TetrisApplication(), config);
	}
}
