package Objek.Items.StackableItem.Foods.RawFoods;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.RawFood;
import Objek.Player.Player;

public class RawMeat extends Food implements RawFood {
    public RawMeat(int currentStack) {
        super("Raw Meat", currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/rawmeat.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        if (rand.nextInt(100) < 20) { // 20% chance of poisoning
            System.out.println("You got food poisoning from the raw meat!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the raw meat safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }
}
