package Objek.Items.Unstackable.Armor.Chestplate;

public class GoldChestplate extends Chestplate {
    public GoldChestplate() {
        super("Gold Chestplate", 100, 20);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Armor/goldchestplate.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
