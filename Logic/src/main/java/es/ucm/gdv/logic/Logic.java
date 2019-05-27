package es.ucm.gdv.logic;

import java.util.Random;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Image;

public class Logic {
    public Logic(Game game){
        _game = game;
        _screen = new Screen(_game.getGraphics());
        //Inicializamos las imágenes
        loadImages();

        //Esto es un experimento e iría en el run
        _colorBoard = new int[10][10];
        _board = new int [10][10];
        seteoChungo();
    }

    //Experimental
    private void seteoChungo(){
        for(int i = 0; i < _board.length;i++){
            for(int j = 0; j < _board[0].length;j++){
                _board[i][j] = rand.nextInt(256);
                _colorBoard[i][j] = rand.nextInt(15);
            }
        }
    }

    public void run(){
        //De momento no hace ni el huevo
        int i = 1;
    }

    //Pinta cada frame
    public void render(){
        _game.getGraphics().startFrame();
        _game.getGraphics().clear(0xFF00FF00);
        _screen.drawScreen(_board,_colorBoard,_images);
        _game.getGraphics().postFrame();
    }

    private void loadImages(){
        for(int i = 0; i < _images.length; i++) {
            String name = "ASCII_" + String.format("%02d", i) + ".png";
            _images[i] = _game.getGraphics().newImage(name);
        }
    }

    //Imagenes con los sprites del juego que se cargarán al inicio
    Image[] _images = new Image[16];
    //El juego le será pasado en el main cuando se construya la lógica
    Game _game;


    //Este es el tablero, pero tiene los colores y no los sprites
    private int[][] _colorBoard;

    //Este es el tablero lógico sobre el que se hacen las transformaciones
    //Lleva un int por casilla con el ascii correspondiente al símbolo a pintar
    private int[][] _board;
    //Símbolos importantes:
    //Además de los de cada letra en ascii
    /*244 Tejado de los edificios
    143 “Pisos” de los edificios
    241 Lado izquierdo del avión
    242 Lado derecho del avión
    252 Bomba
    188 Colisión 1
    238 Colisión 2
    253 Colisión 3
    95 Subrayado (separador barra de puntuación)*/

    private Screen _screen;
    //Experimental
    Random rand = new Random();
}
