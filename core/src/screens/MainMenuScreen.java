package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import main.MainGame;
import utilities.Assets;
import utilities.Constants;

public class MainMenuScreen implements Screen {

	private static OrthographicCamera camera;
	BitmapFont bfont;
	Skin skin;
	Stage stage;
	
	private static final Texture background =  new Texture(Gdx.files.internal("ui/menu.png"));
	
    public MainMenuScreen() {

		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
 
		skin = new Skin();
		Pixmap pixmap = new Pixmap(Constants.APP_WIDTH / 4, 50, Format.RGBA8888);
		pixmap.setColor(25f, 25f, 25f, 0.6f);
		pixmap.fill();
 
		skin.add("white", new Texture(pixmap));
 
		BitmapFont bfont=new BitmapFont();
		skin.add("default",bfont);
 
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.SCARLET);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.over = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.font = skin.getFont("default");
 
		skin.add("default", textButtonStyle);
 
		final TextButton play_button = new TextButton("JOUER", textButtonStyle);
		play_button.setPosition(Constants.APP_WIDTH - pixmap.getWidth() - 100, Constants.APP_HEIGHT - pixmap.getHeight() - 100);
		play_button.addListener(
			new ChangeListener() {
						
				public void changed (ChangeEvent event, Actor actor) { MainGame.getInstance().setScreen(MainGame.gScreen); }
					
			}
		);
		stage.addActor(play_button);
		
		final TextButton exit_button = new TextButton("QUITTER", textButtonStyle);
		exit_button.setPosition(100, 100);
		exit_button.addListener(
				new ChangeListener() {
							
					public void changed (ChangeEvent event, Actor actor) { System.exit(0);}
						
				}
			);
		stage.addActor(exit_button);
		
    	camera = new OrthographicCamera();

    }		
	
	@Override
	public void show() { Assets.main_menu_music.play(); }

	public void render(float delta) {
		
    	MainGame.batch.setProjectionMatrix(camera.combined);
    	MainGame.batch.begin();
    	MainGame.batch.draw(background, 0, 0, Constants.APP_WIDTH, Constants.APP_HEIGHT);
    	MainGame.batch.end();
    	
    	stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
		stage.draw();
    	
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
	public void dispose() {
		
		stage.dispose();
		
	}

}
