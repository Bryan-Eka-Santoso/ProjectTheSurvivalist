package Objek.Items.Unstackable;

public class Immortality extends Unstackable {
    public Immortality() {
        super("Immortality");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/immortal.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
