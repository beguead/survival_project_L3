package characters;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import dungeon.Maze;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;
import utilities.MathExtension;

public class Parasite extends Character {
	
	private static enum states {normal, hunting, lost};
	private states state;
	
	private ConcurrentLinkedQueue<Vector2> path;
	private Vector2 current_target;
	
	private boolean near_of_the_player;
	
	public Parasite() {

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		body = GameScreen.world.createBody(bdef);
			
		CircleShape body_fixture = BodyCreator.createCircleShape(body, 0.3f);
		FixtureDef fdef1 = BodyCreator.createFixtureDef(body_fixture);
		fdef1.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef1.filter.maskBits = (short)(Constants.WALL_FILTER | Constants.PLAYER_FILTER | Constants.LIGHT_FILTER);
		body.createFixture(fdef1).setUserData(this);
		body_fixture.dispose();
		
		CircleShape detection_field_fixture = BodyCreator.createCircleShape(body, 1.2f);
		FixtureDef fdef2 = BodyCreator.createFixtureDef(detection_field_fixture);
		fdef2.isSensor = true;
		fdef2.filter.categoryBits = Constants.SENSOR_FILTER;
		fdef2.filter.maskBits = (short)(Constants.PLAYER_FILTER);
		body.createFixture(fdef2).setUserData(this);
		detection_field_fixture.dispose();
		
		Vector2 initial_position = Maze.getRandomFreePosition();
		body.setTransform(initial_position.x, initial_position.y, 0f);
		
		aura = new Aura(body, Color.WHITE, 0f);
		
		isNearOfThePlayer(false);
		setState(states.normal);
		updatePath();
		
	}
	
	public void setState(states s) {
		
		state = s;
		
		switch (state) {
		
			case normal : {
				
				aura.setActive(false);
				path = null;
				speed = 0.2f;
				break;
				
			}
			
			case hunting : {
				
				aura.setActive(true);
				aura.setColor(Color.RED);
				path = null;
				speed = 1f;
				break;
				
			}
			
			case lost : {
				
				aura.setColor(Color.WHITE);
				speed = 0;
				break;
				
			}
		}	
	}

	private void updatePath() {

		if (state == states.hunting) path = Maze.findShortestPath(body.getPosition(), Player.getInstance().getPosition());
		else path = Maze.getRandomPath(body.getPosition());
		
		if (path != null) current_target = path.poll();
		else current_target = null;
		
	}
	
	@Override
	public void update() {
		
		if (state == states.normal) {
			
			if (near_of_the_player) setState(states.hunting);
				
		}
		
		if (path == null) updatePath();
		else if (body.getPosition().x > current_target.x + 0.30f && body.getPosition().x < current_target.x + 0.70f && body.getPosition().y > current_target.y + 0.30f && body.getPosition().y < current_target.y + 0.70f) {
			
			current_target = path.poll();
			if (current_target  == null) updatePath();
			
		}
		
		if (current_target != null) direction = MathExtension.getAngle(getPosition().scl(Constants.PPM), new Vector2((current_target.x + 0.5f) * Constants.PPM, (current_target.y + 0.5f) * Constants.PPM));
		
		move();

	}
	
	@Override
	public void render() {
		
		final float half_sprite = Constants.SPRITE_SIZE / 2;
		
		GameScreen.batch.draw(	Assets.parasite.getKeyFrame(GameScreen.state_time),
								getPosition().x * Constants.PPM - half_sprite, getPosition().y * Constants.PPM - half_sprite,
								half_sprite, half_sprite, Constants.PPM, Constants.PPM, 1f, 1f, (float)(direction * Constants.TO_DEGREE), false);

	}
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f);}
	
	public void isNearOfThePlayer(boolean near) { near_of_the_player = near;}

}
