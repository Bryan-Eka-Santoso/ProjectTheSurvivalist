package Objek.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.awt.Point;
import javax.imageio.ImageIO;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Foods.CookedFoods.Bacon;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedArwana;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedBelida;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedChicken;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedMutton;
import Objek.Items.StackableItem.Foods.CookedFoods.Steak;
import Objek.Items.StackableItem.Foods.Fruits.Guava;
import Objek.Items.StackableItem.Foods.Fruits.Mango;
import Objek.Items.StackableItem.Foods.Fruits.RaspBerries;
import Objek.Items.StackableItem.Foods.Other.BakedPotato;
import Objek.Items.StackableItem.Foods.Other.Bread;
import Objek.Items.StackableItem.Foods.Other.Carrot;
import Objek.Items.StackableItem.Foods.Other.Egg;
import Objek.Items.StackableItem.Foods.RawFoods.Blackberries;
import Objek.Items.StackableItem.Foods.RawFoods.Potato;
import Objek.Items.StackableItem.Foods.RawFoods.RawArwana;
import Objek.Items.StackableItem.Foods.RawFoods.RawBelida;
import Objek.Items.StackableItem.Foods.RawFoods.RawChicken;
import Objek.Items.StackableItem.Foods.RawFoods.RawMeat;
import Objek.Items.StackableItem.Foods.RawFoods.RawPork;
import Objek.Items.StackableItem.Materials.Strings;
import Objek.Items.StackableItem.Materials.Wheat;
import Objek.Items.StackableItem.Materials.AnimalDrops.Feather;
import Objek.Items.StackableItem.Materials.AnimalDrops.WolfHide;
import Objek.Items.StackableItem.Materials.AnimalDrops.Wool;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalFrame;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalNails;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalSheet;
import Objek.Items.StackableItem.Materials.ForgedComponents.SwordHandle;
import Objek.Items.StackableItem.Materials.ForgedComponents.ToolHandle;
import Objek.Items.StackableItem.Materials.Fuels.Wood;
import Objek.Items.StackableItem.Materials.OreDrops.Gold;
import Objek.Items.StackableItem.Materials.OreDrops.Metal;
import Objek.Items.StackableItem.Materials.OreDrops.Stone;
import Objek.Items.Unstackable.*;
import Objek.Items.Unstackable.Armor.Boots.GoldBoots;
import Objek.Items.Unstackable.Armor.Boots.MetalBoots;
import Objek.Items.Unstackable.Armor.Boots.RapidBoots;
import Objek.Items.Unstackable.Armor.Chestplate.BladeArmor;
import Objek.Items.Unstackable.Armor.Chestplate.GoldChestplate;
import Objek.Items.Unstackable.Armor.Chestplate.MetalChestplate;
import Objek.Items.Unstackable.Armor.Helmet.CursedHelmet;
import Objek.Items.Unstackable.Armor.Helmet.GoldHelmet;
import Objek.Items.Unstackable.Armor.Helmet.GuardianHelmet;
import Objek.Items.Unstackable.Armor.Helmet.MetalHelmet;
import Objek.Items.Unstackable.Armor.Helmet.WolfCloak;
import Objek.Items.Unstackable.Armor.Leggings.GoldLeggings;
import Objek.Items.Unstackable.Armor.Leggings.MetalLeggings;
import Objek.Items.Unstackable.Arsenals.FlimsyAxe;
import Objek.Items.Unstackable.Arsenals.FlimsyPickaxe;
import Objek.Items.Unstackable.Arsenals.GoldSword;
import Objek.Items.Unstackable.Arsenals.HaasClaws;
import Objek.Items.Unstackable.Arsenals.IcePickaxe;
import Objek.Items.Unstackable.Arsenals.LightweightAxe;
import Objek.Items.Unstackable.Arsenals.LightweightPickaxe;
import Objek.Items.Unstackable.Arsenals.MetalClub;
import Objek.Items.Unstackable.Arsenals.MetalSword;
import Objek.Items.Unstackable.Arsenals.SpikedMetalClub;
import Objek.Items.Unstackable.Arsenals.SpikedWoodenClub;
import Objek.Items.Unstackable.Arsenals.WindAxe;
import Objek.Items.Unstackable.Arsenals.WoodenClub;

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
            playSE(15); // Sound for opening chest
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
                    gp.buildings.get(gp.player.buildingIndex).img = ImageIO.read(getClass().getResource("/res/Items/Buildings/SleepingPlayer.png"));
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
            gp.tileM.loadMap("/res/world/cave.txt", 2);
            gp.currentMap = 2;
            gp.animals.clear();
            gp.stopMusic();
            gp.playMusic(27);
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 24 * gp.TILE_SIZE;
            gp.player.worldX = 23 * gp.TILE_SIZE;
            gp.isCave = !gp.isCave;
            gp.eManager.lighting.setLightSource(); 
            gp.player.buildingIndex = -1;
            gp.sp.spawnMonster("bat", 20, usedPositions);
            gp.sp.spawnMonster("golem",8, usedPositions);
            gp.checkAndRespawnOres();
        }
        else if (building instanceof Shop) {
            gp.tileM.loadMap("/res/world/shop.txt", 3);
            gp.currentMap = 3;
            gp.animals.clear();
            gp.stopMusic();
            gp.playMusic(28);
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 53 * gp.TILE_SIZE;
            gp.player.worldX = 52 * gp.TILE_SIZE;
            gp.eManager.lighting.setLightSource(); 
            gp.player.buildingIndex = -1;
        }
        else if (building instanceof ItemTable) {
            gp.gameState = gp.SHOP_STATE;
            gp.ui.shopItems.clear();
            initShopItems();
        }
        else if(building instanceof EffectTable){
            gp.gameState = gp.EFFECT_STATE;
            initEffectItems();
        }
    }

    public void initShopItems() {
        try {
            // Tools (category 0)
            gp.ui.shopItems.add(new ShopItem(new WoodenClub(), 25, 0));
            gp.ui.shopItems.add(new ShopItem(new SpikedWoodenClub(), 40, 0));
            gp.ui.shopItems.add(new ShopItem(new MetalClub(), 60, 0));
            gp.ui.shopItems.add(new ShopItem(new SpikedMetalClub(), 85, 0));
            gp.ui.shopItems.add(new ShopItem(new FlimsyPickaxe(), 35, 0));
            gp.ui.shopItems.add(new ShopItem(new LightweightPickaxe(), 55, 0));
            gp.ui.shopItems.add(new ShopItem(new IcePickaxe(), 70, 0));
            gp.ui.shopItems.add(new ShopItem(new GoldSword(), 80, 0));
            gp.ui.shopItems.add(new ShopItem(new MetalSword(), 75, 0));
            gp.ui.shopItems.add(new ShopItem(new FlimsyAxe(), 30, 0));
            gp.ui.shopItems.add(new ShopItem(new LightweightAxe(), 50, 0));
            gp.ui.shopItems.add(new ShopItem(new WindAxe(), 90, 0));
            gp.ui.shopItems.add(new ShopItem(new Lantern(gp), 45, 0));
            gp.ui.shopItems.add(new ShopItem(new WateringCan(), 20, 0));
            gp.ui.shopItems.add(new ShopItem(new FishingRod(), 35, 0));
            
            // Armor (category 1)
            gp.ui.shopItems.add(new ShopItem(new GoldBoots(), 60, 1));
            gp.ui.shopItems.add(new ShopItem(new MetalBoots(), 50, 1));
            gp.ui.shopItems.add(new ShopItem(new GoldLeggings(), 75, 1));
            gp.ui.shopItems.add(new ShopItem(new MetalLeggings(), 65, 1));
            gp.ui.shopItems.add(new ShopItem(new GoldChestplate(), 90, 1));
            gp.ui.shopItems.add(new ShopItem(new MetalChestplate(), 80, 1));
            gp.ui.shopItems.add(new ShopItem(new GoldHelmet(), 70, 1));
            gp.ui.shopItems.add(new ShopItem(new MetalHelmet(), 60, 1));
            gp.ui.shopItems.add(new ShopItem(new WolfCloak(), 150, 1));
            
            // Food (category 2)
            gp.ui.shopItems.add(new ShopItem(new Bacon(1), 12, 2));
            gp.ui.shopItems.add(new ShopItem(new CookedArwana(1), 18, 2));
            gp.ui.shopItems.add(new ShopItem(new CookedBelida(1), 17, 2));
            gp.ui.shopItems.add(new ShopItem(new CookedChicken(1), 15, 2));
            gp.ui.shopItems.add(new ShopItem(new CookedMutton(1), 16, 2));
            gp.ui.shopItems.add(new ShopItem(new Steak(1), 20, 2));
            gp.ui.shopItems.add(new ShopItem(new Guava(1), 6, 2));
            gp.ui.shopItems.add(new ShopItem(new Mango(1), 7, 2));
            gp.ui.shopItems.add(new ShopItem(new RaspBerries(1), 8, 2));
            gp.ui.shopItems.add(new ShopItem(new BakedPotato(1), 10, 2));
            gp.ui.shopItems.add(new ShopItem(new Bread(1), 9, 2));
            gp.ui.shopItems.add(new ShopItem(new Carrot(1), 5, 2));
            gp.ui.shopItems.add(new ShopItem(new Egg(1), 4, 2));
            gp.ui.shopItems.add(new ShopItem(new Blackberries(1), 8, 2));
            gp.ui.shopItems.add(new ShopItem(new Potato(1), 4, 2));
            gp.ui.shopItems.add(new ShopItem(new RawArwana(1), 10, 2));
            gp.ui.shopItems.add(new ShopItem(new RawBelida(1), 9, 2));
            gp.ui.shopItems.add(new ShopItem(new RawChicken(1), 8, 2));
            gp.ui.shopItems.add(new ShopItem(new RawMeat(1), 7, 2));
            gp.ui.shopItems.add(new ShopItem(new RawPork(1), 7, 2));
            
            // Materials (category 3)
            gp.ui.shopItems.add(new ShopItem(new Feather(1), 3, 3));
            gp.ui.shopItems.add(new ShopItem(new WolfHide(1), 12, 3));
            gp.ui.shopItems.add(new ShopItem(new Wool(1), 10, 3));
            gp.ui.shopItems.add(new ShopItem(new MetalFrame(1), 20, 3));
            gp.ui.shopItems.add(new ShopItem(new MetalNails(1), 3, 3));
            gp.ui.shopItems.add(new ShopItem(new MetalSheet(1), 15, 3));
            gp.ui.shopItems.add(new ShopItem(new SwordHandle(1), 13, 3));
            gp.ui.shopItems.add(new ShopItem(new ToolHandle(1), 11, 3));
            gp.ui.shopItems.add(new ShopItem(new Wood(1), 2, 3));
            gp.ui.shopItems.add(new ShopItem(new Gold(1), 22, 3));
            gp.ui.shopItems.add(new ShopItem(new Metal(1), 7, 3));
            gp.ui.shopItems.add(new ShopItem(new Stone(1), 4, 3));
            gp.ui.shopItems.add(new ShopItem(new Strings(1), 6, 3));
            gp.ui.shopItems.add(new ShopItem(new Wheat(1), 6, 3));

            // Legendary Items (category 4)
            gp.ui.shopItems.add(new ShopItem(new RapidBoots(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new HaasClaws(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new Immortality(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new GuardianHelmet(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new WinterCrown(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new BladeArmor(), 200, 4));
            gp.ui.shopItems.add(new ShopItem(new CursedHelmet(), 200, 4));
            
        } catch (Exception e) {
            System.err.println("Error initializing shop items: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void initEffectItems() {
        try {
            gp.ui.effectItems.clear();
            for(int i = 0; i < gp.player.inventory.slots.length; i++) {
                if(gp.player.inventory.slots[i] != null && gp.player.inventory.slots[i].isCanSell) {
                    gp.ui.effectItems.add(new ShopEffect(gp.player.inventory.slots[i].name, gp.player.inventory.slots[i].img, gp.player.inventory.slots[i].price, 0));
                }
            }
            
            gp.ui.effectItems.add(new ShopEffect("Repair Arsenal", "/res/Items/Equipments/sword.png", 10, 1));
            gp.ui.effectItems.add(new ShopEffect("Repair Armor", "/res/Items/Armor/metalchestplate2.png", 10, 1));
            gp.ui.effectItems.add(new ShopEffect("Repair Fishing Rod", "/res/Items/Equipments/fishingrod.png", 10, 1));
            
            gp.ui.effectItems.add(new ShopEffect("Upgrade Level", "/res/Items/Effect/uplevel.png", 0, 2));
            gp.ui.effectItems.add(new ShopEffect("Coins +999", "/res/Items/Effect/addgold.png", 0, 2));
            if(!gp.isStrong){
                gp.ui.effectItems.add(new ShopEffect("God Mode", "/res/Items/Effect/godmode.png", 0, 2));
            } else {
                gp.ui.effectItems.add(new ShopEffect("Normal Mode", "/res/Items/Effect/normalmode.png", 0, 2));
            }
            // gp.ui.effectItems.add(new ShopEffect("Extra Coins", "ProjectTheSurvivalist/res/Items/Armor/metalchestplate.png", 500, 4));
            // gp.ui.effectItems.add(new ShopEffect("Max Health", "ProjectTheSurvivalist/res/Items/Armor/metalchestplate.png", 1000, 4));
            
            System.out.println("Effect items initialized successfully");
        } catch (Exception e) {
            System.err.println("Error initializing effect items: " + e.getMessage());
            e.printStackTrace();
        }
    }

public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
    
}