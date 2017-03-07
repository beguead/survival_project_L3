package characters;

import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import dungeon.Maze;
import lights.Aura;
import pathfinding.CannotFindPathException;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;
import utilities.Constants.PARASITE_STATES;
import utilities.MathExtension;

public class Parasite extends Character {
	
	private PARASITE_STATES state;
	private long timer;
	
	private ConcurrentLinkedQueue<Vector2> path;
	private Vector2 target;
	
	private boolean near_player;
	
	public Parasite() {
		
		current_frame = Assets.parasite.getKeyFrame(GameScreen.getStateTime());

		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		body = GameScreen.world.createBody(bdef);
			
		CircleShape body_fixture = BodyCreator.createCircleShape(body, current_frame.getRegionWidth() / (4f * Constants.PPM));
		FixtureDef fdef = BodyCreator.createFixtureDef(body_fixture);
		fdef.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef.filter.maskBits = Constants.WALL_FILTER | Constants.PLAYER_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER;
		body.createFixture(fdef).setUserData(this);
		body_fixture.dispose();
		
		float radius = 8;
		Vector2 vertices[] = new Vector2[6];
		vertices[0] = new Vector2(0f, 0f);
		  
		for (int i = 0; i < 5; ++i) {
			 
		     double angle = i / 6.0 * 90 / Constants.TO_DEGREE;
		     vertices[i + 1] = new Vector2((float)(radius * Math.cos(angle) / 7), (float)(radius * Math.sin(angle) / 7));
		      
		}
		 
		PolygonShape detection_field_fixture1 = new PolygonShape();
		detection_field_fixture1.set(vertices);
		 
		CircleShape detection_field_fixture2 = BodyCreator.createCircleShape(body, 0.5f);
		
		FixtureDef fdef1 = BodyCreator.createFixtureDef(detection_field_fixture1);
		FixtureDef fdef2 = BodyCreator.createFixtureDef(detection_field_fixture2);
		
		fdef1.isSensor = fdef2.isSensor = true;
		fdef1.filter.categoryBits = fdef2.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef1.filter.maskBits = Constants.PLAYER_FILTER ;
		fdef2.filter.maskBits = Constants.PLAYER_FILTER | Constants.WALL_FILTER ;
		body.createFixture(fdef1).setUserData(this);
		body.createFixture(fdef2).setUserData(this);
		detection_field_fixture1.dispose();
		detection_field_fixture2.dispose();
		
		Vector2 initial_position = Maze.getRandomFreePosition();
		body.setTransform(initial_position.x, initial_position.y, 0f);
		
		aura = new Aura(body, Color.WHITE, 0.4f);
		
		near_player = false;
		setState(PARASITE_STATES.normal);
		
	}
	
	public void setState(PARASITE_STATES state) {
		
		this.state = state;
		path = null;
		target = null;
		
		switch (state) {
		
			case normal : {
				
				aura.setActive(false);
				speed = 0.4f;
				break;
				
			}
			
			case hunting : {
				
				aura.setActive(true);
				aura.setColor(Color.RED);
				speed = 0.8f;
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
	
	public boolean isNearPlayer() { return near_player; }
	
	public void setNearPlayer(boolean near) {
		
		if (near_player = near) timer = 0;
		else timer = System.currentTimeMillis() + 7000;
		
	}
	
	@Override
	public void update() {
		
		current_frame = Assets.parasite.getKeyFrame(GameScreen.getStateTime());
			
		switch (state) {
		
			case normal : {
			
					if (near_player) setState(PARASITE_STATES.hunting);
					else {
						updatePath();
						move();
					}
				break;
			}
		
			case hunting : {
			
				if (timer != 0 && System.currentTimeMillis() > timer) setState(PARASITE_STATES.stunned);
				else {
					
					if (near_player) updateDirection(Maze.player.getPosition(), false);
					else updatePath();
					
					move();
				}
				break;
				
			}
			case stunned : if (System.currentTimeMillis() > timer) setState(PARASITE_STATES.normal);
			
		}
	}
	
	private void updatePath() {
		
		if (target == null) {
			
			try { 
				
				path = state == PARASITE_STATES.hunting ?
					Maze.findShortestPath(body.getPosition(), Maze.player.getPosition()) :
					Maze.findShortestPath(body.getPosition(), Maze.getRandomFreePosition());
					
				target = path.poll();
				updateDirection(target, true);
				
			}
			catch (CannotFindPathException e) { return; }
		
		} else {
			
			if (	MathExtension.getDistance(getPosition(), new Vector2(target.x + 0.5f, target.y + 0.5f)) < 0.3f
					&& (target = path.poll()) != null ) updateDirection(target, true);
			
		}
	}
	
	private void updateDirection(Vector2 to, boolean shift) {
		
		Vector2 v = shift ? new Vector2 ((to.x + 0.5f) * Constants.PPM, (to.y + 0.5f) * Constants.PPM) : to.scl(Constants.PPM) ;
		direction = MathExtension.getAngle(getPosition().scl(Constants.PPM), v);
		body.setTransform(getPosition(), (float) (direction - 35f / Constants.TO_DEGREE));
		
	}
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f); }

}
