package Object;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class KeyHandler implements KeyListener, MouseListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, shiftPressed;
    GamePanel gp;
    int temp1, temp2, counter;

    public KeyHandler (GamePanel gp) {
        this.gp = gp;
        temp1 = 0;
        temp2 = 0;
        counter = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        
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
            if (gp.gameState == gp.PLAY_STATE) {
                upPressed = true;
            }
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                gp.ui.scrollUp();
            }
        }
        if (code == KeyEvent.VK_S) {
            if (gp.gameState == gp.PLAY_STATE) {
                downPressed = true;
            }
            if (gp.gameState == gp.PLAYER_CRAFTING_STATE) {
                gp.ui.scrollDown();
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.gameState == gp.PLAY_STATE) {
                leftPressed = true;
            }
            if (gp.gameState == gp.INVENTORY_STATE) {
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
            if (gp.gameState == gp.PLAY_STATE) {
                rightPressed = true;
            }
            if (gp.gameState == gp.INVENTORY_STATE) {
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
            if (gp.gameState == gp.PLAY_STATE) {
                gp.gameState = gp.PAUSE_STATE;
            } else if (gp.gameState == gp.PAUSE_STATE) {
                gp.gameState = gp.PLAY_STATE;
            } 
        }
        if (code == KeyEvent.VK_I) {
            if (gp.gameState == gp.PLAY_STATE) {
                gp.gameState = gp.INVENTORY_STATE;
            } else if (gp.gameState == gp.INVENTORY_STATE) {
                gp.gameState = gp.PLAY_STATE;
                gp.ui.slotRow = 0;
                gp.ui.slotCol = 0;
                gp.ui.selectedIndex = 0;
            } 
        }
        if (code >= KeyEvent.VK_1 && code <= KeyEvent.VK_9) {
            gp.ui.slotCol = code - KeyEvent.VK_0 - 1;
            gp.ui.selectedIndex = gp.ui.slotCol;
        }
        if (code == KeyEvent.VK_R) {
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
        if (code == KeyEvent.VK_C) {
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
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
        
    }
    
}
