package Objek.Items.Unstackable.Armor.Boots;

public class RapidBoots extends Boots {

    public RapidBoots() {
        super("Rapid Boots", 125, 4);
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Armor/rapidboots.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
