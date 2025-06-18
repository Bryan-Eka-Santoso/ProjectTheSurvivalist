package Objek.Items.StackableItem.Foods.Fruits;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Hydrating;
import Objek.Player.Player;

public class Coconut extends Food implements Hydrating {

    public Coconut(int currentStack) {
        super("Coconut", currentStack);
        this.isCanSell = true;
        this.price = 5; // Set the price for Coconut
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Foods/coconut.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getHydrationValue() {
        return 10;
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
