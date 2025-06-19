package Objek.Items.Unstackable.Armor.Chestplate;

public class GoldChestplate extends Chestplate {
    
    public GoldChestplate() {
        super("Gold Chestplate", 65, 4);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Armor/goldchestplate2.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
