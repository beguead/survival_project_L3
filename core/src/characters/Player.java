package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;

import box2dLight.ConeLight;
import dungeon.Maze;
import items.BlueCore;
import items.Core;
import items.GreenCore;
import items.YellowCore;
import lights.Aura;
import main.MainGame;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player extends Character {
	
	public boolean moving;

	private Core core;
	private ConeLight cone_light1;
	private Sprite sprite;
	
	public Player() {
		super();
		sprite = Assets.player_base;
		
		/*Movements*/
		speed = 1f;
		moving = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), sprite.getRegionWidth() / (2 * Constants.PPM), false, Constants.PLAYER_FILTER,
												(short)(Constants.WALL_FILTER | Constants.ITEM_FILTER | Constants.SENSOR_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER) , this	);
		
		/*Lights*/
		aura = new Aura(body, Color.WHITE, 0.2f);
		cone_light1 = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		cone_light1.setSoftnessLength(0.1f);
		
	}
	
	public void dispose() {
		
		aura.dispose();
		cone_light1.dispose();
		
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
			sprite = Assets.player_blue;
			bc.setMirrorLightActive(true);
			bc.majMirrorLightDirection((float)(direction * Constants.TO_DEGREE));
			
		} else	if (core instanceof GreenCore) sprite = Assets.player_green;
				else	if (core instanceof YellowCore) sprite = Assets.player_yellow;
						else  sprite = Assets.player_base;	
		
		aura.setColor(core.getColor());
		cone_light1.setColor(core.getColor());
		cone_light1.setDistance(core.getDistance());
		cone_light1.setConeDegree(core.getConeDegree());
	    sprite.setRotation((float)(direction * Constants.TO_DEGREE));
		
	}

	public void setDirection(double angle) {
		
		direction = angle;
		
		float angle_in_degree = (float)(angle * Constants.TO_DEGREE);
		cone_light1.setDirection(angle_in_degree);
		if (core instanceof BlueCore) ((BlueCore)(core)).majMirrorLightDirection(angle_in_degree);
	    sprite.setRotation(angle_in_degree);
		
	}

	public void update() {
		
		if (moving) move();
		else body.setLinearVelocity(0f, 0f);
		cone_light1.setPosition(getPosition());
		core.setPosition(getPosition());
	    sprite.setPosition(getPosition().x * Constants.PPM - sprite.getWidth() * 0.5f, getPosition().y * Constants.PPM - sprite.getHeight() * 0.5f);
		
	}
	
	public void render() { sprite.draw(MainGame.batch); }
	
}
