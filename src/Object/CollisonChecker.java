package Object;

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

        int tileNum1, tileNum2;

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
}
