package es.ucm.gdv.android;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.SurfaceView;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Input;

public class AndroidGame implements Game {

    public AndroidGame(){ }

    //Carga las imágenes
    public void init(AssetManager assets, SurfaceView surface){
        _graphics = new AndroidGraphics(assets,surface);
        _input = new AndroidInput();
        _images[0] = getGraphics().newImage("logo.png");
    }

    @Override
    public Graphics getGraphics() {
        return _graphics;
    }

    @Override
    public Input getInput() {
        return _input;
    }

    @Override
    public void run() {
        //De momento no hace ni el huevo
    }

    @Override
    public void render() {
        getGraphics().startFrame();
        getGraphics().clear(0xFF00FF00);
        getGraphics().drawImage(_images[0],100,100,100,100,0,0,500,500);
        getGraphics().postFrame();
    }

    AndroidGraphics _graphics;
    AndroidInput _input;
}
