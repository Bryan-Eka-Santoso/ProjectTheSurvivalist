package Object.Items;

public class Item implements Cloneable {
    public String name;
    public int currentStack;
    public int maxStack;
    
    public Item(String name, int maxStack, int currentStack) {
        this.name = name;
        this.maxStack = maxStack;
        this.currentStack = currentStack;
    }
}


