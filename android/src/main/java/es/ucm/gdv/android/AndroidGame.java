package es.ucm.gdv.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Input;

public class AndroidGame implements Game {

    public AndroidGame(){

    }

    //Carga las imágenes
    public void init(Context context, SurfaceView surface){
        _graphics = new AndroidGraphics(context,surface);
        _input = new AndroidInput();
    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }


    AndroidGraphics _graphics;
    AndroidInput _input;
}
