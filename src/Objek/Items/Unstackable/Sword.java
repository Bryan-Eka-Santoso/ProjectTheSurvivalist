package Objek.Items.Unstackable;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sword extends Unstackable {
    private int damage;
    private int durability;
    
    public Sword(String name, int damage, int durability) {
        super(name);
        this.damage = damage;
        this.durability = durability;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/sword.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
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
