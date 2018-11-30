package state;

import Asset.AssetManager;
import Input.InputManager;

import java.util.ArrayList;

public class StateMachine {
    public ArrayList<State> states = new ArrayList<State>();
    public InputManager inputManager ;
    public AssetManager assetManager;
    private State waiting_state=null;
    public StateMachine(InputManager inputManager,AssetManager assetManager){
        this.assetManager = assetManager;
        this.inputManager = inputManager;

    }
    public void Process_change(){
        if (waiting_state!=null){

            states.clear();
            this.assetManager.sprites.clear();
            waiting_state.Init(this,this.assetManager,this.inputManager);
            states.add(waiting_state);
            waiting_state = null;
        }
    }

    public void AddState(State state){
        waiting_state = state;
    }

    public void RemoveState(){

    }

    public State get_state(){
        return states.get(states.size()-1);
    }

}
