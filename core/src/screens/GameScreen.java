package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.Light;
import box2dLight.RayHandler;
import characters.Player;
import dungeon.Maze;
import dungeon.Portal;
import items.Core;
import main.MainGame;
import managers.GameInputManager;
import managers.ContactManager;
import utilities.Assets;
import utilities.Constants;

public class GameScreen implements Screen {
	
	public static Maze dungeon;
	public static OrthographicCamera game_cam;
	public static RayHandler ray_handler;
	public static SpriteBatch batch;
	public static World world;
	public static float state_time;
	
	private Box2DDebugRenderer b2dr;
	private Matrix4 debugmatrix;
	
	public static boolean pause, end;
	public static boolean debug_renderer;

	public GameScreen() {
		
    	Gdx.input.setInputProcessor(new GameInputManager());
    	
    	game_cam = new OrthographicCamera();
    	game_cam.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
		game_cam.update();
    	
    	Light.setGlobalContactFilter(Constants.LIGHT_FILTER, (short)0, (short)(Constants.PLAYER_FILTER | Constants.WALL_FILTER | Constants.ENEMY_FILTER));
    	
    }	
	
	public void show() {
		
		resume();
    	world = new World(new Vector2(0f, 0f), true);
    	world.setContactListener(new ContactManager());
		batch = new SpriteBatch();
		b2dr = new Box2DDebugRenderer();
    	ray_handler = new RayHandler(world);
    	ray_handler.setAmbientLight(0);
    	dungeon = new Maze();
    	
    	debug_renderer = end = false;
	
	}

	@SuppressWarnings("deprecation")
	public void render(float delta) {
		
			if (!pause) {
				
				state_time += delta;
				
				debugmatrix = batch.getProjectionMatrix().cpy().scale(Constants.PPM, Constants.PPM, 0);
			
				world.step(1 / 60f, 6, 2);
				gameCamUpdate();
		
				ray_handler.setCombinedMatrix(debugmatrix);
				batch.setProjectionMatrix(game_cam.combined);
		
				dungeon.update();
				batch.begin();
				dungeon.render();
				batch.end();
		
				ray_handler.updateAndRender();
		
			if (debug_renderer) b2dr.render(world, debugmatrix);
			
		if (end) {
			
			pause();
			MainGame.getInstance().setScreen(MainGame.m2Screen);
			dispose();
			
		}
		}
	}

	public void resize(int width, int height) {}
	
	private void gameCamUpdate() {
		
		Vector3 camPosition = game_cam.position;
		
		float player_position = Player.getInstance().getPosition().x * Constants.PPM;
		float half_cam = Assets.virtual_width / 2;
		
		if (player_position - half_cam <= 0) camPosition.x += (half_cam - game_cam.position.x) * Constants.CAMERA_LERP;
			else	if (player_position + half_cam  >= Constants.MAP_WIDTH * Constants.TILE_WIDTH)
						camPosition.x += (Constants.MAP_WIDTH * Constants.TILE_WIDTH - half_cam - game_cam.position.x) * Constants.CAMERA_LERP;

					else camPosition.x += (player_position - game_cam.position.x) * Constants.CAMERA_LERP;
		
		player_position = Player.getInstance().getPosition().y * Constants.PPM;
		half_cam = Assets.virtual_height / 2;
		
		if (player_position - half_cam <= 0) camPosition.y += (half_cam - game_cam.position.y) * Constants.CAMERA_LERP;
			else	if (player_position + half_cam  >= Constants.MAP_HEIGHT * Constants.TILE_HEIGHT)
						camPosition.y += (Constants.MAP_HEIGHT * Constants.TILE_HEIGHT - half_cam - game_cam.position.y) * Constants.CAMERA_LERP;
		
					else camPosition.y += (player_position - game_cam.position.y) * Constants.CAMERA_LERP;
		
		game_cam.position.set(camPosition);
		game_cam.update();
		
	}

	public void pause() { 
		
		Assets.game_background_music.pause();
		pause = true;
		
	}

	public void resume() {
		
		Assets.game_background_music.play();
		pause = false; 
		
	}

	public void hide() {Assets.game_background_music.stop(); }

	public void dispose() {
		
		batch.dispose();
		dungeon.dispose();
		b2dr.dispose();
		ray_handler.dispose();
		//world.dispose();
		
	}

}
