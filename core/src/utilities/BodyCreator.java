package utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;

import screens.GameScreen;

public final class BodyCreator {
	
	private BodyCreator() {}
	
	public static Body createBoxBody(BodyDef.BodyType type, Vector2 initial_position, float width, float height, boolean sensor, short cBits, short mBits, Object o) {
		
		BodyDef bd = new BodyDef();
		bd.type = type;

		Body body = GameScreen.world.createBody(bd);
			
		PolygonShape shape = createBoxShape(body, width, height);
		FixtureDef fdef = createFixtureDef(shape);
		fdef.isSensor = sensor;
		fdef.filter.categoryBits = cBits; //Is a
		fdef.filter.maskBits = mBits; //Collides with
		body.createFixture(fdef).setUserData(o);
		shape.dispose();
	
		body.setTransform(initial_position.x, initial_position.y, 0f);
		
		return body;
		
	}
	
	public static Body createCircleBody(BodyDef.BodyType type, Vector2 initial_position, float radius, boolean sensor, short cBits, short mBits, Object o) {
		
		BodyDef bdef = new BodyDef();
		bdef.type = type;
		
		Body body = GameScreen.world.createBody(bdef);
			
		CircleShape shape = createCircleShape(body, radius);
		FixtureDef fdef = createFixtureDef(shape);
		fdef.isSensor = sensor;
		fdef.filter.categoryBits = cBits; //Is a
		fdef.filter.maskBits = mBits; //Collides with
		body.createFixture(fdef).setUserData(o);
		shape.dispose();
			
		body.setTransform(initial_position.x, initial_position.y, 0f);
			
		
		return body;
		
	}
	
	private static PolygonShape createBoxShape(Body b, float width, float height) {
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width, height, new Vector2(b.getPosition().x + width, b.getPosition().y + height), 0f);
		
		return shape;
		
	}
	
	private static CircleShape createCircleShape(Body b, float radius) {
		
		CircleShape shape = new CircleShape();
		shape.setRadius(radius);
		shape.setPosition(b.getPosition());
		
		return shape;
		
	}
	
	private static FixtureDef createFixtureDef(Shape shape) {
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.density = 0f;
		fdef.friction = 0f;
		fdef.restitution = 0f;
		
		return fdef;
		
	}
	
}
