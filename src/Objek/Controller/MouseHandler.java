package Objek.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {
    
    GamePanel gp;
    
    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(gp.gameState == gp.SHOP_STATE) {
            gp.ui.handleShopClick(e.getX(), e.getY());
        }
        
        // Other mouse click handlers...
    }
    
    // Other mouse event methods if needed...
}