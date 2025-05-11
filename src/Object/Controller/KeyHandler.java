package Object.Controller;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Object.Items.Item;
import Object.Items.Unstackable.Buildings.Buildings;

public class KeyHandler implements KeyListener, MouseListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;
    GamePanel gp;
    int temp1, temp2, counter;
    Sound sound = new Sound();

    public KeyHandler (GamePanel gp) {
        this.gp = gp;
        temp1 = 0;
        temp2 = 0;
        counter = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (gp.gameState == gp.DROPPED_ITEM_STATE) {
            gp.ui.mouseX = e.getX();
            gp.ui.mouseY = e.getY();
        }
        if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
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

        if (code == KeyEvent.VK_W) {
            if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
                upPressed = true;
            }
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                gp.ui.scrollUp();
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
                downPressed = true;
            }
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                gp.ui.scrollDown();
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
                leftPressed = true;
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
        if (code == KeyEvent.VK_D) {
            if (gp.gameState == gp.PLAY_STATE || gp.gameState == gp.BUILDING_STATE) {
                rightPressed = true;
            }
            if (gp.gameState == gp.INVENTORY_STATE) {
                playSE(2);
                if (gp.ui.selectedIndex < 31) {
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
        if (code == KeyEvent.VK_SHIFT) {
            shiftPressed = true;
        }
        if (code == KeyEvent.VK_E) {
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
            if (!gp.player.isBuild) {
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
        if (gp.player.grabbedAnimal == null && !gp.player.isBuild) {
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
        }
        if (code == KeyEvent.VK_C && !gp.player.isBuild) {
            if (gp.gameState == gp.PLAY_STATE) {
                gp.gameState = gp.PLAYER_CRAFTING_STATE;
            } else if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                gp.gameState = gp.PLAY_STATE;
            } 
        }
        if (code == KeyEvent.VK_UP) {
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                if (gp.ui.selectedRecipeIndex > 0) {
                    gp.ui.selectedRecipeIndex--; 
                }
            }
        }
        if (code == KeyEvent.VK_DOWN) {
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                if (gp.ui.selectedRecipeIndex < 19) {
                    gp.ui.selectedRecipeIndex++; 
                }
            }
        }
        if (code == KeyEvent.VK_Q && !gp.player.isBuild) {
            if (gp.player.inventory.slots[gp.ui.selectedIndex] != null){
                gp.player.dropItem(gp.player.inventory.slots[gp.ui.selectedIndex]);
            }
        }
        if (code == KeyEvent.VK_P && !gp.player.isBuild) {
            if (gp.player.droppedItem != -1) {
                gp.player.pickUpItem(gp.droppedItems.get(gp.player.droppedItem).droppedItem);
                gp.player.droppedItem = -1;
            }
        }
        if (code == KeyEvent.VK_G && !gp.player.isBuild) {
            gp.player.handleGrabAction(gp.player.inventory.getSelectedItem());
        }
        if (code == KeyEvent.VK_SPACE) {
            Buildings building = (Buildings) gp.player.inventory.getSelectedItem();
            if (gp.gameState == gp.BUILDING_STATE && building.canBuild()) {
                building.worldX = gp.player.worldX;
                building.worldY = gp.player.worldY;
                gp.player.isBuild = false;
                gp.gameState = gp.PLAY_STATE;
                gp.player.inventory.removeItem(building, 1);
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
                gp.buildings.add((Buildings) building.clone());
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
        
    }
    
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
}
