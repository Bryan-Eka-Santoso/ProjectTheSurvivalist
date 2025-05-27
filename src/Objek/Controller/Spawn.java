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
import Objek.Enemy.Bat;
import Objek.Plant.Plant;

public class Spawn {
    public GamePanel gp;

    public Spawn(GamePanel gp) {
        this.gp = gp;
    }

    public void spawnAnimal(String animalType, int count, ArrayList<Point> usedPositions) {
        int attempts = 0;
        int maxAttempts = 1000; // Prevent infinite loop
        int spawnedCount = 0;
        int validTiles = 18;
        
        int playerSpawnX = 40; 
        int playerSpawnY = 44; 
        int safeZoneRadius = 5;
        while (spawnedCount < count && attempts < maxAttempts) {
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL -5));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW -5));
            Point pos = new Point(x, y);

            if (Math.abs(x - playerSpawnX) < safeZoneRadius && Math.abs(y - playerSpawnY) < safeZoneRadius) {
                attempts++;
                continue;
            }
            // Check if position is already used
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }

            boolean overPlant = false;
            for (Plant plant : gp.plants) {
                int plantX = plant.worldX / gp.TILE_SIZE;
                int plantY = plant.worldY / gp.TILE_SIZE;
                if (Math.abs(x - plantX) < 2 && Math.abs(y - plantY) < 2) { 
                    overPlant = true;
                    break;
                }
            }
            if (overPlant) {
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
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL -8));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW -8));
            Point pos = new Point(x, y);
            System.out.println("Trying to spawn at x:" + x + " y:" + y);
            if (usedPositions.contains(pos)) {
                attempts++;
                continue;
            }
            
            int tileNum = gp.tileM.mapTile[gp.currentMap][x][y];
            boolean isValidTile = false;
            System.out.println("Tile number at position: " + tileNum);
            
            if (tileNum == validTiles) {
                isValidTile = true;
            }
            
            if (!isValidTile) {
                attempts++;
                continue;
            }

            switch (animalType.toLowerCase()) {
                case "bat":
                    gp.monsters.add(new Bat("Bat", x * gp.TILE_SIZE, y * gp.TILE_SIZE, 8, "down", gp));
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
        
            int x = (int)(Math.random() * (gp.MAX_WORLD_COL -8));
            int y = (int)(Math.random() * (gp.MAX_WORLD_ROW -8));
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
