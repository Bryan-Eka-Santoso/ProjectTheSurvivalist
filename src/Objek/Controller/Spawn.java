package Objek.Controller;

import java.util.ArrayList;
import java.awt.Point;
import Objek.Animal.Chicken;
import Objek.Animal.Cow;
import Objek.Animal.Pig;
import Objek.Animal.Sheep;
import Objek.Animal.Wolf;
import Objek.Fish.Arwana;
import Objek.Fish.Belida;
import Objek.Ore.GoldOre;
import Objek.Ore.IronOre;
import Objek.Ore.Rock;
import Objek.Enemy.Bat;
import Objek.Plant.BerryBush;
import Objek.Plant.Bush;
import Objek.Plant.GuavaTree;
import Objek.Plant.MangoTree;
import Objek.Enemy.Golem;
import Objek.Plant.Plant;

public class Spawn {
    public GamePanel gp;

    public Spawn(GamePanel gp) {
        this.gp = gp;
    }
    public void spawnOre(String oreType, int count, ArrayList<Point> usedPositions){
        int attempts = 0;
        int maxAttempts = 1000; // Prevent infinite loop
        int spawnedCount = 0;
        int validTiles = 21; 
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW ));
            Point pos = new Point(x, y);

            // Check if position is already used
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }
            int tileNum = gp.tileM.mapTile[gp.currentMap][x][y];
            boolean isValidTile = false;
            
            if (tileNum == validTiles) {
                isValidTile = true;
            }
            
            if (!isValidTile) {
                attempts++;
                continue;
            }
            switch (oreType.toLowerCase()) {
                case "gold":
                    gp.ores.add(new GoldOre(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "iron":
                    gp.ores.add(new IronOre(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "rock":
                    gp.ores.add(new Rock(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                
            }
           
            // Mark position as used
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;           
        }
    }
    public void spawnPlant(String plantType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000; // Prevent infinite loop
        int spawnedCount = 0;
        int validTiles = 18; // Assuming grass tile number is 18
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL ));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW ));
            Point pos = new Point(x, y);

            // Check if position is already used
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }
            // Check if tile is grass
            int tileNum = gp.tileM.mapTile[gp.currentMap][x][y];
            boolean isValidTile = false;
            
            if (tileNum == validTiles) {
                isValidTile = true;
            }
            
            if (!isValidTile) {
                attempts++;
                continue;
            }
            
            // Spawn the plant based on type
            switch (plantType.toLowerCase()) {
                case "guava":
                    gp.plants.add(new GuavaTree(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "mango":
                    gp.plants.add(new MangoTree(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "berrybush":
                    gp.plants.add(new BerryBush(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "bush":
                    gp.plants.add(new Bush(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
            }
            // Mark position as used
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;           
        }
    }

    public void spawnAnimal(String animalType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000; // Prevent infinite loop
        int spawnedCount = 0;
        int validTiles = 18;
        
        int playerSpawnX = 40; 
        int playerSpawnY = 44; 
        
        while (spawnedCount < count && attempts < maxAttempts) {

            int x = (int)(Math.random() * (gp.MAX_WORLD_COL));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW));
            Point pos = new Point(x, y);

            if (Math.abs(x - playerSpawnX) < 3 && Math.abs(y - playerSpawnY) < 3) {
                attempts++;
                continue;
            }
            Boolean isCloseAnimal = false;
            for (Point usedPos : usedPositions) {
                if (Math.abs(usedPos.x - x) < 3 && Math.abs(usedPos.y - y) < 3) {
                    isCloseAnimal = true;
                    break;
                }
            }
            if (isCloseAnimal) {
                attempts++;
                continue;
            }
            boolean overBuilding = false;
            for (int i = 0; i < gp.buildings.size(); i++) {
                int buildingX = gp.buildings.get(i).worldX / gp.TILE_SIZE;
                int buildingY = gp.buildings.get(i).worldY / gp.TILE_SIZE;
                if (Math.abs(x - buildingX) < 3 && Math.abs(y - buildingY) < 3) { 
                    overBuilding = true;
                    break;
                }
            }
            if (overBuilding) {
                attempts++;
                continue;
            }
            boolean overPlant = false;
            for (Plant plant : gp.plants) {
                int plantX = plant.worldX / gp.TILE_SIZE;
                int plantY = plant.worldY / gp.TILE_SIZE;
                if (Math.abs(x - plantX) < 3 && Math.abs(y - plantY) < 3) { 
                    overPlant = true;
                    break;
                }
            }
            if (overPlant) {
                attempts++;
                continue;
            }
            
            // Check if tile is grass
            boolean nearInvalidTile = false;
            for(int checkX = x-2; checkX <= x+2; checkX++) {
                for(int checkY = y-2; checkY <= y+2; checkY++) {
                    if(checkX >= 0 && checkX < gp.MAX_WORLD_COL && 
                    checkY >= 0 && checkY < gp.MAX_WORLD_ROW) {
                        int tileNum = gp.tileM.mapTile[gp.currentMap][checkX][checkY];
                        if(tileNum != validTiles) {
                            nearInvalidTile = true;
                            break;
                        }
                    }
                }
                if(nearInvalidTile) break;
            }
            if(nearInvalidTile) {
                attempts++;
                continue;
            }
            
            // Spawn the animal based on type
            switch (animalType.toLowerCase()) {
                case "chicken":
                    gp.animals.add(new Chicken("Chicken", x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "cow":
                    gp.animals.add(new Cow("Cow", x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "sheep":
                    gp.animals.add(new Sheep("Sheep", x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "pig":
                    gp.animals.add(new Pig("Pig", x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "wolf":
                    gp.animals.add(new Wolf("Wolf", x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
            }
            
            // Mark position as used
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }

    public void spawnMonster(String animalType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000;
        int spawnedCount = 0;
        int validTiles = 21;
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL ));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW ));
            Point pos = new Point(x, y);
            Boolean isCloseMonster = false;
            for (Point usedPos : usedPositions) {
                if (Math.abs(usedPos.x - x) < 3 && Math.abs(usedPos.y - y) < 3) {
                    isCloseMonster = true;
                    break;
                }
            }
            if (isCloseMonster) {
                attempts++;
                continue;
            }


            boolean nearInvalidTile = false;
            for(int checkX = x-3; checkX <= x+3; checkX++) {
                for(int checkY = y-3; checkY <= y+3; checkY++) {
                    if(checkX >= 0 && checkX < gp.MAX_WORLD_COL && 
                    checkY >= 0 && checkY < gp.MAX_WORLD_ROW) {
                        int tileNum = gp.tileM.mapTile[gp.currentMap][checkX][checkY];
                        if(tileNum != validTiles) {
                            nearInvalidTile = true;
                            break;
                        }
                    }
                }
                if(nearInvalidTile) break;
            }
            if(nearInvalidTile) {
                attempts++;
                continue;
            }

            switch (animalType.toLowerCase()) {
                case "bat":
                    gp.monsters.add(new Bat("Bat", x * gp.TILE_SIZE, y * gp.TILE_SIZE, 8, "down", gp));
                    break;
                case "golem":
                    gp.monsters.add(new Golem("Golem", x * gp.TILE_SIZE, y * gp.TILE_SIZE, 6, "down", gp));
                    break;
            }
            
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
        System.out.println("Final monster count: " + gp.monsters.size());
    }

    public void spawnFish(String animalType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000;
        int spawnedCount = 0;
        int validTiles = 16;
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL ));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW ));
            Point pos = new Point(x, y);
            
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }
            
            int tileNum = gp.tileM.mapTile[gp.currentMap][x][y];
            boolean isValidTile = false;
            
            if (tileNum == validTiles) {
                isValidTile = true;
            }
            
            
            if (!isValidTile) {
                attempts++;
                continue;
            }

            switch (animalType.toLowerCase()) {
                case "arwana":
                    gp.fish.add(new Arwana("arwana", 0, 9, x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "belida":
                    gp.fish.add(new Belida("belida", 0, 7, x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
            }
            
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }
}
