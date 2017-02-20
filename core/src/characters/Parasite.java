package characters;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import dungeon.Maze;
import lights.Aura;
import pathfinding.CannotFindPathException;
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
		
		current_frame = Assets.parasite.getKeyFrame(GameScreen.state_time);

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		body = GameScreen.world.createBody(bdef);
			
		CircleShape body_fixture = BodyCreator.createCircleShape(body, current_frame.getRegionWidth() / (3f * Constants.PPM));
		FixtureDef fdef1 = BodyCreator.createFixtureDef(body_fixture);
		fdef1.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef1.filter.maskBits = (short)(Constants.WALL_FILTER | Constants.PLAYER_FILTER | Constants.LIGHT_FILTER);
		body.createFixture(fdef1).setUserData(this);
		body_fixture.dispose();
		
		CircleShape detection_field_fixture = BodyCreator.createCircleShape(body, 1f);
		FixtureDef fdef2 = BodyCreator.createFixtureDef(detection_field_fixture);
		fdef2.isSensor = true;
		fdef2.filter.categoryBits = Constants.SENSOR_FILTER;
		fdef2.filter.maskBits = (short)(Constants.PLAYER_FILTER);
		body.createFixture(fdef2).setUserData(this);
		detection_field_fixture.dispose();
		
		Vector2 initial_position = Maze.getRandomFreePosition();
		body.setTransform(initial_position.x, initial_position.y, 0f);
		
		aura = new Aura(body, Color.CHARTREUSE, 0.3f);
		
		isNearOfThePlayer(false);
		setState(states.normal);
		
	}
	
	public void setState(states s) {
		
		state = s;
		path = null;
		current_target = null;
		
		switch (state) {
		
			case normal : {
				
				aura.setActive(false);
				speed = 0.2f;
				break;
				
			}
			
			case hunting : {
				
				aura.setActive(true);
				aura.setColor(Color.CHARTREUSE);
				timer = System.currentTimeMillis() + 10000;
				speed = 0.9f;
				break;
				
			}
			
			case stunned : {
				
				aura.setActive(true);
				aura.setColor(Color.YELLOW);
				body.setLinearVelocity(0f, 0f);
				timer = System.currentTimeMillis() + 2000;
				break;
				
			}
		}	
	}
	
	public void setStunned() { setState(states.stunned); }
	
	@Override
	public void update() {
		
		current_frame = Assets.parasite.getKeyFrame(GameScreen.state_time);
		
		switch (state) {
		
			case normal : {
			
					if (near_of_the_player) setState(states.hunting);
					else {
						updatePath();
						move();
					}
				break;
			}
		
			case hunting : {
			
				if (System.currentTimeMillis() > timer) setState(states.stunned);
				else {
					
					if (!near_of_the_player) updatePath();
					else updateDirection(Maze.player.getPosition(), false);
					move();
					
				}
				break;
				
			}
			case stunned : if (System.currentTimeMillis() > timer) setState(states.normal);
		}
	}
	
	private void updatePath() {
		
		if (current_target == null) {
			
			try { 
				
				path = state == states.hunting ?
					Maze.findShortestPath(body.getPosition(), Maze.player.getPosition()) :
					Maze.findShortestPath(body.getPosition(), Maze.getRandomFreePosition());
					
				current_target = path.poll();
				updateDirection(current_target, true);
				
			}
			catch (CannotFindPathException e) { return; }
		
		} else {
			
			if (	body.getPosition().x > current_target.x + 0.30f && body.getPosition().x < current_target.x + 0.70f
					&& body.getPosition().y > current_target.y + 0.30f && body.getPosition().y < current_target.y + 0.70f
					&& (current_target = path.poll()) != null ) updateDirection(current_target, true);
			
		}
	}
	
	private void updateDirection(Vector2 to, boolean shift) {
		
		Vector2 v = shift ? new Vector2 ((to.x + 0.5f) * Constants.PPM, (to.y + 0.5f) * Constants.PPM) : to.scl(Constants.PPM) ;
		direction = MathExtension.getAngle(getPosition().scl(Constants.PPM), v);
		
	}
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f);}
	
	public void isNearOfThePlayer(boolean near) { near_of_the_player = near;}

}
