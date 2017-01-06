package characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import dungeon.Maze;
import interfaces.Enemy;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;
import utilities.Constants.berserker_state;
import utilities.MathExtension;

public class Berserker extends Character implements Enemy {
	
	private berserker_state state;
	private boolean can_hear_player;
	private long timer;
	
	private Aura aura;

	public Berserker() {
		
		timer = 0;
		
		/*Movements*/
		speed = 0.5f;
		current_frame = Assets.player_walk_down_animation.getKeyFrame(0);
		
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.DynamicBody;
		
		body = GameScreen.world.createBody(bdef);
			
		CircleShape shape1 = BodyCreator.createCircleShape(body, current_frame.getRegionWidth() / (3 * Constants.PPM));
		FixtureDef fdef1 = BodyCreator.createFixtureDef(shape1);
		fdef1.isSensor = false;
		fdef1.filter.categoryBits = Constants.ENEMY_FILTER;
		fdef1.filter.maskBits = (short)(Constants.WALL_FILTER | Constants.LIGHT_FILTER | Constants.CHARACTER_FILTER);
		body.createFixture(fdef1).setUserData(this);
		shape1.dispose();
		
		CircleShape shape2 = BodyCreator.createCircleShape(body, 2 * current_frame.getRegionWidth() / Constants.PPM);
		FixtureDef fdef2 = BodyCreator.createFixtureDef(shape2);
		fdef2.isSensor = true;
		fdef2.filter.categoryBits = Constants.SENSOR_FILTER;
		fdef2.filter.maskBits = (short)(Constants.CHARACTER_FILTER);
		body.createFixture(fdef2).setUserData(this);
		shape2.dispose();
			
		body.setTransform(3.5f, 1.5f, 0f);
		
		/*Lights*/
		aura = new Aura(body, Color.RED, 1.5f);
		
		majState(berserker_state.Normal);
		can_hear_player = false;
		
	}
	
	public void majState(berserker_state new_state) { 
		
		state = new_state;
		switch (state) {
		
			case Normal : { 

				aura.setActive(false);
				speed = 0.5f;
				direction = (Math.random() * 361) - 180;
				break;
		
			}
	
			case Rage : { 

				aura.setColor(Color.RED);				
				aura.setActive(true);
				speed = 1.5f;
				purchaseThePlayer();
				timer = System.currentTimeMillis() + 5000;
				break;
		
			}
	
			case Stunned : {
					
					aura.setColor(Color.YELLOW);
					speed = 0f;
					timer = System.currentTimeMillis() + 3000;
					break;
					
			}		
		}
	}
	
	public void purchaseThePlayer() {
		
		direction =		MathExtension.getAngle(body.getPosition().x * Constants.PPM, body.getPosition().y * Constants.PPM,
						Maze.player.getPosition().x * Constants.PPM, Maze.player.getPosition().y * Constants.PPM, false);
		
	}
	
	@Override
	public void updateAndRender() {
		move();
		
		if (state == berserker_state.Normal && can_hear_player && Maze.player.moving && !Maze.player.sneak) majState(berserker_state.Rage);
		else if (timer != 0 && System.currentTimeMillis() >= timer) {
			
				majState(berserker_state.Normal);	
				timer = 0;
			
			}
		
	}
	
	public void canHearThePlayer(boolean b) { can_hear_player = b; }
	
	public long getTimer() { return timer; };
	
	public berserker_state getState() { return state;};

}
