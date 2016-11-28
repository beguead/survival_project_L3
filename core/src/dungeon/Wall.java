package dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import utilities.BodyCreator;
import utilities.Constants;

public class Wall extends MapObject {
	
	public Wall(int x, int y) {

		BodyCreator.createBoxBody(	BodyDef.BodyType.StaticBody, new Vector2(x, y),
									Constants.TILE_WIDTH / (2 * Constants.PPM),
									Constants.TILE_HEIGHT / (2 * Constants.PPM)	);
		
	}
	
}
