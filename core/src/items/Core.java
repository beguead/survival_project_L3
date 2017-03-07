package items;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import dungeon.Maze;
import interfaces.IUpdateAndRender;
import lights.Aura;
import screens.GameScreen;
import utilities.Assets;
import utilities.BodyCreator;
import utilities.Constants;

public abstract class Core implements IUpdateAndRender {
	
	protected Aura aura;
	protected Body body;
	protected Sprite sprite;
	
	public Core() {
		
		if (this instanceof BlueCore) sprite = new Sprite(Assets.blue_core);
		else	if (this instanceof OrangeCore) sprite = new Sprite(Assets.orange_core);
				else 	if (this instanceof PurpleCore) sprite = new Sprite(Assets.purple_core);
						else 	if (this instanceof RedCore) sprite = new Sprite(Assets.red_core);
								else sprite = Assets.cyan_core;
		
		body = BodyCreator.createCircleBody(BodyDef.BodyType.StaticBody, Maze.getRandomFreePosition(), sprite.getWidth() / Constants.PPM, true, Constants.ITEM_FILTER, Constants.PLAYER_FILTER , this);
		aura = new Aura(body, getColor(), 0.5f);
		aura.setActive(false);
			
	}
	
	public abstract float getDistance();
	
	public abstract float getConeDegree();
	
	public abstract Color getColor();
	
	public void setPosition(Vector2 position) { body.setTransform(position.x, position.y, 0f); }
	
	@Override
	public void update() { sprite.setPosition(body.getPosition().x * Constants.PPM - sprite.getWidth() / 2, body.getPosition().y * Constants.PPM - sprite.getWidth() / 2); }
	
	@Override
	public void render() { if (body.isActive()) sprite.draw(GameScreen.game_batch); }
	
	public void setAuraActive(boolean active) { aura.setActive(active); }
	
	public void setBodyActive(boolean active) { body.setActive(active); }
	
}
