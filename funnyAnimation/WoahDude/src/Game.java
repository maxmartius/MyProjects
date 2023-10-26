import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game extends JPanel implements ActionListener{


    private int size = 10;
    private int width = 100;
    private int height = 100;

    private Punkt mid;

    private Timer timer;

    private boolean running;
    private Linie[] points;

    public Game(){
        setPreferredSize(new Dimension(width*size, height*size));
        setFocusable(true);
        setBackground(Color.BLUE);

        running= true;
        timer = new Timer(1,this);
        timer.start();

        mid = new Punkt(width*size*0.5,height*size*0.5);

        createLines();

    }

    private void createLines() {
        this.points = new Linie[110];
        for(int i = 1; i <= points.length; i++){
            double laenge = (points.length*5+10)-i*5;
            //double laenge = i*5;
            points[i-1] = new Linie(mid, laenge,0);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            rotate();

            repaint();
            //running = false;
        }
    }

    private void rotate() {
        for(int i = 0; i < points.length; i++){
            Linie newline = new Linie(points[i].getUrsprungsPunkt(),points[i].getLaenge(),points[i].getGrad()+(i+1)*0.05);
            points[i] = newline;
        }

    }

    @Override
    protected void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        Graphics2D g = (Graphics2D) graph;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


        if(running){
            for (int i = 0; i < points.length-1; i++){

                g.setColor(Color.RED);
                if(i%2==0){
                    g.setColor(Color.BLACK);
                }
                g.drawLine((int)points[i].getEndPunkt().getX(),(int)points[i].getEndPunkt().getY(),
                        (int)points[i+1].getEndPunkt().getX(),(int)points[i+1].getEndPunkt().getY());
            }
//            for(Linie line:points){
//                g.setColor(Color.RED);
//                g.fillOval((int)line.getEndPunkt().getX()-3,(int)line.getEndPunkt().getY()-3,1,1);
//            }

        }

    }
}
