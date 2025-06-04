package Objek.Items.StackableItem;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bucket extends Stackable {
    public String status;
    GamePanel gp;
    BufferedImage empty, water, milk, cleansedWater;

    public Bucket(int currentStack, GamePanel gp) {
        super("Bucket", 20, currentStack);
        this.gp = gp;
        this.status = "empty"; // Default status is empty
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.empty = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Bucket/empty.png"));
            this.water = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/waterbucket.png"));
            this.milk = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/milkbucket.png"));
            this.cleansedWater = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Equipments/cleansedwaterbucket.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public void fillWater() {
        if (this.status.equals("empty")) {
            if (currentStack == 1){
                this.name = "Water Bucket"; // Change name to Water Bucket
                this.status = "water";
                this.img = this.water;
            } else {
                Bucket newBucket = new Bucket(1, gp); // Create a new bucket with stack 1
                newBucket.fillWater();

                gp.player.inventory.addItems(newBucket);
                gp.player.inventory.removeItem(this, 1); // Remove this bucket from inventory
            }
        } else {
            System.out.println("Bucket is already filled.");
        }
    }

    public void fillMilk() {
        if (this.status.equals("empty")) {
            if (currentStack == 1){
                this.name = "Milk Bucket"; // Change name to Milk Bucket
                this.status = "milk";
                this.img = this.milk;
            } else {
                Bucket newBucket = new Bucket(1, gp); // Create a new bucket with stack 1
                newBucket.fillMilk();

                gp.player.inventory.addItems(newBucket);
                gp.player.inventory.removeItem(this, 1); // Remove` this bucket from inventory
            }
        } else {
            System.out.println("Bucket is already filled.");
        }
    }

    public void drink() {
        if (this.status.equals("cleansed")) {
            System.out.println("You drank the cleansed water from the bucket.");
            gp.player.inventory.removeItem(this, 1); // Remove this bucket from inventory
            gp.player.inventory.addItems(new Bucket(1, gp)); // Add a new bucket with reduced stack
            gp.player.thirst += 40;
            if (gp.player.thirst > 100) {
                gp.player.thirst = 100; // Cap thirst at 100
            }
        } else if (this.status.equals("milk")) {
            System.out.println("You drank the milk from the bucket.");
            gp.player.inventory.removeItem(this, 1); // Remove this bucket from inventory
            gp.player.inventory.addItems(new Bucket(1, gp)); // Add a new bucket with reduced stack
            gp.player.thirst += 25;
            if (gp.player.thirst > 100) {
                gp.player.thirst = 100; // Cap thirst at 100
            }
            if (gp.player.isPoisoned()){
                gp.player.curePoison(); // Cure poison if player is poisoned
                System.out.println("The milk cured your poison.");
            }
        } else {
            System.out.println("The bucket is undrinkable.");
        }
    }

    public static Bucket getCleansedWaterBucket(GamePanel gp) {
        Bucket bucket = new Bucket(1, gp);
        bucket.status = "cleansed";
        bucket.name = "Cleansed Water Bucket";
        bucket.img = bucket.cleansedWater;

        return bucket;
    }
}
