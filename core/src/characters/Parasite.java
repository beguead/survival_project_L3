package characters;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import dungeon.Maze;
import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;
import utilities.MathExtension;

public class Parasite extends Character {
	
	private static enum states {normal, hunting, stunned};
	private states state;
	
	private ConcurrentLinkedQueue<Vector2> path;
	private Vector2 current_target;
	
	private long timer;
	
	private boolean near_of_the_player;
	
	public Parasite() {

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		body = GameScreen.world.createBody(bdef);
			
		CircleShape body_fixture = BodyCreator.createCircleShape(body, Assets.parasite.getKeyFrame(GameScreen.state_time).getRegionWidth() / (1.5f * Constants.PPM));
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
		
		aura = new Aura(body, Color.WHITE, 0.5f);
		
		isNearOfThePlayer(false);
		setState(states.normal);
		
	}
	
	public void setState(states s) {
		
		state = s;
		path = null;
		
		switch (state) {
		
			case normal : {
				
				aura.setActive(false);
				speed = 0.2f;
				timer = 0l;
				break;
				
			}
			
			case hunting : {
				
				aura.setActive(true);
				aura.setColor(Color.RED);
				speed = 1f;
				break;
				
			}
			
			case stunned : {
				
				aura.setActive(true);
				aura.setColor(Color.CHARTREUSE);
				speed = 0.2f;
				direction *= -1;
				timer = System.currentTimeMillis() + 2000;
				break;
				
			}
		}	
	}
	
	public void setStunned() { setState(states.stunned); }
	
	@Override
	public void update() {
		
		if (near_of_the_player && state == states.normal) setState( states.hunting);
		
		if (state == states.hunting) {
			
			path = Maze.findShortestPath(body.getPosition(), Maze.player.getPosition());
			if (path != null) current_target = path.poll();
				
		} else if (timer != 0  && System.currentTimeMillis() > timer) setState(states.normal);
		
		if (current_target == null || path == null) {
			
			if (state == states.hunting) path = Maze.findShortestPath(body.getPosition(), Maze.player.getPosition());
			else path = Maze.getRandomPath(body.getPosition());
			
			if (path != null) current_target = path.poll();
			
			return;
			
		}
		
		if ((body.getPosition().x > current_target.x + 0.30f && body.getPosition().x < current_target.x + 0.70f && body.getPosition().y > current_target.y + 0.30f && body.getPosition().y < current_target.y + 0.70f) && ((current_target = path.poll()) != null))
			direction = MathExtension.getAngle(getPosition().scl(Constants.PPM), new Vector2((current_target.x + 0.5f) * Constants.PPM, (current_target.y + 0.5f) * Constants.PPM));
		
		move();

	}
	
	@Override
	public void render() {
		
		float width = Assets.parasite.getKeyFrame(GameScreen.state_time).getRegionWidth() / 2;
		float height = Assets.parasite.getKeyFrame(GameScreen.state_time).getRegionHeight() / 2;
		
		MainGame.batch.draw(	Assets.parasite.getKeyFrame(GameScreen.state_time),
								getPosition().x * Constants.PPM - width, getPosition().y * Constants.PPM - height, width, height,
								Assets.parasite.getKeyFrame(GameScreen.state_time).getRegionWidth(), Assets.parasite.getKeyFrame(GameScreen.state_time).getRegionHeight(),
								2f, 2f, (float)(direction * Constants.TO_DEGREE)	);

	}
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f);}
	
	public void isNearOfThePlayer(boolean near) { near_of_the_player = near;}

}
