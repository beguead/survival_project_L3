package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;

import box2dLight.ConeLight;
import dungeon.Maze;
import items.BlueCore;
import items.Core;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player extends Character {
	
	public boolean moving;

	private Core core;
	private ConeLight cone_light1;
	
	public Player() {
		super();
		
		current_frame = Assets.player.getKeyFrame(GameScreen.state_time);
		
		/*Movements*/
		speed = 1f;
		moving = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), current_frame.getRegionWidth() / (2 * Constants.PPM), false, Constants.PLAYER_FILTER,
												(short)(Constants.WALL_FILTER | Constants.ITEM_FILTER | Constants.SENSOR_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER) , this	);
		
		/*Lights*/
		aura = new Aura(body, Color.WHITE, 0.2f);
		cone_light1 = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		cone_light1.setSoftnessLength(0.1f);
		
	}
	
	public void dispose() {
		
		aura.dispose();
		cone_light1.dispose();
		GameScreen.world.destroyBody(body);
		
	}
	
	private void dropCore() {
		
		if (core instanceof BlueCore)
			((BlueCore)(core)).setMirrorLightActive(false);
		
		core.setBodyActive(true);
		core = null;
		
	}
	
	public void setCore(Core core) {
		
		if (this.core != null) dropCore();
		
		core.setBodyActive(false);
		this.core = core;
		
		if (core instanceof BlueCore) {
			
			BlueCore bc = ((BlueCore)(core));
			bc.setMirrorLightActive(true);
			bc.majMirrorLightDirection((float)(direction * Constants.TO_DEGREE));
			
		}
		
		aura.setColor(core.getColor());
		cone_light1.setColor(core.getColor());
		cone_light1.setDistance(core.getDistance());
		cone_light1.setConeDegree(core.getConeDegree());
		
	}

	public void setDirection(double angle) {
		
		direction = angle;
		
		float angle_in_degree = (float)(angle * Constants.TO_DEGREE);
		cone_light1.setDirection(angle_in_degree);
		if (core instanceof BlueCore) ((BlueCore)(core)).majMirrorLightDirection(angle_in_degree);
		
	}

	public void update() {
		
		current_frame = Assets.player.getKeyFrame(GameScreen.state_time);
		
		if (moving) move();
		else body.setLinearVelocity(0f, 0f);
		cone_light1.setPosition(getPosition());
		core.setPosition(getPosition());
		
	}
}
