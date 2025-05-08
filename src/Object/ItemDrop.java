package Object;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import Object.Items.Item;
import Object.Items.StackableItem.Stackable;

public class ItemDrop {
    public int worldX, worldY;
    public Rectangle rectangle;
    public Item droppedItem;
    public GamePanel gp;

    public ItemDrop(int x, int y, Item item, GamePanel gp) {
        this.worldX = x;
        this.worldY = y;
        this.droppedItem = item;
        this.gp = gp;
        this.rectangle = new Rectangle();
    }
    
    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            
            g2.drawImage(droppedItem.img, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (droppedItem instanceof Stackable) {
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI KECIL
                g2.setColor(Color.white);
                g2.setFont(font);
                g2.drawString(String.valueOf(droppedItem.currentStack), screenX + 25, screenY + 40);
            }
        }
    }

}
