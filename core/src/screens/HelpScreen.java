package screens;

import com.badlogic.gdx.Gdx;
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
	private Texture background;

	private HelpScreen() {
		
		Pixmap pixmap = new Pixmap(Constants.APP_WIDTH / 4, Constants.APP_HEIGHT / 4, Format.RGBA8888);
		pixmap.setColor(0f, 0f, 0f, 0.7f);
		pixmap.fill();
		
		background = new Texture(pixmap);
	
	}
	
	public static HelpScreen getInstance() { return INSTANCE; }

	@Override
	public void show() {}

	@Override
	public void render(float delta) {
		
		if (Gdx.input.isTouched())
			MainGame.getInstance().setScreen(MainMenuScreen.getInstance());
		
		int quarter_width = Constants.APP_WIDTH / 4;
		int quarter_height = Constants.APP_HEIGHT / 4;

		MainGame.batch.begin();
		MainGame.batch.draw(Assets.main_menu_background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		MainGame.batch.draw(background, quarter_width, quarter_height, Constants.APP_WIDTH / 2, Constants.APP_HEIGHT / 2);
		MainGame.batch.draw(Assets.monitor, 3 * quarter_width - Assets.monitor.getWidth() - 10,  3 * quarter_height - Assets.monitor.getHeight() - 10, Assets.monitor.getWidth(), Assets.monitor.getHeight());
		MainGame.batch.draw(Assets.light_barrier_base, quarter_width + 50,  3 * quarter_height - Assets.light_barrier_base.getHeight() / 1.5f - 10, Assets.light_barrier_base.getWidth() / 1.5f, Assets.light_barrier_base.getHeight() / 1.5f);
		MainGame.font.draw(MainGame.batch, Constants.help_text, quarter_width + 25,  3 * quarter_height - 25, Constants.APP_WIDTH / 2 - 50, Align.center, true);
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
