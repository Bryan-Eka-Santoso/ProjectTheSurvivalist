package Objek.Items.StackableItem.Foods.RawFoods;

import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Poisonous;
import Objek.Player.Player;

public class RawMutton extends Food implements Poisonous {

    public RawMutton(int currentStack) {
        super("Raw Mutton", currentStack);
            try {
                this.img = ImageIO.read(getClass().getResource("/res/Items/Foods/rawmutton.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
    
    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        if (rand.nextInt(100) < 30) { // 30% chance of poisoning
            System.out.println("You got food poisoning from the raw mutton!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the raw mutton safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }
}
