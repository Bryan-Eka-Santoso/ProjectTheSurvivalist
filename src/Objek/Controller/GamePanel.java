package Objek.Controller;

import javax.swing.*;
import Objek.Animal.*;
import Objek.Environment.EnvironmentManager;
import Objek.Fish.Arwana;
import Objek.Fish.Belida;
import Objek.Fish.Fish;
import Objek.Items.Buildings.*;
import Objek.Items.Unstackable.Torch;
import Objek.Items.Unstackable.Arsenals.WindAxe;
import Objek.Plant.*;
import Objek.Player.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.Point;

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
    
    Crafting recipe = new Crafting();
    KeyHandler keyH = new KeyHandler(this);
    public Player player = new Player("Player", recipe, this, keyH);
    public TileManager tileM = new TileManager(this);
    public UI ui = new UI(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    Sound sound = new Sound();
    Thread gameThread;
    public CollisonChecker cCheck = new CollisonChecker(this);
    
    // Game State
    public int gameState;
    public final int PLAY_STATE = 1;
    public final int PAUSE_STATE = 2;
    public final int INVENTORY_STATE = 3;
    public final int PLAYER_CRAFTING_STATE = 4;
    public final int DROPPED_ITEM_STATE = 5;
    public final int BUILDING_STATE = 6;
    public final int OPEN_CHEST_STATE = 7;
    public final int OPEN_CRAFTINGTABLE_STATE = 8;
    public final int OPEN_SMELTER_STATE = 9;
    public final int OPEN_BED_STATE = 10;
    public final int KANDANG_STATE = 11;
    public final int FISHING_STATE = 12;

    private static final int MAX_CHICKENS = 10;
    private static final int MAX_COWS = 5;
    private static final int MAX_SHEEP = 5;
    private static final int MAX_PIGS = 5;
    private static final int MAX_WOLF = 3;
    public Kandang currentKandang;
    public final int maxMap = 10;
    public int currentMap = 0;
    public boolean isCave = false;
    
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<ItemDrop> droppedItems = new ArrayList<>();
    public ArrayList<Fish> fish = new ArrayList<>();
    public ArrayList<Buildings> buildings = new ArrayList<>();
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.addMouseWheelListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);
        eManager.setup();
    }

    public void handleKandangInteraction(Kandang kandang) {
        currentKandang = kandang;
        gameState = KANDANG_STATE;
    }

    public void setupGame() {
        gameState = PLAY_STATE;
        playMusic(7);
    }

    public void reloadTile(){
        tileM = new TileManager(this);
    }
    
    public void startgameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void addPlant(Plant p) {
        plants.add(p);
    }
    
    public void checkAndRespawnAnimals() {
        int chickenCount = 0;
        int cowCount = 0;
        int sheepCount = 0;
        int pigCount = 0;
        int wolfCount = 0;

        for(Animal animal : animals) {
            if(animal instanceof Chicken) chickenCount++;
            else if(animal instanceof Cow) cowCount++;
            else if(animal instanceof Sheep) sheepCount++;
            else if(animal instanceof Pig) pigCount++;
            else if(animal instanceof Wolf) wolfCount++;
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
        if (wolfCount < MAX_WOLF) {
            spawnAnimal("wolf", MAX_WOLF - wolfCount, usedPositions);
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

            boolean overPlant = false;
            for (Plant plant : plants) {
                int plantX = plant.worldX / TILE_SIZE;
                int plantY = plant.worldY / TILE_SIZE;
                if (Math.abs(x - plantX) < 2 && Math.abs(y - plantY) < 2) { 
                    overPlant = true;
                    break;
                }
            }
            if (overPlant) {
                attempts++;
                continue;
            }
            
            // Check if tile is grass
            int tileNum = tileM.mapTile[currentMap][x][y];
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
                case "wolf":
                    animals.add(new Wolf("Wolf", x * TILE_SIZE, y * TILE_SIZE, this));
                    break;
            }
            
            // Mark position as used
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }

    public void spawnFish(String animalType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000;
        int spawnedCount = 0;
        int validTiles = 16;
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (MAX_WORLD_COL -8));
            int y = (int)(Math.random() * (MAX_WORLD_ROW -8));
            Point pos = new Point(x, y);
            
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }
            
            int tileNum = tileM.mapTile[currentMap][x][y];
            boolean isValidTile = false;
            
            if (tileNum == validTiles) {
                isValidTile = true;
            }
            
            
            if (!isValidTile) {
                attempts++;
                continue;
            }

            switch (animalType.toLowerCase()) {
                case "arwana":
                    fish.add(new Arwana("arwana", 0, 9, x * TILE_SIZE, y * TILE_SIZE, this));
                    break;
                case "belida":
                    fish.add(new Belida("belida", 0, 7, x * TILE_SIZE, y * TILE_SIZE, this));
                    break;
            }
            
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }

    public void addAnimals() {
       
        ArrayList<Point> usedPositions = new ArrayList<>();
        
        spawnAnimal("chicken", 5, usedPositions);
        
        spawnAnimal("cow", 5, usedPositions);
        
        spawnAnimal("sheep", 5, usedPositions);
        
        spawnAnimal("pig", 5, usedPositions);
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        addPlant(new GuavaTree(40 * TILE_SIZE, 45 * TILE_SIZE, this));
        addPlant(new GuavaTree(40 * TILE_SIZE, 49 * TILE_SIZE, this));
        addPlant(new PalmTree(40 * TILE_SIZE,  54 * TILE_SIZE, this));
        addPlant(new Bush(40 * TILE_SIZE,  57 * TILE_SIZE, this));
        addPlant(new BerryBush(40 * TILE_SIZE,  60 * TILE_SIZE, this));
        addAnimals();
        player.inventory.addItems(new KandangAyam(this));
        player.inventory.addItems(new Torch(this));
        player.inventory.addItems(new WindAxe());

        long interval = 500_000_000L;
        long lastAnimalMoveTime = System.nanoTime();

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                if (currentMap == 0) {
                    checkAndRespawnAnimals();  
                }
                repaint();
                delta--;
            }
            
            if (currentTime - lastAnimalMoveTime >= interval) {
                lastAnimalMoveTime = currentTime;
            }
            
            if (timer >= 1000000000) {
                if(player.level > 14) {
                    reloadTile();
                }
                timer = 0;
            }
        }
    }
    
    public void update() {
        if (gameState == PAUSE_STATE) return;
        
        player.update();
        
        if (gameState != PAUSE_STATE) {
            for (int i = 0; i < animals.size(); i++) {
                animals.get(i).update();
                if (animals.get(i) instanceof Wolf) {
                    ((Wolf) animals.get(i)).chasePlayer(player);
                }
            }
            for (int i = 0; i < fish.size(); i++) {
                fish.get(i).update();
            }
        }

        ui.isCanGoToSea = false;
        ui.isNeedLevel15 = false;

        if (currentMap == 0) {
            int col = player.worldX / TILE_SIZE;
            int row = player.worldY / TILE_SIZE;

            if ((col == 27 || col == 28) && row == 17) {
                ui.isCanGoToSea = true;
            }

            if (player.level < 15 && (col == 27 || col == 28) && row == 18) {
                ui.isNeedLevel15 = true;
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
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
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY <= player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        for (int i = 0; i < fish.size(); i++) {
            if (fish.get(i).worldY <= player.worldY) {
                fish.get(i).draw(g2);
            }
        }
        if (gameState == BUILDING_STATE) {
            ((Buildings) player.inventory.getSelectedItem()).Build(g2);
        }
        player.draw(g2);
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).worldY > player.worldY) {
                animals.get(i).draw(g2);
            }
        }
        for (int i = 0; i < fish.size(); i++) {
            if (fish.get(i).worldY > player.worldY) {
                fish.get(i).draw(g2);
            }
        }
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY > player.worldY) {
                buildings.get(i).draw(g2);
            }
            if (buildings.get(i) instanceof Orchard) {
                ((Orchard) buildings.get(i)).updateGrowth();
            }
        }
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY > player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        
        // Update the lighting system before drawing it
        eManager.update();
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
