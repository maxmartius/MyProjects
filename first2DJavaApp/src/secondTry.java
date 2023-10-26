package lul;

import javax.swing.*;
import java.awt.*;

public class secondTry extends JFrame{

    public secondTry(){
        add(new Schatten());
        setResizable(false);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("MouseFinder");
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                secondTry sT = new secondTry();
                sT.setVisible(true);

            }
        });

    }
}
