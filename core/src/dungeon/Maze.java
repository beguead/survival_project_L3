package dungeon;

import java.util.ArrayList;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;

import characters.Enemy;
import characters.Player;
import screens.GameScreen;
import utilities.Assets;
import utilities.Constants;

public class Maze implements Disposable {

	public static Player player;
	
	private ArrayList<Enemy> enemys;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public Maze() {
		
    	player = new Player();
    	enemys = new ArrayList<Enemy>();
		
		map = new TiledMap();
		MapLayers layers = map.getLayers();

		TiledMapTileLayer tml = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT, Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		MapObjects objects = tml.getObjects();
		
		Cell w = new Cell();
		w.setTile(Assets.wall_tile);
		
		char c[][] = makeBluePrint();
		
		for (int i = 0 ; i < Constants.MAP_WIDTH ; ++i) {
			
			for (int j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				switch(c[i][j]) {
				
				case 'w' : {
					
					tml.setCell(i, j, w);
					objects.add(new Wall((float)i, (float)j));
					break;
					
				}
				
				default : {}
				
				}	
				
				
			}
			
		}
		

		layers.add(tml);
		
		map.getLayers().get(0).setName("walls");
		
		renderer = new OrthogonalTiledMapRenderer(map, GameScreen.batch);
		
	}
	
	public void render() {

		renderer.setView(GameScreen.game_cam);
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("walls"));
		player.render();
		
	}	

	public void dispose() {
		
		map.dispose();
		
	}
	
	private char[][] makeBluePrint() {
		
		int width = Constants.MAP_WIDTH / 2, height = Constants.MAP_HEIGHT / 2, i, j, nb_broken_walls = 0;
		char blueprint[][] = new char[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		boolean[][] horizontal_walls, vertical_walls;
		
		Set[][] cases = new Set[width][height];
		horizontal_walls = new boolean[width][height - 1];
		vertical_walls = new boolean[width - 1][height];
		
		for (i = 0 ; i < width ; ++i)
			for (j = 0 ; j < height ; ++j) {
				
				cases[i][j] = new Set();
				if (j < height - 1) horizontal_walls[i][j] = true;
				if (i < width - 1) vertical_walls[i][j] = true;
				
			}
	
		do {
			
			if (Math.random() < 0.5f ) {
				
				i = (int)(Math.random() * width);
				j = (int)(Math.random() * (height - 1));
				
				
				if (horizontal_walls[i][j])
					if (cases[i][j].find() != cases[i][j + 1].find()) {
						
						cases[i][j].union(cases[i][j + 1]);
						horizontal_walls[i][j] = false;
						++nb_broken_walls;
						
					}
				
			} else {
				
				i = (int)(Math.random() * width - 1);
				j = (int)(Math.random() * height);		
			
				if (vertical_walls[i][j])
					if (cases[i][j].find() != cases[i + 1][j].find()) {
					
						cases[i][j].union(cases[i + 1][j]);
						vertical_walls[i][j] = false;
						++nb_broken_walls;
					
				}
			
			}
			
			
		} while (nb_broken_walls < width * height - 1);
		
		for (i = 0 ; i < Constants.MAP_WIDTH ; ++i)
			for (j = 0 ; j < Constants.MAP_HEIGHT ; ++j)
				if (	i == 0 || j == 0 || i == Constants.MAP_WIDTH - 1 || j == Constants.MAP_HEIGHT - 1 ||
						(i % 2 == 0 && vertical_walls[(i - 2) / 2][(j - 1) / 2]) ||
						(j % 2 == 0 && horizontal_walls[(i - 1) / 2][(j - 2) / 2])	) blueprint[i][j] = 'w';
		
		return blueprint;
		
	}
	
}
