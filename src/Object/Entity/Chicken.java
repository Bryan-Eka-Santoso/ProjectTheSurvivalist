package Object.Entity;
import Object.Player.Player;

import Object.Items.StackableItem.Egg;

public class Chicken extends Animal {
   
   
    public Chicken(String name, int x, int y) {
        super(name, x, y);
        readyBreeding = true;
       
    }

    public Chicken breeding(Chicken pasangan) {
       if(readyBreeding){
            if(pasangan.isReadyBreeding()){
                System.out.println("Breeding chicken with " + pasangan.getName());
                Chicken babyChicken = new Chicken("Baby Chicken", this.x, this.y);
               
                this.readyBreeding = false;
                pasangan.setReadyBreeding(false);
                return babyChicken;
            }else{
                System.out.println("Partner is not ready to breed.");
                return null;
            }
       }else{
              System.out.println("Not ready to breed yet.");
              return null;
       }
       
    }
    
    public void getItem(Player player) {
        player.inventory.addItems(new Egg("Egg", 10, 1));
    }
    
   
}
