package Objek.Controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import Objek.Items.Item;
import Objek.Items.Buildings.Buildings;
import Objek.Items.StackableItem.Stackable;

public class ItemDrop {
    public int worldX, worldY;
    public Rectangle solidArea;
    public Item droppedItem;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;
    public GamePanel gp;
    public int mapIndex;

    public ItemDrop(int x, int y, Item item, GamePanel gp, int amount) {
        this.worldX = x;
        this.worldY = y;
        this.droppedItem = item;
        this.gp = gp;
        this.solidArea = new Rectangle(8, 8, 32, 32 );
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.droppedItem.currentStack = amount;
        this.mapIndex = gp.currentMap; // Menyimpan indeks peta saat ini
    }
    
    public ItemDrop(int x, int y, Item item, GamePanel gp) {
        this.worldX = x;
        this.worldY = y;
        this.droppedItem = item;
        this.gp = gp;
        this.solidArea = new Rectangle(8, 8, 32, 32);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.mapIndex = gp.currentMap;
    }
    
    public void draw(Graphics2D g2) {  // Bisa dipake utk dropped item
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            
            g2.drawImage(droppedItem.img, screenX, screenY, 35, 35, null);
            if (droppedItem instanceof Stackable || droppedItem instanceof Buildings) {
                Font font = new Font("Arial", Font.BOLD, 15); // Family = Arial, Style = Bold, Size = 30 VERSI KECIL
                g2.setColor(Color.white);
                g2.setFont(font);
                g2.drawString(String.valueOf(droppedItem.currentStack), screenX + 20, screenY + 30);
            }
        }
    }

}
