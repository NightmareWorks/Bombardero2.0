package es.ucm.gdv.aninterface;

public interface Graphics {
    // carga una imagen almacenada en el contenedor de recursos
    // de la aplicación a partir de su nombre.
    Image newImage(String name);

    // borra el contenido completo de la ventana,
    // rellenándolo con un color recibido como parámetro.
    void clear(int color);

    //recibe una imagen y la muestra en
    //la pantalla.
    //Recibe la imagen, la pos x e y en pantalla
    //El ancho en pantalla y el alto
    //La posicion x e y en pixeles del fragmento de imagen
    //El  ancho y el alto del fragmento de imagen
    void drawImage(Image image, int posX, int posY, int widthP, int heightP, int Ix, int Iy, int Iheight, int Iwidth);

    //De la ventana
    int getWidth();
    int getHeight();

    //Prepara para pintar
    void startFrame();
    //Dibuja el frame
    void postFrame();
}
