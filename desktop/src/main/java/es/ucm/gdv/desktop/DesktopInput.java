package es.ucm.gdv.desktop;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.aninterface.Input;

public class DesktopInput implements Input, MouseListener, KeyListener {
    public DesktopInput(){
        _events = new ArrayList<>();
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this){
            ArrayList<TouchEvent> aux = new ArrayList<>(_events);
            _events.clear();
            return aux;
        }
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseEvent.BUTTON1){
            synchronized (this){
                _events.add(new TouchEvent(mouseEvent.getXOnScreen(), mouseEvent.getYOnScreen(), true));
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    ArrayList<TouchEvent> _events;

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyChar() == ' '){
            synchronized (this){
                _events.add(new TouchEvent(0, 0, true));
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }
}
