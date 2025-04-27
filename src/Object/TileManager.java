package Object;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTile[][];

    public TileManager(GamePanel gp) {
        this.gp= gp;
        tile = new Tile[20];
        mapTile = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        getTileImage();
        loadMap("ProjectTheSurvivalist/res/world/map.txt");
        for (int i = 0; i < gp.MAX_WORLD_COL; i++) {
            for (int j = 0; j < gp.MAX_WORLD_ROW; j++) {
                System.out.print(mapTile[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/calm-water.png"));
            tile[0].collison = true;
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[1].collison = false;
            
            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/sand.png"));
            tile[2].collison = false;
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[3].collison = true;

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            InputStream is = new FileInputStream(new File(path));
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
    
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append(" "); // kasih spasi antar line
            }
            
            br.close();
    
            // Sekarang sb sudah berisi semua angka dalam 1 string
            String[] numbers = sb.toString().trim().split("\\s+"); // split berdasarkan spasi
            
            int col = 0;
            int row = 0;
            for (int i = 0; i < numbers.length; i++) {
                int num = Integer.parseInt(numbers[i]);
                mapTile[col][row] = num;
                col++;
                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public void loadMap(String path) {
    //     try {
    //         InputStream is = new FileInputStream(new File(path));
    //         BufferedReader br = new BufferedReader(new InputStreamReader(is));

    //         int col = 0;
    //         int row = 0;
    //         while (col < gp.MAX_WORLD_COL && row < gp.MAX_WORLD_ROW) {
    //             String line = br.readLine();
    //             while (col < gp.MAX_WORLD_COL) {
    //                 String numbers[] = line.split(" ");

    //                 int num = Integer.parseInt(numbers[col]);
    //                 mapTile[col][row] = num;
    //                 col++;
    //             }
    //             if (col == gp.MAX_WORLD_COL) {
    //                 col = 0;
    //                 row++;
    //             }
    //         }
            
    //         br.close();
            
    //     } catch (IOException e) {
    //         e.getStackTrace();
    //     }
    // }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

            int tileNum = mapTile[worldCol][worldRow];

            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
            int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
                && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
                && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
                && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            }
            worldCol++;
            
            if (worldCol == gp.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
