package Object.Environment;

import Object.Controller.GamePanel;
import Object.Items.StackableItem.Torch;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

public class Lighting {
    GamePanel gp;
    BufferedImage lightFilter;
    BufferedImage darknessFilter;
    float filterAlpha = 0f;
    int dayCounter;

    final int DAY = 0; 
    final int DUSK = 1; 
    final int NIGHT = 2; 
    final int DAWN = 3; 
    int dayState = DAY;

    public Lighting(GamePanel gp) {
        this.gp = gp;
    }
    
    public void setLightSource() {
        darknessFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB); 
        Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        
        int centerX = gp.player.SCREEN_X + gp.TILE_SIZE / 2;
        int centerY = gp.player.SCREEN_Y + gp.TILE_SIZE / 2;
        
        Color color[] = new Color[12];
        float fraction[] = new float[12];
        
        color[0] = new Color(0, 0, 0, 0.1f);
        color[1] = new Color(0, 0, 0, 0.42f);
        color[2] = new Color(0, 0, 0, 0.52f);
        color[3] = new Color(0, 0, 0, 0.61f);
        color[4] = new Color(0, 0, 0, 0.69f);
        color[5] = new Color(0, 0, 0, 0.76f);
        color[6] = new Color(0, 0, 0, 0.82f);
        color[7] = new Color(0, 0, 0, 0.87f);
        color[8] = new Color(0, 0, 0, 0.91f);
        color[9] = new Color(0, 0, 0, 0.94f);
        color[10] = new Color(0, 0, 0, 0.96f);
        color[11] = new Color(0, 0, 0, 0.98f);
        
        fraction[0] = 0f;
        fraction[1] = 0.4f;
        fraction[2] = 0.5f;
        fraction[3] = 0.6f;
        fraction[4] = 0.65f;
        fraction[5] = 0.7f;
        fraction[6] = 0.75f;
        fraction[7] = 0.8f;
        fraction[8] = 0.85f;
        fraction[9] = 0.9f;
        fraction[10] = 0.95f;
        fraction[11] = 1f;
        
        RadialGradientPaint gPaint = new RadialGradientPaint(centerX, centerY, 0.1f, fraction, color); 
        if (!(gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Torch)) {
            gPaint = new RadialGradientPaint(centerX, centerY, 0.1f, fraction, color);
        } else {
            gPaint = new RadialGradientPaint(centerX, centerY, ((Torch) gp.player.inventory.slots[gp.ui.selectedIndex]).lightRadius, fraction, color);
        }    
        
        g2.setPaint(gPaint);
        
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        g2.dispose();
    }
    
    public void update() {
        if (gp.player.lightUpdated) {
            setLightSource();
            gp.player.lightUpdated = false;
        }
        
        if (dayState == DAY) {
            dayCounter++;
            if (dayCounter > 600) {
                dayState = DUSK;
                dayCounter = 0;
            }
        } 
        if (dayState == DUSK) {
            filterAlpha += 0.001f;
            if (filterAlpha > 0.8f) {
                filterAlpha = 0.8f;
                dayState = NIGHT;
            }
        } 
        if (dayState == NIGHT) {
            dayCounter++;
            if (dayCounter > 600) {
                dayState = DAWN;
                dayCounter = 0;
            }
        } 
        if (dayState == DAWN) {
            filterAlpha -= 0.01f;
            if (filterAlpha < 0f) {
                filterAlpha = 0f;
                dayState = DAY;
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}
