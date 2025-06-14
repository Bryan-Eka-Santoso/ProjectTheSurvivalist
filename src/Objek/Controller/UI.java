package Objek.Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import Objek.AchievementHandler.Achievement;
import Objek.Animal.*;
import Objek.Fish.Fish;
import Objek.Items.Item;
import Objek.Items.Buildings.Buildings;
import Objek.Items.Buildings.Chest;
import Objek.Items.Buildings.Kandang;
import Objek.Items.Buildings.KandangAyam;
import Objek.Items.Buildings.PigCage;
import Objek.Items.Buildings.SheepCage;
import Objek.Items.Buildings.CowCage;
import Objek.Items.StackableItem.*;
import Objek.Player.Player;
import Objek.Items.Buildings.Furnace;
import Objek.Items.Unstackable.FishingRod;
import Objek.Items.Unstackable.Armor.Armor;
import Objek.Items.Unstackable.Arsenals.*;
import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

public class UI {
    GamePanel gp;
    Graphics2D g2;
    public int slotCol = 0;
    public int slotRow = 0;
    public int amountToDrop = 1;
    public int selectedFurnace = 0;
    int mouseX = 0;
    int mouseY = 0;
    public int selectedIndex, selectedChestIndex;
    int scrollY = 0; // scroll posisi saat ini
    int maxScroll = 5; // max scroll, nanti dihitung dari banyaknya data
    public int selectedRecipeIndex = 0; // <<<< tambah ini di UI kamu
    public List<Rectangle> itemHitboxes = new ArrayList<>();
    public List<Item> itemList = new ArrayList<>(); // to match index with the rectangle
    public List<List<Item>> recipeKeys = new ArrayList<>(); 
    public boolean isPointingChest;
    private int kandangScrollPosition = 0;
    private int breedingScrollPosition = 0;
    private int getItemScrollPosition = 0;

    private static final int ANIMALS_PER_PAGE = 4;
    private Rectangle breedButton, getItemButton;
    
    public int achievementScroll = 0;
    public static final int ACHIEVEMENTS_PER_PAGE = 7; // Adjust as needed

    public boolean showNameInput = false;
    private String currentInput = "";
    private TameAnimal animalToName;
    private Kandang targetKandang;
    Rectangle textField;
    private BufferedImage woodBg;
    private Rectangle backButton;
    private TameAnimal selectedMale;
    private TameAnimal selectedFemale;
    public boolean inBreedingMenu = false;
    public boolean inGetItemMenu = false;
    public Rectangle getItemBackButton;
    public Rectangle removeButton;
    public Rectangle removeBackButton;
    public Rectangle breedConfirmButton;
    public Rectangle removeConfirmButton;
    public Rectangle getItemCollectButton;
    public boolean inRemoveMenu = false;
    public int selectedRemoveIndex = 0;
    private int removeScrollPosition = 0;
    public int selectedBreedMaleIndex = 0;
    public int selectedBreedFemaleIndex = 0;
    public int selectedGetItemIndex = 0;
    public boolean isMaleList = true;
    private boolean showKandangFullMessage = false;
    private long kandangFullMessageTimer = 0;
    private final long KANDANG_FULL_MESSAGE_DURATION = 2000;
    public boolean showWrongKandangMessage = false;
    public long wrongKandangMessageTimer = 0;
    public final long WRONG_KANDANG_MESSAGE_DURATION = 2000;

    public boolean canSelectInventory;
    public boolean isCanGoToSea = false;
    public boolean isNeedLevel15 = false;
    public boolean isCanGoToLand = false;
    public boolean isShowUnlockShip = false;
    public boolean isCanGoToCave = false;
    public boolean isCanGoToShop = false;
    public boolean isGoToShopMenu = false;
    public boolean isGoToEffectMenu = false;

    public Fish caughtFish;
    public int fishIndex;
    public int maxFishingStrength = 100;
    public boolean fishingSuccessful = false;
    public boolean canPressFishingButton = true;
    public long lastFishingButtonPressTime = 0;
    public final long FISHING_BUTTON_COOLDOWN = 800; // 800ms cooldown antara penekanan tombol
    public Random random = new Random();
    private boolean showRodRusak = false;
    private boolean showDapatIkan = false;
    private boolean showGagalDapatIkan = false;
    public boolean isSelectInventory = false;
    private long rodRusakTimer = 0;
    private long dapatIkanTimer = 0;
    private long gagalDapatIkanTimer = 0;
    private long messageUnlock = 0;
    private final long MESSAGE_DISPLAY_TIME = 3000;

    public int shopCategory = 0;
    public ArrayList<ShopItem> shopItems = new ArrayList<>();
    public ArrayList<Rectangle> shopItemRects = new ArrayList<>();
    public Rectangle shopExitButton;
    public Rectangle pauseQuitButton;
    public ArrayList<ShopEffect> effectItems = new ArrayList<>();
    public ArrayList<Rectangle> effectItemRects = new ArrayList<>();
    public Rectangle effectExitButton;
    public int effectCategory = 0;
    public boolean showPurchaseSuccess = false;
    public boolean showInsufficientFunds = false;
    public boolean showUnfulfilledRequirements = false;
    public boolean showLevelUpMessage = false;
    public long messageTimer = 0;
    public final long MESSAGE_DURATION = 2000;
    public boolean showNeedBucketMessage = false;
    public long needBucketMessageTimer = 0;
    public final long NEED_BUCKET_MESSAGE_DURATION = 2000;
    public ArrayList<ShopItem> getShopItemsByCategory(int category) {

    ArrayList<ShopItem> result = new ArrayList<>();
    
    for (ShopItem item : shopItems) {
        if (item.category == category) {
            result.add(item);
        }
    }
    
    return result;
}
    
    public UI (GamePanel gp) {
        this.gp = gp;
        selectedIndex = 0;
        selectedChestIndex = 0;
        isPointingChest = true;
        try {
            woodBg = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/bg-wood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(new Font("Arial", Font.PLAIN, 40));
        g2.setColor(Color.white);

        if(gp.gameState == gp.SHOP_STATE) {
            drawShopMenu();
        }
        if (gp.gameState == gp.ACHIEVEMENT_STATE) {
            drawAchievementMenu();
        }
        if(gp.gameState == gp.EFFECT_STATE) {
            drawEffectMenu();
        }
        if (gp.gameState == gp.GAME_OVER_STATE) {
            respawnMenu();
        }
        if (gp.gameState != gp.INVENTORY_STATE && gp.gameState != gp.OPEN_CHEST_STATE 
        && gp.gameState != gp.OPEN_SMELTER_STATE&& gp.gameState != gp.SHOP_STATE 
        && gp.gameState != gp.EFFECT_STATE && gp.gameState != gp.ACHIEVEMENT_STATE) {
            drawSelectedItem();
        }
        if (gp.gameState == gp.PAUSE_STATE) {
            drawPauseScreen();
        }
        if (gp.gameState == gp.INVENTORY_STATE) {
            drawInventory();
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE){
            gp.player.recipe.currentRecipe = gp.player.recipe.smallRecipes;
        }
        if (gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
            gp.player.recipe.currentRecipe = gp.player.recipe.recipes;
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE || gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
            PlayerCraftMenu();
        }
        if (gp.gameState == gp.DROPPED_ITEM_STATE) {
            drawAndGetStacks();
        }
        if (gp.gameState == gp.OPEN_CHEST_STATE) {
            drawChest((Chest) gp.buildings.get(gp.player.buildingIndex));
        }
        if (gp.gameState == gp.OPEN_SMELTER_STATE) {
            furnaceMenu();
        }
        if(gp.gameState == gp.KANDANG_STATE) {  
            if (inBreedingMenu) {
                drawBreedingMenu(g2, gp.currentKandang);
            }  else if(inRemoveMenu) { 
                 drawRemoveMenu(g2, gp.currentKandang);
            } else if(inGetItemMenu) {
                drawGetItemMenu(g2, gp.currentKandang);
            } else if (gp.currentKandang != null) {
                drawKandangMenu(g2, gp.currentKandang);
            }
        }
        if (gp.gameState != gp.OPEN_CHEST_STATE && gp.gameState != gp.OPEN_SMELTER_STATE 
            && gp.gameState != gp.INVENTORY_STATE && gp.gameState != gp.KANDANG_STATE && gp.gameState != gp.SHOP_STATE 
            && gp.gameState != gp.EFFECT_STATE && gp.gameState != gp.ACHIEVEMENT_STATE) {
            drawStats();
        }
        if (showNameInput) {
            drawNameInputWindow(g2);
        }
        if (showKandangFullMessage) {
            drawFullKandangMessage(g2);
        }
        if(showNeedBucketMessage) {
            drawNeedBucketMessage(g2);
        }
        if (showWrongKandangMessage) {
            drawWrongKandangMessage(g2);
        } 
        if (isCanGoToSea) {
            drawText("Press F to go to the sea", Color.GREEN);
        }
        if (isCanGoToShop) {
            drawText("Press space to go to the shop", Color.GREEN);
        }
        if (isCanGoToLand) {
            if(gp.currentMap == 2 || gp.currentMap == 3) {
                drawText("Press space to go to the land", Color.GREEN);
            } else {
                drawText("Press F to go to the land", Color.GREEN);
            }
        }
        if (isNeedLevel15) {
            drawText("Ship locked! Reach level 15 to unlock it", Color.GRAY);
        }
        if ((isGoToShopMenu || isGoToEffectMenu) && gp.gameState != gp.SHOP_STATE && gp.gameState != gp.EFFECT_STATE) {
            if(isGoToShopMenu) {
                drawText("Press space to access the shop menu", Color.GREEN);
            } else if(isGoToEffectMenu) {
                drawText("Press space to access the effect menu", Color.GREEN);
            }
        }
        if(gp.gameState == gp.FISHING_STATE) {
            drawFishingMinigame();
        }
        if(showRodRusak) {
            drawText("Your fishing rod is broken!", Color.RED);
            if(System.currentTimeMillis() - rodRusakTimer > MESSAGE_DISPLAY_TIME) {
                showRodRusak = false;
            }
        }
        if(showDapatIkan) {
            drawText("You successfully caught a " + caughtFish.nameFish + " fish!", Color.GREEN);
            if(System.currentTimeMillis() - dapatIkanTimer > MESSAGE_DISPLAY_TIME) {
                showDapatIkan = false;
            }
        }
        if(showGagalDapatIkan) {
            drawText("You failed to catch a " + caughtFish.nameFish + " fish!", Color.RED);
            if(System.currentTimeMillis() - gagalDapatIkanTimer > MESSAGE_DISPLAY_TIME) {
                showGagalDapatIkan = false;
            }
        }
        if(isShowUnlockShip) {
            drawText("Congrats!! You have unlocked the ship!", Color.GREEN);
            if(System.currentTimeMillis() - messageUnlock > MESSAGE_DISPLAY_TIME) {
                isShowUnlockShip = false;
            }
        }
        if(isCanGoToCave) {
            drawText("Press space to go to the cave", Color.GREEN);
        }
        if(showPurchaseSuccess) {
            drawText("Purchase successful!", Color.GREEN);
            if(System.currentTimeMillis() - messageTimer > MESSAGE_DURATION) {
                showPurchaseSuccess = false;
            }
        }
        if(showInsufficientFunds) {
            drawText("Not enough coins!", Color.RED);
            if(System.currentTimeMillis() - messageTimer > MESSAGE_DURATION) {
                showInsufficientFunds = false;
            }
        }
        if (showUnfulfilledRequirements) {
            drawText("No items detected for repairment!", Color.RED);
            if(System.currentTimeMillis() - messageTimer > MESSAGE_DURATION) {
                showUnfulfilledRequirements = false;
            }
        }
        if (showLevelUpMessage) {
            drawText("You've leveled up to " + gp.player.level + "!", Color.GREEN);
            if(System.currentTimeMillis() - messageTimer > MESSAGE_DURATION) {
                showLevelUpMessage = false;
            }
        }

        if (achievementToShow != null) {
            if (System.currentTimeMillis() - achievementNotificationTime < 3000) { // 3 seconds
                g2.setColor(new Color(0,0,0,180));
                g2.fillRoundRect(20, gp.SCREEN_HEIGHT - 240, 500, 95, 20, 20);
                g2.setFont(new Font("Arial", Font.BOLD, 20));
                g2.setColor(Color.YELLOW);
                g2.drawString("Achievement Unlocked!", 40, gp.SCREEN_HEIGHT - 210);
                g2.setColor(Color.WHITE);
                g2.drawString(achievementToShow.name, 40, gp.SCREEN_HEIGHT - 185);
                g2.drawString(achievementToShow.description, 40, gp.SCREEN_HEIGHT - 160);

                g2.setColor(new Color(255, 175, 255));
                g2.setStroke(new BasicStroke(4));
                g2.drawRoundRect(20, gp.SCREEN_HEIGHT - 240, 500, 95, 20, 20);
            } else {
                achievementToShow = null;
            }
        }
    }

    // In UI.java
    private long achievementNotificationTime = 0;
    private Achievement achievementToShow = null;

    public void showAchievementNotification(Achievement a) {
        achievementToShow = a;
        achievementNotificationTime = System.currentTimeMillis();
    }
    public void showNeedBucketMessage() {
        showNeedBucketMessage = true;
        needBucketMessageTimer = System.currentTimeMillis();
    }
    public void drawNeedBucketMessage(Graphics2D g2) {
        if(System.currentTimeMillis() - needBucketMessageTimer >= NEED_BUCKET_MESSAGE_DURATION) {
            showNeedBucketMessage = false;
        } else {
            int messageWidth = gp.TILE_SIZE * 8;
            int messageHeight = gp.TILE_SIZE * 3;
            int messageX = gp.SCREEN_WIDTH/2 - messageWidth/2;
            int messageY = gp.SCREEN_HEIGHT/2 - messageHeight/2;

            g2.drawImage(woodBg, messageX, messageY, messageWidth, messageHeight, null);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String message = "Need Empty Bucket!";
            int textX = messageX + (messageWidth - g2.getFontMetrics().stringWidth(message))/2;
            g2.drawString(message, textX, messageY + messageHeight/2);
        }
    }

    public void respawnMenu() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        g2.setFont(new Font("Arial", Font.BOLD, 50));
        String message = "You have died!";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT / 2 - 50;
        
        g2.setColor(Color.WHITE);
        g2.drawString(message, x, y);
        
        String respawnMessage = "Press R to respawn or Q to quit";
        String immortalityMessage = "";
        if (gp.player.inventory.hasItem("Immortality")){
            immortalityMessage = "Press E to use Immortality";
        } 
        int respawnX = getXCenteredText(respawnMessage);
        int respawnY = y + 60;
        
        g2.drawString(respawnMessage, respawnX, respawnY);
        g2.setColor(Color.MAGENTA);
        g2.drawString(immortalityMessage, getXCenteredText(immortalityMessage), respawnY + 60);
    }

