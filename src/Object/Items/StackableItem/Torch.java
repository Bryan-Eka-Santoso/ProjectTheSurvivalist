package Object.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Object.Controller.GamePanel;

public class Torch extends Stackable {
    GamePanel gp;
    public int lightRadius;


    public Torch(GamePanel gp) {
        super("Torch", 8, 1);
        this.gp = gp;
        this.lightRadius = 350;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/lightItems/torch1.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

}
