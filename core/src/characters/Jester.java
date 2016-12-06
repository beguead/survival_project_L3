package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import box2dLight.ConeLight;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Jester extends Enemy {
	
	
	public Jester(int x, int y) {
		
		/*Movements*/
		speed = 1.9f;

		current_frame = Assets.player_walk_down_animation.getKeyFrame(0); //Default frame if the player is motionless
		
		b = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody,
											new Vector2(x + 0.5f, y + 0.5f),
											current_frame.getRegionWidth() / (3 * Constants.PPM), false, (short)0, (short)0, this);
		
		/*Lights*/
		aura = new Aura(b, Color.WHITE, 0.5f);

		
	}


	public void move(float delta) {

		
	}


	public Vector2 getPosition() {

		return null;
	}

}
