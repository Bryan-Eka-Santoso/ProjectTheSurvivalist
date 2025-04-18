package Object.Items.Unstackable;

public class Sword extends Unstackable {
    private int damage;
    private int durability;

    public Sword(String name, int damage, int durability) {
        super(name);
        this.damage = damage;
        this.durability = durability;
    }

    public Sword(String name) {
        super(name);
        this.damage = 10; // Default damage
        this.durability = 100; // Default durability
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
    
}
