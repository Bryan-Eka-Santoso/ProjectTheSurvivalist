package Objek.Items.StackableItem.Foods;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Player.Player;

public class RawBelida extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public RawBelida(int currentStack) {
        super("Raw Belida", 10, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/fish/belida/right1.png")); 
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
