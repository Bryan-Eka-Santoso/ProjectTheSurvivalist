package Objek.Animal;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;
import Objek.Items.StackableItem.Foods.Other.Egg;
import Objek.Player.Player;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

public class Chicken extends TameAnimal {
    Random random = new Random();
    
    private int actionMoveCounter = 0;
    private int actionMoveDelay;
    private int speed = 8; 
    public int actionLockCounter = 0;
    private static final int CHICKEN_WIDTH = 32;
    private static final int CHICKEN_HEIGHT = 32;
    
    public Chicken(String name, int x, int y, GamePanel gp) {
        super(name, x, y, 15, "down", gp);
        setRandomDirection();
        this.gender = (Math.random() < 0.5) ? "Male" : "Female";
        this.actionMoveDelay = random.nextInt(91) + 30;
       
        this.solidArea = new Rectangle(0,0,32,32); 
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.grabOffsetX = 0;
        this.grabOffsetY = -10;
        this.hp = 60;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up3.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up4.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down3.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down4.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left3.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left4.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right3.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getWidth() {
        return CHICKEN_WIDTH;
    }
    @Override
    public int getHeight() {
        return CHICKEN_HEIGHT;
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
        if (actionLockCounter < 10) {
            return;
        }
        actionLockCounter = 0;
        if(direction == null) {
            direction = "down"; 
        }
        
        collisionOn = false;
        
        gp.cCheck.animalCheckTile(this);    
        gp.cCheck.animalCheckObject(this);   // Check collision dengan object/plant
        gp.cCheck.checkPlayer(this);        // Check collision dengan player
        gp.cCheck.checkAnimalCollision(this);
        gp.cCheck.animalCheckBuildings(this); // Check collision dengan buildings
        
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
       
        if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
           worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
           worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
           worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if(hp < 60) {
                double oneScale = (double)gp.TILE_SIZE/60;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
               
            }
        }
    }

    public Chicken breeding(Chicken pasangan, GamePanel gp) {
        // Check if both chickens are ready to
        if (readyBreeding) {
            if (pasangan.isReadyBreeding()) {
                System.out.println("Breeding chicken with " + pasangan.getName());
                Chicken babyChicken = new Chicken("Baby Chicken", this.x, this.y, gp);
                this.readyBreeding = false;
                pasangan.setReadyBreeding(false);
                return babyChicken;
            } else {
                System.out.println("Partner is not ready to breed.");
                return null;
            }
        } else {
            System.out.println("Not ready to breed yet.");
            return null;
        }
    }
    
    public void getItem(Player player) {
        if(isReadyGetItem()) {
            player.inventory.addItems(new Egg(1));
            setReadyGetItem(false); 
        }
    }
}
