package Object.Items.Unstackable.Buildings;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import Object.Controller.GamePanel;
import Object.Items.Unstackable.Unstackable;

public class Buildings extends Unstackable {
    public int worldX, worldY, width, height;
    public GamePanel gp;
    public Rectangle solidArea;
    public int solidAreaDefaultX; 
    public int solidAreaDefaultY;

    public Buildings(String name, GamePanel gp, Rectangle solidArea, int width, int height) {
        super(name);
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
                
            g2.drawImage(img, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
        }
    }

    public void Build(Graphics2D g2) {
        g2.setColor(Color.RED);
        
        int dx = gp.TILE_SIZE;
        int dy = gp.TILE_SIZE;

        switch (gp.player.direction) {
            case "up":
                g2.drawRect(gp.player.SCREEN_X, gp.player.SCREEN_Y - dy, width, height);
                g2.fillRect(gp.player.SCREEN_X, gp.player.SCREEN_Y - dy, width, height);
                break;
            case "down":
                g2.drawRect(gp.player.SCREEN_X, gp.player.SCREEN_Y + dy, width, height);
                g2.fillRect(gp.player.SCREEN_X, gp.player.SCREEN_Y + dy, width, height);
                break;
            case "left":
                g2.drawRect(gp.player.SCREEN_X - dx, gp.player.SCREEN_Y, width, height);
                g2.fillRect(gp.player.SCREEN_X - dx, gp.player.SCREEN_Y, width, height);
                break;
            case "right":
                g2.drawRect(gp.player.SCREEN_X + dx, gp.player.SCREEN_Y, width, height);
                g2.fillRect(gp.player.SCREEN_X + dx, gp.player.SCREEN_Y, width, height);
                break;
        }
    }

    // public boolean canBuild() {
    //     int tileNum1 = 0, tileNum2 = 0;
    //     boolean canBuild = true;
    //     switch (gp.player.direction) {
    //         case "up":
    //             playerTopRow = (entityTopY - player.speed) / gp.TILE_SIZE;
    //             tileNum1 = gp.tileM.mapTile[playerLeftCol][playerTopRow];
    //             tileNum2 = gp.tileM.mapTile[playerRightCol][playerTopRow];
    //             if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
    //                 player.collisionOn = true;
    //             }
    //         case "down":
    //             playerBottomRow = (entityBottomY + player.speed) / gp.TILE_SIZE;
    //             tileNum1 = gp.tileM.mapTile[playerLeftCol][playerBottomRow];
    //             tileNum2 = gp.tileM.mapTile[playerRightCol][playerBottomRow];
    //             if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
    //                 player.collisionOn = true;
    //             }
    //         case "left":
    //             playerLeftCol = (entityLeftX - player.speed) / gp.TILE_SIZE;
    //             tileNum1 = gp.tileM.mapTile[playerLeftCol][playerTopRow];
    //             tileNum2 = gp.tileM.mapTile[playerLeftCol][playerBottomRow];
    //             if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
    //                 player.collisionOn = true;
    //             }
    //         case "right":
    //             playerRightCol = (entityRightX + player.speed) / gp.TILE_SIZE;
    //             tileNum1 = gp.tileM.mapTile[playerRightCol][playerTopRow];
    //             tileNum2 = gp.tileM.mapTile[playerRightCol][playerBottomRow];
    //             if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
    //                 player.collisionOn = true;
    //             }
    //     }
    //     return canBuild;
    // }

}
