package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import box2dLight.RayHandler;
import dungeon.Maze;
import utilities.Assets;
import utilities.Constants;
import utilities.GameInputManager;

public class GameScreen implements Screen {
	
	public static Maze dungeon;
	public static OrthographicCamera game_cam;
	public static RayHandler ray_handler;
	public static SpriteBatch batch;
	public static World world;
	
	private Box2DDebugRenderer b2dr;
	private Matrix4 debugmatrix;
	
	private boolean paused;

	public GameScreen() {
    	
    	world = new World(new Vector2(0f, 0f), true);
    	
    	Assets.load();
    	
    	game_cam = new OrthographicCamera();
    	game_cam.setToOrtho(false, Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT);
		game_cam.update();
    	
    	ray_handler = new RayHandler(world);
    	ray_handler.setBlur(false);
    	ray_handler.setAmbientLight(42 / 225f, 42 / 225f, 42 / 225f, 0f);
    	
    	batch = new SpriteBatch();
    	
    	Gdx.input.setInputProcessor(new GameInputManager());
    	
    	paused = false;
    	
    	b2dr = new Box2DDebugRenderer();
    	
    	dungeon = new Maze();
    	
    }	
	
	public void show() {
		
	}

	@SuppressWarnings("deprecation")
	public void render(float delta) {
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugmatrix = batch.getProjectionMatrix().cpy().scale(Constants.PPM, Constants.PPM, 0);
		
		Maze.player.update(delta);
		
		if (!paused) {
			
			world.step(1 / 60f, 6, 2);
			
			Vector3 camPosition = game_cam.position;
			
			camPosition.x += (Maze.player.getPosition().x * Constants.PPM - game_cam.position.x) * Constants.CAMERA_LERP;
			camPosition.y += (Maze.player.getPosition().y * Constants.PPM - game_cam.position.y) * Constants.CAMERA_LERP;
			
			game_cam.position.set(camPosition);
			game_cam.update();
			
		}
		
		ray_handler.setCombinedMatrix(game_cam.combined.cpy().scale(Constants.PPM, Constants.PPM, 0));
		batch.setProjectionMatrix(game_cam.combined);
		
		batch.begin();
		dungeon.render();
		batch.end();
		
		ray_handler.updateAndRender();
		
		b2dr.render(world, debugmatrix);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	public void dispose() {
		batch.dispose();
		dungeon.dispose();
		b2dr.dispose();
		ray_handler.dispose();
		world.dispose();
		
	}

}
