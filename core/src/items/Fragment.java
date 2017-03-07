package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import interfaces.IUpdateAndRender;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Fragment implements IUpdateAndRender {
	
	private Aura aura;
	private Body body;
	
	public boolean catched;
	
	public Fragment() {
		
		catched = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), Assets.particle.getKeyFrame(GameScreen.getStateTime()).getRegionWidth() / (2 * Constants.PPM),
												true, Constants.ITEM_FILTER, Constants.PLAYER_FILTER, this	);
		
		aura = new Aura(body, Color.ORANGE, 0.1f);
		
	}
	
	public void dispose() { aura.dispose(); }

	@Override
	public void update() {
		
		if (!catched) aura.setDistance(0.1f * (Assets.particle.getKeyFrameIndex(GameScreen.getStateTime()) + 1));
		else {
			
			aura.setActive(false);
			GameScreen.world.destroyBody(body);
			
		}
	}
	
	@Override
	public void render() {
		
		TextureRegion frame = Assets.particle.getKeyFrame(GameScreen.getStateTime());
		GameScreen.game_batch.draw(frame, body.getPosition().x * Constants.PPM - frame.getRegionWidth() / 2, body.getPosition().y * Constants.PPM - frame.getRegionHeight() / 2 );
		
	}

	public void updateAndRender() {
		
		update();
		render();
		
	}

}
