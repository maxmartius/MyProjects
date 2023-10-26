package firstSwing;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SwingMain extends JFrame{
	
	public SwingMain() {
		setTitle("FirstSwing");
		setSize(1600,800);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable(){
			
			@Override
			public void run() {
				SwingMain sm = new SwingMain();
				sm.setVisible(true);
				
			}
		});

	}

}
