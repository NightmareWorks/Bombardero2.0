package es.ucm.gdv.desktopapp;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.desktop.DesktopGame;
import es.ucm.gdv.logic.Logic;

public class Main {
    public static void main(String[] args){
        Game game = new DesktopGame("Bombardero");
        ((DesktopGame) game).init(1600,900);
        Logic logic = new Logic(game);

        while(true){
            logic.run();
        }
    }
}
