import java.awt.EventQueue;

import javax.swing.JFrame;

public class PacMain  extends JFrame{
	
	public PacMain(){
		add(new Game());
		setResizable(false);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("PacMan");
		
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				PacMain pm = new PacMain();
				pm.setVisible(true);
				
			}
		});

	}

}
