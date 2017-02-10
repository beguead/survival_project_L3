package dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;

import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class LightBarrier extends MapObject implements Disposable {
	
	private Aura aura;
	private Body barrier;
	private Body base;
	private boolean vertical;
	private final Sprite s = new Sprite(Assets.light_barrier_base);
	
	public LightBarrier(float x, float y, boolean vertical) {
		
		float width, height;
		
		this.vertical = vertical;
		
		s.setPosition(x * Constants.PPM, y * Constants.PPM);
		
		base = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, new Vector2(x + 0.5f, y + 0.5f), s.getWidth() / (2 * Constants.PPM), true, (short)(0), (short)(0) , this);
		
		if (vertical) {
			
			width = Constants.TILE_WIDTH / (4 * Constants.PPM);
			height = Constants.TILE_WIDTH / (2 * Constants.PPM);
			x += 0.25f;
			
		} else {
			
			s.setRotation(90f);
			width = Constants.TILE_WIDTH / (2 * Constants.PPM);
			height = Constants.TILE_WIDTH / (4 * Constants.PPM);
			y += 0.25f;
			
		}
		
		barrier = BodyCreator.createBoxBody(	BodyDef.BodyType.StaticBody, new Vector2(x, y), width, height, false,
												Constants.WALL_FILTER, (short)(Constants.PLAYER_FILTER | Constants.ENEMY_FILTER), this	);
		
		aura = new Aura(base, Color.CYAN, 0.7f);
		aura.setActive(false);
		barrier.setActive(false);
		
	}

	@Override
	public void dispose() {
		
		aura.dispose();
	
	}


	public void renderBase() { s.draw(MainGame.batch); }
	
	public void renderBarrier() {
		
		if (barrier.isActive()) {
			
			float rotation, width, height;
			
			if (vertical) {
				
				rotation = 90f;
				width = Assets.light_barrier.getKeyFrame(GameScreen.state_time).getRegionWidth();
				height = Assets.light_barrier.getKeyFrame(GameScreen.state_time).getRegionHeight();
				
			} else {
				
				rotation = 0f;
				height = Assets.light_barrier.getKeyFrame(GameScreen.state_time).getRegionWidth();
				width = Assets.light_barrier.getKeyFrame(GameScreen.state_time).getRegionHeight();
				
			}
			
			MainGame.batch.draw(	Assets.light_barrier.getKeyFrame(GameScreen.state_time), base.getPosition().x * Constants.PPM - width / 2, base.getPosition().y  * Constants.PPM - height / 2,
									width, height);
			
		}
		
		
	}
	
	public void inverseState() {
		
		boolean tmp = !barrier.isActive();
		aura.setActive(tmp);
		barrier.setActive(tmp);
		
	}

}
