package Objek.Fish;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Objek.Controller.GamePanel;

public class Fish {
    public String nameFish;
    public int price, x, y;
    public int strength;
    public String direction;
    public int worldX, worldY, speed;
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public boolean collisionOn;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public GamePanel gp;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int durabilityCost;
    public int actionLockCounter = 0;

    public Fish (String nameFish, int price, int strength, int worldX, int worldY, int speed, String direction, GamePanel gp, int durabilityCost) {
        this.durabilityCost = durabilityCost;
        this.nameFish = nameFish;
        this.price = price;
        this.strength = strength;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction;
        this.collisionOn = false;
        this.gp = gp;
    }

    public void update() {
        actionLockCounter++;
        if(actionLockCounter < 10) {
            return; // Prevents the fish from moving too quickly
        }
        actionLockCounter = 0;
        collisionOn = false;
        int randomDirection = (int) (Math.random() * 4);
        randomDirection = 2;
        gp.cCheck.fishCheckTile(this);
        if (!collisionOn) {
            switch (randomDirection) {
                case 0:
                    direction = "up";
                    worldY -= speed;
                    break;
                case 1:
                    direction = "down";
                    worldY += speed;
                    break;
                case 2:
                    direction = "left";
                    worldX -= speed;
                    break;
                case 3:
                    direction = "right";
                    worldX += speed;
                    break;
            }
            spriteNum++;
            if (spriteNum > 2) {
                spriteNum = 1;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
                if(spriteNum == 3) image = up3;
                if(spriteNum == 4) image = up4;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
                if(spriteNum == 3) image = down3;
                if(spriteNum == 4) image = down4;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
                if(spriteNum == 3) image = left3;
                if(spriteNum == 4) image = left4;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
                if(spriteNum == 3) image = right3;
                if(spriteNum == 4) image = right4;
                break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y && gp.currentMap == 0) {

            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }
}