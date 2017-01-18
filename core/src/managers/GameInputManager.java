package managers;

import com.badlogic.gdx.InputProcessor;
import dungeon.Maze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;
import utilities.MathExtension;

public class GameInputManager implements InputProcessor {
	
	public boolean keyDown(int keycode) {
			
		if (keycode == Keys.F1) GameScreen.debug_renderer = !GameScreen.debug_renderer;
		if (keycode == Keys.ESCAPE) GameScreen.pause = !GameScreen.pause;

		return false;
	}

	public boolean keyUp(int keycode) {
		
		return false;
	}

	public boolean keyTyped(char character) {
		
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.moving = true;
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().x * Constants.PPM, Maze.player.getPosition().y * Constants.PPM, screenX, screenY, true));

		}
		
		if (button == Input.Buttons.RIGHT && Maze.core_near_the_player != null) { Maze.player.setCore(Maze.core_near_the_player); }
		
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.moving = false;
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().x * Constants.PPM, Maze.player.getPosition().y * Constants.PPM, screenX, screenY, true));
			
		}
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().x * Constants.PPM, Maze.player.getPosition().y * Constants.PPM, screenX, screenY, true));
		
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().x * Constants.PPM, Maze.player.getPosition().y * Constants.PPM, screenX, screenY, true));
			
		return false;
		
	}

	public boolean scrolled(int amount) {
		
		if (amount == 1) {	
		
			if (Assets.virtual_width < Constants.APP_WIDTH && Assets.virtual_height < Constants.APP_HEIGHT) {
			
				Assets.virtual_width += 15;
				Assets.virtual_height += 15;
			
				GameScreen.game_cam.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
				GameScreen.game_cam.update();
			
			}
		
		} else {
	
			final int min_width = (int) (Constants.APP_WIDTH / 2.5);
			final int min_height = (int) (Constants.APP_HEIGHT / 2.5);
		
			if (Assets.virtual_width > min_width && Assets.virtual_height > min_height) {
		
				Assets.virtual_width -= 10;
				Assets.virtual_height -= 10;
			
				GameScreen.game_cam.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
				GameScreen.game_cam.update();
		
			} else {

				Assets.virtual_width = min_width;
				Assets.virtual_height = min_height;
				
			}
		
		}
		
		return false;
		
	}
}
