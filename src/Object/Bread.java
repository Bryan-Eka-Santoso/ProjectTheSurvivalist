package Object;

public class Bread extends Consumable {
    int healthRestored = 10;

    public Bread() {
        super("Bread");
    }

    public void used() {
        System.out.println("You ate the bread and restored " + healthRestored + " health.");
    }
}
