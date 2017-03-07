package managers;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;

import dungeon.LightBarrier;
import dungeon.Maze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;
import utilities.Constants;
import utilities.MathExtension;

public final class GameInputManager implements InputProcessor {
	
	private static QueryCallback callback = new QueryCallback() {

		public boolean reportFixture(Fixture fixture) {
				
			if (fixture.getUserData() instanceof LightBarrier) {
					
				((LightBarrier)(fixture.getUserData())).inverseState();
				return false;
					
			} else return true;	
		}
	};
	
	private static final GameInputManager INSTANCE = new GameInputManager();
	
	private GameInputManager() {}
	
	public static GameInputManager getInstance() { return INSTANCE; }
	
	public boolean keyDown(int keycode) {
			
		if (keycode == Keys.ESCAPE) GameScreen.getInstance().setPause();
		
		return false;
	}

	public boolean keyUp(int keycode) { return false; }

	public boolean keyTyped(char character) { return false; }

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.setMoving(true);
			Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));

		}
		
		if (button == Input.Buttons.MIDDLE && Maze.core_near_the_player != null) Maze.player.setCore(Maze.core_near_the_player);
		
		if (button == Input.Buttons.RIGHT) {
			
			Vector3 v = (GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0))).scl(1 / Constants.PPM);
			GameScreen.world.QueryAABB(callback, v.x, v.y, v.x, v.y);
			
		}
		
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.setMoving(false);
			Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
			Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
		}
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
		
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		Vector3 v = GameScreen.game_cam.unproject(new Vector3(screenX, screenY, 0));
		Maze.player.setDirection(MathExtension.getAngle(Maze.player.getPosition().scl(Constants.PPM), new Vector2(v.x, v.y)));
			
		return false;
		
	}

	public boolean scrolled(int amount) { return false; }
	
}