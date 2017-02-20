package main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import screens.MainMenuScreen;
import utilities.Assets;

public class MainGame extends Game {
	
	private static final MainGame INSTANCE = new MainGame();
	public static SpriteBatch batch;
	public static OrthographicCamera camera = new OrthographicCamera();
	public static BitmapFont font;
	
	private MainGame() {};

    public void create() {
    	
    	Assets.load();
    	batch = new SpriteBatch();
    	camera = new OrthographicCamera();
		font = new BitmapFont();
    	setScreen(MainMenuScreen.getInstance());  
            
    }
    
    public static MainGame getInstance() { return INSTANCE; }
	
}
