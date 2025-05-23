package Objek.Animal;

import java.awt.image.BufferedImage;

import Objek.Controller.GamePanel;
public class TameAnimal extends Animal {
    String name;
    int x,y;
    boolean isGrab,inCage;
    boolean readyBreeding;
    boolean readyGetItem;
    public String direction;
    int grabOffsetX = 0;
    int grabOffsetY = 0;
    int width;
    int height;
    public int hp;
    public String gender;
    
    
    public TameAnimal(String name, int x, int y, int speed, String direction, GamePanel gp) {
        super(name, x, y, speed, direction, gp);
        this.isGrab = false;
        this.inCage = false;
        this.width = 0;
        this.height = 0;
        this.readyBreeding = true;
        this.hp = 100;
        
    }
    
    public boolean isReadyBreeding() {
        return readyBreeding;
    }
    public void setReadyGetItem(boolean ready) {
        this.readyGetItem = ready;
    }
    public void setReadyBreeding(boolean readyBreeding) {
        this.readyBreeding = readyBreeding;
    }
    public boolean isReadyGetItem() {
        return readyGetItem;
    }
    public String getGender() {
        return gender;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }
    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }
    public boolean isGrab() {
        return isGrab;
    }
    public void setGrab(boolean isGrab) {
        this.isGrab = isGrab;
    }
    public boolean isInCage() {
        return inCage;
    }
    public void setInCage(boolean inCage) {
        this.inCage = inCage;
    }
    public void getItem(){

    }
    public void grab(){
        this.isGrab = true;
    }
    public void unGrab() {
        this.isGrab = false;
        
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public int getGrabOffsetX() {
        return grabOffsetX;
    }
    public int getGrabOffsetY() {
        return grabOffsetY;
    }
      public BufferedImage getDirectionalImage() {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
            default:
                image = down1;
        }

        return image;
    }
    
}
