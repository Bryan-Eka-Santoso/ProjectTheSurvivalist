package Object.Items;

public class Item {
    public String name;
    public int currentStack = 0;
    public int maxStack;
    
    public Item(String name, int maxStack) {
        this.name = name;
        this.maxStack = maxStack;
    }

    public Item(int currentStack) {
        this.currentStack = currentStack;
    }
    
}


