package Objek.Controller;

import java.awt.Rectangle;
import java.util.Random;

import Objek.Animal.Animal;
import Objek.Animal.Wolf;
import Objek.Enemy.Golem;
import Objek.Enemy.Monster;
import Objek.Fish.Fish;
import Objek.Items.StackableItem.Materials.AnimalDrops.WolfHide;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalIngot;
import Objek.Items.Unstackable.FishingRod;
import Objek.Items.Unstackable.Armor.Chestplate.BladeArmor;
import Objek.Ore.Ore;
import Objek.Plant.Bushes.Bush;
import Objek.Plant.Trees.Tree;
import Objek.Player.Player;

public class CollisonChecker {
    GamePanel gp;
    Random rand = new Random();
    Sound sound = new Sound();

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

            if (animal.solidArea.intersects(gp.buildings.get(i).solidArea) && gp.buildings.get(i).buildingMap == 0) {
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
                                player.collisionOn = true;
                            }
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true;
                            }
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true;
                            }
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.plants.get(i).solidArea)) {
                            index = i; 
                            if (!(gp.plants.get(i) instanceof Bush)) {
                                player.collisionOn = true;
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
    public int checkOre(Player player, boolean collison) {
        int index = -1;
        
        for(int i = 0; i < gp.ores.size(); i++) {
            player.solidArea.x = player.worldX + player.solidArea.x;
            player.solidArea.y = player.worldY + player.solidArea.y;
            gp.ores.get(i).solidArea.x = gp.ores.get(i).worldX + gp.ores.get(i).solidArea.x;
            gp.ores.get(i).solidArea.y = gp.ores.get(i).worldY + gp.ores.get(i).solidArea.y;
            
            switch(player.direction) {
                case "up":
                    player.solidArea.y -= player.speed;
                    if(player.solidArea.intersects(gp.ores.get(i).solidArea)) {
                        player.collisionOn = true;
                        index = i;
                    }
                    break;
                case "down":
                    player.solidArea.y += player.speed;
                    if(player.solidArea.intersects(gp.ores.get(i).solidArea)) {
                        player.collisionOn = true;
                        index = i;
                    }
                    break;
                case "left":
                    player.solidArea.x -= player.speed;
                    if(player.solidArea.intersects(gp.ores.get(i).solidArea)) {
                        player.collisionOn = true;
                        index = i;
                    }
                    break;
                case "right":
                    player.solidArea.x += player.speed;
                    if(player.solidArea.intersects(gp.ores.get(i).solidArea)) {
                        player.collisionOn = true;
                        index = i;
                    }
                    break;
            }
            player.solidArea.x = player.solidAreaDefaultX;
            player.solidArea.y = player.solidAreaDefaultY;
            gp.ores.get(i).solidArea.x = gp.ores.get(i).solidAreaDefaultX;
            gp.ores.get(i).solidArea.y = gp.ores.get(i).solidAreaDefaultY;
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
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea) && gp.currentMap == gp.buildings.get(i).buildingMap) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison; // Kalo item ini diapus aja
                        }
                        break;
                    case "down":
                        player.solidArea.y += player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea) && gp.currentMap == gp.buildings.get(i).buildingMap) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison;
                        }
                        break;
                    case "left":
                        player.solidArea.x -= player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea) && gp.currentMap == gp.buildings.get(i).buildingMap) {
                            index = i; 
                            player.collisionOn = gp.buildings.get(i).isAllowCollison;
                        }
                        break;
                    case "right":
                        player.solidArea.x += player.speed;
                        if (player.solidArea.intersects(gp.buildings.get(i).solidArea) && gp.currentMap == gp.buildings.get(i).buildingMap) {
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
        if(animal.solidArea.intersects(gp.player.solidArea) && !gp.player.isFrozen && gp.player.health > 0) {
            animal.collisionOn = true;
            int def = gp.player.getDefense();
            if (animal instanceof Wolf) {
                ((Wolf) animal).isBiting = false;
                if (gp.player.helmet != null) {
                    gp.player.helmet.durability--;
                    if (gp.player.helmet.durability <= 0) {
                        gp.player.helmet = null; // Remove helmet if durability is zero
                    }
                }
                if (gp.player.chestplate != null) {
                    gp.player.chestplate.durability--;
                    if (gp.player.chestplate instanceof BladeArmor) {
                        animal.hp -= 5;
                        if(animal.hp <= 0) {
                            gp.player.totalAnimalsKilled++;
                            if (rand.nextInt(10) < 2) {
                                gp.player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new WolfHide(1), gp));
                            }
                            gp.removedAnimals.add(animal);
                            gp.player.gainExp(rand.nextInt(10) + 9);
                            gp.player.animalIndex = -1;
                        }
                    }
                    if (gp.player.chestplate.durability <= 0) {
                        gp.player.chestplate = null; // Remove chestplate if durability is zero
                    }
                }
                if (gp.player.leggings != null) {
                    gp.player.leggings.durability--;
                    if (gp.player.leggings.durability <= 0) {
                        gp.player.leggings = null; // Remove leggings if durability is zero
                    }
                }
                if (gp.player.boots != null) {
                    gp.player.boots.durability--;
                    if (gp.player.boots.durability <= 0) {
                        gp.player.boots = null; // Remove boots if durability is zero
                    }
                }
                if(16 - def <= 0){
                    gp.player.health -= 1; // Decrease player HP by 1 if defense is high enough
                    playSE(9);
                } else {
                    gp.player.health -= (12 - def); // Decrease player HP by 12 minus defense
                    playSE(9);
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
                if (gp.player.inventory.slots[gp.ui.selectedIndex] instanceof FishingRod) {
                    if (((FishingRod) gp.player.inventory.slots[gp.ui.selectedIndex]).durability > 0) {
                        gp.ui.caughtFish = animal;
                        gp.ui.fishIndex = fishIndex;
                        gp.gameState = gp.FISHING_STATE;
                    } else {
                        gp.ui.showRodRusakMessage();
                    }
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
    
        for (int i = 0; i < gp.animals.size(); i++) {
            if (animal == gp.animals.get(i)) continue;
            
            // Create predicted collision box
            Rectangle predictedArea = new Rectangle(
                nextX + animal.solidArea.x,
                nextY + animal.solidArea.y,
                animal.solidArea.width,
                animal.solidArea.height
            );
            
            // Get other animal's area
            Rectangle otherArea = new Rectangle(
                gp.animals.get(i).worldX + gp.animals.get(i).solidArea.x,
                gp.animals.get(i).worldY + gp.animals.get(i).solidArea.y,
                gp.animals.get(i).solidArea.width,
                gp.animals.get(i).solidArea.height
            );
            
            // Check collision with predicted position
            if (predictedArea.intersects(otherArea)) {
                animal.collisionOn = true;
                // Add separation force
                int pushDistance = 2;
                switch (animal.direction) {
                    case "up": 
                        animal.worldY += pushDistance;
                        gp.animals.get(i).worldY -= pushDistance;
                        break;
                    case "down": 
                        animal.worldY -= pushDistance;
                        gp.animals.get(i).worldY += pushDistance;
                        break;
                    case "left": 
                        animal.worldX += pushDistance;
                        gp.animals.get(i).worldX -= pushDistance;
                        break;
                    case "right": 
                        animal.worldX -= pushDistance;
                        gp.animals.get(i).worldX += pushDistance;
                        break;
                }
                // Force direction change for both animals
                animal.direction = getOppositeDirection(animal.direction);
                gp.animals.get(i).direction = getOppositeDirection(gp.animals.get(i).direction);
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
        if(monster.solidArea.intersects(gp.player.solidArea) && !gp.player.isFrozen && gp.player.health > 0) {
            monster.collisionOn = true;
            monster.isCollidePlayer = true; // Set flag to true when colliding with player
            int def = gp.player.getDefense();

            if (gp.player.helmet != null) {
                gp.player.helmet.durability--;
                if (gp.player.helmet.durability <= 0) {
                    gp.player.helmet = null; // Remove helmet if durability is zero
                }
            }
            if (gp.player.chestplate != null) {
                gp.player.chestplate.durability--;
                if (gp.player.chestplate instanceof BladeArmor) {
                    monster.hp -= 5;
                    if(monster.hp <= 0) {
                        gp.player.totalMonstersKilled++;
                        if (monster instanceof Golem) {
                            gp.player.gp.droppedItems.add(new ItemDrop(monster.worldX, monster.worldY, new MetalIngot(rand.nextInt(2) + 1), gp));
                        }
                        gp.removedMonsters.add(monster);
                        gp.player.gainExp(rand.nextInt(10) + 9);
                        gp.player.monsterIndex = -1;
                    }
                }
                if (gp.player.chestplate.durability <= 0) {
                    gp.player.chestplate = null; // Remove chestplate if durability is zero
                }
            }
            if (gp.player.leggings != null) {
                gp.player.leggings.durability--;
                if (gp.player.leggings.durability <= 0) {
                    gp.player.leggings = null; // Remove leggings if durability is zero
                }
            }
            if (gp.player.boots != null) {
                gp.player.boots.durability--;
                if (gp.player.boots.durability <= 0) {
                    gp.player.boots = null; // Remove boots if durability is zero
                }
            }
            if(monster.attack - def <= 0){
                gp.player.health -= 1; // Decrease player HP by 1 if defense is high enough
                playSE(9);
            } else {
                gp.player.health -= (monster.attack-def); // Decrease player HP
                playSE(9);
            }
            if (gp.player.health <= 0) {
                gp.player.health = 0; // Prevent negative health
            }
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

        switch(monster.direction) {
            case "up": entityTopY -= monster.speed; break;
            case "down": entityBottomY += monster.speed; break;
            case "left": entityLeftX -= monster.speed; break;
            case "right": entityRightX += monster.speed; break;
        }

        int nextLeftCol = entityLeftX / gp.TILE_SIZE;
        int nextRightCol = entityRightX / gp.TILE_SIZE;
        int nextTopRow = entityTopY / gp.TILE_SIZE;
        int nextBottomRow = entityBottomY / gp.TILE_SIZE;

        // Boundary check
        if(nextLeftCol < 0 || nextRightCol >= gp.MAX_WORLD_COL || 
        nextTopRow < 0 || nextBottomRow >= gp.MAX_WORLD_ROW) {
            monster.collisionOn = true;
            return;
        }

        // Get tile numbers
        int tileNum1 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextTopRow];
        int tileNum2 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextTopRow];
        int tileNum3 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextBottomRow];
        int tileNum4 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextBottomRow];

        // Check collision property dari tiles
        if(gp.tileM.tile[tileNum1].collison || 
        gp.tileM.tile[tileNum2].collison || 
        gp.tileM.tile[tileNum3].collison || 
        gp.tileM.tile[tileNum4].collison) {
            monster.collisionOn = true;
        }
    }
    public void monsterCheckOre(Monster monster) {
        for(Ore ore : gp.ores) {
            monster.solidArea.x = monster.worldX + monster.solidArea.x;
            monster.solidArea.y = monster.worldY + monster.solidArea.y;
            ore.solidArea.x = ore.worldX + ore.solidArea.x;
            ore.solidArea.y = ore.worldY + ore.solidArea.y;
            
            if(monster.solidArea.intersects(ore.solidArea)) {
                monster.collisionOn = true;
            }
            
            monster.solidArea.x = monster.solidAreaDefaultX;
            monster.solidArea.y = monster.solidAreaDefaultY;
            ore.solidArea.x = ore.solidAreaDefaultX;
            ore.solidArea.y = ore.solidAreaDefaultY;
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

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
    

}
