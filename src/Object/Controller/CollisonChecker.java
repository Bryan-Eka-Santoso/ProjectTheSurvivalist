package Object.Controller;
import java.awt.Rectangle;
import Object.Animal.Animal;
import Object.Player.Player;
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
                tileNum1 = gp.tileM.mapTile[playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[playerRightCol][playerTopRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "down":
                playerBottomRow = (entityBottomY + player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[playerLeftCol][playerBottomRow];
                tileNum2 = gp.tileM.mapTile[playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "left":
                playerLeftCol = (entityLeftX - player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[playerLeftCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[playerLeftCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
            case "right":
                playerRightCol = (entityRightX + player.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[playerRightCol][playerTopRow];
                tileNum2 = gp.tileM.mapTile[playerRightCol][playerBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    player.collisionOn = true;
                }
                break;
        }
    }

    public void animalCheckTile(Animal animal) {
        int nextX = animal.worldX;
        int nextY = animal.worldY;
        
        switch(animal.direction) {
            case "up": nextY -= animal.speed; break;
            case "down": nextY += animal.speed; break;
            case "left": nextX -= animal.speed; break;
            case "right": nextX += animal.speed; break;
        }
    
        // Calculate collision area positions
        int entityLeftCol = (nextX + animal.solidArea.x) / gp.TILE_SIZE;
        int entityRightCol = (nextX + animal.solidArea.x + animal.solidArea.width) / gp.TILE_SIZE;
        int entityTopRow = (nextY + animal.solidArea.y) / gp.TILE_SIZE;
        int entityBottomRow = (nextY + animal.solidArea.y + animal.solidArea.height) / gp.TILE_SIZE;
    
        // Boundary check
        if(entityLeftCol < 0 || entityRightCol >= gp.MAX_WORLD_COL || 
           entityTopRow < 0 || entityBottomRow >= gp.MAX_WORLD_ROW) {
            animal.collisionOn = true;
            return;
        }
    
        // Check each corner for valid tile
        int[] validTiles = {8, 9, 10, 11, 12, 13, 14, 15, 18, 20};
        
        int tileNum1 = gp.tileM.mapTile[entityLeftCol][entityTopRow];     // Top left
        int tileNum2 = gp.tileM.mapTile[entityRightCol][entityTopRow];    // Top right
        int tileNum3 = gp.tileM.mapTile[entityLeftCol][entityBottomRow];  // Bottom left
        int tileNum4 = gp.tileM.mapTile[entityRightCol][entityBottomRow]; // Bottom right
    
        // Check if all corners are on valid tiles
        boolean isValid1 = false;
        boolean isValid2 = false;
        boolean isValid3 = false;
        boolean isValid4 = false;
    
        for(int validTile : validTiles) {
            if(tileNum1 == validTile) isValid1 = true;
            if(tileNum2 == validTile) isValid2 = true;
            if(tileNum3 == validTile) isValid3 = true;
            if(tileNum4 == validTile) isValid4 = true;
        }
    
        // If any corner is on invalid tile, set collision
        if(!isValid1 || !isValid2 || !isValid3 || !isValid4) {
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
                if (animal instanceof Animal) {
                    animal.collisionOn = true;
                }
            }
            animal.solidArea.x = animal.solidAreaDefaultX;
            animal.solidArea.y = animal.solidAreaDefaultY;
            gp.plants.get(i).solidArea.x = gp.plants.get(i).solidAreaDefaultX;
            gp.plants.get(i).solidArea.y = gp.plants.get(i).solidAreaDefaultY;
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
                            player.collisionOn = true; // Kalo item ini diapus aja
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            player.collisionOn = true;
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

    public int checkItemDrop(Player player, boolean canTakeItem) {
        int index = -1; // Default value if no collision is detected
        for (int i = 0; i < gp.droppedItems.size(); i++) {
                player.solidArea.x = player.worldX + player.solidArea.x;
                player.solidArea.y = player.worldY + player.solidArea.y;
                gp.droppedItems.get(i).solidArea.x = gp.droppedItems.get(i).worldX + gp.droppedItems.get(i).solidArea.x;
                gp.droppedItems.get(i).solidArea.y = gp.droppedItems.get(i).worldY + gp.droppedItems.get(i).solidArea.y;
                
                switch (player.direction) {
                    case "up":
                        player.solidArea.y -= player.speed;
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.droppedItems.get(i).solidArea)) {
                            index = i; 
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
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
        
        switch(animal.direction) {
            case "up": nextY -= animal.speed; break;
            case "down": nextY += animal.speed; break;
            case "left": nextX -= animal.speed; break;
            case "right": nextX += animal.speed; break;
        }
    
        for(Animal otherAnimal : gp.animals) {
            if(animal == otherAnimal) continue;
            
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
            if(predictedArea.intersects(otherArea)) {
                animal.collisionOn = true;
                // Add separation force
                int pushDistance = 2;
                switch(animal.direction) {
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

    // public void checkPlantCollison(Player player) {
    //     int index = checkPlant(player, gp.plants);
    //     if (index != -1) {
    //         player.collisionOn = true;
    //     } else {
    //         player.collisionOn = false;
    //     }
    // }
}
