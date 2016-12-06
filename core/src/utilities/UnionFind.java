package utilities;

public class UnionFind {
	
	private UnionFind father;
	public int id;

	public UnionFind() {
		
		father = this;
		id = 0;
		
	}
	
	public UnionFind find() {
		
		if (father != this) father = father.find();
		
		return father;
		
	}
	
	public void union(UnionFind s) {
		
		UnionFind this_root = this.find();
		UnionFind s_root = s.find();
		
		if (this_root != s_root) {
			
			if (this_root.id < s_root.id) this_root.setFather(s_root);
			else {
				
				s_root.setFather(this_root);
				if (this_root.id == s_root.id) ++id;
				
			}
			
		}
		
	}
	
	public void setFather(UnionFind s) {
		
		this.father = s;
		
	}
	
}
