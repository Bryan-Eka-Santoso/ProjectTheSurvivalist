package Objek.Enemy;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import Objek.Controller.GamePanel;
import Objek.Player.Player;
public abstract class Monster {
    public String name, direction;
    public int worldX, worldY, speed;
    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn;
    public GamePanel gp;
    public int hp, attack;
    public boolean isCollidePlayer;

    public Monster(String name, int worldX, int worldY, int speed, String direction, GamePanel gp) {
        this.gp = gp;
        this.name = name;
        this.isCollidePlayer = false;
        this.worldX = worldX;
        this.worldY = worldY;
        this.speed = speed;
        this.direction = direction;
        this.collisionOn = false;
    }

    abstract public void update();
    abstract public void draw(Graphics2D g2d);
    abstract public boolean isPreyNearby(Player player);
    

}
