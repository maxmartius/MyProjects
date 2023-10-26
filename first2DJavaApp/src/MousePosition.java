package lul;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MousePosition implements MouseMotionListener{

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        Schatten.mouseX = mouseEvent.getX();
        Schatten.mouseY = mouseEvent.getY();
    }
}
