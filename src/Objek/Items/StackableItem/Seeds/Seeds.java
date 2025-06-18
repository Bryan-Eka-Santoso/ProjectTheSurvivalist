package Objek.Items.StackableItem.Seeds;

import Objek.Items.StackableItem.Stackable;

public class Seeds extends Stackable {

    public Seeds(String name, int currentStack) {
        super(name, 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Seeds/" + name.toLowerCase().replace(" ", "") + ".png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public Seeds(int currentStack) {
        super("Seeds", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(getClass().getResource("/res/Items/Seeds/seeds.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
