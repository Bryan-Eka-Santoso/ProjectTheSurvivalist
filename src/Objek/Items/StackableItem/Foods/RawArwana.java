package Objek.Items.StackableItem.Foods;

import Objek.Player.Player;

public class RawArwana extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public RawArwana(int currentStack) {
        super("Raw Arwana", 10, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/potato.png")); 
        } catch (java.io.IOException e) {
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
