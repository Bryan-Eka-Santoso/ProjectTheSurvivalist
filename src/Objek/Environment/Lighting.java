// package Objek.Environment;

// import java.awt.AlphaComposite;
// import java.awt.Color;
// import java.awt.Graphics2D;
// import java.awt.RadialGradientPaint;
// import java.awt.image.BufferedImage;
// import Objek.Controller.GamePanel;
// import Objek.Items.Buildings.*;

// public class Lighting {
//     GamePanel gp;
//     BufferedImage lightFilter;
//     BufferedImage darknessFilter;
//     float filterAlpha = 0f;
//     public int dayCounter;

//     final int DAY = 0; 
//     final int DUSK = 1; 
//     final int NIGHT = 2; 
//     final int DAWN = 3; 
//     public int dayState = DAY;

//     public Lighting(GamePanel gp) {
//         this.gp = gp;
//     }
    
//     public void setLightSource() {
//         // Create a new darkness filter with the screen dimensions
//         darknessFilter = new BufferedImage(gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB); 
//         Graphics2D g2 = (Graphics2D) darknessFilter.getGraphics();
        
//         // Define the light gradient
//         Color color[] = new Color[12];
//         float fraction[] = new float[12];
        
//         color[0] = new Color(0, 0, 0, 0.1f);
//         color[1] = new Color(0, 0, 0, 0.42f);
//         color[2] = new Color(0, 0, 0, 0.52f);
//         color[3] = new Color(0, 0, 0, 0.61f);
//         color[4] = new Color(0, 0, 0, 0.69f);
//         color[5] = new Color(0, 0, 0, 0.76f);
//         color[6] = new Color(0, 0, 0, 0.82f);
//         color[7] = new Color(0, 0, 0, 0.87f);
//         color[8] = new Color(0, 0, 0, 0.91f);
//         color[9] = new Color(0, 0, 0, 0.94f);
//         color[10] = new Color(0, 0, 0, 0.96f);
//         color[11] = new Color(0, 0, 0, 0.98f);
        
//         fraction[0] = 0f;
//         fraction[1] = 0.4f;
//         fraction[2] = 0.5f;
//         fraction[3] = 0.6f;
//         fraction[4] = 0.65f;
//         fraction[5] = 0.7f;
//         fraction[6] = 0.75f;
//         fraction[7] = 0.8f;
//         fraction[8] = 0.85f;
//         fraction[9] = 0.9f;
//         fraction[10] = 0.95f;
//         fraction[11] = 1f;
        
//         // Fill the entire screen with darkness first
//         g2.setColor(new Color(0, 0, 0, 0.98f));
//         g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
//         // Create player light if player is holding a torch
//         int centerX = gp.player.SCREEN_X + gp.TILE_SIZE / 2;
//         int centerY = gp.player.SCREEN_Y + gp.TILE_SIZE / 2;
//         float lightRadius = 100; // Default light radius
        
//         // Check if player is holding a torch and adjust light radius
//         if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Torch) {
//             lightRadius = ((Torch) gp.player.inventory.slots[gp.ui.selectedIndex]).lightRadius;
//         }
        
//         // Draw player light
//         RadialGradientPaint playerLight = new RadialGradientPaint(centerX, centerY, lightRadius, fraction, color);
//         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OUT));
//         g2.setPaint(playerLight);
//         g2.fillRect(centerX - (int)lightRadius, centerY - (int)lightRadius, (int)lightRadius * 2, (int)lightRadius * 2);
        
//         // Draw lights for placed torches
//         for (int i = 0; i < gp.buildings.size(); i++) {
//             if (gp.buildings.get(i) instanceof Torch) {
//                 Torch torch = (Torch) gp.buildings.get(i);
                
//                 // Calculate screen position of the torch
//                 int torchScreenX = torch.worldX - gp.player.worldX + gp.player.SCREEN_X;
//                 int torchScreenY = torch.worldY - gp.player.worldY + gp.player.SCREEN_Y;
                
//                 // Only draw light if torch is visible on screen
//                 if (torchScreenX > -torch.lightRadius && torchScreenX < gp.SCREEN_WIDTH + torch.lightRadius &&
//                     torchScreenY > -torch.lightRadius && torchScreenY < gp.SCREEN_HEIGHT + torch.lightRadius) {
                    
//                     // Create light source for this torch
//                     RadialGradientPaint torchLight = new RadialGradientPaint(
//                         torchScreenX + torch.width / 2, 
//                         torchScreenY + torch.height / 2, 
//                         torch.lightRadius, 
//                         fraction, 
//                         color
//                     );
                    
//                     g2.setPaint(torchLight);
//                     g2.fillOval(
//                         torchScreenX + torch.width / 2 - (int)torch.lightRadius, 
//                         torchScreenY + torch.height / 2 - (int)torch.lightRadius, 
//                         (int)torch.lightRadius * 2, 
//                         (int)torch.lightRadius * 2
//                     );
//                 }
//             }
//         }
        
//         g2.dispose();
//     }
    
//     public void update() {
//         // Update lighting when player inventory changes
//         if (gp.player.lightUpdated) {
//             setLightSource();
//             gp.player.lightUpdated = false;
//         }

//         // Handle day/night cycle
//         if (dayState == DAY) {
//             dayCounter++;
//             if (dayCounter > 600) {
//                 dayState = DUSK;
//                 dayCounter = 0;
//             }
//         } 
//         if (dayState == DUSK) {
//             filterAlpha += 0.001f;
//             if (filterAlpha > 0.8f) {
//                 filterAlpha = 0.8f;
//                 dayState = NIGHT;
//             }
//         } 
//         if (dayState == NIGHT) {
//             dayCounter++;
//             if (dayCounter > 600) {
//                 dayState = DAWN;
//                 dayCounter = 0;
//             }
//         } 
//         if (dayState == DAWN) {
//             filterAlpha -= 0.01f;
//             if (filterAlpha < 0f) {
//                 filterAlpha = 0f;
//                 dayState = DAY;
//             }
//         }
        
//         // Always refresh lighting in night time to update torch positions
//         if (gp.player.isPlaceTorch) {
//             setLightSource();
//             gp.player.isPlaceTorch = false;
//         }
//     }
    
//     public void draw(Graphics2D g2) {
//         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
//         g2.drawImage(darknessFilter, 0, 0, null);
//         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//     }
// }

package Objek.Environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;
import Objek.Controller.GamePanel;
import Objek.Items.Unstackable.Torch;

public class Lighting {
    GamePanel gp;
    BufferedImage lightFilter;
    BufferedImage darknessFilter;
    public float filterAlpha = 0f;
    public int dayCounter;
    public float filterAlphaTemp = 0f;

    final int DAY = 0; 
    final int DUSK = 1; 
    final int NIGHT = 2; 
    final int DAWN = 3; 
    public int dayState = DAY;

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
            filterAlphaTemp += 0.001f;
            if (filterAlphaTemp > 0.8f) {
                filterAlpha = 0.8f;
                filterAlphaTemp = 0.8f;
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
            filterAlphaTemp -= 0.01f;
            if (filterAlphaTemp < 0f) {
                filterAlpha = 0f;
                filterAlphaTemp = 0f;
                gp.player.isSleeping = false;
                dayState = DAY;
            }
        }
        if (gp.currentMap == 2) {
            filterAlpha = 0.8f;
        }
    }
    
    public void draw(Graphics2D g2) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, filterAlpha));
        g2.drawImage(darknessFilter, 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
    }
}

