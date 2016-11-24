package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import interfaces.IMoveable;
import items.Crystal;
import items.WhiteCrystal;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player implements IMoveable {
	
	public boolean isMoving, movingUp, movingDown, movingLeft, movingRight;
	private float speed, state_time;
	
	private Body b;
	private TextureRegion current_frame;
	
	private Aura aura;
	public Crystal crystal;
	
	public Player() {
		
		/*Movements*/
		speed = 2.5f;
		isMoving = movingUp = movingDown = movingLeft = movingRight = false;
		current_frame = Assets.player_walk_down_animation.getKeyFrame(0); //Default frame if the player is motionless
		
		b = BodyCreator.createDynamicCircleBody(	b, new Vector2(Constants.MAP_WIDTH / 2, Constants.MAP_HEIGHT / 2),
													current_frame.getRegionWidth() / (4 * Constants.PPM)	);

		/*Lights*/
		aura = new Aura(b, Color.GOLDENROD, 1f);
		crystal = new WhiteCrystal(b);	
		
	}

	public void update(float delta) {
		
		state_time += delta;
		crystal.setPosition(getPosition());	// Light updating
		if (isMoving) move(delta); // Position updating
		
	}
	
	public void render() {
		
		GameScreen.batch.draw(	current_frame,
								getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
								getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 2	);
		
	}

	private void die() {
		
		aura.dispose();
		crystal.dispose();
		
	}

	public void move(float delta) {
			
		if (movingUp) {
			
			b.setLinearVelocity(b.getLinearVelocity().x, speed);
			current_frame = Assets.player_walk_up_animation.getKeyFrame(state_time);
			
		}
		
		if (movingDown) {
			
			b.setLinearVelocity(b.getLinearVelocity().x, -speed);
			current_frame = Assets.player_walk_down_animation.getKeyFrame(state_time);
			
		}
		
		if (movingLeft){
			
			b.setLinearVelocity(-speed, b.getLinearVelocity().y);
			current_frame = Assets.player_walk_left_animation.getKeyFrame(state_time);		
			
		}
		
		if (movingRight) {
			
			b.setLinearVelocity(speed, b.getLinearVelocity().y);
			current_frame = Assets.player_walk_right_animation.getKeyFrame(state_time);
			
		}
			
		if (!movingUp && !movingDown) b.setLinearVelocity(b.getLinearVelocity().x, 0);
		if (!movingLeft && !movingRight) b.setLinearVelocity(0, b.getLinearVelocity().y);
			
		if (!movingUp && !movingDown && !movingLeft && !movingRight) {
				
			isMoving = false;
			b.setLinearVelocity(0, 0);
			current_frame = Assets.player_walk_down_animation.getKeyFrame(0); //Default frame if the player is motionless
			
		}
		
	}

	public Vector2 getPosition() { return b.getPosition(); }

}