     public void showRodRusakMessage() {
        showRodRusak = true;
        rodRusakTimer = System.currentTimeMillis();
    }

    public void showCongratsUnlockShip() {
        isShowUnlockShip = true;
        messageUnlock = System.currentTimeMillis();
    }

    public void showDapatIkanMessage(Fish fish) {
        showDapatIkan = true;
        dapatIkanTimer = System.currentTimeMillis();
        caughtFish = fish;
    }

    public void showGagalDapatIkanMessage(Fish fish) {
        showGagalDapatIkan = true;
        gagalDapatIkanTimer = System.currentTimeMillis();
        caughtFish = fish;
    }

    public void drawText(String message, Color Color) {
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 600;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 15;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color);
        g2.drawString(message, x, y);
    }

    public void showAnimalNameInput(TameAnimal animal, Kandang kandang) {
         if(kandang.getCurrentCapacity() >= kandang.getMaxCapacity()) {
            currentInput = "";
            animalToName = null;
            targetKandang = null;
            return;
        }
        if(kandang instanceof KandangAyam && !(animal instanceof Chicken)) {
            gp.ui.showWrongKandangMessage(); 
            return;
        }
        else if(kandang instanceof CowCage && !(animal instanceof Cow)) {
            gp.ui.showWrongKandangMessage();
            return;
        }
        else if(kandang instanceof SheepCage && !(animal instanceof Sheep)) {
            gp.ui.showWrongKandangMessage();
            return;
        }
        else if(kandang instanceof PigCage && !(animal instanceof Pig)) {
            gp.ui.showWrongKandangMessage();
            return;
        }
        showNameInput = true;
        currentInput = "";
        animalToName = animal;
        targetKandang = kandang;
        textField = new Rectangle(gp.SCREEN_WIDTH/2 - 100, gp.SCREEN_HEIGHT/2 + 10, 200, 30);
    }
    public void resetKandangMenuState() {
        inBreedingMenu = false;
        inGetItemMenu = false;
        selectedMale = null;
        selectedFemale = null;
        selectedBreedMaleIndex = 0;
        selectedBreedFemaleIndex = 0;
        selectedGetItemIndex = 0;
        isMaleList = true;
        canSelectInventory = true;
    }
    
    public void showKandangFullMessage() {
        showKandangFullMessage = true;
        kandangFullMessageTimer = System.currentTimeMillis();
    }

    public void showWrongKandangMessage() {
        showWrongKandangMessage = true;
        wrongKandangMessageTimer = System.currentTimeMillis();
    }

    public void drawWrongKandangMessage(Graphics2D g2) {
        if(System.currentTimeMillis() - wrongKandangMessageTimer >= WRONG_KANDANG_MESSAGE_DURATION) {
            showWrongKandangMessage = false;
        } else {
            int messageWidth = gp.TILE_SIZE * 8;
            int messageHeight = gp.TILE_SIZE * 3;
            int messageX = gp.SCREEN_WIDTH/2 - messageWidth/2;
            int messageY = gp.SCREEN_HEIGHT/2 - messageHeight/2;

            g2.drawImage(woodBg, messageX, messageY, messageWidth, messageHeight, null);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String message = "Wrong Cage Type!";
            int textX = messageX + (messageWidth - g2.getFontMetrics().stringWidth(message))/2;
            g2.drawString(message, textX, messageY + messageHeight/2);
        }
    }

    public void drawFullKandangMessage(Graphics2D g2){
        if(System.currentTimeMillis() - kandangFullMessageTimer >= KANDANG_FULL_MESSAGE_DURATION) {
            showKandangFullMessage = false;
        } else {
            int messageWidth = gp.TILE_SIZE * 8;
            int messageHeight = gp.TILE_SIZE * 3;
            int messageX = gp.SCREEN_WIDTH/2 - messageWidth/2;
            int messageY = gp.SCREEN_HEIGHT/2 - messageHeight/2;

            g2.drawImage(woodBg, messageX, messageY, messageWidth, messageHeight, null);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            String message = "Kandang is Full!";
            int textX = messageX + (messageWidth - g2.getFontMetrics().stringWidth(message))/2;
            g2.drawString(message, textX, messageY + messageHeight/2);
        }
    }

    public void drawFishingMinigame() {
        // Background semi-transparan
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        // Panel fishing
        int panelWidth = 400;
        int panelHeight = 300;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;
        
        g2.setColor(new Color(0, 60, 90));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(panelX+5, panelY+5, panelWidth-10, panelHeight-10, 10, 10);
        
        // Judul
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        String title = "Fishing Challenge!";
        int titleX = getXForCenteredText(title);
        g2.drawString(title, titleX, panelY + 50);
        // Gambar ikan
        BufferedImage fishImage = null;
        int imageSize = 80;
        
        if (caughtFish.nameFish.equalsIgnoreCase("Arwana")) {
            fishImage = caughtFish.right1;
        } else if (caughtFish.nameFish.equalsIgnoreCase("Belida")) {
            fishImage = caughtFish.right1;
        } else if (caughtFish.nameFish.equalsIgnoreCase("Golden")) {
            fishImage = caughtFish.right1;
        }
        
        if (fishImage != null) {
            g2.drawImage(fishImage, gp.SCREEN_WIDTH/2 - imageSize/2, panelY + 70, imageSize, imageSize, null);
        }
        
        // Nama ikan
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        String fishName = caughtFish.nameFish;
        int textX = getXForCenteredText(fishName);
        g2.drawString(fishName, textX, panelY + 180);
        
        // Kekuatan ikan
        g2.setFont(g2.getFont().deriveFont(16F));
        g2.drawString("Fish Strength: " + caughtFish.strength, panelX + 50, panelY + 210);
        g2.drawString("Player Rod: " + gp.player.strengthRod, panelX + 50, panelY + 235);
        
        // Progress bar
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(panelX + 50, panelY + 250, 300, 20);

        if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof FishingRod) {
            if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength > 0) {
                if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength <= 33) {
                    g2.setColor(Color.RED);
                } else if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength <= 66) {
                    g2.setColor(Color.YELLOW);
                } else {
                    g2.setColor(Color.GREEN);
                }
                g2.fillRect(panelX + 50, panelY + 250, ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength * 3, 20);
            }
        }
        // Border untuk progress bar
        g2.setColor(Color.WHITE);
        g2.drawRect(panelX + 50, panelY + 250, 300, 20);
        
        // Instruksi
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        g2.drawString("Press ENTER to pull the fishing rod!", panelX + 65, panelY + 285);
    }

    // Add this utility method if it doesn't exist already
    public int getXForCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH/2 - length/2;
        return x;
    }

    public void handleBreedingKeyPress(int keyCode, Kandang kandang) {
        ArrayList<TameAnimal> males = new ArrayList<>();
        ArrayList<TameAnimal> females = new ArrayList<>();
        
        if(kandang instanceof KandangAyam) {
            for(TameAnimal animal : ((KandangAyam)kandang).chickensInCage) {
                if(animal.getGender().equals("Male") && animal.isReadyBreeding()) 
                    males.add(animal);
                else if(animal.getGender().equals("Female") && animal.isReadyBreeding())
                    females.add(animal);
            }
        } else if(kandang instanceof CowCage) {
            for(Cow cow : ((CowCage)kandang).cowsInCage) {
                if(cow.getGender().equals("Male") && cow.isReadyBreeding()) 
                    males.add(cow);
                else if(cow.getGender().equals("Female") && cow.isReadyBreeding())
                    females.add(cow);
            }
        } else if(kandang instanceof SheepCage) {
            for(Sheep sheep : ((SheepCage)kandang).sheepsInCage) {
                if(sheep.getGender().equals("Male") && sheep.isReadyBreeding()) 
                    males.add(sheep);
                else if(sheep.getGender().equals("Female") && sheep.isReadyBreeding())
                    females.add(sheep);
            }
        } else if(kandang instanceof PigCage) {
            for(Pig pig : ((PigCage)kandang).pigsInCage) {
                if(pig.getGender().equals("Male") && pig.isReadyBreeding()) 
                    males.add(pig);
                else if(pig.getGender().equals("Female") && pig.isReadyBreeding())
                    females.add(pig);
            }
        }
        if(kandang.getCurrentCapacity() >= kandang.getMaxCapacity()) {
            inBreedingMenu = false; 
            selectedMale = null;
            selectedFemale = null;
            selectedBreedMaleIndex = 0;
            selectedBreedFemaleIndex = 0;
            isMaleList = true;
            return;
        }
        else if(keyCode == KeyEvent.VK_UP) {
            if(isMaleList) {
                if(selectedBreedMaleIndex > 0) selectedBreedMaleIndex--;
            } else {
                if(selectedBreedFemaleIndex > 0) selectedBreedFemaleIndex--;
            }
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            if(isMaleList) {
                if(selectedBreedMaleIndex < males.size() - 1) selectedBreedMaleIndex++;
            } else {
                if(selectedBreedFemaleIndex < females.size() - 1) selectedBreedFemaleIndex++;
            }
        }
        else if(keyCode == KeyEvent.VK_ENTER) {
            if(isMaleList && selectedBreedMaleIndex < males.size()) {
               
                selectedMale = males.get(selectedBreedMaleIndex);
                if(!females.isEmpty()) {
                    isMaleList = false;
                }
            }
             else if(!isMaleList && selectedBreedFemaleIndex < females.size()) {
                selectedFemale = females.get(selectedBreedFemaleIndex);

                if(selectedMale != null && selectedFemale != null) {
                    TameAnimal baby = null;
                    if(kandang instanceof KandangAyam) {
                        baby = ((Chicken)selectedMale).breeding((Chicken)selectedFemale, gp);
                    }
                    else if(kandang instanceof CowCage) {
                        baby = ((Cow)selectedMale).breeding((Cow)selectedFemale, gp);
                    }
                    else if(kandang instanceof SheepCage) {
                        baby = ((Sheep)selectedMale).breeding((Sheep)selectedFemale, gp);
                    }
                    else if(kandang instanceof PigCage) {
                        baby = ((Pig)selectedMale).breeding((Pig)selectedFemale, gp);
                    }
                    if(baby != null) {
                        showNameInput = true;
                        animalToName = baby;
                        targetKandang = kandang;
                        currentInput = "";
                        
                        selectedMale.setReadyBreeding(false);
                        selectedFemale.setReadyBreeding(false);
                        inBreedingMenu = false;
                        selectedMale = null;
                        selectedFemale = null;
                        selectedBreedMaleIndex = 0;
                        selectedBreedFemaleIndex = 0;
                        isMaleList = true;
                    }
                }
            }
        }
    }
    public void handleRemoveKeyPress(int keyCode, Kandang kandang, Player player) {
        ArrayList<TameAnimal> animals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            animals.addAll(((KandangAyam)kandang).chickensInCage);
        } else if(kandang instanceof CowCage) {
            animals.addAll(((CowCage)kandang).cowsInCage);
        } else if(kandang instanceof SheepCage) {
            animals.addAll(((SheepCage)kandang).sheepsInCage);
        } else if(kandang instanceof PigCage) {
            animals.addAll(((PigCage)kandang).pigsInCage);
        }

        if(keyCode == KeyEvent.VK_UP) {
            if(selectedRemoveIndex > 0) selectedRemoveIndex--;
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            if(selectedRemoveIndex < animals.size() - 1) selectedRemoveIndex++;
        }
        else if(keyCode == KeyEvent.VK_ENTER && !animals.isEmpty()) {
            if(player.grabbedAnimal == null){
                TameAnimal animal = animals.get(selectedRemoveIndex);
                if(kandang instanceof KandangAyam) {
                    ((KandangAyam)kandang).chickensInCage.remove(animal);
                } else if(kandang instanceof CowCage) {
                    ((CowCage)kandang).cowsInCage.remove(animal);
                } else if(kandang instanceof SheepCage) {
                    ((SheepCage)kandang).sheepsInCage.remove(animal);
                } else if(kandang instanceof PigCage) {
                    ((PigCage)kandang).pigsInCage.remove(animal);
                }
                player.grabbedAnimal = animal;
                gp.gameState = gp.PLAY_STATE;
                inRemoveMenu = false;
                selectedRemoveIndex = 0;
            }
        }
    }
    public void handleGetItemKeyPress(int keyCode, Kandang kandang, Player player) {
        ArrayList<TameAnimal> readyAnimals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            for(Chicken chicken : ((KandangAyam)kandang).chickensInCage) {
                if(chicken.isReadyGetItem()) {
                    readyAnimals.add(chicken);
                }
            }
        } else if(kandang instanceof CowCage) {
            for(Cow cow : ((CowCage)kandang).cowsInCage) {
                if(cow.isReadyGetItem()) {
                    readyAnimals.add(cow);
                }
            }
        }
        else if(kandang instanceof SheepCage) {
            for(Sheep sheep : ((SheepCage)kandang).sheepsInCage) {
                if(sheep.isReadyGetItem()) {
                    readyAnimals.add(sheep);
                }
            }
        }
        else if(kandang instanceof PigCage) {
            for(Pig pig : ((PigCage)kandang).pigsInCage) {
                if(pig.isReadyGetItem()) {
                    readyAnimals.add(pig);
                }
            }
        }

        if(keyCode == KeyEvent.VK_UP) {
            if(selectedGetItemIndex > 0) selectedGetItemIndex--;
        }
        else if(keyCode == KeyEvent.VK_DOWN) {
            if(selectedGetItemIndex < readyAnimals.size() - 1) selectedGetItemIndex++;
        }
        else if(keyCode == KeyEvent.VK_ENTER && !readyAnimals.isEmpty()) {
            TameAnimal animal = readyAnimals.get(selectedGetItemIndex);
            if(animal instanceof Chicken) {
                ((Chicken)animal).getItem(player);
            }
            else if(animal instanceof Cow) {
                ((Cow)animal).getItem(player);
            }
            else if(animal instanceof Sheep) {
                ((Sheep)animal).getItem(player);
            }
            else if(animal instanceof Pig) {
                ((Pig)animal).getItem(player);
            }
            inGetItemMenu = false;
            selectedGetItemIndex = 0;
        }
    }
    
    public void drawGetItemMenu(Graphics2D g2, Kandang kandang) {
        int windowWidth = gp.TILE_SIZE * 14;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        // Draw wooden background with border shadow
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(windowX-5, windowY-5, windowWidth+10, windowHeight+10, 20, 20);
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);
        
        // Decorative hay frame
        g2.setColor(new Color(218, 165, 32, 180));
        g2.fillRoundRect(windowX+10, windowY+10, windowWidth-20, windowHeight-20, 15, 15);
        
        // Title banner
        g2.setColor(new Color(120, 60, 30));
        g2.fillRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = "Collect Products";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 60);

        // Animal list container
        g2.setColor(new Color(30, 15, 0, 160));
        g2.fillRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);

        // Get ready animals
        ArrayList<TameAnimal> readyAnimals = new ArrayList<>();
        
        if(kandang instanceof KandangAyam) {
            for(Chicken chicken : ((KandangAyam)kandang).chickensInCage) {
                if(chicken.isReadyGetItem()) {
                    readyAnimals.add(chicken);
                }
            }
        } else if(kandang instanceof CowCage) {
            for(Cow cow : ((CowCage)kandang).cowsInCage) {
                if(cow.isReadyGetItem()) readyAnimals.add(cow);
            }
        } else if(kandang instanceof SheepCage) {
            for(Sheep sheep : ((SheepCage)kandang).sheepsInCage) {
                if(sheep.isReadyGetItem()) readyAnimals.add(sheep);
            }
        } else if(kandang instanceof PigCage) {
            for(Pig pig : ((PigCage)kandang).pigsInCage) {
                if(pig.isReadyGetItem()) readyAnimals.add(pig);
            }
        }

        int startY = windowY + 120;
        int lineHeight = 40;

        // Headers
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(255, 230, 180));
        g2.drawString("Animal", windowX + 35, startY);
        g2.drawString("Product", windowX + windowWidth - 200, startY);
        
        // Draw horizontal line below headers
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(windowX + 30, startY + 10, windowX + windowWidth - 30, startY + 10);

        if(readyAnimals.isEmpty()) {
            g2.setFont(new Font("Arial", Font.ITALIC, 18));
            g2.setColor(new Color(255, 200, 150));
            g2.drawString("No animals ready to collect products!", windowX + (windowWidth/2) - 150, startY + 50);
        } else {
            int endIndex = Math.min(getItemScrollPosition + ANIMALS_PER_PAGE, readyAnimals.size());
            for(int i = getItemScrollPosition; i < endIndex; i++) {
                TameAnimal animal = readyAnimals.get(i);
                int y = startY + 35 + (i - getItemScrollPosition) * lineHeight;
                
                // Alternate row backgrounds
                if((i - getItemScrollPosition) % 2 == 0) {
                    g2.setColor(new Color(120, 80, 40, 120));
                } else {
                    g2.setColor(new Color(90, 60, 30, 120));
                }
                g2.fillRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                
                // Selection highlight
                if(i == selectedGetItemIndex) {
                    g2.setColor(new Color(70, 100, 170, 180));
                    g2.fillRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                    g2.setColor(new Color(200, 255, 255));
                    g2.drawRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                }
                
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                g2.drawString(animal.getName(), windowX + 40, y);
                
                // Product name
                String productName = "";
                if(animal instanceof Chicken) productName = "Egg";
                else if(animal instanceof Cow) productName = "Milk";
                else if(animal instanceof Sheep) productName = "Wool";
                else if(animal instanceof Pig) productName = "Meat";
                
                g2.drawString(productName, windowX + windowWidth - 200, y);
            }
            
            // Scrollbar if needed
            if(readyAnimals.size() > ANIMALS_PER_PAGE) {
                int scrollBarHeight = 200;
                int scrollBarWidth = 15;
                int scrollBarX = windowX + windowWidth - 35;
                int scrollBarY = windowY + 130;
                
                // Draw scrollbar background
                g2.setColor(new Color(60, 30, 0, 120));
                g2.fillRoundRect(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight, 7, 7);
                
                // Draw scrollbar handle
                float handleRatio = (float)ANIMALS_PER_PAGE / readyAnimals.size();
                int handleHeight = Math.max(30, (int)(scrollBarHeight * handleRatio));
                int maxScroll = readyAnimals.size() - ANIMALS_PER_PAGE;
                int handleY = scrollBarY + (int)(((float)getItemScrollPosition / Math.max(1, maxScroll)) * (scrollBarHeight - handleHeight));
                
                g2.setColor(new Color(200, 150, 80));
                g2.fillRoundRect(scrollBarX, handleY, scrollBarWidth, handleHeight, 7, 7);
            }
        }

        // Buttons
        int buttonWidth = 120;
        int buttonHeight = 40;
        
        // Back button
        getItemBackButton = new Rectangle(windowX + 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
        drawKandangButton(g2, getItemBackButton, "Back", new Color(70, 40, 20), new Color(100, 60, 30));
        
        // Collect button (only show if an animal is selected)
        if(selectedGetItemIndex >= 0 && selectedGetItemIndex < readyAnimals.size()) {
            getItemCollectButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
            drawKandangButton(g2, getItemCollectButton, "Collect", new Color(70, 90, 140), new Color(90, 120, 180));
        }
    }

    public void drawBreedingMenu(Graphics2D g2, Kandang kandang) {
        int windowWidth = gp.TILE_SIZE * 14;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;  
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;
        
        // Draw wooden background with border shadow
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(windowX-5, windowY-5, windowWidth+10, windowHeight+10, 20, 20);
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);
        
        // Decorative hay frame
        g2.setColor(new Color(218, 165, 32, 180));
        g2.fillRoundRect(windowX+10, windowY+10, windowWidth-20, windowHeight-20, 15, 15);
        
        // Title banner
        g2.setColor(new Color(120, 60, 30));
        g2.fillRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = "Breeding Menu";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 60);

        if(kandang.getCurrentCapacity() >= kandang.getMaxCapacity()) {
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.setColor(new Color(255, 100, 100));
            String fullMsg = "Cage is full! Remove animals first.";
            g2.drawString(fullMsg, windowX + (windowWidth - g2.getFontMetrics().stringWidth(fullMsg))/2, windowY + 130);
        } else {
            // Animal list containers
            g2.setColor(new Color(30, 15, 0, 160));
            // Left panel for males
            g2.fillRoundRect(windowX + 20, windowY + 100, windowWidth/2 - 30, windowHeight - 180, 15, 15);
            // Right panel for females
            g2.fillRoundRect(windowX + windowWidth/2 + 10, windowY + 100, windowWidth/2 - 30, windowHeight - 180, 15, 15);
            
            g2.setColor(Color.WHITE);
            g2.drawRoundRect(windowX + 20, windowY + 100, windowWidth/2 - 30, windowHeight - 180, 15, 15);
            g2.drawRoundRect(windowX + windowWidth/2 + 10, windowY + 100, windowWidth/2 - 30, windowHeight - 180, 15, 15);

            // Headers
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(new Color(255, 230, 180));
            g2.drawString("Male Animals", windowX + 30, windowY + 120);
            g2.drawString("Female Animals", windowX + windowWidth/2 + 20, windowY + 120);
            
            // Get animals by gender
            ArrayList<TameAnimal> males = new ArrayList<>();
            ArrayList<TameAnimal> females = new ArrayList<>();
            
            if(kandang instanceof KandangAyam) {
                for(Chicken chicken : ((KandangAyam)kandang).chickensInCage) {
                    if(chicken.isReadyBreeding()) {
                        if(chicken.getGender().equalsIgnoreCase("Male")) males.add(chicken);
                        else females.add(chicken);
                    }
                }
            } else if(kandang instanceof CowCage) {
                for(Cow cow : ((CowCage)kandang).cowsInCage) {
                    if(cow.isReadyBreeding()) {
                        if(cow.getGender().equalsIgnoreCase("Male")) males.add(cow);
                        else females.add(cow);
                    }
                }
            } else if(kandang instanceof SheepCage) {
                for(Sheep sheep : ((SheepCage)kandang).sheepsInCage) {
                    if(sheep.isReadyBreeding()) {
                        if(sheep.getGender().equalsIgnoreCase("Male")) males.add(sheep);
                        else females.add(sheep);
                    }
                }
            } else if(kandang instanceof PigCage) {
                for(Pig pig : ((PigCage)kandang).pigsInCage) {
                    if(pig.isReadyBreeding()) {
                        if(pig.getGender().equalsIgnoreCase("Male")) males.add(pig);
                        else females.add(pig);
                    }
                }
            }

            int startY = windowY + 150;
            int lineHeight = 35;

            // Draw male list
            if(males.isEmpty()) {
                g2.setFont(new Font("Arial", Font.ITALIC, 16));
                g2.setColor(new Color(255, 200, 150));
                g2.drawString("No breeding-ready males", windowX + 30, startY + 30);
            } else {
                int endIndexMale = Math.min(breedingScrollPosition + ANIMALS_PER_PAGE, males.size());
                for(int i = breedingScrollPosition; i < endIndexMale; i++) {
                    TameAnimal male = males.get(i);
                    int y = startY + (i - breedingScrollPosition) * lineHeight;
                    
                    // Row highlight for selected
                    if(i == selectedBreedMaleIndex && isMaleList) {
                        g2.setColor(new Color(140, 100, 60, 180));
                        g2.fillRoundRect(windowX + 25, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                        g2.setColor(new Color(255, 255, 200));
                        g2.drawRoundRect(windowX + 25, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                    }
                    
                    // Alternating row backgrounds
                    else if((i - breedingScrollPosition) % 2 == 0) {
                        g2.setColor(new Color(120, 80, 40, 80));
                        g2.fillRoundRect(windowX + 25, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                    }

                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    g2.drawString(male.getName() + " ♂", windowX + 35, y + 10);
                }
            }

            // Draw female list
            if(females.isEmpty()) {
                g2.setFont(new Font("Arial", Font.ITALIC, 16));
                g2.setColor(new Color(255, 200, 150));
                g2.drawString("No breeding-ready females", windowX + windowWidth/2 + 20, startY + 30);
            } else {
                int endIndexFemale = Math.min(breedingScrollPosition + ANIMALS_PER_PAGE, females.size());
                for(int i = breedingScrollPosition; i < endIndexFemale; i++) {
                    TameAnimal female = females.get(i);
                    int y = startY + (i - breedingScrollPosition) * lineHeight;
                    
                    // Row highlight for selected
                    if(i == selectedBreedFemaleIndex && !isMaleList) {
                        g2.setColor(new Color(140, 100, 60, 180));
                        g2.fillRoundRect(windowX + windowWidth/2 + 15, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                        g2.setColor(new Color(255, 255, 200));
                        g2.drawRoundRect(windowX + windowWidth/2 + 15, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                    }
                    // Alternating row backgrounds
                    else if((i - breedingScrollPosition) % 2 == 0) {
                        g2.setColor(new Color(120, 80, 40, 80));
                        g2.fillRoundRect(windowX + windowWidth/2 + 15, y - 10, windowWidth/2 - 40, lineHeight, 8, 8);
                    }

                    g2.setColor(Color.WHITE);
                    g2.setFont(new Font("Arial", Font.BOLD, 16));
                    g2.drawString(female.getName() + " ♀", windowX + windowWidth/2 + 25, y + 10);
                }
            }

            // Selection message
            if(selectedMale != null) {
                g2.setColor(new Color(255, 255, 200));
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                g2.fillRoundRect(windowX + 20, windowY + windowHeight - 100, windowWidth - 40, 30, 8, 8);
                g2.setColor(new Color(70, 40, 10));
                g2.drawString("Selected male: " + selectedMale.getName(), windowX + 30, windowY + windowHeight - 80);
                
                if(!females.isEmpty()) {
                    g2.setColor(new Color(255, 255, 200));
                    g2.fillRoundRect(windowX + 20, windowY + windowHeight - 70, windowWidth - 40, 30, 8, 8);
                    g2.setColor(new Color(70, 40, 10));
                    g2.drawString("Please select a female", windowX + (windowWidth - g2.getFontMetrics().stringWidth("Please select a female"))/2, windowY + windowHeight - 50);
                }
            }
        }

        // Back Button
        int buttonWidth = 120;
        int buttonHeight = 40;
        backButton = new Rectangle(windowX + 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
        drawKandangButton(g2, backButton, "Back", new Color(70, 40, 20), new Color(100, 60, 30));

        // Only show Breed button if a male is selected
        if(selectedMale != null) {
            breedConfirmButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
            drawKandangButton(g2, breedConfirmButton, "Breed", new Color(90, 130, 70), new Color(120, 170, 90));
        }
    }
    
    public void drawRemoveMenu(Graphics2D g2, Kandang kandang) {
        int windowWidth = gp.TILE_SIZE * 14;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        // Draw wooden background with border shadow
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(windowX-5, windowY-5, windowWidth+10, windowHeight+10, 20, 20);
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);
        
        // Decorative hay frame
        g2.setColor(new Color(218, 165, 32, 180));
        g2.fillRoundRect(windowX+10, windowY+10, windowWidth-20, windowHeight-20, 15, 15);
        
        // Title banner
        g2.setColor(new Color(120, 60, 30));
        g2.fillRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = "Remove Animal";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 60);

        // Animal list container
        g2.setColor(new Color(30, 15, 0, 160));
        g2.fillRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);

        // Get all animals
        ArrayList<TameAnimal> animals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            animals.addAll(((KandangAyam)kandang).chickensInCage);
        } else if(kandang instanceof CowCage) {
            animals.addAll(((CowCage)kandang).cowsInCage);
        } else if(kandang instanceof SheepCage) {
            animals.addAll(((SheepCage)kandang).sheepsInCage);
        } else if(kandang instanceof PigCage) {
            animals.addAll(((PigCage)kandang).pigsInCage);
        }

        int startY = windowY + 120;
        int lineHeight = 40;

        // Headers
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(new Color(255, 230, 180));
        g2.drawString("Animal", windowX + 35, startY);
        g2.drawString("Gender", windowX + windowWidth - 200, startY);
        
        // Draw horizontal line below headers
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(windowX + 30, startY + 10, windowX + windowWidth - 30, startY + 10);

        if(animals.isEmpty()) {
            g2.setFont(new Font("Arial", Font.ITALIC, 18));
            g2.setColor(new Color(255, 200, 150));
            g2.drawString("No animals in cage!", windowX + (windowWidth/2) - 80, startY + 50);
        } else {
            int endIndex = Math.min(removeScrollPosition + ANIMALS_PER_PAGE, animals.size());
            for(int i = removeScrollPosition; i < endIndex; i++) {
                TameAnimal animal = animals.get(i);
                int y = startY + 35 + (i - removeScrollPosition) * lineHeight;
                
                // Alternate row backgrounds
                if((i - removeScrollPosition) % 2 == 0) {
                    g2.setColor(new Color(120, 80, 40, 120));
                } else {
                    g2.setColor(new Color(90, 60, 30, 120));
                }
                g2.fillRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                
                // Selection highlight
                if(i == selectedRemoveIndex) {
                    g2.setColor(new Color(180, 100, 70, 180));
                    g2.fillRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                    g2.setColor(new Color(255, 255, 200));
                    g2.drawRoundRect(windowX + 30, y - 20, windowWidth - 60, lineHeight, 8, 8);
                }
                
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 16));
                g2.drawString(animal.getName(), windowX + 40, y);
                
                // Gender with symbol
                String genderText = animal.getGender() + " " + (animal.getGender().equalsIgnoreCase("Male") ? "♂" : "♀");
                g2.drawString(genderText, windowX + windowWidth - 200, y);
            }
            
            // Scrollbar if needed
            if(animals.size() > ANIMALS_PER_PAGE) {
                int scrollBarHeight = 200;
                int scrollBarWidth = 15;
                int scrollBarX = windowX + windowWidth - 35;
                int scrollBarY = windowY + 130;
                
                // Draw scrollbar background
                g2.setColor(new Color(60, 30, 0, 120));
                g2.fillRoundRect(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight, 7, 7);
                
                // Draw scrollbar handle
                float handleRatio = (float)ANIMALS_PER_PAGE / animals.size();
                int handleHeight = Math.max(30, (int)(scrollBarHeight * handleRatio));
                int maxScroll = animals.size() - ANIMALS_PER_PAGE;
                int handleY = scrollBarY + (int)(((float)removeScrollPosition / Math.max(1, maxScroll)) * (scrollBarHeight - handleHeight));
                
                g2.setColor(new Color(200, 150, 80));
                g2.fillRoundRect(scrollBarX, handleY, scrollBarWidth, handleHeight, 7, 7);
            }
        }

        // Buttons
        int buttonWidth = 120;
        int buttonHeight = 40;
        
        // Back button
        removeBackButton = new Rectangle(windowX + 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
        drawKandangButton(g2, removeBackButton, "Back", new Color(70, 40, 20), new Color(100, 60, 30));
        
        // Remove button (only show if an animal is selected)
        if(selectedRemoveIndex >= 0) {
            removeConfirmButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
            drawKandangButton(g2, removeConfirmButton, "Remove", new Color(170, 70, 70), new Color(210, 90, 90));
        }
    }

    public void drawKandangMenu(Graphics2D g2, Kandang kandang) {
        int windowWidth = gp.TILE_SIZE * 14;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;  
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;
        
        // Draw wooden background with border
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(windowX-5, windowY-5, windowWidth+10, windowHeight+10, 20, 20);
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);
        
        // Decorative hay frame
        g2.setColor(new Color(218, 165, 32, 180));
        g2.fillRoundRect(windowX+10, windowY+10, windowWidth-20, windowHeight-20, 15, 15);
        
        // Title with decorative barn icon
        g2.setColor(new Color(120, 60, 30));
        g2.fillRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = kandang.getName() + " (" + kandang.getCurrentCapacity() + "/" + kandang.getMaxCapacity() + ")";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 60);
        
        // Animal list container
        g2.setColor(new Color(30, 15, 0, 160));
        g2.fillRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);
        g2.setColor(new Color(255, 255, 255));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(windowX + 20, windowY + 100, windowWidth - 40, windowHeight - 180, 15, 15);
        
        // Get animal list based on cage type
        ArrayList<TameAnimal> animals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            animals.addAll(((KandangAyam)kandang).chickensInCage);
        } else if(kandang instanceof CowCage) {
            animals.addAll(((CowCage)kandang).cowsInCage);
        } else if(kandang instanceof SheepCage) {
            animals.addAll(((SheepCage)kandang).sheepsInCage);
        } else if(kandang instanceof PigCage) {
            animals.addAll(((PigCage)kandang).pigsInCage);
        }
        
        // Display animals with nicer formatting
        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        int startY = windowY + 140;
        int lineHeight = 32;
        int endIndex = Math.min(kandangScrollPosition + ANIMALS_PER_PAGE, animals.size());
        
        // Draw column headers
        g2.setColor(new Color(255, 230, 180));
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.drawString("Animal", windowX + 30, startY - 10);
        g2.drawString("Breeding Status", windowX + windowWidth/2 - 70, startY - 10);
        g2.drawString("Product Status", windowX + windowWidth - 160, startY - 10);
        
        // Draw horizontal line below headers
        g2.setStroke(new BasicStroke(1));
        g2.drawLine(windowX + 30, startY, windowX + windowWidth - 30, startY);
        
        // Draw each animal with alternating row colors
        for(int i = kandangScrollPosition; i < endIndex; i++) {
            TameAnimal animal = animals.get(i);
            int y = startY + 25 + (i - kandangScrollPosition) * (lineHeight + 10);
            
            // Alternate row backgrounds
            if((i - kandangScrollPosition) % 2 == 0) {
                g2.setColor(new Color(120, 80, 40, 120));
            } else {
                g2.setColor(new Color(90, 60, 30, 120));
            }
            g2.fillRoundRect(windowX + 25, y - 20, windowWidth - 50, lineHeight + 5, 10, 10);
            
            // Animal name with gender icon
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            String genderIcon = animal.getGender().equalsIgnoreCase("Male") ? "♂" : "♀";
            g2.drawString(animal.getName() + " " + genderIcon, windowX + 30, y);
            
            // Breeding status with color indicator
            String breedingStatus = animal.isReadyBreeding() ? "Ready" : "Not Ready";
            g2.setColor(animal.isReadyBreeding() ? new Color(100, 255, 100) : new Color(255, 180, 100));
            g2.drawString(breedingStatus, windowX + windowWidth/2 - 70, y);
            
            // Product status with color indicator
            if(animal instanceof Chicken || animal instanceof Cow || animal instanceof Sheep || animal instanceof Pig) {
                String productStatus = ((TameAnimal)animal).isReadyGetItem() ? "Ready" : "Not Ready";
                g2.setColor(((TameAnimal)animal).isReadyGetItem() ? new Color(100, 255, 100) : new Color(255, 180, 100));
                g2.drawString(productStatus, windowX + windowWidth - 160, y);
            }
        }
        
        // Scrollbar if needed
        if(animals.size() > ANIMALS_PER_PAGE) {
            int scrollBarHeight = 200;
            int scrollBarWidth = 15;
            int scrollBarX = windowX + windowWidth - 35;
            int scrollBarY = windowY + 130;
            
            // Draw scrollbar background
            g2.setColor(new Color(60, 30, 0, 120));
            g2.fillRoundRect(scrollBarX, scrollBarY, scrollBarWidth, scrollBarHeight, 7, 7);
            
            // Draw scrollbar handle
            float handleRatio = (float)ANIMALS_PER_PAGE / animals.size();
            int handleHeight = Math.max(30, (int)(scrollBarHeight * handleRatio));
            int maxScroll = animals.size() - ANIMALS_PER_PAGE;
            int handleY = scrollBarY + (int)(((float)kandangScrollPosition / maxScroll) * (scrollBarHeight - handleHeight));
            
            g2.setColor(new Color(200, 150, 80));
            g2.fillRoundRect(scrollBarX, handleY, scrollBarWidth, handleHeight, 7, 7);
        }
        
        // Action buttons with icon and hover effects
        int buttonWidth = 180;
        int buttonHeight = 50;
        int buttonY = windowY + windowHeight - buttonHeight - 20;
        
        // Breeding button
        breedButton = new Rectangle(windowX + 30, buttonY, buttonWidth, buttonHeight);
        drawKandangButton(g2, breedButton, "Breeding", new Color(90, 130, 70), new Color(120, 170, 90));
        
        // Remove button
        removeButton = new Rectangle(windowX + (windowWidth - buttonWidth)/2, buttonY, buttonWidth, buttonHeight);
        drawKandangButton(g2, removeButton, "Remove Animal", new Color(170, 70, 70), new Color(200, 90, 90));
        
        // Get Item button
        getItemButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, buttonY, buttonWidth, buttonHeight);
        drawKandangButton(g2, getItemButton, "Collect Products", new Color(70, 90, 140), new Color(90, 120, 180));
    }
    public void drawKandangButton(Graphics2D g2, Rectangle button, String text, Color baseColor, Color hoverColor) {
        // Check if mouse is hovering over button
        Point mousePosition = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePosition, gp);
        boolean isHovering = button.contains(mousePosition);
        
        // Button shadow
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRoundRect(button.x + 3, button.y + 3, button.width, button.height, 10, 10);
        
        // Button background
        g2.setColor(isHovering ? hoverColor : baseColor);
        g2.fillRoundRect(button.x, button.y, button.width, button.height, 10, 10);
        
        // Button border
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(button.x, button.y, button.width, button.height, 10, 10);
        
        // Button text
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        int textX = button.x + (button.width - g2.getFontMetrics().stringWidth(text))/2;
        int textY = button.y + (button.height + g2.getFontMetrics().getHeight()/2)/2;
        g2.drawString(text, textX, textY);
        
        // Subtle glow effect when hovering
        if(isHovering) {
            g2.setColor(new Color(255, 255, 200, 60));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(button.x-2, button.y-2, button.width+4, button.height+4, 12, 12);
        }
    }
    public void handleKandangScroll(int notches) {
        if(inBreedingMenu) {
            breedingScrollPosition = Math.max(0, breedingScrollPosition - notches);
        }
        else if(inGetItemMenu) {
            getItemScrollPosition = Math.max(0, getItemScrollPosition - notches);
        }
        else if(inRemoveMenu) {
            removeScrollPosition = Math.max(0, removeScrollPosition - notches);
        }
        else {
            kandangScrollPosition = Math.max(0, kandangScrollPosition + notches);
        }
    }

    public void handleKandangClick(int x, int y, Kandang kandang, Player player) {
        if(inRemoveMenu) {
            if(removeBackButton != null && removeBackButton.contains(x, y)) {
                inRemoveMenu = false;
                selectedRemoveIndex = 0;
                return;
            }
        }
        else if(inGetItemMenu) {
            if(getItemBackButton != null && getItemBackButton.contains(x, y)) {
                inGetItemMenu = false;
                selectedGetItemIndex = 0;
                return;
            }
        }
        else if(inBreedingMenu) {
            if(backButton != null && backButton.contains(x, y)) {
                inBreedingMenu = false;
                selectedMale = null;
                selectedFemale = null;
                selectedBreedMaleIndex = 0;
                selectedBreedFemaleIndex = 0;
                isMaleList = true;
                return;
            }
        }
        else {
            if(breedButton != null && breedButton.contains(x, y)) {
                inBreedingMenu = true; 
            }
            else if(removeButton != null && removeButton.contains(x, y)) {
                inRemoveMenu = true;
            }
            else if(getItemButton != null && getItemButton.contains(x, y)) {
                inGetItemMenu = true; 
            }
        }
    }
    
    public void drawNameInputWindow(Graphics2D g2) {
        int windowWidth = gp.TILE_SIZE * 10;
        int windowHeight = gp.TILE_SIZE * 7;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        // Draw shadow background overlay
        g2.setColor(new Color(0, 0, 0, 180));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        // Draw wooden background with border shadow
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRoundRect(windowX-5, windowY-5, windowWidth+10, windowHeight+10, 20, 20);
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);
        
        // Decorative hay frame
        g2.setColor(new Color(218, 165, 32, 180));
        g2.fillRoundRect(windowX+10, windowY+10, windowWidth-20, windowHeight-20, 15, 15);
        
        // Title banner
        g2.setColor(new Color(120, 60, 30));
        g2.fillRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(windowX + 20, windowY + 20, windowWidth - 40, 60, 10, 10);

        g2.setFont(new Font("Arial", Font.BOLD, 28));
        String title = "Name Your Animal";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 60);

        // Input field background
        g2.setColor(new Color(30, 15, 0, 160));
        int fieldX = windowX + 50;
        int fieldY = windowY + windowHeight/2 - 20;
        int fieldWidth = windowWidth - 100;
        int fieldHeight = 50;
        g2.fillRoundRect(fieldX, fieldY, fieldWidth, fieldHeight, 10, 10);
        
        // Input field border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(fieldX, fieldY, fieldWidth, fieldHeight, 10, 10);

        // Draw the current input text
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        String displayText = currentInput;
        if(System.currentTimeMillis() / 500 % 2 == 0) { // Blinking cursor effect
            displayText += "_";
        }
        
        int textX = fieldX + 10;
        int textY = fieldY + 35;
        g2.drawString(displayText, textX, textY);

        // Instruction text
        g2.setFont(new Font("Arial", Font.ITALIC, 16));
        g2.setColor(new Color(255, 255, 200));
        String instructions = "Type a name and press Enter";
        int instructionsX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(instructions))/2;
        g2.drawString(instructions, instructionsX, fieldY + fieldHeight + 30);
        
        // Character limit indicator
        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        if(currentInput.length() > 12) {
            g2.setColor(new Color(255, 100, 100));
        } else {
            g2.setColor(new Color(200, 200, 200));
        }
        String charLimit = currentInput.length() + "/15";
        g2.drawString(charLimit, fieldX + fieldWidth - 40, fieldY + fieldHeight + 20);

        // Buttons
        int buttonWidth = 120;
        int buttonHeight = 40;
        int buttonY = windowY + windowHeight - buttonHeight - 30;
        
        // Confirm button
        Rectangle confirmButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, buttonY, buttonWidth, buttonHeight);
        drawButton(g2, confirmButton, "Confirm", 
                currentInput.trim().isEmpty() ? new Color(100, 100, 100) : new Color(90, 130, 70));
        
        // Cancel button
        Rectangle cancelButton = new Rectangle(windowX + 30, buttonY, buttonWidth, buttonHeight);
        drawButton(g2, cancelButton, "Cancel", new Color(170, 70, 70));
        
        // Add warning if name is too long
        if(currentInput.length() > 15) {
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            g2.setColor(new Color(255, 100, 100));
            String warning = "Name too long (15 chars max)";
            int warningX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(warning))/2;
            g2.drawString(warning, warningX, fieldY - 10);
        }
    }

    // Helper method for drawing nice buttons
    public void drawButton(Graphics2D g2, Rectangle button, String text, Color baseColor) {
        // Button shadow
        g2.setColor(new Color(0, 0, 0, 80));
        g2.fillRoundRect(button.x + 3, button.y + 3, button.width, button.height, 10, 10);
        
        // Button background
        g2.setColor(baseColor);
        g2.fillRoundRect(button.x, button.y, button.width, button.height, 10, 10);
        
        // Button border
        g2.setColor(new Color(255, 255, 255, 180));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(button.x, button.y, button.width, button.height, 10, 10);
        
        // Button text
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.WHITE);
        int textX = button.x + (button.width - g2.getFontMetrics().stringWidth(text))/2;
        int textY = button.y + (button.height + g2.getFontMetrics().getHeight()/2)/2;
        g2.drawString(text, textX, textY);
    }

    public void handleNameInput(char c) {
        if (showNameInput) {
            if(c >= 32 && c <= 126) { 
                if(currentInput.length() < 15) { 
                    currentInput += c;
                }
            }
        }
    }

    public void handleNameInputKey(int keyCode) {
        if(showNameInput) {
            if(keyCode == KeyEvent.VK_BACK_SPACE && currentInput.length() > 0) {
                currentInput = currentInput.substring(0, currentInput.length() - 1);
            }
            else if (keyCode == KeyEvent.VK_ENTER && !currentInput.isEmpty()) {
                animalToName.setName(currentInput);
                if (animalToName.getName().equalsIgnoreCase("bryan")){
                    gp.player.hasNamedChicken = true;
                }
                if(targetKandang.addAnimal(animalToName)) {
                    showNameInput = false;
                    currentInput = "";
                    if(gp.player.grabbedAnimal != null) {
                        gp.gameState = gp.PLAY_STATE;
                        gp.player.grabbedAnimal = null;
                        gp.checkAndRespawnAnimals();
                    } else {
                        gp.gameState = gp.KANDANG_STATE;
                    }
                    animalToName = null;
                    targetKandang = null;
                }
            }
        }
        
    }

    public void furnaceMenu() {
        int frameX = gp.TILE_SIZE * 4;
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int frameWidth = gp.TILE_SIZE * 7 + 15;
        int frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35;
        int slotX = slotXStart;
        int slotY = slotYStart + 56;

        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.drawString("Furnace", slotX, slotYStart + 24);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString("Raw", slotX + 95, slotYStart + 80);

        if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0] == null) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 30, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        } else {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 30, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawImage(((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0].img, slotX + 100, slotY + 30, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0] instanceof Stackable) {
                Stackable stackableItem = (Stackable) ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx + 95, slotY + 80);
            }
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString("Fuel", slotX + 95, slotY + 130);

        if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0] == null) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 150, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        } else {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 150, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawImage(((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0].img, slotX + 100, slotY + 150, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0] instanceof Stackable) {
                Stackable stackableItem = (Stackable) ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx + 95, slotY + 200);
            }
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.drawString("Cooked", slotX + 95, slotY + 245);

        if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] == null) {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 270, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        } else {
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(slotX + 95, slotY + 270, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
            g2.setColor(Color.WHITE);
            g2.drawImage(((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0].img, slotX + 100, slotY + 270, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] instanceof Stackable) {
                Stackable stackableItem = (Stackable) ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx + 95, slotY + 320);
            }
        }

        frameX = gp.TILE_SIZE * 14;
        frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        frameWidth = gp.TILE_SIZE * 7 + 15;
        frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        slotXStart = frameX + 30;
        slotYStart = frameY + 35;
        slotX = slotXStart;
        slotY = slotYStart + 56;
        
        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.drawString("Inventory", slotX, slotYStart + 24);
        
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            if (gp.player.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                if ((i + 1) % 4 == 0) {
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
            if (gp.player.inventory.slots[i] instanceof Buildings) {
                Buildings stackableItem = (Buildings) gp.player.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }

        if (canSelectInventory) {
            slotXStart = gp.TILE_SIZE * 4 + 30;
            slotYStart = frameY + 91;
        } else {
            slotXStart = gp.TILE_SIZE * 14 + 30;
            slotYStart = frameY + 91;
        }

        if (!canSelectInventory) {
            int cursorX = slotXStart +  ((gp.TILE_SIZE + 25) * slotCol);
            int cursorY = slotYStart + (gp.TILE_SIZE + 25) * slotRow;
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        } else {
            int cursorX = slotXStart + 95 +  ((gp.TILE_SIZE + 25) * slotCol);
            int cursorY = slotYStart + (120 * selectedFurnace) + 30;
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }  
        
        if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0] != null 
            && ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0] != null) {
            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cook();
        }
    }

    public void drawChest(Chest chest) {
        int frameX = gp.TILE_SIZE * 4;
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int frameWidth = gp.TILE_SIZE * 7 + 15;
        int frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35;
        int slotX = slotXStart;
        int slotY = slotYStart + 56;

        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.drawString("Chest", slotX, slotYStart + 24);

        for (int i = 0; i < chest.inventory.slots.length; i++) {
            if (chest.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                if ((i + 1) % 4 == 0) {
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
            g2.drawImage(chest.inventory.slots[i].img, slotX + 5, slotY + 5, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if (chest.inventory.slots[i] instanceof Stackable) {
                Stackable stackableItem = (Stackable) chest.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if (chest.inventory.slots[i] instanceof Arsenal) { // Assuming ArsenalItem has durability
                Arsenal arsenalItem = (Arsenal) chest.inventory.slots[i];
                if (arsenalItem.durability < arsenalItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) arsenalItem.durability / arsenalItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (chest.inventory.slots[i] instanceof Armor) {
                Armor armorItem = (Armor) chest.inventory.slots[i];
                if (armorItem.durability < armorItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) armorItem.durability / armorItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (chest.inventory.slots[i] instanceof FishingRod) {
                FishingRod fishingRod = (FishingRod) chest.inventory.slots[i];
                if (fishingRod.durability < fishingRod.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float)fishingRod.durability / fishingRod.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (chest.inventory.slots[i] instanceof Buildings) {
                Buildings stackableItem = (Buildings) chest.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }
        
        frameX = gp.TILE_SIZE * 14;
        frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        frameWidth = gp.TILE_SIZE * 7 + 15;
        frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        slotXStart = frameX + 30;
        slotYStart = frameY + 35;
        slotX = slotXStart;
        slotY = slotYStart + 56;
        
        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.drawString("Inventory", slotX, slotYStart + 24);
        
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            if (gp.player.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                if ((i + 1) % 4 == 0) {
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
            if (gp.player.inventory.slots[i] instanceof Arsenal) { // Assuming ArsenalItem has durability
                Arsenal arsenalItem = (Arsenal) gp.player.inventory.slots[i];
                if (arsenalItem.durability < arsenalItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) arsenalItem.durability / arsenalItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof Armor) { // Assuming ArsenalItem has durability
                Armor armorItem = (Armor) gp.player.inventory.slots[i];
                if (armorItem.durability < armorItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) armorItem.durability / armorItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof FishingRod) {
                FishingRod fishingRod = (FishingRod) gp.player.inventory.slots[i];
                if (fishingRod.durability < fishingRod.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float)fishingRod.durability / fishingRod.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof Buildings) {
                Buildings stackableItem = (Buildings) gp.player.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }

        if (isPointingChest) {
            slotXStart = gp.TILE_SIZE * 4 + 30;
            slotYStart = frameY + 91;
        } else {
            slotXStart = gp.TILE_SIZE * 14 + 30;
            slotYStart = frameY + 91;
            
        }

        int cursorX = slotXStart +  ((gp.TILE_SIZE + 25) * slotCol);
        int cursorY = slotYStart + (gp.TILE_SIZE + 25) * slotRow;
        int cursorWidth = gp.TILE_SIZE + 10;
        int cursorHeight = gp.TILE_SIZE + 10;
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
    }

    public void drawAndGetStacks() {
        int frameX = gp.TILE_SIZE * gp.MAX_SCREEN_COL - (gp.TILE_SIZE * 3);
        int frameY = gp.TILE_SIZE * gp.MAX_SCREEN_ROW - (gp.TILE_SIZE * 5);
        int frameWidth = gp.TILE_SIZE * 2;
        int frameHeight = gp.TILE_SIZE * 4;

        String plusBtn = "< + >";
        String amount = " " + String.valueOf(amountToDrop) + " ";
        String minusBtn = "< - >";

        int posXPlus = frameX + (frameWidth - sentenceLength(plusBtn)) / 2 + 6;
        int posYPlus = frameY + frameHeight / 4;
        int posXMinus = frameX + (frameWidth - sentenceLength(plusBtn)) / 2 + 10;
        int posYMinus = frameY + frameHeight * 3 / 4;

        drawSubWindow(frameX, frameY, frameWidth + 10, frameHeight);

        g2.drawString(plusBtn, posXPlus, posYPlus);
        g2.drawString(amount, frameX + frameWidth / 2 - sentenceLength(plusBtn) / 2, frameY + frameHeight * 2 / 4);
        g2.drawString(minusBtn, posXMinus, posYMinus);
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

        for (Map.Entry<List<Item>, Item> entry : gp.player.recipe.currentRecipe.entrySet()) {
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

            // Create hitbox for the full row
            Rectangle hitbox = new Rectangle(contentX, contentY - 35, frameWidth - 60, 50);
            itemHitboxes.add(hitbox);
            itemList.add(result);
            recipeKeys.add(ingredients); // track the key (ingredient list)

            contentY += 45;
        }

        g2.setClip(oldClip);
    }
    

    public void scrollUp() {
        scrollY -= 20;
        if (scrollY < 0) scrollY = 0;
    }
    
    public void scrollDown() {
        scrollY += 20;
        maxScroll = gp.player.recipe.currentRecipe.size() * 50;
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
            if (gp.player.inventory.slots[i] instanceof Arsenal) {
                Arsenal arsenalItem = (Arsenal) gp.player.inventory.slots[i];
                if (arsenalItem.durability < arsenalItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) arsenalItem.durability / arsenalItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof Armor) {
                Armor arsenalItem = (Armor) gp.player.inventory.slots[i];
                if (arsenalItem.durability < arsenalItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) arsenalItem.durability / arsenalItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof FishingRod) {
                FishingRod fishingRod = (FishingRod) gp.player.inventory.slots[i];
                if (fishingRod.durability <fishingRod.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float)fishingRod.durability / fishingRod.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
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
            if (gp.player.inventory.slots[i] instanceof Buildings) {
                Buildings stackableItem = (Buildings) gp.player.inventory.slots[i];
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
            String text = gp.player.inventory.slots[slotCol].name;
            if (gp.player.inventory.slots[slotCol].isLegendaryItem){
                FontMetrics fm = g2.getFontMetrics();
                Rectangle2D bounds = fm.getStringBounds(text, g2);

                // Create a GlyphVector for the text
                java.awt.font.GlyphVector gv = font.createGlyphVector(g2.getFontRenderContext(), text);
                Shape textShape = gv.getOutline(getXForCenteredText(text) - text.length()+ 55, cursorY - 50);

                // Create gradient paint
                GradientPaint gradient = new GradientPaint(
                    getXForCenteredText(text) - text.length() + 55, cursorY - 100 - (float)bounds.getHeight(), new Color(103, 238, 255),
                    getXForCenteredText(text) - text.length() + 55 + (float)bounds.getWidth(), cursorY - (float)bounds.getHeight(), new Color(255, 239, 94)
                );
                Paint oldPaint = g2.getPaint();
                g2.setPaint(gradient);
                g2.fill(textShape);
                g2.setPaint(oldPaint);
            } else {
                g2.setColor(Color.WHITE);
                g2.drawString(text, getXForCenteredText(text) - text.length() * 2 + 52, cursorY - 50);
            }
            g2.setColor(Color.WHITE);
            g2.setPaint(Color.WHITE);
        }
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    }

    public void drawInventory() {
        int frameX = gp.TILE_SIZE * 4;
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int frameWidth = gp.TILE_SIZE * 8 + 15;
        int frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(frameX + 55, frameY + 45 , gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        g2.drawRoundRect(frameX + 130, frameY + 45 , gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        g2.drawRoundRect(frameX + 205, frameY + 45 , gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
        g2.drawRoundRect(frameX + 280, frameY + 45 , gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);

        if (gp.player.helmet != null) {
            g2.drawImage(gp.player.helmet.img, frameX + 60, frameY + 50, gp.TILE_SIZE, gp.TILE_SIZE, null);

            if (gp.player.helmet != null) {
                if (gp.player.helmet.durability < gp.player.helmet.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = frameX + 55;
                    int durabilityBarY = frameY + 45 + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) gp.player.helmet.durability / gp.player.helmet.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
        }
        if (gp.player.chestplate != null) {
            g2.drawImage(gp.player.chestplate.img, frameX + 135, frameY + 50, gp.TILE_SIZE, gp.TILE_SIZE, null);

            if (gp.player.chestplate != null) {
                if (gp.player.chestplate.durability < gp.player.chestplate.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = frameX + 130;
                    int durabilityBarY = frameY + 45 + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) gp.player.chestplate.durability / gp.player.chestplate.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
        }
        if (gp.player.leggings != null) {
            g2.drawImage(gp.player.leggings.img, frameX + 210, frameY + 50, gp.TILE_SIZE, gp.TILE_SIZE, null);

            if (gp.player.leggings != null) {
                if (gp.player.leggings.durability < gp.player.leggings.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = frameX + 205;
                    int durabilityBarY = frameY + 45 + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) gp.player.leggings.durability / gp.player.leggings.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
        }
        if (gp.player.boots != null) {
            g2.drawImage(gp.player.boots.img, frameX + 285, frameY + 50, gp.TILE_SIZE, gp.TILE_SIZE, null);

            if (gp.player.boots != null) {
                if (gp.player.boots.durability < gp.player.boots.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = frameX + 280;
                    int durabilityBarY = frameY + 45 + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) gp.player.boots.durability / gp.player.boots.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
        }

        g2.drawImage(gp.player.stay, frameX + 65, frameY + 200, 250, 300, null);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        g2.setColor(Color.WHITE);
        g2.drawString("Defense: " + gp.player.getDefense(), frameX + 125, frameY + 530);

        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35;
        int slotX = slotXStart;
        int slotY = slotYStart + 56;

        frameX = gp.TILE_SIZE * 15;
        frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        frameWidth = gp.TILE_SIZE * 7 + 15;
        frameHeight = gp.TILE_SIZE * 15;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);
        
        slotXStart = frameX + 30;
        slotYStart = frameY + 35;
        slotX = slotXStart;
        slotY = slotYStart + 56;
        
        g2.setFont(new Font("Arial", Font.PLAIN, 35));
        g2.drawString("Inventory", slotX, slotYStart + 24);
        
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            if (gp.player.inventory.slots[i] == null) {
                g2.setColor(Color.GRAY);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10, 10, 10);
                if ((i + 1) % 4 == 0) {
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
            if (gp.player.inventory.slots[i] instanceof Buildings) {
                Buildings stackableItem = (Buildings) gp.player.inventory.slots[i];
                Font font = new Font("Arial", Font.BOLD, 20); // Family = Arial, Style = Bold, Size = 30 VERSI LENGKAP
                g2.setFont(font);
                int dx = 30;
                if (stackableItem.currentStack < 10) {
                    dx = 40;
                }
                g2.drawString(String.valueOf(stackableItem.currentStack), slotX + dx, slotY + 50);
            }
            if (gp.player.inventory.slots[i] instanceof Arsenal) { // Assuming ArsenalItem has durability
                Arsenal arsenalItem = (Arsenal) gp.player.inventory.slots[i];
                if (arsenalItem.durability < arsenalItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) arsenalItem.durability / arsenalItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof Armor) { // Assuming ArsenalItem has durability
                Armor armorItem = (Armor) gp.player.inventory.slots[i];
                if (armorItem.durability < armorItem.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float) armorItem.durability / armorItem.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if (gp.player.inventory.slots[i] instanceof FishingRod) {
                FishingRod fishingRod = (FishingRod) gp.player.inventory.slots[i];
                if (fishingRod.durability <fishingRod.maxDurability) {
                    int durabilityBarWidth = gp.TILE_SIZE + 10;
                    int durabilityBarHeight = 5;
                    int durabilityBarX = slotX;
                    int durabilityBarY = slotY + gp.TILE_SIZE + 5;

                    // Calculate durability percentage
                    float durabilityPercentage = (float)fishingRod.durability / fishingRod.maxDurability;

                    // Set color based on durability
                    if (durabilityPercentage > 0.5) {
                        g2.setColor(Color.GREEN);
                    } else {
                        g2.setColor(Color.RED);
                    }

                    // Draw the durability bar
                    g2.fillRect(durabilityBarX, durabilityBarY, (int) (durabilityBarWidth * durabilityPercentage), durabilityBarHeight);

                    // Draw the border of the durability bar
                    g2.setColor(Color.BLACK);
                    g2.drawRect(durabilityBarX, durabilityBarY, durabilityBarWidth, durabilityBarHeight);
                }
            }
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }

        slotYStart = frameY + 91;
        frameX = gp.TILE_SIZE * 4;
        frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);

        if (gp.keyH.selectCounter == 0) {
            int cursorX = slotXStart +  ((gp.TILE_SIZE + 25) * slotCol);
            int cursorY = slotYStart + (gp.TILE_SIZE + 25) * slotRow;
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        }
        if (gp.keyH.selectCounter == 1) {
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(frameX + 55, frameY + 45, cursorWidth, cursorHeight, 10, 10);
        }
        if (gp.keyH.selectCounter == 2) {
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(frameX + 130, frameY + 45 , cursorWidth, cursorHeight, 10, 10);
        }
        if (gp.keyH.selectCounter == 3) {
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(frameX + 205, frameY + 45, cursorWidth, cursorHeight, 10, 10);
        }
        if (gp.keyH.selectCounter == 4) {
            int cursorWidth = gp.TILE_SIZE + 10;
            int cursorHeight = gp.TILE_SIZE + 10;
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(frameX + 280, frameY + 45, cursorWidth, cursorHeight, 10, 10);
        }
    }

    public void drawStats() {
        int frameX = gp.TILE_SIZE - 35;
        int frameY = gp.TILE_SIZE - 35;
        int frameWidth = gp.TILE_SIZE * 6;
        int frameHeight = gp.TILE_SIZE * 3;
        Color c = new Color(255,255,255, 255);
        g2.setColor(c);
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/bg-wood.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        g2.drawImage(img, frameX, frameY, frameWidth, frameHeight, null);

        // Bar dimensions
        int barX = frameX + gp.TILE_SIZE / 2;
        int barY = frameY + gp.TILE_SIZE - 15;
        int barWidth = frameWidth - gp.TILE_SIZE;
        int barHeight = gp.TILE_SIZE / 4;

        g2.setFont(new Font("Arial", Font.BOLD, 16));

        // Health bar
        g2.setColor(Color.RED);
        g2.fillRect(barX, barY, (int) (barWidth * (gp.player.health / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);
        g2.drawString("Health: " + gp.player.health + "/100", barX, barY - 5);

        // Hunger bar
        g2.setColor(Color.ORANGE);
        g2.fillRect(barX, barY + gp.TILE_SIZE - 5, (int) (barWidth * (gp.player.hunger / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY + gp.TILE_SIZE - 5, barWidth, barHeight);
        g2.drawString("Hunger: " + gp.player.hunger + "/100", barX, barY + gp.TILE_SIZE - 10);

        // Thirst bar
        g2.setColor(Color.BLUE);
        g2.fillRect(barX, barY + gp.TILE_SIZE * 2 - 10, (int) (barWidth * (gp.player.thirst / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY + gp.TILE_SIZE * 2 - 10, barWidth, barHeight);
        g2.drawString("Thirst: " + gp.player.thirst + "/100", barX, barY + gp.TILE_SIZE * 2 - 20);

        // Level bar
        int levelBarY = frameY + gp.TILE_SIZE * 4;
        float brightness = (float) gp.player.exp / gp.player.maxExp;

        // Draw the bar background (darker tone)
        g2.setColor(new Color(50, 50, 50));
        g2.fillRect(barX, levelBarY, barWidth, barHeight);

        // Draw the brighter part of the bar
        g2.setColor(new Color((int) (255 * brightness), (int) (255 * brightness), 0));
        g2.fillRect(barX, levelBarY, (int) (barWidth * brightness), barHeight);

        // Draw the bar border
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, levelBarY, barWidth, barHeight);

        // Draw the level and exp text
        g2.setColor(Color.WHITE);
        g2.drawString("Level: " + gp.player.level, barX, levelBarY - 10);
        g2.drawString("EXP: " + gp.player.exp + "/" + gp.player.maxExp, barX, levelBarY + barHeight + 20);
        // draw status effects
        int effectY = levelBarY + barHeight + 40; // Position effects below EXP text
        g2.setFont(new Font("Arial", Font.BOLD, 20));
         // Poison effect
        if(gp.player.isPoisoned()) {
            g2.setColor(new Color(124, 252, 0)); // Light green color
            g2.drawString("POISONED", barX, effectY);
            effectY += 25; // Space between effects
        }

        // Starving effect 
        if(gp.player.hunger <= 20) {
            g2.setColor(new Color(255, 69, 0)); // Red-orange color
            g2.drawString("STARVING", barX, effectY);
            effectY += 25;
        }
        // Dehydration effect
        if(gp.player.isDehydrated()) {
            g2.setColor(new Color(0, 191, 255)); // Deep sky blue
            g2.drawString("DEHYDRATED", barX, effectY);
        }
    }

    public void drawPauseScreen() {
        g2.setColor(new Color(0, 0, 0, 150)); // Semi-transparent black
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 28));
        String text = "PAUSED";
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.SCREEN_WIDTH / 2 - textLength / 2;
        int y = gp.SCREEN_HEIGHT / 2;
        g2.drawString(text, x, y - 50);

        // Draw "Quit" button
        int buttonWidth = 140;
        int buttonHeight = 40;
        int buttonX = gp.SCREEN_WIDTH / 2 - buttonWidth / 2;
        int buttonY = y - 10;
        pauseQuitButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);

        g2.setColor(Color.RED);
        g2.drawRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 15, 15);
        g2.fillRoundRect(buttonX, buttonY, buttonWidth, buttonHeight, 15, 15);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        String quitText = "Quit";
        int quitTextX = buttonX + (buttonWidth - g2.getFontMetrics().stringWidth(quitText)) / 2;
        int quitTextY = buttonY + (buttonHeight + g2.getFontMetrics().getHeight()) / 2 - 4;
        g2.drawString(quitText, quitTextX, quitTextY);
    }

    public int getXCenteredText(String text) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = (int)(gp.SCREEN_WIDTH / 2 - length/2);
        return x; // Adjust for better centering
    }

    
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
        g2.setStroke(new BasicStroke(3)); // Reset stroke to default
    }

    // Returns [isChest, slotIndex] or null if not found
    public Object[] getClickedInventoryOrChestSlot(int mouseX, int mouseY, Chest chest) {
        // Chest slots (first 32)
        int slotXStart = gp.TILE_SIZE * 4 + 30;
        int slotYStart = gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16) + 35 + 56;
        int slotX = slotXStart;
        int slotY = slotYStart;
        for (int i = 0; i < chest.inventory.slots.length; i++) {
            Rectangle r = new Rectangle(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (r.contains(mouseX, mouseY)) return new Object[]{true, i};
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }
        // Inventory slots (next 24)
        slotXStart = gp.TILE_SIZE * 14 + 30;
        slotYStart = gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16) + 35 + 56;
        slotX = slotXStart;
        slotY = slotYStart;
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            Rectangle r = new Rectangle(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (r.contains(mouseX, mouseY)) return new Object[]{false, i};
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }
        return null;
    }

    public Integer getClickedInventorySlot(int mouseX, int mouseY) {
        // Match the inventory panel position in drawInventory()
        int frameX = gp.TILE_SIZE * 15;
        int frameY = gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35 + 56;
        int slotX = slotXStart;
        int slotY = slotYStart;

        // Check inventory slots
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            Rectangle r = new Rectangle(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (r.contains(mouseX, mouseY)) return i;
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }

        // Check armor slots (helmet, chestplate, leggings, boots)
        int armorFrameX = gp.TILE_SIZE * 4;
        int armorFrameY = gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int[] armorOffsets = {55, 130, 205, 280}; // X offsets for helmet, chestplate, leggings, boots
        int armorY = armorFrameY + 45;
        for (int i = 0; i < 4; i++) {
            Rectangle armorRect = new Rectangle(armorFrameX + armorOffsets[i], armorY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (armorRect.contains(mouseX, mouseY)) {
                // Move armor to first empty inventory slot
                Item armorItem = null;
                if (i == 0 && gp.player.helmet != null) {
                    armorItem = gp.player.helmet;
                    gp.player.helmet = null;
                } else if (i == 1 && gp.player.chestplate != null) {
                    armorItem = gp.player.chestplate;
                    gp.player.chestplate = null;
                } else if (i == 2 && gp.player.leggings != null) {
                    armorItem = gp.player.leggings;
                    gp.player.leggings = null;
                } else if (i == 3 && gp.player.boots != null) {
                    armorItem = gp.player.boots;
                    gp.player.boots = null;
                }
                if (armorItem != null) {
                    for (int j = 0; j < gp.player.inventory.slots.length; j++) {
                        if (gp.player.inventory.slots[j] == null) {
                            gp.player.inventory.slots[j] = armorItem;
                            break;
                        }
                    }
                }
                return null; // Don't select any inventory slot
            }
        }

        return null;
    }

    public Object[] getClickedInventoryOrFurnaceSlot(int mouseX, int mouseY, Furnace furnace) {
        // Furnace slots (raw, fuel, cooked)
        int frameX = gp.TILE_SIZE * 4;
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 16);
        int slotXStart = frameX + 30 + 95;
        int slotYStart = frameY + 35 + 56;

        // Raw slot
        Rectangle rawRect = new Rectangle(slotXStart, slotYStart + 30, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
        if (rawRect.contains(mouseX, mouseY)) return new Object[]{"furnace", 0}; // 0 = raw

        // Fuel slot
        Rectangle fuelRect = new Rectangle(slotXStart, slotYStart + 150, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
        if (fuelRect.contains(mouseX, mouseY)) return new Object[]{"furnace", 1}; // 1 = fuel

        // Cooked slot
        Rectangle cookedRect = new Rectangle(slotXStart, slotYStart + 270, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
        if (cookedRect.contains(mouseX, mouseY)) return new Object[]{"furnace", 2}; // 2 = cooked

        // Inventory slots (right panel)
        frameX = gp.TILE_SIZE * 14;
        slotXStart = frameX + 30;
        slotYStart = frameY + 35 + 56;
        int slotX = slotXStart;
        int slotY = slotYStart;
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            Rectangle r = new Rectangle(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (r.contains(mouseX, mouseY)) return new Object[]{"inventory", i};
            if ((i + 1) % 4 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
            }
        }
        return null;
    }
    public void drawShopMenu() {
        // Background semi-transparent overlay
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        // Shop panel
        int panelWidth = 1000;
        int panelHeight = 600;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;
        
        g2.setColor(new Color(70, 40, 0));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(panelX+5, panelY+5, panelWidth-10, panelHeight-10, 10, 10);
        
        // Title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.drawString("Shop Items", panelX + 30, panelY + 50);
        g2.drawString("Your Coins: " + gp.player.coins, panelX + 550, panelY + 50);
        
        // Category tabs
        int tabWidth = 150;
        int tabHeight = 40;
        int tabY = panelY + 70;
        
        // Categories
        String[] categories = {"Tools", "Armor", "Food", "Materials", "Legendary Items"};
        
        for(int i = 0; i < categories.length; i++) {
            int tabX = panelX + 30 + (i * (tabWidth + 10));
            boolean isSelected = (shopCategory == i);
            
            if(isSelected) {
                g2.setColor(new Color(255, 215, 0));  // Gold for selected tab
                g2.fillRect(tabX, tabY, tabWidth, tabHeight);
                g2.setColor(Color.BLACK);
            } else {
                g2.setColor(new Color(200, 200, 200)); // Light gray for unselected tab
                g2.fillRect(tabX, tabY, tabWidth, tabHeight);
                g2.setColor(Color.BLACK);
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
            int textX = tabX + (tabWidth - g2.getFontMetrics().stringWidth(categories[i])) / 2;
            g2.drawString(categories[i], textX, tabY + 25);
        }
        
        // Item display area
        int itemAreaX = panelX + 30;
        int itemAreaY = tabY + tabHeight + 20;
        int itemAreaWidth = panelWidth - 60;
        int itemAreaHeight = panelHeight - 160;
        
        g2.setColor(new Color(50, 30, 0));
        g2.fillRect(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        g2.setColor(Color.WHITE);
        g2.drawRect(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        
        // Draw items based on selected category
        drawShopItems(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        
        // Exit button
        int buttonWidth = 100;
        int buttonHeight = 40;
        int buttonX = panelX + panelWidth - buttonWidth - 50;
        int buttonY = panelY + panelHeight - buttonHeight - 50;
        
        shopExitButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(shopExitButton.x, shopExitButton.y, shopExitButton.width, shopExitButton.height);
        
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        String exitText = "Exit";
        int textX = buttonX + (buttonWidth - g2.getFontMetrics().stringWidth(exitText)) / 2;
        int textY = buttonY + (buttonHeight + g2.getFontMetrics().getHeight()) / 2 - 4;
        g2.drawString(exitText, textX, textY);
    }

    public void drawEffectMenu() {
        // Background fully opaque untuk menutupi UI lainnya
        g2.setColor(new Color(0, 0, 0, 255)); // Menggunakan alpha 255 agar fully opaque
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);
        
        // Panel menu effect
        int panelWidth = 1000;
        int panelHeight = 600;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;
        
        g2.setColor(new Color(70, 40, 0));
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(panelX+5, panelY+5, panelWidth-10, panelHeight-10, 10, 10);
        
        // Judul
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
        g2.drawString("Shop Effect", panelX + 30, panelY + 50);
        g2.drawString("Your Coins: " + gp.player.coins, panelX + 550, panelY + 50);
        
        // Tab kategori
        int tabWidth = 150;
        int tabHeight = 40;
        int tabY = panelY + 70;
        
        // Kategori
        String[] categories = {"Sell Items", "Repair", "Cheat"};
        
        for(int i = 0; i < categories.length; i++) {
            int tabX = panelX + 30 + (i * (tabWidth + 10));
            boolean isSelected = (effectCategory == i);
            
            if(isSelected) {
                g2.setColor(new Color(255, 215, 0));  // Gold untuk tab yang dipilih
                g2.fillRect(tabX, tabY, tabWidth, tabHeight);
                g2.setColor(Color.BLACK);
            } else {
                g2.setColor(new Color(200, 200, 200)); // Light gray untuk tab yang tidak dipilih
                g2.fillRect(tabX, tabY, tabWidth, tabHeight);
                g2.setColor(Color.BLACK);
            }
            
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
            int textX = tabX + (tabWidth - g2.getFontMetrics().stringWidth(categories[i])) / 2;
            g2.drawString(categories[i], textX, tabY + 25);
        }
        
        // Area tampilan item
        int itemAreaX = panelX + 30;
        int itemAreaY = tabY + tabHeight + 20;
        int itemAreaWidth = panelWidth - 60;
        int itemAreaHeight = panelHeight - 160;
        
        g2.setColor(new Color(50, 30, 0));
        g2.fillRect(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        g2.setColor(Color.WHITE);
        g2.drawRect(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        
        // Draw items based on selected category
        drawEffectItems(itemAreaX, itemAreaY, itemAreaWidth, itemAreaHeight);
        
        // Exit button
        int buttonWidth = 100;
        int buttonHeight = 40;
        int buttonX = panelX + panelWidth - buttonWidth - 50;
        int buttonY = panelY + panelHeight - buttonHeight - 50;
        
        effectExitButton = new Rectangle(buttonX, buttonY, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(effectExitButton.x, effectExitButton.y, effectExitButton.width, effectExitButton.height);
        
        g2.setColor(Color.BLACK);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        String exitText = "Exit";
        int textX = buttonX + (buttonWidth - g2.getFontMetrics().stringWidth(exitText)) / 2;
        int textY = buttonY + (buttonHeight + g2.getFontMetrics().getHeight()) / 2 - 4;
        g2.drawString(exitText, textX, textY);
    }
    private void drawShopItems(int x, int y, int width, int height) {
        // Set up item grid
        int columns = 10;
        int itemSize = gp.TILE_SIZE + 10;
        int itemSpacing = 35;  // Increased spacing between items
        int totalItemWidth = itemSize + itemSpacing;
        
        int startX = x + 20;
        int startY = y + 20;
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12));
        
        // Get items for the current category
        ArrayList<ShopItem> categoryItems = getShopItemsByCategory(shopCategory);
        
        // Draw items
        shopItemRects.clear();
        for (int i = 0; i < categoryItems.size(); i++) {
            ShopItem item = categoryItems.get(i);
            
            int row = i / columns;
            int col = i % columns;
            
            // Add more horizontal spacing
            int itemX = startX + (col * totalItemWidth);
            // Add more vertical spacing between rows
            int itemY = startY + (row * (itemSize + 80));
            
            // Background for item
            g2.setColor(new Color(100, 70, 30));
            g2.fillRect(itemX, itemY, itemSize, itemSize);
            g2.setColor(Color.WHITE);
            g2.drawRect(itemX, itemY, itemSize, itemSize);
            
            // Item image
            g2.drawImage(item.item.img, itemX + 5, itemY + 5, itemSize - 10, itemSize - 10, null);
            
            // Item name - centering text
            g2.setColor(Color.WHITE);
            String name = item.item.name.length() > 12 ? item.item.name.substring(0, 12) + "..." : item.item.name;
            int nameWidth = g2.getFontMetrics().stringWidth(name);
            int nameX = itemX + (itemSize - nameWidth)/2;
            g2.drawString(name, nameX, itemY + itemSize + 16);
            
            // Item price - centering text
            g2.setColor(Color.YELLOW);
            String price = item.price + " coins";
            int priceWidth = g2.getFontMetrics().stringWidth(price);
            int priceX = itemX + (itemSize - priceWidth)/2;
            g2.drawString(price, priceX, itemY + itemSize + 32);
            
            // Buy button - centered
            int buyButtonWidth = 80;
            int buyButtonHeight = 25;
            int buyButtonX = itemX + (itemSize - buyButtonWidth) / 2;
            int buyButtonY = itemY + itemSize + 38;
            
            Rectangle buyButton = new Rectangle(buyButtonX, buyButtonY, buyButtonWidth, buyButtonHeight);
            shopItemRects.add(buyButton);
            
            g2.setColor(new Color(50, 150, 50));  // Green buy button
            g2.fillRect(buyButton.x, buyButton.y, buyButton.width, buyButton.height);
            
            g2.setColor(Color.WHITE);
            String buyText = "Buy";
            int textX = buyButtonX + (buyButtonWidth - g2.getFontMetrics().stringWidth(buyText)) / 2;
            g2.drawString(buyText, textX, buyButtonY + 17);
        }
    }
    private ArrayList<ShopEffect> getEffectItemsByCategory(int category) {
        ArrayList<ShopEffect> result = new ArrayList<>();
        
        for (ShopEffect item : effectItems) {
            if (item.category == category) {
                result.add(item);
            }
        }
        
        return result;
    }
    private void drawEffectItems(int x, int y, int width, int height) {
        // Set up item grid
        String buyText;
        int columns = 10;
        int itemSize = gp.TILE_SIZE + 10;
        int itemSpacing = 35;  // Increased spacing between items
        int totalItemWidth = itemSize + itemSpacing;
        
        int startX = x + 20;
        int startY = y + 20;
        
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 12));
        
        // Get items for the current category
        ArrayList<ShopEffect> categoryEffects = getEffectItemsByCategory(effectCategory);
        
        // Draw items
        effectItemRects.clear();
        for (int i = 0; i < categoryEffects.size(); i++) {
            ShopEffect item = categoryEffects.get(i);
            
            int row = i / columns;
            int col = i % columns;
            
            // Add more horizontal spacing
            int itemX = startX + (col * totalItemWidth);
            // Add more vertical spacing between rows
            int itemY = startY + (row * (itemSize + 80));
            
            BufferedImage lock = null;
            try {
                lock = ImageIO.read(new File("ProjectTheSurvivalist/res/ui/lock.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Buy button - centered
            int buyButtonWidth = 80;
            int buyButtonHeight = 25;
            int buyButtonX = itemX + (itemSize - buyButtonWidth) / 2;
            int buyButtonY = itemY + itemSize + 35;

            // Background for item
            if (isEffectUsable(item)){
                g2.setColor(new Color(100, 70, 30));
                g2.fillRect(itemX, itemY, itemSize, itemSize);
                g2.setColor(Color.WHITE);
                g2.drawRect(itemX, itemY, itemSize, itemSize);
                
                // Item image
                if (item.img != null) {
                    g2.drawImage(item.img, itemX + 5, itemY + 5, itemSize - 10, itemSize - 10, null);
                }
                
                Rectangle buyButton = new Rectangle(buyButtonX, buyButtonY, buyButtonWidth, buyButtonHeight);
                effectItemRects.add(buyButton);
                
                g2.setColor(new Color(50, 150, 50));  // Green buy button
                g2.fillRect(buyButton.x, buyButton.y, buyButton.width, buyButton.height);
                
                g2.setColor(Color.WHITE);
                if(effectCategory == 0){
                    buyText = "Sell";
                } else {
                    buyText = "Buy";
                }
            } else {
                g2.setColor(new Color(70, 40, 0));
                g2.fillRect(itemX, itemY, itemSize, itemSize);
                g2.setColor(Color.WHITE);
                g2.drawRect(itemX, itemY, itemSize, itemSize);
                
                // Draw lock icon
                if (lock != null) {
                    int lockSize = 30;
                    int lockX = itemX + (itemSize - lockSize) / 2;
                    int lockY = itemY + (itemSize - lockSize) / 2;
                    g2.drawImage(lock, lockX, lockY, lockSize, lockSize, null);
                }
                
                Rectangle lockedButton = new Rectangle(buyButtonX, buyButtonY, buyButtonWidth, buyButtonHeight);
                effectItemRects.add(lockedButton);

                g2.setColor(Color.GRAY);
                g2.fillRect(lockedButton.x, lockedButton.y, lockedButton.width, lockedButton.height);
                g2.setColor(Color.WHITE);
                buyText = "Locked";

            }
            
            // Item name - centering text
            g2.setColor(Color.WHITE);
            String name = item.name;
            int nameWidth = g2.getFontMetrics().stringWidth(name);
            int nameX = itemX + (itemSize - nameWidth)/2;
            g2.drawString(name, nameX, itemY + itemSize + 15);
            
            // Item price - centering text
            g2.setColor(Color.YELLOW);
            String price = item.price + " coins";
            int priceWidth = g2.getFontMetrics().stringWidth(price);
            int priceX = itemX + (itemSize - priceWidth)/2;
            g2.drawString(price, priceX, itemY + itemSize + 30);

            int textX = buyButtonX + (buyButtonWidth - g2.getFontMetrics().stringWidth(buyText)) / 2;
            g2.drawString(buyText, textX, buyButtonY + 17);
        }
    }
    public void handleShopClick(int x, int y) {
        // Check if exit button clicked
        if(shopExitButton != null && shopExitButton.contains(x, y)) {
            gp.gameState = gp.PLAY_STATE;
            return;
        }

        // Calculate exact panel coordinates to match drawEffectMenu
        int panelWidth = 1000;
        int panelHeight = 600;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;
        
        // Check category tabs
        int tabWidth = 150;
        int tabHeight = 40;
        int tabY = panelY + 70;
        
        // Categories
        String[] categories = {"Tools", "Armor", "Food", "Materials", "Legendary Items"};
        
        for(int i = 0; i < categories.length; i++) {
            int tabX = panelX + 30 + (i * (tabWidth + 10));
            Rectangle tabRect = new Rectangle(tabX, tabY, tabWidth, tabHeight);
            
            if(tabRect.contains(x, y)) {
                shopCategory = i;
                return;
            }
        }
        
        // Check buy buttons
        ArrayList<ShopItem> categoryItems = getShopItemsByCategory(shopCategory);
        for(int i = 0; i < shopItemRects.size(); i++) {
            if(i < categoryItems.size() && shopItemRects.get(i).contains(x, y)) {
                purchaseItem(categoryItems.get(i));
                return;
            }
        }
    }
    public void handleEffectClick(int x, int y) {
        // Check if exit button clicked
        if(effectExitButton != null && effectExitButton.contains(x, y)) {
            gp.gameState = gp.PLAY_STATE;
            return;
        }
        
        // Check category tabs
        int panelWidth = 1000;
        int panelHeight = 600;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;
        int tabWidth = 150;
        int tabHeight = 40;
        int tabY = panelY + 70;
        
        // Categories
        String[] categories = {"Sell Items", "Repair", "Cheat"};
        
        for(int i = 0; i < categories.length; i++) {
            int tabX = panelX + 30 + (i * (tabWidth + 10));
            Rectangle tabRect = new Rectangle(tabX, tabY, tabWidth, tabHeight);
            
            if(tabRect.contains(x, y)) {
                effectCategory = i;
                return;
            }
        }
        
        // Check buy buttons
        ArrayList<ShopEffect> categoryItems = getEffectItemsByCategory(effectCategory);
        for(int i = 0; i < effectItemRects.size(); i++) {
            if(i < categoryItems.size() && effectItemRects.get(i).contains(x, y)) {
                purchaseEffect(categoryItems.get(i));
                return;
            }
        }
    }
    private void purchaseItem(ShopItem item) {
        if(gp.player.coins >= item.price) {
            // Deduct coins
            if (item.category == 4){
                gp.player.hasLegendaryItem = true;
            }
            gp.player.coins -= item.price;
            item.item.currentStack++;
            gp.player.inventory.addItems(item.item);
            
            // Add item to inventory
            // boolean added = gp.player.inventory.addItems(item.item);
            
            // if(added) {
                showPurchaseSuccess = true;
                messageTimer = System.currentTimeMillis();
            // } else {
                // Refund if inventory is full
                // gp.player.coins += item.price;
                // showInsufficientFunds = true;
                // messageTimer = System.currentTimeMillis();
            // }
        } else {
            showInsufficientFunds = true;
            messageTimer = System.currentTimeMillis();
        }
    }
    private void purchaseEffect(ShopEffect item) {
        if (!isEffectUsable(item)) {
            showUnfulfilledRequirements = true;
            messageTimer = System.currentTimeMillis();
            return; // Effect already active, cannot purchase again
        }
        if(effectCategory == 0){
            gp.player.coins += item.price;
            applyEffect(item);
            showPurchaseSuccess = true;
            messageTimer = System.currentTimeMillis();
        } else {
            if(gp.player.coins >= item.price) {
                // Deduct coins
                gp.player.coins -= item.price;
                
                // Apply effect based on category
                applyEffect(item);
                
                showPurchaseSuccess = true;
                messageTimer = System.currentTimeMillis();
            } else {
                showInsufficientFunds = true;
                messageTimer = System.currentTimeMillis();
            }
        }
    }
    private boolean isEffectUsable(ShopEffect effect) {
        if (effect.category == 0) {
            // For selling items, always usable
            return true;
        }
        if (effect.name.equals("Repair Arsenal")) {
            for (Item item : gp.player.inventory.slots) {
                if (item instanceof Arsenal && ((Arsenal)item).durability < ((Arsenal)item).maxDurability) {
                    return true;
                }
            }
        } else if (effect.name.equals("Repair Armor")) {
            for (Item item : gp.player.inventory.slots) {
                if (item instanceof Armor && ((Armor)item).durability < ((Armor)item).maxDurability) {
                    return true;
                }
            }
        } else if (effect.name.equals("Repair Fishing Rod")) {
            for (Item item : gp.player.inventory.slots) {
                if (item instanceof FishingRod && ((FishingRod)item).durability < ((FishingRod)item).maxDurability) {
                    return true;
                }
            }
        } else if (effect.name.equals("Upgrade Level")) {
            return gp.player.level < 100;
        } else if (effect.name.equals("Coins +999")) {
            return true;
        } else if (effect.name.equals("God Mode") || effect.name.equals("Normal Mode")) {
            return true;
        }
        return false;
    }
    private void applyEffect(ShopEffect effect) {
        switch(effect.category) {
            case 0:
                for(int i = 0; i < gp.player.inventory.slots.length; i++) {
                    if(gp.player.inventory.slots[i] != null && 
                    gp.player.inventory.slots[i].name.equals(effect.name)) {
                        Stackable stackItem = (Stackable)gp.player.inventory.slots[i];
                        if(gp.player.inventory.slots[i] instanceof Stackable) {
                            if(stackItem.currentStack > 1) {
                                gp.player.inventory.removeItem(stackItem, 1);
                            } else {
                                gp.player.inventory.removeItem(stackItem, 1);

                            }
                        } else {
                            gp.player.inventory.removeItem(stackItem, 1);
                        }
                        
                        gp.interactBuild.initEffectItems();
                        return;
                    }
                }
                break;
            case 1:
                if(effect.name.equals("Repair Arsenal")) {
                    for(int i = 0; i < gp.player.inventory.slots.length; i++) {
                        if(gp.player.inventory.slots[i] instanceof Arsenal) {
                            Arsenal arsenal = (Arsenal)gp.player.inventory.slots[i];
                            arsenal.durability = arsenal.maxDurability;
                        }
                    }
                }
                if(effect.name.equals("Repair Armor")) {
                    for(int i = 0; i < gp.player.inventory.slots.length; i++) {
                        if(gp.player.inventory.slots[i] instanceof Armor) {
                            Armor armor = (Armor)gp.player.inventory.slots[i];
                            armor.durability = armor.maxDurability;
                        }
                    }
                }
                if(effect.name.equals("Repair Fishing Rod")) {
                    for(int i = 0; i < gp.player.inventory.slots.length; i++) {
                        if(gp.player.inventory.slots[i] instanceof FishingRod) {
                            FishingRod fishingRod = (FishingRod)gp.player.inventory.slots[i];
                            fishingRod.durability = fishingRod.maxDurability;
                        }
                    }
                }
                break;
            case 2:
                if(effect.name.equals("Upgrade Level")) {
                    gp.player.gainLevel();
                }
                if(effect.name.equals("Coins +999")) {
                    gp.player.coins += 999;
                }
                if(effect.name.equals("God Mode")) {
                    gp.isStrong = true;
                    gp.ui.effectItems.clear();
                    gp.interactBuild.initEffectItems();
                }
                if(effect.name.equals("Normal Mode")) {
                    gp.isStrong = false;
                    gp.ui.effectItems.clear();
                    gp.interactBuild.initEffectItems();
                }
                break;
        }
    }
    public void drawAchievementMenu() {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, gp.SCREEN_WIDTH, gp.SCREEN_HEIGHT);

        int panelWidth = 900;
        int panelHeight = 500;
        int panelX = gp.SCREEN_WIDTH/2 - panelWidth/2;
        int panelY = gp.SCREEN_HEIGHT/2 - panelHeight/2;

        // g2.setColor(new Color(70, 40, 0));
        // g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
        GradientPaint gpGradient = new GradientPaint(
            panelX * 2, panelY, new Color(200, 160, 90), // Bottom color (light brown/gold)
            panelX + panelWidth, panelY + panelHeight, new Color(90, 60, 30) // Top color (brownish)
        );
        Paint oldPaint = g2.getPaint(); 
        g2.setPaint(gpGradient);
        g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 15, 15);
        g2.setPaint(oldPaint);
        
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(8));
        g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);

        // Draw achievements
        int startY = panelY + 40;
        int lineHeight = 60;
        int imgSize = 48;
        int i = 0;
        ArrayList<Achievement> achievementList = new ArrayList<>(gp.aManager.achievements.values());
        int total = achievementList.size();

        int end = Math.min(achievementScroll + ACHIEVEMENTS_PER_PAGE, total);
        for (int idx = achievementScroll; idx < end; idx++) {
            Achievement a = achievementList.get(idx);
            int y = startY + (i * lineHeight);

            // Draw image
            if (a.img != null) {
                g2.drawImage(a.img, panelX + 40, y, imgSize, imgSize, null);
            }

            // Draw name
            g2.setFont(new Font("Arial", Font.BOLD, 24));
            g2.setColor(a.isCompleted ? Color.GREEN : Color.WHITE);
            g2.drawString(a.name, panelX + 110, y + 20);
            g2.setFont(new Font("Arial", Font.PLAIN, 16));
            g2.drawString(a.description, panelX + 110, y + 42);

            i++;
        }   

        // Draw scroll instructions
        g2.setFont(new Font("Arial", Font.ITALIC, 18));
        g2.setColor(Color.WHITE);
        g2.drawString("Press W/S to scroll", panelX + panelWidth - 200, panelY + panelHeight - 30);
    }
}
