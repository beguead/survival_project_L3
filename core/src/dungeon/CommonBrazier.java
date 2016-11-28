package dungeon;

import com.badlogic.gdx.graphics.Color;

import lights.Aura;
import screens.GameScreen;
import utilities.Assets;

public class CommonBrazier extends Brazier {

	public CommonBrazier(int x, int y) {
		super(x, y);
		
		aura = new Aura(b, Color.GOLDENROD, 3f);
		current_frame = Assets.common_brazier_animation.getKeyFrame(0);
		
	}

	public void update(float delta) {

		current_frame = Assets.common_brazier_animation.getKeyFrame(GameScreen.state_time);
		
	}

}
