package Objek.Items.Unstackable;

public class WateringCan extends Unstackable {
    public int durability;
    public int maxDurability = 100; // Maximum durability
    
    public WateringCan() {
        super("Watering Can");
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/wateringcan.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
