package es.ucm.gdv.desktop;

import java.awt.Color;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Image;

public class DesktopGraphics extends JFrame implements Graphics {

    public DesktopGraphics(String title){
        super(title);
    }

    //Inicializa el tamaño de la ventana
    //Y el doble buffer
    public void init(){
        //setSize(width,height);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        //Creamos el doble buffer
        int tries = 100;
        while (tries-- > 0){
            try{
                createBufferStrategy(2);
                break;
            }
            catch(Exception ioe){}
        }
        _strategy = getBufferStrategy();
    }

    @Override
    public Image newImage(String name) {
        //Tengo que sacar el awt.image
        java.awt.Image im = null;
        try{
            im = javax.imageio.ImageIO.read(new java.io.File("Resources/" + name));
        }
        catch(Exception e){
            System.out.println(e);
        }
        return new DesktopImage(im);
    }

    @Override
    public void clear(int color) {
        Color c = new Color(color);
        _g.setColor(c);
        _g.fillRect(0,0,getWidth(),getHeight());
    }

    @Override
    public void drawImage(Image image, int posX, int posY, int widthP, int heightP, int Ix, int Iy, int Iheight, int Iwidth) {
        _g.drawImage(((DesktopImage)image).getImage(),posX,posY,posX+widthP,posY+heightP,Ix,Iy,Ix+Iwidth,Iy+Iheight,null);
    }

    @Override
    public void startFrame() {
        _g = _strategy.getDrawGraphics();
    }

    @Override
    public void postFrame() {
        if(!_strategy.contentsLost() && !_strategy.contentsRestored()) {
            _g.dispose();
            _strategy.show();
        }
    }

    BufferStrategy _strategy;
    java.awt.Graphics _g;
}
