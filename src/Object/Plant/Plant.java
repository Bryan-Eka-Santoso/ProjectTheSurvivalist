package Object.Plant;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import Object.GamePanel;

public class Plant {
    public int worldX, worldY, solidAreaX, solidAreaY;
    public GamePanel gp;
    public BufferedImage image;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public Plant(int x, int y, GamePanel gp) {
        this.worldX = x;
        this.worldY = y;
        this.gp = gp;
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }

}
