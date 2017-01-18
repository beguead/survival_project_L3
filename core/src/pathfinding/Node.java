package pathfinding;

import java.util.ArrayList;

public class Node implements Comparable<Node> {
	
	public int height;
	public boolean checked;
	
	public int predecessor;
	public ArrayList<Integer> sons;
	
	public Node() {
		
		height = -1;
		checked = false;
		predecessor = -1;
		sons = new ArrayList<Integer>();
		
	}
	
	public void addSon(int key) { sons.add(key); };

	@Override
	public int compareTo(Node n) { return this.height - n.height; }

}
