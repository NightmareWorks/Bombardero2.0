package es.ucm.gdv.logic;

import java.util.List;
import java.util.Random;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Image;
import es.ucm.gdv.aninterface.Input;

enum State{Intro,Dificulty,Speed,Building,Game,Score}

public class Logic {
    public Logic(Game game){
        _game = game;
        //Se crea sólo una vez y se adapta según los tableros que tengamos
        _screen = new Screen(_game.getGraphics());
        //Inicializamos las imágenes
        loadImages();
        _currentState = State.Intro;
        //setBoard(_speedW,_speedH,_speedText,_speedColors);
        //setBoard(_introW,_introH, _introText,_introColors);
        //setBoard(_dificultyW,_dificultyH,_dificultyText,_dificultyColors);
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

    //Este método transforma un string bruto en cada
    //caracter para después generar el tablero
    private void setBoard(int w, int h, String grid, String colorsGrid){
        _board = new int[h][w];
        _colorBoard = new int[h][w];
        for(int i = 0; i < h; ++i){
            for(int j = 0; j < w; ++j){
                _board[i][j] = grid.charAt(i * w + j);
                _colorBoard[i][j] = Integer.parseInt(String.valueOf(colorsGrid.charAt(i * w + j)));
            }
        }
    }

    //Más específico que el anterior, sólo se llama en la pantalla de juego
    private void setColorBoard(int w, int h, String colorsGrid){
        _colorBoard = new int[h][w];
        for(int i = 0; i < h; ++i){
            for(int j = 0; j < w; ++j){
                _colorBoard[i][j] = Integer.parseInt(String.valueOf(colorsGrid.charAt(i * w + j)));
            }
        }
    }

    public void run(){

        //AQUI VA LA CONDICION DE PARADA DEL JUEGO
        //while (true){
            switch (_currentState){
                case Intro:
                    setBoard(_introW,_introH, _introText,_introColors);
                    while(_currentState == State.Intro)
                        introPhaseTick();
                    break;
                case Dificulty:
                    setBoard(_dificultyW,_dificultyH,_dificultyText,_dificultyColors);
                    while(_currentState == State.Dificulty)
                        dificultyPhaseTick();
                    break;
                case Speed:
                    setBoard(_speedW,_speedH,_speedText,_speedColors);
                    while(_currentState == State.Speed)
                        speedPhaseTick();
                    break;
                case Building:
                    while(_currentState == State.Building)
                        buildCity();
                    break;
                case Game:
                    while(_currentState == State.Game)
                        gameTick();
                    break;
                case Score:
                    break;
                default:
                    break;
            }
        //}
    }

    //Este tick se ejecuta en la pantalla de inicio
    private void introPhaseTick(){
        _evts = _game.getInput().getTouchEvents();
        if(!_evts.isEmpty()){
            for (Input.TouchEvent t : _evts){
                if(t.get_action()) {
                    _currentState = State.Dificulty;
                    break;
                }
            }
        }
        render();
    }

    //Tick para la pantalla de dificultad
    private void dificultyPhaseTick(){
        _evts = _game.getInput().getTouchEvents();
        if(!_evts.isEmpty()){
            int[] cell;
            for (Input.TouchEvent t : _evts){
                if(t.get_action()) {
                    //Comprobamos en qué casilla se ha pulsado
                    cell = _screen.cellTouched(t.get_x(),t.get_y());
                    if(cell != null){
                        if(cell[0] == 2){
                            switch (cell[1]){
                                case 10:
                                    _dificulty = 0;
                                    _currentState = State.Speed;
                                    break;
                                case 13:
                                    _dificulty = 1;
                                    _currentState = State.Speed;
                                    break;
                                case 16:
                                    _dificulty = 2;
                                    _currentState = State.Speed;
                                    break;
                                case 19:
                                    _dificulty = 3;
                                    _currentState = State.Speed;
                                    break;
                                case 22:
                                    _dificulty = 4;
                                    _currentState = State.Speed;
                                    break;
                                case 25:
                                    _dificulty = 5;
                                    _currentState = State.Speed;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        render();
    }

    //Tick de la pantalla de velocidad
    private void speedPhaseTick(){
        _evts = _game.getInput().getTouchEvents();
        if(!_evts.isEmpty()){
            int[] cell;
            for (Input.TouchEvent t : _evts){
                if(t.get_action()) {
                    //Comprobamos en qué casilla se ha pulsado
                    cell = _screen.cellTouched(t.get_x(),t.get_y());
                    if(cell != null){
                        if(cell[0] == 2){
                            switch (cell[1]){
                                case 11:
                                    _speed = 0;
                                    _currentState = State.Building;
                                    break;
                                case 14:
                                    _speed = 1;
                                    _currentState = State.Building;
                                    break;
                                case 17:
                                    _speed = 2;
                                    _currentState = State.Building;
                                    break;
                                case 20:
                                    _speed = 3;
                                    _currentState = State.Building;
                                    break;
                                case 23:
                                    _speed = 4;
                                    _currentState = State.Building;
                                    break;
                                default:
                                    break;
                            }
                        }
                        else if(cell[0] == 4){
                            switch (cell[1]){
                                case 11:
                                    _speed = 0;
                                    _currentState = State.Building;
                                    break;
                                case 14:
                                    _speed = 1;
                                    _currentState = State.Building;
                                    break;
                                case 17:
                                    _speed = 2;
                                    _currentState = State.Building;
                                    break;
                                case 20:
                                    _speed = 3;
                                    _currentState = State.Building;
                                    break;
                                case 23:
                                    _speed = 4;
                                    _currentState = State.Building;
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        render();
    }

    //Tick del game
    private void gameTick(){
        //Si toca hacer tick
        long currentTime = System.nanoTime();
        if(currentTime - _lastFrameTime > _updateTime) {
            _lastFrameTime = currentTime;

            //Comprobamos pulsación



            //Avanza el avion

            //Si el avion llega al final de la linea
            if (_planeX == 17) {
                if (_planeY == 21) {
                    //Termina la partida
                    _planeY = 1;
                } else {
                    _board[_planeY][_planeX] = ' ';
                    _board[_planeY][_planeX - 1] = ' ';
                    ++_planeY;
                    _planeX = 2;
                }
            }
            //Si no
            else {
                //Comprobamos si se la ha pegado
                if (_board[_planeY][_planeX + 1] == 244 || _board[_planeY][_planeX + 1] == 143) {
                    //explosion
                    //Habría que crear como un estado en el que durante 3 ticks el avión explote
                    int i = 0;
                } else{
                    _board[_planeY][_planeX - 1] = ' ';
                    ++_planeX;
                }

            }

            //Pintamos el avion
            _board[_planeY][_planeX] = 242;
            _board[_planeY][_planeX - 1] = 241;
            _colorBoard[_planeY][_planeX] = 1;
            _colorBoard[_planeY][_planeX - 1] = 1;
        }
        render();
    }

    int _planeX,_planeY;

    //Pinta cada frame
    public void render(){
        if(_canDraw) {
            _game.getGraphics().startFrame();
            _game.getGraphics().clear(0xFF000000);
            _screen.drawScreen(_board, _colorBoard, _images);
            _game.getGraphics().postFrame();
        }
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

    //Hace que todos los caracteres de la matriz sean un espacio en blanco
    private void clearBoard(){
        for(int i = 0; i < _board.length; ++i){
            for(int j = 0; j < _board[0].length; ++j){
                _board[i][j] = 32;
            }
        }
    }

    //Va construyendo la ciudad
    //Cuando termina el tablero ya está relleno y se puede empezar a jugar
    private void buildCity(){
        _board = new int[_playingH][_playingW];
        clearBoard();
        setColorBoard(_board[0].length,_board.length,_playingInitialColors);

        int[] buildings = new int[11];
        for(int i = 0; i < buildings.length; ++i)
            //La altura de cada edificio es 5 - dif sumado a un valor aleatorio hasta el 7
            buildings[i] = rand.nextInt(8) + (5 - _dificulty);

        int baseX = 4; int baseY = 21; //El lugar donde se empieza a construir el primer edificio

        _lastFrameTime = System.nanoTime();
        int i = 0;
        int j = 0;
        int endedBuildings = 0;
        while(endedBuildings < buildings.length){
            long currentTime = System.nanoTime();
            if(currentTime - _lastFrameTime > 0.1e9){
                //Primero bloqueo el render
                _canDraw = false;

                //Si el edificio tiene más de un piso
                if(buildings[j] > 1){
                    _board[baseY - i][baseX + j] = 143;
                    --buildings[j];
                    ++i;
                }
                //Si al edificio le falta un piso
                else if(buildings[j] == 1){
                    _board[baseY - i][baseX + j] = 244;
                    --buildings[j];
                    ++j;
                    ++endedBuildings;
                    i = 0;
                }
                //Si el edificio no tiene pisos
                else if(buildings[j] == 0){
                    ++j;
                    ++endedBuildings;
                    i = 0;
                }

                //Desbloqueo el render
                _canDraw = true;
                render();
            }
        }
        readyGame();
        render();
        _currentState = State.Game;
    }

    //Escribe la línea de puntos debajo de la ciudad
    //Y coloca el avion en el 0,1
    private void readyGame(){
        for(int i = 0; i < _board[23].length; ++i)
            _board[23][i] = 95;
        String s = "PUNTOS";
        for(int i = 0; i < s.length();++i)
            _board[24][i] = s.charAt(i);
        s = "MAX";
        for(int i = 0; i < s.length();++i)
            _board[24][12+i] = s.charAt(i);
        _board[24][7] = '0';
        _board[24][16] = '0';

        _planeX = 1;
        _planeY = 1;

        _updateTime = (_speed + 1)*0.1e9;
    }

    //El screen se encarga del renderizado
    //Y de darte la casilla que has pulsado
    private Screen _screen;
    private Random rand = new Random();
    private List<Input.TouchEvent> _evts;
    private int _speed, _dificulty;
    private double _updateTime;

    //Vamos a necesitar algún tipo de mutex
    //para que la hebra de pintado no pinte cuando la lógica se está ejecutando
    private volatile boolean _canDraw = true;
    State _currentState;
    private long _lastFrameTime;




    /////////////Pantallas del juego//////////////////////

    //Pantalla de introducción
    private int _introW = 40;
    private int _introH = 24;
    private String _introText = "Usted esta pilotando un avion sobre una " +
            "ciudad desierta y tiene que pasar sobre " +
            "los edificios para aterrizar y repos-   " +
            "tar. Su avion se mueve de izquierda a   " +
            "derecha.                                " +
            "                                        " +
            "Al llegar a la derecha, el avion vuelve " +
            "a salir por la izquierda, pero MAS BAJO." +
            "Dispone de un numero limitado de bombas " +
            "y puede hacerlas caer sobre los edifi-  " +
            "cios pulsando sobre la pantalla.        " +
            "                                        " +
            "Cada vez que aterriza, sube la altura   " +
            "de los edificios y la velocidad.        " +
            "                                        " +
            "UNA VEZ DISPARADA UNA BOMBA, YA NO PUEDE" +
            "DISPARAR OTRA MIENTRAS NO HAYA EXPLOSIO-" +
            "NADO LA PRIMERA!!!!                     " +
            "                                        " +
            "                                        " +
            "Pulse para empezar                      " +
            "                                        " +
            "                                        " +
            "                                        ";
    private String _introColors = "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "2222222222222222222222222222222222222222" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444" +
            "4444444444444444444444444444444444444444";

    //Pantalla de selección de dificultad
    private int _dificultyW = 40;
    private int _dificultyH = 3;
    private String _dificultyText = "Elija nivel: 0 (AS) a 5 (principiante)  " +
            "                                        " +
            "          0  1  2  3  4  5              ";
    private String _dificultyColors = "2222222222222222222222222222222222222222" +
            "2222222222222222222222222222222222222222" +
            "9999999999999999999999999999999999999999";

    //Pantalla de selección de velocidad
    private int _speedW = 40;
    private int _speedH = 5;
    private String _speedText = "   Elija velocidad: 0 (MAX) a 9 (MIN)   " +
            "                                        " +
            "           0  1  2  3  4                " +
            "                                        " +
            "           5  6  7  8  9                ";
    private String _speedColors = "2222222222222222222222222222222222222222" +
            "2222222222222222222222222222222222222222" +
            "9999999999999999999999999999999999999999" +
            "9999999999999999999999999999999999999999" +
            "9999999999999999999999999999999999999999";

    //Pantalla de la partida
    private int _playingW = 20;
    private int _playingH = 25;
    private String _playingInitialColors = "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "00001234563789200000" +
            "99999999999999999999" +
            "99999999999999999999" +
            "22222299999922299999";

    //Pantalla de victoria
    // . . .
}
