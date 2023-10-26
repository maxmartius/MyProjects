import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	private int width = 280;
	private int height= 310;
	private int size = 10;
	
	private PacListener pl;
	//0=left, 1=right, 2=up, 3=down
	public static int direction;
	public static int next_direction;
	
	private boolean running;
	private Timer t;
	
	
	
	private wall[] wall_hor = walls_hor();
	private wall[] wall_vert = walls_vert();
	private ArrayList<apple> apples;
	
	private int pm_x;
	private int pm_y;
	
	private Image wall_image;
	private Image apple_image;
	private Image head_R;
	
	public static boolean up;
	public static boolean down;
	public static boolean left;
	public static boolean right;
	
	public Game() {
		pl = new PacListener();
		addKeyListener(pl);
		setPreferredSize(new Dimension(width, height));
		setBackground(Color.BLUE);
		setFocusable(true);
		
		ImageIcon wall_icon = new ImageIcon("wall.png");
		ImageIcon apple_icon = new ImageIcon("apple.png");
		ImageIcon head_icon_R = new ImageIcon("head_R.png");
		wall_image = wall_icon.getImage();
		apple_image = apple_icon.getImage();
		head_R = head_icon_R.getImage();
		
		apples = spawn_apples();
		pm_x = 13;
		pm_y = 23;
		direction = 1;
		next_direction = 1;
		
		up = true;
		down = true;
		left = true;
		right = true;
		
		
		running = true;
		t = new Timer(200, this);
		t.start();
		
	}

	

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(running) {
			up = true;
			down = true;
			left = true;
			right = true;
			remove_apples();
			move_pacman();
			check_directions();
			check_next_direction();
		}
		repaint();
		
	}



	private void check_next_direction() {
		if(next_direction == 0 && left) {
			direction = next_direction;
		}else if(next_direction == 1 && right) {
			direction = next_direction;
		}else if(next_direction == 2 && up) {
			direction = next_direction;
		}else if(next_direction == 3 && down) {
			direction = next_direction;
		}
		
	}



	private void move_pacman() {
		switch (direction) {
		case 0:
			if(pm_x > 0) {
				pm_x --;
			}else {
				pm_x = width/size;
			}
			
			break;
		case 1:
			if(pm_x < width/size) {
				pm_x ++;
			}else {
				pm_x = 0;
			}
			break;
		case 2:
			pm_y --;
			break;
		case 3:
			pm_y ++;
			break;
		default:
			break;
		}
		
	}



	private void remove_apples() {
		apples.get(pm_x+pm_y*width/size).setVisible(false);
		
	}



	private void check_directions() {
		for(int i = 0; i < walls_hor().length; i++) {
			for(int l = 0; l < walls_hor()[i].getLength(); l++){
				if(pm_x == walls_hor()[i].get_wall_quader()[l].getX() && pm_y-1 == walls_hor()[i].get_wall_quader()[l].getY()) {
					up = false;
					if(direction == 2) {
						direction = 4;
					}
				}
				if(pm_x == walls_hor()[i].get_wall_quader()[l].getX() && pm_y+1 == walls_hor()[i].get_wall_quader()[l].getY()) {
					down = false;
					if(direction == 3) {
						direction = 4;
					}
				}
				if(pm_x+1 == walls_hor()[i].get_wall_quader()[l].getX() && pm_y == walls_hor()[i].get_wall_quader()[l].getY()) {
					right = false;
					if(direction == 1) {
						direction = 4;
					}
				}
				if(pm_x-1 == walls_hor()[i].get_wall_quader()[l].getX() && pm_y == walls_hor()[i].get_wall_quader()[l].getY()) {
					left = false;
					if(direction == 0) {
						direction = 4;
					}
				}
			}
		}
		for(int i = 0; i < walls_vert().length; i++) {
			for(int l = 0; l < walls_vert()[i].getLength(); l++){
				if(pm_x == walls_vert()[i].get_wall_quader()[l].getX() && pm_y-1 == walls_vert()[i].get_wall_quader()[l].getY()) {
					up = false;
					if(direction == 2) {
						direction = 4;
					}
				}
				if(pm_x == walls_vert()[i].get_wall_quader()[l].getX() && pm_y+1 == walls_vert()[i].get_wall_quader()[l].getY()) {
					down = false;
					if(direction == 3) {
						direction = 4;
					}
				}
				if(pm_x+1 == walls_vert()[i].get_wall_quader()[l].getX() && pm_y == walls_vert()[i].get_wall_quader()[l].getY()) {
					right = false;
					if(direction == 1) {
						direction = 4;
					}
				}
				if(pm_x-1 == walls_vert()[i].get_wall_quader()[l].getX() && pm_y == walls_vert()[i].get_wall_quader()[l].getY()) {
					left = false;
					if(direction == 0) {
						direction = 4;
					}
				}
			}
			
		}
		
		
		
	}
	
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(running) {
			for(int i = 0; i < apples.size(); i++) {
				if(apples.get(i).getVisible()) {
					g.drawImage(apple_image, apples.get(i).getX()*size, apples.get(i).getY()*size,this);
				}
				
			}
			for(int i = 0; i < wall_hor.length; i++) {
				for(int l = 0; l < wall_hor[i].getLength(); l++) {
					g.drawImage(wall_image, wall_hor[i].get_wall_quader()[l].getX()*size, wall_hor[i].get_wall_quader()[l].getY()*size, this);
				}
				
			}
			for(int i = 0; i < wall_vert.length; i++) {
				for(int l = 0; l < wall_vert[i].getLength(); l++) {
					g.drawImage(wall_image,  wall_vert[i].get_wall_quader()[l].getX()*size, wall_vert[i].get_wall_quader()[l].getY()*size, this);
				}
				
			}
			g.drawImage(head_R, pm_x*size, pm_y*size, this);
			g.drawRect(10, 10,50, 50);
			
			Toolkit.getDefaultToolkit().sync();
			
		}else {
			Font f = new Font("Calibri", Font.BOLD, 16);
			FontMetrics metrics = getFontMetrics(f);
			
			g.setColor(Color.RED);
			g.setFont(f);
			String s = "GAME OVER";
			g.drawString(s,((width/2) - metrics.stringWidth(s)/2), height/2);
		}
		
		
	}
	
	
	private ArrayList<apple> spawn_apples() {
		ArrayList<apple> tmp = new ArrayList<apple>();
		for(int i = 0; i < height/size; i++) {
			for(int l = 0; l < width/size; l++) {
				apple a = new apple(l, i, true);
				tmp.add(a);
			}
		}
		for(int i = 0; i < wall_hor.length; i++) {
			for(int l = 0; l < wall_hor[i].getLength(); l++) {
				int x = wall_hor[i].get_wall_quader()[l].getX();
				int y = wall_hor[i].get_wall_quader()[l].getY();
				tmp.get(x+y*width/size).setVisible(false);
			}
		}
		for(int i = 0; i < wall_vert.length; i++) {
			for(int l = 0; l < wall_vert[i].getLength(); l++) {
				int x = wall_vert[i].get_wall_quader()[l].getX();
				int y = wall_vert[i].get_wall_quader()[l].getY();
				tmp.get(x+y*width/size).setVisible(false);
			}
		}
		tmp.get(3*28+3).setVisible(false);
		tmp.get(3*28+4).setVisible(false);
		tmp.get(3*28+8).setVisible(false);
		tmp.get(3*28+9).setVisible(false);
		tmp.get(3*28+10).setVisible(false);
		tmp.get(3*28+19).setVisible(false);
		tmp.get(3*28+17).setVisible(false);
		tmp.get(3*28+18).setVisible(false);
		tmp.get(3*28+24).setVisible(false);
		tmp.get(3*28+23).setVisible(false);
		tmp.get(11*28+0).setVisible(false);
		tmp.get(11*28+1).setVisible(false);
		tmp.get(11*28+2).setVisible(false);
		tmp.get(11*28+3).setVisible(false);
		tmp.get(11*28+4).setVisible(false);
		tmp.get(11*28+27).setVisible(false);
		tmp.get(11*28+23).setVisible(false);
		tmp.get(11*28+24).setVisible(false);
		tmp.get(11*28+25).setVisible(false);
		tmp.get(11*28+26).setVisible(false);
		tmp.get(10*28+0).setVisible(false);
		tmp.get(10*28+1).setVisible(false);
		tmp.get(10*28+2).setVisible(false);
		tmp.get(10*28+3).setVisible(false);
		tmp.get(10*28+4).setVisible(false);
		tmp.get(10*28+27).setVisible(false);
		tmp.get(10*28+23).setVisible(false);
		tmp.get(10*28+24).setVisible(false);
		tmp.get(10*28+25).setVisible(false);
		tmp.get(10*28+26).setVisible(false);
		tmp.get(12*28+0).setVisible(false);
		tmp.get(12*28+1).setVisible(false);
		tmp.get(12*28+2).setVisible(false);
		tmp.get(12*28+3).setVisible(false);
		tmp.get(12*28+4).setVisible(false);
		tmp.get(12*28+27).setVisible(false);
		tmp.get(12*28+23).setVisible(false);
		tmp.get(12*28+24).setVisible(false);
		tmp.get(12*28+25).setVisible(false);
		tmp.get(12*28+26).setVisible(false);
		tmp.get(16*28+0).setVisible(false);
		tmp.get(16*28+1).setVisible(false);
		tmp.get(16*28+2).setVisible(false);
		tmp.get(16*28+3).setVisible(false);
		tmp.get(16*28+4).setVisible(false);
		tmp.get(16*28+27).setVisible(false);
		tmp.get(16*28+23).setVisible(false);
		tmp.get(16*28+24).setVisible(false);
		tmp.get(16*28+25).setVisible(false);
		tmp.get(16*28+26).setVisible(false);
		tmp.get(17*28+0).setVisible(false);
		tmp.get(17*28+1).setVisible(false);
		tmp.get(17*28+2).setVisible(false);
		tmp.get(17*28+3).setVisible(false);
		tmp.get(17*28+4).setVisible(false);
		tmp.get(17*28+27).setVisible(false);
		tmp.get(17*28+23).setVisible(false);
		tmp.get(17*28+24).setVisible(false);
		tmp.get(17*28+25).setVisible(false);
		tmp.get(17*28+26).setVisible(false);
		tmp.get(18*28+0).setVisible(false);
		tmp.get(18*28+1).setVisible(false);
		tmp.get(18*28+2).setVisible(false);
		tmp.get(18*28+3).setVisible(false);
		tmp.get(18*28+4).setVisible(false);
		tmp.get(18*28+27).setVisible(false);
		tmp.get(18*28+23).setVisible(false);
		tmp.get(18*28+24).setVisible(false);
		tmp.get(18*28+25).setVisible(false);
		tmp.get(18*28+26).setVisible(false);
		tmp.get(13*28+11).setVisible(false);
		tmp.get(13*28+12).setVisible(false);
		tmp.get(13*28+13).setVisible(false);
		tmp.get(13*28+14).setVisible(false);
		tmp.get(13*28+15).setVisible(false);
		tmp.get(13*28+16).setVisible(false);
		tmp.get(14*28+11).setVisible(false);
		tmp.get(14*28+12).setVisible(false);
		tmp.get(14*28+13).setVisible(false);
		tmp.get(14*28+14).setVisible(false);
		tmp.get(14*28+15).setVisible(false);
		tmp.get(14*28+16).setVisible(false);
		tmp.get(15*28+11).setVisible(false);
		tmp.get(15*28+12).setVisible(false);
		tmp.get(15*28+13).setVisible(false);
		tmp.get(15*28+14).setVisible(false);
		tmp.get(15*28+15).setVisible(false);
		tmp.get(15*28+16).setVisible(false);
		tmp.get(16*28+11).setVisible(false);
		tmp.get(16*28+12).setVisible(false);
		tmp.get(16*28+13).setVisible(false);
		tmp.get(16*28+14).setVisible(false);
		tmp.get(16*28+15).setVisible(false);
		tmp.get(16*28+16).setVisible(false);
		
		return tmp;
	}

	private wall[] walls_hor() {
		wall[] tmp = new wall[50];
		
		tmp[0] = new wall(1,1,28,true);
		tmp[1] = new wall(3,3,4,true);
		tmp[2] = new wall(8,3,5,true);
		tmp[3] = new wall(17,3,5,true);
		tmp[4] = new wall(23,3,4,true);
		tmp[5] = new wall(3,5,4,true);
		tmp[6] = new wall(8,5,5,true);
		tmp[7] = new wall(17,5,5,true);
		tmp[8] = new wall(23,5,4,true);
		tmp[9] = new wall(3,7,4,true);
		tmp[10] = new wall(11,7,8,true);
		tmp[11] = new wall(23,7,4,true);
		tmp[12] = new wall(3,8,4,true);
		tmp[13] = new wall(11,8,8,true);
		tmp[14] = new wall(23,8,4,true);
		tmp[15] = new wall(1,10,6,true);
		tmp[16] = new wall(9,10,4,true);
		tmp[17] = new wall(17,10,4,true);
		tmp[18] = new wall(23,10,6,true);
		tmp[19] = new wall(9,11,4,true);
		tmp[20] = new wall(17,11,4,true);
		tmp[21] = new wall(1,14,6,true);
		tmp[22] = new wall(11,13,8,true);
		tmp[23] = new wall(23,14,6,true);
		tmp[24] = new wall(1,16,6,true);
		tmp[25] = new wall(11,17,8,true);
		tmp[26] = new wall(23,16,6,true);
		tmp[27] = new wall(11,19,8,true);
		tmp[28] = new wall(1,20,6,true);
		tmp[29] = new wall(11,20,8,true);
		tmp[30] = new wall(23,20,6,true);
		tmp[31] = new wall(3,22,4,true);
		tmp[32] = new wall(8,22,5,true);
		tmp[33] = new wall(17,22,5,true);
		tmp[34] = new wall(23,22,4,true);
		tmp[35] = new wall(3,23,4,true);
		tmp[36] = new wall(8,23,5,true);
		tmp[37] = new wall(17,23,5,true);
		tmp[38] = new wall(23,23,4,true);
		tmp[39] = new wall(1,25,3,true);
		tmp[40] = new wall(11,25,8,true);
		tmp[41] = new wall(26,25,3,true);
		tmp[42] = new wall(1,26,3,true);
		tmp[43] = new wall(11,26,8,true);
		tmp[44] = new wall(26,26,3,true);
		tmp[45] = new wall(3,28,10,true);
		tmp[46] = new wall(17,28,10,true);
		tmp[47] = new wall(3,29,10,true);
		tmp[48] = new wall(17,29,10,true);
		tmp[49] = new wall(1,31,28,true);
		
		return tmp;
	}

	private wall[] walls_vert() {
		wall[] tmp = new wall[42];
		
		tmp[0] = new wall(1,1,10,false);
		tmp[1] = new wall(1,20,11,false);
		tmp[2] = new wall(3,3,3,false);
		tmp[3] = new wall(5,23,4,false);
		tmp[4] = new wall(6,3,3,false);
		tmp[5] = new wall(6,10,5,false);
		tmp[6] = new wall(6,17,4,false);
		tmp[7] = new wall(6,23,4,false);
		tmp[8] = new wall(8,3,3,false);
		tmp[9] = new wall(8,7,8,false);
		tmp[10] = new wall(8,16,5,false);
		tmp[11] = new wall(8,25,3,false);
		tmp[12] = new wall(9,7,8,false);
		tmp[13] = new wall(9,16,5,false);
		tmp[14] = new wall(9,25,3,false);
		tmp[15] = new wall(11,13,5,false);
		tmp[16] = new wall(14,1,5,false);
		tmp[17] = new wall(14,7,5,false);
		tmp[18] = new wall(14,19,5,false);
		tmp[19] = new wall(14,25,5,false);
		tmp[20] = new wall(15,1,5,false);
		tmp[21] = new wall(15,7,5,false);
		tmp[22] = new wall(15,19,5,false);
		tmp[23] = new wall(15,25,5,false);
		tmp[24] = new wall(17,3,3,false);
		tmp[25] = new wall(18,13,5,false);
		tmp[26] = new wall(20,7,8,false);
		tmp[27] = new wall(20,16,5,false);
		tmp[28] = new wall(20,25,5,false);
		tmp[29] = new wall(21,7,8,false);
		tmp[30] = new wall(21,16,5,false);
		tmp[31] = new wall(21,25,5,false);
		tmp[32] = new wall(21,3,3,false);
		tmp[33] = new wall(23,3,3,false);
		tmp[34] = new wall(23,10,5,false);
		tmp[35] = new wall(23,16,5,false);
		tmp[36] = new wall(23,22,5,false);
		tmp[37] = new wall(24,22,5,false);
		tmp[38] = new wall(26,3,3,false);
		tmp[39] = new wall(28,1,10,false);
		tmp[40] = new wall(28,21,10,false);
		tmp[41] = new wall(12,3,3,false);
		
		
		
		return tmp;
	}
	

}
