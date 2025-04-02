package Object.Items.StackableItem;
import Object.Player.Player;

public class Bread extends Food {
    final int HP_INCREASE = 5; // Health points increase
    final int HUNGER_INCREASE = 10; // Hunger points increase

    public Bread() {
        super("Bread", 10, 5); 
    }

}
