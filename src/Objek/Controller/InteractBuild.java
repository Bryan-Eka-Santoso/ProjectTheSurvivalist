package Objek.Controller;

import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Plant.*;
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
        }
        if (building instanceof CraftingTable) {
            gp.gameState = gp.OPEN_CRAFTINGTABLE_STATE;
        }
        if (building instanceof Furnace) {
            gp.gameState = gp.OPEN_SMELTER_STATE;
        }
        if (building instanceof Bed) {
            if (gp.eManager.lighting.dayState == 2) {
                gp.eManager.lighting.dayCounter = 580;
                System.out.println("Selamat pagi");
            } else {
                System.out.println("Masih pagi kerja");
            }
        }
    }
}
