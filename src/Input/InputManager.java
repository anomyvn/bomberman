package Input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class InputManager implements KeyListener {
    public boolean[] keypressed = new boolean[120];
    public boolean space_enter=false;

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) {

    }


    @Override
    public void keyPressed(KeyEvent e) {
        keypressed[e.getKeyCode()] = true;

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        keypressed[key] = false;
        if (key==32) {
            space_enter = true;
        }
    }


    public int getKeypressed(){
        for (int i=37;i<=40;i++){
            if (keypressed[i]) return i;
        }
        return 0;
    }
}
