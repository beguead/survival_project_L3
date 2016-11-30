package utilities;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import dungeon.Maze;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import screens.GameScreen;

public class GameInputManager implements InputProcessor {
	
	public boolean keyDown(int keycode) {
		
		switch (keycode) {
		
			case Keys.MINUS: {
				
				if (Assets.virtual_width < Constants.APP_WIDTH && Assets.virtual_height < Constants.APP_HEIGHT) {
				
					Assets.virtual_width += 100;
					Assets.virtual_height += 100;
					
			    	GameScreen.game_cam.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
			    	GameScreen.game_cam.update();
					
				}
				
				break;
				
			}
			
			case Keys.PLUS : {
				
				if (Assets.virtual_width > Constants.APP_WIDTH / 4 && Assets.virtual_height > Constants.APP_HEIGHT / 4) {
				
					Assets.virtual_width -= 100;
					Assets.virtual_height -= 100;
					
			    	GameScreen.game_cam.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
			    	GameScreen.game_cam.update();
				
				}
				
			}
			
			default : {}
			
			break;
		
		}

		return false;
	}

	public boolean keyUp(int keycode) {
		
		return false;
	}

	public boolean keyTyped(char character) {
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) Maze.player.is_moving = true;
		
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {

		if (button == Input.Buttons.LEFT) {
			
			Maze.player.is_moving = false;
			Maze.player.move(0f, 0f);
			
		}
		
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		
		double angle = getMouseAngle(screenX, screenY);
		
		if (Maze.player.is_moving) Maze.player.move((float)(Math.cos(angle)), (float)(Math.sin(angle)));
		
		angle *= Constants.TO_DEGREE;
		
		Maze.player.setDirection((float)angle);
		Maze.player.crystal.setDirection((float)angle);
		
		return false;
	}


	public boolean mouseMoved(int screenX, int screenY) {
		
		Maze.player.crystal.setDirection((float)(Constants.TO_DEGREE * getMouseAngle(screenX, screenY)));
			
		return false;
		
	}

	public boolean scrolled(int amount) {
		
		return false;
		
	}
	
	private double getMouseAngle(int screenX, int screenY) {
		
		Vector3 input = new Vector3(screenX, screenY, 0);
		GameScreen.game_cam.unproject(input);
	
		double adj, angle, opp;
		opp = input.y - Maze.player.getPosition().y * Constants.PPM;
		adj = input.x - Maze.player.getPosition().x * Constants.PPM;	
		angle = Math.atan2(opp , adj);
		
		return angle;
		
	}

}
