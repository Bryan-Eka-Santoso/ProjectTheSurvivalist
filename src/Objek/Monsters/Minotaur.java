package Objek.Monsters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import Objek.Controller.GamePanel;

public class Minotaur extends Monster {

    public Minotaur(String name, int worldX, int worldY, int speed, String direction, GamePanel gp) {
        super(name, worldX, worldY, speed, direction, gp);
        this.hp = 200; // Set Minotaur's HP
        this.solidArea = new Rectangle(8, 16, 32, 32); // Define the solid area for collision detection
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
    }

    @Override
    public void update() {
        // Update logic for Minotaur
    }

    @Override
    public void draw(Graphics2D g2d) {
        // Draw logic for Minotaur
    }

    @Override
    public boolean isPlayerNearby() {
        // Logic to check if player is nearby
        return false; // Placeholder return value
    }
    
}
