package es.ucm.gdv.android;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceView;

import java.io.InputStream;

import es.ucm.gdv.aninterface.Graphics;
import es.ucm.gdv.aninterface.Image;

public class AndroidGraphics implements Graphics {

    public AndroidGraphics(AssetManager assetManager, SurfaceView surface){
        _assetManager = assetManager;
        _surface = surface;
    }

    @Override
    public Image newImage(String name) {
        InputStream is = null;
        Bitmap sprite;
        try{
            is = _assetManager.open(name);
            sprite = BitmapFactory.decodeStream(is);

        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        finally{
            try{
                is.close();
            }catch (Exception e){System.out.println(e);}
        }

        return new AndroidImage(sprite);
    }

    @Override
    public void clear(int color) {
        _canvas.drawColor(color);
    }

    @Override
    public void drawImage(Image image, int posX, int posY, int widthP, int heightP, int Ix, int Iy, int Iheight, int Iwidth) {
        Bitmap sprite = ((AndroidImage)image).getImage();
        Rect src = new Rect(Ix,Iy,Ix+Iwidth,Iy+Iheight);
        Rect dst = new Rect(posX,posY,posX+widthP,posY+heightP);
        if(sprite != null){
            _canvas.drawBitmap(sprite,src,dst,null);
        }
    }

    @Override
    public int getWidth() {
        return _surface.getWidth();
    }

    @Override
    public int getHeight() {
        return _surface.getHeight();
    }

    @Override
    public void startFrame() {
        while (!_surface.getHolder().getSurface().isValid())
            ;
        _canvas = _surface.getHolder().lockCanvas();
    }

    @Override
    public void postFrame() {
        _surface.getHolder().unlockCanvasAndPost(_canvas);
    }

    Canvas _canvas;
    AssetManager _assetManager;
    SurfaceView _surface;
}
