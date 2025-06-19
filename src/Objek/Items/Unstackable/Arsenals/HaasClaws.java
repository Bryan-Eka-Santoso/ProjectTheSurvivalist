package Objek.Items.Unstackable.Arsenals;

public class HaasClaws extends Arsenal {
    
    public HaasClaws() {
        super("Haas Claws", 12, 256);
        this.isLegendaryItem = true;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Equipments/haasclaws.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
