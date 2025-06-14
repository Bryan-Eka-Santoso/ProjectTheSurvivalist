package Objek.Controller;

import javax.swing.*;
import Objek.AchievementHandler.AchievementManager;
import Objek.Animal.*;
import Objek.Enemy.Monster;
import Objek.Environment.EnvironmentManager;
import Objek.Fish.Fish;
import Objek.Ore.CrystalOre;
import Objek.Ore.GemOre;
import Objek.Ore.GoldOre;
import Objek.Ore.MetalOre;
import Objek.Ore.Ore;
import Objek.Ore.Rock;
import Objek.Items.Buildings.*;
import Objek.Items.Unstackable.Immortality;
import Objek.Items.Unstackable.WinterCrown;
import Objek.Items.Unstackable.Armor.Boots.RapidBoots;
import Objek.Items.Unstackable.Armor.Chestplate.BladeArmor;
import Objek.Items.Unstackable.Armor.Helmet.CursedHelmet;
import Objek.Items.Unstackable.Armor.Helmet.GuardianHelmet;
import Objek.Items.Unstackable.Arsenals.HaasClaws;
import Objek.Items.Unstackable.Arsenals.WindAxe;
import Objek.Plant.*;
import Objek.Player.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.awt.Point;

public class GamePanel extends JPanel implements Runnable {
    final int ORI_TILE_SIZE = 15;
    final int scale = 3;

    public final int TILE_SIZE = ORI_TILE_SIZE * scale;
    final int MAX_SCREEN_COL = 25;
    final int MAX_SCREEN_ROW = 17;
    public final int SCREEN_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    final int FPS = 45;

    public final int MAX_WORLD_COL = 98;
    public final int MAX_WORLD_ROW = 98;
    public final int WORLD_WIDTH = TILE_SIZE * MAX_SCREEN_COL;
    public final int WORLD_HEIGHT = TILE_SIZE * MAX_SCREEN_ROW;
    
    public int SpawnX = 40, SpawnY = 44;
    
    KeyHandler keyH = new KeyHandler(this);
    MouseHandler mouseHandler = new MouseHandler(this);
    public Player player = new Player("Player", 15, this, keyH);
    Crafting recipe = new Crafting(this);
    public TileManager tileM = new TileManager(this);
    public UI ui = new UI(this);
    public EnvironmentManager eManager = new EnvironmentManager(this);
    Sound sound = new Sound();
    Thread gameThread;
    Spawn sp = new Spawn(this);
    public CollisonChecker cCheck = new CollisonChecker(this);
    public AchievementManager aManager = new AchievementManager();
    public InteractBuild interactBuild;
    
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
    public final int SHOP_STATE = 14;
    public final int EFFECT_STATE = 15;
    public final int ACHIEVEMENT_STATE = 16;

    private static final int MAX_CHICKENS = 12 ;
    private static final int MAX_COWS = 8;
    private static final int MAX_SHEEP = 8;
    private static final int MAX_PIGS = 8;
    private static final int MAX_WOLF = 3;
    private static final int MAX_GOLD = 15;
    private static final int MAX_CRYSTAL = 8;
    private static final int MAX_GEM = 8;
    private static final int MAX_METAL = 20;
    private static final int MAX_ROCK = 30;

    public Kandang currentKandang;
    public final int maxMap = 10;
    public int currentMap = 0;
    public boolean isCave = false;
    public boolean isAfterUnlockShip = false;
    public boolean isStrong = false;
    
    public ArrayList<Plant> plants = new ArrayList<>();
    public ArrayList<Animal> animals = new ArrayList<>();
    public ArrayList<Fish> fish = new ArrayList<>();
    public ArrayList<Monster> monsters = new ArrayList<>();
    public ArrayList<ItemDrop> droppedItems = new ArrayList<>();
    public ArrayList<Buildings> buildings = new ArrayList<>();
    public ArrayList<Ore> ores = new ArrayList<>();
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.addMouseListener(keyH);
        this.addMouseWheelListener(keyH);
        this.setFocusable(true);
        this.setLayout(null);
        this.addMouseListener(mouseHandler);
        this.interactBuild = new InteractBuild(this);
        eManager.setup();
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

