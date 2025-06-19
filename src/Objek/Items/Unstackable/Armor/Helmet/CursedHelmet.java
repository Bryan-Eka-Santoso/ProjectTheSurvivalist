package Objek.Items.Unstackable.Armor.Helmet;

public class CursedHelmet extends Helmet {

    public CursedHelmet() {
        super("Cursed Helmet", 135, 4);
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Armor/cursedhelmet.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
