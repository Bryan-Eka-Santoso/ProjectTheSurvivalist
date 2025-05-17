package Objek.Items.StackableItem.Foods;
import Objek.Items.StackableItem.Stackable;
import Objek.Player.Player;

public class Food extends Stackable {
    
    public Food(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
    }

    public Food(String name, int maxStack) {
        super(name, maxStack);
    }

    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action
        player.hunger += 10;
        player.health += 5;  
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger); // Display updated stats
    }
}
