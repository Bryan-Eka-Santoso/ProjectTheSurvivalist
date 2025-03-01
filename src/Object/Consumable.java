package Object;

public abstract class Consumable extends Item {
    public Consumable(String name) {
        super(name);
    }

    abstract void used();
}