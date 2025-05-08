package Object;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import Object.Player.Player;
import Object.Items.StackableItem.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    public int selectedIndex;
    int scrollY = 0; // scroll posisi saat ini
    int maxScroll = 1000; // max scroll, nanti dihitung dari banyaknya data
    public int selectedRecipeIndex = 0; // <<<< tambah ini di UI kamu

    public UI (GamePanel gp) {
        this.gp = gp;
        selectedIndex = 0;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(Color.white);

        if (gp.gameState != gp.INVENTORY_STATE) {
            drawSelectedItem();
        }
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.INVENTORY_STATE) {
            drawInventory();
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
            PlayerCraftMenu();
        }
    }

    public void PlayerCraftMenu() {
        int frameX = gp.TILE_SIZE * 5;
        int frameY = gp.TILE_SIZE * 4;
        int frameWidth = gp.TILE_SIZE * 15;
        int frameHeight = gp.TILE_SIZE * 7;
    
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    
        Shape oldClip = g2.getClip(); 
        g2.setClip(frameX + 10, frameY + 10, frameWidth - 20, frameHeight - 20); 
    
        int contentX = frameX + 30;
        int contentY = frameY + 40 - scrollY;
    
        for (int i = 0; i < 20; i++) {
            int itemY = contentY + i * 40;

            if (i == selectedRecipeIndex) {
                g2.setColor(new Color(100, 100, 255)); // Biru transparan
                g2.fillRoundRect(contentX - 10, itemY - 25, frameWidth - 50, 35, 10, 10);
            }
    

            g2.setColor(Color.white);
            g2.drawString("Hello " + i, contentX, contentY + i * 40);
        }
    
        g2.setClip(oldClip); // Kembalikan clip ke semula
    
        drawScrollBar(frameX + frameWidth - 20, frameY + 10, 10, frameHeight - 30);
    }

    public void drawScrollBar(int x, int y, int width, int height) {
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(x, y, width, height, 10, 10);
    
        int totalContentHeight = 120 * 40; // total tinggi konten
        int visibleHeight = height; // tinggi area kotak (frame)
        int thumbHeight = (int) ((float) visibleHeight / totalContentHeight * visibleHeight);
    
        if (thumbHeight < 30) thumbHeight = 30; // Minimal ukuran thumb biar kelihatan
    
        int maxThumbPos = height - thumbHeight;
        int thumbY = y + (int) ((float) scrollY / (totalContentHeight - visibleHeight) * maxThumbPos);
    
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, thumbY, width, thumbHeight, 10, 10);
    }

    public void scrollUp() {
        scrollY -= 20;
        if (scrollY < 0) scrollY = 0;
    }
    
    public void scrollDown() {
        scrollY += 20;
        maxScroll = Math.max(0, (15 * 40) - 40);
        if (scrollY > maxScroll) scrollY = maxScroll;
    }

    public void drawSelectedItem() {
        int frameX = gp.TILE_SIZE * ((gp.SCREEN_WIDTH / gp.TILE_SIZE) / 4);
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 3);
        int frameWidth = gp.TILE_SIZE * 14;
        int frameHeight = gp.TILE_SIZE * 2;
        drawSubWindow(frameX, frameY, frameWidth + 10, frameHeight);

        int slotXStart = frameX + 18;
        int slotYStart = frameY + 18;
        int slotX = slotXStart;
        int slotY = slotYStart;

        for (int i = 0; i < 9; i++) {
            if (gp.player.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                slotX += (gp.TILE_SIZE + 25);
                continue;
            }
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawImage(gp.player.inventory.slots[i].img, slotX + 5, slotY + 5, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (gp.player.inventory.slots[i] instanceof Stackable) {
                Stackable stackableItem = (Stackable) gp.player.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            slotX += (gp.TILE_SIZE + 25);
        }

        int cursorX = slotXStart + ((gp.TILE_SIZE + 25) * slotCol);
        int cursorY = slotYStart + (gp.TILE_SIZE * slotRow);
        int cursorWidth = gp.TILE_SIZE + 10;
        int cursorHeight = gp.TILE_SIZE + 10;

        g2.setColor(Color.WHITE);
        if (gp.player.inventory.slots[slotCol] != null) {
            Font font = new Font("Arial", Font.BOLD, 25); // Family = Arial, Style = Bold, Size = 30
            g2.setFont(font);
            g2.drawString(gp.player.inventory.slots[slotCol].name, gp.TILE_SIZE * 14, cursorY - 50);
        }
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    public void drawInventory() {
        int frameX = gp.TILE_SIZE * ((gp.SCREEN_WIDTH / gp.TILE_SIZE) / 4);
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 14);
        int frameWidth = gp.TILE_SIZE * 15;
        int frameHeight = gp.TILE_SIZE * 7;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int slotXStart = frameX + 45;
        int slotYStart = frameY + 30;
        int slotX = slotXStart;
        int slotY = slotYStart;

        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            if (gp.player.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                if ((i + 1) % 9 == 0) {
                    slotX = slotXStart;
                    slotY += (gp.TILE_SIZE + 25);
                } else {
                    slotX += (gp.TILE_SIZE + 25);
                }
                continue;
            }
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawImage(gp.player.inventory.slots[i].img, slotX + 5, slotY + 5, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (gp.player.inventory.slots[i] instanceof Stackable) {
                Stackable stackableItem = (Stackable) gp.player.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if ((i + 1) % 9 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }

        int cursorX = slotXStart + ((gp.TILE_SIZE + 25) * slotCol);
        int cursorY = slotYStart + ((gp.TILE_SIZE + 25) * slotRow);
        int cursorWidth = gp.TILE_SIZE + 10;
        int cursorHeight = gp.TILE_SIZE + 10;

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    public void drawStats() {
        int frameX = gp.TILE_SIZE;
        int frameY = gp.TILE_SIZE;
        int frameWidth = gp.TILE_SIZE * 8;
        int frameHeight = gp.TILE_SIZE * 4;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public void drawPauseScreen() {
        String text = "PAUSED";

        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH / 2 - textLength / 2;
        int y = gp.SCREEN_HEIGHT / 2;

        g2.drawString(text, x, y);
    }

    public int getXCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (int)(gp.SCREEN_WIDTH / 2 - length/2);
        return x;
    }
}
