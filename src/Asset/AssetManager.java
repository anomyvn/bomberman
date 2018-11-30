package Asset;

import state.PlayState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class AssetManager extends JPanel {
    public ArrayList<Sprite> sprites;
    public static int width;
    public static int height;
    public BufferedImage[] image = new BufferedImage[15];
    public AssetManager() {
        sprites = new ArrayList<Sprite>();
        String[] strings = {"back_ground.png","bomb.png","bomber.png","creep1.png","creep2.png","flame.png","items.png","Tiles.png","game_over.png","you_win.png"};
        for (int i=0;i<10;i++){
            BufferedImage result = null;
            try {
                result = ImageIO.read(new File("./resource/"+strings[i]));
            } catch (IOException e) {
                System.err.println("Error image not found "+strings[i]);
            }
            image[i] = result;
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        super.paintComponent(g);
        Iterator<Sprite> listIterator = sprites.iterator();
        Player player = null;
        while (listIterator.hasNext()) {

            Object object = listIterator.next();
            Sprite sprite = ((Sprite) object);
            if (sprite.clear()) listIterator.remove();

        }
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            if ((sprite instanceof Player))
                player = (Player)sprite;
        }

        int x=0,y=0;

        if (player!=null){
            x = Math.max(0,player.getX()-64*16/2);
            y = Math.max(0,player.getY()-64*9/2);
            x = Math.min(x,width-64*16);
            y = Math.min(y,height-64*9);
        }


        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.get(i);
            if (sprite==null) continue;
            if (sprite.getImage()==13){
                Dimension d = this.getPreferredSize();
                int fontSize = 60;
                g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
                g.setColor(Color.red);
                g.drawString("LEVEL "+String.valueOf(PlayState.level), 400, 300);
                continue;
            }
            if (sprite instanceof Background){

                g.drawImage(image[sprite.getImage()],0,0,16*64,9*64,null);
                continue;
            }

            if (!(sprite instanceof Player)& sprite!=null)
                g.drawImage(image[sprite.getImage()], sprite.getX()-x, sprite.getY()-y, sprite.getX()-x + 64, sprite.getY()-y + 64,
                        sprite.getdx(), sprite.getdy(), sprite.getdx() + sprite.getWidth(), sprite.getdy() + sprite.getHeight(), null);

        }

        if (player!=null)
            g.drawImage(image[player.getImage()], player.getX()-x, player.getY()-y, player.getX()-x + 64, player.getY()-y + 64,
                player.getdx(), player.getdy(), player.getdx() + player.getWidth(), player.getdy() + player.getHeight(), null);



    }
}
