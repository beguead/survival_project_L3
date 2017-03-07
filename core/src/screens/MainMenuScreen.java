package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import main.MainGame;
import managers.MainMenuStage;
import utilities.Assets;
import utilities.Constants;

public class MainMenuScreen implements Screen {
	
	private static MainMenuScreen INSTANCE = new MainMenuScreen();
	private static MainMenuStage stage;
	
    private MainMenuScreen() { MainGame.camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT); }	
    
    public static MainMenuScreen getInstance() { return INSTANCE; }
	
	@Override
	public void show() {
		
		Assets.main_menu_music.play();
		MainGame.font.setColor(Color.WHITE);
		stage = MainMenuStage.getInstance();
		Gdx.input.setInputProcessor(stage);
	
	}

	public void render(float delta) {

		MainGame.batch.begin();
		MainGame.batch.draw(Assets.main_menu_background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		MainGame.batch.end();
    	
		stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
		stage.draw();
    	
	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() { Gdx.input.setInputProcessor(null); }

	@Override
	public void dispose() {
		
		Assets.main_menu_music.dispose();
		stage.dispose();
		
	}

}
