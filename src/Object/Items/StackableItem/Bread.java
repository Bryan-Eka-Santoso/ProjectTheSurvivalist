package Object.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bread extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public Bread() {
        super("Bread", 10, 5);
                try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/Bread.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public Bread(int currentStack) {
        super("Bread", 10, currentStack);
                try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/Bread.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
