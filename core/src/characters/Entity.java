package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Entity extends AI {
	
	private Aura aura;
	
	public Entity() {
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), 0.3f, false, Constants.ENEMY_FILTER,
												(short)(Constants.ENEMY_FILTER | Constants.WALL_FILTER | Constants.LIGHT_FILTER | Constants.PLAYER_FILTER) , this	);
		
		aura = new Aura(body, Color.CYAN, 0.2f);
		
		body.setAngularVelocity(0.3f);
		
		current_target = updatePath();
		speed = 1.5f;
		
		animation = Assets.entity;
		
	}

	@Override
	protected Vector2 updatePath() {
		
		path = Maze.getRandomPath(body.getPosition());
		return path.poll();
	}

	@Override
	protected void update() {
		super.update();
		
		aura.setDistance(0.2f * (animation.getKeyFrameIndex((GameScreen.state_time)) + 1));

	}
	
	@Override
	public void dispose() {
			
		aura.setActive(false);
		aura.dispose();
		GameScreen.world.destroyBody(body);
	
	}

}
