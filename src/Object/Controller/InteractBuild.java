package Object.Controller;

import Object.Items.Buildings.*;
public class InteractBuild {
    GamePanel gp;
    Sound sound = new Sound();

    public InteractBuild(GamePanel gp) {
        this.gp = gp;
    }

    public void interact() {
        Buildings building = (Buildings) gp.buildings.get(gp.player.buildingIndex);
        if (building instanceof Chest) {
            ((Chest) building).openChest(gp.ui.g2);
        }   
    }
}
