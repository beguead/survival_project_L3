package characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Character {
	
	protected float speed;
	protected double direction;
	
	protected Body body;
	protected TextureRegion current_frame;
	
	public Character() {}
	
	public abstract void updateAndRender();
	
	public void move() { body.setLinearVelocity(speed * (float)(Math.cos(direction)), speed * (float)(Math.sin(direction))); }
	
	public Vector2 getPosition() { return body.getPosition(); }

}
