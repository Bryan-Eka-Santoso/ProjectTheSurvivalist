package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Items.StackableItem.Foods.Carrot;
import Objek.Items.StackableItem.Foods.RawFoods.Potato;
import Objek.Items.StackableItem.Seeds.Seeds;

public class GardenPatch extends Buildings {
    public Item seed;
    public BufferedImage cropImg;
    public String phase; // "empty", "gphase1", "gphase2", "gphase3"
    public long[] phaseStartTimes; // When each phase started
    private BufferedImage[] phaseSprites;
    private static final long[] PHASE_DURATIONS = {8_000, 12_000}; // ms for each phase
    private int currentPhaseIndex;
    private boolean watered;
    private long lastWateredTime;

    public GardenPatch(GamePanel gp, int currentStack, int buildingMap) {
        super("Garden Patch", 15, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48, buildingMap);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/gardenpatch.png")); 
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        this.seed = null;
        this.phase = "empty";
        this.phaseSprites = new BufferedImage[2];
        try {
            phaseSprites[0] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/gphase1.png"));
        } catch (Exception e) {
            System.err.println("Error loading first phase image: " + e.getMessage());
        }
        this.phaseStartTimes = new long[]{0, 0};
        this.currentPhaseIndex = -1;
        this.watered = false;
        this.lastWateredTime = 0;
    }

    public void plant(Item seed) {
        if (phase.equals("empty")) {
            this.seed = seed;
            if (seed instanceof Seeds){
                try {
                    this.phaseSprites[1] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/wheatcrop1.png"));
                    this.cropImg = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/wheatcrop2.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (seed instanceof Carrot) {
                try {
                    this.phaseSprites[1] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/carrotcrop1.png"));
                    this.cropImg = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/carrotcrop2.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (seed instanceof Potato) {
                try {
                    this.phaseSprites[1] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/potatocrop1.png"));
                    this.cropImg = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/potatocrop2.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Unknown seed type: " + seed.getClass().getSimpleName());
            }
            if (this.cropImg != null) {
                this.phase = "gphase1";
                this.currentPhaseIndex = 0;
                this.phaseStartTimes[0] = 0; // Not started yet
                this.watered = false;
                this.lastWateredTime = 0;
                this.img = phaseSprites[0];
                System.out.println("Planted crops! Water to start growing!");
            }
        } else {
            System.out.println("Cannot plant: Garden Patch is not empty");
        }
    }

    public void water() {
        if (phase.equals("gphase1") || phase.equals("gphase2")) {
            if (!watered) {
                watered = true;
                lastWateredTime = System.currentTimeMillis();
                phaseStartTimes[currentPhaseIndex] = lastWateredTime;
                System.out.println("Watered! Growth for phase '" + phase + "' started.");
            } else {
                System.out.println("Already watered for this phase. Wait for growth.");
            }
        } else {    
            System.out.println("Cannot water: Garden Patch is not in a plantable state");
        }
    }

    public void updateGrowth() {
        if (phase.equals("empty") || cropImg == null || !watered) return;

        long now = System.currentTimeMillis();
        long elapsed = now - phaseStartTimes[currentPhaseIndex];

        if (currentPhaseIndex == 0 && elapsed >= PHASE_DURATIONS[0]) {
            // Move to gphase2, require watering again
            phase = "gphase2";
            img = phaseSprites[1];
            currentPhaseIndex = 1;
            watered = false;
            System.out.println("Phase changed to gphase2! Water again to continue growth.");
        } else if (currentPhaseIndex == 1 && elapsed >= PHASE_DURATIONS[1]) {
            // Move to gphase3 (fully grown)
            phase = "crops";
            img = this.cropImg;
            watered = false;
            System.out.println("Crops fully grown!");
        }
    }
}
