package managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import main.MainGame;
import screens.GameScreen;
import screens.HelpScreen;
import utilities.Assets;
import utilities.Constants;

public class MainMenuStage extends Stage {
	
	private MainMenuStage() {
		
    	Skin skin = new Skin();
    	Pixmap pixmap = new Pixmap(Constants.APP_WIDTH / 4, 50, Format.RGBA8888);
		pixmap.setColor(25f, 25f, 25f, 0.7f);
		pixmap.fill();
		
		skin.add("white", new Texture(pixmap));
		skin.add("default", MainGame.font);
 
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
 
		skin.add("default", textButtonStyle);
		
		final TextButton play_button = new TextButton("JOUER", textButtonStyle);
		play_button.setPosition(100, 250);
		play_button.addListener(
			new ChangeListener() {
						
				public void changed (ChangeEvent event, Actor actor) {  Assets.main_menu_music.stop(); MainGame.getInstance().setScreen(GameScreen.getInstance()); }
					
			}
		);
		addActor(play_button);
		
		final TextButton how_to_play = new TextButton("AIDE", textButtonStyle);
		how_to_play.setPosition(100, 175);
		how_to_play.addListener(
			new ChangeListener() {
						
				public void changed (ChangeEvent event, Actor actor) { MainGame.getInstance().setScreen(HelpScreen.getInstance()); }
					
			}
		);
		addActor(how_to_play);
		
		final TextButton exit_button = new TextButton("QUITTER", textButtonStyle);
		exit_button.setPosition(100, 100);
		exit_button.addListener(
				new ChangeListener() {
							
					public void changed (ChangeEvent event, Actor actor) { Gdx.app.exit(); }
				}
			);
		addActor(exit_button);
		
	}
	
	public static MainMenuStage getInstance() { return new MainMenuStage(); }
	
}
