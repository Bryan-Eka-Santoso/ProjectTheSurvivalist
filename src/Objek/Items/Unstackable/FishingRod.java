package Objek.Items.Unstackable;

public class FishingRod extends Unstackable {
    public int durability;
    public int strength;
    public int maxStr;
    public int maxDurability = 100; // Maximum durability

    public FishingRod() {
        super("Fishing Rod");
        this.durability = 100; // Default durability
        this.strength = 50; // Default strength
        this.maxStr = 50; // Maximum strength
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Equipments/fishingRod.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

}
