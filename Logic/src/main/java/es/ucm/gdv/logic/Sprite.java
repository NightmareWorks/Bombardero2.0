package es.ucm.gdv.logic;

import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Image;

//DE MOMENTO NO SIRVE Y PUEDE SER PRESCINDIBLE
//Esta clase contiene el fragmento útil de un png
//Que vamos a pintar en pantalla
public class Sprite {
    Sprite(Image image, int x, int y, int width, int height, Graphics graphics, int posX, int posY, int cellH, int cellW){
        _x = x; _y = y;
        _width = width; _height = height;
        _image = image;
        _graphics = graphics;
        _posX = posX; _posY = posY; _cellW = cellW; _cellH = cellH;
    }

    //Constructora por copia
    Sprite(Sprite s){
        _x = s._x; _y = s._y;
        _width = s._width; _height = s._height;
        _image = s._image;
        _graphics = s._graphics;
        _posX = s._posX; _posY = s._posY; _cellW = s._cellW; _cellH = s._cellH;
    }

    void render(){
        _graphics.drawImage(_image,_posX,_posY,_cellW,_cellH,_x,_y,_height,_width);
    }


    //Cambia el origen del sprite en pantalla
    void setPos(int x, int y){
        _posX = x;
        _posY = y;
    }

    //Tamaño en la img (en el png) en pixeles
    int _x, _y, _width, _height;

    //Tamaño en la pantalla (en pixeles)
    int _posX, _posY, _cellW, _cellH;

    //Png completo
    Image _image;
    //Referencia a la pantalla
    Graphics _graphics;
}
