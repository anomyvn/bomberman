package Asset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

enum ItemType{
    Speed,Flame,Bomb;
}

public class Items extends Sprite {
    public int x = 0;
    public int y = 0;
    public int height=64;
    public int width=64;
    public int dx=0;
    public int dy=0;
    public int type ;
    public boolean clear = false;
    public int imageID = 6;
    public Items(int type){
        this.type = type;
        if (type==0){
            dx = 2*width;
            dy = 0;
        }

        if (type ==1){
            dx = 1*width;
            dy = 0;
        }

        if (type ==2){
            dx = 0;
            dy = 0;
        }
    }

    @Override
    public void setclear() {
        this.clear=true;
    }
    @Override
    public boolean clear() {
        return this.clear;
    }

    @Override
    public void Update(float dt) {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getImage() {
        return imageID;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getdx() {
        return dx;

    }

    @Override
    public int getdy() {
        return dy;
    }

    @Override
    public int gettype() {
        return type;
    }
}
