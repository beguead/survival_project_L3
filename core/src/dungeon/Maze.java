package dungeon;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Disposable;

import characters.Enemy;
import characters.Player;
import screens.GameScreen;
import utilities.Constants;

public class Maze implements Disposable {

	public static Player player;
	
	private Enemy[] enemys;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public Maze() {
		
    	player = new Player();
    	createTiledMaze();
		renderer = new OrthogonalTiledMapRenderer(map, GameScreen.batch);
		
	}
	
	public void update(float delta) {
		
		player.update(delta);
		
		for (MapObject obj : map.getLayers().get("walls").getObjects()) {
			
			if (obj instanceof Brazier) {
			
	                Brazier brazier = (Brazier) obj;
	                brazier.update(delta);
	                
			}
		}
	}
	
	public void render(float delta) {

		renderer.setView(GameScreen.game_cam);
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("floor"));	
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("walls"));
		
		for (MapObject obj : map.getLayers().get("walls").getObjects()) {
			
			if (obj instanceof Brazier) {
			
	                Brazier brazier = (Brazier) obj;
	                brazier.update(delta);
	                brazier.render();
	                
			}
		}


		player.render();
		
	}	

	public void dispose() {
		
		map.dispose();
		
	}
	
	private void createTiledMaze() {
		
		map = new TiledMap();
		MapLayers layers = map.getLayers();	
		TiledMapTileLayer floor = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT,  Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		TiledMapTileLayer tmtl = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT,  Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		MapObjects mo = tmtl.getObjects();
		
		char blueprint[][] = createBluePrint();
		Cell w = new Cell();
		w.setTile(new StaticTiledMapTile(new TextureRegion (new Texture(Gdx.files.internal("environment/wall.png")))));
		Cell f = new Cell();
		f.setTile(new StaticTiledMapTile(new TextureRegion (new Texture(Gdx.files.internal("environment/floor.png")))));
		
		for (int i = 0 ; i < Constants.MAP_WIDTH ; ++i) 
			for (int j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				switch (blueprint[i][j]) {
					
					case 'w' : {
						
						tmtl.setCell(i, j, w);
						mo.add(new Wall(i, j));
						break;
						
					}
					
					case 'c' : {
						
						mo.add(new CommonBrazier(i, j));
						
						break;
						
					}
					
					case 'e' : {
						
						mo.add(new ElderBrazier(i, j));
						
						break;
						
					}
					
					default : {}
					
				}
				
				floor.setCell(i, j, f);
				
			}
		
		layers.add(floor);	
		map.getLayers().get(0).setName("floor");
		layers.add(tmtl);
		map.getLayers().get(1).setName("walls");
		
	}
	
	private char[][] createBluePrint() {
		
		int width = Constants.MAP_WIDTH / 2, height = Constants.MAP_HEIGHT / 2, i, j, nb_broken_walls = 0;
		
		boolean[][] horizontal_walls, vertical_walls;
		UnionFind[][] tab = new UnionFind[width][height];
		horizontal_walls = new boolean[width][height - 1];
		vertical_walls = new boolean[width - 1][height];
		
		for (i = 0 ; i < width ; ++i)
			for (j = 0 ; j < height ; ++j) {
				
				tab[i][j] = new UnionFind();
				if (j < height - 1)horizontal_walls[i][j] = true;
				if (i < width - 1)vertical_walls[i][j] = true;
				
			}
	
		do {
			
			if (Math.random() < 0.5) {
				
				i = (int)(Math.random() * width);
				j = (int)(Math.random() * (height - 1));
				
				if (horizontal_walls[i][j])
					if (tab[i][j].find() != tab[i][j + 1].find()) {
						
						tab[i][j].union(tab[i][j + 1]);
						horizontal_walls[i][j] = false;
						++nb_broken_walls;
						
					}
				
			} else {
				
				i = (int)(Math.random() * (width - 1));
				j = (int)(Math.random() * height);		
			
				if (vertical_walls[i][j])
					if (tab[i][j].find() != tab[i + 1][j].find()) {
					
						tab[i][j].union(tab[i + 1][j]);
						vertical_walls[i][j] = false;
						++nb_broken_walls;
					
				}
			
			}
			
		} while (nb_broken_walls < width * height - 1);
		
		char blueprint[][] = new char[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		for (i = 0 ; i < Constants.MAP_WIDTH ; ++i)
			for (j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				if (i == 0 || i == Constants.MAP_WIDTH - 1 || j == 0 || j == Constants.MAP_HEIGHT - 1) blueprint[i][j] = 'w';
				else {
				
					if (i % 2 == 0 && j % 2 == 0) blueprint[i][j] = 'w';
					else {
						
						if (	(((j < Constants.MAP_HEIGHT - 1 && i % 2 == 0 && vertical_walls[(i / 2) - 1][j / 2]))
					 				||
					 				((i < Constants.MAP_WIDTH - 1 && j % 2 == 0 && horizontal_walls[i / 2][(j / 2) - 1])))
					 				&&
					 				Math.random() < 0.9f) blueprint[i][j] = 'w';
						else  {
							
							if (	Math.random() < 0.10f 
									&&
									blueprint[i - 1][j] != 'c' && blueprint[i - 1][j] != 'e'
									&&
									blueprint[i][j - 1] != 'c' && blueprint[i][j - 1] != 'e'
									&&
									blueprint[i - 1][j - 1] != 'c' && blueprint[i - 1][j - 1] != 'e') {
								
								if (Math.random() < 0.95f) blueprint[i][j] = 'c';
								else blueprint[i][j] = 'e';
							
							} else blueprint[i][j] = '\0';	
						}
					}
				}			
			}
		
		return blueprint;
		
	}
}