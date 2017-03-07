package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

import main.MainGame;
import utilities.Assets;
import utilities.Constants;

public class EndScreen implements Screen {
	
	private static EndScreen INSTANCE = new EndScreen();
	
	private static Texture img;
	private static String text;
	private static long timer;
	
	private EndScreen() {}
	
	public static EndScreen getInstance(boolean escape) {
		
		if (escape) {
			
			img = Assets.escape_img;
			text = Constants.escape_screen_text;
			MainGame.font.setColor(Color.ROYAL);
			
		} else {
			
			img = Assets.fail_img;
			text = Constants.fail_screen_text;
			MainGame.font.setColor(Color.SCARLET);
			
		}
		
		return INSTANCE;
		
	}
	
	@Override
	public void show() { timer = System.currentTimeMillis() + 5000; }

	@Override
	public void render(float delta) {
		
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if (System.currentTimeMillis() > timer)
			MainGame.getInstance().setScreen(MainMenuScreen.getInstance());
		
		MainGame.batch.begin();
		MainGame.batch.draw(img, (Constants.APP_WIDTH - img.getWidth()) / 2f, (Constants.APP_HEIGHT - img.getHeight()) / 2f, img.getWidth(), img.getHeight());
		MainGame.font.draw(MainGame.batch, text, Constants.APP_WIDTH / 2f, 25f, 0, Align.center, false);
		MainGame.batch.end();

	}

	@Override
	public void resize(int width, int height) {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {}

}
