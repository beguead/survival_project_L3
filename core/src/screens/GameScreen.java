package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
	private static float state_time;
	
	public static Maze dungeon;
	public static RayHandler ray_handler;
	public static World world;
	public static OrthographicCamera game_cam;
	public static SpriteBatch game_batch;
	
	private Matrix4 debugmatrix;
	
	private short end;
	private boolean pause;

	private GameScreen() {
    	
		game_cam = new OrthographicCamera();
		game_cam.setToOrtho(false, Constants.APP_WIDTH, Constants.APP_HEIGHT);
	   	game_batch = new SpriteBatch();
    	Light.setGlobalContactFilter(Constants.LIGHT_FILTER, (short)0, (short)(Constants.PLAYER_FILTER | Constants.WALL_FILTER | Constants.ENEMY_FILTER));
    	
    }
	
	public static GameScreen getInstance() { return INSTANCE; }
	
	@Override
	public void show() {
		
		resume();
	   	end = 0;
		
	   	game_batch = new SpriteBatch();
    	world = new World(new Vector2(0f, 0f), true);
    	ray_handler = new RayHandler(world);
    	ray_handler.setAmbientLight(0f);
    	dungeon = Maze.getInstance();
    	world.setContactListener(ContactManager.getInstance());
	   	Gdx.input.setInputProcessor(GameInputManager.getInstance());
	
	}

	@Override
	@SuppressWarnings("deprecation")
	public void render(float delta) {
		
		if (end != 0) {
			
			pause();
			dispose();
			MainGame.getInstance().setScreen(EndScreen.getInstance(end > 0));
			return;

		}
		
		if (!pause) {
				
			state_time += delta;
				
			debugmatrix = game_batch.getProjectionMatrix().cpy().scl(Constants.PPM);
			
			world.step(1 / 60f, 6, 2);
			gameCamUpdate();
		
			ray_handler.setCombinedMatrix(debugmatrix);
			
			dungeon.update();
			
			game_batch.setProjectionMatrix(game_cam.combined);
			game_batch.begin();
			dungeon.render();
			game_batch.end();
		
			ray_handler.updateAndRender();
			
		}
	}

	@Override
	public void resize(int width, int height) {}
	
	private void gameCamUpdate() {
		
		Vector3 camPosition = game_cam.position;
		
		Vector2 player_position = Maze.player.getPosition().scl(Constants.PPM);
		float half_cam = Constants.APP_WIDTH / 2;
		
		if (player_position.x - half_cam <= 0) camPosition.x += (half_cam - game_cam.position.x) * Constants.CAMERA_LERP;
			else	if (player_position.x + half_cam  >= Constants.MAP_WIDTH * Constants.TILE_WIDTH)
						camPosition.x += (Constants.MAP_WIDTH * Constants.TILE_WIDTH - half_cam - game_cam.position.x) * Constants.CAMERA_LERP;

					else camPosition.x += (player_position.x - game_cam.position.x) * Constants.CAMERA_LERP;
		
		half_cam = Constants.APP_HEIGHT / 2;
		
		if (player_position.y - half_cam <= 0) camPosition.y += (half_cam - game_cam.position.y) * Constants.CAMERA_LERP;
			else	if (player_position.y + half_cam  >= Constants.MAP_HEIGHT * Constants.TILE_HEIGHT)
						camPosition.y += (Constants.MAP_HEIGHT * Constants.TILE_HEIGHT - half_cam - game_cam.position.y) * Constants.CAMERA_LERP;
		
					else camPosition.y += (player_position.y - game_cam.position.y) * Constants.CAMERA_LERP;
		
		game_cam.position.set(camPosition);
		game_cam.update();
		
	}

	@Override
	public void pause() { 
		
		Assets.game_background_music.pause();
		pause = true;
		
	}

	@Override
	public void resume() {
		
		Assets.game_background_music.play();
		pause = false; 
		
	}

	@Override
	public void hide() { 
		
		Gdx.input.setInputProcessor(null);
		Assets.game_background_music.stop();
		
	}

	@Override
	public void dispose() {
		
		dungeon.dispose();
		ray_handler.dispose();
		world.dispose();
		game_batch.dispose();
		
	}
	
	public static float getStateTime() { return state_time; }
	
	public void setEnd(short end) { this.end = end; }
	
	public void setPause() {
		
		if (pause) resume();
		else pause();
		
	}
	
}
