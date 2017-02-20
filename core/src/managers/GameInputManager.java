package managers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import dungeon.Maze;
import main.MainGame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;
import utilities.MathExtension;

public final class GameInputManager implements InputProcessor {
	
	private static CallBack callback = new CallBack();
	
	private static final GameInputManager INSTANCE = new GameInputManager();
	
	private GameInputManager() {}
	
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
			Vector3 v = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));

		}
		
		if (button == Input.Buttons.MIDDLE && Maze.core_near_the_player != null) Maze.player.setCore(Maze.core_near_the_player);
		
		if (button == Input.Buttons.RIGHT) {
			
			Vector3 v = (MainGame.camera.unproject(new Vector3(screenX, screenY, 0))).scl(1 / Constants.PPM);
			GameScreen.world.QueryAABB(callback, v.x, v.y, v.x, v.y);
			
		}
		
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.moving = false;
			Vector3 v = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
		}
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		Vector3 v = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
		
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		Vector3 v = MainGame.camera.unproject(new Vector3(screenX, screenY, 0));
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
		return false;
		
	}

	public boolean scrolled(int amount) {
		
		if (amount == 1) {	
		
			if (Assets.virtual_width < Constants.APP_WIDTH * 2 && Assets.virtual_height < Constants.APP_HEIGHT * 2) {
			
				Assets.virtual_width += 10;
				Assets.virtual_height += 10;
			
				MainGame.camera.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
				MainGame.camera.update();
			
			} else {

				Assets.virtual_width = Constants.APP_WIDTH * 2;
				Assets.virtual_height = Constants.APP_HEIGHT * 2;
				
			}
		
		} else {
		
			if (Assets.virtual_width > 1200 && Assets.virtual_height > 800) {
		
				Assets.virtual_width -= 10;
				Assets.virtual_height -= 10;
			
				MainGame.camera.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
				MainGame.camera.update();
		
			} else {

				Assets.virtual_width = 1200;
				Assets.virtual_height = 800;
				
			}
		
		}
		
		return false;
		
	}
	
	public static GameInputManager getInstance() { return INSTANCE; }
	
}