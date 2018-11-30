package Asset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Flame extends Sprite {
    public int x = 0;
    public int y = 0;
    public int height=64;
    public int width=64;
    public int dx=0;
    public int dy=0;

    public boolean clear = false;
    public float time = 0;
    public float max_time = 0.2f;
    public float loop = 0.05f;
    public float time_ani = loop;
    public int animation = 0;
    public int imageID = 5;
    public Flame(int x,int y){
        this.x =x;
        this.y =y;



    }
    @Override
    public void Update(float dt) {
        time +=dt;
        if (time > max_time) {
            setclear();
            time =0;
            time_ani = loop;
        }
        if (time>time_ani){
            animation = (animation+1)%3;
            time_ani += loop;
        }
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

        return dx+animation*64;

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
        return this.clear;
    }

    @Override
    public void setclear() {
        this.clear = true;
    }
}
