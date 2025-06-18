package Objek.Items.Unstackable.Armor.Chestplate;

public class BladeArmor extends Chestplate {
    
    public BladeArmor() {
        super("Blade Armor", 250, 6);
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Armor/bladearmor.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
