package Object.Items.StackableItem;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wood extends Material {
    public Wood(String name, int maxStack, int currentStack) {
        super(name, maxStack, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wood-log.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Wood(int currentStack) {
        super("Wood", 20, currentStack);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Material/wood-log.png")); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
