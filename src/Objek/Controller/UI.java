package Objek.Controller;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import Objek.Items.Unstackable.Arsenals.*;
import javax.imageio.ImageIO;

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

    public boolean showNameInput = false;
    private String currentInput = "";
    private TameAnimal animalToName;
    private Kandang targetKandang;
    private Rectangle textField;
    private BufferedImage woodBg;
    private Rectangle backButton;
    private TameAnimal selectedMale;
    private TameAnimal selectedFemale;
    public boolean inBreedingMenu = false;
    public boolean inGetItemMenu = false;
    private Rectangle getItemBackButton;
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

    public Fish caughtFish;
    public int fishIndex;
    public int playerFishingStrength = 0;
    public int maxFishingStrength = 100;
    public boolean fishingSuccessful = false;
    public boolean canPressFishingButton = true;
    public long lastFishingButtonPressTime = 0;
    public final long FISHING_BUTTON_COOLDOWN = 800; // 800ms cooldown antara penekanan tombol
    public Random random = new Random();
    private boolean showRodRusak = false;
    private boolean showDapatIkan = false;
    private boolean showGagalDapatIkan = false;
    private long rodRusakTimer = 0;
    private long dapatIkanTimer = 0;
    private long gagalDapatIkanTimer = 0;
    private final long MESSAGE_DISPLAY_TIME = 2000;
    
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

        if (gp.gameState != gp.INVENTORY_STATE && gp.gameState != gp.OPEN_CHEST_STATE && gp.gameState != gp.OPEN_SMELTER_STATE) {
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
            } else if(inGetItemMenu) {
                drawGetItemMenu(g2, gp.currentKandang);
            } else if (gp.currentKandang != null) {
                drawKandangMenu(g2, gp.currentKandang);
            }
        }
        if (gp.gameState != gp.OPEN_CHEST_STATE) {
            drawStats();
        }
        if (showNameInput) {
            drawNameInputWindow(g2);
        }
        if (showKandangFullMessage) {
            drawFullKandangMessage(g2);
        }
        if (showWrongKandangMessage) {
            drawWrongKandangMessage(g2);
        } 
        if (isCanGoToSea) {
            drawTextKeteranganKeLaut();
        }
        if (isNeedLevel15) {
            drawTextButuhLevel();
        }
        if(gp.gameState == gp.FISHING_STATE) {
            drawFishingMinigame();
        }
        if(showRodRusak) {
            drawTextRodRusak();
            if(System.currentTimeMillis() - rodRusakTimer > MESSAGE_DISPLAY_TIME) {
                showRodRusak = false;
            }
        }
        if(showDapatIkan) {
            drawTextDapatIkan();
            if(System.currentTimeMillis() - dapatIkanTimer > MESSAGE_DISPLAY_TIME) {
                showDapatIkan = false;
            }
        }
        if(showGagalDapatIkan) {
            drawTextGagalDapatIkan();
            if(System.currentTimeMillis() - gagalDapatIkanTimer > MESSAGE_DISPLAY_TIME) {
                showGagalDapatIkan = false;
            }
        }
    }

     public void showRodRusakMessage() {
        showRodRusak = true;
        rodRusakTimer = System.currentTimeMillis();
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

    public void drawTextRodRusak() {
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        
        String message = "Your fishing rod is broken!";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 595;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 10;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.red);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.red);
        g2.drawString(message, x, y);
    }

    public void drawTextDapatIkan() {
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        
        String message = "You successfully caught a " + caughtFish.nameFish + " fish!";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 595;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 10;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.green);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.green);
        g2.drawString(message, x, y);
    }

    public void drawTextGagalDapatIkan() {
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        
        String message = "You failed to catch a " + caughtFish.nameFish + " fish!";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 595;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 10;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.red);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.red);
        g2.drawString(message, x, y);
    }

    public void drawTextKeteranganKeLaut() {
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        
        String message = "Press F to go to the sea";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 595;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 10;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.blue);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.blue);
        g2.drawString(message, x, y);
    }

    private void drawTextButuhLevel() {
        g2.setFont(new Font("Arial", Font.BOLD, 25));
        
        String message = "Ship locked! Reach level 15 to unlock it";
        int x = getXCenteredText(message);
        int y = gp.SCREEN_HEIGHT - 595;
        
        g2.setColor(new Color(0, 0, 0, 180));
        int padding = 10;
        int messageWidth = (int)g2.getFontMetrics().getStringBounds(message, g2).getWidth() + padding * 2;
        int messageHeight = (int)g2.getFontMetrics().getStringBounds(message, g2).getHeight() + padding * 2;
        g2.fillRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.gray);
        g2.drawRoundRect(x - padding, y - messageHeight + padding, messageWidth, messageHeight, 10, 10);
        
        g2.setColor(Color.gray);
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
        // Posisi indikator pada progress bar
        if (playerFishingStrength > 0) {
            if (playerFishingStrength <= 33) {
                g2.setColor(Color.RED);
            } else if (playerFishingStrength <= 66) {
                g2.setColor(Color.YELLOW);
            } else {
                g2.setColor(Color.GREEN);
            }
            g2.fillRect(panelX + 50, panelY + 250, playerFishingStrength * 3, 20);
        }
        // Border untuk progress bar
        g2.setColor(Color.WHITE);
        g2.drawRect(panelX + 50, panelY + 250, 300, 20);
        
        // Instruksi
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 16F));
        g2.drawString("Press ENTER to pull the fishing rod!", panelX + 80, panelY + 285);
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
        }else if(kandang instanceof CowCage) {
            for(Cow cow : ((CowCage)kandang).cowsInCage) {
                if(cow.getGender().equals("Male") && cow.isReadyBreeding()) 
                    males.add(cow);
                else if(cow.getGender().equals("Female") && cow.isReadyBreeding())
                    females.add(cow);
            }
        }
        else if(kandang instanceof SheepCage) {
            for(Sheep sheep : ((SheepCage)kandang).sheepsInCage) {
                if(sheep.getGender().equals("Male") && sheep.isReadyBreeding()) 
                    males.add(sheep);
                else if(sheep.getGender().equals("Female") && sheep.isReadyBreeding())
                    females.add(sheep);
            }
        }
        else if(kandang instanceof PigCage) {
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
            animal.setReadyGetItem(false);
            inGetItemMenu = false;
            selectedGetItemIndex = 0;
        }
    }
   
    
    public void drawGetItemMenu(Graphics2D g2, Kandang kandang) {

        int windowWidth = gp.TILE_SIZE * 12;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = "Get Item Menu";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 40);

        ArrayList<TameAnimal> readyAnimals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            for(Chicken chicken : ((KandangAyam)kandang).chickensInCage) {
                if(chicken.isReadyGetItem()) {
                    readyAnimals.add(chicken);
                }
            }
        }else if(kandang instanceof CowCage) {
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

        if(readyAnimals.isEmpty()) {
            g2.drawString("No animals ready to get items!", windowX + 30, startY + 40);
        } else {
            int endIndex = Math.min(getItemScrollPosition + ANIMALS_PER_PAGE+2, readyAnimals.size());
            for(int i = getItemScrollPosition; i < endIndex; i++) {
                TameAnimal animal = readyAnimals.get(i);
                int y = startY + (i - getItemScrollPosition) * lineHeight;
                
                if(i == selectedGetItemIndex) {
                    g2.setColor(Color.BLACK);
                    g2.drawRect(windowX + 25, y - 10, windowWidth - 50, lineHeight);
                }
                g2.setColor(Color.WHITE);
                g2.drawString(animal.getName() + " is ready!", windowX + 30, y + 15);
            }
        }

        int buttonWidth = 100;
        int buttonHeight = 40;
        getItemBackButton = new Rectangle(windowX + 30, windowY + windowHeight - buttonHeight - 20,buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(getItemBackButton.x, getItemBackButton.y, getItemBackButton.width, getItemBackButton.height);
        g2.setColor(Color.BLACK);
        g2.drawString("Back", getItemBackButton.x + 30, getItemBackButton.y + 25);
    }
    public void drawBreedingMenu(Graphics2D g2, Kandang kandang) {

        int windowWidth = gp.TILE_SIZE * 12;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;  
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = "Breeding Menu";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 40);

        if(kandang.getCurrentCapacity() >= kandang.getMaxCapacity()) {
            g2.setFont(new Font("Arial", Font.BOLD, 20));
            String msg = "Kandang is full! Cannot breed!";
            int msgX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(msg))/2;
            g2.drawString(msg, msgX, windowY + 100);
        } else {

            g2.setFont(new Font("Arial", Font.BOLD, 20));
            g2.drawString("Male Animals", windowX + 50, windowY + 80);
            g2.drawString("Female Animals", windowX + windowWidth/2 + 50, windowY + 80);

            ArrayList<TameAnimal> animals = new ArrayList<>();
            if(kandang instanceof KandangAyam) {
                animals.addAll(((KandangAyam)kandang).chickensInCage);
            }else if(kandang instanceof CowCage) {
                animals.addAll(((CowCage)kandang).cowsInCage);
            } else if(kandang instanceof SheepCage) {
                animals.addAll(((SheepCage)kandang).sheepsInCage);
            } else if(kandang instanceof PigCage) {
                animals.addAll(((PigCage)kandang).pigsInCage);
            }

            ArrayList<TameAnimal> males = new ArrayList<>();
            ArrayList<TameAnimal> females = new ArrayList<>();
            for(TameAnimal animal : animals) {
                if(animal.getGender().equals("Male") && animal.isReadyBreeding()) 
                    males.add(animal);
                else if(animal.getGender().equals("Female") && animal.isReadyBreeding())
                    females.add(animal);
            }

            int startY = windowY + 120;
            int lineHeight = 40;
            if(males.isEmpty()) {
                g2.drawString("No male animals available!", windowX + 30, startY);
            } else {
                int endIndexMale = Math.min(breedingScrollPosition + ANIMALS_PER_PAGE+2, males.size());
                for(int i = breedingScrollPosition; i < endIndexMale; i++) {
                    TameAnimal male = males.get(i);
                    int y = startY + (i - breedingScrollPosition) * lineHeight;
                    
                    if(i == selectedBreedMaleIndex && isMaleList) {
                        g2.setColor(Color.BLACK);
                        g2.drawRect(windowX + 25, y - 10, windowWidth/2 - 50, lineHeight);
                    }
                    g2.setColor(Color.WHITE);
                    g2.drawString(male.getName() + " (Ready)", windowX + 30, y + 15);
                }   
            }

            if(females.isEmpty()) {
                g2.drawString("No female animals available!", windowX + windowWidth/2 + 30, startY);
            } else {
                int endIndexFemale = Math.min(breedingScrollPosition + ANIMALS_PER_PAGE+2, females.size());
                for(int i = breedingScrollPosition; i < endIndexFemale; i++) {
                    TameAnimal female = females.get(i);
                    int y = startY + (i - breedingScrollPosition) * lineHeight;
                    
                    if(i == selectedBreedFemaleIndex && !isMaleList) {
                        g2.setColor(Color.BLACK);
                        g2.drawRect(windowX + windowWidth/2 + 25, y - 10, windowWidth/2 - 50, lineHeight);
                    }
                    g2.setColor(Color.WHITE);
                    g2.drawString(female.getName() + " (Ready)", windowX + windowWidth/2 + 30, y + 15);
                }
            }

            if(selectedMale != null) {
                g2.setFont(new Font("Arial", Font.ITALIC, 16));
                g2.drawString("Selected male: " + selectedMale.getName(), windowX + 30, windowY + windowHeight - 100);
                if(!females.isEmpty()) {
                    g2.drawString("Please select a female", windowX + (windowWidth - g2.getFontMetrics().stringWidth("Please select a female"))/2, windowY + windowHeight - 80);
                }
            }
        }

        int buttonWidth = 100;
        int buttonHeight = 40;
        backButton = new Rectangle(windowX + 30, windowY + windowHeight - buttonHeight - 20, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(backButton.x, backButton.y, backButton.width, backButton.height);
        g2.setColor(Color.BLACK);
        g2.drawString("Back", backButton.x + 30, backButton.y + 25);
    }

    public void drawKandangMenu(Graphics2D g2, Kandang kandang) {
        int windowWidth = gp.TILE_SIZE * 12;
        int windowHeight = gp.TILE_SIZE * 10;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;  
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        String title = kandang.getName() + " (" + kandang.getCurrentCapacity() + "/" + kandang.getMaxCapacity() + ")";
        int titleX = windowX + (windowWidth - g2.getFontMetrics().stringWidth(title))/2;
        g2.drawString(title, titleX, windowY + 40);

        g2.setFont(new Font("Arial", Font.PLAIN, 20));
        int startY = windowY + 80;
        int lineHeight = 30;
        
        ArrayList<TameAnimal> animals = new ArrayList<>();
        if(kandang instanceof KandangAyam) {
            animals.addAll(((KandangAyam)kandang).chickensInCage);
        }else if(kandang instanceof CowCage) {
            animals.addAll(((CowCage)kandang).cowsInCage);
        } else if(kandang instanceof SheepCage) {
            animals.addAll(((SheepCage)kandang).sheepsInCage);
        } else if(kandang instanceof PigCage) {
            animals.addAll(((PigCage)kandang).pigsInCage);
        }
        
        int endIndex = Math.min(kandangScrollPosition + ANIMALS_PER_PAGE, animals.size());
        for(int i = kandangScrollPosition; i < endIndex; i++) {
            TameAnimal animal = animals.get(i);
            int y = startY + (i - kandangScrollPosition) * (lineHeight * 2);
            
            g2.setColor(Color.WHITE);
            g2.drawString(animal.getName() + " (" + animal.getGender() + ")", windowX + 20, y);
            g2.drawString("Breeding: " + (animal.isReadyBreeding() ? "Ready" : "Not Ready"), windowX + 20, y + lineHeight);
            if(animal instanceof Chicken || animal instanceof Cow || animal instanceof Sheep || animal instanceof Pig) {
                g2.drawString("Get Item: " + (((TameAnimal)animal).isReadyGetItem() ? "Ready" : "Not Ready"), windowX + windowWidth/2, y + lineHeight);
            }
        }

        int buttonWidth = 150;
        int buttonHeight = 50;
        int buttonY = windowY + windowHeight - buttonHeight - 20;
        
        breedButton = new Rectangle(windowX + 30, buttonY, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(breedButton.x, breedButton.y, breedButton.width, breedButton.height);
        g2.setColor(Color.BLACK);
        g2.drawString("Breeding", breedButton.x + 40, breedButton.y + 30);

        getItemButton = new Rectangle(windowX + windowWidth - buttonWidth - 30, buttonY, buttonWidth, buttonHeight);
        g2.setColor(Color.WHITE);
        g2.fillRect(getItemButton.x, getItemButton.y, getItemButton.width, getItemButton.height);
        g2.setColor(Color.BLACK);
        g2.drawString("Get Item", getItemButton.x + 40, getItemButton.y + 30);
    }

    public void handleKandangScroll(int notches) {
        if(inBreedingMenu) {
            breedingScrollPosition = Math.max(0, breedingScrollPosition - notches);
        }
        else if(inGetItemMenu) {
            getItemScrollPosition = Math.max(0, getItemScrollPosition - notches);
        }
        else {
            kandangScrollPosition = Math.max(0, kandangScrollPosition + notches);
        }
    }

    public void handleKandangClick(int x, int y, Kandang kandang, Player player) {
        if(inGetItemMenu) {
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
            else if(getItemButton != null && getItemButton.contains(x, y)) {
                inGetItemMenu = true; 
            }
        }
    }
    public void drawNameInputWindow(Graphics2D g2) {
        int windowWidth = 400;
        int windowHeight = 200;
        int windowX = gp.SCREEN_WIDTH/2 - windowWidth/2;
        int windowY = gp.SCREEN_HEIGHT/2 - windowHeight/2;

       
        g2.drawImage(woodBg, windowX, windowY, windowWidth, windowHeight, null);

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(Color.WHITE);
        String title = "Name your " + animalToName.getClass().getSimpleName();
        int titleX = getXCenteredText(title);
        g2.drawString(title, titleX, windowY + 50);

        g2.setColor(Color.WHITE);
        g2.fillRect(textField.x, textField.y, textField.width, textField.height);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString(currentInput, textField.x + 5, textField.y + 20);

        if(System.currentTimeMillis() % 1000 < 500) {
            g2.drawString("|", textField.x + 5 + g2.getFontMetrics().stringWidth(currentInput), textField.y + 20);
        }

        g2.setFont(new Font("Arial", Font.ITALIC, 14));
        g2.setColor(Color.WHITE);
        g2.drawString("Press ENTER to confirm", windowX + windowWidth/2 - 70, windowY + windowHeight - 30);
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
        int barY = frameY + gp.TILE_SIZE / 2;
        int barWidth = frameWidth - gp.TILE_SIZE;
        int barHeight = gp.TILE_SIZE / 4;

        // Health bar
        g2.setColor(Color.RED);
        g2.fillRect(barX, barY, (int) (barWidth * (gp.player.health / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY, barWidth, barHeight);
        g2.drawString("Health: " + gp.player.health + "/100", barX, barY - 5);

        // Hunger bar
        g2.setColor(Color.ORANGE);
        g2.fillRect(barX, barY + gp.TILE_SIZE / 2, (int) (barWidth * (gp.player.hunger / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY + gp.TILE_SIZE / 2, barWidth, barHeight);
        g2.drawString("Hunger: " + gp.player.hunger + "/100", barX, barY + gp.TILE_SIZE / 2 - 5);

        // Thirst bar
        g2.setColor(Color.BLUE);
        g2.fillRect(barX, barY + gp.TILE_SIZE, (int) (barWidth * (gp.player.thirst / 100.0)), barHeight);
        g2.setColor(Color.BLACK);
        g2.drawRect(barX, barY + gp.TILE_SIZE, barWidth, barHeight);
        g2.drawString("Thirst: " + gp.player.thirst + "/100", barX, barY + gp.TILE_SIZE - 5);

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
        g2.drawString("Level: " + gp.player.level, barX, levelBarY - 5);
        g2.drawString("EXP: " + gp.player.exp + "/" + gp.player.maxExp, barX, levelBarY + barHeight + 15);
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

    
    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0, 0, 0, 210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
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
        int frameX = gp.TILE_SIZE * ((gp.SCREEN_WIDTH / gp.TILE_SIZE) / 4);
        int frameY =  gp.TILE_SIZE * (gp.SCREEN_HEIGHT / gp.TILE_SIZE - 13);
        int slotXStart = frameX + 30;
        int slotYStart = frameY + 35;
        int slotX = slotXStart;
        int slotY = slotYStart;
        for (int i = 0; i < gp.player.inventory.slots.length; i++) {
            Rectangle r = new Rectangle(slotX, slotY, gp.TILE_SIZE + 10, gp.TILE_SIZE + 10);
            if (r.contains(mouseX, mouseY)) return i;
            if ((i + 1) % 9 == 0) {
                slotX = slotXStart;
                slotY += (gp.TILE_SIZE + 25);
            } else {
                slotX += (gp.TILE_SIZE + 25);
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
}
