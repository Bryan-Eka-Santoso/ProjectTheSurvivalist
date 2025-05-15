package Objek.Controller;

import Objek.Items.Buildings.*;
public class InteractBuild {
    GamePanel gp;
    Sound sound = new Sound();

    public InteractBuild(GamePanel gp) {
        this.gp = gp;
    }

    public void interact() {
        Buildings building = (Buildings) gp.buildings.get(gp.player.buildingIndex);
        if (building instanceof Chest) {
            gp.gameState = gp.OPEN_CHEST_STATE;
            System.out.println("Interacting with chest: " + building.name);
        }   
    }
}
