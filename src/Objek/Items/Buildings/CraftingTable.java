package Objek.Items.Buildings;

import Objek.Controller.GamePanel;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CraftingTable extends Buildings {
    
    public CraftingTable(GamePanel gp, int currentStack, int buildingMap) {
        super("Crafting Table", 10, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48, buildingMap);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Buildings/craftingTable.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    
}
