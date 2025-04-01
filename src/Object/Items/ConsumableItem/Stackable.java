package Object.Items.ConsumableItem;
import Object.Items.Item;

public class Stackable extends Item {
    public Stackable(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
    }

    public Stackable(String name, int maxStack) {
        super(name, maxStack, maxStack);
    }
}