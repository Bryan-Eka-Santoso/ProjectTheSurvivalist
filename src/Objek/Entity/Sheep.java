package Objek.Entity;

import Objek.Items.StackableItem.Materials.Wool;
import Objek.Player.Player;

public class Sheep extends Animal {
    public Sheep(String name, int x, int y, String gender) {
        super(name, x, y, gender);
    }
    
    public void getItem(Player player) {
        if(readyGetItem) {
            player.inventory.addItems(new Wool(1));
            setReadyGetItem(false);
            System.out.println("Got wool from " + getName());
        } else {
            System.out.println("This sheep cannot be sheared right now!");
        }
    }
    
    public Sheep breeding(Sheep pasangan) {
        if(readyBreeding) {
            if(pasangan.isReadyBreeding()) {
                if(!this.getGender().equals(pasangan.getGender())) {
                    System.out.println("Breeding sheep with " + pasangan.getName());
                    String babyGender = (Math.random() < 0.5) ? "Male" : "Female";
                    
                    this.readyBreeding = false;
                    pasangan.setReadyBreeding(false);
                    Sheep babySheep = new Sheep("Baby Sheep", this.x, this.y, babyGender);
                    System.out.println("Baby sheep is born!");
                    return babySheep;
                }
            }
        }
        return null;
    }
}