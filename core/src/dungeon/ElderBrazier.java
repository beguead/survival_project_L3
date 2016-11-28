package dungeon;

import com.badlogic.gdx.graphics.Color;

import lights.Aura;
import screens.GameScreen;
import utilities.Assets;

public class ElderBrazier extends Brazier {

	public ElderBrazier(int x, int y) {
		super(x, y);
		
		aura = new Aura(b, Color.CYAN, 2f);
		current_frame = Assets.elder_brazier_animation.getKeyFrame(0);
		
	}

	public void update(float delta) {

		current_frame = Assets.elder_brazier_animation.getKeyFrame(GameScreen.state_time);
		
	}

}
