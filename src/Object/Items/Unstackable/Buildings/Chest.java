package Object.Items.Unstackable.Buildings;

import Object.Controller.GamePanel;
import Object.Player.Inventory;

public class Chest extends Buildings {
    Inventory inventory;
    final int maxSize = 15; // Ukuran maksimum inventory chest
    GamePanel gp;

    public Chest(int x, int y, GamePanel gp) {
        super("Chest");
        inventory = new Inventory(maxSize, gp);
        this.gp = gp;
    }   

    public void showInventory() {
        System.out.println("Chest Inventory:");
    }
}