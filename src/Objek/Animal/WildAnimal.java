package Objek.Animal;

import Objek.Controller.GamePanel;

public abstract class WildAnimal extends Animal {
    
    public WildAnimal(String name, int x, int y, int speed, String direction, GamePanel gp) {
        super(name, x, y, speed, direction, gp);
        this.hp = 100;  
    }
}
