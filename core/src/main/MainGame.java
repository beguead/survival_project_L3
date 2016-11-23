package main;


import com.badlogic.gdx.Game;

import screens.GameScreen;
import screens.MainMenuScreen;

public class MainGame extends Game {
	
	//Singloton
	public static MainGame singloton;
	
	//Screens
	public static MainMenuScreen m2Screen;
    public static GameScreen gScreen ;

    public void create() {
    	
    	singloton = this;
    	
    	m2Screen = new MainMenuScreen();
    	gScreen = new GameScreen();
    	setScreen(gScreen);  
            
    }
	
}
