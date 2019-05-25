package es.ucm.gdv.aninterface;

public interface Game {
    //Devuelven las instancias
    Graphics getGraphics();
    Input getInput();

    void run();
    void render();

    Image[] _images = new Image[16];
}
