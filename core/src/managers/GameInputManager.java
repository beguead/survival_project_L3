package managers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import characters.Player;
import dungeon.Maze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;
import utilities.MathExtension;

public final class GameInputManager implements InputProcessor {
	
	private static CallBack callback = new CallBack();
	
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
			
			Player.getInstance().moving = true;
			Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
			Player.getInstance().setDirection(MathExtension.getAngle(Player.getInstance().getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));

		}
		
		if (button == Input.Buttons.MIDDLE && Maze.core_near_the_player != null) Player.getInstance().setCore(Maze.core_near_the_player);
		
		if (button == Input.Buttons.RIGHT) {
			
			Vector3 v = (GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0))).scl(1 / Constants.PPM);
			GameScreen.world.QueryAABB(callback, v.x, v.y, v.x, v.y);
			
		}
		
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Player.getInstance().moving = false;
			Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
			Player.getInstance().setDirection(MathExtension.getAngle(Player.getInstance().getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
		}
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
		Player.getInstance().setDirection(MathExtension.getAngle(Player.getInstance().getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
		
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
		Player.getInstance().setDirection(MathExtension.getAngle(Player.getInstance().getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
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