package Objek.Items.StackableItem.Materials;

public class ToolHandle extends Material {
    public ToolHandle(int currentStack) {
        super("Tool Handle", 20, currentStack);
        try {
            this.img = javax.imageio.ImageIO.read(new java.io.File("ProjectTheSurvivalist/res/Items/Material/toolhandle.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
