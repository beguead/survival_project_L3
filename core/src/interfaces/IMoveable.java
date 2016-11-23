package interfaces;

import com.badlogic.gdx.math.Vector2;

public interface IMoveable {

	public abstract void move(float delta);
	public abstract Vector2 getPosition();
	
}
