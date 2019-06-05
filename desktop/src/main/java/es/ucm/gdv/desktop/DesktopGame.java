package es.ucm.gdv.desktop;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Input;

public class DesktopGame implements Game {
    public DesktopGame(String name){
        _graphics = new DesktopGraphics(name);
        _input = new DesktopInput();
    }

    public void init(){
        _graphics.init();
        //Le añadimos el listener a la pantalla
        _graphics.addMouseListener(_input);
    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    private DesktopGraphics _graphics;
    private DesktopInput _input;
}
