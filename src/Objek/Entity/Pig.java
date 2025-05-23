package Objek.Entity;
import Objek.Items.StackableItem.Foods.Bacon;

import Objek.Player.Player;

public class Pig extends Animal{
    public Pig(String name, int x, int y, String gender) {
        super(name, x, y, gender);
    }
    
    public Pig breeding(Pig pasangan) {
        if(readyBreeding) {
            if(pasangan.isReadyBreeding()) {
                if(!this.getGender().equals(pasangan.getGender())) {
                    System.out.println("Breeding pig with " + pasangan.getName());
                    String babyGender = (Math.random() < 0.5) ? "Male" : "Female";
                    
                    this.readyBreeding = false;
                    pasangan.setReadyBreeding(false);
                    Pig babyPig = new Pig("Baby Pig", this.x, this.y, babyGender);
                    System.out.println("Baby pig is born!");
                    return babyPig;
                } else {
                    System.out.println("Cannot breed pigs of the same gender!");
                    return null;
                }
            }
        }
        System.out.println("Not ready to breed yet.");
        return null;
    }
    public void getItem(Player player) {
        if(readyGetItem) {
            player.inventory.addItems(new Bacon("Bacon", 10, 1));
            setReadyGetItem(false);
            System.out.println("Got milk from " + getName());
        } else {
            System.out.println("This cow cannot be milked right now!");
        }
    }
}

