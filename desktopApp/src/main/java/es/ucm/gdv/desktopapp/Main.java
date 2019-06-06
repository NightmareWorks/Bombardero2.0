package es.ucm.gdv.desktopapp;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.desktop.DesktopGame;
import es.ucm.gdv.logic.Logic;

public class Main {
    public static void main(String[] args){
        Game game = new DesktopGame("Bombardero");
        ((DesktopGame) game).init();
        Logic logic = new Logic(game);
        logic.loadImages();

        while(true){
            logic.run();
        }
    }
}
