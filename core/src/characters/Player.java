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
	
	//Singleton
	private static final Player INSTANCE = new Player();
	
	public boolean moving;

	private Core core;
	private ConeLight cone_light1;
	private ConeLight cone_light2;
	private Sprite sprite;
	
	private Player() {
		super();
		sprite = Assets.player_base;
		
		/*Movements*/
		speed = 1f;
		moving = false;
		
		body = BodyCreator.createCircleBody(	BodyDef.BodyType.DynamicBody, Maze.getRandomFreePosition(), 0.2f, false, Constants.PLAYER_FILTER,
												(short)(Constants.WALL_FILTER | Constants.ITEM_FILTER | Constants.SENSOR_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER) , this	);
		
		/*Lights*/
		aura = new Aura(body, Color.WHITE, 0.3f);
		cone_light1 = new ConeLight(GameScreen.ray_handler, 100, Color.WHITE, 0f, body.getPosition().x, body.getPosition().y, 0f, 0f);
		cone_light1.setSoftnessLength(0.1f);
		
	}
	
	public void dispose() {
		
		aura.dispose();
		cone_light1.dispose();
		
	}
	
	private void dropCore() {
		
		core.setActive(true);
		core = null;
		
	}
	
	public void setCore(Core core) {
		
		if (this.core != null) dropCore();
		
		core.setActive(false);
		this.core = core;
		
		Color c;
		if (core instanceof BlueCore) {
			
			c = Color.ROYAL;
			sprite = Assets.player_blue;
			
			cone_light2 = new ConeLight(GameScreen.ray_handler, 100, c, core.getDistance(), body.getPosition().x, body.getPosition().y, (float)(direction * Constants.TO_DEGREE) + 180, core.getConeDegree());
			cone_light2.setSoftnessLength(0.1f);
			
		} else {
			
			cone_light2 = null;
			
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
		cone_light1.setColor(c);
		cone_light1.setDistance(core.getDistance());
		cone_light1.setConeDegree(core.getConeDegree());
	    sprite.setRotation((float)(direction * Constants.TO_DEGREE));
		
	}

	public void setDirection(double angle) {
		
		direction = angle;
		
		float angle_in_degree = (float)(angle * Constants.TO_DEGREE);
		cone_light1.setDirection(angle_in_degree);
		if (cone_light2 != null) cone_light2.setDirection(angle_in_degree + 180);
	    sprite.setRotation(angle_in_degree);
		
	}

	public void update() {
		
		if (moving) move();
		else body.setLinearVelocity(0f, 0f);
		cone_light1.setPosition(getPosition());
		if (cone_light2 != null) cone_light2.setPosition(getPosition());
		core.setPosition(getPosition());
	    sprite.setPosition(getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2, getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2);
		
	}
	
	public void render() { sprite.draw(GameScreen.batch); }
	
    public static Player getInstance() { return INSTANCE; }
	
}
