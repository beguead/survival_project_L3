package items;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import box2dLight.ConeLight;

public abstract class Crystal {
	
	protected ConeLight cone_light;
	protected Texture texture;
	
	public Crystal() {};
	public Crystal(Body b) {};
	
	public void dispose() {
		
		cone_light.dispose();
		
	}

	public void setPosition(Vector2 position) {
		
		cone_light.setPosition(position);
		
	}
	
	public void setDirection(float angle) {
		
		cone_light.setDirection(angle);
		
	}

}
