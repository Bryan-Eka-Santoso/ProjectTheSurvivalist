package Objek.Items.StackableItem.Foods;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawChicken extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public RawChicken(int currentStack) {
        super("Raw Chicken", 10, currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/Chicken.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
}
