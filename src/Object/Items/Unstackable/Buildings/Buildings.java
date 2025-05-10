package Object.Items.Unstackable.Buildings;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Object.Controller.GamePanel;
import Object.Items.Unstackable.Unstackable;

public class Buildings extends Unstackable {
    public int worldX, worldY, solidAreaX, solidAreaY;
    public GamePanel gp;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;

    public Buildings(String name, GamePanel gp, Rectangle solidArea) {
        super(name);
        this.gp = gp;
        this.solidArea = solidArea;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
    }

    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                
            g2.drawImage(img, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }

    public void Build(Graphics2D g2) {
        // Implementasi untuk membangun bangunan
        // Misalnya, menampilkan gambar bangunan yang sedang dibangun
        int buildingX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int buildingY = worldY - gp.player.worldY + gp.player.SCREEN_Y;
        g2.fillRect(buildingX, buildingY, 48, 48);
        
    }
}
