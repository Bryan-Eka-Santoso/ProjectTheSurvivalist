package Objek.Items.Unstackable;

public class FishingRod extends Unstackable {
    public FishingRod() {
        super("Fishing Rod");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/fishingrod.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
