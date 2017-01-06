package utilities;

import com.badlogic.gdx.math.Vector3;

import screens.GameScreen;

public final class MathExtension {
	
	private MathExtension() {}
	
	public static double getAngle(float x1, float y1, float x2, float y2, boolean unproject) {
		
		Vector3 v = new Vector3(x2, y2, 0);
		if (unproject) GameScreen.game_cam.unproject(v);
	
		double adj, angle, opp;
		opp = v.y - y1;
		adj = v.x - x1;	
		angle = Math.atan2(opp , adj);
		
		return angle;
		
	}

}
