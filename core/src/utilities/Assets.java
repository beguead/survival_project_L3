package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Assets {
	
	private Assets() {}
	
	/*Camera*/
    public static int virtual_width = 1200;
    public static int virtual_height = 800;
	
   public static final Music main_menu_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/halo5_main_menu_theme.mp3"));
   public static final Music game_background_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/flood_theme.mp3"));
    
	public static final Sprite player_base = new Sprite(new Texture(Gdx.files.internal("characters/player/base.png")));
	public static final Sprite player_blue = new Sprite(new Texture(Gdx.files.internal("characters/player/blue.png")));
	public static final Sprite player_green = new Sprite(new Texture(Gdx.files.internal("characters/player/green.png")));
	public static final Sprite player_yellow = new Sprite(new Texture(Gdx.files.internal("characters/player/yellow.png")));
	
	public static final Sprite white_core = new Sprite(new Texture(Gdx.files.internal("items/cores/white.png")));
	public static final Texture blue_core = new Texture(Gdx.files.internal("items/cores/blue.png"));
	public static final Texture green_core = new Texture(Gdx.files.internal("items/cores/green.png"));
	public static final Texture yellow_core = new Texture(Gdx.files.internal("items/cores/yellow.png"));
	
	public static final Texture light_barrier_base = new Texture(Gdx.files.internal("environment/light_barrier/base.png"));
    
    /*Animations*/
		/*Characters*/
	
	public static Animation parasite;
	public static Animation particle;
	
		/*Portal*/
	public static Animation portal_opening;
	public static Animation portal_standing;
	public static Animation portal_closing;
	
		/*Light barrier*/
	public static Animation barrier_closing;
	public static Animation light_barrier;
	public static Animation barrier_opening;
	
	public static void load() {
		
		main_menu_music.setLooping(true);
		main_menu_music.setVolume(0.5f);
		
		game_background_music.setLooping(true);
		game_background_music.setVolume(0.2f);
		
		TextureRegion [][] split = TextureRegion.split(new Texture(Gdx.files.internal("characters/parasite.png")), 64, 64);
		parasite = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 4, 0));
		parasite.setPlayMode(Animation.PlayMode.LOOP);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("items/particle.png")), 32, 32);
		particle = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 5, 0));
		particle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/portal.png")), 32, 32);
		portal_opening = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 4, 0));
		portal_standing = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 8, 1));
		portal_standing.setPlayMode(Animation.PlayMode.LOOP);
		portal_closing = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 7, 2));
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/light_barrier/light_barrier.png")), 32, 128);
		light_barrier = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 32, 0));
		light_barrier.setPlayMode(Animation.PlayMode.LOOP);
		
	}
	
	private static TextureRegion[] framesForAnimation(TextureRegion [][] split, int nb_frames, int row) {
		
		TextureRegion [] frames = new TextureRegion[nb_frames];
		
		for (int i = 0 ; i < nb_frames ; ++i) frames[i] = split[row][i];		
		
		return frames;
		
	}
	
}