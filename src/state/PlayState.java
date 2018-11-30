package state;

import Asset.*;
import Input.InputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class PlayState extends State{

    public StateMachine stateMachine ;
    public AssetManager assetManager;
    public InputManager inputManager;
    public Player player;
    public static ArrayList<Integer> levels = new ArrayList<Integer>();
    public static int level=1;

    public PlayState(int level){
        this.level = level;
    }

    @Override
    public void  Init(StateMachine stateMachine, AssetManager assetManager,InputManager inputManager) {
        this.assetManager = assetManager;
        this.stateMachine = stateMachine;
        this.inputManager = inputManager;

        assetManager.sprites.clear();
        String path = "./resource/classic.png";

        File file = new File("./resource/level.txt");
        boolean flag = true;
        try {
            Scanner scanner = new Scanner(file);
            levels.clear();
            while(scanner.hasNextLine()){

                String s= scanner.next();

                int level = Integer.parseInt(s);
                s =scanner.next();
                levels.add(level);

                int row = Integer.parseInt(s);
                s =scanner.next();
                int column = Integer.parseInt(s);
                scanner.nextLine();



                if (PlayState.level==level){
                    flag = false;
                    assetManager.height= row*64;
                    assetManager.width = column*64;
                    for (int i=0;i<row;i++)
                        for (int j=0;j<column;j++){
                            Grass grass = new Grass();
                            grass.x = j*64;
                            grass.y = i*64;
                            assetManager.sprites.add(grass);
                        }
                }
                for (int i=0;i<row;i++){
                    String col = scanner.nextLine();
                    if (PlayState.level!=level) continue;
                    for (int j=0;j<col.length();j++){

                        if (col.charAt(j)=='#'){
                            Tiles tile = new Tiles(0);
                            tile.x = j*64;
                            tile.y = i*64;
                            assetManager.sprites.add(tile);
                            continue;
                        }
                        if (col.charAt(j)=='*'){
                            Tiles tile = new Tiles(1);
                            tile.x = j*64;
                            tile.y = i*64;
                            assetManager.sprites.add(tile);
                            continue;
                        }
                        if (col.charAt(j)=='x'){
                            Tiles tile = new Tiles(2);
                            tile.x = j*64;
                            tile.y = i*64;
                            assetManager.sprites.add(tile);
                            continue;
                        }
                        if (col.charAt(j)=='p'){
                            player = new Player(inputManager,assetManager,stateMachine);
                            player.x = j*64;
                            player.y = i*64;
                            player.last_x = (int)player.x;
                            player.last_y = (int)player.y;
                            assetManager.sprites.add(player);
                            continue;
                        }
                        if (col.charAt(j)=='b'){
                            Items items = new Items(2);
                            items.x = j*64;
                            items.y = i*64;
                            assetManager.sprites.add(items);
                            continue;
                        }
                        if (col.charAt(j)=='f'){
                            Items items = new Items(1);
                            items.x = j*64;
                            items.y = i*64;
                            assetManager.sprites.add(items);
                            continue;
                        }
                        if (col.charAt(j)=='s'){
                            Items items = new Items(0);
                            items.x = j*64;
                            items.y = i*64;
                            assetManager.sprites.add(items);
                            continue;
                        }

                        if (col.charAt(j)=='1'){
                            Enemy enemy = new Enemy(0);
                            enemy.x = j*64;
                            enemy.last_x = (int)enemy.x;
                            enemy.y = i*64;
                            enemy.last_y = (int)enemy.y;
                            assetManager.sprites.add(enemy);
                            enemy.assetManager = assetManager;
                            continue;
                        }
                        if (col.charAt(j)=='2'){
                            Enemy enemy = new Enemy(1);
                            enemy.x = j*64;
                            enemy.last_x = (int)enemy.x;
                            enemy.y = i*64;
                            enemy.last_y = (int)enemy.y;
                            enemy.assetManager = assetManager;
                            assetManager.sprites.add(enemy);
                            continue;
                        }
                    }

                }
                if (PlayState.level==level){
                    for (int i=0;i<assetManager.sprites.size();i++){
                        Sprite sprite = assetManager.sprites.get(i);
                        if (sprite instanceof Items){
                            Tiles tiles = new Tiles(1);
                            tiles.x = sprite.getX();
                            tiles.y = sprite.getY();

                            assetManager.sprites.add(tiles);

                        }
                        if (sprite instanceof Tiles){
                            Tiles tiles = (Tiles)sprite;
                            if (tiles.type==2){
                                Tiles tile = new Tiles(1);
                                tile.x = sprite.getX();
                                tile.y = sprite.getY();
                                assetManager.sprites.add(tile);
                            }

                        }
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Update(float dt) {
        for (int i=0;i<assetManager.sprites.size();i++){
            assetManager.sprites.get(i).Update(dt);
        }
    }

    @Override
    public void Draw() {

    }

    @Override
    public void HandleInput() {

        player.HandleInput();

    }


}
