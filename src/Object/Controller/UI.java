package Object.Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import Object.Items.Item;
import Object.Items.StackableItem.*;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    int mouseX = 0;
    int mouseY = 0;
    public int selectedIndex;
    int scrollY = 0; // scroll posisi saat ini
    int maxScroll = 5; // max scroll, nanti dihitung dari banyaknya data
    public int selectedRecipeIndex = 0; // <<<< tambah ini di UI kamu
    public List<Rectangle> itemHitboxes = new ArrayList<>();
    public List<Item> itemList = new ArrayList<>(); // to match index with the rectangle
    public List<List<Item>> recipeKeys = new ArrayList<>(); 

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
        if (gp.gameState == gp.DROPPED_ITEM_STATE){
            boxAmount();
        }
    }

    public void drawPlusMinus() {
        int frameX = gp.TILE_SIZE * gp.MAX_SCREEN_COL - (gp.TILE_SIZE * 3);
        int frameY = gp.TILE_SIZE * gp.MAX_SCREEN_ROW - (gp.TILE_SIZE * 5);
        int frameWidth = gp.TILE_SIZE * 2;
        int frameHeight = gp.TILE_SIZE * 4;

        String plusBtn = "< + >";
        String amount = " 10 ";
        String minusBtn = "< - >";

        int posXPlus = frameX + (frameWidth - sentenceLength(plusBtn)) / 2 + 6;
        int posYPlus = frameY + frameHeight / 4;
        int posXMinus = frameX + (frameWidth - sentenceLength(plusBtn)) / 2 + 10;
        int posYMinus = frameY + frameHeight * 3 / 4;

        drawSubWindow(frameX, frameY, frameWidth + 10, frameHeight);

        g2.drawString(plusBtn, posXPlus, posYPlus);
        g2.drawString(amount, frameX + frameWidth / 2 - sentenceLength(plusBtn) / 2, frameY + frameHeight * 2 / 4);
        g2.drawString(minusBtn, posXMinus, posYMinus);

        if (isMouseOverButton(posXPlus, posYPlus, sentenceLength(plusBtn), 30)) {
            System.out.println("Mouse over plus button");
        }
        if (isMouseOverButton(posXMinus, posYMinus, sentenceLength(minusBtn), 30)) {
            System.out.println("Mouse over minus button");
        }
    }

    public int sentenceLength(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return length;
    }

    public boolean isMouseOverButton(int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    public void boxAmount(){
        int frameX = gp.TILE_SIZE * 5;
        int frameY = gp.TILE_SIZE * 5;
        int frameWidth = gp.TILE_SIZE * 14;
        int frameHeight = gp.TILE_SIZE * 14;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        if (mouseX >= frameX && mouseX <= frameX + frameWidth && mouseY >= frameY && mouseY <= frameY + frameHeight) {
            mouseX = 0;
            mouseY = 0;
            System.out.println("Mouse is over the button!");
        } 
    }

    // public void PlayerCraftMenu() {
    //     int frameX = gp.TILE_SIZE * 6;
    //     int frameY = gp.TILE_SIZE * 4;
    //     int frameWidth = gp.TILE_SIZE * 15;
    //     int frameHeight = gp.TILE_SIZE * 7;
    
    //     drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    
    //     Shape oldClip = g2.getClip(); 
    //     g2.setClip(frameX + 10, frameY + 10, frameWidth - 20, frameHeight - 20); 
    
    //     int contentX = frameX + 30;
    //     int contentY = frameY + 60 - scrollY;
    
    //     g2.drawString("Items to craft:", contentX, contentY);
    //     g2.drawString("Materials:", frameWidth - sentenceLength("Materials:"), contentY);
    //     contentY += 60;
    //     for (Map.Entry<List<Item>, Item> entry : gp.player.recipe.recipes.entrySet()) {
    //         List<Item> ingredients = entry.getKey();
    //         Item result = entry.getValue();
    
    //         g2.setColor(Color.WHITE);
    //         g2.drawImage(result.img, contentX, contentY - 25, 30, 30, null);
    //         Font font = new Font("Arial", Font.BOLD, 18); // Family = Arial, Style = Bold, Size = 30 VERSI KECIL
    //         g2.setFont(font);
    //         g2.drawString(String.valueOf(result.currentStack), contentX + 20, contentY + 5);
    //         g2.drawString(result.name, contentX + 50, contentY);

    //         int drawX = frameWidth - sentenceLength("Materials:") + 30;
    //         int drawPictureX = frameWidth - sentenceLength("Materials:") + 5;
    //         for (Item ingredient : ingredients) {
    //             g2.drawImage(ingredient.img, drawPictureX, contentY - 25, 30, 30, null);
    //             Font font2 = new Font("Arial", Font.BOLD, 18);
    //             g2.setFont(font2);
    //             g2.drawString(String.valueOf(ingredient.currentStack), drawX, contentY + 5);
    //             drawX += 50;
    //             drawPictureX += 50;
    //         }
    
    //         Rectangle hitbox = new Rectangle(contentX, contentY - 30 + scrollY, 200, 30);
    //         g2.drawRect(contentX, contentY - 30 + scrollY, 200, 300);
    //         itemHitboxes.add(hitbox);
    //         itemList.add(result);

    //         contentY += 60;
    //     }
    
    //     g2.setClip(oldClip);
    // }
    public void PlayerCraftMenu() {
        int frameX = gp.TILE_SIZE * 6;
        int frameY = gp.TILE_SIZE * 4;
        int frameWidth = gp.TILE_SIZE * 15;
        int frameHeight = gp.TILE_SIZE * 7;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Clear previous frame's data
        itemHitboxes.clear();
        itemList.clear();
        recipeKeys.clear();

        Shape oldClip = g2.getClip();
        g2.setClip(frameX + 10, frameY + 10, frameWidth - 20, frameHeight - 20);

        int contentX = frameX + 30;
        int contentY = frameY + 60 - scrollY;

        g2.drawString("Items to craft:", contentX, contentY);
        g2.drawString("Materials:", frameX + frameWidth - sentenceLength("Materials:") - 250, contentY);
        contentY += 60;

        for (Map.Entry<List<Item>, Item> entry : gp.player.recipe.recipes.entrySet()) {
            List<Item> ingredients = entry.getKey();
            Item result = entry.getValue();

            g2.setColor(Color.WHITE);
            g2.drawImage(result.img, contentX, contentY - 25, 30, 30, null);

            Font font = new Font("Arial", Font.BOLD, 18);
            g2.setFont(font);
            g2.drawString(String.valueOf(result.currentStack), contentX + 20, contentY + 5);
            g2.drawString(result.name, contentX + 50, contentY);

            int drawX = frameX + frameWidth - sentenceLength("Materials:") + 30 - 300;
            int drawPictureX = frameX + frameWidth - sentenceLength("Materials:") + 5 - 300;
            for (Item ingredient : ingredients) {
                g2.drawImage(ingredient.img, drawPictureX, contentY - 25, 30, 30, null);
                Font font2 = new Font("Arial", Font.BOLD, 18);
                g2.setFont(font2);
                g2.drawString(String.valueOf(ingredient.currentStack), drawX, contentY + 5);
                drawX += 50;
                drawPictureX += 50;
            }
            g2.drawRect(contentX, contentY - 35, frameWidth - 60, 50);

            // Create hitbox for the full row
            Rectangle hitbox = new Rectangle(contentX, contentY - 35, frameWidth - 60, 50);
            itemHitboxes.add(hitbox);
            itemList.add(result);
            recipeKeys.add(ingredients); // track the key (ingredient list)

            contentY += 60;
        }

        g2.setClip(oldClip);
    }

    public void scrollUp() {
        scrollY -= 20;
        if (scrollY < 0) scrollY = 0;
    }
    
    public void scrollDown() {
        scrollY += 20;
        maxScroll = gp.player.recipe.recipes.size() * 60;
        if (scrollY > maxScroll) scrollY = maxScroll;
    }

    public void checkCraftClick(int mouseX, int mouseY) {
        List<List<Item>> recipeKeys = new ArrayList<>();
        itemHitboxes.clear();
        itemList.clear();
        recipeKeys.clear();
        for (int i = 0; i < itemHitboxes.size(); i++) {
            if (itemHitboxes.get(i).contains(mouseX, mouseY)) {
                List<Item> ingredients = recipeKeys.get(i); // ← this is your key
                Item result = itemList.get(i);              // ← optional, if you need it

                // Do something with the clicked recipe
                System.out.println("Clicked recipe to craft: " + result.name);
                System.out.println("Needs: " + ingredients.size() + " ingredients");
            }
        }
    }

    public void drawSelectedItem() {
        int frameX = gp.TILE_SIZE * ((gp.SCREEN_WIDTH / gp.TILE_SIZE) / 4);
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 3);
        int frameWidth = gp.TILE_SIZE * 15;
        int frameHeight = gp.TILE_SIZE * 2;
        drawSubWindow(frameX, frameY, frameWidth + 10, frameHeight);

        int slotXStart = frameX + 30;
        int slotYStart = frameY + 15;
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
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI KECIL
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
            g2.drawString(gp.player.inventory.slots[slotCol].name, gp.TILE_SIZE * 12, cursorY - 50);
        }
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    public void drawInventory() {
        int frameX = gp.TILE_SIZE * ((gp.SCREEN_WIDTH / gp.TILE_SIZE) / 4);
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 13);
        int frameWidth = gp.TILE_SIZE * 15;
        int frameHeight = gp.TILE_SIZE * 8;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35;
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
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
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
