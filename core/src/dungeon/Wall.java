package dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

import utilities.BodyCreator;
import utilities.Constants;

public class Wall extends MapObject {
	
	public Wall(float x, float y, boolean wall_above, boolean wall_bellow) {
			
			float height = Constants.TILE_WIDTH / (2 * Constants.PPM);
			
			if (!wall_above) {
				
				BodyCreator.createBoxBody(BodyDef.BodyType.StaticBody, new Vector2(x, y + 0.75f), Constants.TILE_WIDTH / (2 * Constants.PPM), Constants.TILE_HEIGHT / (8 * Constants.PPM), true, Constants.HALF_WALL_FILTER, Constants.LIGHT_FILTER, this);
				height -= Constants.TILE_WIDTH / (8 * Constants.PPM);
				
			}
			
			if (!wall_bellow) BodyCreator.createBoxBody(BodyDef.BodyType.StaticBody, new Vector2(x, y + 0.25f), Constants.TILE_WIDTH / (2 * Constants.PPM), height - Constants.TILE_WIDTH / (8 * Constants.PPM), false, Constants.WALL_FILTER, (short)(Constants.CHARACTER_FILTER | Constants.LIGHT_FILTER), this);
			else BodyCreator.createBoxBody(BodyDef.BodyType.StaticBody, new Vector2(x, y), Constants.TILE_WIDTH / (2 * Constants.PPM), height, false, Constants.WALL_FILTER, (short)(Constants.CHARACTER_FILTER | Constants.LIGHT_FILTER), this);
		
	}
	
}
