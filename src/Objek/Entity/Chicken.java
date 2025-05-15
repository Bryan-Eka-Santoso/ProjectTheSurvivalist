package Objek.Entity;
import Objek.Items.StackableItem.Egg;
import Objek.Player.Player;

public class Chicken extends Animal { 
    public Chicken(String name, int x, int y,String gender) {
        super(name, x, y,gender);
        
    }
    
    public Chicken breeding(Chicken pasangan) {
       if(readyBreeding){
            if(pasangan.isReadyBreeding()){
                if(!this.getGender().equals(pasangan.getGender())){
                    System.out.println("Breeding chicken with " + pasangan.getName());
                    String babyGender = (Math.random() < 0.5) ? "Male" : "Female";
                    
                    this.readyBreeding = false;
                    pasangan.setReadyBreeding(false);
                    Chicken babyChicken = new Chicken("Baby Chicken", this.x, this.y,babyGender);
                    
                    System.out.println("Baby chicken is born!");
                    return babyChicken;
                } else {
                    System.out.println("Cannot breed chickens of the same gender!");
                    return null;
                }    
            } else {
                System.out.println("Partner is not ready to breed.");
                return null;
            }
       } else {
              System.out.println("Not ready to breed yet.");
              return null;
       }
    }
    
    public void getItem(Player player) {
        if(readyGetItem){
            player.inventory.addItems(new Egg("Egg", 10, 1));
            setReadyGetItem(false);
            System.out.println("Got an egg from " + getName());
        } else {
            System.out.println("This chicken cannot lay eggs right now!");  
        }  
    }
}
