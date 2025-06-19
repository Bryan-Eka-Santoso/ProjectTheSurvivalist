package Objek.Items.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import Objek.Animal.Animal;
import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Plant.Plant;

public class Buildings extends Item {
    public int worldX, worldY, width, height;
    public GamePanel gp;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;
    public int hp = 100;
    public boolean isAllowCollison;
    public boolean isBreakable;
    public int buildingMap;

    public Buildings(String name, int maxStack, int currentStack, GamePanel gp, Rectangle solidArea, int width, int height, int buildingMap) {
        super(name, maxStack, currentStack, false, 0);
        this.buildingMap = buildingMap;
        this.gp = gp;
        this.solidArea = solidArea;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.width = width;
        this.height = height;
        this.isAllowCollison = true;  // Defaultnya true, bisa diubah di subclass
        this.isBreakable = true;
    }

    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y && gp.currentMap == 0) {

            if (this instanceof Orchard) {
                Orchard orchard = (Orchard) this;
                if (orchard.phase.equals("tree")) {
                    g2.drawImage(img, screenX - 20, screenY - gp.TILE_SIZE + 5, gp.TILE_SIZE * 2, gp.TILE_SIZE * 2, null);
                } else {
                    g2.drawImage(img, screenX, screenY, width, height, null);
                }
            } else {
                g2.drawImage(img, screenX, screenY, width, height, null);
            }
            
            if(hp < 100) {
                double oneScale = (double)gp.TILE_SIZE/100;
                double hpBarValue = oneScale * hp;
                
                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);
                
                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
            }
        }
    }

    public void Build(Graphics2D g2) {
        if (!canBuild()) {
            g2.setColor(Color.RED);
        } else {
            g2.setColor(Color.GREEN);
        }

        switch (gp.player.direction) {
            case "up":
                g2.drawRect(gp.player.SCREEN_X, gp.player.SCREEN_Y - height, width, height);
                g2.fillRect(gp.player.SCREEN_X, gp.player.SCREEN_Y - height, width, height);
                break;
            case "down":
                g2.drawRect(gp.player.SCREEN_X, gp.player.SCREEN_Y + height, width, height);
                g2.fillRect(gp.player.SCREEN_X, gp.player.SCREEN_Y + height, width, height);
                break;
            case "left":
                g2.drawRect(gp.player.SCREEN_X - width, gp.player.SCREEN_Y, width, height);
                g2.fillRect(gp.player.SCREEN_X - width, gp.player.SCREEN_Y, width, height);
                break;
            case "right":
                g2.drawRect(gp.player.SCREEN_X + width, gp.player.SCREEN_Y, width, height);
                g2.fillRect(gp.player.SCREEN_X + width, gp.player.SCREEN_Y, width, height);
                break;
        }
        
    }

    public boolean canBuild() {
        int newX = gp.player.worldX, newY = gp.player.worldY; 
        switch (gp.player.direction) {
            case "up":
                newY = gp.player.worldY - height;  
                break;
            case "down":
                newY = gp.player.worldY + height;  
                break;
            case "left":
                newX = gp.player.worldX - width;  
                break;
            case "right":
                newX = gp.player.worldX+ width;  
                break;
        }
        if (newX < 0 || newX >= gp.MAX_WORLD_COL * gp.TILE_SIZE || 
            newY < 0 || newY >= gp.MAX_WORLD_ROW * gp.TILE_SIZE) {
            return false;
        }
        int tileNum = gp.tileM.mapTile[gp.currentMap][newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
        if (tileNum != 8 && tileNum != 9 && tileNum != 10 && tileNum != 11 && 
            tileNum != 12 && tileNum != 13 && tileNum != 14 && tileNum != 15 && 
            tileNum != 18 && tileNum != 20 && tileNum != 17) {
            return false;
        }
        boolean canPlace = true;
        for (Animal other : gp.animals) {
            if(Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
            Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                canPlace = false;
                break;
            }
        }
        for (Plant other : gp.plants) {
            if(Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
            Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                canPlace = false;
                break;
            }
        }
        for (Buildings other : gp.buildings) {
            if(Math.abs(other.worldX - newX) < gp.TILE_SIZE && 
            Math.abs(other.worldY - newY) < gp.TILE_SIZE) {
                canPlace = false;
                break;
            }
        }
        return canPlace;
    }

}
