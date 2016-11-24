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
	
	private static enum body_type {Dynamic, Kinematic, Static};
	
	private BodyCreator() {}
	
	public static Body createDynamicBoxBody(Body b, Vector2 initial_position, float width, float height) {
		
		return createBoxBody(b, body_type.Dynamic, initial_position, width, height);
		
	}
	
	public static Body createKinematicBoxBody(Body b, Vector2 initial_position, float width, float height) {
		
		return createBoxBody(b, body_type.Kinematic, initial_position, width, height);
		
	}
	
	public static Body createStaticBoxBody(Body b, Vector2 initial_position, float width, float height) {
		
		return createBoxBody(b, body_type.Static, initial_position, width, height);
		
	}
	
	public static Body createDynamicCircleBody(Body b, Vector2 initial_position, float radius) {
		
		return createCircleBody(b, body_type.Dynamic, initial_position, radius);
		
	}
	
	public static Body createKinematicCircleBody(Body b, Vector2 initial_position, float radius) {
		
		return createCircleBody(b, body_type.Kinematic, initial_position, radius);
		
	}
	
	public static Body createStaticCircleBody(Body b, Vector2 initial_position, float radius) {
		
		return createCircleBody(b, body_type.Static, initial_position, radius);
		
	}
	
	private static Body createBoxBody(Body b, body_type type, Vector2 initial_position, float width, float height) {
		
		BodyDef bdef = createBodyDef(type);
		
		if (bdef != null) {

			b = GameScreen.world.createBody(bdef);
			
			PolygonShape shape = createBoxShape(b, width, height);
			FixtureDef fdef = createFixtureDef(shape);
			b.createFixture(fdef);
			shape.dispose();
			
			b.setTransform(initial_position.x, initial_position.y, 0f);
			
		}
		
		return b;
		
	}
	
	private static Body createCircleBody(Body b, body_type type, Vector2 initial_position, float radius) {
		
		BodyDef bdef = createBodyDef(type);
		
		if (bdef != null) {

			b = GameScreen.world.createBody(bdef);
			
			CircleShape shape = createCircleShape(b, radius);
			FixtureDef fdef = createFixtureDef(shape);
			b.createFixture(fdef);
			shape.dispose();
			
			b.setTransform(initial_position.x, initial_position.y, 0f);
			
		}
		
		return b;
		
	}
	
	private static BodyDef createBodyDef(body_type type) {
		
		BodyDef bd = new BodyDef();
		
		switch (type) {
		
			case Dynamic : bd.type = BodyDef.BodyType.DynamicBody;
			break;
			
			case Kinematic : bd.type = BodyDef.BodyType.KinematicBody;
			break;

			case Static : bd.type = BodyDef.BodyType.StaticBody;
			break;
			
			default : return null;
		
		}
		
		return bd;

	}
	
	private static PolygonShape createBoxShape(Body b, float width, float height) {
		
		float w = width / (2 * Constants.PPM);
		float h = height / (2 * Constants.PPM);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(w, h, new Vector2(b.getPosition().x + w, b.getPosition().y + h), 0f);
		
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
