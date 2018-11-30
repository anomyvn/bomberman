import Asset.AssetManager;
//import Input.InputManager;
import Input.InputManager;
import state.PlayState;
import state.SplashState;
import state.StateMachine;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

public class Game {
    private JFrame mainFrame;
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;
    private JLabel msglabel;

    private AssetManager assetManager;
    private InputManager inputManager;
    private StateMachine stateMachine;
    //private boolean isopen=true;
    public Game(){

        assetManager = new AssetManager();
        inputManager = new InputManager();
        stateMachine = new StateMachine(inputManager,assetManager);
    }


    public void set_window(){
        mainFrame = new JFrame("Bomberman");
        mainFrame.setSize(16*64,9*64+35);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        mainFrame.add(assetManager);
        mainFrame.setVisible(true);

    }

    public void Run(){
        set_window();
        //ti them lai
        stateMachine.AddState(new SplashState(0));


        mainFrame.add(assetManager);

        mainFrame.addKeyListener(inputManager);

        long newTime,  interpolation;
        float frameTime;
        long currentTime = System.nanoTime();
        int a =0;
        while(true){



            newTime = System.nanoTime();

            frameTime = (float)(newTime - currentTime)/1000000000.0f;


            currentTime = System.nanoTime();
            //mainFrame.setTitle("Bomberman"+"       "+String.valueOf((long)(1.0f/frameTime))+" FPS");
            stateMachine.Process_change();

            stateMachine.get_state().HandleInput();

            stateMachine.get_state().Update(frameTime);
            mainFrame.repaint();


        }

    }
}
