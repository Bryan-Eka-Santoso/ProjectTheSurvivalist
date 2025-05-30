package Objek.Items.StackableItem.Foods.RawFoods;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Player.Player;

public class RawPork extends Food {
    public RawPork(int currentStack) {
        super("Raw Pork", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/rawpork.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }
    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        if (rand.nextInt(100) < 50) { // 50% chance of poisoning
            System.out.println("You got food poisoning from the raw pork!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the raw pork safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }
}
