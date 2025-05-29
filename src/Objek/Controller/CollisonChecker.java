package Objek.Controller;

import java.awt.Rectangle;
import Objek.Animal.Animal;
import Objek.Animal.Wolf;
import Objek.Enemy.Monster;
import Objek.Fish.Fish;
import Objek.Plant.Bush;
import Objek.Plant.Tree;
import Objek.Player.Player;

public class CollisonChecker {
    GamePanel gp;

    public CollisonChecker (GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Player player) {
        int entityLeftX = player.worldX + player.solidArea.x;
        int entityRightX = player.worldX + player.solidArea.x + player.solidArea.width;
        int entityTopY = player.worldY + player.solidArea.y;
        int entityBottomY = player.worldY + player.solidArea.y + player.solidArea.height;

        int playerLeftCol = entityLeftX / gp.TILE_SIZE;
        int playerRightCol = entityRightX / gp.TILE_SIZE;
        int playerTopRow = entityTopY / gp.TILE_SIZE;
        int playerBottomRow = entityBottomY / gp.TILE_SIZE;

        int tileNum1 = 0, tileNum2 = 0;

        switch (player.direction) {
            case "up":
                playerTopRow = (entityTopY - player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[gp.currentMap][playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[gp.currentMap][playerRightCol][playerTopRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "down":
                playerBottomRow = (entityBottomY + player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[gp.currentMap][playerLeftCol][playerBottomRow];
                tileNum2 = gp.tileM.mapTile[gp.currentMap][playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "left":
                playerLeftCol = (entityLeftX - player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[gp.currentMap][playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[gp.currentMap][playerLeftCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "right":
                playerRightCol = (entityRightX + player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[gp.currentMap][playerRightCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[gp.currentMap][playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
        }
    }

    public void animalCheckTile(Animal animal) {
        int entityLeftX = animal.worldX + animal.solidArea.x;
        int entityRightX = animal.worldX + animal.solidArea.x + animal.solidArea.width;
        int entityTopY = animal.worldY + animal.solidArea.y;
        int entityBottomY = animal.worldY + animal.solidArea.y + animal.solidArea.height;

        switch(animal.direction) {
            case "up": entityTopY -= animal.speed; break;
            case "down": entityBottomY += animal.speed; break;
            case "left": entityLeftX -= animal.speed; break;
            case "right": entityRightX += animal.speed; break;
        }

        int entityLeftCol = entityLeftX / gp.TILE_SIZE;
        int entityRightCol = entityRightX / gp.TILE_SIZE;
        int entityTopRow = entityTopY / gp.TILE_SIZE;
        int entityBottomRow = entityBottomY / gp.TILE_SIZE;
        
        // Boundary check
        if(entityLeftCol < 0 || entityRightCol >= gp.MAX_WORLD_COL || 
        entityTopRow < 0 || entityBottomRow >= gp.MAX_WORLD_ROW) {
            animal.collisionOn = true;
            return;
        }

        int validTiles = 18;
        
        int tileNum1 = gp.tileM.mapTile[gp.currentMap][entityLeftCol][entityTopRow];     // Top left
        int tileNum2 = gp.tileM.mapTile[gp.currentMap][entityRightCol][entityTopRow];    // Top right
        int tileNum3 = gp.tileM.mapTile[gp.currentMap][entityLeftCol][entityBottomRow];  // Bottom left
        int tileNum4 = gp.tileM.mapTile[gp.currentMap][entityRightCol][entityBottomRow]; // Bottom right
        
        // Check if all corners are on valid tiles
        boolean isValid1 = false;
        boolean isValid2 = false;
        boolean isValid3 = false;
        boolean isValid4 = false;
    
        if(tileNum1 == validTiles) isValid1 = true;
        if(tileNum2 == validTiles) isValid2 = true;
        if(tileNum3 == validTiles) isValid3 = true;
        if(tileNum4 == validTiles) isValid4 = true;

        // If any corner is on invalid tile, set collision
        if(!isValid1 && !isValid2 && !isValid3 && !isValid4) {
            animal.collisionOn = true;
        }
    }
    
    public void animalCheckObject(Animal animal) {
        for (int i = 0; i < gp.plants.size(); i++) {
            animal.solidArea.x = animal.worldX + animal.solidArea.x;
            animal.solidArea.y = animal.worldY + animal.solidArea.y;
            gp.plants.get(i).solidArea.x = gp.plants.get(i).worldX + gp.plants.get(i).solidArea.x;
            gp.plants.get(i).solidArea.y = gp.plants.get(i).worldY + gp.plants.get(i).solidArea.y;

            if (animal.solidArea.intersects(gp.plants.get(i).solidArea)) {
                if (gp.plants.get(i) instanceof Tree) {
                    animal.collisionOn = true;
                }
            }
            animal.solidArea.x = animal.solidAreaDefaultX;
            animal.solidArea.y = animal.solidAreaDefaultY;
            gp.plants.get(i).solidArea.x = gp.plants.get(i).solidAreaDefaultX;
            gp.plants.get(i).solidArea.y = gp.plants.get(i).solidAreaDefaultY;
        }
    }

    public void fishCheckTile(Fish animal) {
        int entityLeftX = animal.worldX + animal.solidArea.x;
        int entityRightX = animal.worldX + animal.solidArea.x + animal.solidArea.width;
        int entityTopY = animal.worldY + animal.solidArea.y;
        int entityBottomY = animal.worldY + animal.solidArea.y + animal.solidArea.height;

        int nextLeftX = entityLeftX;
        int nextRightX = entityRightX;
        int nextTopY = entityTopY;
        int nextBottomY = entityBottomY;

        switch(animal.direction) {
            case "up": nextTopY -= animal.speed; break;
            case "down": nextBottomY += animal.speed; break;
            case "left": nextLeftX -= animal.speed; break;
            case "right": nextRightX += animal.speed; break;
        }

        int nextLeftCol = nextLeftX / gp.TILE_SIZE;
        int nextRightCol = nextRightX / gp.TILE_SIZE;
        int nextTopRow = nextTopY / gp.TILE_SIZE;
        int nextBottomRow = nextBottomY / gp.TILE_SIZE;
        
        if(nextLeftCol < 0 || nextRightCol >= gp.MAX_WORLD_COL || 
        nextTopRow < 0 || nextBottomRow >= gp.MAX_WORLD_ROW) {
            animal.collisionOn = true;
            return;
        }

        int validWaterTile = 16;
        
        int tileNum1 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextTopRow];     // Top left
        int tileNum2 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextTopRow];    // Top right
        int tileNum3 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextBottomRow];  // Bottom left
        int tileNum4 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextBottomRow]; // Bottom right
        
        if(tileNum1 != validWaterTile || tileNum2 != validWaterTile || 
        tileNum3 != validWaterTile || tileNum4 != validWaterTile) {
            animal.collisionOn = true;
        }
    }
    
    public void animalCheckBuildings(Animal animal) {
        for (int i = 0; i < gp.buildings.size(); i++) {
            animal.solidArea.x = animal.worldX + animal.solidArea.x;
            animal.solidArea.y = animal.worldY + animal.solidArea.y;
            gp.buildings.get(i).solidArea.x = gp.buildings.get(i).worldX + gp.buildings.get(i).solidArea.x;
            gp.buildings.get(i).solidArea.y = gp.buildings.get(i).worldY + gp.buildings.get(i).solidArea.y;

            if (animal.solidArea.intersects(gp.buildings.get(i).solidArea)) {
                if (animal instanceof Animal) {
                    animal.collisionOn = gp.buildings.get(i).isAllowCollison;
                }
            }
            animal.solidArea.x = animal.solidAreaDefaultX;
            animal.solidArea.y = animal.solidAreaDefaultY;
            gp.buildings.get(i).solidArea.x = gp.buildings.get(i).solidAreaDefaultX;
            gp.buildings.get(i).solidArea.y = gp.buildings.get(i).solidAreaDefaultY;
        }
    }

    public int checkPlant(Player player, boolean collison) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.plants.size(); i++) {
            player.solidArea.x = player.worldX + player.solidArea.x;
            player.solidArea.y = player.worldY + player.solidArea.y;
            gp.plants.get(i).solidArea.x = gp.plants.get(i).worldX + gp.plants.get(i).solidArea.x;
            gp.plants.get(i).solidArea.y = gp.plants.get(i).worldY + gp.plants.get(i).solidArea.y;
            
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.plants.get(i).solidArea.x = gp.plants.get(i).solidAreaDefaultX;
                gp.plants.get(i).solidArea.y = gp.plants.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public int checkBuildings(Player player, boolean collison) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.buildings.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.buildings.get(i).solidArea.x = gp.buildings.get(i).worldX + gp.buildings.get(i).solidArea.x;
                gp.buildings.get(i).solidArea.y = gp.buildings.get(i).worldY + gp.buildings.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison; // Kalo item ini diapus aja
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison;
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison;
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison;
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.buildings.get(i).solidArea.x = gp.buildings.get(i).solidAreaDefaultX;
                gp.buildings.get(i).solidArea.y = gp.buildings.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public int checkFish(Player player, boolean collison) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.fish.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.fish.get(i).solidArea.x = gp.fish.get(i).worldX + gp.fish.get(i).solidArea.x;
                gp.fish.get(i).solidArea.y = gp.fish.get(i).worldY + gp.fish.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.fish.get(i).solidArea)) {
                            index = i; 
                            if (gp.currentMap == 1) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.fish.get(i).solidArea)) {
                            index = i; 
                            if (gp.currentMap == 1) {
                                player.collisionOn = true; // Kalo item ini diapus aja
                            }
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.fish.get(i).solidArea)) {
                            index = i; 
                            if (gp.currentMap == 1) {
                                player.collisionOn = true;
                            }
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.fish.get(i).solidArea)) {
                            index = i; 
                            if (gp.currentMap == 1) {
                                player.collisionOn = true;
                            }
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.fish.get(i).solidArea.x = gp.fish.get(i).solidAreaDefaultX;
                gp.fish.get(i).solidArea.y = gp.fish.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public int checkItemDrop(Player player, boolean canTakeItem) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.droppedItems.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.droppedItems.get(i).solidArea.x = gp.droppedItems.get(i).worldX + gp.droppedItems.get(i).solidArea.x;
                gp.droppedItems.get(i).solidArea.y = gp.droppedItems.get(i).worldY + gp.droppedItems.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "down":
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "left":
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "right":
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.droppedItems.get(i).solidArea.x = gp.droppedItems.get(i).solidAreaDefaultX;
                gp.droppedItems.get(i).solidArea.y = gp.droppedItems.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public void checkPlayer(Animal animal) {
        // Get hitbox areas
        animal.solidArea.x = animal.worldX + animal.solidArea.x;
        animal.solidArea.y = animal.worldY + animal.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
    
        // Check collision
        if(animal.solidArea.intersects(gp.player.solidArea)) {
            animal.collisionOn = true;
            int def = gp.player.getDefense();
            if (animal instanceof Wolf) {
                System.out.println("Player is attacked by a wolf!");
                if(10 - def <= 0){
                    gp.player.health -= 1; // Decrease player HP by 1 if defense is high enough
                }else {
                    gp.player.health -= (10-def); // Decrease player HP
                }
                if (gp.player.health <= 0) {
                    gp.player.health = 0; // Prevent negative health
                }
            }
        }
    
        // Reset hitbox positions
        animal.solidArea.x = animal.solidAreaDefaultX;
        animal.solidArea.y = animal.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }

    public void checkFishPlayer(Fish animal) {
        animal.solidArea.x = animal.worldX + animal.solidArea.x;
        animal.solidArea.y = animal.worldY + animal.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;

        if(animal.solidArea.intersects(gp.player.solidArea)) {
            animal.collisionOn = true;

            int fishIndex = gp.fish.indexOf(animal);
            if (gp.currentMap == 1) {
                // System.out.println("Player bertemu ikan: " + animal.nameFish + " (strength: " + animal.strength + ")");
                if (gp.player.durabilityRod > 0) {
                    // Start fishing minigame
                    gp.ui.caughtFish = animal;
                    gp.ui.fishIndex = fishIndex;
                    gp.ui.playerFishingStrength = 50;
                    gp.gameState = gp.FISHING_STATE;
                } else {
                    gp.ui.showRodRusakMessage();
                }
            }
        }

        // Reset hitbox positions
        animal.solidArea.x = animal.solidAreaDefaultX;
        animal.solidArea.y = animal.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
    
    public void checkAnimalCollision(Animal animal) {
        int nextX = animal.worldX;
        int nextY = animal.worldY;
        
        switch (animal.direction) {
            case "up": nextY -= animal.speed; break;
            case "down": nextY += animal.speed; break;
            case "left": nextX -= animal.speed; break;
            case "right": nextX += animal.speed; break;
        }
    
        for (Animal otherAnimal : gp.animals) {
            if (animal == otherAnimal) continue;
            
            // Create predicted collision box
            Rectangle predictedArea = new Rectangle(
                nextX + animal.solidArea.x,
                nextY + animal.solidArea.y,
                animal.solidArea.width,
                animal.solidArea.height
            );
            
            // Get other animal's area
            Rectangle otherArea = new Rectangle(
                otherAnimal.worldX + otherAnimal.solidArea.x,
                otherAnimal.worldY + otherAnimal.solidArea.y,
                otherAnimal.solidArea.width,
                otherAnimal.solidArea.height
            );
            
            // Check collision with predicted position
            if (predictedArea.intersects(otherArea)) {
                animal.collisionOn = true;
                // Add separation force
                int pushDistance = 2;
                switch (animal.direction) {
                    case "up": 
                        animal.worldY += pushDistance;
                        otherAnimal.worldY -= pushDistance;
                        break;
                    case "down": 
                        animal.worldY -= pushDistance;
                        otherAnimal.worldY += pushDistance;
                        break;
                    case "left": 
                        animal.worldX += pushDistance;
                        otherAnimal.worldX -= pushDistance;
                        break;
                    case "right": 
                        animal.worldX -= pushDistance;
                        otherAnimal.worldX += pushDistance;
                        break;
                }
                // Force direction change for both animals
                animal.direction = getOppositeDirection(animal.direction);
                otherAnimal.direction = getOppositeDirection(otherAnimal.direction);
            }
        }
    }
    
    public void checkFishCollision(Fish animal) {
        int nextX = animal.worldX;
        int nextY = animal.worldY;
        
        switch (animal.direction) {
            case "up": nextY -= animal.speed; break;
            case "down": nextY += animal.speed; break;
            case "left": nextX -= animal.speed; break;
            case "right": nextX += animal.speed; break;
        }
    
        for (int i = 0; i < gp.fish.size(); i++) {
            if (animal == gp.fish.get(i)) continue;
            
            // Create predicted collision box
            Rectangle predictedArea = new Rectangle(
                nextX + animal.solidArea.x,
                nextY + animal.solidArea.y,
                animal.solidArea.width,
                animal.solidArea.height
            );
            
            // Get other animal's area
            Rectangle otherArea = new Rectangle(
                gp.fish.get(i).worldX + gp.fish.get(i).solidArea.x,
                gp.fish.get(i).worldY + gp.fish.get(i).solidArea.y,
                gp.fish.get(i).solidArea.width,
                gp.fish.get(i).solidArea.height
            );
            
            // Check collision with predicted position
            if (predictedArea.intersects(otherArea)) {
                animal.collisionOn = true;
                // Add separation force
                int pushDistance = 2;
                switch (animal.direction) {
                    case "up": 
                        animal.worldY += pushDistance;
                        gp.fish.get(i).worldY -= pushDistance;
                        break;
                    case "down": 
                        animal.worldY -= pushDistance;
                        gp.fish.get(i).worldY += pushDistance;
                        break;
                    case "left": 
                        animal.worldX += pushDistance;
                        gp.fish.get(i).worldX -= pushDistance;
                        break;
                    case "right": 
                        animal.worldX -= pushDistance;
                        gp.fish.get(i).worldX += pushDistance;
                        break;
                }
                animal.direction = getOppositeDirection(animal.direction);
                gp.fish.get(i).direction = getOppositeDirection(gp.fish.get(i).direction);
            }
        }
    }

    private String getOppositeDirection(String direction) {
        switch(direction) {
            case "up": return "down";
            case "down": return "up";
            case "left": return "right";
            case "right": return "left";
            default: return "down";
        }
    }

    public int checkAnimal(Player player, boolean collison) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.animals.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.animals.get(i).solidArea.x = gp.animals.get(i).worldX + gp.animals.get(i).solidArea.x;
                gp.animals.get(i).solidArea.y = gp.animals.get(i).worldY + gp.animals.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.animals.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.animals.get(i).collisionOn = true;
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.animals.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.animals.get(i).collisionOn = true;
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.animals.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.animals.get(i).collisionOn = true;
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.animals.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.animals.get(i).collisionOn = true;
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.animals.get(i).solidArea.x = gp.animals.get(i).solidAreaDefaultX;
                gp.animals.get(i).solidArea.y = gp.animals.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public int checkMonsters(Player player, boolean collison) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.monsters.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.monsters.get(i).solidArea.x = gp.monsters.get(i).worldX + gp.monsters.get(i).solidArea.x;
                gp.monsters.get(i).solidArea.y = gp.monsters.get(i).worldY + gp.monsters.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.monsters.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.monsters.get(i).collisionOn = true;
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.monsters.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.monsters.get(i).collisionOn = true;
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.monsters.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.monsters.get(i).collisionOn = true;
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.monsters.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                            gp.monsters.get(i).collisionOn = true;
                        }
                        break;
                }
                player.solidArea.x = player.solidAreaDefaultX;
                player.solidArea.y = player.solidAreaDefaultY;
                gp.monsters.get(i).solidArea.x = gp.monsters.get(i).solidAreaDefaultX;
                gp.monsters.get(i).solidArea.y = gp.monsters.get(i).solidAreaDefaultY;

            }
        return index;
    }

