package state;

import Asset.AssetManager;
import Asset.Background;
import Asset.Sprite;
import Input.InputManager;

import javax.swing.*;
import java.awt.*;

public class SplashState extends State {
    private float time = 0;
    public Background background  = new Background();

    public StateMachine stateMachine ;
    public AssetManager assetManager;
    public InputManager inputManager;

    public SplashState(int type){
        background = new Background();
        background.imageID = type;

    }
    @Override
    public void  Init(StateMachine stateMachine, AssetManager assetManager, InputManager inputManager) {
        this.stateMachine = stateMachine;
        this.assetManager = assetManager;
        this.inputManager = inputManager;
        assetManager.sprites.add(background);



    }

    @Override
    public void Update(float dt) {
        time += dt;

        if (time >2){
            if (background.imageID==9){
                PlayState.level=1;

            }
            if (background.imageID==13)
                stateMachine.AddState(new PlayState(PlayState.level));
            else
                stateMachine.AddState(new SplashState(13));
        }

    }

    @Override
    public void Draw() {

    }

    @Override
    public void HandleInput() {

    }

}
