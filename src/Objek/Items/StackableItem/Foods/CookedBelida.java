package Objek.Items.StackableItem.Foods;

public class CookedBelida extends Food {
    public CookedBelida(int currentStack) {
        super("Cooked Belida", 5, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/cookedBelida.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
