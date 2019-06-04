package es.ucm.gdv.desktop;

import es.ucm.gdv.aninterface.Image;

public class DesktopImage implements Image {

    public DesktopImage(java.awt.Image img){
        _img = img;
    }

    @Override
    public int getHeight() {
        return _img.getHeight(null);
    }

    @Override
    public int getWidth() {
        return _img.getWidth(null);
    }

    public java.awt.Image getImage(){return _img;}

    private java.awt.Image _img;
}
