package Objek.Items.Unstackable.Arsenals;

public class LightweightPickaxe extends Pickaxe {
    public LightweightPickaxe() {
        super("Lightweight Pickaxe", 14, 80);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/lightweightpickaxe.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
