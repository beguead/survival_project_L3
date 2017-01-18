package dungeon;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;

import utilities.BodyCreator;
import utilities.Constants;

public class Wall extends MapObject {
	
	public Wall(float x, float y) {
			
		BodyCreator.createBoxBody(	BodyDef.BodyType.StaticBody, new Vector2(x, y), Constants.TILE_WIDTH / (2 * Constants.PPM), Constants.TILE_WIDTH / (2 * Constants.PPM),
									false, Constants.WALL_FILTER, (short)(Constants.PLAYER_FILTER | Constants.LIGHT_FILTER | Constants.ENEMY_FILTER | Constants.ITEM_FILTER), this);
		
	}
}
