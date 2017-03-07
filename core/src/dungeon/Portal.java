package dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Portal {
	
	private Aura aura;
	private Body body;
	
	private Portal() {
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), Assets.portal.getKeyFrame(GameScreen.getStateTime()).getRegionWidth() / (2 * Constants.PPM),
												true, Constants.ITEM_FILTER, Constants.PLAYER_FILTER , this);
		body.setAwake(false);
		
		aura = new Aura(body, Color.WHITE, 0f);
		
	}
	
	public static Portal getInstance() { return new Portal(); }
	
	public void render() {
			
		TextureRegion frame = Assets.portal.getKeyFrame(GameScreen.getStateTime());
		aura.setDistance((Assets.portal.getKeyFrameIndex(GameScreen.getStateTime()) % 4 + 1) * 0.35f);	
		GameScreen.game_batch.draw(	frame, body.getPosition().x * Constants.PPM - frame.getRegionWidth() / 2, body.getPosition().y * Constants.PPM - frame.getRegionHeight() / 2 );
			
	}
}
