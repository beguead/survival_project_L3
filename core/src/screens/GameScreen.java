package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.Light;
import box2dLight.RayHandler;
import dungeon.Maze;
import main.MainGame;
import managers.ContactManager;
import managers.GameInputManager;
import utilities.Assets;
import utilities.Constants;

public class GameScreen implements Screen {
	
	private static GameScreen INSTANCE = new GameScreen();
	
	public static Maze dungeon;
	public static RayHandler ray_handler;
	public static World world;
	public static float state_time;
	
	private Box2DDebugRenderer b2dr;
	private Matrix4 debugmatrix;
	
	public static boolean pause, end;
	public static boolean debug_renderer;

	private GameScreen() {
    	
    	Light.setGlobalContactFilter(Constants.LIGHT_FILTER, (short)0, (short)(Constants.PLAYER_FILTER | Constants.WALL_FILTER | Constants.ENEMY_FILTER));
    	
    }
	
	public static GameScreen getInstance() { return INSTANCE; }
	
	public void show() {
		
		MainGame.camera.setToOrtho(false, Assets.virtual_width, Assets.virtual_height);
		
		resume();
		
	   	Gdx.input.setInputProcessor(GameInputManager.getInstance());
		
    	world = new World(new Vector2(0f, 0f), true);
    	world.setContactListener(new ContactManager());
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
				
				debugmatrix = MainGame.batch.getProjectionMatrix().cpy().scale(Constants.PPM, Constants.PPM, 0);
			
				world.step(1 / 60f, 6, 2);
				gameCamUpdate();
		
				ray_handler.setCombinedMatrix(debugmatrix);
				MainGame.batch.setProjectionMatrix(MainGame.camera.combined);
		
				dungeon.update();
				MainGame.batch.begin();
				dungeon.render();
				MainGame.batch.end();
		
				ray_handler.updateAndRender();
		
			if (debug_renderer) b2dr.render(world, debugmatrix);
			
		if (end) {
			
			pause();
			MainGame.getInstance().setScreen(MainMenuScreen.getInstance());
			dispose();
			
		}
		}
	}

	public void resize(int width, int height) {}
	
	private void gameCamUpdate() {
		
		Vector3 camPosition = MainGame.camera.position;
		
		float player_position = Maze.player.getPosition().x * Constants.PPM;
		float half_cam = Assets.virtual_width / 2;
		
		if (player_position - half_cam <= 0) camPosition.x += (half_cam - MainGame.camera.position.x) * Constants.CAMERA_LERP;
			else	if (player_position + half_cam  >= Constants.MAP_WIDTH * Constants.TILE_WIDTH)
						camPosition.x += (Constants.MAP_WIDTH * Constants.TILE_WIDTH - half_cam - MainGame.camera.position.x) * Constants.CAMERA_LERP;

					else camPosition.x += (player_position - MainGame.camera.position.x) * Constants.CAMERA_LERP;
		
		player_position = Maze.player.getPosition().y * Constants.PPM;
		half_cam = Assets.virtual_height / 2;
		
		if (player_position - half_cam <= 0) camPosition.y += (half_cam - MainGame.camera.position.y) * Constants.CAMERA_LERP;
			else	if (player_position + half_cam  >= Constants.MAP_HEIGHT * Constants.TILE_HEIGHT)
						camPosition.y += (Constants.MAP_HEIGHT * Constants.TILE_HEIGHT - half_cam - MainGame.camera.position.y) * Constants.CAMERA_LERP;
		
					else camPosition.y += (player_position - MainGame.camera.position.y) * Constants.CAMERA_LERP;
		
		MainGame.camera.position.set(camPosition);
		MainGame.camera.update();
		
	}

	public void pause() { 
		
		Assets.game_background_music.pause();
		pause = true;
		
	}

	public void resume() {
		
		Assets.game_background_music.play();
		pause = false; 
		
	}

	public void hide() { 
		
		Gdx.input.setInputProcessor(null);
		Assets.game_background_music.stop();
		
	}

	public void dispose() {
		
		dungeon.dispose();
		b2dr.dispose();
		ray_handler.dispose();
		//world.dispose();
		
	}

}
