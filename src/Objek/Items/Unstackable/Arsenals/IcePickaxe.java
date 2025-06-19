package Objek.Items.Unstackable.Arsenals;

public class IcePickaxe extends Pickaxe {

    public IcePickaxe() {
        super("Ice Pickaxe", 18, 150);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Equipments/icepickaxe.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
