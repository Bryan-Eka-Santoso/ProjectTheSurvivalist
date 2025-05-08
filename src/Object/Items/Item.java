package Object.Items;
import java.awt.image.BufferedImage;

public abstract class Item implements Cloneable {
    public String name;
    public int currentStack;
    public int maxStack;
    public BufferedImage img;
    
    public Item(String name, int maxStack, int currentStack) {
        this.name = name;
        this.maxStack = maxStack;
        this.currentStack = currentStack;
    }

    public Item clone() {
        try {
            return (Item) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Seharusnya tidak terjadi
        }
    }

}


