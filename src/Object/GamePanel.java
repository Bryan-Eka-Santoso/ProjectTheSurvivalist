package Object;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Object.Plant.*;
import Object.Player.*;
import Object.Items.Unstackable.*;
import Object.Environment.EnvironmentManager;
import Object.Items.StackableItem.Bread;
import Object.Animal.*;
import Object.Items.StackableItem.Torch;

public class GamePanel extends JPanel implements Runnable {

    final int ORI_TILE_SIZE = 16;
    final int scale = 3;

    public final int TILE_SIZE = ORI_TILE_SIZE * scale;
    final int MAX_SCREEN_COL = 30;
    final int MAX_SCREEN_ROW = 20;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    final int FPS = 60;

    public final int MAX_WORLD_COL = 98;
    public final int MAX_WORLD_ROW = 98;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    TileManager tileM = new TileManager(this);
    public UI ui = new UI(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    Crafting recipe = new Crafting();
    public CollisonChecker cCheck = new CollisonChecker(this);
    
    // Game State
    public int gameState;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int INVENTORY_STATE = 3;
    public final int PLAYER_CRAFTING_STATE = 4;
    
    public Player player = new Player("Player", recipe, this, keyH);
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
        eManager.setup();
    }
    
    public void setupGame() {
        gameState = PLAY_STATE;
    }
    
    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void addPlant(int x, int y) {
        plants.add(new GuavaTree(x * TILE_SIZE, y * TILE_SIZE, this));
    }
    
    public void addAnimal(int x, int y) {
        animals.add(new Chicken("Chiscken", x * TILE_SIZE, y * TILE_SIZE, this));
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        addPlant(40, 45);
        addPlant(40, 49);
        addAnimal(30, 50);
        player.inventory.addItems(new Sword("Sword", 20, 30));
        player.inventory.addItems(new Torch(this));
        player.inventory.addItems(new Bread(34));
        player.inventory.addItems(new Axe("Axe", 20, 30));
        
        long interval = 500_000_000L;
        long lastAnimalMoveTime = System.nanoTime();

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            lastTime = currentTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }

            if (currentTime - lastAnimalMoveTime >= interval) {
                for (int i = 0; i < animals.size(); i++) {
                    animals.get(i).update();
                }
                
                lastAnimalMoveTime = currentTime;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }

        }
    }

    public void update() {
        if (gameState == PLAY_STATE) {
            player.update();
        } 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        for (int i = 0; i < plants.size(); i++) {
            plants.get(i).draw(g2);
        }
        for (int i = 0; i < animals.size(); i++) {
            animals.get(i).draw(g2);
        }
        player.draw(g2);
        eManager.lighting.update();
        eManager.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

}
