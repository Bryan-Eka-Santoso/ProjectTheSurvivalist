package Object.Items.UnconsumableItem;

import Object.Items.Item;

public class Unstackable extends Item {
    public Unstackable(String name) {
        super(name, 1, 1);
    }

    public Unstackable(String name, int maxStack) {
        super(name, 1, 0);
    }
}
