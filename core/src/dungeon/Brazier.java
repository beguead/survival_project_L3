package dungeon;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import lights.Aura;
import screens.GameScreen;
import utilities.Constants;

public abstract class Brazier extends MapObject{

	protected Body b;
	protected Aura aura;
	protected TextureRegion current_frame;
	
	public Brazier (int x, int y) {
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.StaticBody;
		b = GameScreen.world.createBody(bdef);
		b.setTransform(	x + Constants.TILE_WIDTH / (2 * Constants.PPM),
						y + Constants.TILE_HEIGHT / (2 * Constants.PPM), 0f);
	
	}
	
	public abstract void update(float delta);
	
	public void render() {
		
		GameScreen.batch.draw(	current_frame,
								b.getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
								b.getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 2);
		
	}
	
	public void dispose() {
		
		aura.dispose();
		
	}
	
}
