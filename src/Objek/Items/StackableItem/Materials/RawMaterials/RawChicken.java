package Objek.Items.StackableItem.Materials.RawMaterials;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Player.Player;

public class RawChicken extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public RawChicken(int currentStack) {
        super("Raw Chicken", 10, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/Chicken.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        System.out.println("You've been poisoned!");
        player.setPoisoned();
        player.hunger += 10;
        if(player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger); // Display updated stats
    }
}
