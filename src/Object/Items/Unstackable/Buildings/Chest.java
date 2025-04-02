package Object.Items.Unstackable.Buildings;
import Object.Player.Inventory;

public class Chest extends Buildings {
    Inventory inventory;
    final int maxSize = 15; // Ukuran maksimum inventory chest

    public Chest(int x, int y) {
        super("Chest", x, y);
        inventory = new Inventory(maxSize);
    }
}
