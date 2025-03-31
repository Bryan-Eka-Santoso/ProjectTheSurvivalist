package Object.Items.ConsumableItem;

import Object.Items.Item;

public abstract class Consumable extends Item {
    public Consumable(String name, int maxStack) {
        super(name, maxStack);
    }
}