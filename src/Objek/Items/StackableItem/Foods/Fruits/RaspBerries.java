package Objek.Items.StackableItem.Foods.Fruits;

public class RaspBerries extends Berries {
    
    public RaspBerries(int currentStack) {
        super("Raspberries", currentStack);
        this.isCanSell = true;
        this.price = 3;
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/raspberry.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }    
}
