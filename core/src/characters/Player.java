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
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public class Player extends Character {
	
	public boolean moving;
	
	private Aura aura;
	private Core core;
	private ConeLight cone_light;
	private Sprite sprite;
	
	public Player() {
		super();
		sprite = Assets.player_base;
		
		/*Movements*/
		speed = 1f;
		moving = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), 0.2f, false, Constants.PLAYER_FILTER,
												(short)(Constants.WALL_FILTER | Constants.ITEM_FILTER | Constants.SENSOR_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER) , this	);
		
		/*Lights*/
		aura = new Aura(body, Color.WHITE, 0.3f);
		cone_light = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		cone_light.setSoftnessLength(0.1f);
		
	}
	
	@Override
	public void dispose() {
		
		aura.dispose();
		cone_light.dispose();
		
	}
	
	private void dropCore() {
		
		core.is_on_the_map = true;
		core.setPosition(getPosition());
		core = null;
		
	}
	
	public void setCore(Core core) {
		
		Color c;
		
		if (this.core != null) dropCore();
		
		core.is_on_the_map = false;
		this.core = core;
		
		if (core instanceof BlueCore) {
			
			c = Color.CYAN;
			sprite = Assets.player_blue;
			
		} else {
			
			if (core instanceof GreenCore) {
				
				c = Color.GREEN;
				sprite = Assets.player_green;
			
			} else {
						
				if (core instanceof YellowCore) {
						
					c = Color.YELLOW;
					sprite = Assets.player_yellow;
							
				} else { 
						
					c = Color.WHITE;
					sprite = Assets.player_base;	
					
				}
			}
		}
		
		aura.setColor(c);
		cone_light.setColor(c);
		cone_light.setDistance(core.getDistance());
		cone_light.setConeDegree(core.getConeDegree());
	    sprite.setRotation((float)(direction * Constants.TO_DEGREE));
		
	}

	public void setDirection(double angle) {
		
		direction = angle;
		
		float angle_in_degree = (float)(angle * Constants.TO_DEGREE);
		cone_light.setDirection(angle_in_degree);
		if (core instanceof BlueCore) ((BlueCore)core).setDirection(angle_in_degree + 180);
	    sprite.setRotation(angle_in_degree);
		
	}

	protected void update() {
		
		if (moving) move();
		else body.setLinearVelocity(0f, 0f);
		cone_light.setPosition(getPosition());
		core.setPosition(getPosition());
	    sprite.setPosition(getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2, getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2);
		
	}
	
	protected void render() { sprite.draw(GameScreen.batch); }
	
}
