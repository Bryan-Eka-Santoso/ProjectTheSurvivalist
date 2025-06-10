package Objek.Items.StackableItem.Foods.Fruits;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Hydrating;
import Objek.Player.Player;

public class Guava extends Food implements Hydrating {
    public Guava(int currentStack) {
        super("Guava", currentStack);
        this.isCanSell = true;
        this.price = 5; // Set the price for Guava
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/guava.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getHydrationValue() {
        return 8;
    }

    @Override
    public void eat(Player player) {
        super.eat(player);
        player.thirst += getHydrationValue();

        if (player.thirst > 100) {
            player.thirst = 100; // Cap thirst at 100
        }
        System.out.println("You ate a guava. Hydration increased by " + getHydrationValue() + ".");
    }
    
}
