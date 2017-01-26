package characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import interfaces.IUpdateAndRender;
import lights.Aura;

public abstract class Character implements IUpdateAndRender {
	
	protected Aura aura;
	protected float speed;
	protected double direction;
	
	protected Body body;
	
	public void updateAndRender() {
		
		update();
		render();
		
	}
	
	protected void move() { body.setLinearVelocity(speed * (float)(Math.cos(direction)), speed * (float)(Math.sin(direction))); }
	
	public Vector2 getPosition() { return body.getPosition(); }

}
