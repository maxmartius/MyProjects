package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class TetrisGame extends JPanel implements ActionListener{
	
	private int width = 10;
	private int height = 30;//18
	public static int size = 30;
	
	private TetrisListener tl;
	
	private boolean running;
	private Timer t;
	
	private int l;
	private int variant;
	
	private TetrisComponent current;
	
	private int[][] rect = new int[width][height];
	
	private boolean bool;
	
	public static int direction;
	
	
	public TetrisGame() {
		tl = new TetrisListener();
		addKeyListener(tl);
		setPreferredSize(new Dimension(width*size, height*size));
		setFocusable(true);
		setBackground(Color.WHITE);
		
		running = true;
		
		t = new Timer(200, this);
		t.start();
		
		bool = true;
		
		direction = 0;
		
		spawn_new();
		
		for(int i = 0; i < width; i++) {
			for(int l = 0; l < height ; l++) {
				rect[i][l] = 7;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(running) {
			switch(direction) {
				case 1:
					move_left();
					break;
				case 2:
					move_right();
					break;
				case 3:
					turn();
					break;
			}
			check_death();
			move_current();
			check_full_lines();
			direction = 0;
		}
		repaint();
		
	}
	

	private void spawn_new() {
		
		l = (int) (Math.random()*7);
		current = new TetrisComponent(l, 4, 0);
		variant = 0;
		
	}

	private void move_current() {
		for(int i = 0; i < 4; i++) {
			if(current.getRect()[i].y/size + 1 < height && rect[current.getRect()[i].x/size][(current.getRect()[i].y)/size + 1] == 7) {
				bool  = true;
			}else {
				bool = false;
				break;			}
		}
		if(bool) {
			for(int i = 0; i < 4; i++) {
				current.getRect()[i].setBounds(current.getRect()[i].x, current.getRect()[i].y + 1 * size, size, size);
			}
		}else {
			for(int i = 0; i < 4; i++) {
				rect[current.getRect()[i].x/size][current.getRect()[i].y/size] =  l;
			}
			spawn_new();
		}
		
	}
	
	private void move_left() {
		for(int i = 0; i < 4; i++) {
			if(current.getRect()[i].x/size - 1 >= 0 && rect[current.getRect()[i].x/size - 1][(current.getRect()[i].y)/size] == 7) {
				bool  = true;
			}else {
				bool = false;
				break;			}
		}
		if(bool) {
			for(int i = 0; i < 4; i++) {
				current.getRect()[i].setBounds(current.getRect()[i].x - size, current.getRect()[i].y, size, size);
			}
		}else {
			 move_current();
		}
		
	}
	
	private void move_right() {
		for(int i = 0; i < 4; i++) {
			if(current.getRect()[i].x/size + 1 < width && rect[current.getRect()[i].x/size + 1][(current.getRect()[i].y)/size] == 7) {
				bool  = true;
			}else {
				bool = false;
				break;			}
		}
		if(bool) {
			for(int i = 0; i < 4; i++) {
				current.getRect()[i].setBounds(current.getRect()[i].x + size, current.getRect()[i].y, size, size);
			}
		}else {
			 move_current();
		}
		
	}
	
	private void turn() {
		switch(l) {
			case 0:
				break;
			case 1:
				if(variant % 2 == 0 && current.getRect()[1].x/size - 1 >= 0 && 
						rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size] == 7 && rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size + 1] == 7) {
					
					current.getRect()[0].y += 2 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 2 == 1 && 
						rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size] == 7 && rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size - 1] == 7) {
					
					current.getRect()[0].y -= 2 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
					
				}
				break;
			case 2:
				if(variant % 2 == 0 && current.getRect()[2].x/size + 1 < width && 
						rect[current.getRect()[2].x/size + 1][current.getRect()[2].y/size] == 7 && rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size + 1] == 7) {
					
					current.getRect()[0].y += 2 * size;
					current.getRect()[1].x += 1 * size;
					current.getRect()[1].y += 1 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 2 == 1 && 
						rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size] == 7 && rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size - 1] == 7) {
					
					current.getRect()[0].y -= 2 * size;
					current.getRect()[1].x -= 1 * size;
					current.getRect()[1].y -= 1 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
				}
				break;
			case 3:
				if(variant % 4 == 0 && current.getRect()[1].x/size - 1 >= 0 && 
						rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size] == 7 && rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size] == 7 && 
						rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size - 1] == 7) {
					
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].y -= 2 * size;
					variant++;
					
				}else if(variant % 4 == 1 && current.getRect()[1].y/size + 1 < height &&
						rect[current.getRect()[1].x/size][current.getRect()[1].y/size + 1] == 7 && rect[current.getRect()[1].x/size][current.getRect()[1].y/size - 1] == 7 &&
						rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size - 1] == 7) {
					
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].x -= 2 * size;
					variant++;
					
				}else if(variant % 4 == 2 && current.getRect()[1].x/size + 1 < width &&
						rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size] == 7 && rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size + 1] == 7 &&
						rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size] == 7) {
					
					current.getRect()[3].y += 2 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y -= 1 * size;
					variant++;
					
				}else if(variant % 4 == 3 &&
						rect[current.getRect()[1].x/size][current.getRect()[1].y/size - 1] == 7 && rect[current.getRect()[1].x/size][current.getRect()[1].y/size + 1] == 7 &&
						rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size + 1] == 7) {
					
					current.getRect()[3].x += 2 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y -= 1 * size;
					variant++;
					
				}
				break;
			case 4:
				if(variant % 4 == 0 && current.getRect()[2].x/size - 1 >= 0 && 
						rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size] == 7 && rect[current.getRect()[2].x/size + 1][current.getRect()[2].y/size] == 7 && 
						rect[current.getRect()[2].x/size + 1][current.getRect()[2].y/size + 1] == 7) {
			
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[1].x += 0 * size;
					current.getRect()[1].y += 2 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 4 == 1 && 
						rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size + 1] == 7 && rect[current.getRect()[2].x/size][current.getRect()[2].y/size + 1] == 7 && 
						rect[current.getRect()[2].x/size][current.getRect()[2].y/size - 1] == 7) {
			
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[1].x -= 2 * size;
					current.getRect()[1].y += 0 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 4 == 2 && current.getRect()[2].x/size + 1 < width && 
						rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size] == 7 && rect[current.getRect()[2].x/size - 1][current.getRect()[2].y/size - 1] == 7 && 
						rect[current.getRect()[2].x/size + 1][current.getRect()[2].y/size] == 7) {
			
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y -= 1 * size;
					current.getRect()[1].x += 0 * size;
					current.getRect()[1].y -= 2 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
					
				}else if(variant % 4 == 3 && current.getRect()[2].y/size + 1 < height && 
						rect[current.getRect()[2].x/size][current.getRect()[2].y/size - 1] == 7 && rect[current.getRect()[2].x/size + 1][current.getRect()[2].y/size - 1] == 7 && 
						rect[current.getRect()[2].x/size][current.getRect()[2].y/size + 1] == 7) {
			
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y -= 1 * size;
					current.getRect()[1].x += 2 * size;
					current.getRect()[1].y -= 0 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
				}
				break;
			case 5:
				if(variant % 2 == 0 && current.getRect()[1].x/size - 1 >= 0 && current.getRect()[1].x/size + 2 < width && 
						rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size] == 7 && rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size] == 7 && 
						rect[current.getRect()[1].x/size + 2][current.getRect()[1].y/size] == 7) {
			
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].x += 2 * size;
					current.getRect()[3].y -= 2 * size;
					variant++;
					
				}else if(variant % 2 == 1 && current.getRect()[1].y/size + 2 < height && 
						rect[current.getRect()[1].x/size][current.getRect()[1].y/size - 1] == 7 && rect[current.getRect()[1].x/size][current.getRect()[1].y/size + 1] == 7 && 
						rect[current.getRect()[1].x/size][current.getRect()[1].y/size + 2] == 7) {
			
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y -= 1 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[3].x -= 2 * size;
					current.getRect()[3].y += 2 * size;
					variant++;
					
				}
				break;
			case 6:
				if(variant % 4 == 0  && current.getRect()[1].y/size - 1 > 0 && rect[current.getRect()[1].x/size][current.getRect()[1].y/size - 1] == 7) {
					
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y -= 1 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
					
				}else if(variant % 4 == 1 && current.getRect()[1].x/size + 1 < width && rect[current.getRect()[1].x/size + 1][current.getRect()[1].y/size] == 7) {
					
					current.getRect()[0].x += 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y -= 1 * size;
					current.getRect()[3].x -= 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 4 == 2 && current.getRect()[1].y/size + 1 < height && rect[current.getRect()[1].x/size][current.getRect()[1].y/size - 1] == 7) {
					
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y += 1 * size;
					current.getRect()[2].x += 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y -= 1 * size;
					variant++;
					
				}else if(variant % 4 == 3 && current.getRect()[1].x/size - 1 >= 0 && rect[current.getRect()[1].x/size - 1][current.getRect()[1].y/size] == 7) {
					
					current.getRect()[0].x -= 1 * size;
					current.getRect()[0].y -= 1 * size;
					current.getRect()[2].x -= 1 * size;
					current.getRect()[2].y += 1 * size;
					current.getRect()[3].x += 1 * size;
					current.getRect()[3].y += 1 * size;
					variant++;
					
				}
				break;
			default:
				break;
		}
		
	}


	private void check_death() {
		// TODO Auto-generated method stub
		
	}

	private void check_full_lines() {
		
		for(int i = 0; i < height; i++) {
			boolean full = true;
			for(int l = 0; l < width; l++) {
				if(rect[l][i] == 7) {
					full = false;
					break;
				}
			}
			if(full) {
				for(int r = i; r > 0 ; r--) {
					for(int l = 0; l < width; l++) {
						rect[l][r] = rect[l][r-1];
					}
				}
			}
			
		}
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(running) {
			for(int i = 0; i < 4 ; i++) {
				g.setColor(Color.BLACK);
				g.drawRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
				switch(l) {
					case 0:
						g.setColor(Color.RED);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 1:
						g.setColor(Color.GREEN);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 2:
						g.setColor(Color.darkGray);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 3:
						g.setColor(Color.BLUE);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 4:
						g.setColor(Color.PINK);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 5:
						g.setColor(Color.ORANGE);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					case 6:
						g.setColor(Color.YELLOW);
						g.fillRect(current.getRect()[i].x, current.getRect()[i].y, current.getRect()[i].width, current.getRect()[i].height);
						break;
					default:
						break;
				
				}
				
			}
			for(int i = 0; i < width; i++) {
				for(int l = 0; l < height; l++) {
					switch(rect[i][l]) {
						case 0:
							g.setColor(Color.RED);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 1:
							g.setColor(Color.GREEN);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 2:
							g.setColor(Color.darkGray);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 3:
							g.setColor(Color.BLUE);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 4:
							g.setColor(Color.PINK);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 5:
							g.setColor(Color.ORANGE);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 6:
							g.setColor(Color.YELLOW);
							g.fillRect(i*size, l*size, size, size);
							break;
						case 7:
							break;
						default:
							break;
							
					}
				}
			}
			
		}
		Toolkit.getDefaultToolkit().sync();
		
	}
	
}
