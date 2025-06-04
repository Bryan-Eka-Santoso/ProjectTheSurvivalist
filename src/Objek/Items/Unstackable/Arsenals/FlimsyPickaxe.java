package Objek.Items.Unstackable.Arsenals;

public class FlimsyPickaxe extends Pickaxe {
    public FlimsyPickaxe() {
        super("Flimsy Pickaxe", 10, 75);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/flimsypickaxe.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
