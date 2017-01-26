package dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.utils.Disposable;

import interfaces.IUpdateAndRender;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class LightBarrier extends MapObject implements Disposable, IUpdateAndRender {
	
	private Aura aura;
	private Body barrier;
	private Body base;
	private boolean horizontal;
	private Sprite s = new Sprite(Assets.light_barrier_base);
	
	public LightBarrier(float x, float y, boolean horizontal) {
		
		float width, height;
		
		this.horizontal = horizontal;
		
		s.setPosition(x * Constants.PPM , y * Constants.PPM);
		
		base = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, new Vector2(x + 0.5f, y + 0.5f), 0.5f, true, (short)(0), (short)(0) , this);
		
		if (horizontal) {
			
			s.setRotation(90f);
			
			width = Constants.TILE_WIDTH / (2 * Constants.PPM);
			height = Constants.TILE_WIDTH / (4 * Constants.PPM);
			y += 0.25f;
			
		} else {
			
			width = Constants.TILE_WIDTH / (4 * Constants.PPM);
			height = Constants.TILE_WIDTH / (2 * Constants.PPM);
			x += 0.25f;
			
		}
		
		barrier = BodyCreator.createBoxBody(	BodyDef.BodyType.StaticBody, new Vector2(x, y), width, height, false,
												Constants.WALL_FILTER, (short)(Constants.PLAYER_FILTER | Constants.ENEMY_FILTER), this	);
		
		aura = new Aura(base, Color.FOREST, 0.5f);
		aura.setActive(false);
		barrier.setActive(false);
		
	}

	@Override
	public void dispose() {
		
		aura.dispose();
	
	}

	@Override
	public void update() {
		
	}

	@Override
	public void render() {
		
		s.draw(GameScreen.batch);
		
		if (barrier.isActive()) {
			
			float x, y, rotation;
			if (horizontal) {
				
				rotation = 90f;
				x = barrier.getPosition().x;
				y = barrier.getPosition().y - 0.25f;
				
			}
			else {
				
				rotation = 0f;
				x = barrier.getPosition().x - 0.25f;
				y = barrier.getPosition().y;
				
			}
			
			final float half_sprite = Constants.SPRITE_SIZE / 2;
			
			GameScreen.batch.draw(	Assets.barrier_standing.getKeyFrame(GameScreen.state_time), x * Constants.PPM, y * Constants.PPM,
									half_sprite, half_sprite, Constants.PPM, Constants.PPM, 1f, 1f, rotation, false);
			
		}
		
		
	}
	
	public void inverseState() {
		
		boolean tmp = !barrier.isActive();
		aura.setActive(tmp);
		barrier.setActive(tmp);
		
	}

}
