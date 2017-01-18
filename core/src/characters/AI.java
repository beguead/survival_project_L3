package characters;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;
import utilities.MathExtension;

public abstract class AI extends Character {

	protected long timer;
	protected ConcurrentLinkedQueue<Vector2> path;
	protected Vector2 current_target;
	protected Animation animation;
	
	protected abstract Vector2 updatePath();
	
	public AI() {
		super();
		
		timer = 0;

	}
	
	protected void update() {
		
		if (body.getPosition().x > current_target.x + 0.10f && body.getPosition().x < current_target.x + 0.90f && body.getPosition().y > current_target.y + 0.10f && body.getPosition().y < current_target.y + 0.90f) {
			
			current_target = path.poll();
			if (current_target  == null) current_target = updatePath();
			
			direction = MathExtension.getAngle(body.getPosition().x * Constants.PPM, body.getPosition().y * Constants.PPM, (current_target.x + 0.5f) * Constants.PPM, (current_target.y + 0.5f) * Constants.PPM, false);
			
		}
		
		move();

	}
	
	protected void render() {
		
		GameScreen.batch.draw(	animation.getKeyFrame(GameScreen.state_time),
								getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2,
								getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2 );

	}

}
