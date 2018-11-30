package Asset;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Bomb extends Sprite {
    public int x = 0;
    public int y = 0;
    public int height=64;
    public int width=64;
    public int dx=0;
    public int dy=0;
    public BufferedImage image;
    public boolean clear = false;
    public float time = 0;
    public float max_time = 1.2f;
    public float loop = 0.2f;
    public float time_ani = loop;
    public int animation = 0;
    public int length = 0;
    public AssetManager assetManager;
    public int imageID = 1;

    @Override
    public boolean clear() {
        return this.clear;
    }

    @Override
    public void setclear() {
        this.clear = true;
    }

    public boolean set_flame(int x_,int y_){
        boolean res =true;
        for (int i=0;i<assetManager.sprites.size();i++){
            Sprite sprite = assetManager.sprites.get(i);
            if (sprite.getX()==x_ &sprite.getY()==y_){
                if (sprite instanceof Tiles){

                    Tiles tiles = (Tiles)sprite;
                    if (tiles.type==0 | tiles.type==2){
                        res= false;
                    }
                    if (tiles.type==1){
                        sprite.setclear();
                        assetManager.sprites.add(new Flame(x_,y_));
                        res =  false;
                    }
                }
            }
        }
        if (res)
            assetManager.sprites.add(new Flame(x_,y_));

        return res;
    }
    public void Booom(){
        boolean st = set_flame(x,y);

        for (int len=1;len<=length;len++){
            int x_ = x+len*64;
            int y_ = y;
            boolean flag = set_flame(x_,y_);
            if (!flag) break;

        }

        for (int len=1;len<=length;len++){
            int x_ = x-len*64;
            int y_ = y;
            boolean flag = set_flame(x_,y_);
            if (!flag) break;
        }

        for (int len=1;len<=length;len++){
            int x_ = x;
            int y_ = y+len*64;
            boolean flag = set_flame(x_,y_);
            if (!flag) break;
        }

        for (int len=1;len<=length;len++){
            int x_ = x;
            int y_ = y-len*64;
            boolean flag = set_flame(x_,y_);
            if (!flag) break;
        }

    }
    @Override
    public void Update(float dt) {
        time +=dt;
        if (time > max_time) {
            Booom();
            setclear();
            time =0;
            time_ani = loop;
        }
        if (time>time_ani){
            animation = (animation+1)%5;
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
        return dx+animation*width;

    }

    @Override
    public int getdy() {
        return dy;
    }

    @Override
    public int gettype() {
        return 0;
    }
}
