package Objek.Controller;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import Objek.Fish.Arwana;
import Objek.Fish.Belida;
import Objek.Fish.Golden;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.GoldenFish;
import Objek.Items.StackableItem.Stackable;
import Objek.Items.StackableItem.Foods.RawFoods.RawArwana;
import Objek.Items.StackableItem.Foods.RawFoods.RawBelida;
import Objek.Items.Unstackable.FishingRod;
import Objek.Items.Unstackable.Immortality;
import Objek.Items.Unstackable.Armor.Armor;
import Objek.Player.Inventory;
import Objek.Player.Player;

public class KeyHandler implements KeyListener, MouseListener, MouseWheelListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;
    GamePanel gp;
    int temp1, temp2, counter, itemStack, selectCounter;
    int furnaceIdx1, furnaceIdx2;
    Item temp1Furnace, temp2Furnace;
    Sound sound = new Sound();
    Spawn sp;
    boolean isTemp1Chest, isTemp2Chest;
    Random rand = new Random();

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
        this.sp = new Spawn(gp);
        temp1 = 0;
        temp2 = 0;
        counter = 0;
        isTemp1Chest = false;
        isTemp2Chest = false;
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if(gp.gameState == gp.KANDANG_STATE) {
            gp.ui.handleKandangScroll(e.getWheelRotation());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(gp.gameState == gp.KANDANG_STATE) {
            gp.ui.handleKandangClick(e.getX(), e.getY(), gp.currentKandang, gp.player);
        }
        if (gp.gameState == gp.DROPPED_ITEM_STATE) {
            gp.ui.mouseX = e.getX();
            gp.ui.mouseY = e.getY();
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE || gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
            gp.ui.mouseX = e.getX();
            gp.ui.mouseY = e.getY();
            for (int i = 0; i < gp.ui.itemHitboxes.size(); i++) {
                Rectangle r = gp.ui.itemHitboxes.get(i);
                if (r.contains(gp.ui.mouseX, gp.ui.mouseY)) {
                    Item clickedItem = gp.ui.itemList.get(i);
                    gp.player.recipe.craft(gp.player, clickedItem.name);
                    System.out.println("Clicked on item: " + clickedItem.name);
                    break;
                }
            }
        }
        if (gp.gameState == gp.OPEN_CHEST_STATE) {
            gp.ui.mouseX = e.getX();
            gp.ui.mouseY = e.getY();
            Chest chest = (Chest) gp.buildings.get(gp.player.buildingIndex);
            Object[] result = gp.ui.getClickedInventoryOrChestSlot(gp.ui.mouseX, gp.ui.mouseY, chest);
            if (result != null) {
                boolean isChest = (boolean) result[0];
                int idx = (int) result[1];
                if (isChest) {
                    // Move from chest to inventory
                    if (chest.inventory.slots[idx] != null) {
                        gp.player.inventory.addItems(chest.inventory.slots[idx]);
                        chest.inventory.slots[idx] = null;
                    }
                } else {
                    // Move from inventory to chest
                    if (gp.player.inventory.slots[idx] != null) {
                        chest.inventory.addItems(gp.player.inventory.slots[idx]);
                        gp.player.inventory.slots[idx] = null;
                    }
                }
            }
        }
        if (gp.gameState == gp.OPEN_SMELTER_STATE){
            gp.ui.mouseX = e.getX();
            gp.ui.mouseY = e.getY();
            Furnace furnace = (Furnace) gp.buildings.get(gp.player.buildingIndex);
            Object[] result = gp.ui.getClickedInventoryOrFurnaceSlot(gp.ui.mouseX, gp.ui.mouseY, furnace);
            if (result != null) {
                String area = (String) result[0];
                int idx = (int) result[1];
                if (area.equals("furnace")) {
                    if (idx == 0 && furnace.rawMaterial[0] != null) {
                        gp.player.inventory.addItems(furnace.rawMaterial[0]);
                        furnace.rawMaterial[0] = null;
                    } else if (idx == 1 && furnace.fuelMaterial[0] != null) {
                        gp.player.inventory.addItems(furnace.fuelMaterial[0]);
                        furnace.fuelMaterial[0] = null;
                    } else if (idx == 2 && furnace.cookedMaterial[0] != null) {
                        gp.player.inventory.addItems(furnace.cookedMaterial[0]);
                        furnace.cookedMaterial[0] = null;
                    }
                } else if (area.equals("inventory")) {
                    if (gp.player.inventory.slots[idx] != null) {
                        Item item = gp.player.inventory.slots[idx];
                        if (furnace.rawMaterial[0] == null) {
                            furnace.rawMaterial[0] = item;
                            gp.player.inventory.slots[idx] = null;
                        } else if (furnace.rawMaterial[0].name.equals(item.name)) {
                            furnace.rawMaterial[0].currentStack += item.currentStack;
                            gp.player.inventory.slots[idx] = null;
                        } else if (furnace.fuelMaterial[0] == null) {
                            furnace.fuelMaterial[0] = item;
                            gp.player.inventory.slots[idx] = null;
                        } else if (furnace.fuelMaterial[0].name.equals(item.name)) {
                            furnace.fuelMaterial[0].currentStack += item.currentStack;
                            gp.player.inventory.slots[idx] = null;
                        } 
                        // Optionally: handle cooked slot if you want to allow putting cooked items back
                    }
                }
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gp.gameState == gp.INVENTORY_STATE && !gp.player.isFrozen) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            Integer slotIdx = gp.ui.getClickedInventorySlot(mouseX, mouseY);
            if (slotIdx != null && gp.player.inventory.slots[slotIdx] != null) {
                Item item = gp.player.inventory.slots[slotIdx];
                if (item instanceof Stackable || item instanceof Buildings) {
                    gp.player.dropItem(item, item.currentStack, gp.currentMap);
                } else {
                    gp.player.dropItem(item, 1, gp.currentMap);
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();
        gp.player.lightUpdated = true;
        
        if (gp.gameState == gp.GAME_OVER_STATE) {
            if (code == KeyEvent.VK_R) RPressed();
            if (code == KeyEvent.VK_Q) QPressed();
            if (code == KeyEvent.VK_E && gp.player.inventory.hasItem("Immortality")) EPressed();
            return; // Do nothing if game is overs
        }
        if (gp.gameState == gp.KANDANG_STATE) {
            if(gp.ui.inBreedingMenu) {
                gp.ui.handleBreedingKeyPress(code, gp.currentKandang);
            } else if(gp.ui.inGetItemMenu) {
                gp.ui.handleGetItemKeyPress(code, gp.currentKandang, gp.player);
            } else if(gp.ui.inRemoveMenu){
                gp.ui.handleRemoveKeyPress(code, gp.currentKandang, gp.player);
            }
        }
        if (gp.ui.showNameInput) {  
            gp.ui.handleNameInputKey(code);
            return; 
        } else {
            if (gp.gameState == gp.FISHING_STATE) {
                if (code == KeyEvent.VK_ENTER) {
                    EnterPressed();
                }
                if (code == KeyEvent.VK_ESCAPE) {
                    EscapePressed();
                }
                return;
            }
            if (code == KeyEvent.VK_W && !gp.player.isSleeping) {
                WPressed();
            }
            if (code == KeyEvent.VK_S && !gp.player.isSleeping) {
                SPressed();
            }
            if (code == KeyEvent.VK_A && !gp.player.isSleeping) {
                APressed();
            }
            if (code == KeyEvent.VK_D && !gp.player.isSleeping) {
                DPressed();
            }
            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }
            if (code == KeyEvent.VK_E) {
                EPressed();
            }
            if (code == KeyEvent.VK_I) {
                IPressed();
            }
            if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE) {
                if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9) {
                    OneToNinePressed(code);
                }
            }
            if (code == KeyEvent.VK_R && !gp.player.isBuild) {
                RPressed();
            }
            if (code == KeyEvent.VK_UP) {
                UpPressed();
            }
            if (code == KeyEvent.VK_LEFT) {
                LeftPressed();
            }
            if (code == KeyEvent.VK_C && !gp.player.isBuild) {
                if (shiftPressed){
                    ShiftCPressed();
                } else {
                    CPressed();
                }
            }
            if (code == KeyEvent.VK_RIGHT) {
                RightPressed();
            }
            if (code == KeyEvent.VK_DOWN) {
                DownPressed();
            }
            if (code == KeyEvent.VK_Q && !gp.player.isBuild) {
                QPressed();
            }
            if (code == KeyEvent.VK_P && !gp.player.isBuild) {
                PPressed();
            }
            if (code == KeyEvent.VK_G && !gp.player.isBuild) {
                GPressed();
            }
            if (code == KeyEvent.VK_T){
                TPressed();
            }
            if (code == KeyEvent.VK_ESCAPE) {
                EscapePressed();
            }
            if (code == KeyEvent.VK_SPACE) {
                SpacePressed();
            } 
            if(code == KeyEvent.VK_F) {
                FPressed();
            } 
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        }
        
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_A) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = false;
        }
        if(code == KeyEvent.VK_E) {
            gp.player.isCutting = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        if (gp.ui.showNameInput) {
            if (e.getKeyChar() == 'g' || e.getKeyChar() == 'G') {
                return;
            }
            gp.ui.handleNameInput(e.getKeyChar());
        }
    }

    public void WPressed() {
        if (gp.gameState == gp.ACHIEVEMENT_STATE){
            if (gp.ui.achievementScroll > 0) {
                gp.ui.achievementScroll--;
                playSE(2);
            }
        }
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
            upPressed = true;
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE || gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
            gp.ui.scrollUp();
        }
    }

    public void APressed() {
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
            leftPressed = true;
        }
        if (gp.gameState == gp.OPEN_CHEST_STATE) {
            playSE(2);
            if (gp.ui.isPointingChest && gp.ui.selectedChestIndex > 0) {
                if (gp.ui.selectedChestIndex > 0) {
                    if (gp.ui.slotCol > 0) {
                        gp.ui.slotCol--;
                    } else {
                        gp.ui.slotCol = 3;
                        gp.ui.slotRow--;
                    }
                    gp.ui.selectedChestIndex--;
                }
            } else if (gp.ui.selectedIndex > 0) {
                if (gp.ui.selectedIndex > 0) {
                    if (gp.ui.slotCol > 0) {
                        gp.ui.slotCol--;
                    } else {
                        gp.ui.slotCol = 3;
                        gp.ui.slotRow--;
                    }
                    gp.ui.selectedIndex--;
                }
            }
        }
        if (gp.gameState == gp.OPEN_SMELTER_STATE && gp.ui.selectedFurnace == -1) {
            playSE(2);
            if (gp.ui.selectedIndex > 0) {
                if (gp.ui.slotCol > 0) {
                    gp.ui.slotCol--;
                } else {
                    gp.ui.slotCol = 3;
                    gp.ui.slotRow--;
                }
                gp.ui.selectedIndex--;
            }
        }
        if (gp.gameState == gp.INVENTORY_STATE && selectCounter == 0) {
            playSE(2);
            if (gp.ui.selectedIndex > 0) {
                if (gp.ui.slotCol > 0) {
                    gp.ui.slotCol--;
                } else {
                    gp.ui.slotCol = 3;
                    gp.ui.slotRow--;
                }
                gp.ui.selectedIndex--;
            }
        }
    }
    
    public void SPressed() {
        if (gp.gameState == gp.ACHIEVEMENT_STATE){
            if (gp.ui.achievementScroll < gp.aManager.achievements.size() - UI.ACHIEVEMENTS_PER_PAGE) {
                gp.ui.achievementScroll++;
                playSE(2);
            }
        }
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
            downPressed = true;
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE || gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
            gp.ui.scrollDown();
        }
        if (gp.gameState == gp.OPEN_CHEST_STATE) {
            gp.ui.selectedChestIndex = 0;
            gp.ui.selectedIndex = 0;
            gp.ui.slotRow = 0;
            gp.ui.slotCol = 0;
            gp.ui.isPointingChest = !gp.ui.isPointingChest;
        }
        if (gp.gameState == gp.OPEN_SMELTER_STATE) {
            gp.ui.selectedIndex = 0;
            gp.ui.slotRow = 0;
            gp.ui.slotCol = 0;
            if (gp.ui.selectedFurnace < 3) {
                gp.ui.selectedFurnace++;
                gp.ui.canSelectInventory = true;
                if (gp.ui.selectedFurnace == 3) {
                    gp.ui.canSelectInventory = false;
                    gp.ui.selectedFurnace = -1;
                }
            } else {
                gp.ui.selectedFurnace = 0;
            }
        }
        if (gp.gameState == gp.INVENTORY_STATE) {
            playSE(2);
            temp1 = gp.ui.selectedIndex;
            gp.ui.selectedIndex = 0;
            gp.ui.slotRow = 0;
            gp.ui.slotCol = 0;
            selectCounter++;
            System.out.println(selectCounter);
            if (selectCounter > 4) {
                selectCounter = 0;
            }
        }
    }

    public void DPressed() {
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
            rightPressed = true;
        }
        if (gp.gameState == gp.OPEN_CHEST_STATE) {
            playSE(2);
            int maxIndex = gp.ui.isPointingChest ? 31 : 23;
            if (gp.ui.isPointingChest) {
                if (gp.ui.selectedChestIndex < maxIndex) {
                    if ((gp.ui.slotCol + 1) % 4 == 0) {
                        gp.ui.slotCol = 0;
                        gp.ui.slotRow++;
                        gp.ui.selectedChestIndex++;
                    } else {
                        gp.ui.slotCol++;
                        gp.ui.selectedChestIndex++;
                    }
                }
            } else {
                if (gp.ui.selectedIndex < maxIndex) {
                    if ((gp.ui.slotCol + 1) % 4 == 0) {
                        gp.ui.slotCol = 0;
                        gp.ui.slotRow++;
                        gp.ui.selectedIndex++;
                    } else if (gp.ui.selectedIndex < maxIndex) {
                        gp.ui.slotCol++;
                        gp.ui.selectedIndex++;
                    }
                }
            }
        }
        if (gp.gameState == gp.OPEN_SMELTER_STATE && gp.ui.selectedFurnace == -1) {
            playSE(2);
            if (gp.ui.selectedIndex < 23) {
                if ((gp.ui.slotCol + 1) % 4 == 0) {
                    gp.ui.slotCol = 0;
                    gp.ui.slotRow++;
                } else {
                    gp.ui.slotCol++;
                }
                gp.ui.selectedIndex++;
            }
        }
        if (gp.gameState == gp.INVENTORY_STATE && selectCounter == 0) {
            playSE(2);
            if (gp.ui.selectedIndex < 23) {
                if ((gp.ui.slotCol + 1) % 4 == 0) {
                    gp.ui.slotCol = 0;
                    gp.ui.slotRow++;
                } else {
                    gp.ui.slotCol++;
                }
                gp.ui.selectedIndex++;
            }
        }
    }

    public void EPressed() {
        if (gp.gameState == gp.ACHIEVEMENT_STATE){
            System.out.println("Disabled");
            return;
        }
        if (gp.gameState == gp.GAME_OVER_STATE){
            gp.gameState = gp.PLAY_STATE;
            gp.player.inventory.removeItem(new Immortality(), 1);
            gp.player.health = 40;
        } else if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Armor || gp.gameState != gp.INVENTORY_STATE) {
            gp.player.useItem(gp.player.inventory.slots[gp.ui.selectedIndex]);
        }
    }

    public void CPressed() {
        if (gp.gameState == gp.PLAY_STATE) {
            gp.gameState = gp.PLAYER_CRAFTING_STATE;
        } else if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
            gp.gameState = gp.PLAY_STATE;
            gp.ui.scrollY = 0; // Reset scroll position when exiting crafting state
        } 
    }

    public void RPressed() {
        if (gp.gameState == gp.ACHIEVEMENT_STATE){
            System.out.println("Disabled");
            return;
        }
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.INVENTORY_STATE) {
            playSE(2);
            if (counter == 0) {
                temp1 = gp.ui.selectedIndex;
            }
            if (counter == 1) {
                temp2 = gp.ui.selectedIndex;
            }
            counter++;
            if (counter == 2) {
                counter = 0;
                gp.player.inventory.swapItems(temp1, temp2);
            }
            isTemp1Chest = false;
            isTemp2Chest = false;
        }
        if (gp.gameState == gp.GAME_OVER_STATE) {
            if (gp.currentMap == 2) {
                gp.monsters.clear();
            }
            if (gp.currentMap == 1) {
                gp.fish.clear();
            }
            if (gp.player.inventory.hasItem("Immortality")) {
                gp.player.dropAllItems();
                gp.player.health = 0;
                gp.player.daysAlive = 0;
                gp.eManager.lighting.filterAlpha = gp.eManager.lighting.filterAlphaTemp;
            }
            gp.player = new Player("Player", gp.player.level, gp, gp.keyH);
            gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
            gp.currentMap = 0;
            gp.fish.clear();
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.gameState = gp.PLAY_STATE;
            gp.checkAndRespawnAnimals();
        }
    }

    public void FPressed() {
        ArrayList<Point> usedPositions = new ArrayList<>();
        int col = gp.player.worldX / gp.TILE_SIZE;
        int row = gp.player.worldY / gp.TILE_SIZE;

        System.out.println(col + " " + row);
        
        if(gp.currentMap == 0){
            if((col == 27 || col == 28) && row == 17) {
                int randGetGolden = rand.nextInt(2);
                gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
                gp.currentMap = 1;
                gp.animals.clear();
                gp.player.getPlayerImg();
                gp.tileM.getTileImage();
                gp.player.worldY = 25 * gp.TILE_SIZE;
                gp.player.worldX = 60 * gp.TILE_SIZE;
                sp.spawnFish("Arwana", 20, usedPositions);
                sp.spawnFish("Belida", 20, usedPositions);
                gp.stopMusic();
                gp.playMusic(18);
                if(randGetGolden == 1){
                    sp.spawnFish("Golden", 2, usedPositions);
                }
            }
        } else if (gp.currentMap == 1){
            if(col == 60 && row == 25) {
                gp.tileM.loadMap("ProjectTheSurvivalist/res/world/seamap.txt", 1);
                gp.currentMap = 0;
                gp.fish.clear();
                gp.stopMusic();
                gp.playMusic(7);
                gp.player.getPlayerImg();
                gp.tileM.getTileImage();
                gp.player.worldY = 18 * gp.TILE_SIZE;
                gp.player.worldX = 28 * gp.TILE_SIZE;
                gp.checkAndRespawnAnimals();
            }
        }
    }

    public void TPressed() {
        if (gp.gameState == gp.PLAY_STATE) {
            for(Buildings building : gp.buildings) {
                if(building instanceof Kandang) {
                    if(Math.abs(gp.player.worldX - building.worldX) <= gp.TILE_SIZE && 
                    Math.abs(gp.player.worldY - building.worldY) <= gp.TILE_SIZE) {
                        gp.currentKandang = (Kandang)building;
                        gp.gameState = gp.KANDANG_STATE;
                        return;
                    }
                }
            }
        } else if (gp.gameState == gp.KANDANG_STATE) {
            gp.gameState = gp.PLAY_STATE;
            gp.currentKandang = null;
            gp.ui.resetKandangMenuState();
        }
    }

    public void QPressed() {
        if (gp.player.isFrozen){
            return; // Prevent dropping items if player is frozen
        }
        if (gp.gameState == gp.INVENTORY_STATE && gp.player.inventory.slots[gp.ui.selectedIndex] != null) {
            gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex], 1, gp.currentMap);
        }
        if (gp.gameState == gp.DROPPED_ITEM_STATE){
            gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex], gp.ui.amountToDrop, gp.currentMap);
            gp.gameState = gp.PLAY_STATE;
            gp.ui.amountToDrop = 1;
        } else if (gp.gameState == gp.PLAY_STATE){
            if (gp.player.inventory.slots[gp.ui.selectedIndex] != null){
                if ((gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Stackable || gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Buildings) && gp.player.inventory.slots[gp.ui.selectedIndex].currentStack > 1) {
                    itemStack = gp.player.inventory.slots[gp.ui.selectedIndex].currentStack;
                    gp.gameState = gp.DROPPED_ITEM_STATE;
                } else {
                    gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex], 1, gp.currentMap);
                }
            }
        } else if (gp.gameState == gp.GAME_OVER_STATE) {
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(gp);
            topFrame.setContentPane(new MenuPanel(topFrame));
            topFrame.revalidate(); // Memaksa refresh layout
            topFrame.repaint();
            gp.sound.stop();
        }
    }

    public void PPressed() {
        if (gp.gameState == gp.OPEN_SMELTER_STATE) {
            if (gp.ui.selectedFurnace == 2) {
                if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] != null) {
                    gp.player.inventory.addItems(((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0]);
                    ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] = null;
                }
            }
        }
        if (gp.player.droppedItem != -1) {
            if (gp.currentMap == gp.droppedItems.get(gp.player.droppedItem).mapIndex) {
                gp.player.pickUpItem(gp.droppedItems.get(gp.player.droppedItem).droppedItem);
                playSE(22);
                gp.player.droppedItem = -1;
            }
        }
    }

    public void GPressed() {
        gp.player.handleGrabAction(gp.player.inventory.getSelectedItem());
    }

    public void LeftPressed() {
        if (gp.gameState == gp.DROPPED_ITEM_STATE){
            gp.gameState = gp.PLAY_STATE;
            gp.ui.amountToDrop = 1;
        }
        if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE){
            if (gp.ui.slotCol == 0) {
                gp.ui.slotCol = 9;
            } 
            if (gp.ui.slotCol > 0) {
                gp.ui.slotCol--;
                playSE(2);
            } else {
                playSE(2);
                if (counter == 0) {
                    if (gp.ui.isPointingChest) {
                        temp1 = gp.ui.selectedChestIndex;
                        isTemp1Chest = true;
                    } else {
                        temp1 = gp.ui.selectedIndex;
                    }
                }
                if (counter == 1) {
                    if (gp.ui.isPointingChest) {
                        temp2 = gp.ui.selectedChestIndex;
                        isTemp2Chest = true;
                    } else {
                        temp2 = gp.ui.selectedIndex;
                    }
                }
                counter++;
                if (counter == 2) {
                    counter = 0;
                    if (isTemp1Chest && isTemp2Chest) {
                        Item tempItem = ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp1];  
                        ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp1] = ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp2];
                        ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp2] = tempItem;
                    } else if (isTemp1Chest && !isTemp2Chest) {
                        Item tempItem1 = ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp1];  
                        Item tempItem2 = gp.player.inventory.slots[temp2];
                        ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp1] = tempItem2;
                        gp.player.inventory.slots[temp2] = tempItem1;
                    } else if (!isTemp1Chest && isTemp2Chest) {
                        Item tempItem1 = gp.player.inventory.slots[temp1];
                        Item tempItem2 = ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp2];
                        gp.player.inventory.slots[temp1] = tempItem2;
                        ((Chest) gp.buildings.get(gp.player.buildingIndex)).inventory.slots[temp2] = tempItem1;
                    } else {
                        gp.player.inventory.swapItems(temp1, temp2);
                    }
                    isTemp1Chest = false;
                    isTemp2Chest = false;
                }
            }
        }
    }

    public void RightPressed() {
        if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE){
            if (gp.ui.slotCol < 8) {
                gp.ui.slotCol++;
            } else {
                gp.ui.slotCol = 0;
            }
            gp.ui.selectedIndex = gp.ui.slotCol;
            playSE(2);
        }
    }

    public void UpPressed() {
        if (gp.gameState == gp.DROPPED_ITEM_STATE){
            if (gp.ui.amountToDrop < itemStack){
                gp.ui.amountToDrop++;
            }
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
            if (gp.ui.selectedRecipeIndex > 0) {
                gp.ui.selectedRecipeIndex--; 
            }
        }
        playSE(2);
    }

    public void DownPressed() {
        if (gp.gameState == gp.DROPPED_ITEM_STATE){
            if (gp.ui.amountToDrop > 1){
                gp.ui.amountToDrop--;
            }
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
            if (gp.ui.selectedRecipeIndex < 19) {
                gp.ui.selectedRecipeIndex++; 
            }
        }
        playSE(2);
    }

    public void IPressed() {
        if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.INVENTORY_STATE) {
            playSE(2);
            if (gp.gameState == gp.PLAY_STATE) {
                gp.gameState = gp.INVENTORY_STATE;
                gp.ui.isSelectInventory = true;
                selectCounter = 0;
                gp.ui.slotRow = 0;
                gp.ui.slotCol = 0;
                gp.ui.selectedIndex = 0;
            } else if (gp.gameState == gp.INVENTORY_STATE) {
                gp.gameState = gp.PLAY_STATE;
                gp.ui.slotRow = 0;
                gp.ui.slotCol = 0;
                gp.ui.selectedIndex = 0;
            } 
            temp1 = 0;
            temp2 = 0;
        }
    }

    public void OneToNinePressed(int code) {
        if (gp.gameState != gp.INVENTORY_STATE && gp.gameState != gp.FISHING_STATE && !gp.player.isSleeping) { // Ada bug kalo game state ny di inventory
            gp.ui.slotCol = code - KeyEvent.VK_0 - 1;
            playSE(2);
            gp.ui.selectedIndex = gp.ui.slotCol;
        }
    }

    public void EnterPressed() {
        int fishStrength = gp.ui.caughtFish.strength;
        int playerStrength = gp.player.strengthRod;
        
        int fishRandomStrength = gp.ui.random.nextInt(fishStrength) + (fishStrength / 2);
        int playerRandomStrength = gp.ui.random.nextInt(playerStrength) + (playerStrength / 2);
        
        int strengthDifference = playerRandomStrength - fishRandomStrength;

        if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof FishingRod) {
            ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength += strengthDifference;
            
            if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength > 100) {
                ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength = 100;
            } else if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength < 0) {
                ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength = 0;
            }

            if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength >= 100) {
                gp.ui.fishingSuccessful = true;
                
                
                gp.ui.showDapatIkanMessage(gp.ui.caughtFish);
                if (gp.fish.get(gp.ui.fishIndex) instanceof Arwana) {
                    gp.player.inventory.addItems(new RawArwana(1));
                } 
                
                if (gp.fish.get(gp.ui.fishIndex) instanceof Belida) {
                    gp.player.inventory.addItems(new RawBelida(1));
                } 

                if (gp.fish.get(gp.ui.fishIndex) instanceof Golden) {
                    gp.player.inventory.addItems(new GoldenFish(1));
                } 
                
                ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength = ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).maxStr;
                ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability -= 20;
                if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability <= 0) {
                    gp.player.inventory.slots[gp.ui.selectedIndex] = null;
                }
                gp.fish.remove(gp.ui.fishIndex);
                gp.player.totalFishCaught++;
                
                gp.gameState = gp.PLAY_STATE;
                
            } else if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).strength <= 0) {
                gp.ui.showGagalDapatIkanMessage(gp.ui.caughtFish);
                
                ((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability -= 20;
                if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability <= 0) {
                    gp.player.inventory.slots[gp.ui.selectedIndex] = null;
                }
                gp.fish.remove(gp.ui.fishIndex);
                
                gp.gameState = gp.PLAY_STATE;
            }
            
            if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof FishingRod) {
                if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability <= 0) {
                    gp.ui.showRodRusakMessage();
                    gp.gameState = gp.PLAY_STATE;
                }
            }
        }
    }

    public void EscapePressed() {
        if (gp.gameState == gp.FISHING_STATE) {
            gp.gameState = gp.PLAY_STATE;
        } else if (gp.gameState != gp.PAUSE_STATE) {
            gp.gameState = gp.PAUSE_STATE;
            gp.sound.stop();
        } else if (gp.gameState == gp.PAUSE_STATE) {
            gp.gameState = gp.PLAY_STATE;
            gp.sound.play();
        } 
    }

    public void ShiftCPressed() {
        if (gp.gameState == gp.PLAY_STATE) {
            gp.gameState = gp.ACHIEVEMENT_STATE;
            gp.ui.slotRow = 0;
            gp.ui.slotCol = 0;
            gp.ui.selectedIndex = 0;
        } else if (gp.gameState == gp.ACHIEVEMENT_STATE) {
            gp.gameState = gp.PLAY_STATE;
            gp.ui.achievementScroll = 0;
            gp.ui.slotRow = 0;
            gp.ui.slotCol = 0;
            gp.ui.selectedIndex = 0;
        }
    }

    public void SpacePressed() {
        int col = gp.player.worldX / gp.TILE_SIZE;
        int row = gp.player.worldY / gp.TILE_SIZE;

        if (gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE || gp.gameState == gp.OPEN_SMELTER_STATE) {
            gp.gameState = gp.PLAY_STATE;
            temp1Furnace = null;
            temp2Furnace = null;
            gp.ui.slotCol = 0;
            gp.ui.slotRow = 0;
            gp.ui.selectedIndex = 0;
            gp.ui.selectedChestIndex = 0;
            gp.ui.scrollY = 0; // Reset scroll position when exiting crafting state
        } else if (gp.gameState == gp.OPEN_CHEST_STATE) {
            playSE(14);
            gp.gameState = gp.PLAY_STATE;
            gp.ui.slotCol = 0;
            gp.ui.slotRow = 0;
            gp.ui.selectedIndex = 0;
            gp.ui.selectedChestIndex = 0;
        } else if (gp.gameState == gp.BUILDING_STATE) {
            Buildings building = (Buildings) gp.player.inventory.getSelectedItem().clone();
            building.buildingMap = gp.currentMap;
            playSE(11);
            if (building instanceof Chest) {
                ((Chest) building).inventory = new Inventory(32, gp);
            }
            if (building instanceof Furnace) {
                ((Furnace) building).rawMaterial = new Item[1];
                ((Furnace) building).fuelMaterial = new Item[1];
                ((Furnace) building).cookedMaterial = new Item[1];
            }
            if (building.canBuild()) {
                building.worldX = gp.player.worldX;
                building.worldY = gp.player.worldY;
                gp.player.isBuild = false;
                gp.gameState = gp.PLAY_STATE;
                gp.player.inventory.removeItem(gp.player.inventory.getSelectedItem(), 1);
                switch (gp.player.direction) {
                    case "up":
                        building.worldY -= gp.TILE_SIZE;
                    break;
                    case "down":
                        building.worldY += gp.TILE_SIZE;
                    break;
                    case "left":
                        building.worldX -= building.width;
                    break;
                    case "right":
                        building.worldX += gp.TILE_SIZE;
                    break;
                }
                gp.buildings.add((Buildings) building);
            }
        } else if (gp.buildings.size() > 0) {
            counter = 0;
            gp.ui.slotCol = 0;
            gp.ui.slotRow = 0;
            gp.ui.selectedIndex = 0;
            gp.ui.selectedChestIndex = 0;
            if (gp.player.buildingIndex != -1 && gp.gameState == gp.PLAY_STATE) {
                gp.player.interactBuild(gp.buildings.get(gp.player.buildingIndex));
            }
        }

        if(gp.currentMap == 2){
            if ((col == 22 || col == 23) && row == 23) {
                gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
                gp.currentMap = 0;
                gp.monsters.clear();
                gp.checkAndRespawnAnimals();
                gp.stopMusic();
                gp.playMusic(7);
                gp.player.getPlayerImg();
                gp.tileM.getTileImage();
                gp.player.worldY = 51 * gp.TILE_SIZE;
                gp.player.worldX = 51 * gp.TILE_SIZE;
                gp.eManager.lighting.filterAlpha = gp.eManager.lighting.filterAlphaTemp;
                gp.checkAndRespawnAnimals();
            }
        }
        if(gp.currentMap == 3){
            if(col == 52 && row == 53) {
                gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
                gp.currentMap = 0;
                gp.checkAndRespawnAnimals();
                gp.stopMusic();
                gp.playMusic(7);
                gp.player.getPlayerImg();
                gp.tileM.getTileImage();
                gp.player.worldY = 41 * gp.TILE_SIZE + 10;
                gp.player.worldX = 40 * gp.TILE_SIZE + 10;
                gp.eManager.lighting.filterAlpha = gp.eManager.lighting.filterAlphaTemp;
                gp.checkAndRespawnAnimals();
            }
        }
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
    
}