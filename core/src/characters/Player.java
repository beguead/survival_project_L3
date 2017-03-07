package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;

import box2dLight.ConeLight;
import dungeon.Maze;
import interfaces.MirrorCore;
import items.BlueCore;
import items.Core;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player extends Character {
	
	private boolean moving;
	private Core core;
	private ConeLight cone_light;
	private Animation current_animation;
	
	public static Player getInstance() { return new Player(); }
	
	private Player() {
		super();
		
		current_animation = Assets.player_normal;
		current_frame = current_animation.getKeyFrame(GameScreen.getStateTime());
		
		/*Movements*/
		speed = 1f;
		moving = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), current_frame.getRegionWidth() / (2.5f * Constants.PPM), false, Constants.PLAYER_FILTER,
												(short)(Constants.WALL_FILTER | Constants.ITEM_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER) , this	);
		
		/*Lights*/
		aura = new Aura(body, Color.CYAN, 0.2f);
		cone_light = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		cone_light.setSoftnessLength(0.1f);
		
		setHunted(false);

	}
	
	public void setHunted(boolean b) { current_animation = b ? Assets.player_hunted : Assets.player_normal; }
	
	public void dispose() {
		
		aura.dispose();
		cone_light.dispose();
		
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
		
		if (core instanceof MirrorCore) {
			
			MirrorCore mc = (MirrorCore)core;
			mc.setMirrorLightActive(true);
			mc.majMirrorLightDirection((float)(direction * Constants.TO_DEGREE));
			
		}
		
		aura.setColor(core.getColor());
		cone_light.setColor(core.getColor());
		cone_light.setDistance(core.getDistance());
		cone_light.setConeDegree(core.getConeDegree());
		
	}

	public void setDirection(float angle) {
		
		direction = angle;
		
		float angle_in_degree = (float)(angle * Constants.TO_DEGREE);
		cone_light.setDirection(angle_in_degree);
		if (core instanceof MirrorCore) ((MirrorCore)(core)).majMirrorLightDirection(angle_in_degree);
		
	}

	public void update() {
		
		current_frame = current_animation.getKeyFrame(GameScreen.getStateTime());
		
		if (moving) move();
		else body.setLinearVelocity(0f, 0f);
		cone_light.setPosition(getPosition());
		core.setPosition(getPosition());
		
	}
	
	public void setMoving(boolean moving) { this.moving = moving; }
	
}
