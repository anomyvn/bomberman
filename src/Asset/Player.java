package Asset;

import Input.InputManager;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import state.PlayState;
import state.SplashState;
import state.StateMachine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Math.round;

public class Player extends Sprite{
    public float x ;
    public float y ;
    public int height=64;
    public int width=64;
    public float speed = 128*1.5f;
    public int animation = 0;
    public int type = 0;
    public float time_change = 64.0f/speed/3.0f;
    public float time = 0.0f;
    public int dx = 0 ;
    public int dy=0;
    public int last_x ;
    public int last_y ;
    public int max_boom =1;
    public int boom_length = 2;

    public int current_key=0;
    public boolean ismoving = false;
    public int current_action = 0;

    public int imageID=2;
    public InputManager inputManager;
    public AssetManager assetManager;
    public StateMachine stateMachine;
    public boolean death=false;
    public boolean finish = false;

    public Player(InputManager inputManager,AssetManager assetManager,StateMachine stateMachine){
        this.inputManager = inputManager;
        this.assetManager = assetManager;
        this.stateMachine = stateMachine;
    }


    @Override
    public int getX() {
        return round(x);
    }

    @Override
    public int getY() {
        return round(y);
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
        return dx +animation*width;

    }

    @Override
    public int getdy() {

        return dy+current_action*width;
    }

    public void Bombing(){
        int count =0;

        for (int i=0;i<assetManager.sprites.size();i++){
            if (assetManager.sprites.get(i) instanceof Bomb)
            {
                if (assetManager.sprites.get(i).getX()==last_x & assetManager.sprites.get(i).getY()==last_y) return;
                count +=1;
            }
        }
        if (count== max_boom) return;

        Bomb bomb = new Bomb();
        bomb.assetManager = assetManager;

        bomb.x = last_x;
        bomb.y = last_y;
        if (last_x<x-32){
            bomb.x +=64;
        }
        if (last_x>x+32){
            bomb.x -= 64;
        }
        if (last_y<y-32){
            bomb.y +=64;
        }
        if (last_y>y+32){
            bomb.y -= 64;
        }
        bomb.length = boom_length;
        assetManager.sprites.add(bomb);


    }
    public void HandleInput(){

        current_key = inputManager.getKeypressed();
    }

    public boolean check_next_move(int x,int y){

        boolean flag = true;
        for (int i=0;i<assetManager.sprites.size();i++){
            if (assetManager.sprites.get(i).getX()==x & assetManager.sprites.get(i).getY()==y){
                Sprite sprite = assetManager.sprites.get(i);
                if (sprite instanceof Tiles & sprite.gettype()!=2){

                    flag = false;
                }
                if (sprite instanceof Bomb) flag = false;
            }
        }
        if (flag)
            check_finish(x,y);
        return flag;
    }


    public void next_move(int x ,int y){
        for (int i=0;i<assetManager.sprites.size();i++){
            Sprite sprite = assetManager.sprites.get(i);
            if (sprite instanceof Items & sprite.getX()==x & sprite.getY()==y){
                Items items = (Items) sprite;
                if (items.type==0)
                {
                    speed += 50.f;
                    sprite.setclear();
                }

                if (items.type==1)
                {
                    boom_length+=1;
                    sprite.setclear();
                }


                if (items.type==2)
                {
                    sprite.setclear();
                    max_boom+=1;
                }
            }
        }
    }

    public boolean check_collision(int x,int y,int x_, int y_){
        boolean f1 = (x<=x_&x+64>x_);
        boolean f2 = (x_<=x&x_+64>x);
        boolean f3 = (y<=y_&y+64>y_);
        boolean f4 = (y_<=y&y_+64>y);
        if (x==x_&y==y_) return true;
        return ((f1|f2)&(f3|f4));
    }

    public void check_finish(int x,int y){

        for (int i=0;i<assetManager.sprites.size();i++){
            Sprite sprite = assetManager.sprites.get(i);
            if (sprite instanceof Tiles & sprite.getX()==x & sprite.getY()==y){
                Tiles tiles = (Tiles) sprite;
                if (tiles.type==2){
                    int count = 0;
                    for (int j=0;j<assetManager.sprites.size();j++){
                        Sprite sprite1 = assetManager.sprites.get(j);
                        if (sprite1 instanceof Enemy) count +=1;
                    }
                    if (count==0) finish = true;
                }
            }
        }

    }
    public void check_death(){
        if (death) return;
        for (int i=0;i<assetManager.sprites.size()-1;i++){
            Sprite sprite =assetManager.sprites.get(i);
            if (sprite instanceof Flame | sprite instanceof Enemy){
                if (check_collision((int)x,(int)y,sprite.getX(),sprite.getY()))
                {
                    death = true;
                    time = 0;
                    time_change = 0.2f;
                }

            }
        }

    }
    @Override
    public void Update(float dt) {
        if (inputManager.space_enter){
            inputManager.space_enter = false;
            Bombing();
        }

        if (finish){
            PlayState.level +=1;
            for (int i=0;i<PlayState.levels.size();i++){
                System.out.println(PlayState.levels.get(i));
                if (PlayState.levels.get(i)== PlayState.level){
                    stateMachine.AddState(new SplashState(13));

                    return;
                }
            }
            stateMachine.AddState(new SplashState(9));
            return;

        }
        check_death();
        if (death){
            time += dt;
            if (time > time_change){
                animation +=1;
                current_action =4;
                animation = animation ;
                time -= time_change;
            }
            if (animation>3){
                stateMachine.AddState(new SplashState(8));

            }
            return;
        }


        if (ismoving){
            time += dt;
            if (time > time_change){
                animation +=1;
                animation = animation % 3;
                time -= time_change;
            }
            if (current_action==3) x+= (speed)*dt;
            if (current_action==2) x-= (speed)*dt;
            if (current_action==0) y+= (speed)*dt;
            if (current_action==1) y-= (speed)*dt;
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


        if (current_key==39)
        {
            if (check_next_move(last_x+64,last_y)){
                ismoving = true;
                current_action=3;
                next_move(last_x+64,last_y);
                check_finish(last_x+64,last_y);
            }

        }

        if (current_key==38) {
            if (check_next_move(last_x,last_y-64)){
                current_action=1;
                ismoving = true;
                next_move(last_x,last_y-64);
                check_finish(last_x,last_y-64);
            }

        }

        if (current_key==40) {
            if (check_next_move(last_x,last_y+64)){
                current_action=0;
                ismoving = true;
                next_move(last_x,last_y+64);
                check_finish(last_x,last_y+64);
            }

        }
        if (current_key==37) {
            if (check_next_move(last_x-64,last_y)){
                current_action=2;
                ismoving = true;
                next_move(last_x-64,last_y);
                check_finish(last_x-64,last_y);
            }

        }

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
