package Objek.Items.StackableItem.Foods.Fruits;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Hydrating;
import Objek.Player.Player;

public class Mango extends Food implements Hydrating {
    public Mango(int currentStack) {
        super("Mango", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/mango.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getHydrationValue() {
        return 12;
    }

    @Override
    public void eat(Player player) {
        super.eat(player);
        player.thirst += getHydrationValue();

        if (player.thirst > 100) {
            player.thirst = 100; // Cap thirst at 100
        }
        System.out.println("You ate a coconut. Hydration increased by " + getHydrationValue() + ".");
    }
}
