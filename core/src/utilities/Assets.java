package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Assets {
	
	public static TextureRegion [][] split;
	
	private Assets() {}
	
	/*Camera*/
    public static int virtual_width = Constants.APP_WIDTH / 4;
    public static int virtual_height = Constants.APP_HEIGHT / 4;
	
	/*Player*/
	public static Animation player_walk_down_animation;
	public static Animation player_walk_left_animation;
	public static Animation player_walk_right_animation;
	public static Animation player_walk_up_animation;
	public static Animation player_walk_left_down_animation;
	public static Animation player_walk_right_down_animation;
	public static Animation player_walk_left_up_animation;
	public static Animation player_walk_right_up_animation;
	
	/*Braziers*/
	public static Animation common_brazier_animation;
	public static Animation elder_brazier_animation;
	
	public static void load() {
		
		/*Player animation*/
		split = TextureRegion.split(new Texture(Gdx.files.internal("characters/player.png")), 32, 32);
		
		player_walk_down_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 0));
		player_walk_down_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_left_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 1));
		player_walk_left_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_right_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 2));
		player_walk_right_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_up_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 3));
		player_walk_up_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_left_down_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 4));
		player_walk_left_down_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_right_down_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 5));
		player_walk_right_down_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_left_up_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 6));
		player_walk_left_up_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		player_walk_right_up_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 7));
		player_walk_right_up_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		/*Braziers animation*/
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/brazier.png")), 32, 32);
		
		common_brazier_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 12, 0));
		common_brazier_animation.setPlayMode(Animation.PlayMode.LOOP);
		
		elder_brazier_animation = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 12, 1));
		elder_brazier_animation.setPlayMode(Animation.PlayMode.LOOP);
		
	}
	
	private static TextureRegion[] framesForAnimation(TextureRegion [][] split, int nb_frames, int row) {
		
		TextureRegion [] frames = new TextureRegion[nb_frames];
		
		for (int i = 0 ; i < nb_frames ; ++i) frames[i] = split[row][i];		
		
		return frames;
		
	}
	
}