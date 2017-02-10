package dungeon;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Portal {
	
	private Body body;
	private Aura aura;
	
	public Portal() {
		
		body = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), 0.5f, true, Constants.SENSOR_FILTER, Constants.PLAYER_FILTER , this);
		body.setAwake(false);
		
		aura = new Aura(body, Color.CYAN, 0f);
		
	}
	
	public void render() {
			
			aura.setDistance(0.5f * (Assets.portal_standing.getKeyFrameIndex(GameScreen.state_time) % 2 + 1));	
			MainGame.batch.draw(	Assets.portal_standing.getKeyFrame(GameScreen.state_time),
									body.getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2,
									body.getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2 );
			
	}

}
