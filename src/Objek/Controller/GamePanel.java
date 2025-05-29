package Objek.Controller;

import javax.swing.*;
import Objek.Animal.*;
import Objek.Enemy.Bat;
import Objek.Enemy.Monster;
import Objek.Enemy.Golem;
import Objek.Environment.EnvironmentManager;
import Objek.Fish.Fish;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Foods.Bread;
import Objek.Items.StackableItem.Foods.Carrot;
import Objek.Items.StackableItem.Foods.Potato;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.Lantern;
import Objek.Items.Unstackable.WateringCan;
import Objek.Items.Unstackable.Armor.Boots.MetalBoots;
import Objek.Items.Unstackable.Armor.Chestplate.MetalChestplate;
import Objek.Items.Unstackable.Armor.Helmet.MetalHelmet;
import Objek.Items.Unstackable.Armor.Leggings.MetalLeggings;
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
    final int FPS = 30;

    public final int MAX_WORLD_COL = 98;
    public final int MAX_WORLD_ROW = 98;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public int SpawnX = 40, SpawnY = 44;
    
    KeyHandler keyH = new KeyHandler(this);
    Crafting recipe = new Crafting(this);
    public Player player = new Player("Player", 15, recipe, this, keyH);
    public TileManager tileM = new TileManager(this);
    public UI ui = new UI(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    Sound sound = new Sound();
    Thread gameThread;
    Spawn sp = new Spawn(this);
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
    public final int GAME_OVER_STATE = 13;

    private static final int MAX_CHICKENS = 10;
    private static final int MAX_COWS = 5;
    private static final int MAX_SHEEP = 5;
    private static final int MAX_PIGS = 5;
    private static final int MAX_WOLF = 3;

    public Kandang currentKandang;
    public final int maxMap = 10;
    public int currentMap = 0;
    public boolean isCave = false;
    public boolean isAfterUnlockShip = false;
    
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<Fish> fish = new ArrayList<>();
    public ArrayList<Monster> monsters = new ArrayList<>();
    public ArrayList<ItemDrop> droppedItems = new ArrayList<>();
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
            sp.spawnAnimal("chicken", MAX_CHICKENS - chickenCount, usedPositions);
        }
        if(cowCount < MAX_COWS) {
            sp.spawnAnimal("cow", MAX_COWS - cowCount, usedPositions);
        }
        if(sheepCount < MAX_SHEEP) {
            sp.spawnAnimal("sheep", MAX_SHEEP - sheepCount, usedPositions);
        }
        if(pigCount < MAX_PIGS) {
            sp.spawnAnimal("pig", MAX_PIGS - pigCount, usedPositions);
        }
        if (wolfCount < MAX_WOLF) {
            sp.spawnAnimal("wolf", MAX_WOLF - wolfCount, usedPositions);
        }
    }

    public void addAnimals() {
        ArrayList<Point> usedPositions = new ArrayList<>();
        sp.spawnAnimal("chicken", 5, usedPositions);
        sp.spawnAnimal("cow", 5, usedPositions);
        sp.spawnAnimal("sheep", 5, usedPositions);
        sp.spawnAnimal("pig", 5, usedPositions);
        sp.spawnAnimal("wolf", 3, usedPositions);
    }
    public void addPlant() {
        ArrayList<Point> usedPositions = new ArrayList<>();
        sp.spawnPlant("guava", 20, usedPositions);
        sp.spawnPlant("mango", 20, usedPositions);
        sp.spawnPlant("bush", 200, usedPositions);
        sp.spawnPlant("berrybush", 10, usedPositions);
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
        addPlant();
        player.inventory.addItems(new MetalHelmet());
        player.inventory.addItems(new MetalChestplate());
        player.inventory.addItems(new MetalLeggings());
        player.inventory.addItems(new MetalBoots());
        player.inventory.addItems(new WateringCan());
        player.inventory.addItems(new Seeds(5));
        player.inventory.addItems(new Potato(5));
        player.inventory.addItems(new Carrot(5));
        player.inventory.addItems(new GardenPatch(this, 5));
        player.inventory.addItems(new Orchard(this, 1));
        player.inventory.addItems(new CoconutSeeds(2));
        player.inventory.addItems(new KandangAyam(this));
        player.inventory.addItems(new Lantern(this));
        player.inventory.addItems(new WindAxe());
        player.inventory.addItems(new Bed(this, 2));
        player.inventory.addItems(new Furnace(this, 2));
        player.inventory.addItems(new Chest(this, 2));
        player.inventory.addItems(new WateringCan());
        player.inventory.addItems(new Orchard(this, 1));
        player.inventory.addItems(new CoconutSeeds(2));
        player.inventory.addItems(new Bread());
        
        Buildings shop = new Shop(this, 1);
        shop.worldX = 40 * TILE_SIZE;
        shop.worldY = 40 * TILE_SIZE;
        buildings.add(shop);
        Buildings cave = new Cave(this, 1);
        cave.worldX = 50 * TILE_SIZE;
        cave.worldY = 50 * TILE_SIZE;
        buildings.add(cave);

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
                if(player.level == 15 && !isAfterUnlockShip) {
                    isAfterUnlockShip = true;
                    reloadTile();
                    ui.showCongratsUnlockShip();
                }

                timer = 0;
            }
        }
    }
    
    public void update() {
        
        if (gameState == PAUSE_STATE) return;
        
        if (player.health <= 0) {
            if (!player.inventory.isEmpty()) 
                player.dropAllItems();

            gameState = GAME_OVER_STATE;
            player.health = 0;
            eManager.lighting.filterAlpha = eManager.lighting.filterAlphaTemp;
            
        } else {
            player.update();
        }
        
        if (gameState != PAUSE_STATE) {
            if (currentMap == 0) {
                for (int i = 0; i < animals.size(); i++) {
                    animals.get(i).update();
                }
            }
            if (currentMap == 1) {
                for (int i = 0; i < fish.size(); i++) {
                    fish.get(i).update();
                }
            }
            if (currentMap == 2) {
                for (int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                    if (monsters.get(i) instanceof Bat) {
                        ((Bat) monsters.get(i)).chasePlayer();
                    }
                }
                for (int i = 0; i < monsters.size(); i++) {
                    monsters.get(i).update();
                    if (monsters.get(i) instanceof Golem) {
                        ((Golem) monsters.get(i)).chasePlayer();
                    }
                        
                }
            }
        }

        ui.isCanGoToSea = false;
        ui.isCanGoToLand = false;
        ui.isNeedLevel15 = false;
        ui.isCanGoToCave = false;
        int col = player.worldX / TILE_SIZE;
        int row = player.worldY / TILE_SIZE;

        if (currentMap == 0) {
            if ((col == 27 || col == 28) && row == 17) {
                ui.isCanGoToSea = true;
            }
            if (player.level < 15 && (col == 27 || col == 28) && row == 18) {
                ui.isNeedLevel15 = true;
            }
            if ((col == 50 || col == 51) && row == 51) {
                ui.isCanGoToCave = true;
            }
        } else if(currentMap == 1){
            if (col == 60 && row == 25) {
                ui.isCanGoToLand = true;
            }
        } else if(currentMap == 2){
            if((col == 22 || col == 23) && row == 23) {
                ui.isCanGoToLand = true;
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2);
        for (int i = 0; i < droppedItems.size(); i++) {
            if (droppedItems.get(i).mapIndex == currentMap) {
                droppedItems.get(i).draw(g2);
            }
        }
        for (int i = 0; i < animals.size(); i++) {
            if (animals.get(i).worldY <= player.worldY) {
                animals.get(i).draw(g2);
            }
        }
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY <= player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY <= player.worldY || !buildings.get(i).isAllowCollison) {
                buildings.get(i).draw(g2);
            }
        }
        for (int i = 0; i < fish.size(); i++) {
            if (fish.get(i).worldY <= player.worldY) {
                fish.get(i).draw(g2);
            }
        }
        for(Monster monster : monsters) {
            if(monster.worldY <= player.worldY) {
                monster.draw(g2);
            }
        }
        if (gameState == BUILDING_STATE) {
            ((Buildings) player.inventory.getSelectedItem()).Build(g2);
        }
        player.draw(g2);
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY > player.worldY && buildings.get(i).isAllowCollison) {
                buildings.get(i).draw(g2);
            }
            if (buildings.get(i) instanceof Orchard) {
                ((Orchard) buildings.get(i)).updateGrowth();
            }
            if (buildings.get(i) instanceof GardenPatch) {
                ((GardenPatch) buildings.get(i)).updateGrowth();
            }
        }
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
        for (int i = 0; i < plants.size(); i++) {
            if (plants.get(i).worldY > player.worldY) {
                plants.get(i).draw(g2);
            }
        }
        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).worldY > player.worldY) {
                buildings.get(i).draw(g2);
            }
            if (buildings.get(i) instanceof Orchard) {
                if (((Orchard) buildings.get(i)).phase.equals("seed") || ((Orchard) buildings.get(i)).phase.equals("sprout")) {
                    ((Orchard) buildings.get(i)).updateGrowth();
                }
            }
        }
        for(Monster monster : monsters) {
            if(monster.worldY > player.worldY) {
                monster.draw(g2);
            }
        }
        
        // Update the lighting system before drawing it
        eManager.update();
        eManager.draw(g2);
        ui.draw(g2);
        paintChildren(g);
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
