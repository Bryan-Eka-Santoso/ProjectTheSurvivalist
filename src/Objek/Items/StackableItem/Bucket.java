package Objek.Items.StackableItem;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bucket extends Stackable {
    public String status;
    GamePanel gp;
    BufferedImage empty, water, milk;

    public Bucket(int currentStack, GamePanel gp) {
        super("Bucket", 20, currentStack);
        this.gp = gp;
        this.status = "empty"; // Default status is empty
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.empty = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.water = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/waterbucket.png"));
            this.milk = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/milkbucket.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void fillWater() {
        if (this.status.equals("empty")) {
            this.name = "Water Bucket"; // Change name to Water Bucket
            this.status = "water";
            this.img = this.water;
        } else {
            System.out.println("Bucket is already filled.");
        }
    }

    public void fillMilk() {
        if (this.status.equals("empty")) {
            this.status = "milk";
            this.name = "Milk Bucket"; // Change name to Milk Bucket
            this.img = this.milk;
        } else {
            System.out.println("Bucket is already filled.");
        }
    }

    public void drink() {
        if (this.status.equals("water")) {
            System.out.println("You drank the water from the bucket.");
            this.status = "empty";
            this.name = "Bucket"; // Reset name to Bucket
            this.img = this.empty;
            gp.player.thirst += 5;
            if (gp.player.thirst > 100) {
                gp.player.thirst = 100; // Cap thirst at 100
            }
        } else if (this.status.equals("milk")) {
            System.out.println("You drank the milk from the bucket.");
            this.status = "empty";
            this.name = "Bucket"; // Reset name to Bucket
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
