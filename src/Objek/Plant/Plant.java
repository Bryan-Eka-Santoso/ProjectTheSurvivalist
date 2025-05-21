package Objek.Plant;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Objek.Controller.GamePanel;

import java.awt.Color;

public class Plant {
    public int worldX, worldY, solidAreaX, solidAreaY;
    public GamePanel gp;
    public BufferedImage image;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;
    public boolean collision;
    public int hp;
    public int maxHp;
    Graphics2D g2;

    public Plant(int hp, int x, int y, GamePanel gp, Rectangle solidArea) {
        this.worldX = x;
        this.worldY = y;
        this.gp = gp;
        this.solidArea = solidArea;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.hp = hp;
        this.maxHp = hp;
    }

    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if(hp < maxHp) {
                double oneScale = (double)gp.TILE_SIZE/100;
                double hpBarValue = oneScale * hp;
                
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);
                
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
            }
        }
    }
  

}
