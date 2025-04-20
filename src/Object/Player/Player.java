package Object.Player;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Item;
import Object.Items.Unstackable.Buildings.Kandang;
import Object.Items.Unstackable.Buildings.KandangAyam;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.*; 
import javax.imageio.ImageIO;
import Object.GamePanel;
import Object.KeyHandler;
import Object.Animal.Animal;
import Object.Animal.Chicken;

public class Player {
    public String name;
    public int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;
    public Island island;
    public int itemIndex; // Index of the selected item in the inventory
    Item selectedItem; // Currently selected item
    public int worldX, worldY, speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public String lastMove;
    public Animal grabbedAnimal;
    ArrayList<Kandang> kandang= new ArrayList<>(); // List of cages owned by the player

    public Rectangle solidArea;
    public boolean collisionOn = false;
    public final int SCREEN_Y;
    public final int SCREEN_X;

    GamePanel gp; 
    KeyHandler keyH;

    // public Player(String name, int x, int y) {
    //     this.name = name;
    //     this.worldX = x;
    //     this.worldY = y;
    //     this.health = 100;
    //     this.thirst = 100;
    //     this.hunger = 100;
    //     this.exp = 0;
    //     this.level = 1;
    //     this.speed = 5;
    //     this.inventory = new Inventory(10);
    //     solidArea = new Rectangle();
    //     solidArea.x = 8;
    //     solidArea.y = 16;
    //     solidArea.width = 32;
    //     solidArea.height = 32;

    //     this.grabbedAnimal= null; 
    //     this.kandang = new ArrayList<>(); 
    // }
    
    public Player(String name, GamePanel gp, KeyHandler keyH) {
        this.name = name;
        this.worldX = gp.TILE_SIZE * 40;
        this.worldY = gp.TILE_SIZE * 35;
        this.health = 100;
        this.thirst = 100;
        this.hunger = 100;
        this.exp = 0;
        this.level = 1;
        this.speed = 5;
        this.inventory = new Inventory(10);
        this.gp = gp;
        this.keyH = keyH;
        this.grabbedAnimal= null; 
        this.kandang = new ArrayList<>(); 
        this.direction = "down";
        this.solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidArea.width = 32;
        solidArea.height = 32;
        this.collisionOn = false;
        SCREEN_X = gp.SCREEN_WIDTH / 2 - (gp.TILE_SIZE / 2);
        SCREEN_Y = gp.SCREEN_HEIGHT / 2 - (gp.TILE_SIZE / 2);

        getPlayerImg();
    }

