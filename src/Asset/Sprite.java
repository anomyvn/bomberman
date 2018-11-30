package Asset;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite {
    public abstract void Update(float dt);
    public abstract int getImage();
    public abstract int getX();
    public abstract int getY();
    public abstract int getWidth();
    public abstract int getHeight();
    public abstract int getdx();
    public abstract int getdy();
    public abstract int gettype();
    public abstract boolean clear();
    public abstract void setclear();


}
