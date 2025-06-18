package Objek.Items.StackableItem.Foods.Other;

import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Foods.Food;

public class Bread extends Food {

    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public Bread(int currentStack) {
        super("Bread", currentStack);
        this.isCanSell = true;
        this.price = 15; // Set the price for Bread
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Foods/Bread.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
}