    public void checkMonsterPlayer(Monster monster) {
        // Get hitbox areas
        monster.solidArea.x = monster.worldX + monster.solidArea.x;
        monster.solidArea.y = monster.worldY + monster.solidArea.y;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
    
        // Check collision
        if(monster.solidArea.intersects(gp.player.solidArea)) {
            int def = gp.player.getDefense();
            if(10-def < 0){
                gp.player.health -= 1; 
            }else{

                gp.player.health -= (10- def); // Decrease player HP
            }
            if (gp.player.health <= 0) {
                gp.player.health = 0; // Prevent negative health
            } 
            monster.collisionOn = true;
        }
    
        // Reset hitbox positions
        monster.solidArea.x = monster.solidAreaDefaultX;
        monster.solidArea.y = monster.solidAreaDefaultY;
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
    }
    
    public void monsterCheckTile(Monster monster) {
        int entityLeftX = monster.worldX + monster.solidArea.x;
        int entityRightX = monster.worldX + monster.solidArea.x + monster.solidArea.width;
        int entityTopY = monster.worldY + monster.solidArea.y;
        int entityBottomY = monster.worldY + monster.solidArea.y + monster.solidArea.height;

        int nextLeftX = entityLeftX;
        int nextRightX = entityRightX;
        int nextTopY = entityTopY;
        int nextBottomY = entityBottomY;

        switch(monster.direction) {
            case "up": nextTopY -= monster.speed; break;
            case "down": nextBottomY += monster.speed; break;
            case "left": nextLeftX -= monster.speed; break;
            case "right": nextRightX += monster.speed; break;
        }

        int nextLeftCol = nextLeftX / gp.TILE_SIZE;
        int nextRightCol = nextRightX / gp.TILE_SIZE;
        int nextTopRow = nextTopY / gp.TILE_SIZE;
        int nextBottomRow = nextBottomY / gp.TILE_SIZE;
        
        if(nextLeftCol < 0 || nextRightCol >= gp.MAX_WORLD_COL || 
        nextTopRow < 0 || nextBottomRow >= gp.MAX_WORLD_ROW) {
            monster.collisionOn = true;
            return;
        }

        int validTile = 21;
        
        int tileNum1 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextTopRow];     // Top left
        int tileNum2 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextTopRow];    // Top right
        int tileNum3 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextBottomRow];  // Bottom left
        int tileNum4 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextBottomRow]; // Bottom right
        
        if(tileNum1 != validTile || tileNum2 != validTile || 
        tileNum3 != validTile || tileNum4 != validTile) {
            monster.collisionOn = true;
        }
    }

    public void checkMonstersCollision(Monster monster) {
        int nextX = monster.worldX;
        int nextY = monster.worldY;
        
        switch (monster.direction) {
            case "up": nextY -= monster.speed; break;
            case "down": nextY += monster.speed; break;
            case "left": nextX -= monster.speed; break;
            case "right": nextX += monster.speed; break;
        }
    
        for (int i = 0; i < gp.monsters.size(); i++) {
            if (monster == gp.monsters.get(i)) continue;
            
            // Create predicted collision box
            Rectangle predictedArea = new Rectangle(
                nextX + monster.solidArea.x,
                nextY + monster.solidArea.y,
                monster.solidArea.width,
                monster.solidArea.height
            );
            
            // Get other animal's area
            Rectangle otherArea = new Rectangle(
                gp.monsters.get(i).worldX + gp.monsters.get(i).solidArea.x,
                gp.monsters.get(i).worldY + gp.monsters.get(i).solidArea.y,
                gp.monsters.get(i).solidArea.width,
                gp.monsters.get(i).solidArea.height
            );
            
            // Check collision with predicted position
            if (predictedArea.intersects(otherArea)) {
                monster.collisionOn = true;
                // Add separation force
                int pushDistance = 2;
                switch (monster.direction) {
                    case "up": 
                        monster.worldY += pushDistance;
                        gp.monsters.get(i).worldY -= pushDistance;
                        break;
                    case "down": 
                        monster.worldY -= pushDistance;
                        gp.monsters.get(i).worldY += pushDistance;
                        break;
                    case "left": 
                        monster.worldX += pushDistance;
                        gp.monsters.get(i).worldX -= pushDistance;
                        break;
                    case "right": 
                        monster.worldX -= pushDistance;
                        gp.monsters.get(i).worldX += pushDistance;
                        break;
                }
                // Force direction change for both animals
                monster.direction = getOppositeDirection(monster.direction);
                gp.monsters.get(i).direction = getOppositeDirection(gp.monsters.get(i).direction);
            }
        }
    }

}
