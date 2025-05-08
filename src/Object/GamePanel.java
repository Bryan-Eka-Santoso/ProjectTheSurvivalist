package Object;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Object.Plant.*;
import Object.Player.*;
import java.awt.Point;
import java.awt.Rectangle;
import Object.Items.Unstackable.*;
import Object.Environment.EnvironmentManager;
import Object.Items.StackableItem.Bread;
import Object.Animal.*;
import Object.Items.StackableItem.Torch;

public class GamePanel extends JPanel implements Runnable {

    final int ORI_TILE_SIZE = 16;
    final int scale = 3;

    public final int TILE_SIZE = ORI_TILE_SIZE * scale;
    final int MAX_SCREEN_COL = 25;
    final int MAX_SCREEN_ROW = 15;
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
    public void addAnimals() {
        // Spawn animals with shared position checking
        ArrayList<Point> usedPositions = new ArrayList<>();
        
        // Spawn 10 chickens
        spawnAnimal("chicken", 10, usedPositions);
        
        // Spawn 5 cows
        spawnAnimal("cow", 5, usedPositions);
        
        // Spawn 5 sheep
        spawnAnimal("sheep", 5, usedPositions);
        
        // Spawn 5 pigs
        spawnAnimal("pig", 5, usedPositions);
    }
    private void spawnAnimal(String type, int count, ArrayList<Point> usedPositions) {
        for(int i = 0; i < count; i++) {
            int randomX, randomY;
            boolean validPosition;
            do {
                validPosition = true;
                
                // Generate random position dengan batas 
                int margin = 3;
                randomX = (int)(Math.random() * ((MAX_WORLD_COL-margin) * TILE_SIZE));
                randomY = (int)(Math.random() * ((MAX_WORLD_ROW-margin) * TILE_SIZE));
                if(randomX < margin * TILE_SIZE || randomY < margin * TILE_SIZE) {
                    validPosition = false;
                    continue;
                }
                // Check position with temporary animal
                Animal tempAnimal = null;
                switch(type) {
                    case "chicken": tempAnimal = new Chicken("temp", randomX, randomY, this); break;
                    case "cow": tempAnimal = new Cow("temp", randomX, randomY, this); break;
                    case "sheep": tempAnimal = new Sheep("temp", randomX, randomY, this); break;
                    case "pig": tempAnimal = new Pig("temp", randomX, randomY, this); break;
                }
                
                // Check tile collision
                try {
                    cCheck.animalCheckTile(tempAnimal);
                    if(tempAnimal.collisionOn) {
                        validPosition = false;
                        continue;
                    }
                } catch(ArrayIndexOutOfBoundsException e) {
                    validPosition = false;
                    continue;
                }
                
                // Check used positions
                Point newPos = new Point(randomX, randomY);
                for(Point pos : usedPositions) {
                    if(Math.abs(pos.x - randomX) < TILE_SIZE * 2 && 
                       Math.abs(pos.y - randomY) < TILE_SIZE * 2) {
                        validPosition = false;
                        break;
                    }
                }
                
                // Check plant collision
                Rectangle tempArea = new Rectangle(randomX, randomY, TILE_SIZE, TILE_SIZE);
                for(Plant plant : plants) {
                    if(tempArea.intersects(new Rectangle(plant.worldX, plant.worldY, TILE_SIZE, TILE_SIZE))) {
                        validPosition = false;
                        break;
                    }
                }
                
            } while(!validPosition);
            
            // Add position to used positions
            usedPositions.add(new Point(randomX, randomY));
            
            // Create and add actual animal
            Animal animal = null;
            switch(type) {
                case "chicken": animal = new Chicken(type + i, randomX, randomY, this); break;
                case "cow": animal = new Cow(type + i, randomX, randomY, this); break;
                case "sheep": animal = new Sheep(type + i, randomX, randomY, this); break;
                case "pig": animal = new Pig(type + i, randomX, randomY, this); break;
            }
            
            if(animal != null) {
                animals.add(animal);
            }
        }
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
        addAnimals();
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
