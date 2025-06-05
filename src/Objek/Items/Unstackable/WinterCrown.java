package Objek.Items.Unstackable;

public class WinterCrown extends Unstackable {
    public boolean isActive = false;
    
    public WinterCrown() {
        super("Winter Crown");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/sword.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
