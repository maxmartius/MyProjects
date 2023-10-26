package tetris;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TetrisListener extends KeyAdapter{
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key_id = e.getKeyCode();
		
		if(key_id == KeyEvent.VK_LEFT) {
			TetrisGame.direction = 1;
		}else if(key_id == KeyEvent.VK_RIGHT) {
			TetrisGame.direction = 2;
		}else if(key_id == KeyEvent.VK_UP) {
			TetrisGame.direction = 3;
		}
		
	}

}
