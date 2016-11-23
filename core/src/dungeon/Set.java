package dungeon;

public class Set {
	
	private Set father;
	public int rank;

	public Set() {
		
		father = this;
		rank = 0;
		
	}
	
	public Set find() {
		
		if (father != this) father = father.find();
		
		return father;
		
	}
	
	public void union(Set s) {
		
		Set this_root = this.find();
		Set s_root = s.find();
		
		if (this_root != s_root) {
			
			if (this_root.rank < s_root.rank) this_root.setFather(s_root);
			else {
				
				s_root.setFather(this_root);
				if (this_root.rank == s_root.rank) ++rank;
				
			}
			
		}
		
	}
	
	public void setFather(Set s) {
		
		this.father = s;
		
	}
	
}
