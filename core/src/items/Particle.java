package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import interfaces.IUpdateAndRender;
import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Particle implements IUpdateAndRender {
	
	private Aura aura;
	private Body body;
	
	public boolean catched;
	
	public Particle() {
		
		catched = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), Assets.particle.getKeyFrame(GameScreen.state_time).getRegionWidth() / (2 * Constants.PPM), true,
												Constants.ITEM_FILTER, (short)(Constants.WALL_FILTER | Constants.PLAYER_FILTER), this	);
		
		aura = new Aura(body, Color.ORANGE, 0.1f);
		
		body.setAngularVelocity(0.3f);
		
	}
	
	public void dispose() {
			
		aura.setActive(false);
		aura.dispose();
		GameScreen.world.destroyBody(body);
	
	}

	@Override
	public void update() { aura.setDistance(0.1f * (Assets.particle.getKeyFrameIndex((GameScreen.state_time)) + 1)); }
	
	@Override
	public void render() {
		
		MainGame.batch.draw(	Assets.particle.getKeyFrame(GameScreen.state_time),
								body.getPosition().x * Constants.PPM - Assets.particle.getKeyFrame(GameScreen.state_time).getRegionWidth() / 2,
								body.getPosition().y * Constants.PPM - Assets.particle.getKeyFrame(GameScreen.state_time).getRegionHeight() / 2 );
		
	}

	public void updateAndRender() {
		
		update();
		render();
		
	}

}
