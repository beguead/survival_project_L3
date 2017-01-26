package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import utilities.Assets;

public class MainMenuScreen implements Screen {

	private static OrthographicCamera camera;
	private static SpriteBatch batch;
	
	private static Texture background;
	
    public MainMenuScreen() {
    	
    	batch = new SpriteBatch();
    	camera = new OrthographicCamera();
    	background = new Texture(Gdx.files.internal("ui/menu.jpg"));

    }		
	
	@Override
	public void show() { Assets.main_menu_music.play(); }

	public void render(float delta) {

    	batch.setProjectionMatrix(camera.combined);
    	batch.begin();
    	batch.draw(background, 0, 0);
    	batch.end();
    	
	}

	@Override
	public void resize(int width, int height) {
		
    	camera.setToOrtho(false,width,height);
		
	}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() { Assets.main_menu_music.stop(); }

	@Override
	public void dispose() {}

}
