package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class LemariAtas extends Buildings {
    public LemariAtas(GamePanel gp, int currentStack, int buildingMap) {
        super("LemariAtas", 10, currentStack, gp, new Rectangle(0, 0, 450, 50), 450, 50, buildingMap);
        isBreakable = false;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/lemariatas.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
            && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
            && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
            && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y 
            && gp.currentMap == 3) {

            g2.drawImage(img, screenX, screenY, width, height, null);
            
            if(hp < 100) {
                double oneScale = (double)gp.TILE_SIZE/100;
                double hpBarValue = oneScale * hp;
                
                g2.setColor(new java.awt.Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);
                
                g2.setColor(new java.awt.Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
            }
        }
    }
}