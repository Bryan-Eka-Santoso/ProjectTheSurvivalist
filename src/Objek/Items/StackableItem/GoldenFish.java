package Objek.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GoldenFish extends Stackable {
    public GoldenFish(int currentStack) {
        super("Golden Fish", currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/fish/golden/right1.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
