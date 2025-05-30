package Objek.Items.StackableItem.Foods;

public class CookedChicken extends Food {
    public CookedChicken(int currentStack) {
        super("Cooked Chicken", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/chickendrum.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
