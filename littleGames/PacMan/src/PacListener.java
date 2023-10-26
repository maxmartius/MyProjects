import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacListener extends KeyAdapter{
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key_id = e.getKeyCode();
		
		if(key_id == KeyEvent.VK_LEFT) {
			if(Game.left) {
				Game.direction = 0;
				Game.next_direction = 0;
			}else {
				Game.next_direction = 0;
			}
			
		}else if(key_id == KeyEvent.VK_RIGHT) {
			if(Game.right) {
				Game.direction = 1;
				Game.next_direction = 1;
			}else {
				Game.next_direction = 1;
			}
		}else if(key_id == KeyEvent.VK_UP) {
			if(Game.up) {
				Game.direction = 2;
				Game.next_direction = 2;
			}else {
				Game.next_direction = 2;
			}
		}else if(key_id == KeyEvent.VK_DOWN) {
			if(Game.down) {
				Game.direction = 3;
				Game.next_direction = 3;
			}else {
				Game.next_direction = 3;
			}
		}
		



	}
	

}
