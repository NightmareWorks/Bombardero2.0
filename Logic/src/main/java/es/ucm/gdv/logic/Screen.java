package es.ucm.gdv.logic;

import java.util.Map;

import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Image;

public class Screen {
    //Se construye con el graphics y con el número de celdas en horizontal y en vertical
    public Screen(Graphics graphics){
        _graphics = graphics;
    }

    //Segun el tamaño de pantalla del que dispongamos, damos un tamaño u otro a las celdas
    // Y colocamos la esquina superior izquierda del tablero
    private void setCellDimensions(){
        int totalW = _graphics.getWidth();
        int totalH = _graphics.getHeight();

        _cellH = totalH/_Ycells;
        _cellW = _cellH*2;
        if(_cellW * _Xcells > totalW){
            _cellW = totalW/_Xcells;
            _cellH = _cellW/2;
        }

        _oriW = (totalW - _cellW*_Xcells)/2;
        _oriH = (totalH - _cellH*_Ycells)/2;
    }

    //Este método adecua las celdas segun el numero de ellas
    public void setScreen(int xCells, int yCells){
        _Xcells = xCells;
        _Ycells = yCells;

        setCellDimensions();
    }

    //Este método pinta el tablero entero sin necesidad de tener sprites
    //Recibe el propio tablero y un vector con las imágenes disponibles
    public void drawScreen(int[][] board, int[][] colorBoard, Image[] pngs){
        setScreen(board[0].length,board.length);
        for(int i = 0; i < board.length;++i){
            for(int j = 0; j < board[0].length;++j){
                int imX = (board[i][j] % SPRITE_S) * SPRITE_S;
                int imY = (board[i][j] / SPRITE_S) * SPRITE_S;
                _graphics.drawImage(pngs[colorBoard[i][j]],
                        _oriW + j * _cellW,_oriH + i * _cellH,_cellW,_cellH, //Lugar en el screen
                        imX, imY, SPRITE_S, SPRITE_S); //Lugar en el png
            }
        }
    }

    //Este metodo recibe unas coordenadas en pixeles
    //Y devuelve la casilla que ha sido tocada
    //Como no puedo devolver dos valores, devuelvo un vector
    //Si el toque queda fuera del screen, devuelvo null
    public int[] cellTouched(int sX, int sY){
        int[] cell = new int[2];
        if(sY < _oriH || sY > _oriH + (_Ycells*_cellH))
            return null;
        else if (sX < _oriW || sX > _oriW + (_Xcells*_cellW))
            return null;
        else{
            cell[0] = (sY - _oriH)/_cellH;
            cell[1] = (sX - _oriW)/_cellW;
            return cell;
        }
    }

    //celdas de ancho y de largo del tablero
    private int _Xcells, _Ycells;
    //Píxeles de ancho y de largo de cada celda
    private int _cellW, _cellH;
    //Píxel origen en x e y del screen
    //(No tiene por qué empezar en la esquina de la pantalla)
    private int _oriH, _oriW;

    //Tamaño de cada sprite en las imágenes originales
    private int SPRITE_S = 16;

    private Graphics _graphics;

}
