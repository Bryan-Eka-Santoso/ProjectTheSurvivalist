package Objek.AchievementHandler;

import java.util.HashMap;
import Objek.Controller.GamePanel;

public class AchievementManager {
    public HashMap<String, Achievement> achievements = new HashMap<>();

    public AchievementManager() {
        // Initialize achievements here
        addAchievement(new Achievement(
            "The Usual",
            "Cut down your first Tree.", 
            gp -> gp.player.totalTreesCut > 1));
        addAchievement(new Achievement(
            "The Unforgettable",
            "Craft your first Crafting Table.",
            gp -> gp.player.madeCraftingTable));
        addAchievement(new Achievement(
            "I Yearn for the Mines!", 
            "Craft your first pickaxe", 
            gp -> gp.player.madePickaxe));
        addAchievement(new Achievement(
            "Lumberjack",
            "Cut down 100 trees.",
            gp -> gp.player.totalTreesCut >= 100));
        addAchievement(new Achievement(
            "Bushwhacker",
            "Destroy 50 bushes.",
            gp -> gp.player.totalBushesCut >= 50));
        addAchievement(new Achievement(
            "Bushwhacker Pro",
            "Destroy 100 bushes.",
            gp -> gp.player.totalBushesCut >= 100));
        addAchievement(new Achievement(
            "Ore Collector",
            "Mine 50 various stones.",
            gp -> gp.player.totalOresMined >= 50));
        addAchievement(new Achievement(
            "Novice Farmer",
            "Plant your first crop.",
            gp -> gp.player.totalPlantsPlanted >= 1));
        addAchievement(new Achievement(
            "Green Thumb",
            "Plant 10 crops.",
            gp -> gp.player.totalPlantsPlanted >= 10));
        addAchievement(new Achievement(
            "Forest Guardian",
            "Plant 100 crops.",
            gp -> gp.player.totalPlantsPlanted >= 100));
        addAchievement(new Achievement(
            "Blacksmith",
            "Craft your first iron item.",
            gp -> gp.player.hasForgedIronItem));
        addAchievement(new Achievement(
            "Monster Hunter",
            "Defeat your first monster.",
            gp -> gp.player.totalMonstersKilled >= 1));
        addAchievement(new Achievement(
            "Master Hunter",
            "Defeat 50 monsters.",
            gp -> gp.player.totalMonstersKilled >= 50));
         addAchievement(new Achievement(
            "First Steps",
            "Survive for one day.",
            gp -> gp.player.daysAlive >= 1));
        addAchievement(new Achievement(
            "Undefeated",
            "Survive for 10 days without dying.",
            gp -> gp.player.maxDaysAlive >= 10));
        addAchievement(new Achievement(
            "Legend",
            "Survive for 100 days without dying.",
            gp -> gp.player.maxDaysAlive >= 100));

    }

    public void addAchievement(Achievement a) {
        achievements.put(a.name, a);
    }

    public void checkAll(GamePanel gp) {
        for (Achievement a : achievements.values()) {
            if (a.checkCompleted(gp)) {
                gp.ui.showAchievementNotification(a);
            }
        }
    }
}
