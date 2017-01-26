package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import interfaces.IUpdateAndRender;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Particle implements IUpdateAndRender {
	
	private Aura aura;
	private Body body;
	private Animation animation;
	
	public boolean catched;
	
	public Particle() {
		
		catched = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), 0.2f, true,
												Constants.ITEM_FILTER, (short)(Constants.WALL_FILTER | Constants.PLAYER_FILTER), this	);
		
		aura = new Aura(body, Color.ORANGE, 0.1f);
		
		body.setAngularVelocity(0.3f);
		
		animation = Assets.particle;
		
	}
	
	public void dispose() {
			
		aura.setActive(false);
		aura.dispose();
		GameScreen.world.destroyBody(body);
	
	}

	@Override
	public void update() { aura.setDistance(0.1f * (animation.getKeyFrameIndex((GameScreen.state_time)) + 1)); }
	
	@Override
	public void render() {
		
		GameScreen.batch.draw(	animation.getKeyFrame(GameScreen.state_time),
								body.getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2,
								body.getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2 );
		
	}

	public void updateAndRender() {
		
		update();
		render();
		
	}

}