    public void getPlayerImg() {
        try {
            up1 = ImageIO.read(new File("res/player/walkup1.png"));
            up2 = ImageIO.read(new File("res/player/walkup2.png"));
            down1 = ImageIO.read(new File("res/player/walkdown1.png"));
            down2 = ImageIO.read(new File("res/player/walkdown2.png"));
            left1 = ImageIO.read(new File("res/player/walkleft1.png"));
            left2 = ImageIO.read(new File("res/player/walkleft2.png"));
            right1 = ImageIO.read(new File("res/player/walkright1.png"));
            right2 = ImageIO.read(new File("res/player/walkright2.png"));
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
                worldY -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                worldY += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                worldX -= speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                worldX += speed;
            }

            collisionOn = false;
            gp.cCheck.checkTile(this);

            if (!collisionOn) {
                switch (direction) {
                    case "up": worldY += speed; break;
                    case "down": worldY -= speed; break;
                    case "left": worldX += speed; break;
                    case "right": worldX -= speed; break;
                }
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

        g2.drawImage(image, SCREEN_X, SCREEN_Y, gp.TILE_SIZE, gp.TILE_SIZE, null);
    }

   public void move(int dx, int dy) {
        if (island.world[worldY + dy][worldX + dx] == ' ' ) {
            

            if (dx > 0) lastMove = "d";
            else if (dx < 0) lastMove = "a";
            else if (dy > 0) lastMove = "s";
            else if (dy < 0) lastMove = "w";

            island.world[worldY][worldX] = ' ';
            worldX += dx;
            worldY += dy;
            island.world[worldY][worldX] = 'P';
        }
    }
    public boolean isAnimal(char tile) {
        return tile == 'A'; 
    }

    public void handleGrabAction() {
        if (grabbedAnimal == null) {
            Animal nearbyAnimal = findNearbyAnimal();
            if (nearbyAnimal != null) {
                grabAnimal(nearbyAnimal);
            }
        } else {  
            unGrabAnimal();
        }
    }

    public Animal findNearbyAnimal() {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int checkX = worldX + dx;
                int checkY = worldY + dy;
                if (isAnimal(island.world[checkY][checkX])) {
                    return island.getAnimalAt(checkX, checkY);
                }
            }
        }
        return null;
    }

    public void grabAnimal(Animal animal) {
        if (selectedItem != null) {
            System.out.println("Cannot grab animal while holding an item!");
            return;
        }
        grabbedAnimal = animal;
        island.removeAnimal(animal);
        System.out.println("Grabbed " + animal.getName());
    }

    public void displayStatus() {
        displayStats(this);
        if (grabbedAnimal != null) {
            System.out.println("Currently holding: " + grabbedAnimal.getName());
        }
    }

    public void unGrabAnimal() {
        KandangAyam nearbyKandang = findNearbyKandang();
        int newX = worldX, newY = worldY;
        switch (lastMove) {
            case "w": newY--; break;
            case "s": newY++; break;
            case "a": newX--; break;
            case "d": newX++; break;
        }
        if (nearbyKandang != null) {
            if (nearbyKandang.getCurrentCapacity() < nearbyKandang.getMaxCapacity()) {
                if (nearbyKandang.addAnimal((Chicken)grabbedAnimal)) {
                    System.out.println("Added animal to kandang");
                    island.spawnChicken(); 
                    grabbedAnimal = null;
                    return;
                }
            } else {
                System.out.println("Kandang is full!");
                return;
            }
        }else if (island.world[newY][newX] != ' ') {
           
            System.out.println("Cannot release animal here ");
            return;
        }
        placeAnimalNearby();
        System.out.println("Released " + grabbedAnimal.getName());
        grabbedAnimal = null;
    }

    public void placeAnimalNearby() {
        int newX = worldX, newY = worldY;
        switch (lastMove) {
            case "w": newY--; break;
            case "s": newY++; break;
            case "a": newX--; break;
            case "d": newX++; break;
        }
        
        if (island.world[newY][newX] == ' ') {
            island.placeAnimal(grabbedAnimal, newX, newY);
        }
    }

    public KandangAyam findNearbyKandang() {
        for (Buildings building : island.buildings) {
            if (building instanceof KandangAyam) {
                if (Math.abs(building.getX() - worldX) <= 1 && Math.abs(building.getY() - worldY) <= 1) {
                    return (KandangAyam) building;
                }
            }
        }
        return null;
    }

    public boolean isHoldingAnimal() {
        return grabbedAnimal != null;
    }

    public void selectItem(int index) {
        selectedItem = inventory.slots[index]; 
        this.itemIndex = index;
    }

    public void useItem() {
        if (isHoldingAnimal()) {
            System.out.println("Cannot use items while holding an animal!");
            return;
        }
        selectItem(itemIndex);
        if (selectedItem != null && selectedItem.name != null) {
            if (selectedItem instanceof Material) {
                Material material = (Material) selectedItem;
                System.out.println("Using material: " + material.name);
            } else if (selectedItem instanceof Buildings) {
                Buildings building = (Buildings) selectedItem;
                System.out.println("Using building: " + building.name);
            } else if (selectedItem instanceof Food) {
                Food food = (Food) selectedItem;
                System.out.println("Using food: " + food.name);
                food.eat(this); 
                inventory.removeItem(selectedItem.name); 
            } else {
                System.out.println("Unknown item type!"); 
            }
        } else {
            System.out.println("No item selected!"); 
        }
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
