package Objek.Items.Unstackable.Armor.Helmet;

public class WolfCloak extends Helmet {
    public WolfCloak() {
        super("Wolf Cloak", 100, 4);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Armor/wolfcloak.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
