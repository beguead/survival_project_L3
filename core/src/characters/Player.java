package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import box2dLight.ConeLight;
import items.Crystal;
import items.WhiteCrystal;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player extends Character {
	
	public boolean moving, sneak;
	
	private TextureRegion current_frame;
	private Animation current_animation;

	private Aura aura;
	public Crystal crystal;
	protected ConeLight cone_light;
	
	public Player() {
		
		/*Movements*/
		speed = 1.5f;
		moving = sneak = false;
		current_frame = Assets.player_walk_down_animation.getKeyFrame(0);
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody,
											new Vector2(1.5f, 1.5f),
											current_frame.getRegionWidth() / (4 * Constants.PPM), false, Constants.CHARACTER_FILTER, (short)(Constants.WALL_FILTER | Constants.SENSOR_FILTER | Constants.LIGHT_FILTER) , this);
		
		/*Lights*/
		aura = new Aura(body, Color.WHITE, 0.3f);
		crystal = new WhiteCrystal();
		
		cone_light = new ConeLight(GameScreen.ray_handler, 100, crystal.getColor(), crystal.getDistance(), body.getPosition().x, body.getPosition().y, 0f, crystal.getConeDegree());
		cone_light.setXray(false);
		cone_light.setSoftnessLength(0f);
		
	}
	
	public void die() {
		
		aura.dispose();
		cone_light.dispose();
		
	}
	
	/*public void setElderVision (boolean b) {
		
		cone_light.setXray(b);
		
		if (b) {
			
			cone_light.setColor(Color.CYAN);
			cone_light.setDistance(10f);
			
		} else setCrystal(crystal);
		
	}*/
	
	public void setCrystal(Crystal c) {
		
		crystal = c;
		
		cone_light.setColor(crystal.getColor());
		cone_light.setDistance(crystal.getDistance());
		cone_light.setConeDegree(crystal.getConeDegree());
		
	}

	public void setDirection(double angle) {
		
		direction = angle;
		angle *= Constants.TO_DEGREE;
		
		cone_light.setDirection((float)angle);
		
		if (Math.abs(angle) < 22.5f) current_animation = Assets.player_walk_right_animation;
		else {
		
			if (Math.abs(angle) < 67.5f) {
				
				if (angle > 0f) current_animation = Assets.player_walk_right_up_animation;
				else current_animation = Assets.player_walk_right_down_animation;
				
			} else {
				
				if (Math.abs(angle) < 112.5f) {
					
					if (angle > 0f) current_animation = Assets.player_walk_up_animation;
					else current_animation = Assets.player_walk_down_animation;
					
				} else {
					
					if (Math.abs(angle) < 157.5f) {
						
						if (angle > 0f) current_animation = Assets.player_walk_left_up_animation;
						else current_animation = Assets.player_walk_left_down_animation;
						
					} else current_animation = Assets.player_walk_left_animation;			
				}
			}	
		}	
	}

	public void updateAndRender() {
		
		if (moving) {
			
			current_frame = current_animation.getKeyFrame(GameScreen.state_time);
			if (sneak) speed = 1f;
			else speed = 2f;
			move();
			
		} else body.setLinearVelocity(0f, 0f);
		
		cone_light.setPosition(getPosition());	// Light updating
		
		GameScreen.batch.draw(	current_frame,
								getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
								getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 2);
	
		
	}
}