     public void checkAndRespawnOres() {
        int goldCount = 0;
        int metalCount = 0;
        int crystalCount = 0;
        int gemCount = 0;
        int rockCount = 0;

        for(Ore ore : ores) {
            if(ore instanceof GoldOre) goldCount++;
            else if(ore instanceof MetalOre) metalCount++;
            else if (ore instanceof CrystalOre) crystalCount++;
            else if (ore instanceof GemOre) gemCount++;
            else if(ore instanceof Rock) rockCount++;
        }

        ArrayList<Point> usedPositions = new ArrayList<>();
        for(Ore ore : ores) {
            usedPositions.add(new Point(ore.worldX/TILE_SIZE, ore.worldY/TILE_SIZE));
        }

        if(goldCount < MAX_GOLD) {
            sp.spawnOre("gold", MAX_CHICKENS - MAX_GOLD, usedPositions);
        }
        if(metalCount < MAX_METAL) {
            sp.spawnOre("metal", MAX_METAL - metalCount, usedPositions);
        }
        if (crystalCount < MAX_CRYSTAL) {
            sp.spawnOre("crystal", MAX_CRYSTAL - crystalCount, usedPositions);
        }
        if(gemCount < MAX_GEM) {
            sp.spawnOre("gem", MAX_GEM - gemCount, usedPositions);
        }
        if(rockCount < MAX_ROCK) {
            sp.spawnOre("rock", MAX_ROCK - rockCount, usedPositions);
        }

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

    public void addPlant() {
        ArrayList<Point> usedPositions = new ArrayList<>();
        sp.spawnPlant("guava", 50, usedPositions);
        sp.spawnPlant("mango", 40, usedPositions);
        sp.spawnPlant("bush", 280, usedPositions);
        sp.spawnPlant("berrybush", 40, usedPositions);
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        checkAndRespawnAnimals();
        addPlant();

        plants.sort(Comparator.comparingInt(p -> p.worldY));
        buildings.sort(Comparator.comparingInt(p -> p.worldY));
        player.inventory.addItems(new WinterCrown());
        player.inventory.addItems(new HaasClaws());
        player.inventory.addItems(new GuardianHelmet());
        player.inventory.addItems(new BladeArmor());
        player.inventory.addItems(new RapidBoots());
        player.inventory.addItems(new Immortality());
        player.inventory.addItems(new CursedHelmet());
        player.inventory.addItems(new KandangAyam(this, 0)); 
        player.inventory.addItems(new WindAxe()); 

        Buildings shop = new Shop(this, 1, 0);
        shop.worldX = 40 * TILE_SIZE;
        shop.worldY = 40 * TILE_SIZE;
        buildings.add(shop);

        Buildings cave = new Cave(this, 1, 0);
        cave.worldX = 50 * TILE_SIZE;
        cave.worldY = 50 * TILE_SIZE;
        buildings.add(cave);

        Buildings lemariAtas = new LemariAtas(this, 1, 3);
        lemariAtas.worldX = 44 * TILE_SIZE;
        lemariAtas.worldY = 44 * TILE_SIZE;
        buildings.add(lemariAtas);

        Buildings lemariKanan = new LemariKanan(this, 1, 3);
        lemariKanan.worldX = 53 * TILE_SIZE;
        lemariKanan.worldY = 45 * TILE_SIZE;
        buildings.add(lemariKanan);

        Buildings lemariKiri = new LemariKiri(this, 1, 3);
        lemariKiri.worldX = 44 * TILE_SIZE;
        lemariKiri.worldY = 45 * TILE_SIZE;
        buildings.add(lemariKiri);

        Buildings itemTable = new ItemTable(this, 1, 3);
        itemTable.worldX = 46 * TILE_SIZE;
        itemTable.worldY = 48 * TILE_SIZE;
        buildings.add(itemTable);

        Buildings itemSell = new ItemSell(this, 1, 3);
        itemSell.worldX = 46 * TILE_SIZE + 23;
        itemSell.worldY = 47 * TILE_SIZE;
        buildings.add(itemSell);

        Buildings effectTable = new EffectTable(this, 1, 3);
        effectTable.worldX = 50 * TILE_SIZE;
        effectTable.worldY = 48 * TILE_SIZE;
        buildings.add(effectTable);

        Buildings effectSell = new EffectSell(this, 1, 3);
        effectSell.worldX = 50 * TILE_SIZE + 23;
        effectSell.worldY = 47 * TILE_SIZE;
        buildings.add(effectSell);

        Buildings pintuDalam = new PintuDalam(this, 1, 3);
        pintuDalam.worldX = 52 * TILE_SIZE + 10;
        pintuDalam.worldY = 52 * TILE_SIZE;
        buildings.add(pintuDalam);

        long interval = 500_000_000L;
        long lastAnimalMoveTime = System.nanoTime();

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
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
        if(isStrong){
            player.health = 100;
            player.hunger = 100;
            player.thirst = 100;
        }

        if (player.health > 100) player.health = 100;

        if (gameState == PAUSE_STATE || gameState == ACHIEVEMENT_STATE) return;
        
        if (player.health <= 0) {
            gameState = GAME_OVER_STATE;
            if (!player.inventory.hasItem("Immortality")) {
                if (!player.inventory.isEmpty()) 
                    player.dropAllItems();
    
                player.health = 0;
                player.daysAlive = 0;
                eManager.lighting.filterAlpha = eManager.lighting.filterAlphaTemp;
            }
            
        } else {
            player.update();
        }
        
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
            }
        }

        ui.isCanGoToSea = false;
        ui.isCanGoToLand = false;
        ui.isNeedLevel15 = false;
        ui.isCanGoToCave = false;
        ui.isCanGoToShop = false;
        ui.isGoToShopMenu = false;
        ui.isGoToEffectMenu = false;
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
            if (col == 40 && row == 41) {
                ui.isCanGoToShop = true;
            }
        } else if(currentMap == 1){
            if (col == 60 && row == 25) {
                ui.isCanGoToLand = true;
            }
        } else if(currentMap == 2){
            if((col == 22 || col == 23) && row == 23) {
                ui.isCanGoToLand = true;
            }
        } else if(currentMap == 3){
            if (col == 52 && row == 53) {
                ui.isCanGoToLand = true;
            }
            if(col == 46 && row == 48) {
                ui.isGoToShopMenu = true;
            }
            if(col == 50 && row == 48) {
                ui.isGoToEffectMenu = true;
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
        for (int i = 0; i < ores.size(); i++) {
            if (ores.get(i).worldY <= player.worldY) {
                ores.get(i).draw(g2);
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
                    buildings.sort(Comparator.comparingInt(p -> p.worldY));
                }
            }
        }
        for(Monster monster : monsters) {
            if(monster.worldY > player.worldY) {
                monster.draw(g2);
            }
        }
        for (int i = 0; i < ores.size(); i++) {
            if (ores.get(i).worldY > player.worldY) {
                ores.get(i).draw(g2);
            }
        }
        
        // Update the lighting system before drawing it
        eManager.update();
        eManager.draw(g2);
        aManager.checkAll(this);
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
