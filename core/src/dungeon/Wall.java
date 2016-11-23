package dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import screens.GameScreen;
import utilities.Assets;

public class Wall extends MapObject{
	
	protected BodyDef bodydef;
	public Body body;
	protected PolygonShape circleshape;
	protected FixtureDef fixture;
	
	public Wall(float x, float y) {
		
		bodydef = new BodyDef();
		
		bodydef.type = BodyDef.BodyType.StaticBody;

		body = GameScreen.world.createBody(bodydef);
		
		circleshape = new PolygonShape();
		circleshape.setAsBox(0.5f , 0.5f, new Vector2(x + 0.5f,y + 0.5f), 0f);
		
		fixture = new FixtureDef();
		fixture.shape = circleshape;
		fixture.density = 0f;
		fixture.friction = 0f;
		fixture.restitution = 0f;
		
		body.createFixture(fixture);
		circleshape.dispose();
		
	}
	
}
