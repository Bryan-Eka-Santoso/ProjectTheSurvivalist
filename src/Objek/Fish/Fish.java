package Objek.Fish;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Objek.Controller.GamePanel;

public abstract class Fish {
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

    public abstract void update();
    public abstract void draw(Graphics2D g2d);
}