package pathfinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.badlogic.gdx.math.Vector2;

import utilities.Constants;

public class Graph {

	private HashMap<Integer, Node> nodes;
	private ArrayList<Integer> keys;
	
	public Graph() { 
		
		nodes = new HashMap<Integer, Node>();
		keys = new ArrayList<Integer>();
		
	}
	
	public void addNode(int key) {
		
		nodes.put(key, new Node());
		keys.add(key);
		
	}
	
	public void makeLinks() {
		
		Iterator<Entry<Integer, Node>> it = nodes.entrySet().iterator();
		while (it.hasNext()) {
		    	
			Map.Entry<Integer, Node> pair = (Map.Entry<Integer, Node>)it.next();
			int current_key = (int)pair.getKey();
			Node current_node = pair.getValue();
		        
			int key = current_key - 1;
			if (nodes.containsKey(key)) current_node.addSon(key);
			
			key = current_key + 1;
			if (nodes.containsKey(key)) current_node.addSon(key);
			
			key = current_key - Constants.MAP_HEIGHT;
			if (nodes.containsKey(key)) current_node.addSon(key);
			
			key = current_key + Constants.MAP_HEIGHT;
			if (nodes.containsKey(key)) current_node.addSon(key);
		        
		}
	}
	
	private void initiationForResearsh(int start_key) {
		
		Iterator<Entry<Integer, Node>> it = nodes.entrySet().iterator();
		while (it.hasNext()) {
		    	
			Map.Entry<Integer, Node> pair = (Map.Entry<Integer, Node>)it.next();
			int current_key = (int)pair.getKey();
			Node current_node = pair.getValue();

			if (current_key == start_key) current_node.height = 0;
			else current_node.height = -1;
			
			current_node.checked = false;
			current_node.predecessor = -1;
		        
		}
		
	}
	
	public ConcurrentLinkedQueue<Vector2> findShortestPath(int start_key, int end_key) throws CannotFindPathException {
		
		initiationForResearsh(start_key);
		
		int current_key;
		
		do {
		
			Node current_node;
			int low_height = -1;
			current_key = -1;
		
			Iterator<Entry<Integer, Node>> it = nodes.entrySet().iterator();
			do {
			
				Map.Entry<Integer, Node> pair = (Map.Entry<Integer, Node>)it.next();
				current_node = pair.getValue();
			
				if (!current_node.checked && current_node.height >= 0 && (low_height == -1 || low_height > current_node.height)) {
				
					low_height = current_node.height;
					current_key = (int)pair.getKey();
				
				} 
			} while (it.hasNext() && low_height != 0);

			if (current_key == -1) throw new CannotFindPathException();
			
			nodes.get(current_key).checked = true;

			for (int node : nodes.get(current_key).sons) {
			
				if (!nodes.get(node).checked && (nodes.get(current_key).height + nodes.get(current_key).height - nodes.get(node).height < nodes.get(node).height  || nodes.get(node).height == -1))
				{

					nodes.get(node).height = nodes.get(current_key).height + nodes.get(current_key).height - nodes.get(node).height;
					nodes.get(node).predecessor = current_key;

				}	
			}
		} while (current_key != end_key);
		
		LinkedList<Vector2> inverted_path = new LinkedList<Vector2>();
		do {
			
			inverted_path.add(new Vector2(current_key / Constants.MAP_HEIGHT, current_key % Constants.MAP_HEIGHT));

			current_key = nodes.get(current_key).predecessor;
			
		} while (current_key != -1);
		
		ConcurrentLinkedQueue<Vector2> path = new ConcurrentLinkedQueue<Vector2>();
		for (int i = inverted_path.size() - 1 ; i >= 0 ; --i) {
			
			if (	i == 0 || i == inverted_path.size() - 1 ||
					!((inverted_path.get(i - 1).x == inverted_path.get(i).x && inverted_path.get(i).x == inverted_path.get(i + 1).x) ||
					(inverted_path.get(i - 1).y == inverted_path.get(i).y && inverted_path.get(i).y == inverted_path.get(i + 1).y))) path.add(inverted_path.get(i));
		}
		
		return path;
		
	}
	
	public int getRandomKey() { return keys.get((int)(Math.random() * keys.size())); }
	
}
