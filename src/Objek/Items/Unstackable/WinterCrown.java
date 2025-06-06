package Objek.Items.Unstackable;

public class WinterCrown extends Unstackable {
    public WinterCrown() {
        super("Winter Crown");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/wintercrown.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
