package Objek.Items.Unstackable;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bucket extends Unstackable {
    public String status;
    GamePanel gp;
    BufferedImage empty, water, milk;

    public Bucket(int currentStack, GamePanel gp) {
        super("Bucket");
        this.gp = gp;
        this.status = "empty"; // Default status is empty
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.empty = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.water = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/water.png"));
            this.milk = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/milk.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void fillWater() {
        int newX = gp.player.worldX, newY = gp.player.worldY; 
        switch (gp.player.direction) {
            case "up":
                newY = gp.player.worldY - gp.TILE_SIZE;  
                break;
            case "down":
                newY = gp.player.worldY + gp.TILE_SIZE;  
                break;
            case "left":
                newX = gp.player.worldX - gp.TILE_SIZE;  
                break;
            case "right":
                newX = gp.player.worldX+ gp.TILE_SIZE;  
                break;
        }
        int tileNum = gp.tileM.mapTile[gp.currentMap][newX/gp.TILE_SIZE][newY/gp.TILE_SIZE];
        if (tileNum == 16) {
            if (this.status.equals("empty")) {
                this.status = "water";
                this.img = this.water;
            } else {
                System.out.println("Bucket is already filled.");
            }
        }
    }

    public void fillMilk() {
        if (this.status.equals("empty")) {
            this.status = "milk";
            this.img = this.milk;
        } else {
            System.out.println("Bucket is already filled.");
        }
    }

    public void drink() {
        if (this.status.equals("water")) {
            System.out.println("You drink the water from the bucket.");
            this.status = "empty";
            this.img = this.empty;
            gp.player.thirst += 5;
            if (gp.player.thirst > 100) {
                gp.player.thirst = 100; // Cap thirst at 100
            }
        } else if (this.status.equals("milk")) {
            System.out.println("You drink the milk from the bucket.");
            this.status = "empty";
            this.img = this.empty;
            gp.player.thirst += 10;
            if (gp.player.thirst > 100) {
                gp.player.thirst = 100; // Cap thirst at 100
            }
        } else {
            System.out.println("The bucket is empty.");
        }
    }
}
