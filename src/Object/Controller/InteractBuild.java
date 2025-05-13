package Object.Controller;

import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.Unstackable.Buildings.Chest;

public class InteractBuild {
    GamePanel gp;
    Sound sound = new Sound();

    public InteractBuild(GamePanel gp) {
        this.gp = gp;
    }

    public void interact() {
        Buildings building = (Buildings) gp.buildings.get(gp.player.buildingIndex);
        if (building instanceof Chest) {
            ((Chest) building).openChest();
        }   
    }
}
