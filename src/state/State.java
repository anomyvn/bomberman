package state;

import javax.swing.*;
import Asset.AssetManager;
import Input.InputManager;

abstract public class State {
    abstract public void Init(StateMachine stateMachine, AssetManager assetManager, InputManager inputManager);
    abstract public void HandleInput();
    abstract public void Update(float dt);
    abstract public void Draw();
}
