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
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Stackable;
import Objek.Player.Inventory;

public class KeyHandler implements KeyListener, MouseListener, MouseWheelListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;
    GamePanel gp;
    int temp1, temp2, counter, itemStack;
    int furnaceIdx1, furnaceIdx2;
    Item temp1Furnace, temp2Furnace;
    Sound sound = new Sound();
    boolean isTemp1Chest, isTemp2Chest;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
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
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
        int code = e.getKeyCode();
        if (gp.gameState == gp.KANDANG_STATE) {
            if(gp.ui.inBreedingMenu) {
                gp.ui.handleBreedingKeyPress(code, gp.currentKandang);
            }
            else if(gp.ui.inGetItemMenu) {
                gp.ui.handleGetItemKeyPress(code, gp.currentKandang, gp.player);
            }
        }
        if (gp.ui.showNameInput) {
            gp.ui.handleNameInputKey(code);
            return; 
        } else {
            if (code == KeyEvent.VK_W) {
                if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
                    upPressed = true;
                }
                if (gp.gameState == gp.PLAYER_CRAFTING_STATE || gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE) {
                    gp.ui.scrollUp();
                }
            }
            if (code == KeyEvent.VK_S) {
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
            }
            if (code == KeyEvent.VK_A) {
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
            }
            if (code == KeyEvent.VK_D) {
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
            }
            if (code == KeyEvent.VK_SHIFT) {
                shiftPressed = true;
            }
            if (code == KeyEvent.VK_E && gp.gameState != gp.INVENTORY_STATE) {
                gp.player.useItem(gp.player.inventory.slots[gp.ui.selectedIndex]);
            }
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState != gp.PAUSE_STATE) {
                    gp.gameState = gp.PAUSE_STATE;
                } else if (gp.gameState == gp.PAUSE_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                } 
            }
            if (code == KeyEvent.VK_I) {
                if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.INVENTORY_STATE) {
                    playSE(2);
                    if (gp.gameState == gp.PLAY_STATE) {
                        gp.gameState = gp.INVENTORY_STATE;
                    } else if (gp.gameState == gp.INVENTORY_STATE) {
                        gp.gameState = gp.PLAY_STATE;
                        gp.ui.slotRow = 0;
                        gp.ui.slotCol = 0;
                        gp.ui.selectedIndex = 0;
                    } 
                    gp.player.lightUpdated = true;
                }
            }
            if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE) {
                if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9) {
                    if (gp.gameState != gp.INVENTORY_STATE){ // Ada bug kalo game state ny di inventory
                        gp.ui.slotCol = code - KeyEvent.VK_0 - 1;
                        playSE(2);
                        gp.ui.selectedIndex = gp.ui.slotCol;
                        gp.player.lightUpdated = true;
                    }
                }
            }
            if (code == KeyEvent.VK_R && !gp.player.isBuild) {
                if (gp.gameState == gp.INVENTORY_STATE || gp.gameState == gp.PLAY_STATE) {
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
                } else if (gp.gameState == gp.OPEN_SMELTER_STATE) {
                    playSE(2);
                    if (counter == 0) {
                        if (gp.ui.selectedFurnace == -1) {
                            temp1Furnace = gp.player.inventory.slots[gp.ui.selectedIndex];
                            temp1 = gp.ui.selectedIndex;
                            furnaceIdx1 = -1;
                        } 
                        if (gp.ui.selectedFurnace == 0) {
                            temp1Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0];
                            temp1 = 0;
                            furnaceIdx1 = 0;
                        } 
                        if (gp.ui.selectedFurnace == 1) {
                            temp1Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0];
                            temp1 = 0;
                            furnaceIdx1 = 1;
                        } 
                        if (gp.ui.selectedFurnace == 2) {
                            temp1Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0];
                            temp1 = 0;
                            furnaceIdx1 = 2;
                        }
                    }
                    if (counter == 1) {
                        if (gp.ui.selectedFurnace == -1) {
                            temp2Furnace = gp.player.inventory.slots[gp.ui.selectedIndex];
                            temp2 = gp.ui.selectedIndex;
                            furnaceIdx2 = -1;
                        } 
                        if (gp.ui.selectedFurnace == 0) {
                            temp2Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0];
                            temp2 = 0;
                            furnaceIdx2 = 0;
                        } 
                        if (gp.ui.selectedFurnace == 1) {
                            temp2Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0];
                            temp2 = 0;
                            furnaceIdx2 = 1;
                        } 
                        if (gp.ui.selectedFurnace == 2) {
                            temp2Furnace = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0];
                            temp2 = 0;
                            furnaceIdx2 = 2;
                        }
                    }
                    counter++;
                    if (counter == 2) {
                        counter = 0;
                        if (furnaceIdx1 == -1 && furnaceIdx2 == -1) { // inventory ke inventory
                            Item tempItem = gp.player.inventory.slots[temp1];  
                            gp.player.inventory.slots[temp1] = gp.player.inventory.slots[temp2];
                            gp.player.inventory.slots[temp2] = tempItem;
                        } 
                        if (furnaceIdx1 == -1 && furnaceIdx2 == 0) { // inventory ke raw material
                            Item tempItem1 = gp.player.inventory.slots[temp1];  
                            Item tempItem2 = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0];
                            gp.player.inventory.slots[temp1] = tempItem2;
                            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0] = tempItem1;
                        } 
                        if (furnaceIdx1 == -1 && furnaceIdx2 == 1) { // inventory ke fuel material
                            Item tempItem1 = gp.player.inventory.slots[temp1];  
                            Item tempItem2 = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0];
                            gp.player.inventory.slots[temp1] = tempItem2;
                            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0] = tempItem1;
                        } 
                        if (furnaceIdx1 == 0 && furnaceIdx2 == -1) { // raw material ke inventory
                            Item tempItem1 = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0];  
                            Item tempItem2 = gp.player.inventory.slots[temp2];
                            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).rawMaterial[0] = tempItem2;
                            gp.player.inventory.slots[temp2] = tempItem1;
                        } 
                        if (furnaceIdx1 == 1 && furnaceIdx2 == -1) { // fuel material ke inventory material
                            Item tempItem1 = ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0];  
                            Item tempItem2 = gp.player.inventory.slots[temp2];
                            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).fuelMaterial[0] = tempItem2;
                            gp.player.inventory.slots[temp2] = tempItem1;
                        } 
                        temp1Furnace = null;
                        temp2Furnace = null;;
                    }  
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
            if (code == KeyEvent.VK_C && !gp.player.isBuild) {
                if (gp.gameState == gp.PLAY_STATE) {
                    gp.gameState = gp.PLAYER_CRAFTING_STATE;
                } else if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                } 
            }
            if (code == KeyEvent.VK_UP) {
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
            }
            if (code == KeyEvent.VK_RIGHT) {
                if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE){
                    if (gp.ui.slotCol < 8) {
                        gp.ui.slotCol++;
                    } else {
                        gp.ui.slotCol = 0;
                    }
                    gp.ui.selectedIndex = gp.ui.slotCol;
                    gp.player.lightUpdated = true;
                    playSE(2);
                }
                if (gp.gameState == gp.INVENTORY_STATE) {
                    playSE(2);
                    if (gp.ui.selectedIndex < 23) {
                        if ((gp.ui.slotCol + 1) % 9 == 0) {
                            gp.ui.slotCol = 0;
                            gp.ui.slotRow++;
                        } else {
                            gp.ui.slotCol++;
                        }
                        gp.ui.selectedIndex++;
                    }
                }
            }
            if (code == KeyEvent.VK_LEFT) {
                if (gp.player.grabbedAnimal == null && gp.gameState == gp.PLAY_STATE){
                    if (gp.ui.slotCol > 0) {
                        gp.ui.slotCol--;
                    } else {
                        gp.ui.slotCol = 8;
                    }
                    gp.ui.selectedIndex = gp.ui.slotCol;
                    gp.player.lightUpdated = true;
                    playSE(2);
                }
                if (gp.gameState == gp.INVENTORY_STATE) {
                    playSE(2);
                    if (gp.ui.selectedIndex > 0) {
                        if (gp.ui.slotCol > 0) {
                            gp.ui.slotCol--;
                        } else {
                            gp.ui.slotCol = 8;
                            gp.ui.slotRow--;
                        }
                        gp.ui.selectedIndex--;
                    }
                }
            }
            if (code == KeyEvent.VK_DOWN) {
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
            }
            if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9) {
                if (gp.gameState == gp.DROPPED_ITEM_STATE){
                    gp.gameState = gp.PLAY_STATE;
                    gp.ui.amountToDrop = 1;
                }
            }
            if (code == KeyEvent.VK_Q && !gp.player.isBuild) {
                if (gp.gameState == gp.DROPPED_ITEM_STATE){
                    gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex], gp.ui.amountToDrop);
                    gp.gameState = gp.PLAY_STATE;
                    gp.ui.amountToDrop = 1;
                } else if (gp.gameState == gp.PLAY_STATE){
                    if (gp.player.inventory.slots[gp.ui.selectedIndex] != null){
                        if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Stackable || gp.player.inventory.slots[gp.ui.selectedIndex] instanceof Buildings){
                            itemStack = gp.player.inventory.slots[gp.ui.selectedIndex].currentStack;
                            gp.gameState = gp.DROPPED_ITEM_STATE;
                        } else {
                            gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex], 1);
                        }
                    }
                }
            }
            if (code == KeyEvent.VK_P && !gp.player.isBuild) {
                if (gp.gameState == gp.OPEN_SMELTER_STATE) {
                    if (gp.ui.selectedFurnace == 2) {
                        if (((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] != null) {
                            gp.player.inventory.addItems(((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0]);
                            ((Furnace) gp.buildings.get(gp.player.buildingIndex)).cookedMaterial[0] = null;
                        }
                    }
                }
                if (gp.player.droppedItem != -1) {
                    gp.player.pickUpItem(gp.droppedItems.get(gp.player.droppedItem).droppedItem);
                    gp.player.droppedItem = -1;
                }
            }
            if (code == KeyEvent.VK_G && !gp.player.isBuild) {
                gp.player.handleGrabAction(gp.player.inventory.getSelectedItem());
            }
            if (code == KeyEvent.VK_T){
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
                }
                else if (gp.gameState == gp.KANDANG_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                    gp.currentKandang = null;
                    gp.ui.resetKandangMenuState();
                    
                }
            }
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.gameState != gp.PAUSE_STATE) {
                    gp.gameState = gp.PAUSE_STATE;
                } else if (gp.gameState == gp.PAUSE_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                } 
            }
            if (code == KeyEvent.VK_SPACE) {
                if (gp.gameState == gp.OPEN_CRAFTINGTABLE_STATE || gp.gameState == gp.OPEN_SMELTER_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                    temp1Furnace = null;
                    temp2Furnace = null;
                    gp.ui.slotCol = 0;
                    gp.ui.slotRow = 0;
                    gp.ui.selectedIndex = 0;
                    gp.ui.selectedChestIndex = 0;
                } else if (gp.gameState == gp.OPEN_CHEST_STATE) {
                    gp.gameState = gp.PLAY_STATE;
                    gp.ui.slotCol = 0;
                    gp.ui.slotRow = 0;
                    gp.ui.selectedIndex = 0;
                    gp.ui.selectedChestIndex = 0;
                } else if (gp.gameState == gp.BUILDING_STATE) {
                    Buildings building = (Buildings) gp.player.inventory.getSelectedItem().clone();
                    if (building instanceof Chest) {
                        ((Chest) building).inventory = new Inventory(32, gp);
                    }
                    if (building instanceof Furnace) {
                        ((Furnace) building).rawMaterial = new Item[1];
                        ((Furnace) building).fuelMaterial = new Item[1];
                        ((Furnace) building).cookedMaterial = new Item[1];
                    }
                    if (building.canBuild()) {
                        if (gp.player.inventory.getSelectedItem() instanceof Torch) {
                            gp.player.isPlaceTroch = true;
                        }
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
            } 
            if(code == KeyEvent.VK_F) {
                ArrayList<Point> usedPositions = new ArrayList<>();
                int col = gp.player.worldX / gp.TILE_SIZE;
                int row = gp.player.worldY / gp.TILE_SIZE;
                
                if(gp.currentMap == 0){
                    if((col == 27 || col == 28) && row == 17) {
                        gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
                        gp.currentMap = 1;
                        gp.animals.clear();
                        gp.player.getPlayerImg();
                        gp.tileM.getTileImage();
                        gp.player.worldY = 11 * gp.TILE_SIZE;
                        gp.player.worldX = 72 * gp.TILE_SIZE;
                        gp.spawnFish("Arwana", 100, usedPositions);
                        gp.spawnFish("Belida", 100, usedPositions);
                    } else if(col == 43 && row == 55){
                        gp.tileM.loadMap("ProjectTheSurvivalist/res/world/cave.txt", 2);
                        gp.currentMap = 2;
                        gp.animals.clear();
                        gp.player.getPlayerImg();
                        gp.tileM.getTileImage();
                        gp.player.worldY = 24 * gp.TILE_SIZE;
                        gp.player.worldX = 23 * gp.TILE_SIZE;
                        gp.isCave = !gp.isCave;
                        gp.eManager.lighting.setLightSource(); 
                    }
                } else if(gp.currentMap == 1){
                    if(col == 72 && row == 11) {
                        gp.tileM.loadMap("ProjectTheSurvivalist/res/world/seamap.txt", 1);
                        gp.currentMap = 0;
                        gp.animals.clear();
                        gp.player.getPlayerImg();
                        gp.tileM.getTileImage();
                        gp.player.worldY = 18 * gp.TILE_SIZE;
                        gp.player.worldX = 28 * gp.TILE_SIZE;
                    }
                } else if(gp.currentMap == 2){
                    if(col == 23 && row == 23) {
                        gp.tileM.loadMap("ProjectTheSurvivalist/res/world/map.txt", 0);
                        gp.currentMap = 0;
                        gp.animals.clear();
                        gp.player.getPlayerImg();
                        gp.tileM.getTileImage();
                        gp.player.worldY = 56 * gp.TILE_SIZE;
                        gp.player.worldX = 43 * gp.TILE_SIZE;
                    }
                }
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
    
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}