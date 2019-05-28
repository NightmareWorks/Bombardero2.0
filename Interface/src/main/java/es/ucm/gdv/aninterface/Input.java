package es.ucm.gdv.aninterface;

import java.util.List;

public interface Input {
    //clase que representa la información de un toque sobre
    //la pantalla
    //Indicará el tipo (pulsación, liberación, desplazamiento),
    // la posición y el identificador del “dedo” (o botón).
    class TouchEvent{
        public TouchEvent(float _x, float _y, Boolean action) {
            this._x = _x;
            this._y = _y;
            this._action = action;
        }

        public float get_x() {
            return _x;
        }

        public float get_y() {
            return _y;
        }

        private float _x, _y;

        public boolean get_action() {
            return _action;
        }

        private boolean _action;
        //Action es false si DOWN y true si UP
    }

    // devuelve la lista de eventos recibidos desde la última invocación.
    List<TouchEvent> getTouchEvents();
}
