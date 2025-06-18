package Objek.Animal;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;
import Objek.Items.StackableItem.Materials.AnimalDrops.Wool;
import Objek.Player.Player;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
public class Sheep extends TameAnimal {
    Random random = new Random();
    // private Rectangle upHitbox;
    // private Rectangle downHitbox;
    // private Rectangle leftHitbox;
    // private Rectangle rightHitbox;
    private int actionMoveCounter = 0;
    private int actionMoveDelay;
    private int speed = 8; 
    public int actionLockCounter = 0;
    private static final int SHEEP_WIDTH = 128;
    private static final int SHEEP_HEIGHT = 128;
    
    public Sheep(String name, int x, int y, GamePanel gp) {
        super(name, x, y, 15, "down", gp);
        setRandomDirection();
        this.actionMoveDelay = random.nextInt(91) + 30;
        // upHitbox = new Rectangle(50, 45, 30, 63);    // Lebih sempit di atas
        // downHitbox = new Rectangle(50, 45, 30, 63); // Lebih sempit di bawah
        // leftHitbox = new Rectangle(33, 60, 57,28 );  // Lebih sempit di kiri
        // rightHitbox = new Rectangle(33, 60, 57,28 );
        // this.solidArea = downHitbox;
        this.solidArea = new Rectangle(20,20,44,44); 
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.grabOffsetX = 0;
        this.grabOffsetY = -40;
        this.gender = (Math.random() < 0.5) ? "Male" : "Female";
        
        this.hp = 100;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/up3.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/up4.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/down3.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/down4.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/left3.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/left4.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/right3.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/sheep/right4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getWidth() {
        return SHEEP_WIDTH;
    }
    @Override
    public int getHeight() {
        return SHEEP_HEIGHT;
    }

    private void setRandomDirection() {
        String newDirection= null;
        String oldDirection = this.direction;
        do{
            int random = this.random.nextInt(4);
            switch(random) {
                case 0: newDirection = "up"; break;
                case 1: newDirection = "down"; break;
                case 2: newDirection = "left"; break;
                case 3: newDirection = "right"; break;
                
            }
        } while (newDirection.equals(oldDirection));
        this.direction = newDirection;
        this.actionMoveDelay = this.random.nextInt(91) + 30;
        gp.player.collisionOn = false;
    }

    @Override
    public void update() {
        actionLockCounter++;
        if(actionLockCounter < 15) {
            return; 
        }
        actionLockCounter = 0; // Reset counter
        if(direction == null) {
            direction = "down"; 
        }
        // switch(direction) {
        //     case "up": 
        //         solidArea = upHitbox;
        //         break;
        //     case "down": 
        //         solidArea = downHitbox;
        //         break;
        //     case "left": 
        //         solidArea = leftHitbox;
        //         break;
        //     case "right": 
        //         solidArea = rightHitbox;
        //         break;
        // }

        collisionOn = false;
        gp.cCheck.animalCheckTile(this);     // Check collision dengan tile
        gp.cCheck.animalCheckObject(this);   // Check collision dengan object/plant
        gp.cCheck.checkPlayer(this);        // Check collision dengan player
        gp.cCheck.checkAnimalCollision(this);
        gp.cCheck.animalCheckBuildings(this);
        
        // Jika tidak ada collision, boleh bergerak
        if(!collisionOn) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
            actionMoveCounter++;
            if(actionMoveCounter >= actionMoveDelay) {
                setRandomDirection();
                actionMoveCounter = 0;
            }
        } else {
            String newDirection;
            String oldDirection = this.direction;

            switch(oldDirection) {
                case "up": newDirection = "down"; break;
                case "down": newDirection = "up"; break;
                case "left": newDirection = "right"; break;
                case "right": newDirection = "left"; break;
                default: newDirection = "down"; break;
            }
            this.direction = newDirection;
            this.actionMoveDelay = this.random.nextInt(91)+30;
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
            actionMoveCounter++;
        }

        spriteCounter++;
        if(spriteCounter > 0) {
            spriteNum++;
            if(spriteNum > 4) {
                spriteNum = 1;
            }
           
            spriteCounter = 0;
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        
        switch(direction) {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                if(spriteNum == 3) image = up3;
                if(spriteNum == 4) image = up4;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                if(spriteNum == 3) image = down3;
                if(spriteNum == 4) image = down4;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                if(spriteNum == 3) image = left3;
                if(spriteNum == 4) image = left4;
                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                if(spriteNum == 3) image = right3;
                if(spriteNum == 4) image = right4;
                break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
        g2.drawRect(
            screenX + solidArea.x,
            screenY + solidArea.y,
            solidArea.width,
            solidArea.height
        );
        if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
           worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
           worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
           worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);
            if(hp < 100) {
                double oneScale = (double)gp.TILE_SIZE*2/100;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE*2+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
               
            }
        }
    }
  
    public void getItem(Player player) {
        if(isReadyGetItem()) {
            player.inventory.addItems(new Wool(1));
            setReadyGetItem(false);
        }
    }

    public void setReadyGetItem(boolean ready) {
        this.readyGetItem = ready;
    }

    public Sheep breeding(Sheep pasangan, GamePanel gp) {
        if (readyBreeding) {
            if (pasangan.isReadyBreeding()) {
                System.out.println("Breeding sheep with " + pasangan.getName());
                Sheep babysheep = new Sheep("Baby sheep", this.x, this.y, gp);
                this.readyBreeding = false;
                pasangan.setReadyBreeding(false);
                return babysheep;
            } else {
                System.out.println("Partner is not ready to breed.");
                return null;
            }
        } else {
            System.out.println("Not ready to breed yet.");
            return null;
        }
    }

}