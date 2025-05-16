package Object.Controller;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Object.Plant.*;
import Object.Player.*;
import java.awt.Point;
import Object.Items.Unstackable.*;
import Object.Environment.EnvironmentManager;
import Object.Items.StackableItem.Bread;
import Object.Animal.*;
import Object.Items.StackableItem.Torch;
import Object.Items.Unstackable.Buildings.*;
import Object.Items.StackableItem.Wood;

public class GamePanel extends JPanel implements Runnable {

    final int ORI_TILE_SIZE = 15;
    final int scale = 3;

    public final int TILE_SIZE = ORI_TILE_SIZE * scale;
    final int MAX_SCREEN_COL = 25;
    final int MAX_SCREEN_ROW = 17;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    final int FPS = 60;

    public final int MAX_WORLD_COL = 98;
    public final int MAX_WORLD_ROW = 98;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public TileManager tileM = new TileManager(this);
    public UI ui = new UI(this);
    EnvironmentManager eManager = new EnvironmentManager(this);
    KeyHandler keyH = new KeyHandler(this);
    Sound sound = new Sound();
    Thread gameThread;
    Crafting recipe = new Crafting();
    public CollisonChecker cCheck = new CollisonChecker(this);
    
    // Game State
    public int gameState;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int INVENTORY_STATE = 3;
    public final int PLAYER_CRAFTING_STATE = 4;
    public final int DROPPED_ITEM_STATE = 5;
    public final int BUILDING_STATE = 6;
    private static final int MAX_CHICKENS = 10;
    private static final int MAX_COWS = 5;
    private static final int MAX_SHEEP = 5;
    private static final int MAX_PIGS = 5;
    
    public Player player = new Player("Player", recipe, this, keyH);
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<ItemDrop> droppedItems = new ArrayList<>();
    public ArrayList<Buildings> buildings = new ArrayList<>();
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);
        eManager.setup();
    }
    
    public void setupGame() {
        gameState = PLAY_STATE;
        playMusic(7);
    }
    
    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void addPlant(int x, int y) {
        plants.add(new GuavaTree(x * TILE_SIZE, y * TILE_SIZE, this));
    }
    public void checkAndRespawnAnimals() {
        int chickenCount = 0;
        int cowCount = 0;
        int sheepCount = 0;
        int pigCount = 0;

        for(Animal animal : animals) {
            if(animal instanceof Chicken) chickenCount++;
            else if(animal instanceof Cow) cowCount++;
            else if(animal instanceof Sheep) sheepCount++;
            else if(animal instanceof Pig) pigCount++;
        }

        ArrayList<Point> usedPositions = new ArrayList<>();
        for(Animal animal : animals) {
            usedPositions.add(new Point(animal.worldX/TILE_SIZE, animal.worldY/TILE_SIZE));
        }

        if(chickenCount < MAX_CHICKENS) {
            spawnAnimal("chicken", MAX_CHICKENS - chickenCount, usedPositions);
        }
        if(cowCount < MAX_COWS) {
            spawnAnimal("cow", MAX_COWS - cowCount, usedPositions);
        }
        if(sheepCount < MAX_SHEEP) {
            spawnAnimal("sheep", MAX_SHEEP - sheepCount, usedPositions);
        }
        if(pigCount < MAX_PIGS) {
            spawnAnimal("pig", MAX_PIGS - pigCount, usedPositions);
        }
    }

private void spawnAnimal(String animalType, int count, ArrayList<Point> usedPositions) {
    int attempts = 0;
    int maxAttempts = 1000; // Prevent infinite loop
    int spawnedCount = 0;
    int validTiles = 18;
    
    int playerSpawnX = 40; 
    int playerSpawnY = 44; 
    int safeZoneRadius = 5;
    while (spawnedCount < count && attempts < maxAttempts) {
       
        int x = (int)(Math.random() * (MAX_WORLD_COL -5));
        int y = (int)(Math.random() * (MAX_WORLD_ROW -5));
        Point pos = new Point(x, y);

        if (Math.abs(x - playerSpawnX) < safeZoneRadius && Math.abs(y - playerSpawnY) < safeZoneRadius) {
            attempts++;
            continue;
        }
        // Check if position is already used
        if (usedPositions.contains(pos)) {
            attempts++;
            continue;
        }
        
        // Check if tile is grass
        int tileNum = tileM.mapTile[x][y];
        boolean isValidTile = false;
        
        if (tileNum == validTiles) {
            isValidTile = true;
        }
        
        
        if (!isValidTile) {
            attempts++;
            continue;
        }
        
        // Spawn the animal based on type
        switch (animalType.toLowerCase()) {
            case "chicken":
                animals.add(new Chicken("Chicken", x * TILE_SIZE, y * TILE_SIZE, this));
                break;
            case "cow":
                animals.add(new Cow("Cow", x * TILE_SIZE, y * TILE_SIZE, this));
                break;
            case "sheep":
                animals.add(new Sheep("Sheep", x * TILE_SIZE, y * TILE_SIZE, this));
                break;
            case "pig":
                animals.add(new Pig("Pig", x * TILE_SIZE, y * TILE_SIZE, this));
                break;
        }
        
        // Mark position as used
        usedPositions.add(pos);
        spawnedCount++;
        attempts = 0;
    }
}

    
    public void addAnimal(int x, int y) {
        animals.add(new Chicken("Chicken", x * TILE_SIZE, y * TILE_SIZE, this));
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
        player.inventory.addItems(new Sword("Sword", 20, 30));
        player.inventory.addItems(new Torch(this));
        player.inventory.addItems(new Axe("Axe", 20, 30));
        player.inventory.addItems(new Wood("Wood", 20, 30));
        player.inventory.addItems(new Bread(10));
        player.inventory.addItems(new Chest(this));
        player.inventory.addItems(new KandangAyam(this));
        player.inventory.addItems(new PigCage(this));
        player.inventory.addItems(new SheepCage(this));
        player.inventory.addItems(new CowCage(this));

        
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
                checkAndRespawnAnimals();  
                repaint();
                delta--;
            }

            if (currentTime - lastAnimalMoveTime >= interval) {
                if (gameState != PAUSE_STATE) {
                    for (int i = 0; i < animals.size(); i++) {
                        animals.get(i).update();
                    }
                }
                lastAnimalMoveTime = currentTime;
            }

            if (timer >= 1000000000) {
                timer = 0;
            }
        }
    }

    public void update() {
        if (gameState != PAUSE_STATE) {
            player.update();
        } 
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY <= player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        for (int i = 0; i < droppedItems.size(); i++) {
            droppedItems.get(i).draw(g2);
        }
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).worldY <= player.worldY) {
                animals.get(i).draw(g2);
            }
        }
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY <= player.worldY) {
                buildings.get(i).draw(g2);
            }
        }
        if (gameState == BUILDING_STATE) {
            ((Buildings) player.inventory.getSelectedItem()).Build(g2);
        }
        player.draw(g2);
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY > player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).worldY > player.worldY) {
                animals.get(i).draw(g2);
            }
        }
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY > player.worldY) {
                buildings.get(i).draw(g2);
            }
        }
        eManager.lighting.update();
        eManager.draw(g2);
        ui.draw(g2);
        g2.dispose();
    }

    public void playMusic(int i) {
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic() {
        sound.stop();
    }

}
