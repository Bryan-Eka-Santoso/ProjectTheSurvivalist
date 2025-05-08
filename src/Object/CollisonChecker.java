package Object;

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
        int entityLeftX = animal.worldX + animal.solidArea.x;
        int entityRightX = animal.worldX + animal.solidArea.x + animal.solidArea.width;
        int entityTopY = animal.worldY + animal.solidArea.y;
        int entityBottomY = animal.worldY + animal.solidArea.y + animal.solidArea.height;

        int animalLeftCol = entityLeftX / gp.TILE_SIZE;
        int animalRightCol = entityRightX / gp.TILE_SIZE;
        int animalTopRow = entityTopY / gp.TILE_SIZE;
        int animalBottomRow = entityBottomY / gp.TILE_SIZE;

        int tileNum1 = 0, tileNum2 = 0;

        switch (animal.direction) {
            case "up":
                animalTopRow = (entityTopY - animal.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[animalLeftCol][animalTopRow];
                tileNum2 = gp.tileM.mapTile[animalLeftCol][animalTopRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    animal.collisionOn = true;
                }
                break;
            case "down":
                animalBottomRow = (entityBottomY + animal.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[animalLeftCol][animalBottomRow];
                tileNum2 = gp.tileM.mapTile[animalRightCol][animalBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    animal.collisionOn = true;
                }
                break;
            case "left":
                animalLeftCol = (entityLeftX - animal.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[animalLeftCol][animalTopRow];
                tileNum2 = gp.tileM.mapTile[animalLeftCol][animalBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    animal.collisionOn = true;
                }
                break;
            case "right":
                animalRightCol = (entityRightX + animal.speed) / gp.TILE_SIZE;
                tileNum1 = gp.tileM.mapTile[animalRightCol][animalTopRow];
                tileNum2 = gp.tileM.mapTile[animalRightCol][animalBottomRow];
                if (gp.tileM.tile[tileNum1].collison == true || gp.tileM.tile[tileNum2].collison == true) {
                    animal.collisionOn = true;
                }
                break;
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
