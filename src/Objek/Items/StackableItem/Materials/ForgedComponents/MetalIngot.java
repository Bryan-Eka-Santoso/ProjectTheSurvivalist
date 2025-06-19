package Objek.Items.StackableItem.Materials.ForgedComponents;

import java.io.IOException;
import javax.imageio.ImageIO;
import Objek.Items.StackableItem.Materials.Material;

public class MetalIngot extends Material {

    public MetalIngot(int currentStack) {
        super("Metal Ingot", currentStack);
        this.isCanSell = true;
        this.price = 25; // Set the price for Metal Ingot
        try {
            this.img = ImageIO.read(getClass().getResource("/res/Items/Material/metalingot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
