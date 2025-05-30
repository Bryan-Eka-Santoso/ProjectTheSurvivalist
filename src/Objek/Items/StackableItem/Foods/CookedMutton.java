package Objek.Items.StackableItem.Foods;

public class CookedMutton extends Food {
    public CookedMutton(int currentStack) {
        super("Cooked Mutton", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/cookedmutton.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
