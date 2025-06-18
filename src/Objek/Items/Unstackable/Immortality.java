package Objek.Items.Unstackable;

public class Immortality extends Unstackable {
    
    public Immortality() {
        super("Immortality");
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Equipments/immortality.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
