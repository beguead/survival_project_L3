package items;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

public abstract class Crystal {
	
	//protected final Texture texture;
	
	public Crystal() {};
	
	public Color getColor() {
	
		if (this instanceof WhiteCrystal) return Color.WHITE;
		else return null;
		
	}
	
	public float getDistance() {
		
		if (this instanceof WhiteCrystal) return 5f;
		else return 0f;
		
	}
	
	public float getConeDegree() {
		
		if (this instanceof WhiteCrystal) return 10f;
		else return 0f;
		
	}

}
