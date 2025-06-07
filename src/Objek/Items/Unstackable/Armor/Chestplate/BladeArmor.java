package Objek.Items.Unstackable.Armor.Chestplate;

public class BladeArmor extends Chestplate {
    public BladeArmor() {
        super("Blade Armor", 250, 6);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Armor/bladearmor.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
