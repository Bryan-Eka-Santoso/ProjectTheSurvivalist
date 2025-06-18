package Objek.Items.Unstackable.Armor.Leggings;

import javax.imageio.ImageIO;

public class GoldLeggings extends Leggings {
    
    public GoldLeggings() {
        super("Gold Leggings", 60, 2);
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Armor/goldpants2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
