package Objek.Animal;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Player.*;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Wolf extends WildAnimal {
    Random random = new Random();
    String gender;
    boolean readyGetItem;
    boolean readyBreeding;
    private Rectangle upHitbox;
    private Rectangle downHitbox;
    private Rectangle leftHitbox;
    private Rectangle rightHitbox;
    
    public Wolf(String name, int x, int y, GamePanel gp) {
        super(name, x, y, 15, "down", gp);
        setRandomDirection();
        this.gender = (Math.random() < 0.5) ? "Male" : "Female";
        this.readyBreeding = true;
        this.readyGetItem = true;  
        this.actionMoveDelay = random.nextInt(91) + 30;
        upHitbox = new Rectangle(17, 1, 28, 63);   
        downHitbox = new Rectangle(18, 5, 28, 59);   
        leftHitbox = new Rectangle(1, 16, 63, 35);   
        rightHitbox = new Rectangle(1, 16, 63, 35);   
        this.solidArea = downHitbox; 
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        readyBreeding = true;
        this.hp = 100;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/up1.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/up2.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/down1.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/down2.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/left1.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/left2.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/right1.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/wolf/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getGender() {
        return gender;
    }
    public boolean isReadyGetItem() {
        return readyGetItem;
    }
    public void setReadyGetItem(boolean ready) {
        this.readyGetItem = ready;
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

    private int actionMoveCounter = 0;
    private int actionMoveDelay;
    private int speed = 15; 

    @Override
    public void update() {
        if(direction == null) {
            direction = "down"; 
        }
        switch(direction) {
            case "up": 
                solidArea = upHitbox;
                break;
            case "down": 
                solidArea = downHitbox;
                break;
            case "left": 
                solidArea = leftHitbox;
                break;
            case "right": 
                solidArea = rightHitbox;
                break;
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
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE * 2, gp.TILE_SIZE * 2, null);
            if(hp < 100) {
                double oneScale = (double) gp.TILE_SIZE*2/100;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE*2+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue +10, 10);
                
               
            }
        }
    }

    public boolean isPreyNearby(Player player) {
        if (Math.pow((player.worldX - this.worldX), 2) + Math.pow((player.worldY - this.worldY), 2) <= Math.pow(450, 2)) {
            return true;
        } else {
            return false;
        }
    }

    public void chasePlayer(Player player) {
        if (isPreyNearby(player)) {
            if (Math.abs(player.worldX - this.worldX) > Math.abs(player.worldY - this.worldY)) {
                if (player.worldX > this.worldX) {
                    this.direction = "right";
                } else {
                    this.direction = "left";
                }
            } else {
                if (player.worldY > this.worldY) {
                    this.direction = "down";
                } else {
                    this.direction = "up";
                }
            }
        }
    }
}
