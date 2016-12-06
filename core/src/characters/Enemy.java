package characters;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

import lights.Aura;

public abstract class Enemy {
	
	protected float speed;
	
	protected Body b;
	protected TextureRegion current_frame;

	protected Aura aura;
	
	public Enemy() {
			
			aura = null;

	}
	
	public void updateAndRender(float delta) {
		
		move(delta);
		
	}

	public abstract void move(float delta);

}
