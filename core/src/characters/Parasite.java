package characters;

import com.badlogic.gdx.graphics.Color;
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

public class Parasite extends AI {
	
	private enum parasite_state {normal, escape, hunt};
	
	private Aura aura;
	private boolean rage;

	public Parasite() {
		super();
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		body = GameScreen.world.createBody(bdef);
			
		CircleShape shape1 = BodyCreator.createCircleShape(body, 0.3f);
		FixtureDef fdef1 = BodyCreator.createFixtureDef(shape1);
		fdef1.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef1.filter.maskBits = (short)(Constants.WALL_FILTER | Constants.PLAYER_FILTER);
		body.createFixture(fdef1).setUserData(this);
		shape1.dispose();
		
		CircleShape shape2 = BodyCreator.createCircleShape(body, 1.5f);
		FixtureDef fdef2 = BodyCreator.createFixtureDef(shape2);
		fdef2.isSensor = true;
		fdef2.filter.categoryBits = Constants.SENSOR_FILTER;
		fdef2.filter.maskBits = (short)(Constants.PLAYER_FILTER);
		body.createFixture(fdef2).setUserData(this);
		shape2.dispose();
		
		Vector2 initial_position = Maze.getRandomFreePosition();
		body.setTransform(initial_position.x, initial_position.y, 0f);
		
		/*Lights*/
		aura = new Aura(body, Color.PURPLE, 1f);
		setRage(false);
		
	}
	
	public void setRage(boolean rage) { 

		this.rage = rage;
	
			if (rage) { 
				
				aura.setActive(true);
				timer = System.currentTimeMillis() + 3000;
				speed = 1.2f;
		
			} else {
				
				aura.setActive(false);
				speed = 0.4f;
				
			}
		
		current_target = updatePath();
		
	}

	public long getTimer() { return timer; };

	@Override
	protected Vector2 updatePath() {

		if (!rage) path = Maze.getRandomPath(body.getPosition());
		else path = Maze.findShortestPath(body.getPosition(), Maze.player.getPosition());
		
		return path.poll();
		
	}
	
	@Override
	public void dispose() { aura.dispose(); }
	
	@Override
	protected void update() {
		
		if (GameScreen.ray_handler.pointAtLight(getPosition().x, getPosition().y)) {

			if (timer == 0) timer = System.currentTimeMillis() + 3000;
			else if (System.currentTimeMillis() > timer);
			
		}
		
		super.update();

		double absolute_value = Math.abs(direction * Constants.TO_DEGREE);
		
		if (absolute_value > 45) {
			
			if (absolute_value > 135) animation = Assets.parasite_left;
			else	if (direction > 0) animation = Assets.parasite_up;
					else animation = Assets.parasite_down;
			
		} else animation = Assets.parasite_right;
		
		
	}

}
