
public class apple {
	
	private int x;
	private int y;
	private boolean visible;
	
	public apple(int x, int y, boolean visible) {
		this.x = x;
		this.y = y;
		this.visible = visible;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean getVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}
	
}
