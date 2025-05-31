package Objek.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Point;
import javax.imageio.ImageIO;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Bucket;
import Objek.Items.StackableItem.Foods.Bread;
import Objek.Items.StackableItem.Foods.Carrot;
import Objek.Items.StackableItem.Foods.Potato;
import Objek.Items.StackableItem.Materials.Metal;
import Objek.Items.StackableItem.Materials.Stone;
import Objek.Items.StackableItem.Materials.Fuels.Wood;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.FishingRod;
import Objek.Items.Unstackable.Arsenals.Axe;
import Objek.Items.Unstackable.Arsenals.Pickaxe;
import Objek.Items.Unstackable.Arsenals.Sword;
import Objek.Items.*;
import Objek.Controller.ShopItem;

public class InteractBuild {
    GamePanel gp;
    Sound sound = new Sound();

    public InteractBuild(GamePanel gp) {
        this.gp = gp;
    }

    public void interact() {
        if (gp.player.buildingIndex < 0 || gp.player.buildingIndex >= gp.buildings.size()) {
            System.out.println("Invalid building index: " + gp.player.buildingIndex);
            return;
        }
        
        Buildings building = (Buildings) gp.buildings.get(gp.player.buildingIndex);
        
        if (building instanceof Chest) {
            gp.gameState = gp.OPEN_CHEST_STATE;
        }
        else if (building instanceof CraftingTable) {
            gp.gameState = gp.OPEN_CRAFTINGTABLE_STATE;
        }
        else if (building instanceof Furnace) {
            gp.gameState = gp.OPEN_SMELTER_STATE;
        }
        else if (building instanceof Bed) {
            if (gp.eManager.lighting.dayState == 2) {
                gp.eManager.lighting.dayCounter = 580;
                gp.player.isSleeping = true;
                gp.SpawnX = gp.player.worldX / gp.TILE_SIZE;
                gp.SpawnY = gp.player.worldY / gp.TILE_SIZE;
                try {
                    gp.buildings.get(gp.player.buildingIndex).img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/SleepingPlayer.png"));
                    gp.player.isSleeping = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Selamat pagi");
            } else {
                System.out.println("Masih pagi kerja");
            }
        }
        else if (building instanceof Cave) {
            ArrayList<Point> usedPositions = new ArrayList<>();
            gp.tileM.loadMap("ProjectTheSurvivalist/res/world/cave.txt", 2);
            gp.currentMap = 2;
            gp.animals.clear();
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 24 * gp.TILE_SIZE;
            gp.player.worldX = 23 * gp.TILE_SIZE;
            gp.isCave = !gp.isCave;
            gp.eManager.lighting.setLightSource(); 
            gp.player.buildingIndex = -1;
            gp.sp.spawnMonster("bat", 5, usedPositions);
            gp.sp.spawnMonster("golem", 3, usedPositions);
            gp.checkAndRespawnOres();
        }
        else if (building instanceof Shop) {
            gp.tileM.loadMap("ProjectTheSurvivalist/res/world/shop.txt", 3);
            gp.currentMap = 3;
            gp.animals.clear();
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 53 * gp.TILE_SIZE;
            gp.player.worldX = 52 * gp.TILE_SIZE;
            gp.eManager.lighting.setLightSource(); 
            gp.player.buildingIndex = -1;
        }
        else if (building instanceof Table) {
            // Open shop UI when player interacts with table
            gp.gameState = gp.SHOP_STATE;
            System.out.println("Game state set to: " + gp.gameState + ", SHOP_STATE is: " + gp.SHOP_STATE);
            
            if (gp.ui.shopItems == null) {
                gp.ui.shopItems = new ArrayList<>();
                System.out.println("Created new shopItems ArrayList");
            }
            if (gp.ui.shopItems.isEmpty()) {
                initShopItems();
            }
            System.out.println("Opening shop menu with " + gp.ui.shopItems.size() + " items");
        }
    }

    public void initShopItems() {
        try {
            // Weapons (category 0)
            // gp.ui.shopItems.add(new ShopItem(new Sword(1), 100, 0));
            // gp.ui.shopItems.add(new ShopItem(new Axe(1), 120, 0));
            // gp.ui.shopItems.add(new ShopItem(new Pickaxe(1), 150, 0));
            gp.ui.shopItems.add(new ShopItem(new FishingRod(), 80, 0));
            
            // Armor (category 1)
            // gp.ui.shopItems.add(new ShopItem(new LeatherHelmet(), 60, 1));
            // gp.ui.shopItems.add(new ShopItem(new LeatherChestplate(), 100, 1));
            // gp.ui.shopItems.add(new ShopItem(new IronHelmet(), 150, 1));
            // gp.ui.shopItems.add(new ShopItem(new IronChestplate(), 200, 1));
            
            // Food (category 2)
            gp.ui.shopItems.add(new ShopItem(new Bread(2), 20, 2));
            gp.ui.shopItems.add(new ShopItem(new Carrot(5), 25, 2));
            gp.ui.shopItems.add(new ShopItem(new Potato(4), 20, 2));
            
            // Materials (category 3)
            gp.ui.shopItems.add(new ShopItem(new Wood(10), 30, 3));
            gp.ui.shopItems.add(new ShopItem(new Stone(10), 40, 3));
            gp.ui.shopItems.add(new ShopItem(new Metal(5), 70, 3));
            gp.ui.shopItems.add(new ShopItem(new Seeds(3), 15, 3));
            
            // Fishing (category 4)
            gp.ui.shopItems.add(new ShopItem(new FishingRod(), 100, 4));
            gp.ui.shopItems.add(new ShopItem(new Bucket(1, gp), 50, 4));
            
            System.out.println("Shop items initialized successfully");
        } catch (Exception e) {
            System.err.println("Error initializing shop items: " + e.getMessage());
            e.printStackTrace();
        }
    }
}