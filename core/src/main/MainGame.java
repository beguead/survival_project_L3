package main;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.GameScreen;
import screens.MainMenuScreen;
import utilities.Assets;

public class MainGame extends Game {
	
	//Singleton
	private static final MainGame INSTANCE = new MainGame();
	
	private MainGame() {};
	
	//Screens
	public static MainMenuScreen m2Screen;
    public static GameScreen gScreen ;
    public static SpriteBatch batch;

    public void create() {
    	
      	batch = new SpriteBatch();
    	
    	Assets.load();
    	
    	m2Screen = new MainMenuScreen();
    	gScreen = new GameScreen();
    	setScreen(m2Screen);  
            
    }
    
    public static MainGame getInstance() { return INSTANCE; }
	
}
