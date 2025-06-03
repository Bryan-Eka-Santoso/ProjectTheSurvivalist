package Objek.Items.StackableItem.Foods.RawFoods;

import java.util.Random;
import Objek.Items.StackableItem.Foods.Berries;
import Objek.Items.StackableItem.Foods.Poisonous;
import Objek.Player.Player;

public class Blackberries extends Berries implements Poisonous {

    Random rand = new Random();

    public Blackberries(int currentStack) {
        super("Blackberries", currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Foods/blackberry.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void eat(Player player) {
        // Implement the logic for eating food here
        System.out.println("Eating " + name + "..."); // Eating action

        if (rand.nextInt(100) < 20) { // 20% chance of poisoning
            System.out.println("You got food poisoning from the blackberry!");
            player.setPoisoned();
        } else {
            System.out.println("You ate the blackberry safely.");
        }
        player.hunger += 5;

        if (player.hunger >= 100){
            player.hunger = 100; 
        }
        
        System.out.println("Health: " + player.health + ", Hunger: " + player.hunger);
    }
}
