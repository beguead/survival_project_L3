package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public final class Assets {
	
	public static TextureRegion [][] split;
	
	private Assets() {
		
	}
	
	public static StaticTiledMapTile wall_tile;
	public static StaticTiledMapTile floor_tile;
	
	/*Player*/
	
	public static Animation player_walk_down_animation;
	public static Animation player_walk_left_animation;
	public static Animation player_walk_right_animation;
	public static Animation player_walk_up_animation;
	public static Animation player_walk_left_down_animation;
	
	public static void load() {
		
		wall_tile = new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("environment/wall.png"))));
		
		/*Player animation*/
		split = TextureRegion.split(new Texture(Gdx.files.internal("characters/player.png")), 32, 32);
		
		player_walk_down_animation = new Animation(0.2f, framesForAnimation(split, 5, 0));
		player_walk_down_animation.setPlayMode(Animation.PlayMode.LOOP);

		
		player_walk_left_animation = new Animation(0.2f, framesForAnimation(split, 5, 1));
		player_walk_left_animation.setPlayMode(Animation.PlayMode.LOOP);

		
		player_walk_right_animation = new Animation(0.2f, framesForAnimation(split, 5, 2));
		player_walk_right_animation.setPlayMode(Animation.PlayMode.LOOP);

		
		player_walk_up_animation = new Animation(0.2f, framesForAnimation(split, 5, 3));
		player_walk_up_animation.setPlayMode(Animation.PlayMode.LOOP);
		
	}
	
	private static TextureRegion[] framesForAnimation(TextureRegion [][] split, int nb_frames, int row) {
		
		TextureRegion [] frames = new TextureRegion[nb_frames];
		
		for (int i = 0 ; i < nb_frames ; ++i) frames[i] = split[row][i];		
		
		return frames;
		
	}
	
}