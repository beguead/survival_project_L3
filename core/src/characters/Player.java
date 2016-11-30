package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import interfaces.IMoveable;
import items.Crystal;
import items.WhiteCrystal;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player {
	
	public boolean is_moving;
	private float speed;
	
	private Body b;
	private TextureRegion current_frame;
	
	private Aura aura;
	public Crystal crystal;
	
	public Player() {
		
		/*Movements*/
		speed = 2f;
		is_moving = false;
		current_frame = Assets.player_walk_down_animation.getKeyFrame(0); //Default frame if the player is motionless
		
		b = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody,
											new Vector2(Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2),
											current_frame.getRegionWidth() / (4 * Constants.PPM)	);

		/*Lights*/
		aura = new Aura(b, Color.GOLDENROD, 0.5f);
		crystal = new WhiteCrystal(b);	

	}

	public void update(float delta) {
		
		crystal.setPosition(getPosition());	// Light updating
		
	}
	
	public void render() {
		
		GameScreen.batch.draw(	current_frame,
								getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
								getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 3);
		
	}

	private void die() {
		
		aura.dispose();
		crystal.dispose();
		
	}
	
	public void setDirection(float angle) {
		
		if (Math.abs(angle) < 22.5f) current_frame = Assets.player_walk_right_animation.getKeyFrame(GameScreen.state_time);
		else {
		
			if (Math.abs(angle) < 67.5f) {
				
				if (angle > 0f) current_frame = Assets.player_walk_right_up_animation.getKeyFrame(GameScreen.state_time);
				else current_frame = Assets.player_walk_right_down_animation.getKeyFrame(GameScreen.state_time);
				
			} else {
				
				if (Math.abs(angle) < 112.5f) {
					
					if (angle > 0f) current_frame = Assets.player_walk_up_animation.getKeyFrame(GameScreen.state_time);
					else current_frame = Assets.player_walk_down_animation.getKeyFrame(GameScreen.state_time);
					
				} else {
					
					if (Math.abs(angle) < 157.5f) {
						
						if (angle > 0f) current_frame = Assets.player_walk_left_up_animation.getKeyFrame(GameScreen.state_time);
						else current_frame = Assets.player_walk_left_down_animation.getKeyFrame(GameScreen.state_time);
						
					} else current_frame = Assets.player_walk_left_animation.getKeyFrame(GameScreen.state_time);			
				}
			}	
		}	
	}

	public void move(float x, float y) { b.setLinearVelocity(x * speed, y * speed); }

	public Vector2 getPosition() { return b.getPosition(); }

}
