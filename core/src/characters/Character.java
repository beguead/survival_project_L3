package characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class Character {
	
	protected float speed;
	protected double direction;
	
	protected Body body;
	
	public Character() {}
	
	public void updateAndRender() {
		
		update();
		render();
		
	}
	
	protected abstract void update();
	protected abstract void render();
	
	protected void move() { body.setLinearVelocity(speed * (float)(Math.cos(direction)), speed * (float)(Math.sin(direction))); }
	
	public Vector2 getPosition() { return body.getPosition(); }
	
	public abstract void dispose();

}
