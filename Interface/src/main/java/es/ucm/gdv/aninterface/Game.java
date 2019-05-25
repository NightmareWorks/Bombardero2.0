package es.ucm.gdv.aninterface;

public interface Game {
    //Devuelven las instancias
    Graphics getGraphics();
    Input getInput();

    Input _input = null;
    Graphics _graphics = null;
}
