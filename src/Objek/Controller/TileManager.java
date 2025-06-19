package Objek.Controller;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    public int mapTile[][][];

    public TileManager(GamePanel gp) {
        this.gp = gp;
        tile = new Tile[30];
        mapTile = new int[gp.maxMap][gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        loadMap("/res/world/map.txt", 0);
        loadMap("/res/world/seamap.txt", 1);
        loadMap("/res/world/cave.txt", 2);
        loadMap("/res/world/shop.txt", 3);
        getTileImage();
    }

    public void getTileImage() {
        setup(0, "calm-water", true);
        setup(1, "calm-water", true);
        setup(2, "calm-water", true);
        setup(3, "calm-water", true);
        setup(4, "calm-water", true);
        setup(5, "calm-water", true);
        setup(6, "stone", true);
        setup(7, "calm-water", true);
        setup(8, "grass", false);
        setup(9, "grass", false);
        setup(10, "grass", false);
        setup(11, "grass", false);
        setup(12, "grass", false);
        setup(13, "grass", false);
        setup(14, "grass", false);
        setup(15, "grass", false);
        
        if (gp.currentMap == 0){
            setup(16, "calm-water", true);
            setup(17, "sand", false);
            setup(19, "bridge1", false);
            if(gp.player.level < 15){
                setup(21, "kapalrusak", true);
            } else {
                setup(21, "ship", false);
            }
            setup(22, "bridgesand", false);
            setup(23, "grass", false);
        } else if(gp.currentMap == 1){
            setup(16, "calm-water", false);
            setup(17, "sand", true);
            setup(19, "bridge2", true);
            setup(21, "ship", false);
            setup(22, "bridgesand", false);
            setup(23, "stone", true);
        } else if(gp.currentMap == 2){
            setup(16, "calm-water", false);
            setup(17, "sand", true);
            setup(19, "bridge2", true);
            setup(21, "sandcave", false);
            setup(22, "lavacave", true);
            setup(23, "bridgecave", false);
        }

        setup(18, "grass", false);
        setup(20, "grass", false);
        setup(24, "calm-water", false);
        setup(25, "lantaishop", false);
        setup(26, "blank", true);
    }

    public void setup(int index, String imagePath, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResource("/res/world/" + imagePath + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.TILE_SIZE, gp.TILE_SIZE);
            tile[index].collison = collision;
        } catch (IOException e) {
            e.printStackTrace();
        } 
    }

    public void loadMap(String path, int map) {
        try {
            InputStream is = getClass().getResourceAsStream(path);
            if (is == null) {
                System.err.println("File tidak ditemukan: " + path);
                return;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            List<Integer> numbers = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                for (String token : tokens) {
                    if (!token.trim().isEmpty()) {
                        numbers.add(Integer.parseInt(token.trim()));
                    }
                }
            }

            br.close();

            int totalTiles = gp.MAX_WORLD_COL * gp.MAX_WORLD_COL;
            if (numbers.size() != totalTiles) {
                throw new IllegalArgumentException("Jumlah angka dalam file tidak sesuai dengan ukuran peta (" 
                    + gp.MAX_WORLD_COL + "x" + gp.MAX_WORLD_COL + "). Dibutuhkan " + totalTiles + " angka, tetapi ditemukan " + numbers.size() + ".");
            }

            int col = 0;
            int row = 0;
            for (int num : numbers) {
                mapTile[map][col][row] = num;
                col++;
                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            // System.out.println("Peta berhasil dimuat!");

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;
        while (worldCol < gp.MAX_WORLD_COL && worldRow < gp.MAX_WORLD_ROW) {

            int tileNum = mapTile[gp.currentMap][worldCol][worldRow];

            int worldX = worldCol * gp.TILE_SIZE;
            int worldY = worldRow * gp.TILE_SIZE;
            int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
            int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

            if (worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X 
                && worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X 
                && worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y 
                && worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
                if (tile[tileNum].image != null) {
                    g2.drawImage(tile[tileNum].image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
                } else {
                    System.err.println("Gambar tile tidak ditemukan untuk tile number: " + tileNum);
                }
            }
            worldCol++;
            
            if (worldCol == gp.MAX_WORLD_COL) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
