package Objek.Animal;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;

public class Pig extends TameAnimal{
    Random random = new Random();
    boolean readyGetItem;
    private Rectangle upHitbox;
    private Rectangle downHitbox;
    private Rectangle leftHitbox;
    private Rectangle rightHitbox;
    private static final int PIG_WIDTH = 128;
    private static final int PIG_HEIGHT = 128;
    public Pig(String name, int x, int y, GamePanel gp) {
        super(name, x, y, 15, "down", gp);
        setRandomDirection();
        this.actionMoveDelay = random.nextInt(91) + 30;
        upHitbox = new Rectangle(50, 45, 30, 63);    // Lebih sempit di atas
        downHitbox = new Rectangle(50, 45, 30, 63); // Lebih sempit di bawah
        leftHitbox = new Rectangle(33, 60, 57,28 );  // Lebih sempit di kiri
        rightHitbox = new Rectangle(33, 60, 57,28 );
        this.solidArea = downHitbox;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        readyBreeding = true;
        readyGetItem = true;
        this.grabOffsetX = 0;
        this.grabOffsetY = -40;
        this.hp = 100;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/up3.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/up4.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/down3.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/down4.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/left3.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/left4.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/right3.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/animal/pig/right4.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getWidth() {
        return PIG_WIDTH;
    }
    @Override
    public int getHeight() {
        return PIG_HEIGHT;
    }
    private void setRandomDirection() {
        String newDirection= null;
        String oldDirection = this.direction;
        do{
            int random = this.random.nextInt(4);
    
            switch(random) {
                case 0: newDirection = "up"; break;
                case 1: newDirection = "down"; break;
                case 2: newDirection = "left"; break;
                case 3: newDirection = "right"; break;
               
            }
        } while (newDirection.equals(oldDirection));
        this.direction = newDirection;
        this.actionMoveDelay = this.random.nextInt(91) + 30;
        gp.player.collisionOn = false;
    }
    private int actionMoveCounter = 0;
    private int actionMoveDelay;
    private int speed = 8; 
    @Override
    public void update() {
        if(direction == null) {
            direction = "down"; 
        }
        switch(direction) {
            case "up": 
                solidArea = upHitbox;
                break;
            case "down": 
                solidArea = downHitbox;
                break;
            case "left": 
                solidArea = leftHitbox;
                break;
            case "right": 
                solidArea = rightHitbox;
                break;
        }

        collisionOn = false;
        gp.cCheck.animalCheckTile(this);     // Check collision dengan tile
        gp.cCheck.animalCheckObject(this);   // Check collision dengan object/plant
        gp.cCheck.checkPlayer(this);        // Check collision dengan player
        gp.cCheck.checkAnimalCollision(this);
        gp.cCheck.animalCheckBuildings(this);
        
        // Jika tidak ada collision, boleh bergerak
        if(!collisionOn) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
            actionMoveCounter++;
            if(actionMoveCounter >= actionMoveDelay) {
                setRandomDirection();
                actionMoveCounter = 0;
            }
        } else {
          
            String newDirection;
            String oldDirection = this.direction;

            switch(oldDirection) {
                case "up": newDirection = "down"; break;
                case "down": newDirection = "up"; break;
                case "left": newDirection = "right"; break;
                case "right": newDirection = "left"; break;
                default: newDirection = "down"; break;
            }
            this.direction = newDirection;
            this.actionMoveDelay = this.random.nextInt(91)+30;
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
            actionMoveCounter++;
        }
       
        spriteCounter++;
        if(spriteCounter > 0) {
            spriteNum++;
            if(spriteNum > 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        
        switch(direction) {
            case "up":
                if(spriteNum == 1) image = up1;
                if(spriteNum == 2) image = up2;
                if(spriteNum == 3) image = up3;
                if(spriteNum == 4) image = up4;
                break;
            case "down":
                if(spriteNum == 1) image = down1;
                if(spriteNum == 2) image = down2;
                if(spriteNum == 3) image = down3;
                if(spriteNum == 4) image = down4;
                break;
            case "left":
                if(spriteNum == 1) image = left1;
                if(spriteNum == 2) image = left2;
                if(spriteNum == 3) image = left3;
                if(spriteNum == 4) image = left4;
                break;
            case "right":
                if(spriteNum == 1) image = right1;
                if(spriteNum == 2) image = right2;
                if(spriteNum == 3) image = right3;
                if(spriteNum == 4) image = right4;
                break;
        }

        int screenX = worldX - gp.player.worldX + gp.player.SCREEN_X;
        int screenY = worldY - gp.player.worldY + gp.player.SCREEN_Y;

        if(worldX + gp.TILE_SIZE > gp.player.worldX - gp.player.SCREEN_X && 
           worldX - gp.TILE_SIZE < gp.player.worldX + gp.player.SCREEN_X && 
           worldY + gp.TILE_SIZE > gp.player.worldY - gp.player.SCREEN_Y && 
           worldY - gp.TILE_SIZE < gp.player.worldY + gp.player.SCREEN_Y) {
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*3, gp.TILE_SIZE*3, null);
            if(hp < 100) {
                double oneScale = (double)gp.TILE_SIZE*3/100;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE*3+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
               
            }
        }
    }

    public Pig breeding(Pig pasangan, GamePanel gp) {
        if (readyBreeding) {
            if (pasangan.isReadyBreeding()) {
                System.out.println("Breeding pig with " + pasangan.getName());
                Pig babypig = new Pig("Baby pig", this.x, this.y, gp);
                this.readyBreeding = false;
                pasangan.setReadyBreeding(false);
                return babypig;
            } else {
                System.out.println("Partner is not ready to breed.");
                return null;
            }
        } else {
            System.out.println("Not ready to breed yet.");
            return null;
        }
    }

    // public Pig(String name, int x, int y, String gender) {
    //     super(name, x, y, gender);
    // }
    
    // public Pig breeding(Pig pasangan) {
    //     if(readyBreeding) {
    //         if(pasangan.isReadyBreeding()) {
    //             if(!this.getGender().equals(pasangan.getGender())) {
    //                 System.out.println("Breeding pig with " + pasangan.getName());
    //                 String babyGender = (Math.random() < 0.5) ? "Male" : "Female";
                    
    //                 this.readyBreeding = false;
    //                 pasangan.setReadyBreeding(false);
    //                 Pig babyPig = new Pig("Baby Pig", this.x, this.y, babyGender);
    //                 System.out.println("Baby pig is born!");
    //                 return babyPig;
    //             } else {
    //                 System.out.println("Cannot breed pigs of the same gender!");
    //                 return null;
    //             }
    //         }
    //     }
    //     System.out.println("Not ready to breed yet.");
    //     return null;
    // }
}

