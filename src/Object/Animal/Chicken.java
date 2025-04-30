package Object.Animal;
import Object.Player.Player;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import Object.GamePanel;
import Object.Items.StackableItem.Egg;

public class Chicken extends TameAnimal {
   
    public Chicken(String name, int x, int y, GamePanel gp) {
        super(name, x, y, 15, "down", gp);
        this.solidArea = new Rectangle(12, 24, 24, 20);
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        readyBreeding = true;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/up2.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/down2.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/left2.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/chicken/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Chicken breeding(Chicken pasangan, GamePanel gp) {
        // Check if both chickens are ready to
        if (readyBreeding) {
            if (pasangan.isReadyBreeding()) {
                System.out.println("Breeding chicken with " + pasangan.getName());
                Chicken babyChicken = new Chicken("Baby Chicken", this.x, this.y, gp);
                this.readyBreeding = false;
                pasangan.setReadyBreeding(false);
                return babyChicken;
            } else {
                System.out.println("Partner is not ready to breed.");
                return null;
            }
        } else {
            System.out.println("Not ready to breed yet.");
            return null;
        }
    }
    
    public void getItem(Player player) {
        player.inventory.addItems(new Egg("Egg", 10, 1));
    }
    
}
