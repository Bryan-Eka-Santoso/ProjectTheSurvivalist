package Objek.Items.StackableItem.Foods.Fruits;

public class BlueBerries extends Berries {
    public BlueBerries(int currentStack) {
        super("Blueberries", currentStack);
        this.isCanSell = true;
        this.price = 3;
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/blueberry.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
}
