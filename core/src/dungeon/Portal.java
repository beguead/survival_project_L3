package dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import interfaces.IUpdateAndRender;
import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Portal implements IUpdateAndRender {
	
	private Body body;
	private Aura aura;
	
	private TextureRegion current_frame;
	
	public Portal() {
		
		current_frame = Assets.portal_standing.getKeyFrame(GameScreen.state_time);
		
		body = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), current_frame.getRegionWidth() / (2 * Constants.PPM), true, Constants.SENSOR_FILTER, Constants.PLAYER_FILTER , this);
		body.setAwake(false);
		
		aura = new Aura(body, Color.CYAN, 0f);
		
	}
	
	public void render() {
			
			aura.setDistance(0.1f * (Assets.portal_standing.getKeyFrameIndex(GameScreen.state_time) + 1));	
			MainGame.batch.draw(	current_frame,
									body.getPosition().x * Constants.PPM - current_frame.getRegionWidth() / 2,
									body.getPosition().y * Constants.PPM - current_frame.getRegionHeight() / 2 );
			
	}

	@Override
	public void update() { current_frame = Assets.portal_standing.getKeyFrame(GameScreen.state_time); }

}
