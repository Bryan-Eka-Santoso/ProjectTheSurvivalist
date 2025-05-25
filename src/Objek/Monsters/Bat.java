package Objek.Monsters;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class Bat extends Monster {
    private int radius = 300; 
    public int actionLockEnemyNearby = 15; // Delay untuk aksi ketika ada musuh di dekatnya
    private int actionLockCounter = 0;
    private int actionMoveCounter = 0;
    private Rectangle upHitbox;
    private Rectangle downHitbox;
    private Rectangle leftHitbox;
    private Rectangle rightHitbox;
    private int actionMoveDelay;

    Random random = new Random();

    public Bat(String name, int worldX, int worldY, int speed, String direction, GamePanel gp) {
        super(name, worldX, worldY, speed, direction, gp);
        this.hp = 50;
        this.solidArea = new Rectangle(8, 16, 32, 32); 
        this.actionMoveDelay = random.nextInt(91) + 30;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        upHitbox = new Rectangle(2, 1, 26, 40);   
        downHitbox = new Rectangle(2, 1, 26, 40);   
        leftHitbox = new Rectangle(4, 4, 30, 25);   
        rightHitbox = new Rectangle(4, 4, 30, 25);   // Lebih lebar di kanan
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/up1.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/up2.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/down1.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/down2.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/left1.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/left2.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/right1.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/bat/right2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @Override
    public void update() {  
       if (isPlayerNearby()) {
            actionLockEnemyNearby = 10;
        } else {
            actionLockEnemyNearby = 15;
        }
        actionLockCounter++;
        if(actionLockCounter < actionLockEnemyNearby) {
            return; 
        }
        actionLockCounter = 0;
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
        
        gp.cCheck.checkMonsterPlayer(this);        // Check collision dengan player
        gp.cCheck.checkMonstersCollision(this);
        // gp.cCheck.animalCheckTile(this);    
        
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

            int randomMove = random.nextInt(2) + 1;
            switch(oldDirection) {
                case "up": 
                    if (randomMove == 1) {
                        newDirection = "right"; 
                    } else {
                        newDirection = "left"; 
                    }
                break;
                case "down":
                    if (randomMove == 1) {
                        newDirection = "right"; 
                    } else {
                        newDirection = "left"; 
                    }
                break;
                case "left": 
                    if (randomMove == 1) {
                        newDirection = "up"; 
                    } else {
                        newDirection = "down"; 
                    }
                break;
                case "right": 
                    if (randomMove == 1) {
                        newDirection = "up"; 
                    } else {
                        newDirection = "down"; 
                    }
                break;
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
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE, gp.TILE_SIZE, null);
            if(hp < 60) {
                double oneScale = (double)gp.TILE_SIZE/60;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
            }
        }
    }

    @Override
    public boolean isPlayerNearby() {
        // TODO Auto-generated method stub
        if (Math.pow((gp.player.worldX - this.worldX), 2) + Math.pow((gp.player.worldY - this.worldY), 2) <= Math.pow(radius, 2) && !collisionOn) {
            return true;
        } else {
            return false;
        }
    }

    public void chasePlayer() {
        if (isPlayerNearby()) {
            if (Math.abs(gp.player.worldX - this.worldX) > Math.abs(gp.player.worldY - this.worldY)) {
                if (gp.player.worldX > this.worldX) {
                    this.direction = "right";
                } else {
                    this.direction = "left";
                }
            } else {
                if (gp.player.worldY > this.worldY) {
                    this.direction = "down";
                } else {
                    this.direction = "up";
                }
            }
        }
    }
}
