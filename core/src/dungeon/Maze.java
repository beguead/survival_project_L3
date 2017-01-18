package dungeon;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import characters.Parasite;
import characters.Entity;
import characters.AI;
import characters.Player;
import items.BlueCore;
import items.Core;
import items.GreenCore;
import items.WhiteCore;
import items.YellowCore;
import pathfinding.Graph;
import screens.GameScreen;
import utilities.Constants;
import utilities.UnionFind;

public class Maze implements Disposable {

	private static Graph graph;
	public static Entity cortana;
	public static Player player;
	public static Portal portal;
	
	private static ArrayList<AI> parasites;
	public static ArrayList<Core> cores;
	public static Core core_near_the_player;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public Maze() {
		
		core_near_the_player = null;
		
    	graph = new Graph();
    	createTiledMaze();
		renderer = new OrthogonalTiledMapRenderer(map, GameScreen.batch);
		
    	player = new Player();
    	parasites = new ArrayList<AI>();
    	
		WhiteCore start_core = new WhiteCore();
		player.setCore(start_core);
		cores = new ArrayList<Core>();
		cores.add(start_core);
		
		for (int i = 0 ; i < Constants.MAP_WIDTH * Constants.MAP_HEIGHT / 50 ; ++i) {
			
			parasites.add(new Parasite());
			
			double r = Math.random();
				
			if (r < 0.25d) cores.add(new BlueCore());
			else 	if (r < 0.50d) cores.add(new GreenCore());
					else if (r < 0.75d) cores.add(new YellowCore());
				
			
		}
		
		cortana = new Entity();
		portal = null;

	}
	
	public void updateAndRender(float delta) {
		
		renderer.setView(GameScreen.game_cam);
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("background"));
		
		for (Core c : cores) c.updateAndRender();
		
		player.updateAndRender();
		for (AI ai : parasites) ai.updateAndRender();
		
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("walls"));
		if (cortana != null) cortana.updateAndRender();
		if (portal != null) portal.render();
		
	}	

	public void dispose() {
		
		map.dispose();
		
	}
	
	private void createTiledMaze() {
		
		map = new TiledMap();
		
		MapLayers layers = map.getLayers();	
		TiledMapTileLayer background = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT,  Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		TiledMapTileLayer tmtl = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT,  Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		
		char blueprint[][] = createBluePrint();
		
		Cell w = new Cell();
		w.setTile(new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("environment/wall.png")))));
		Cell f = new Cell();
		f.setTile(new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("environment/floor.png")))));
		
		for (int i = 0 ; i < Constants.MAP_WIDTH ; ++i)
			for (int j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				if (blueprint[i][j] != 'w') graph.addNode(i * Constants.MAP_HEIGHT + j);
				
				switch (blueprint[i][j]) {
					
					case 'w' : {
						
						background.setCell(i, j, w);
						new Wall(i, j);

						break;
						
					}
					
					default : {
						
						background.setCell(i, j, f); 
						
					}
					
				}
				
			}
		
		graph.makeLinks();
		
		layers.add(background);	
		map.getLayers().get(0).setName("background");
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
						
						if (	Math.random() < 0.75f
								&&
								(((j < Constants.MAP_HEIGHT - 1 && i % 2 == 0 && vertical_walls[(i / 2) - 1][j / 2]))
					 			||
					 			((i < Constants.MAP_WIDTH - 1 && j % 2 == 0 && horizontal_walls[i / 2][(j / 2) - 1])))) blueprint[i][j] = 'w';
						
						else blueprint[i][j] = '\0';
					}
				}			
			}
		
		return blueprint;
		
	}
	
	public static ConcurrentLinkedQueue<Vector2> findShortestPath(Vector2 from, Vector2 to) {
		return graph.findShortestPath((int)((int)from.x * Constants.MAP_HEIGHT + from.y), (int)((int)to.x * Constants.MAP_HEIGHT + to.y)); }
	
	public static ConcurrentLinkedQueue<Vector2> getRandomPath(Vector2 from) {
		return graph.findShortestPath((int)((int)from.x * Constants.MAP_HEIGHT + from.y), graph.getRandomKey()); }
	
	public static Vector2 getRandomFreePosition() {
		
		int random_key = graph.getRandomKey();
		return (new Vector2(random_key / Constants.MAP_HEIGHT + 0.5f, random_key % Constants.MAP_HEIGHT + 0.5f));
		
	}
	
}