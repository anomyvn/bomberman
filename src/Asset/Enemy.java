package Asset;

import state.PlayState;

import java.awt.image.BufferedImage;

public class Enemy extends Sprite {
    public float x ;
    public float y ;
    public int height=64;
    public int width=64;
    public float speed = 0;
    public int animation = 0;
    public int type = 0;
    public float time_change = 0.1f;
    public float time = 0.0f;
    public int dx = 0 ;
    public int dy=0;
    public float[] speed_ ={128*0.5f,128*1.5f };
    public int last_x ;
    public int last_y ;
    public int imageID;
    public boolean ismoving = false;
    public int current_action = 0;
    public int key =0;
    public boolean clear = false;
    public AssetManager assetManager;
    public boolean death = false;
    public Enemy(int type){
        this.type = type;
        if (type ==0){
            speed = 128;
            imageID=3;
        }
        else{
            speed = 128*1.5f;
            imageID=4;
        }

    }


    public boolean check_next_move(int x,int y){
        boolean flag = true;
        for (int i=0;i<assetManager.sprites.size();i++){

            if (assetManager.sprites.get(i).getX()==x & assetManager.sprites.get(i).getY()==y){
                Sprite sprite = assetManager.sprites.get(i);
                if (sprite instanceof Tiles){
                    flag= false;
                }
                int xx =0;
                if (sprite instanceof Bomb) flag = false;
            }
        }
        if (flag){
            for (int i=0;i<assetManager.sprites.size();i++){
                Sprite sprite = assetManager.sprites.get(i);
                if (sprite instanceof Flame){
                    if (sprite.getY()==y&sprite.getX()==x)
                        death = true;
                }
            }
        }
        return flag;
    }
    public void check_death(){
        if (death) return;
        for (int i=0;i<assetManager.sprites.size()-1;i++){
            Sprite sprite =assetManager.sprites.get(i);
            if (sprite instanceof Flame){
                if (check_collision((int)x,(int)y,sprite.getX(),sprite.getY()))
                {
                    death = true;
                    time = 0;
                    time_change = 0.2f;
                }

            }
        }

    }

    public void chase(){
        if (type==0) return;
        for (int i=0;i<assetManager.sprites.size();i++){
            Sprite sprite = assetManager.sprites.get(i);
            if (sprite instanceof Player){
                Player player = (Player)sprite;
                if (player.last_x==last_x){
                    if (player.last_y<=last_y){
                        for (int j=0;j<assetManager.sprites.size();j++){
                            Sprite sprite1 = assetManager.sprites.get(j);
                            if (sprite1 instanceof Tiles | sprite1 instanceof Bomb){

                                if (sprite1.getX()==last_x & sprite1.getY()<=last_y & sprite1.getY()>=player.getY()) return;
                            }
                        }
                        key = 1;
                    }

                    else {
                        for (int j=0;j<assetManager.sprites.size();j++){
                            Sprite sprite1 = assetManager.sprites.get(j);
                            if (sprite1 instanceof Tiles | sprite1 instanceof Bomb){

                                if (sprite1.getX()==last_x & sprite1.getY()>=last_y & sprite1.getY()<=player.getY()) return;
                            }
                        }
                        key = 0;
                    }
                }
                if (player.last_y==last_y){
                    if (player.last_x<=last_x) {
                        for (int j=0;j<assetManager.sprites.size();j++){
                            Sprite sprite1 = assetManager.sprites.get(j);
                            if (sprite1 instanceof Tiles |  sprite1 instanceof Bomb){

                                if (sprite1.getY()==last_y & sprite1.getX()<=last_x & sprite1.getX()>=player.getX()) return;
                            }
                        }
                        key = 2;
                    }
                    else {
                        for (int j=0;j<assetManager.sprites.size();j++){
                            Sprite sprite1 = assetManager.sprites.get(j);
                            if (sprite1 instanceof Tiles |  sprite1 instanceof Bomb){

                                if (sprite1.getY()==last_y & sprite1.getX()<=last_x & sprite1.getX()>=player.getX()) return;
                            }
                        }
                        key = 3;
                    }
                }
            }
        }
    }

    public boolean check_collision(int x,int y,int x_, int y_){
        boolean f1 = (x<=x_&x+64>x_);
        boolean f2 = (x_<=x&x_+64>x);
        boolean f3 = (y<=y_&y+64>y_);
        boolean f4 = (y_<=y&y_+64>y);
        return ((f1|f2)&(f3|f4));
    }



    @Override
    public void Update(float dt) {
        key =  (int)(Math.random() * ((3 - 0) + 1)) ;
        if (type==1)
        {
            speed = speed_[(int)(Math.random() * ((1 - 0) + 1))];
            chase();
        }

        check_death();
        if (death){
            time += dt;
            if (time > time_change){
                animation +=1;
                current_action =4;

                time -= time_change;
            }
            if (animation>3){
                setclear();
            }
            return;
        }

        if (ismoving){

            time+=dt;
            if (time > time_change){
                animation +=1;
                animation = animation % 4;
                time -= time_change;

            }
            if (current_action==3) x+= (speed)*dt;
            if (current_action==2) x-= (speed)*dt;
            if (current_action==1) y-= (speed)*dt;
            if (current_action==0) y+= (speed)*dt;
            if (x > last_x+64 | y>last_y+64 ) {
                ismoving = false;
                animation = 0;
                time = 0;
                x = Math.min(x,last_x+64);
                y = Math.min(y,last_y+64);
                last_x = (int)x;
                last_y = (int)y;
            }

            if (x < last_x-64 | y<last_y-64 ) {
                ismoving = false;
                animation = 0;
                time = 0;
                x = Math.max(x,last_x-64);
                y = Math.max(y,last_y-64);
                last_x = (int)x;
                last_y = (int)y;
            }
            return;
        }


        if (key==3)
        {
            if (check_next_move(last_x+64,last_y)){
                ismoving = true;
                current_action=3;

            }

        }

        if (key==1) {
            if (check_next_move(last_x,last_y-64)){
                current_action=1;
                ismoving = true;

            }

        }

        if (key==0) {
            if (check_next_move(last_x,last_y+64)){
                current_action=0;
                ismoving = true;

            }

        }
        if (key==2) {
            if (check_next_move(last_x-64,last_y)){
                current_action=2;
                ismoving = true;

            }

        }
    }


    @Override
    public int getX() {
        return(int) x;
    }

    @Override
    public int getY() {
        return (int)y;
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
        return dy+current_action*width;
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
