package Objek.Items.Unstackable;

public class Immortal extends Unstackable {
    public Immortal() {
        super("Immortal");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/immortal.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
