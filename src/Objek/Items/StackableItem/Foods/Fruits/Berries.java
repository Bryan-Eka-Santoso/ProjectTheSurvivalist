package Objek.Items.StackableItem.Foods.Fruits;// pac

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Hydrating;
import Objek.Items.StackableItem.Foods.RawFoods.Blackberries;
import Objek.Player.Player;

public class Berries extends Food implements Hydrating {

    public Berries(String name, int currentStack) {
        super(name, currentStack);
    }
    
    public static Berries createBerries(int type, int currentStack) {
        switch (type) {
            case 1:
                return new Blackberries(currentStack);
            case 2:
                return new BlueBerries(currentStack);
            case 3:
                return new RaspBerries(currentStack);
            default:
                throw new IllegalArgumentException("Unknown berry type: " + type);
        }
    }   
    
    @Override
    public int getHydrationValue() {
        return 5;
    }

    @Override
    public void eat(Player player) {
        super.eat(player);
        player.thirst += getHydrationValue();

        if (player.thirst > 100) {
            player.thirst = 100; // Cap thirst at 100
        }
        System.out.println("You ate some berries. Hydration increased by " + getHydrationValue() + ".");
    }
}
