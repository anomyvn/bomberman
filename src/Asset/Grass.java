package Asset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Grass extends Sprite {
    public int x = 0;
    public int y = 0;
    public int height=64;
    public int width=64;
    public int dx=0;
    public int dy=0;
    public int imageID = 7;

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
        return 0;
    }

    @Override
    public boolean clear() {
        return false;
    }

    @Override
    public void setclear() {

    }
}

