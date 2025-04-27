package Object;
import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Object.Plant.*;
import Object.Player.*;
import Object.Items.Unstackable.Sword;
import Object.Items.StackableItem.Bread;

public class GamePanel extends JPanel implements Runnable {

    final int ORI_TILE_SIZE = 16;
    final int scale = 3;

    public final int TILE_SIZE = ORI_TILE_SIZE * scale;
    final int MAX_SCREEN_COL = 30;
    final int MAX_SCREEN_ROW = 20;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    final int FPS = 60;

    public final int MAX_WORLD_COL = 94;
    public final int MAX_WORLD_ROW = 94;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    TileManager tileM = new TileManager(this);
    UI ui = new UI(this);
    KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisonChecker cCheck = new CollisonChecker(this);
    
    // Game State
    public int gameState;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int INVENTORY_STATE = 3;

    
    public Player player = new Player("Player", this, keyH);
    ArrayList<Plant> plants = new ArrayList<>();
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        tileM.mapTile[x][y] = 3;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        addPlant(23, 24);
        player.inventory.addItems(new Sword("Sword", 20, 30));
        player.inventory.addItems(new Bread(10));

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
        for (Plant p : plants) {
            p.draw(g2);
        }
        player.draw(g2);        
        ui.draw(g2);

        g2.dispose();
    }

}
