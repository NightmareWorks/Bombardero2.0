package es.ucm.gdv.android;

import android.graphics.Bitmap;

import es.ucm.gdv.aninterface.Image;

public class AndroidImage implements Image {
    public AndroidImage(Bitmap image){_image = image;}

    @Override
    public int getHeight() {
        return _image.getHeight();
    }

    @Override
    public int getWidth() {
        return _image.getWidth();
    }

    public Bitmap getImage(){
        return _image;
    }

    private Bitmap _image;
}
