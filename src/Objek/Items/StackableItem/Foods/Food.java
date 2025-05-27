package Objek.Items.StackableItem.Foods;
import java.util.Random;

import Objek.Items.StackableItem.Stackable;
import Objek.Player.Player;

public class Food extends Stackable {
    Random random = new Random();
    int poisonChance ;// Random chance for poison effect
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
        if(player.hunger >= 100){
            player.hunger = 100; 
        }
        player.health += 5;
        if(player.health >= 100){
            player.health = 100; 
        }
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger); // Display updated stats
    }
}
