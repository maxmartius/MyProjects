package lul;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Schatten extends JPanel implements ActionListener{

    public int height = 500;
    public int width = 700;

    private ArrayList<Polygon> polygons;
    private Circle circle;
    private ArrayList<Line> lines;


    public static int mouseX;
    public static int mouseY;
    public static boolean running;

    public Timer t;

    public Schatten(){
        MousePosition mp = new MousePosition();
        addMouseMotionListener(mp);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.WHITE);
        setFocusable(true);

        running = true;
        lines = test1();
        polygons = new ArrayList<Polygon>();

        t = new Timer(1, this);
        t.start();
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(running){
            refactorCirkle();
            refactorPolygon();
        }
        repaint();
    }

    private void refactorPolygon() {
        polygons.clear();
        for(Line l : lines){
            int x1 = 0,x2 = 0,y1 = 0,y2 = 0;
            double anstieg1 = (mouseY-l.getEndY())/(mouseX-l.getEndX());
            double anstieg2 = (mouseY-l.getStartY())/(mouseX-l.getStartX());
            double n1 = mouseY - anstieg1*mouseX;
            double n2 = mouseY - anstieg2*mouseX;
            if(mouseX>l.getEndX()){
                y1 = (int) n1;
            }else if(mouseX==l.getEndX()){
                x1=mouseX;
                if(mouseY<l.getEndY()){
                    y1=height;
                }else {
                    y1=0;
                }
            }else{
                x1 = width;
                y1 = (int)(anstieg1*width + n1);
            }
            if(mouseX>l.getStartX()){
                y2 = (int) n2;
            }else if(mouseX==l.getStartX()){
                x2=mouseX;
                if(mouseY<l.getStartY()){
                    y2=height;
                }else {
                    y2=0;
                }
            }else{
                x2 = width;
                y2 = (int)(anstieg2*width + n2);
            }
            Polygon polygon = new Polygon();
            polygon.addPoint((int)l.getStartX(),(int)l.getStartY());
            polygon.addPoint((int)l.getEndX(),(int)l.getEndY());
            polygon.addPoint(x1,y1);
            if(x1 != x2){
                if(x1 == 0){
                    if(y1<0 && y2>=0){
                        polygon.addPoint(width,0);
                    }else if (y1>height && y2< height) {
                        polygon.addPoint(width,height);
                    }else if(y1<l.getEndY()){
                        if(y2 == 0){
                            polygon.addPoint(0,0);
                        }else if(anstieg1<anstieg2){
                            polygon.addPoint(0,height);
                            polygon.addPoint(width,height);
                        }else{
                            polygon.addPoint(0,0);
                            polygon.addPoint(width,0);
                        }
                    }else if(y1>l.getEndY()){
                        if(y2 == height){
                            polygon.addPoint(0,height);
                        }else if(anstieg1<anstieg2){
                            polygon.addPoint(0,height);
                            polygon.addPoint(width,height);
                        }else {
                            polygon.addPoint(0,0);
                            polygon.addPoint(width,0);
                        }
                    }else {
                        if(y2>y1){
                            polygon.addPoint(0,height);
                            if(y2>height){
                                polygon.addPoint(width,height);
                            }
                        }else {
                            polygon.addPoint(0,0);
                            if(y2<0){
                                polygon.addPoint(width,0);
                            }
                        }
                    }
                }else if(x2 == 0){
                    if(y2<0 && y1>=0){
                        polygon.addPoint(width,0);
                    }else if (y2>height && y1< height) {
                        polygon.addPoint(width,height);
                    }else if(y2>l.getStartY()){
                        if(y1 == height){
                            polygon.addPoint(0,height);
                        }else if(anstieg2>anstieg1){
                            polygon.addPoint(width,0);
                            polygon.addPoint(0,0);
                        }else{
                            polygon.addPoint(width,height);
                            polygon.addPoint(0,height);
                        }
                    }else if(y2<l.getStartY()){
                        if(y1 == 0){
                            polygon.addPoint(0,0);
                        }else if(anstieg2<anstieg1){
                            polygon.addPoint(0,height);
                            polygon.addPoint(width,height);
                        }else{
                            polygon.addPoint(width,0);
                            polygon.addPoint(0,0);
                        }
                    }else {
                        if(y1>y2){
                            polygon.addPoint(0,height);
                            if(y1>height){
                                polygon.addPoint(width,height);
                            }
                        }else{
                            polygon.addPoint(0,0);
                            if(y1<0){
                                polygon.addPoint(width,0);
                            }
                        }
                    }
                }else if(y1 == 0 || y1 == height){
                    polygon.addPoint(x2,y1);
                }else if(y2 == 0 || y2 == height){
                    polygon.addPoint(x1,y2);
                }
            }
            polygon.addPoint(x2,y2);
            polygons.add(polygon);
        }
    }


    private void refactorCirkle() {
        circle = new Circle(mouseX,mouseY, 20);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (running) {
            g.drawString(Integer.toString(mouseX),10,10);
            g.drawString(Integer.toString(mouseY),20,20);
            g.fillOval((int)circle.getCenterX()-(int) circle.getRadius()/2,(int)circle.getCenterY()-(int)circle.getRadius()/2,(int)circle.getRadius(),(int)circle.getRadius());
            for(Line l : lines){
                g.drawLine((int)l.getStartX(),(int)l.getStartY(),(int)l.getEndX(),(int)l.getEndY());
            }
            for (Polygon p : polygons){
                g.fillPolygon(p);
            }

        }
    }

    private ArrayList<Line> test1(){
        ArrayList<Line> rst = new ArrayList<Line>();
        rst.add(new Line(100,300,100,100));
        rst.add(new Line(100,300,300,300));
        rst.add(new Line(300,300,300,100));
        rst.add(new Line(100,100,300,100));
        rst.add(new Line(50,300,204,213));
        rst.add(new Line(400,240,123,123));
        return rst;
    }


}
