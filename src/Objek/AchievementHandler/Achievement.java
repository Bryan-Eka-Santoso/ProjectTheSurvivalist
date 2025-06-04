package Objek.AchievementHandler;

import Objek.Controller.GamePanel;

public class Achievement {
    public String name;
    public String description;
    public boolean isCompleted = false;
    public Condition condition;
    
    public Achievement(String name, String description, Condition condition) {
        this.name = name;
        this.description = description;
        this.condition = condition;
    }

    public boolean checkCompleted(GamePanel gp) {
        if (!isCompleted && condition.check(gp)) {
            isCompleted = true;
            return true;
        }
        return false;
    }
}
