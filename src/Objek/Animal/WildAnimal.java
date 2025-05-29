package Objek.Animal;

import Objek.Controller.GamePanel;
import Objek.Player.Player;

public abstract class WildAnimal extends Animal implements Cloneable {
    
    public WildAnimal(String name, int x, int y, int speed, String direction, GamePanel gp) {
        super(name, x, y, speed, direction, gp);
        this.hp = 100;  
    }

     public WildAnimal clone() {
        try {
            return (WildAnimal) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(); // Seharusnya tidak terjadi
        }
    }
    
    public abstract boolean isPreyNearby(Player player);
    public abstract String chasePlayer(Player player);
}
