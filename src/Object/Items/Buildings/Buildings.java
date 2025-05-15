package Object.Items.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import Object.Animal.Animal;
import Object.Controller.GamePanel;
import Object.Plant.Plant;
import Object.Items.Item;

public class Buildings extends Item {
    public int worldX, worldY, width, height;
    public GamePanel gp;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;

    public Buildings(String name, int maxStack, int currentStack, GamePanel gp, Rectangle solidArea, int width, int height) {
        super(name, maxStack, currentStack);
        this.gp = gp;
        this.worldX = gp.player.worldX;
        this.worldY = gp.player.worldY;
        this.solidArea = solidArea;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.width = width;
        this.height = height;
    }

    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                
            g2.drawImage(img, screenX, screenY, width, height, null);
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
        int tileNum = gp.tileM.mapTile[newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
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
