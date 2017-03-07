package characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import interfaces.IUpdateAndRender;
import lights.Aura;
import screens.GameScreen;
import utilities.Constants;

public abstract class Character implements IUpdateAndRender {
	
	protected Aura aura;
	protected Body body;
	protected float direction, speed;
	protected TextureRegion current_frame;
	
	public void updateAndRender() {
		
		update();
		render();
		
	}
	
	@Override
	public void render() {
		
		float width = current_frame.getRegionWidth() / 2;
		float height = current_frame.getRegionHeight() / 2;

		GameScreen.game_batch.draw(	current_frame, getPosition().x * Constants.PPM - width, getPosition().y * Constants.PPM - height, width, height,
									current_frame.getRegionWidth(), current_frame.getRegionHeight(), 1f, 1f, (float)(direction * Constants.TO_DEGREE)	);
		
	}
	
	protected void move() { body.setLinearVelocity(speed * (float)(Math.cos(direction)), speed * (float)(Math.sin(direction))); }
	
	public Vector2 getPosition() { return body.getPosition(); }

}
