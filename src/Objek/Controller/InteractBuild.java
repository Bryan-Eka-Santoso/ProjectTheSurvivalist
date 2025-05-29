package Objek.Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Point;

import javax.imageio.ImageIO;

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
                gp.player.isSleeping = true;
                gp.SpawnX = gp.player.worldX / gp.TILE_SIZE;
                gp.SpawnY = gp.player.worldY / gp.TILE_SIZE;
                try {
                    gp.buildings.get(gp.player.buildingIndex).img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/SleepingPlayer.png"));
                    gp.player.isSleeping = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Selamat pagi");
            } else {
                System.out.println("Masih pagi kerja");
            }
        }
        if(building instanceof Cave) {
            ArrayList<Point> usedPositions = new ArrayList<>();
            gp.tileM.loadMap("ProjectTheSurvivalist/res/world/cave.txt", 2);
            gp.currentMap = 2;
            gp.animals.clear();
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 24 * gp.TILE_SIZE;
            gp.player.worldX = 23 * gp.TILE_SIZE;
            gp.isCave = !gp.isCave;
            gp.eManager.lighting.setLightSource(); 
            gp.player.buildingIndex = -1;
            gp.sp.spawnMonster("bat", 10, usedPositions);
            gp.sp.spawnMonster("golem", 10, usedPositions);
            for (int i = 0; i < gp.monsters.size(); i++) {
                System.out.println("Monster " + i + ": " + gp.monsters.get(i)+ " at (" + 
                                   gp.monsters.get(i).worldX/gp.TILE_SIZE + ", " + 
                                   gp.monsters.get(i).worldY/gp.TILE_SIZE + ")");
            }
        }
        if(building instanceof Shop) {
            gp.tileM.loadMap("ProjectTheSurvivalist/res/world/shop.txt", 3);
            gp.currentMap = 3;
            gp.animals.clear();
            gp.player.getPlayerImg();
            gp.tileM.getTileImage();
            gp.player.worldY = 53 * gp.TILE_SIZE;
            gp.player.worldX = 52 * gp.TILE_SIZE;
        }
    }
}
