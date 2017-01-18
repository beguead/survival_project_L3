package items;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import dungeon.Maze;
import screens.GameScreen;
import utilities.BodyCreator;
import utilities.Constants;

public abstract class Core {
	
	protected Sprite sprite;
	protected Body body;
	public boolean is_on_the_map;
	
	public Core() {
		
		body = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), 0.1f, true, Constants.SENSOR_FILTER, Constants.PLAYER_FILTER , this);
		is_on_the_map = !(this instanceof WhiteCore);
		
	}
	
	public abstract float getDistance();
	
	public abstract float getConeDegree();
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f); }
	
	protected void update() { sprite.setPosition(body.getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2, body.getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2); }
	
	protected void render() { sprite.draw(GameScreen.batch); }
	
	public void updateAndRender() {
		
		if (is_on_the_map){
		
		update();
		render();
		
		}
		
	}
	
	public void setBodyActive(boolean active) { body.setActive(active); }
	
	public Body getBody() { return body; };

}
