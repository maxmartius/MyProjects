package tetris;

import java.awt.Polygon;
import java.awt.Rectangle;


public class TetrisComponent {
	
	private Rectangle[] rect = new Rectangle[4];
	private int size = TetrisGame.size;
	
	public TetrisComponent(int l, int x, int y) {
		initComponent(l, x, y);
	}
	
	private void initComponent(int l, int x, int y) {
		
		switch (l) {
			case 0:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((1 + x)*size ,(0 + y)*size , size, size);
				rect[2] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[3] = new Rectangle((1 + x)*size ,(1 + y)*size , size, size);
				break;
			case 1:
				rect[0] = new Rectangle((1 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[2] = new Rectangle((1 + x)*size ,(1 + y)*size , size, size);
				rect[3] = new Rectangle((0 + x)*size ,(2 + y)*size , size, size);
				break;
			case 2:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[2] = new Rectangle((1 + x)*size ,(1 + y)*size , size, size);
				rect[3] = new Rectangle((1 + x)*size ,(2 + y)*size , size, size);
				break;
			case 3:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[2] = new Rectangle((0 + x)*size ,(2 + y)*size , size, size);
				rect[3] = new Rectangle((1 + x)*size ,(2 + y)*size , size, size);
				break;
			case 4:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((1 + x)*size ,(0 + y)*size , size, size);
				rect[2] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[3] = new Rectangle((0 + x)*size ,(2 + y)*size , size, size);
				break;
			case 5:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((0 + x)*size ,(1 + y)*size , size, size);
				rect[2] = new Rectangle((0 + x)*size ,(2 + y)*size , size, size);
				rect[3] = new Rectangle((0 + x)*size ,(3 + y)*size , size, size);
				break;
			case 6:
				rect[0] = new Rectangle((0 + x)*size ,(0 + y)*size , size, size);
				rect[1] = new Rectangle((1 + x)*size ,(0 + y)*size , size, size);
				rect[2] = new Rectangle((1 + x)*size ,(1 + y)*size , size, size);
				rect[3] = new Rectangle((2 + x)*size ,(0 + y)*size , size, size);
				break;
			default:
				break;
			}
	}
	
	public Rectangle[] getRect() {
		return this.rect;
	}
}