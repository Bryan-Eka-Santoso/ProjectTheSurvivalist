package Objek.Items.Unstackable.Armor.Helmet;

public class CursedHelmet extends Helmet {
    public CursedHelmet() {
        super("Cursed Helmet", 135, 4);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/cursedhelmet.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
