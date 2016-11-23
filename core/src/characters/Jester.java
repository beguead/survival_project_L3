package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import box2dLight.ConeLight;
import screens.GameScreen;

public class Jester extends Enemy {
	
	ConeLight lights[];
	
	public Jester() {
		
		final Color c[] = {Color.PURPLE, Color.BLUE, Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED};
		lights = new ConeLight[7];
		
		for (int i = 0 ; i < 7 ; ++i) {
			
			lights[i] = new ConeLight(GameScreen.ray_handler, 100, c[i], 2f, body.getPosition().x, body.getPosition().y, i * 50f, 40f);
			lights[i].setSoftnessLength(0.7f);
			
		}
		
	}
	
	public void update(float delta) {
		super.update(delta);
		
		for (int i = 0 ; i < 7 ; ++i) {
			
			lights[i].setPosition(body.getPosition().x, body.getPosition().y);
			lights[i].setDirection(i * 50f + state_time * 25f);
			
		}
		
	}

	@Override
	public void move(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector2 getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}
