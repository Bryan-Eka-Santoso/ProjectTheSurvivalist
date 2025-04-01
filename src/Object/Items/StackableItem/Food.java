package Object.Items.ConsumableItem;

public class Food extends Stackable {
    
    public Food(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
    }

    public Food(String name, int maxStack) {
        super(name, maxStack);
    }
}
