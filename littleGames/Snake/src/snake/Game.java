package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel implements ActionListener{
	
	private int width = 300;
	private int height = 300;
	
	private Image apple;
	private int apple_x;
	private int apple_y;
	
	private Image head_L;
	private Image head_D;
	private Image head_R;
	private Image head_U;
	private Image tail;
	
	private int snake_size = 3;
	private int snake_width = 10;
	private int snake_x[] = new int[width*height/(snake_width*snake_width)];
	private int snake_y[] = new int[width*height/(snake_width*snake_width)];
	
	
	private boolean running;
	private Timer t;
	
	private JButton jb = new JButton("retry");;
	
	//0=left, 1=right, 2=up, 3=down
	public static int direction  = 1;
	
	private SnakeListener sl;
	
	
	public Game() {
		this.sl = new SnakeListener();
		addKeyListener(sl);
		setPreferredSize(new Dimension(width, height));
		setFocusable(true);
		setBackground(Color.DARK_GRAY);
		
		add(jb);
		jb.setVisible(false);
		jb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		
		
		ImageIcon icon_apple = new ImageIcon("apple.png");
		ImageIcon icon_head_L = new ImageIcon("head_L.png");
		ImageIcon icon_head_R = new ImageIcon("head_R.png");
		ImageIcon icon_head_D = new ImageIcon("head_D.png");
		ImageIcon icon_head_U = new ImageIcon("head_U.png");
		ImageIcon icon_tail = new ImageIcon("tail.png");
		
		apple = icon_apple.getImage();
		head_L = icon_head_L.getImage();
		head_R = icon_head_R.getImage();
		head_U = icon_head_U.getImage();
		head_D = icon_head_D.getImage();
		tail = icon_tail.getImage();
		
		for(int i= 0; i < snake_size ; i++) {
			snake_x[i] = 160 - i*10;
			snake_y[i] = 150;
		}
		
		running = true;
		t = new Timer(200, this);
		t.start();
		
		spawn_apple();
		
	}
	
	private void spawn_apple() {
		int rdm = (int) (Math.random()*29);
		apple_x =rdm*snake_width;
		rdm = (int) (Math.random()*29);
		apple_y =rdm*snake_width;
		for(int i = 0; i <= snake_size; i++) {
			if(apple_x == snake_x[i] && apple_y == snake_y[i]) {
				spawn_apple();
				break;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			check_apple();
			check_death();
			move_snake();
			
		}
		repaint();
	}


	private void move_snake() {
		for(int i = snake_size; i > 0; i--) {
			snake_x[i]=snake_x[i-1];
			snake_y[i]=snake_y[i-1];
		}
		
		switch (direction) {
		case 0:
			snake_x[0] -= snake_width;
			break;
		case 1:
			snake_x[0] += snake_width;
			break;
		case 2:
			snake_y[0] -= snake_width;
			break;
		case 3:
			snake_y[0] += snake_width;
			break;
		default:
			break;
		}
	}

	private void check_death() {
		for(int i = snake_size; i > 3; i--) {
			if(snake_x[0] == snake_x[i] && snake_y[0] == snake_y[i]) {
				running = false;
			}
		}
		if(snake_x[0] >= height || snake_y[0] >= width || snake_x[0] <= 0 || snake_y[0] <= 0) {
			running = false;
		}
		if(!running) {
			t.stop();
			jb.setVisible(true);
		}
	}

	private void check_apple() {
		if(snake_x[0] == apple_x && snake_y[0] == apple_y) {
			snake_size++;
			spawn_apple();
			
		}
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(running) {
			g.drawImage(apple, apple_x, apple_y, this);
			
			for(int i = snake_size-1; i > 0; i--) {
				g.drawImage(tail, snake_x[i], snake_y[i], this);
			}
			if(direction == 0) {
				g.drawImage(head_L, snake_x[0], snake_y[0], this);
			}else if(direction == 1) {
				g.drawImage(head_R, snake_x[0], snake_y[0], this);
			}else if(direction == 2) {
				g.drawImage(head_U, snake_x[0], snake_y[0], this);
			}else if(direction == 3) {
				g.drawImage(head_D, snake_x[0], snake_y[0], this);
			}
			Toolkit.getDefaultToolkit().sync();
		}
		else {
			Font f = new Font("Calibri", Font.BOLD, 16);
			FontMetrics metrics = getFontMetrics(f);
			
			g.setColor(Color.RED);
			g.setFont(f);
			String s = "GAME OVER";
			g.drawString(s,((width/2) - metrics.stringWidth(s)/2), height/2);
		}
	}

	public void restart() {
		this.sl = new SnakeListener();
		addKeyListener(sl);
		jb.setVisible(false);
		direction = 1;
		snake_size = 3;
		for(int i= 0; i < snake_size ; i++) {
			snake_x[i] = 160 - i*10;
			snake_y[i] = 150;
		}
		
		running = true;
		t = new Timer(200, this);
		t.start();
		addKeyListener(new SnakeListener());
		spawn_apple();
		
	}
	
	
}
