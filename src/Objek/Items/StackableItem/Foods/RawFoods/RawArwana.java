package Objek.Items.StackableItem.Foods.RawFoods;

import java.io.File;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.RawFood;
import Objek.Player.Player;

public class RawArwana extends Food implements RawFood {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public RawArwana(int currentStack) {
        super("Raw Arwana", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/fish/arwana/right1.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        if (rand.nextInt(100) < 50) { // 50% chance of poisoning
            System.out.println("You got food poisoning from the raw arwana!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the raw arwana safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }

}
