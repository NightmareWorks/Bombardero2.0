package es.ucm.gdv.logic;

import java.util.List;
import java.util.Random;

import es.ucm.gdv.aninterface.Game;
import es.ucm.gdv.aninterface.Image;
import es.ucm.gdv.aninterface.Input;

enum State{Intro, Difficulty, Speed, Building, Game, Score}

public class Logic {
    public Logic(Game game){
        _game = game;
        //Se crea sólo una vez y se adapta según los tableros que tengamos
        _screen = new Screen(_game.getGraphics());
        //Inicializamos las imágenes
        loadImages();

        setState(State.Intro);
    }

    //Pinta cada frame
    public void render(){
        _game.getGraphics().startFrame();
        _game.getGraphics().clear(0xFF000000);
        _screen.drawScreen(_board, _colorBoard, _images);
        _game.getGraphics().postFrame();
    }

    private void loadImages(){
        for(int i = 0; i < _images.length; i++) {
            String name = "ASCII_" + String.format("%02d", i) + ".png";
            _images[i] = _game.getGraphics().newImage(name);
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

    //Hace que todos los caracteres de la matriz sean un espacio en blanco
    private void clearBoard(){
        for(int i = 0; i < _board.length; ++i){
            for(int j = 0; j < _board[0].length; ++j){
                _board[i][j] = 32;
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

    //Es el tick del juego
    public void run(){
        switch (_currentState){
            case Intro:
                introAndEndPhaseTick();
                break;
            case Difficulty:
                difficultyPhaseTick();
                break;
            case Speed:
                speedPhaseTick();
                break;
            case Building:
                buildCity();
                break;
            case Game:
                gameTick();
                break;
            case Score:
                introAndEndPhaseTick();
                break;
            default:
                break;
        }
        render();
    }

    //Método auxiliar para cambiar de estado
    private void setState(State newState){
        _currentState = newState;
        switch (_currentState){
            case Score:
                setBoard(_endW,_endH,_endText,_endColors);
                setFinalHiScore();
                _screen.setScreen(_board[0].length,_board.length,false);
                break;
            case Difficulty:
                setBoard(_difficultyW,_difficultyH, _difficultyText, _difficultyColors);
                _screen.setScreen(_board[0].length,_board.length,false);
                break;
            case Speed:
                setBoard(_speedW,_speedH,_speedText,_speedColors);
                _screen.setScreen(_board[0].length,_board.length,false);
                break;
            case Intro:
                setBoard(_introW,_introH, _introText,_introColors);
                _screen.setScreen(_board[0].length,_board.length,false);
                break;
            default:
                break;
        }
    }

    //Este tick se ejecuta en la pantalla de inicio
    //Y en la de puntuación
    private void introAndEndPhaseTick(){
        _evts = _game.getInput().getTouchEvents();
        if(!_evts.isEmpty()){
            for (Input.TouchEvent t : _evts){
                if(t.get_action()) {
                    setState(State.Difficulty);
                    break;
                }
            }
        }
    }

    //Tick para la pantalla de dificultad
    private void difficultyPhaseTick(){
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
                                    _difficulty = 0;
                                    setState(State.Speed);
                                    break;
                                case 13:
                                    _difficulty = 1;
                                    setState(State.Speed);
                                    break;
                                case 16:
                                    _difficulty = 2;
                                    setState(State.Speed);
                                    break;
                                case 19:
                                    _difficulty = 3;
                                    setState(State.Speed);
                                    break;
                                case 22:
                                    _difficulty = 4;
                                    setState(State.Speed);
                                    break;
                                case 25:
                                    _difficulty = 5;
                                    setState(State.Speed);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
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
                                    setState(State.Building);
                                    break;
                                case 14:
                                    _speed = 1;
                                    setState(State.Building);
                                    break;
                                case 17:
                                    _speed = 2;
                                    setState(State.Building);
                                    break;
                                case 20:
                                    _speed = 3;
                                    setState(State.Building);
                                    break;
                                case 23:
                                    _speed = 4;
                                    setState(State.Building);
                                    break;
                                default:
                                    break;
                            }
                        }
                        else if(cell[0] == 4){
                            switch (cell[1]){
                                case 11:
                                    _speed = 5;
                                    setState(State.Building);
                                    break;
                                case 14:
                                    _speed = 6;
                                    setState(State.Building);
                                    break;
                                case 17:
                                    _speed = 7;
                                    setState(State.Building);
                                    break;
                                case 20:
                                    _speed = 8;
                                    setState(State.Building);
                                    break;
                                case 23:
                                    _speed = 9;
                                    setState(State.Building);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

    //Va construyendo la ciudad
    //Cuando termina el tablero ya está relleno y se puede empezar a jugar
    private void buildCity(){
        _board = new int[_playingH][_playingW];
        _screen.setScreen(_playingW,_playingH,true);
        clearBoard();
        setColorBoard(_board[0].length,_board.length,_playingInitialColors);

        int[] buildings = new int[11];
        for(int i = 0; i < buildings.length; ++i)
            //La altura de cada edificio es 5 - dif sumado a un valor aleatorio hasta el 7
            buildings[i] = rand.nextInt(8) + (5 - _difficulty);

        int baseX = 4; int baseY = 21; //El lugar donde se empieza a construir el primer edificio

        _lastFrameTime = System.nanoTime();
        int i = 0;
        int j = 0;
        int endedBuildings = 0;
        while(endedBuildings < buildings.length){
            long currentTime = System.nanoTime();
            if(currentTime - _lastFrameTime > 0.05e9){
                _lastFrameTime = currentTime;
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

                render();
            }
        }
        readyGame();
        render();
        setState(State.Game);

        //Así tiro a la basura cualquier click
        //durante la construcción
        _game.getInput().getTouchEvents();
    }

    //Escribe la línea de puntos debajo de la ciudad
    //Y coloca el avion en el 0,1
    private void readyGame(){
        //GUI
        for(int i = 0; i < _board[23].length; ++i)
            _board[23][i] = 95;
        String s = "PUNTOS";
        for(int i = 0; i < s.length();++i)
            _board[24][i] = s.charAt(i);
        s = "MAX";
        for(int i = 0; i < s.length();++i)
            _board[24][12+i] = s.charAt(i);

        //Posicion del avion, tiempo de update
        // y establecimiento del avion y las bombas
        _planeX = 1;
        _planeY = 1;

        _updateTime = (_speed + 0.1)*0.05e9;

        _collisioned = false;
        _bombIntensity = 0;

        //Establecemos las puntuaciones iniciales
        if(!_keepscore) {
            _score = 0;
        }
        setScore();
        _keepscore = false;
        setGameHiScore();
    }

    //Tick del game
    private void gameTick(){
        //Comprobamos pulsación si la bomba ya ha impactado y la plantamos
        //Esto lo hacemos siempre
        checkBombPress();

        //Si toca hacer tick
        long currentTime = System.nanoTime();
        if(currentTime - _lastFrameTime > _updateTime) {
            _lastFrameTime = currentTime;
            stepBomb();
            stepPlane();
        }
    }

    //Métodos auxiliares del update del juego
    private void stepBomb(){
        //Avanzamos la bomba
        if(_bombIntensity > 0){
            ++_bombY;
            //Si la bomba colisiona seguimos la estela de destrucción
            if(_board[_bombY][_bombX] == 244 || _board[_bombY][_bombX] == 143){
                //disminuyo la intensidad de la bomba y exploto el piso
                --_bombIntensity;
                _board[_bombY][_bombX] = 238;
                _colorBoard[_bombY][_bombX] = 2;

                //Aumenta puntuación
                _score += 5;
                setScore();
            }
            //Si no colisiona sigue cayendo
            else{
                //Si la bomba ya ha llegado al final del tablero desaparece
                if(_bombY == 22){
                    _bombIntensity = 0;
                }
                else{
                    _board[_bombY][_bombX] = 252;
                    _colorBoard[_bombY][_bombX] = 2;
                }
            }
            //Borro la explosion anterior
            if(_board[_bombY - 1][_bombX] == 252 || _board[_bombY-1][_bombX] == 238)
                _board[_bombY - 1][_bombX] = ' ';
            //Activo el bool para borrar la ultima explosion
            if(_bombIntensity == 0)
                _bombEnded = true;
        }

        //Este if se usa para borrar el último sprite de explosión
        if(_bombEnded) {
            _board[_bombY][_bombX] = ' ';
            _bombEnded = false;
        }
    }

    //Comprueba pulsación para lanzar la bomba
    private void checkBombPress(){
        if(_bombIntensity == 0 && !_collisioned) {
            _evts = _game.getInput().getTouchEvents();
            if (!_evts.isEmpty()) {
                for (Input.TouchEvent t : _evts) {
                    if (t.get_action()) {
                        //Si el aleatorio no ayuda, no hay quien se lo pase
                        _bombIntensity = rand.nextInt(5) + 2;
                        if (_planeX == 17){
                            _bombX = 2;
                            _bombY = _planeY + 1;
                        }
                        else {
                            _bombX = _planeX + 1;
                            _bombY = _planeY;
                        }
                        break;
                    }
                }
            }
        }
        //De esta forma volcamos la lista y no se guarda
        //el input para cuando esté disponible
        //(Evitamos que la bomba caiga sola si hemos pulsado antes)
        else
            _game.getInput().getTouchEvents();
    }

    //Avance del avion
    private void stepPlane(){
        //Avanza el avion
        //Si el avion llega al final de la linea
        if (_planeX == 17) {
            if (_planeY == 21) {
                //Termina la partida
                //Construye una ciudad nueva com más velocidad y más dificultad
                _keepscore = true;
                if(_speed > 0)
                    _speed--;
                if(_difficulty > 0)
                    _difficulty--;
                setState(State.Building);
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
                _collisioned = true;
                _board[_planeY][_planeX] = ' ';
                _board[_planeY][_planeX - 1] = ' ';
                _planeX++;

                //Llamamos a un metodo con la animación de muerte
                deathAnimation();

                //Pasamos a la pantalla de puntuación
                if(_score > _hiScore) {
                    _hiScore = _score;
                    _record = true;
                }
                else
                    _record = false;
                setState(State.Score);
            }
            //Si no, seguimos
            else{
                _board[_planeY][_planeX - 1] = ' ';
                ++_planeX;
            }

        }

        if(!_collisioned) {
            //Pintamos el avion
            _board[_planeY][_planeX] = 242;
            _board[_planeY][_planeX - 1] = 241;
            _colorBoard[_planeY][_planeX] = 1;
            _colorBoard[_planeY][_planeX - 1] = 1;
        }
    }

    //Método con la animación de muerte
    private void deathAnimation(){
        _lastFrameTime = System.nanoTime();
        _colorBoard[_planeY][_planeX] = 1;
        int frames = 24;
        int sprite = 0;
        while(frames > 0) {
            long currentTime = System.nanoTime();
            if (currentTime - _lastFrameTime > 0.1e9) {
                _lastFrameTime = currentTime;
                if (sprite % 3 == 0)
                    _board[_planeY][_planeX] = 238;
                else if (sprite % 3 == 1)
                    _board[_planeY][_planeX] = 253;
                else
                    _board[_planeY][_planeX] = 188;
                ++sprite;
                --frames;
                render();
            }
        }
    }

    //Posicion del avion y la bomba, y numero de pisos que puede destruir
    private int _planeX,_planeY;
    private int _bombX, _bombY, _bombIntensity;
    private boolean _bombEnded; //Este es para borrar el último sprite de explosión de la bomba
    private boolean _collisioned = false; //Si el avion se la ha pegado, no puede lanzar bombas

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
    143 Pisos de los edificios
    241 Lado izquierdo del avión
    242 Lado derecho del avión
    252 Bomba
    188 Colisión 1
    238 Colisión 2
    253 Colisión 3
    95 Subrayado (separador barra de puntuación)*/

    //El screen se encarga del renderizado
    //Y de darte la casilla que has pulsado
    private Screen _screen;
    private Random rand = new Random();
    private List<Input.TouchEvent> _evts;
    private int _speed, _difficulty;
    private double _updateTime;

    State _currentState;
    private long _lastFrameTime;

    ////////////////Puntuaciones y relacionado////////////////////
    private int _score;
    private int _hiScore = 0;
    private boolean _keepscore = false;

    private void setScore(){
        int c = _score/100;
        int d = (_score%100)/10;
        int u = (_score%100)%10;

        if(c > 0){
            _board[24][7] = String.valueOf(c).charAt(0);
            _board[24][8] = String.valueOf(d).charAt(0);
            _board[24][9] = String.valueOf(u).charAt(0);
        }
        else if(d > 0){
            _board[24][7] = String.valueOf(d).charAt(0);
            _board[24][8] = String.valueOf(u).charAt(0);
            _board[24][9] = ' ';
        }
        else{
            _board[24][7] = String.valueOf(u).charAt(0);
            _board[24][8] = ' ';
            _board[24][9] = ' ';
        }
    }

    private void setGameHiScore(){
        int c = _hiScore/100;
        int d = (_hiScore%100)/10;
        int u = (_hiScore%100)%10;

        if(c > 0){
            _board[24][16] = String.valueOf(c).charAt(0);
            _board[24][17] = String.valueOf(d).charAt(0);
            _board[24][18] = String.valueOf(u).charAt(0);
        }
        else if(d > 0){
            _board[24][16] = String.valueOf(d).charAt(0);
            _board[24][17] = String.valueOf(u).charAt(0);
            _board[24][18] = ' ';
        }
        else{
            _board[24][16] = String.valueOf(u).charAt(0);
            _board[24][17] = ' ';
            _board[24][18] = ' ';
        }
    }

    private void setFinalHiScore(){
        int c = _score/100;
        int d = (_score%100)/10;
        int u = (_score%100)%10;

        if(c > 0){
            _board[0][14] = String.valueOf(c).charAt(0);
            _board[0][15] = String.valueOf(d).charAt(0);
            _board[0][16] = String.valueOf(u).charAt(0);
        }
        else if(d > 0){
            _board[0][14] = String.valueOf(d).charAt(0);
            _board[0][15] = String.valueOf(u).charAt(0);
            _board[0][16] = ' ';
        }
        else{
            _board[0][14] = String.valueOf(u).charAt(0);
            _board[0][15] = ' ';
            _board[0][16] = ' ';
        }

        //Si hay record escribimos que lo hay
        if(_record)
            writeRecordLine();
    }

    ///////////////////Pantallas del juego//////////////////////

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
    private int _difficultyW = 40;
    private int _difficultyH = 3;
    private String _difficultyText = "Elija nivel: 0 (AS) a 5 (principiante)  " +
            "                                        " +
            "          0  1  2  3  4  5              ";
    private String _difficultyColors = "2222222222222222222222222222222222222222" +
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

    //Pantalla de puntuación
    private int _endW = 20;
    private int _endH = 10;
    private String _endText = "Ha conseguido 000   " +
            "puntos              " +
            "                    " +
            "                    " +
            "                    " +
            "                    " +
            "                    " +
            "                    " +
            "Pulse para volver a " +
            "empezar             ";
    private String _endColors = "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222" +
            "22222222222222222222";
    private boolean _record;

    private void writeRecordLine(){
        String p = "BATIO EL RECORD!!   ";
        for(int i = 0; i < _board[0].length; ++i){
            _board[5][i] = p.charAt(i);
        }
    }
}