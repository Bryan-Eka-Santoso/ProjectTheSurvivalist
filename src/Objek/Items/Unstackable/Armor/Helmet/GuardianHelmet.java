package Objek.Items.Unstackable.Armor.Helmet;

public class GuardianHelmet extends Helmet {
    public GuardianHelmet() {
        super("Guardian Helmet", 175, 0);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Armor/metalhelmet.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
