package Objek.Items.StackableItem.Foods;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RawPork extends Food{
    public RawPork(int currentStack) {
        super("Raw Pork", 10, currentStack);
            try {
                this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Foods/null.png")); 
            } catch (IOException e) {
                e.printStackTrace();
            } 
    }
}
