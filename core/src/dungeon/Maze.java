package dungeon;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

import characters.Parasite;
import characters.Player;
import interfaces.IUpdateAndRender;
import items.BlueCore;
import items.Core;
import items.OrangeCore;
import items.Particle;
import items.CyanCore;
import items.PurpleCore;
import items.RedCore;
import main.MainGame;
import pathfinding.CannotFindPathException;
import pathfinding.Graph;
import screens.GameScreen;
import utilities.Constants;
import utilities.MathExtension;
import utilities.UnionFind;

public class Maze implements Disposable, IUpdateAndRender {
	
	public static Graph graph;
	public static Portal portal;
	
	public static ArrayList<Parasite> parasites;
	public static ArrayList<Core> cores;
	public static ArrayList<Particle> particles;
	public static Player player;
	
	public static Core core_near_the_player;
	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	
	public Maze() {
		
		core_near_the_player = null;
		portal = null;
		
    	graph = new Graph();
    	createTiledMaze();
		renderer = new OrthogonalTiledMapRenderer(map, MainGame.batch);
		
    	parasites = new ArrayList<Parasite>();
    	particles = new ArrayList<Particle>();
    	
		CyanCore start_core = CyanCore.getInstance();
		player = new Player();
		player.setCore(start_core);
		cores = new ArrayList<Core>();
		cores.add(start_core);

		int i;
		for (i = 0 ; i < 0 ; ++i) particles.add(new Particle());
		for (i = 0 ; i < Constants.MAP_WIDTH * Constants.MAP_HEIGHT / 25 ; ++i) {
			
			parasites.add(new Parasite());
			
			while (MathExtension.getDistance(parasites.get(i).getPosition(), player.getPosition()) < 2d)
				parasites.get(i).setPosition(getRandomFreePosition());
			
			double r = Math.random();
			if (r < 0.10d) cores.add(new BlueCore());
			else 	if (r < 0.20d) cores.add(new OrangeCore());
					else	if (r < 0.30d) cores.add(new PurpleCore());
							else if (r < 0.40d) cores.add(new RedCore());
				
		}
	}

	public void dispose() { map.dispose(); }
	
	private void createTiledMaze() {
		
		map = new TiledMap();
		
		TiledMapTileLayer environment_layer = new TiledMapTileLayer(Constants.MAP_WIDTH,  Constants.MAP_HEIGHT,  Constants.TILE_WIDTH, Constants.TILE_HEIGHT);
		MapObjects mapobjects = environment_layer.getObjects();
		
		boolean blueprint[][] = createBluePrint();
		
		Cell wall = new Cell();
		wall.setTile(new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("environment/wall.png")))));
		Cell floor = new Cell();
		floor.setTile(new StaticTiledMapTile(new TextureRegion(new Texture(Gdx.files.internal("environment/floor.png")))));
		
		for (int i = 0 ; i < Constants.MAP_WIDTH ; ++i)
			for (int j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				if (blueprint[i][j]) {
						
						environment_layer.setCell(i, j, wall);
						new Wall(i, j);
						
				} else {
					
					if (Math.random() < 0.07f) {
						
						int neighbours = 0;
						
						if (blueprint[i - 1][j]) neighbours = neighbours | 1;
						if (blueprint[i][j - 1]) neighbours = neighbours | 2;
						if (blueprint[i + 1][j]) neighbours = neighbours | 4;
						if (blueprint[i][j + 1]) neighbours = neighbours | 8;
						
						switch (neighbours) {
						
							case 5: {
							
								mapobjects.add(new LightBarrier(i, j, false));
								break;
							}
						
							case 10: {
							
								mapobjects.add(new LightBarrier(i, j, true));
								break;
							}
						}
					}
					environment_layer.setCell(i, j, floor);
					graph.addNode(i * Constants.MAP_HEIGHT + j);
				}
			}
		
		graph.makeLinks();
		map.getLayers().add(environment_layer);
		map.getLayers().get(0).setName("environment_layer");
		
	}
	
	private boolean[][] createBluePrint() {
		
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
		
		boolean blueprint[][] = new boolean[Constants.MAP_WIDTH][Constants.MAP_HEIGHT];
		for (i = 0 ; i < Constants.MAP_WIDTH ; ++i)
			for (j = 0 ; j < Constants.MAP_HEIGHT ; ++j) {
				
				if (blueprint[i][j] = (i == 0 || i == Constants.MAP_WIDTH - 1 || j == 0 || j == Constants.MAP_HEIGHT - 1));
				else {
				
					if (blueprint[i][j] = (i % 2 == 0 && j % 2 == 0));
					else blueprint[i][j] = (	Math.random() < 0.75f
							&&
							(((j < Constants.MAP_HEIGHT - 1 && i % 2 == 0 && vertical_walls[(i / 2) - 1][j / 2]))
				 			||
				 			((i < Constants.MAP_WIDTH - 1 && j % 2 == 0 && horizontal_walls[i / 2][(j / 2) - 1]))));

					}
				}			
		
		return blueprint;
		
	}
	
	public static ConcurrentLinkedQueue<Vector2> findShortestPath(Vector2 from, Vector2 to) throws CannotFindPathException {
		
		return graph.findShortestPath((int)((int)from.x * Constants.MAP_HEIGHT + from.y), (int)((int)to.x * Constants.MAP_HEIGHT + to.y));
		
	}
	
	public static ConcurrentLinkedQueue<Vector2> getRandomPath(Vector2 from) throws CannotFindPathException {
		
		return graph.findShortestPath((int)((int)from.x * Constants.MAP_HEIGHT + from.y), graph.getRandomKey());
		
	}
	
	public static Vector2 getRandomFreePosition() {
		
		int random_key = graph.getRandomKey();
		return (new Vector2(random_key / Constants.MAP_HEIGHT + 0.5f, random_key % Constants.MAP_HEIGHT + 0.5f));
		
	}

	@Override
	public void update() {
		
		for (Core c : Maze.cores) c.update();
		
		if (portal == null) {
		
			if (particles.size() == 0) portal = new Portal();
			else {
			
				for (int i = particles.size() - 1 ; i >= 0 ; --i)
					if (particles.get(i).catched) {
					
						particles.get(i).dispose();
						particles.remove(i);
					
					} else particles.get(i).update();
			}
		} else portal.update();
	}

	@Override
	public void render() {
		
		renderer.setView(MainGame.camera);
		renderer.renderTileLayer((TiledMapTileLayer) map.getLayers().get("environment_layer"));

		for (MapObject mo : map.getLayers().get("environment_layer").getObjects())
			((LightBarrier)(mo)).renderBase();
		
		for (Core c : cores) c.render();
		for (Particle p : particles) p.render();
		player.updateAndRender();
		for (Parasite p : parasites) p.updateAndRender();
		
		if (portal != null) portal.render();
		
		for (MapObject mo : map.getLayers().get("environment_layer").getObjects())
			((LightBarrier)(mo)).renderBarrier();
		
	}
}