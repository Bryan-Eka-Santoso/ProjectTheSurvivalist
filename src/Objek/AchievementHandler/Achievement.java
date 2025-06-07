package Objek.AchievementHandler;

import java.awt.image.BufferedImage;

import Objek.Controller.GamePanel;

public class Achievement {
    public String name;
    public String description;
    public boolean isCompleted = false;
    public Condition condition;
    public BufferedImage img;
    
    public Achievement(String name, String description, Condition condition, BufferedImage img) {
        this.name = name;
        this.description = description;
        this.condition = condition;
        this.img = img;
    }

    public boolean checkCompleted(GamePanel gp) {
        if (!isCompleted && condition.check(gp)) {
            isCompleted = true;
            return true;
        }
        return false;
    }
}
