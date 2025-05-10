package Object.Animal;

import Object.Controller.GamePanel;

public class TameAnimal extends Animal {
    String name;
    int x,y;
    boolean isGrab,inCage;
    boolean readyBreeding;
    public String direction;
    
    public boolean isReadyBreeding() {
        return readyBreeding;
    }
    public void setReadyBreeding(boolean readyBreeding) {
        this.readyBreeding = readyBreeding;
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
    public TameAnimal(String name, int x, int y, int speed, String direction, GamePanel gp) {
        super(name, x, y, speed, direction, gp);
        this.isGrab = false;
        this.inCage = false;
        this.readyBreeding = true;
    }
    public void getItem(){

    }
    public void grab(){
        this.isGrab = true;
    }
    public void unGrab(int x, int y, boolean inCages) {
        this.isGrab = false;
        this.x = x;
        this.y = y;
        this.inCage = inCages;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
