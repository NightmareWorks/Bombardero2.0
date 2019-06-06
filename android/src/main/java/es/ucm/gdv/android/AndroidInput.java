package es.ucm.gdv.android;

import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import es.ucm.gdv.aninterface.Input;

public class AndroidInput implements Input, View.OnTouchListener {
    public AndroidInput(){_events = new ArrayList<>();}
    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this){
            ArrayList<TouchEvent> touches = new ArrayList<>();
            for(TouchEvent t : _events){
                TouchEvent copy = new TouchEvent(t.getX(),t.getY(),t.getAction());
                touches.add(copy);
            }
            _events.clear();
            return touches;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_UP){
            //Nos aseguramos que solo un hilo toca la variable
            synchronized (this){
                _events.add(new TouchEvent(Math.round(event.getX()), Math.round(event.getY()), true));
            }
            return  true;
        }
        return false;
    }

    List<TouchEvent> _events;
}
