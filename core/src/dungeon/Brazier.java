package dungeon;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public abstract class Brazier extends MapObject{

	protected Body b;
	protected Aura aura;
	protected TextureRegion current_frame;
	protected final Animation animation;
	
	public Brazier (int x, int y) {
		
		if (this instanceof CommonBrazier) animation = Assets.common_brazier_animation;
		else animation = Assets.elder_brazier_animation;
		
		current_frame = animation.getKeyFrame(0);

		b = BodyCreator.createCircleBody(	BodyDef.BodyType.StaticBody,
											new Vector2(x + Constants.TILE_WIDTH / (2 * Constants.PPM), y + Constants.TILE_HEIGHT / (2 * Constants.PPM)),
											current_frame.getRegionWidth() / (2 * Constants.PPM), true, Constants.BRAZIER_FILTER, Constants.CHARACTER_FILTER, this);
	
	}
	
	public void updateAndRender(float delta) {
		
		TextureRegion tr = current_frame;

		current_frame = animation.getKeyFrame(GameScreen.state_time);
		
		if (tr != current_frame) aura.setDistance((float)(Math.random() * 2) + 1f);
		
		GameScreen.batch.draw(	current_frame,
				b.getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
				b.getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 2);
		
	}
	
	public void dispose() {
		
		aura.dispose();
		
	}
	
}
