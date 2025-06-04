package Objek.Items.Unstackable.Armor.Chestplate;

public class BladeArmor extends Chestplate {
    public BladeArmor() {
        super("Blade Armor", 120, 7);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Armor/goldchestplate2.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
