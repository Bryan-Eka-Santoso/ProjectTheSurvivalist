package Objek.Items.Unstackable.Arsenals;

public class LightweightPickaxe extends Pickaxe {

    public LightweightPickaxe() {
        super("Lightweight Pickaxe", 14, 100);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Equipments/lightweightpickaxe.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
