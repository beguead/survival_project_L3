package characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import interfaces.IMoveable;

public abstract class Enemy implements IMoveable {

	protected BodyDef bodydef;
	public Body body;
	protected CircleShape shape;
	protected FixtureDef fixture;
	
	protected TextureRegion current_frame;
	protected float state_time;
	
	public Enemy() {

	}
	
	public void update(float delta) {
		
		state_time += delta;
		move(delta);
		
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		
	}

}
