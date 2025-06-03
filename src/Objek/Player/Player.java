package Objek.Player;

import javax.imageio.ImageIO;
import Objek.Animal.Animal;
import Objek.Animal.TameAnimal;
import Objek.Controller.GamePanel;
import Objek.Controller.InteractBuild;
import Objek.Controller.ItemDrop;
import Objek.Controller.KeyHandler;
import Objek.Controller.UseItem;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Stackable;
import Objek.Items.Unstackable.Armor.Boots.Boots;
import Objek.Items.Unstackable.Armor.Chestplate.Chestplate;
import Objek.Items.Unstackable.Armor.Helmet.Helmet;
import Objek.Items.Unstackable.Armor.Leggings.Leggings;
import Objek.Plant.Bush;
import Objek.Plant.Plant;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    public String name;
    public int health, thirst, hunger, exp, level;
    public int maxHealth = 100, maxThirst = 100, maxHunger = 100;
    public int maxExp = 100;
    public Inventory inventory;
    public int itemIndex; 
    public int worldX, worldY, speed, solidAreaX, solidAreaY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public BufferedImage cutup1, cutup2, cutdown1, cutdown2, cutleft1, cutleft2, cutright1, cutright2;
    public BufferedImage sleep, stay;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String lastMove;
    public TameAnimal grabbedAnimal;
    public Crafting recipe;
    public int solidAreaDefaultX, solidAreaDefaultY; 
    public boolean lightUpdated = true;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public boolean isBuild = false; 
    public UseItem interactObj;
    public final int SCREEN_Y;
    public final int SCREEN_X;
    public int plantIndex, animalIndex, droppedItem, buildingIndex, fishIndex, monsterIndex,oreIndex;
    public GamePanel gp;
    public KeyHandler keyH;
    public Boolean isCutting;
    public Boolean isSlash;
    public Boolean isSleeping;
    public Rectangle cutArea = new Rectangle(0, 0, 0, 0);
    public InteractBuild interactBuild;
    public int strengthRod = 30;
    public Helmet helmet; // Array to hold helmets
    public Chestplate chestplate; // Array to hold chestplates
    public Leggings leggings; // Array to hold leggings
    public Boots boots; // Array to hold boots
    private boolean isPoisoned = false;
    private static final long POISON_DURATION = 300; // 5 detik diitung dari framenya
    private static final int POISON_DAMAGE = 1;
    private static final long HUNGER_DECREASE_INTERVAL = 420; // 7 detik diitung dari frame
    private static final long THIRST_DECREASE_INTERVAL = 300; // 5 detik diitung dari frame
    private static final long HEALTH_REGEN_INTERVAL = 180; // 3 detik diitung dari frame
    private static final int DEHYDRATION_DAMAGE_INTERVAL = 120; // 2 detik diitung dari frame
    int hungerCounter = 0;
    int thirstCounter = 0;
    public int coins = 0;
    int healthCounter = 0;
    private int poisonCounter = 0;
    private boolean isDehydrated = false;
    private int dehydrationCounter = 0;


    public Player(String name, int level, GamePanel gp, KeyHandler keyH) {
        this.name = name;
        this.worldX = gp.TILE_SIZE * gp.SpawnX;
        this.worldY = gp.TILE_SIZE * gp.SpawnY;
        this.health = 100;
        this.thirst = 100;
        this.hunger = 100;
        this.exp = 0;
        this.level = level;
        this.speed = 5;
        this.inventory = new Inventory(24, gp);
        this.gp = gp;
        this.keyH = keyH;
        this.grabbedAnimal= null; 
        this.direction = "down";
        this.solidArea = new Rectangle(0, 0, 17, 25);
        solidAreaX = solidArea.x;
        solidAreaY = solidArea.y;
        solidArea.x = 12;
        solidArea.y = 20;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        solidArea.width = 24;
        solidArea.height = 24;
        this.collisionOn = false;
        System.out.println(gp.SCREEN_WIDTH + " " + gp.SCREEN_HEIGHT);
        SCREEN_X = gp.SCREEN_WIDTH / 2 - gp.TILE_SIZE / 2;
        SCREEN_Y = gp.SCREEN_HEIGHT / 2 - gp.TILE_SIZE / 2;
        this.recipe = new Crafting(gp);
        interactObj = new UseItem(gp);
        interactBuild = new InteractBuild(gp);
        this.isCutting = false;
        this.isSlash = false;
        this.isSleeping = false;
        getPlayerImg();
        getPlayerCutImg();
        this.helmet = null;
        this.chestplate = null;
        this.leggings = null;
        this.boots = null;
        cutArea.width = 36;
        cutArea.height = 36;
    }
    
    public void getPlayerImg() {
        try {
            if(gp.currentMap == 1){
                left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleftwater1.png"));
                left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleftwater2.png"));
                right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkrightwater1.png"));
                right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkrightwater2.png"));
                up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkupwater1.png"));
                up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkupwater2.png"));
                down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdownwater1.png"));
                down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdownwater2.png"));
            } else {
                left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleft1.png"));
                left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleft2.png"));
                right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkright1.png"));
                right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkright2.png"));
                up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkup1.png"));
                up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkup2.png"));
                down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdown1.png"));
                down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdown2.png"));
            }
            sleep = ImageIO.read(new File("ProjectTheSurvivalist/res/player/none.png"));
            stay = ImageIO.read(new File("ProjectTheSurvivalist/res/player/stay.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void getPlayerCutImg() {
        try {
            cutup1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutup.png"));
            cutup2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutup.png"));
            cutdown1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutdown.png"));
            cutdown2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutdown.png"));
            cutleft1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutleft.png"));
            cutleft2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutleft.png"));
            cutright1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutright.png"));
            cutright2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/cutright.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gainExp(int amount) {
        exp += amount;
        if (exp >= maxExp) {
            exp -= maxExp;
            level++;
            maxExp += 50;
            System.out.println("Level up! Current level: " + level);
        }
        if (level > 10) {
            // TODO: Implement benefits of leveling up
        } else if (level > 20) {
            // TODO: Implement benefits of leveling up
        }
    }

    public int getDefense() {
        int defense = 0;
        if (helmet != null) {
            defense += helmet.defense;
        }
        if (chestplate != null) {
            defense += chestplate.defense;
        }
        if (leggings != null) {
            defense += leggings.defense;
        }
        if (boots != null) {
            defense += boots.defense;
        }
        return defense;
    }
    private void handlePoisonEffect() {
        if(isPoisoned) {
            poisonCounter++;
            
            // Damage setiap 60 frames (1 detik)
            if(poisonCounter % 60 == 0) {
                if(health > 1) { // Check to prevent death
                    health =  health - POISON_DAMAGE;
                }
            }
            
            // Cek apakah efek poison selesai
            if(poisonCounter >= POISON_DURATION) {
                isPoisoned = false;
                poisonCounter = 0;
            }
        }
    }

    public Boolean isNearWater(){
        int newX = gp.player.worldX, newY = gp.player.worldY; 
        switch (gp.player.direction) {
            case "up":
                newY = gp.player.worldY - gp.TILE_SIZE;  
                break;
            case "down":
                newY = gp.player.worldY + gp.TILE_SIZE;  
                break;
            case "left":
                newX = gp.player.worldX - gp.TILE_SIZE;  
                break;
            case "right":
                newX = gp.player.worldX + gp.TILE_SIZE;  
                break;
        }
        int tileNum = gp.tileM.mapTile[gp.currentMap][newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
        return tileNum == 16; // Assuming tile number 16 is water
    }

    public void setPoisoned() {
        isPoisoned = true;
        poisonCounter = 0;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }
    private void handleDehydrationEffect() {
        if(thirst < 10) {
            isDehydrated = true;
            dehydrationCounter++;
            
            // Damage every 2 seconds (120 frames)
            if(dehydrationCounter >= DEHYDRATION_DAMAGE_INTERVAL) {
                if(health > 1) { // Check to prevent death
                    health -= 1;
                }
                dehydrationCounter = 0;
            }
        } else {
            isDehydrated = false;
            dehydrationCounter = 0;
        }
    }

    public boolean isDehydrated() {
        return isDehydrated;
    }

    public void update() {
        
        if (keyH.shiftPressed && hunger > 20 && thirst >= 10 && gp.currentMap != 1) {
            speed = 10;
            if(hungerCounter >= HUNGER_DECREASE_INTERVAL/4) {
                if(hunger > 0) {
                    hunger--;
                }
                hungerCounter = 0;
            }
            if(thirstCounter >= THIRST_DECREASE_INTERVAL/4) {
                if(thirst > 0) {
                    thirst--;
                }
                thirstCounter = 0;
            }
        } else {
            speed = 5;
            if(hungerCounter >= HUNGER_DECREASE_INTERVAL) {
                if(hunger > 0) {
                    hunger--;
                }
                hungerCounter = 0;
            }
            if(thirstCounter >= THIRST_DECREASE_INTERVAL) {
                thirst--;
                if(thirst > 0) {
                }
                thirstCounter = 0;
            }
        }

         if (this.hunger >= 75 || this.thirst >= 75) {
            healthCounter++;
            if (healthCounter >= HEALTH_REGEN_INTERVAL) {
                if (health < maxHealth) {
                    health++;
                }
                healthCounter = 0;
            }
        }

        thirstCounter++;
        hungerCounter++;
        
        if(isPoisoned) {
            handlePoisonEffect();
        }
        handleDehydrationEffect();
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }
            
            collisionOn = false;
            gp.cCheck.checkTile(this);
            
            
            droppedItem = gp.cCheck.checkItemDrop(this, true);
            if (gp.currentMap == 1) {
                fishIndex = gp.cCheck.checkFish(this, true);
            } else if (gp.currentMap == 2) {
                monsterIndex = gp.cCheck.checkMonsters(this, true);
                oreIndex = gp.cCheck.checkOre(this, true);
            } else if (gp.currentMap == 0) {
                monsterIndex = gp.cCheck.checkMonsters(this, true);
                animalIndex = gp.cCheck.checkAnimal(this, true);
                buildingIndex = gp.cCheck.checkBuildings(this, true);
                plantIndex = gp.cCheck.checkPlant(this, true);
            } else if (gp.currentMap == 3) {
                buildingIndex = gp.cCheck.checkBuildings(this, true);
            }
            
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            updateGrabbedAnimalPosition();
            spriteCounter++;
            if (spriteCounter > 10) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (health <= 0) {
            image = down1;
        } else if (!isCutting) {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) image = up1;
                    if (spriteNum == 2) image = up2;
                    break;
                case "down":
                    if (spriteNum == 1) image = down1;
                    if (spriteNum == 2) image = down2;
                    break;
                case "left":
                    if (spriteNum == 1) image = left1;
                    if (spriteNum == 2) image = left2;
                    break;
                case "right":
                    if (spriteNum == 1) image = right1;
                    if (spriteNum == 2) image = right2;
                    break;
            }
        } else {
            switch (direction) {
                case "up":
                    if (spriteNum == 1) image = cutup1;
                    if (spriteNum == 2) image = cutup2;
                    break;
                case "down":
                    if (spriteNum == 1) image = cutdown1;
                    if (spriteNum == 2) image = cutdown2;
                    break;
                case "left":
                    if (spriteNum == 1) image = cutleft1;
                    if (spriteNum == 2) image = cutleft2;
                    break;
                case "right":
                    if (spriteNum == 1) image = cutright1;
                    if (spriteNum == 2) image = cutright2;
                    break;
            }
        }
        if (isSleeping) {
            image = sleep;
        }
        
        g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE + 8, null);
        if (grabbedAnimal != null) {
            
            BufferedImage animalImg = grabbedAnimal.getDirectionalImage();
            int animalDrawX = SCREEN_X + grabbedAnimal.getGrabOffsetX();
            int animalDrawY = SCREEN_Y + grabbedAnimal.getGrabOffsetY();
            
            g2.drawImage(animalImg, animalDrawX, animalDrawY,grabbedAnimal.getWidth(), grabbedAnimal.getHeight(), null);
            
        }
    }

    public void cutting(){
       
        spriteCounter++;
        if(spriteCounter == 1) {
            spriteNum = 1;
            isCutting = true;
        }
        
        if(spriteCounter == 2) {
            spriteNum = 2;
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;
        
            switch(direction){
                case "up":
                    worldY -= cutArea.height;
                    break;
                case "down":
                    worldY += cutArea.height;
                    break;
                case "left":
                    worldX -= cutArea.width;
                    break;
                case "right":
                    worldX += cutArea.width;
                    break;
            }
            
            solidArea.width = cutArea.width;
            solidArea.height = cutArea.height;

            plantIndex = gp.cCheck.checkPlant(this, true);
            animalIndex = gp.cCheck.checkAnimal(this, true);
            
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        
        if(spriteCounter > 2){
            spriteNum = 1;
            spriteCounter = 0;
            isCutting = false;
        }
    }
    
    private void updateGrabbedAnimalPosition() {
        if (grabbedAnimal != null) {
           
            grabbedAnimal.worldX = worldX;
            grabbedAnimal.worldY = worldY;
           
            grabbedAnimal.direction = direction;
           
            grabbedAnimal.spriteNum = spriteNum;
        }
    }

    public void handleGrabAction(Item selectedItem) {
        if (grabbedAnimal == null) {
            TameAnimal nearbyAnimal = findNearbyAnimal();
            if (nearbyAnimal != null) {
                grabAnimal(nearbyAnimal, selectedItem);
            }
        } else {  
            unGrabAnimal();
        }
    }

    public TameAnimal findNearbyAnimal() {
        int playerCol = worldX / gp.TILE_SIZE;
        int playerRow = worldY / gp.TILE_SIZE;
        for (Animal animal : gp.animals){
            if (animal instanceof TameAnimal) {
                int animalCol = animal.worldX / gp.TILE_SIZE;
                int animalRow = animal.worldY / gp.TILE_SIZE;
                if (Math.abs(playerCol - animalCol) <= 1 && Math.abs(playerRow - animalRow) <= 1) {
                    return (TameAnimal) animal;
                }
            }
        }
        return null;
    }

    public void grabAnimal(TameAnimal animal, Item selectedItem) {
    
        if (selectedItem != null) {
            System.out.println("Cannot grab animal while holding an item!");
            return;
        }
        if (grabbedAnimal != null) {
            System.out.println("Already holding an animal!");
            return;
        }

        grabbedAnimal = animal;

        gp.animals.remove(animal);
        updateGrabbedAnimalPosition();
        grabbedAnimal.grab();
        System.out.println("Grabbed " + animal.getName());
        
    }

    public void unGrabAnimal() {
        if (grabbedAnimal != null){
            for(Buildings building : gp.buildings) {
                if(building instanceof Kandang) {
                    if(Math.abs(worldX - building.worldX) <= gp.TILE_SIZE && 
                    Math.abs(worldY - building.worldY) <= gp.TILE_SIZE) {   
                        Kandang kandang = (Kandang)building;
                        if(kandang.getCurrentCapacity() >= kandang.getMaxCapacity()) {
                            // Show kandang full message
                            gp.ui.showKandangFullMessage();
                            return;
                        }
                        gp.ui.showAnimalNameInput(grabbedAnimal, kandang);
                        return;
                    }
                }
            }
            int newX = worldX, newY = worldY;   
            switch (direction) {
                case "up":
                    newY = worldY - gp.TILE_SIZE;
                    break;
                case "down":
                    newY = worldY + gp.TILE_SIZE;
                    break;
                case "left":
                    newX = worldX - gp.TILE_SIZE;
                    break;
                case "right":
                    newX = worldX + gp.TILE_SIZE;
                    break;
            }
            if(newX < 0 || newX >= gp.MAX_WORLD_COL * gp.TILE_SIZE || 
                newY < 0 || newY >= gp.MAX_WORLD_ROW * gp.TILE_SIZE) {
                    System.out.println("Cannot place animal outside map bounds!");
                    return;
            }
            int tileNum = gp.tileM.mapTile[gp.currentMap][newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
            if (tileNum != 8 && tileNum != 9 && tileNum != 10 && tileNum != 11 && 
                tileNum != 12 && tileNum != 13 && tileNum != 14 && tileNum != 15 && 
                tileNum != 18 && tileNum != 20) {
                System.out.println("Cannot place animal on this type of tile!");
                return;
            }
            boolean canPlace = true;
            grabbedAnimal.worldX = newX;
            grabbedAnimal.worldY = newY;
            for (Animal other : gp.animals) {
                if (Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
                    Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                    canPlace = false;
                    break;
                }
            }
            for (Plant other : gp.plants) {
                if (other instanceof Bush){
                    continue;
                }
                if (Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
                Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                    canPlace = false;
                    break;
                }
            }
            if (canPlace) {
                grabbedAnimal.worldX = newX;
                grabbedAnimal.worldY = newY;
                grabbedAnimal.unGrab();
                gp.animals.add(grabbedAnimal);
                System.out.println("Placed " + grabbedAnimal.getName() + " at (" + newX + ", " + newY + ")");
                grabbedAnimal = null;
            } else {
                System.out.println("Cannot place animal here!");
            }
        }
    }

    public boolean isHoldingAnimal() {
        return grabbedAnimal != null;
    }

    public void useItem(Item selectedItem) {
        if (isHoldingAnimal()) {
            System.out.println("Cannot use items while holding an animal!");
            return;
        }
        interactObj.useItem(selectedItem, this);
    }

    public void interactBuild(Buildings selectedBuilding) {
        if (selectedBuilding != null) {
            interactBuild.interact();
        } else {
            System.out.println("No building selected.");
        }

    }

    public void pickUpItem(Item selectedItem) {
        if (selectedItem != null) {
            if (selectedItem.currentStack > 0) {
                if (selectedItem instanceof Stackable) {
                    Item itemToAdd = selectedItem.clone();
                    if (itemToAdd.currentStack > itemToAdd.maxStack) {
                        itemToAdd.currentStack = itemToAdd.maxStack;
                        selectedItem.currentStack -= itemToAdd.maxStack;
                    } else {
                        gp.droppedItems.remove(gp.player.droppedItem);
                    }
                    inventory.addItems(itemToAdd);
                } else {
                    System.out.println("Picked up " + selectedItem.name);
                    inventory.addItems(selectedItem.clone());
                    selectedItem.currentStack = 0;
                    gp.droppedItems.remove(gp.player.droppedItem);
                }
            } else {
                System.out.println("No items to pick up.");
            }
        } else {
            System.out.println("No item selected.");
        }
    }

    public void dropItem(Item selectedItem, int amount, int mapIndex){
        gp.droppedItems.add(new ItemDrop(worldX, worldY, selectedItem.clone(), gp, amount));
        gp.player.inventory.removeItem(selectedItem, amount);
    }

    public void dropAllItems() {
        for (int i = 0; i < inventory.slots.length; i++) {
            if (inventory.slots[i] != null) {
                dropItem(inventory.slots[i], inventory.slots[i].currentStack, gp.currentMap);
            }
        }
        System.out.println("Dropped all items.");
    }
}
