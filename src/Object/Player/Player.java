package Object.Player;

import Object.Items.Item;
import Object.Items.StackableItem.Torch;
import Object.Animal.Animal;
import Object.Animal.TameAnimal;
import Object.Animal.Chicken;
import Object.Animal.Cow;
import Object.Animal.Sheep;
import Object.Animal.Pig;

import Object.Controller.GamePanel;
import Object.Controller.ItemDrop;
import Object.Controller.KeyHandler;
import Object.Controller.UseItem;

import Object.Items.StackableItem.Stackable;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Player {
    public String name;
    public int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;
    public int itemIndex; // Index of the selected item in the inventory
    public int worldX, worldY, speed, solidAreaX, solidAreaY;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String lastMove;
    public TameAnimal grabbedAnimal;
    public Crafting recipe;
    public int solidAreaDefaultX, solidAreaDefaultY; 
    public Torch torch;
    // ArrayList<Kandang> kandang= new ArrayList<>(); // List of cages owned by the player
    public boolean lightUpdated = true;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public UseItem interactObj; // Object to handle item interactions
    public final int SCREEN_Y;
    public final int SCREEN_X;
    public int plantIndex, animalIndex, droppedItem; // Index of the selected plant in the inventory
    public GamePanel gp;
    public KeyHandler keyH;

    public Player(String name, Crafting recipe, GamePanel gp, KeyHandler keyH) {
        this.name = name;
        this.worldX = gp.TILE_SIZE * 40;
        this.worldY = gp.TILE_SIZE * 44;
        this.health = 100;
        this.thirst = 100;
        this.hunger = 100;
        this.exp = 0;
        this.level = 1;
        this.speed = 5;
        this.inventory = new Inventory(32, gp);
        this.gp = gp;
        this.keyH = keyH;
        this.grabbedAnimal= null; 
        // this.kandang = new ArrayList<>(); 
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
        this.recipe = recipe;
        interactObj = new UseItem();

        getPlayerImg();
    }

    public void getPlayerImg() {
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkup1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkup2.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdown1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkdown2.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleft1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkleft2.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkright1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/player/walkright2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.shiftPressed) {
            speed = 10;
        } else {
            speed = 5;
        }

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
            plantIndex = gp.cCheck.checkPlant(this, true);
            animalIndex = gp.cCheck.checkAnimal(this, true);
            droppedItem = gp.cCheck.checkItemDrop(this, true);
            
            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY -= speed; break;
                    case "down": worldY += speed; break;
                    case "left": worldX -= speed; break;
                    case "right": worldX += speed; break;
                }
            }
            if (grabbedAnimal != null) {
                grabbedAnimal.worldX = worldX;
                grabbedAnimal.worldY = worldY;
            }
            
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

        g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE + 8, null);
        if (grabbedAnimal != null) {
            grabbedAnimal.draw(g2);
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
        grabbedAnimal.grab();
        System.out.println("Grabbed " + animal.getName());
        
    }

    public void displayStatus() {
        displayStats(this);
        if (grabbedAnimal != null) {
            System.out.println("Currently holding: " + grabbedAnimal.getName());
        }
    }

    public void unGrabAnimal() {
        if(grabbedAnimal != null){
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
            int tileNum = gp.tileM.mapTile[newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
            if (tileNum != 8 && tileNum != 9 && tileNum != 10 && tileNum != 11 && 
                tileNum != 12 && tileNum != 13 && tileNum != 14 && tileNum != 15 && 
                tileNum != 18 && tileNum != 20) {
                System.out.println("Cannot place animal on this type of tile!");
                return;
            }
            boolean canPlace = true;
            grabbedAnimal.worldX = newX;
            grabbedAnimal.worldY = newY;
            for(Animal other : gp.animals) {
                if(Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
                Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                    canPlace = false;
                    break;
                }
            }

            if(canPlace){
                grabbedAnimal.worldX = newX;
                grabbedAnimal.worldY = newY;
                gp.animals.add(grabbedAnimal);
                System.out.println("Placed " + grabbedAnimal.getName() + " at (" + newX + ", " + newY + ")");
                grabbedAnimal = null;
            }else {
                System.out.println("Cannot place animal here!");
            }
        }
    }

        // public void buildBuildings() {

        // }
       
        // KandangAyam nearbyKandang = findNearbyKandang();
        // if (nearbyKandang != null) {
        //     if (nearbyKandang.getCurrentCapacity() < nearbyKandang.getMaxCapacity()) {
        //         if (nearbyKandang.addAnimal((Chicken)grabbedAnimal)) {
        //             System.out.println("Added animal to kandang");
        //             island.spawnChicken(); 
        //             grabbedAnimal = null;
        //             return;
        //         }
        //     } else {
        //         System.out.println("Kandang is full!");
        //         return;
        //     }
        // }else

  

    // public KandangAyam findNearbyKandang() {
    //     for (Buildings building : island.buildings) {
    //         if (building instanceof KandangAyam) {
    //             if (Math.abs(building.getX() - worldX) <= 1 && Math.abs(building.getY() - worldY) <= 1) {
    //                 return (KandangAyam) building;
    //             }
    //         }
    //     }
    //     return null;
    // }

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

    public void dropItem(Item selectedItem){
        // Add to dropped Item list
        // Item berkurang dari inventory
        gp.droppedItems.add(new ItemDrop(worldX, worldY, selectedItem.clone(), gp));
        gp.player.inventory.removeItem(selectedItem, selectedItem.currentStack);
    }

    public String displayStats(Player player) {
        return  "<html> ======Player stats====== <br>" +
                "Player name: " + player.name + "<br>" +
                "Health: " + player.health + "<br>" +
                "Thirst: " + player.thirst + "<br>" +
                "Hunger: " + player.hunger + "<br>" +
                "Experience: " + player.exp + "<br>" +
                "Level: " + player.level + "</html>";
    }
    
}
