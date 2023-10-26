package tetris;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class TetrisMain extends JFrame{
	
	public TetrisMain() {
		add(new TetrisGame());
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				TetrisMain tm = new TetrisMain();
				tm.setVisible(true);
				
			}
		});

	}

}
