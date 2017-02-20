package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;

import main.MainGame;
import utilities.Assets;
import utilities.Constants;

public class HelpScreen implements Screen {
	
	private static HelpScreen INSTANCE = new HelpScreen();
	Texture mist;
	private int step;
	private String text;

	private HelpScreen() {
		
		Pixmap pixmap = new Pixmap(Constants.APP_WIDTH / 4, Constants.APP_HEIGHT / 4, Format.RGBA8888);
		pixmap.setColor(0f, 0f, 0f, 0.7f);
		pixmap.fill();
		
		mist = new Texture(pixmap);
	
	}
	
	public static HelpScreen getInstance() { return INSTANCE; }

	@Override
	public void show() { text = Constants.T0; }

	@Override
	public void render(float delta) {
		
		if (Gdx.input.isTouched()) {
			
			switch (step++) {
			
			
			}
			
		}// MainGame.getInstance().setScreen(MainMenuScreen.getInstance());
		
		int half_width = Constants.APP_WIDTH / 2;
		int quarter_width = half_width / 2;
		int quarter_height = Constants.APP_HEIGHT / 4;

		MainGame.batch.begin();
		MainGame.batch.draw(Assets.main_menu_background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		MainGame.batch.draw(mist, quarter_width, quarter_height, half_width, Constants.APP_HEIGHT / 2);
		MainGame.batch.draw(Assets.monitor, 3 * quarter_width - Assets.monitor.getWidth() - 10,  3 * quarter_height - Assets.monitor.getHeight() - 10, Assets.monitor.getWidth(), Assets.monitor.getHeight());
		MainGame.font.draw(MainGame.batch, "Bonjour dépositaire ! Besoin d'aide ?\nLaissez moi vous éclairer...\n\n" + text, quarter_width,  3 * quarter_height - 25, half_width, Align.center, true);
		MainGame.batch.draw( 100, 100)
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
