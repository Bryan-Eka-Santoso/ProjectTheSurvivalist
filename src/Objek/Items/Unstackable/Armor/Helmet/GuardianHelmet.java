package Objek.Items.Unstackable.Armor.Helmet;

public class GuardianHelmet extends Helmet {
    
    public GuardianHelmet() {
        super("Guardian Helmet", 175, 0);
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Armor/guardianhelmet.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
