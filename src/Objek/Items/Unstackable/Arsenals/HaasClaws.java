package Objek.Items.Unstackable.Arsenals;

public class HaasClaws extends Arsenal {
    public HaasClaws() {
        super("Haas Claws", 12, 200);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/haasclaws.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
