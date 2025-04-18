package Object.Items.StackableItem;

public class Material extends Stackable {
    public Material(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
    }

    public Material(String name, int maxStack) {
        super(name, maxStack);
    }
}
