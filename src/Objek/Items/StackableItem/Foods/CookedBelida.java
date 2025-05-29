package Objek.Items.StackableItem.Foods;

public class CookedBelida extends Food {
    public CookedBelida(int currentStack) {
        super("Steak", 5, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Food/cookedBelida.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
