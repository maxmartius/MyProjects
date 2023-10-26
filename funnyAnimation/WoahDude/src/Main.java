import javax.swing.*;
import java.awt.*;

public class Main extends JFrame{

    public Main(){
        add(new Game());
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setTitle("WOAH");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Main tm = new Main();
                tm.setVisible(true);

            }
        });
    }
}
