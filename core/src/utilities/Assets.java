package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Assets {
	
	private Assets() {}
	
	/*Camera*/
    public static int virtual_width = (int) (Constants.APP_WIDTH / 2.5);
    public static int virtual_height = (int) (Constants.APP_HEIGHT / 2.5);
	
	public static Sprite player_base = new Sprite(new Texture(Gdx.files.internal("characters/player_base.png")));
	public static Sprite player_blue = new Sprite(new Texture(Gdx.files.internal("characters/player_blue.png")));
	public static Sprite player_green = new Sprite(new Texture(Gdx.files.internal("characters/player_green.png")));
	public static Sprite player_purple = new Sprite(new Texture(Gdx.files.internal("characters/player_purple.png")));
	public static Sprite player_red = new Sprite(new Texture(Gdx.files.internal("characters/player_red.png")));
	public static Sprite player_yellow = new Sprite(new Texture(Gdx.files.internal("characters/player_yellow.png")));
	
	public static Sprite white_core = new Sprite(new Texture(Gdx.files.internal("items/white_core.png")));
	public static Sprite blue_core = new Sprite(new Texture(Gdx.files.internal("items/blue_core.png")));
	public static Sprite green_core = new Sprite(new Texture(Gdx.files.internal("items/green_core.png")));
	public static Sprite yellow_core = new Sprite(new Texture(Gdx.files.internal("items/yellow_core.png")));
    
    /*Animations*/
		/*Characters*/
	
	public static Animation parasite_up;
	public static Animation parasite_down;
	public static Animation parasite_left;
	public static Animation parasite_right;
	
	public static Animation entity;
	
		/*Portal*/
	public static Animation portal_opening;
	public static Animation portal_standing;
	public static Animation portal_closing;		
	
	public static void load() {
		
		TextureRegion [][] split = TextureRegion.split(new Texture(Gdx.files.internal("characters/parasite.png")), 32, 32);
		parasite_up = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 0));
		parasite_up.setPlayMode(Animation.PlayMode.LOOP);
		parasite_down = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 1));
		parasite_down.setPlayMode(Animation.PlayMode.LOOP);
		parasite_left = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 2));
		parasite_left.setPlayMode(Animation.PlayMode.LOOP);
		parasite_right = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 3));
		parasite_right.setPlayMode(Animation.PlayMode.LOOP);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("characters/entity.png")), 32, 32);
		entity = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 0));
		entity.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/portal.png")), 32, 32);
		portal_opening = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 4, 0));
		portal_standing = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 8, 1));
		portal_standing.setPlayMode(Animation.PlayMode.LOOP);
		portal_closing = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 7, 2));
		
	}
	
	private static TextureRegion[] framesForAnimation(TextureRegion [][] split, int nb_frames, int row) {
		
		TextureRegion [] frames = new TextureRegion[nb_frames];
		
		for (int i = 0 ; i < nb_frames ; ++i) frames[i] = split[row][i];		
		
		return frames;
		
	}
	
}