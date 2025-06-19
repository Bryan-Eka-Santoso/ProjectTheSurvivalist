package Objek.Fish;

import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.util.Random;
import Objek.Controller.GamePanel;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

public class Belida extends Fish {
    Random random = new Random();
    public Rectangle upHitbox;
    public Rectangle downHitbox;
    public Rectangle leftHitbox;
    public Rectangle rightHitbox;
    public int actionLockCounter = 0;
    public int actionMoveCounter = 0;
    public int speed = 8; 

    public Belida(String nameFish, int price, int stregth, int x, int y, GamePanel gp) {
        super("Belida", 15, 20, x, y, 30, "down", gp, 2);
        setRandomDirection();
        this.actionMoveDelay = random.nextInt(91) + 30;
        upHitbox = new Rectangle(8, 1, 20, 30);   
        downHitbox = new Rectangle(8, 1, 20, 30);       
        leftHitbox = new Rectangle(1, 6, 30, 20);
        rightHitbox = new Rectangle(1, 6, 30, 20);
        this.solidArea = downHitbox; 
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        try {
            up1 = ImageIO.read(getClass().getResource("/res/fish/belida/up1.png"));
            up2 = ImageIO.read(getClass().getResource("/res/fish/belida/up2.png"));
            up3 = ImageIO.read(getClass().getResource("/res/fish/belida/up3.png"));
            up4 = ImageIO.read(getClass().getResource("/res/fish/belida/up4.png"));
            down1 = ImageIO.read(getClass().getResource("/res/fish/belida/down1.png"));
            down2 = ImageIO.read(getClass().getResource("/res/fish/belida/down2.png"));
            down3 = ImageIO.read(getClass().getResource("/res/fish/belida/down3.png"));
            down4 = ImageIO.read(getClass().getResource("/res/fish/belida/down4.png"));
            left1 = ImageIO.read(getClass().getResource("/res/fish/belida/left1.png"));
            left2 = ImageIO.read(getClass().getResource("/res/fish/belida/left2.png"));
            left3 = ImageIO.read(getClass().getResource("/res/fish/belida/left3.png"));
            left4 = ImageIO.read(getClass().getResource("/res/fish/belida/left4.png"));
            right1 = ImageIO.read(getClass().getResource("/res/fish/belida/right1.png"));
            right2 = ImageIO.read(getClass().getResource("/res/fish/belida/right2.png"));
            right3 = ImageIO.read(getClass().getResource("/res/fish/belida/right3.png"));
            right4 = ImageIO.read(getClass().getResource("/res/fish/belida/right4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int actionMoveDelay;
    public void setRandomDirection() {
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
        if(actionLockCounter < 10) {
            return; // Prevents the fish from moving too quickly
        }
        actionLockCounter = 0;
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
        
        gp.cCheck.fishCheckTile(this);    
        gp.cCheck.checkFishPlayer(this);
        gp.cCheck.checkFishCollision(this);
        
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

        if(gp.currentMap == 1 && 
           worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
           worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
           worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
           worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }
}
