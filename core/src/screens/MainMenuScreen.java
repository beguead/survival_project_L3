package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import main.MainGame;
import utilities.Assets;
import utilities.Constants;

public class MainMenuScreen implements Screen {
	
	private static MainMenuScreen INSTANCE = new MainMenuScreen();
	private static Stage stage;
	private static TextButtonStyle textButtonStyle;
	
    private MainMenuScreen() {
		
    	Skin skin = new Skin();
    	Pixmap pixmap = new Pixmap(Constants.APP_WIDTH / 4, 50, Format.RGBA8888);
		pixmap.setColor(25f, 25f, 25f, 0.7f);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		skin.add("default", MainGame.font);
 
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
 
		skin.add("default", textButtonStyle);

    }	
    
    public static MainMenuScreen getInstance() { return INSTANCE; }
	
	@Override
	public void show() {
		
		MainGame.camera.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
		
		Assets.main_menu_music.play();
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		final TextButton play_button = new TextButton("JOUER", textButtonStyle);
		play_button.setPosition(100, 250);
		play_button.addListener(
			new ChangeListener() {
						
				public void changed (ChangeEvent event, Actor actor) {  Assets.main_menu_music.stop(); MainGame.getInstance().setScreen(GameScreen.getInstance()); }
					
			}
		);
		stage.addActor(play_button);
		
		TextButton how_to_play = new TextButton("AIDE", textButtonStyle);
		how_to_play.setPosition(100, 175);
		how_to_play.addListener(
			new ChangeListener() {
						
				public void changed (ChangeEvent event, Actor actor) { MainGame.getInstance().setScreen(HelpScreen.getInstance()); }
					
			}
		);
		stage.addActor(how_to_play);
		
		TextButton exit_button = new TextButton("QUITTER", textButtonStyle);
		exit_button.setPosition(100, 100);
		exit_button.addListener(
				new ChangeListener() {
							
					public void changed (ChangeEvent event, Actor actor) { Gdx.app.exit(); }
				}
			);
		stage.addActor(exit_button);
	
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
	public void hide() {}

	@Override
	public void dispose() {
		
		stage.dispose();
		Assets.main_menu_music.dispose();
		
	}

}
