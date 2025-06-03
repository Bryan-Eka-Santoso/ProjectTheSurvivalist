package Objek.Items.StackableItem.Foods.RawFoods;

import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Poisonous;
import Objek.Player.Player;

public class Potato extends Food implements Poisonous {
    public Potato(int currentStack) {
        super("Potato", currentStack);
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

        if (rand.nextInt(100) < 20) { // 20% chance of poisoning
            System.out.println("You got food poisoning from the potato!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the potato safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }
    
}
