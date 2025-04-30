package Object;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int mapTile[][];

    public TileManager(GamePanel gp) {
        this.gp= gp;
        tile = new Tile[30];
        mapTile = new int[gp.MAX_WORLD_COL][gp.MAX_WORLD_ROW];

        loadMap("ProjectTheSurvivalist/res/world/map.txt");
        getTileImage();
    }

    public void getTileImage() {
        try {
            
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/LKananBawahPasir.png"));
            tile[0].collison = false;
            
            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/LKiriBawahPasir.png"));
            tile[1].collison = false;

            tile[2] = new Tile();
            tile[2].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/LKananAtasPasir.png"));
            tile[2].collison = false;
            
            tile[3] = new Tile();
            tile[3].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/LKiriAtasPasir.png"));
            tile[3].collison = false;
            
            tile[4] = new Tile();
            tile[4].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/KananPasir.png"));
            tile[4].collison = false;
            
            tile[5] = new Tile();
            tile[5].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/AtasPasir.png"));
            tile[5].collison = false;
            
            tile[6] = new Tile();
            tile[6].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/BawahPasir.png"));
            tile[6].collison = false;
            
            tile[7] = new Tile();
            tile[7].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/KiriPasir.png"));
            tile[7].collison = false;
            
            tile[8] = new Tile();
            tile[8].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[8].collison = false;
            
            tile[9] = new Tile();
            tile[9].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[9].collison = false;
            
            tile[10] = new Tile();
            tile[10].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[10].collison = false;
            
            tile[11] = new Tile();
            tile[11].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[11].collison = false;
            
            tile[12] = new Tile();
            tile[12].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[12].collison = false;

            tile[13] = new Tile();
            tile[13].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[13].collison = false;
            
            tile[14] = new Tile();
            tile[14].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[14].collison = false;

            tile[15] = new Tile();
            tile[15].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[15].collison = false;
            
            tile[16] = new Tile();
            tile[16].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/calm-water.png"));
            tile[16].collison = true;

            tile[17] = new Tile();
            tile[17].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/sand.png"));
            tile[17].collison = false;

            tile[18] = new Tile();
            tile[18].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[18].collison = false;

            tile[19] = new Tile();
            tile[19].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/bridge.png"));
            tile[19].collison = false;

            tile[20] = new Tile();
            tile[20].image = ImageIO.read(new File("ProjectTheSurvivalist/res/world/grass.png"));
            tile[20].collison = false;

        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void loadMap(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                System.err.println("File tidak ditemukan: " + path);
                return;
            }

            InputStream is = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            List<Integer> numbers = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                for (String token : tokens) {
                    if (!token.trim().isEmpty()) { // Pastikan token tidak kosong
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
                mapTile[row][col] = num;
                col++;
                if (col == gp.MAX_WORLD_COL) {
                    col = 0;
                    row++;
                }
            }

            System.out.println("Peta berhasil dimuat!");

        } catch (IOException e) {
            System.err.println("Terjadi kesalahan saat membaca file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

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
