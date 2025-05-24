package Objek.Mosnters;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import Objek.Controller.GamePanel;

public class Bat extends Monster {
    private int radius = 300; 

    public Bat(String name, int worldX, int worldY, int speed, String direction, GamePanel gp) {
        super(name, worldX, worldY, speed, direction, gp);
        this.hp = 50;
        this.solidArea = new Rectangle(8, 16, 32, 32); 
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
    }

    @Override
    public void update() {  
       
    }

    @Override
    public void draw(Graphics2D g2d) {

    }

    @Override
    public boolean isPlayerNearby() {
        // TODO Auto-generated method stub
        if (Math.pow((gp.player.worldX - this.worldX), 2) + Math.pow((gp.player.worldY - this.worldY), 2) <= Math.pow(radius, 2) && !collisionOn) {
            return true;
        } else {
            return false;
        }
    }

}
