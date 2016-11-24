package dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import utilities.BodyCreator;
import utilities.Constants;

public class Wall extends MapObject{
	
	private Body body;
	
	public Wall(float x, float y) {
		
		body = BodyCreator.createStaticBoxBody(body, new Vector2(x, y), Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		
	}
	
}
