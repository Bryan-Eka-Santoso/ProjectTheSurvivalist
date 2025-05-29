package Objek.Items.StackableItem;

public class Bucket extends Stackable {
    public Bucket(int currentStack) {
        super("Bucket", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/bucket.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
