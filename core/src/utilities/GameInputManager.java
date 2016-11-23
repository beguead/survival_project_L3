package utilities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import dungeon.Maze;

import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;

public class GameInputManager implements InputProcessor {
	
	public boolean keyDown(int keycode) {
		
		/*Management of player movements*/
		
		if (keycode == Keys.Z || keycode == Keys.UP) {
			
			Maze.player.movingUp = true;
			Maze.player.isMoving = true;
			
		}
		
		if (keycode == Keys.S || keycode == Keys.DOWN) {
			
			Maze.player.movingDown = true;
			Maze.player.isMoving = true;
			
		}
		
		if (keycode == Keys.Q || keycode == Keys.LEFT) {
			
			Maze.player.movingLeft = true;
			Maze.player.isMoving = true;
			
		}
		
		if (keycode == Keys.D || keycode == Keys.RIGHT) {
			
			Maze.player.movingRight = true;
			Maze.player.isMoving = true;
			
		}

		return false;
	}

	public boolean keyUp(int keycode) {
		
		if (keycode == Keys.Z || keycode == Keys.UP) Maze.player.movingUp = false;
		if (keycode == Keys.S || keycode == Keys.DOWN) Maze.player.movingDown = false;
		if (keycode == Keys.Q || keycode == Keys.LEFT) Maze.player.movingLeft = false;
		if (keycode == Keys.D || keycode == Keys.RIGHT) Maze.player.movingRight = false;
		
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		double adj, angle, opp;
		
		Vector3 input = new Vector3(screenX, screenY, 0);
		GameScreen.game_cam.unproject(input);
		
		opp = input.y - Maze.player.getPosition().y * Constants.PPM;
		adj = input.x - Maze.player.getPosition().x * Constants.PPM;	
		angle = Constants.TO_DEGREE * (Math.atan2(opp , adj));
		
		Maze.player.crystal.setDirection((float)angle);
			
		return false;
		
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
