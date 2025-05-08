package Object;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Object.Items.Item;

public class ItemDrop {
    public int worldX, worldY;
    public Rectangle rectangle;
    public Item droppedItem;
    public GamePanel gp;

    public ItemDrop(int x, int y, Item item, GamePanel gp) {
        this.worldX = x;
        this.worldY = y;
        this.droppedItem = item;
        this.rectangle = new Rectangle();
    }
    
    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX;
        int screenY = worldY;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                
            g2.drawImage(droppedItem.img, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }
}
