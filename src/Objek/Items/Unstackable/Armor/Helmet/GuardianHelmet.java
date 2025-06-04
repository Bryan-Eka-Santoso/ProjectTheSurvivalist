package Objek.Items.Unstackable.Armor.Helmet;

public class GuardianHelmet extends Helmet {
    public GuardianHelmet() {
        super("Guardian Helmet", 175, 2);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/guardianhelmet.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
