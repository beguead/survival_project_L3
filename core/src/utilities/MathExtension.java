package utilities;

import com.badlogic.gdx.math.Vector2;

public final class MathExtension {
	
	private MathExtension() {}
	
	public static double getAngle(Vector2 v1, Vector2 v2) { return Math.atan2(v2.y - v1.y , v2.x - v1.x); }
	
	public static double getDistance(Vector2 v1, Vector2 v2) {
		
		double a = Math.abs(v2.x - v1.x);
		double b = Math.abs(v2.y - v1.y);
		
		return Math.sqrt(a * a + b * b);
		
	}

}
