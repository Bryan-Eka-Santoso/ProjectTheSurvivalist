package Objek.AchievementHandler;

import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;

public class AchievementManager {
    
    public LinkedHashMap<String, Achievement> achievements = new LinkedHashMap<>();

    public AchievementManager() {
        // Initialize achievements here
        BufferedImage img1 = null;
        try {
            img1 = ImageIO.read(getClass().getResource("/res/plant/guavatree.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "The Usual",
            "Cut down your first tree.", 
            gp -> gp.player.totalTreesCut >= 1,
            img1
            ));
        BufferedImage img4 = null;
        try {
            img4 = ImageIO.read(getClass().getResource("/res/Items/Equipments/lightweightaxe.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Lumberjack",
            "Cut down 100 trees.",
            gp -> gp.player.totalTreesCut >= 100,
            img4));
        BufferedImage img2 = null;
        try {
            img2 = ImageIO.read(getClass().getResource("/res/Items/Buildings/craftingTable.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "The Unforgettable",
            "Craft your first crafting table.",
            gp -> gp.player.madeCraftingTable,
            img2));
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(getClass().getResource("/res/Items/Equipments/icepickaxe.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "I Yearn for the Mines!", 
            "Craft your first pickaxe", 
            gp -> gp.player.madePickaxe,
            img3));
        BufferedImage img5 = null;
        try {
            img5 = ImageIO.read(getClass().getResource("/res/plant/bush2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Bushwhacker",
            "Destroy 50 bushes.",
            gp -> gp.player.totalBushesCut >= 50,
            img5));
        BufferedImage img6 = null;
        try {
            img6 = ImageIO.read(getClass().getResource("/res/plant/bush4c.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Mowing the Lawn",
            "Destroy 100 bushes.",
            gp -> gp.player.totalBushesCut >= 100,
            img6));
        BufferedImage img7 = null;
        try {
            img7 = ImageIO.read(getClass().getResource("/res/Items/Material/stone.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Ore Collector",
            "Mine 50 various stones.",
            gp -> gp.player.totalOresMined >= 50,
            img7));
        BufferedImage img8 = null;
        try {
            img8 = ImageIO.read(getClass().getResource("/res/Items/Seeds/seeds.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Novice Farmer",
            "Plant and grow your first seed.",
            gp -> gp.player.totalPlantsPlanted >= 1,
            img8));
        BufferedImage img9 = null;
        try {
            img9 = ImageIO.read(getClass().getResource("/res/Items/Seeds/guavaseeds.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Green Thumb",
            "Plant and grow 10 seeds.",
            gp -> gp.player.totalPlantsPlanted >= 10,
            img9));
        BufferedImage img10 = null;
        try {
            img10 = ImageIO.read(getClass().getResource("/res/Items/Equipments/wateringcan.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Forest Guardian",
            "Plant and grow 100 seeds.",
            gp -> gp.player.totalPlantsPlanted >= 100,
            img10));
        BufferedImage img11 = null;
        try {
            img11 = ImageIO.read(getClass().getResource("/res/Items/Armor/metalchestplate2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Suit Up",
            "Craft your first piece of armor.",
            gp -> gp.player.hasCraftedArmor,
            img11));
        BufferedImage img12 = null;
        try {
            img12 = ImageIO.read(getClass().getResource("/res/Items/Equipments/metalsword.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Monster Hunter",
            "Defeat your first monster.",
            gp -> gp.player.totalMonstersKilled >= 1,
            img12));
        BufferedImage img13 = null;
        try {
            img13 = ImageIO.read(getClass().getResource("/res/Items/Equipments/goldsword.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Master Hunter",
            "Defeat 50 mobs.",
            gp -> gp.player.totalMonstersKilled + gp.player.totalAnimalsKilled >= 50,
            img13));
        BufferedImage img17 = null;
        try {
            img17 = ImageIO.read(getClass().getResource("/res/Items/Material/gem.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Bang Bang!",
            "Buy your first legendary item.",
            gp -> gp.player.hasLegendaryItem,
            img17));
        BufferedImage img19 = null;
        try {
            img19 = ImageIO.read(getClass().getResource("/res/Items/Equipments/fishingRod.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Caught Ya!",
            "Catch your first fish.",
            gp -> gp.player.totalFishCaught >= 1,
            img19));
        BufferedImage img20 = null;
        try {
            img20 = ImageIO.read(getClass().getResource("/res/fish/golden/right1.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Gotta Catch'em All",
            "Catch 25 fish.",
            gp -> gp.player.totalFishCaught >= 25,
            img20));
        BufferedImage img21 = null;
        try {
            img21 = ImageIO.read(getClass().getResource("/res/animal/chicken/right3.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Little Bryan",
            "Name a chicken \"Bryan\".",
            gp -> gp.player.hasNamedChicken,
            img21));
        BufferedImage img22 = null;
        try {
            img22 = ImageIO.read(getClass().getResource("/res/Items/Buildings/smelter.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Master Chef",
            "Cook 10 raw foods.",
            gp -> gp.player.totalMaterialsCooked >= 10,
            img22));
        BufferedImage img23 = null;
        try {
            img23 = ImageIO.read(getClass().getResource("/res/Items/Bucket/cleansedwater.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Better Safe Than Sorry",
            "Cleanse a bucket of water.",
            gp -> gp.player.hasPurifiedWater,
            img23));
        BufferedImage img24 = null;
        try {
            img24 = ImageIO.read(getClass().getResource("/res/Items/Equipments/wintercrown.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Freestyle Dulu Ni Bos",
            "Use winter crown to dodge 3 wolves.",
            gp -> gp.player.hasDodgedWolves,
            img24));
        BufferedImage img18 = null;
        try {
            img18 = ImageIO.read(getClass().getResource("/res/Items/Armor/cursedhelmet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Burning Soul",
            "Kill 10 animals/monsters with cursed helmet.",
            gp -> gp.player.totalCursedHelmetKills >= 10,
            img18));
        BufferedImage img14 = null;
        try {
            img14 = ImageIO.read(getClass().getResource("/res/Items/Armor/rapidboots.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
         addAchievement(new Achievement(
            "First Steps",
            "Survive for one day.",
            gp -> gp.player.daysAlive >= 1,
            img14));
        BufferedImage img15 = null;
        try {
            img15 = ImageIO.read(getClass().getResource("/res/Items/Armor/guardianhelmet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Undefeated",
            "Survive for 10 days without dying.",
            gp -> gp.player.maxDaysAlive >= 10,
            img15));
        BufferedImage img16 = null;
        try {
            img16 = ImageIO.read(getClass().getResource("/res/Items/Equipments/immortality.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        addAchievement(new Achievement(
            "Hardcore Professional",
            "Survive for 100 days without dying.",
            gp -> gp.player.maxDaysAlive >= 100,
            img16));

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
