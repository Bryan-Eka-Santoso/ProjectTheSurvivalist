package Objek.Items.Unstackable;

public class WinterCrown extends Unstackable {
    
    public WinterCrown() {
        super("Winter Crown");
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Equipments/wintercrown.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
