package es.ucm.gdv.aninterface;

import java.util.List;

public interface Input {
    //clase que representa la información de un toque sobre
    //la pantalla
    //Indicará el tipo (pulsación, liberación, desplazamiento),
    // la posición y el identificador del “dedo” (o botón).
    class TouchEvent{

    }

    // devuelve la lista de eventos recibidos desde la última invocación.
    List<TouchEvent> getTouchEvents();
}
