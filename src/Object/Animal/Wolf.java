package Object.Animal;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import Object.GamePanel;
import Object.Player.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Wolf extends Animal {
    // private String name;
    public int x, y, radius, speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public boolean collisonOn = false;
    GamePanel gp;

    public Wolf(String name, int x, int y, String direction, GamePanel gp) {
        // this.name = name;
        super(name, x, y, direction);
        radius = 5; // Default radius for tiger
        speed = 7;
        this.gp = gp;
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidArea.width = 64;
        solidArea.height = 32;
        getImg();
    }

    public void getImg() {
        try {
            up1 = ImageIO.read(new File("res/animal/wolf/up1.png"));
            up2 = ImageIO.read(new File("res/animal/wolf/up2.png"));
            down1 = ImageIO.read(new File("res/animal/wolf/down1.png"));
            down2 = ImageIO.read(new File("res/animal/wolf/down2.png"));
            left1 = ImageIO.read(new File("res/animal/wolf/left1.png"));
            left2 = ImageIO.read(new File("res/animal/wolf/left2.png"));
            right1 = ImageIO.read(new File("res/animal/wolf/right1.png"));
            right2 = ImageIO.read(new File("res/animal/wolf/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) image = up1;
                if (spriteNum == 2) image = up2;
                break;
            case "down":
                if (spriteNum == 1) image = down1;
                if (spriteNum == 2) image = down2;
                break;
            case "left":
                if (spriteNum == 1) image = left1;
                if (spriteNum == 2) image = left2;
                break;
            case "right":
                if (spriteNum == 1) image = right1;
                if (spriteNum == 2) image = right2;
                break;
        }

        g2.drawImage(image, x, y, gp.TILE_SIZE * 2, gp.TILE_SIZE , null);
    }

    public void chasePrey(Player player) {
        if (isPreyNearby(player)) {
            player.island.world[y][x] = ' '; // Display tiger on the island
            if (x + 1 < player.worldX) {
                x++;
            } else if (x - 1 > player.worldX) {
                x--;
            }
            if (y + 1 < player.worldY) {
                y++;
            } else if (y - 1 > player.worldY) {
                y--;
            }
            player.island.world[y][x] = 'T'; // Display tiger on the island
            System.out.println("Chasing prey at (" + player.worldX + ", " + player.worldY + ")");
        } else {
            System.out.println("No prey nearby to chase.");
        }
    }

    public boolean isPreyNearby(Player player) {
        if (Math.pow((player.worldX - this.x), 2) + Math.pow((player.worldY - this.y), 2) <= Math.pow(radius, 2)) {
            return true;
        } else {
            return false;
        }
    }
    
} 
