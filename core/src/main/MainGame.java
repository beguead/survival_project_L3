package main;


import com.badlogic.gdx.Game;

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

    public void create() {
    	
    	Assets.load();
    	m2Screen = new MainMenuScreen();
    	gScreen = new GameScreen();
    	setScreen(gScreen);  
            
    }
    
    public static MainGame getInstance() { return INSTANCE; }
	
}
