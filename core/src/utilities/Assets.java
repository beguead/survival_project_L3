package utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class Assets {
	
	private Assets() {}
	
   public static final Music main_menu_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/halo5_main_menu_theme.mp3"));
   public static final Music game_background_music = Gdx.audio.newMusic(Gdx.files.internal("sounds/flood_theme.mp3"));
   public static final Texture main_menu_background =  new Texture(Gdx.files.internal("ui/menu.png"));
   public static final Texture fail_img =  new Texture(Gdx.files.internal("ui/fail.png"));
   public static final Texture escape_img =  new Texture(Gdx.files.internal("ui/escape.png"));
   public static final Texture monitor =  new Texture(Gdx.files.internal("ui/monitor.png"));
    
	
	public static final Sprite cyan_core = new Sprite(new Texture(Gdx.files.internal("items/cores/cyan.png")));
	public static final Texture blue_core = new Texture(Gdx.files.internal("items/cores/blue.png"));
	public static final Texture orange_core = new Texture(Gdx.files.internal("items/cores/orange.png"));
	public static final Texture purple_core = new Texture(Gdx.files.internal("items/cores/purple.png"));
	public static final Texture red_core = new Texture(Gdx.files.internal("items/cores/red.png"));
	
	public static final Texture light_barrier_base = new Texture(Gdx.files.internal("environment/light_barrier/base.png"));
    
    /*Animations*/
		/*Characters*/
	
	public static Animation parasite;
	public static Animation particle;
	public static Animation player_normal;
	public static Animation player_hunted;
	public static Animation player_dead;
	
	public static Animation portal;
	public static Animation light_barrier;
	
	public static void load() {
		
		main_menu_music.setLooping(true);
		main_menu_music.setVolume(0.5f);
		
		game_background_music.setLooping(true);
		game_background_music.setVolume(0.2f);
		
		TextureRegion [][] split = TextureRegion.split(new Texture(Gdx.files.internal("characters/parasite.png")), 64, 64);
		parasite = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 4, 0));
		parasite.setPlayMode(Animation.PlayMode.LOOP);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("items/particle.png")), 16, 16);
		particle = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 6, 0));
		particle.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("characters/player.png")), 32, 32);
		player_normal = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 0));
		player_normal.setPlayMode(Animation.PlayMode.LOOP);
		player_hunted = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 1));
		player_hunted.setPlayMode(Animation.PlayMode.LOOP);
		player_dead = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 2));
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/portal.png")), 100, 75);
		portal = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 8, 0));
		portal.setPlayMode(Animation.PlayMode.LOOP);
		
		split = TextureRegion.split(new Texture(Gdx.files.internal("environment/light_barrier/light_barrier.png")), 50, 128);
		light_barrier = new Animation(Constants.FRAME_DURATION, framesForAnimation(split, 3, 0));
		light_barrier.setPlayMode(Animation.PlayMode.LOOP);
		
	}
	
	private static TextureRegion[] framesForAnimation(TextureRegion [][] split, int nb_frames, int row) {
		
		TextureRegion [] frames = new TextureRegion[nb_frames];
		
		for (int i = 0 ; i < nb_frames ; ++i) frames[i] = split[row][i];		
		
		return frames;
		
	}
	
}