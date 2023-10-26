
public class wall {
	
	private quader[] wall_quader;
	private int length;

	public wall(int x, int y, int length, boolean fuck_me) {
		this.length=length;
		this.wall_quader = new quader[this.length];
		if(!fuck_me) {
			for(int i = 0; i < this.length; i++) {
				wall_quader[i] = new quader(x,y+i);
			}
		}else {
			for(int i = 0; i < this.length; i++) {
				wall_quader[i] = new quader(x+i,y);
			}
		}
		
	}
	
	public int getLength() {
		return this.length;
	}
	
	public quader[] get_wall_quader() {
		return this.wall_quader;
	}

}