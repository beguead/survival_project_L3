package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import main.MainGame;
import utilities.Constants;

public class DesktopLauncher {
	
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		
		cfg.title = Constants.GAME_TITLE;
		cfg.resizable = false;
        cfg.width = Constants.APP_WIDTH;
        cfg.height = Constants.APP_HEIGHT;
		
		new LwjglApplication(new MainGame(), cfg);
		
	}
	
}
