package Objek.Enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Controller.Sound;
import Objek.Player.Player;

public class Golem extends Monster { 
    Sound sound = new Sound();
    public int actionLockEnemyNearby = 15; // Delay untuk aksi ketika ada musuh di dekatnya
    private int actionLockCounter = 0;
    private int actionMoveCounter = 0;
    public int soundCounter = 0;
    private Rectangle upHitbox;
    private Rectangle downHitbox;
    private Rectangle leftHitbox;
    private Rectangle rightHitbox;
    private int actionMoveDelay;
    int spriteDisplaySize = gp.TILE_SIZE * 2;
    int originalSpriteSize = 64;
    double scaleFactor = (double)spriteDisplaySize / originalSpriteSize;

    Random random = new Random();
    public Golem(String name, int worldX, int worldY, int speed, String direction, GamePanel gp) {
        super(name, worldX, worldY, speed, direction, gp);
        setRandomDirection();
        this.hp = 300; // Set Golem's HP
        this.attack = 15;
        this.actionMoveDelay = random.nextInt(91) + 30;
        upHitbox = new Rectangle((int)(10*scaleFactor), 0, (int)(44*scaleFactor),(int)(59*scaleFactor) );   
        downHitbox = new Rectangle((int)(10*scaleFactor), 0, (int)(44*scaleFactor),(int)(59*scaleFactor) );   
        leftHitbox =  new Rectangle((int)(40*scaleFactor), (int)(2*scaleFactor), (int)(30*scaleFactor), (int)(59*scaleFactor));     
        rightHitbox = new Rectangle((int)(5*scaleFactor), (int)(2*scaleFactor), (int)(30*scaleFactor), (int)(59*scaleFactor));  
        this.solidArea = downHitbox;
        this.solidAreaDefaultX = this.solidArea.x;
        this.solidAreaDefaultY = this.solidArea.y;
        try {
            up1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/up1.png"));
            up2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/up2.png"));
            up3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/up3.png"));
            up4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/up4.png"));
            down1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/down1.png"));
            down2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/down2.png"));
            down3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/down3.png"));
            down4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/down4.png"));
            left1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/left1.png"));
            left2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/left2.png"));
            left3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/left3.png"));
            left4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/left4.png"));
            right1 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/right1.png"));
            right2 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/right2.png"));
            right3 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/right3.png"));
            right4 = ImageIO.read(new File("ProjectTheSurvivalist/res/monsters/golem/right4.png"));
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
       if (isPreyNearby(gp.player)) {
            actionLockEnemyNearby = 10;
            chasePlayer(gp.player);
            if (soundCounter == 0) playSE(13);
        } else {
            actionLockEnemyNearby = 15;
        }
        actionLockCounter++;
        soundCounter++;
        if (soundCounter >= 140) {
            soundCounter = 0;
        }
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
        isCollision(this);
        
        // Jika tidak ada collision, boleh bergerak
        if (!isPreyNearby(gp.player) || gp.player.isFrozen) {
            moveNormally();
        } else {
            moveTowardsPlayer();
        }
        
    }
    public void moveTowardsPlayer() {
        String nextDirection = chasePlayer(gp.player);
        direction = nextDirection;
        switch(nextDirection) {
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
        isCollision(this);
        if (!collisionOn) {
            this.direction = nextDirection;
            switch(this.direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        } else {
            if (nextDirection.equals("up")) {
                if (gp.player.worldX < this.worldX) {
                    nextDirection = "left";
                } else {
                    nextDirection = "right";
                }
            } else if (nextDirection.equals("down")) {
                if (gp.player.worldX < this.worldX) {
                    nextDirection = "left";
                } else {
                    nextDirection = "right";
                }
            } else if (nextDirection.equals("left")) {
                if (gp.player.worldY < this.worldY) {
                    nextDirection = "up";
                } else {
                    nextDirection = "down";
                }
            } else if (nextDirection.equals("right")) {
                if (gp.player.worldY < this.worldY) {
                    nextDirection = "up";
                } else {
                    nextDirection = "down";
                }
            }
            this.direction = nextDirection;
            switch (direction) {
                case "up":
                worldY -= speed;
                break;
                case "down":
                worldY += speed;
                    break;
                case "left":
                    worldX -= speed;
                    break;
                case "right":
                    worldX += speed;
                    break;
            }
        }
        actionMoveCounter++;
        spriteCounter++;
        if(spriteCounter > 0) {
            spriteNum++;
            if(spriteNum > 4) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    public void moveNormally() {
        if(!collisionOn) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
            actionMoveCounter++;
            if(actionMoveCounter >= actionMoveDelay) {
                if (!isPreyNearby(gp.player)) {
                    setRandomDirection();
                }
                actionMoveCounter = 0;
            }
        } else {
            String newDirection;
            String oldDirection = direction;
    
            switch(oldDirection) {
                case "up": newDirection = "down"; break;
                case "down": newDirection = "up"; break;
                case "left": newDirection = "right"; break;
                case "right": newDirection = "left"; break;
                default: newDirection = "down"; break;
            }
    
            this.direction = newDirection;
            this.actionMoveDelay = this.random.nextInt(91) + 30;
            switch(newDirection) {
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
            g2.drawImage(image, screenX, screenY, gp.TILE_SIZE*2, gp.TILE_SIZE*2, null);

            if(hp < 200) {
                double oneScale = (double)gp.TILE_SIZE/200;
                double hpBarValue = oneScale * hp;

                g2.setColor(new Color(35,35,35));
                g2.fillRect(screenX-1, screenY-16, gp.TILE_SIZE+2, 12);

                g2.setColor(new Color(255,0,30));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
            }
        }
    }
    public void isCollision(Monster monster) { 
        gp.cCheck.checkMonsterPlayer(monster);        // Check collision dengan player
        gp.cCheck.checkMonstersCollision(monster);
        gp.cCheck.monsterCheckTile(monster);
        gp.cCheck.monsterCheckOre(monster);
    }

    public boolean isPreyNearby(Player player) {
        int entityLeftX = player.worldX + player.solidArea.x;
        int entityRightX = player.worldX + player.solidArea.x + player.solidArea.width;
        int entityTopY = player.worldY + player.solidArea.y;
        int entityBottomY = player.worldY + player.solidArea.y + player.solidArea.height;

        int nextLeftX = entityLeftX;
        int nextRightX = entityRightX;
        int nextTopY = entityTopY;
        int nextBottomY = entityBottomY;

        switch(direction) {
            case "up": nextTopY -= speed; break;
            case "down": nextBottomY += speed; break;
            case "left": nextLeftX -= speed; break;
            case "right": nextRightX += speed; break;
        }

        int nextLeftCol = nextLeftX / gp.TILE_SIZE;
        int nextRightCol = nextRightX / gp.TILE_SIZE;
        int nextTopRow = nextTopY / gp.TILE_SIZE;
        int nextBottomRow = nextBottomY / gp.TILE_SIZE; 

        int validTile = 21;
        
        int tileNum1 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextTopRow];     // Top left
        int tileNum2 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextTopRow];    // Top right
        int tileNum3 = gp.tileM.mapTile[gp.currentMap][nextLeftCol][nextBottomRow];  // Bottom left
        int tileNum4 = gp.tileM.mapTile[gp.currentMap][nextRightCol][nextBottomRow]; // Bottom right
        
        if(tileNum1 != validTile || tileNum2 != validTile || 
        tileNum3 != validTile || tileNum4 != validTile) {
            return false; // Tidak bisa bergerak jika ada tile yang bukan air
        }

        if (player.health <= 0) {
            return false; // Tidak mengejar jika player sudah mati
        }

        if (Math.pow((player.worldX - this.worldX), 2) + Math.pow((player.worldY - this.worldY), 2) <= Math.pow(450, 2)) {
            if (gp.player.isSleeping || gp.player.health <= 0) {
                return false; // Tidak mengejar jika player sedang tidur
            }
            return true;
        } else {
            return false;
        }
    }

    public String chasePlayer(Player player) {
        String nextDirection = "down";
        if (Math.abs(player.worldX - this.worldX) > Math.abs(player.worldY - this.worldY)) {
            if (player.worldX > this.worldX) {
                nextDirection = "right";
            } else {
                nextDirection = "left";
            }
        } else {
            if (player.worldY > this.worldY) {
                nextDirection = "down";
            } else {
                nextDirection = "up";
            }
        }
        return nextDirection;
    }
    
    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }
    
}
