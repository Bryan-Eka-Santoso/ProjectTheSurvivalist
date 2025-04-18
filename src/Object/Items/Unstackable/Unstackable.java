package Object.Items.Unstackable;

import Object.Items.Item;

public abstract class Unstackable extends Item {
    public Unstackable(String name) {
        super(name, 1, 1);
    }

    public Unstackable(String name, int maxStack) {
        super(name, 1, 1);
    }
}
