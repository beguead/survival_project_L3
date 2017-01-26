package items;


import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import interfaces.IUpdateAndRender;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public abstract class Core implements IUpdateAndRender {
	
	protected Sprite sprite;
	protected Body body;
	
	public Core() {
		
		body = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), 0.1f, true, Constants.ITEM_FILTER, Constants.PLAYER_FILTER , this);
		
		if (this instanceof BlueCore) sprite = new Sprite(Assets.blue_core);
		else	if (this instanceof GreenCore) sprite = new Sprite(Assets.green_core);
				else 	if (this instanceof YellowCore) sprite = new Sprite(Assets.yellow_core);
						else sprite = Assets.white_core;
			
	}
	
	public abstract float getDistance();
	
	public abstract float getConeDegree();
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f); }
	
	@Override
	public void update() { sprite.setPosition(body.getPosition().x * Constants.PPM - Constants.SPRITE_SIZE / 2, body.getPosition().y * Constants.PPM - Constants.SPRITE_SIZE / 2); }
	
	@Override
	public void render() { if (body.isActive()) sprite.draw(GameScreen.batch); }
	
	public void setActive(boolean active) { body.setActive(active); }

}
