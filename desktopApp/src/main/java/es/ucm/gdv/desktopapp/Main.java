package es.ucm.gdv.desktopapp;

import es.ucm.gdv.desktop.DesktopGame;
import es.ucm.gdv.logic.Logic;

public class Main {
    public static void main(String[] args){
        DesktopGame game = new DesktopGame("Bombardero");
        game.init();
        Logic logic = new Logic(game);
        logic.loadImages();

        while(true){
            logic.run();
            logic.render();
        }
    }
}
