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
import Objek.Fish.Golden;
import Objek.Ore.CrystalOre;
import Objek.Ore.GemOre;
import Objek.Ore.GoldOre;
import Objek.Ore.MetalOre;
import Objek.Ore.Rock;
import Objek.Enemy.Bat;
import Objek.Enemy.Golem;
import Objek.Plant.Bushes.BerryBush;
import Objek.Plant.Bushes.Bush;
import Objek.Plant.Trees.GuavaTree;
import Objek.Plant.Trees.MangoTree;

public class Spawn {
    public GamePanel gp;

    public Spawn(GamePanel gp) {
        this.gp = gp;
    }
    public Point[] ANIMAL_SPAWN_POINTS = {
        new Point(29,27), new Point(28,71), new Point(73,73),
        new Point(69,29), new Point(35,31), new Point(43,28),
        new Point(50,28), new Point(50,43), new Point(36,52),
        new Point(44,46), new Point(56,57), new Point(49,58),
        new Point(49,68), new Point(55,69), new Point(65,69),
        new Point(68,73), new Point(62,62), new Point(60,53),
        new Point(69,43), new Point(60,37), new Point(64,30),
        new Point(42,34), new Point(50,28), new Point(63,31),
        new Point(36,57), new Point(32,53), new Point(44,41),
        new Point(49,32), new Point(57,29)
    };

    public void spawnOre(String oreType, int count, ArrayList<Point> usedPositions){
        int attempts = 0;
        int maxAttempts = 1000; // Prevent infinite loop
        int spawnedCount = 0;
        int validTiles = 21; 
        int playerSpawnX = 52; 
        int playerSpawnY = 53;
        
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW ));
            Point pos = new Point(x, y);
            if (Math.abs(x - playerSpawnX) < 3 && Math.abs(y - playerSpawnY) < 3) {
                attempts++;
                continue;
            }
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
                case "metal":
                    gp.ores.add(new MetalOre(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "crystal":
                    gp.ores.add(new CrystalOre(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "gem":
                    gp.ores.add(new GemOre(x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
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
        int maxAttempts = 1000;
        int spawnedCount = 0;
        int validTiles = 18;
        
        while(spawnedCount < count && attempts < maxAttempts) {
            int x = (int)(Math.random() * gp.MAX_WORLD_COL);
            int y = (int)(Math.random() * gp.MAX_WORLD_ROW);
            Point pos = new Point(x, y);
            
            // Check if position is already used
            if(usedPositions.contains(pos)) {
                attempts++;
                continue;
            }

            // Check minimum distance from animal spawn points
            boolean tooCloseToSpawnPoint = false;
            for(Point spawnPoint : ANIMAL_SPAWN_POINTS) {
                if(Math.abs(x - spawnPoint.x) <= 2 && Math.abs(y - spawnPoint.y) <= 2) {
                    tooCloseToSpawnPoint = true;
                    break;
                }
            }
            
            if(tooCloseToSpawnPoint) {
                attempts++;
                continue;
            }
            if(Math.abs(x - gp.SpawnX) < 3 && Math.abs(y - gp.SpawnY) < 3) {
                attempts++;
                continue;
            }
            // Check tile validity
            int tileNum = gp.tileM.mapTile[gp.currentMap][x][y];
            if(tileNum != validTiles) {
                attempts++;
                continue;
            }

            // Spawn plant based on type
            switch(plantType.toLowerCase()) {
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
            
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }

    public void spawnAnimal(String animalType, int count, ArrayList<Point> usedPositions) {
        int spawnedCount = 0;
        
        // Create list of available spawn points
        ArrayList<Point> availablePoints = new ArrayList<>();
        for(Point p : ANIMAL_SPAWN_POINTS) {
            if(!usedPositions.contains(p)) {
                availablePoints.add(p);
            }
        }

        // Randomly select from available points until count is reached
        while(spawnedCount < count && !availablePoints.isEmpty()) {
            int index = (int)(Math.random() * availablePoints.size());
            Point spawnPoint = availablePoints.get(index);
            
            switch(animalType.toLowerCase()) {
                case "chicken":
                    gp.animals.add(new Chicken("Chicken", spawnPoint.x * gp.TILE_SIZE, spawnPoint.y * gp.TILE_SIZE, gp));
                    break;
                case "cow":
                    gp.animals.add(new Cow("Cow", spawnPoint.x * gp.TILE_SIZE, spawnPoint.y * gp.TILE_SIZE, gp));
                    break;
                case "sheep":
                    gp.animals.add(new Sheep("Sheep", spawnPoint.x * gp.TILE_SIZE, spawnPoint.y * gp.TILE_SIZE, gp));
                    break;
                case "pig":
                    gp.animals.add(new Pig("Pig", spawnPoint.x * gp.TILE_SIZE, spawnPoint.y * gp.TILE_SIZE, gp));
                    break;
                case "wolf":
                    gp.animals.add(new Wolf("Wolf", spawnPoint.x * gp.TILE_SIZE, spawnPoint.y * gp.TILE_SIZE, gp));
                    
                    break;
            }
            
            usedPositions.add(spawnPoint);
            availablePoints.remove(index);
            spawnedCount++;
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
                    gp.monsters.add(new Bat("Bat", x * gp.TILE_SIZE, y * gp.TILE_SIZE, "down", gp));
                    break;
                case "golem":
                    gp.monsters.add(new Golem("Golem", x * gp.TILE_SIZE, y * gp.TILE_SIZE, "down", gp));
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
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW));
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
                    gp.fish.add(new Arwana("arwana", 0, 15, x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "belida":
                    gp.fish.add(new Belida("belida", 0, 20, x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
                case "golden":
                    gp.fish.add(new Golden("golden", 0, 30, x * gp.TILE_SIZE, y * gp.TILE_SIZE, gp));
                    break;
            }
            
            usedPositions.add(pos);
            spawnedCount++;
            attempts = 0;
        }
    }
}
