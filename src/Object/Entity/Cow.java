package Object.Entity;
import Object.Player.Player;
import Object.Items.StackableItem.Milk;

public class Cow extends Animal {
    public Cow(String name, int x, int y, String gender) {
        super(name, x, y, gender);
    }
    
    public void getItem(Player player) {
        if(readyGetItem) {
            player.inventory.addItems(new Milk("Milk", 10, 1));
            setReadyGetItem(false);
            System.out.println("Got milk from " + getName());
        } else {
            System.out.println("This cow cannot be milked right now!");
        }
    }
    
    public Cow breeding(Cow pasangan) {
        if(readyBreeding) {
            if(pasangan.isReadyBreeding()) {
                if(!this.getGender().equals(pasangan.getGender())) {
                    System.out.println("Breeding cow with " + pasangan.getName());
                    String babyGender = (Math.random() < 0.5) ? "Male" : "Female";
                    
                    this.readyBreeding = false;
                    pasangan.setReadyBreeding(false);
                    Cow babyCow = new Cow("Baby Cow", this.x, this.y, babyGender);
                    System.out.println("Baby cow is born!");
                    return babyCow;
                }
            }
        }
        return null;
    }
}