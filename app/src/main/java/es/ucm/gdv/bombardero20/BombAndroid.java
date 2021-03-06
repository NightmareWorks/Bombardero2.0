package es.ucm.gdv.bombardero20;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;

import es.ucm.gdv.android.AndroidGame;
import es.ucm.gdv.logic.Logic;

public class BombAndroid extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _view = new MyView(this);
        _game = new AndroidGame();
        _game.init(this,_view);
        _view.setOnTouchListener((View.OnTouchListener)_game.getInput());
        _logic = new Logic(_game);
        _logic.loadImages();

        setContentView(_view);

        _gameThread = new gameThread();
    }

    @Override
    protected void onResume(){
        super.onResume();
        _gameThread.resume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        _gameThread.pause();
    }

    //Clase con mi SurfaceView
    class MyView extends SurfaceView{
        public MyView(Context context) {
            super(context);
        }
    }

    //CLASE GAME THREAD -> lleva el run del juego (la logica)
    public class gameThread implements Runnable{
        public gameThread(){}

        public void resume(){
            if(!_running){
                _running = true;
                // Creamos la hebra que lleva el run()
                _runningThread = new Thread(this);
                _runningThread.start(); //En paralelo se ejecutará el run.

            }//if !running
        } //resume

        public void pause(){
            _running = false;
            while (true) {
                try {
                    _runningThread.join();
                    break;
                } catch (InterruptedException ie) {

                }//Catch
            }//While

        }//Pause

        @Override
        public void run(){
            while(_running) {
                _logic.run();
                _logic.render();
            }
        }

        volatile boolean _running = false;
        Thread _runningThread;

    }//Fin clase GameThread


    MyView _view;
    AndroidGame _game;
    gameThread _gameThread;
    Logic _logic;
}

