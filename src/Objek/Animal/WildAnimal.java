package Objek.Animal;

import Objek.Controller.GamePanel;
import Objek.Player.Player;

public abstract class WildAnimal extends Animal {
    
    public WildAnimal(String name, int x, int y, int speed, String direction, GamePanel gp) {
        super(name, x, y, speed, direction, gp);
        this.hp = 100;  
    }
    
    public abstract boolean isPreyNearby(Player player);
    public abstract void chasePlayer(Player player);
}
