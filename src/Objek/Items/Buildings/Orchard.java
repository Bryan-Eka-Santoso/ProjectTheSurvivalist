package Objek.Items.Buildings;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Plant.Trees.GuavaTree;
import Objek.Plant.Trees.MangoTree;
import Objek.Plant.Trees.PalmTree;
import Objek.Plant.Trees.Tree;

public class Orchard extends Buildings {
    public Seeds seed;
    public Tree tree;
    public BufferedImage treeImg;
    public String phase; // "empty", "seed", "sprout", "tree"
    public long[] phaseStartTimes; // When each phase started
    private BufferedImage[] phaseSprites;
    private static final long[] PHASE_DURATIONS = {10_000, 20_000}; // ms for each phase
    private int currentPhaseIndex;
    private boolean watered; // Has the current phase been watered?
    private long lastWateredTime;

    public Orchard(GamePanel gp, int currentStack, int buildingMap) {
        super("Orchard", 15, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48, buildingMap);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/orchard.png"));
        } catch (Exception e) {
            System.err.println("Error loading orchard image: " + e.getMessage());
        }
        this.seed = null;
        this.tree = null;
        this.treeImg = null;
        this.phase = "empty";
        this.phaseSprites = new BufferedImage[2];
        try {
            phaseSprites[0] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/phase1.png"));
        } catch (Exception e) {
            System.err.println("Error loading phase1 image: " + e.getMessage());
        }
        try {
            phaseSprites[1] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/phase2.png"));
        } catch (Exception e) {
            System.err.println("Error loading phase2 image: " + e.getMessage());
        }
        this.phaseStartTimes = new long[]{0, 0};
        this.currentPhaseIndex = -1;
        this.watered = false;
        this.lastWateredTime = 0;
    }

    public void plant(Seeds seed) {
        if (phase.equals("empty")) {
            if (seed instanceof GuavaSeeds) {
                this.seed = (GuavaSeeds) seed;
                this.tree = new GuavaTree(0, 0, this.gp);
            } else if (seed instanceof CoconutSeeds) {
                this.seed = (CoconutSeeds) seed;
                this.tree = new PalmTree(0, 0, this.gp);
            } else if (seed instanceof MangoSeeds) {
                this.seed = (MangoSeeds) seed;
                this.tree = new MangoTree(0, 0, this.gp);
            }
            if (this.seed != null) {
                this.phase = "seed";
                this.currentPhaseIndex = 0;
                this.phaseStartTimes[0] = 0; // Not started yet
                this.watered = false;
                this.lastWateredTime = 0;
                this.img = phaseSprites[0];
                System.out.println("Planted " + this.tree.getClass().getSimpleName() + ". Water to start growing!");
            }
        } else {
            System.out.println("Cannot plant: Orchard is not empty");
        }
    }

    public void water() {
        if (phase.equals("seed") || phase.equals("sprout")) {
            if (!watered) {
                watered = true;
                lastWateredTime = System.currentTimeMillis();
                phaseStartTimes[currentPhaseIndex] = lastWateredTime;
                System.out.println("Watered! Growth for phase '" + phase + "' started.");
            } else {
                System.out.println("Already watered for this phase. Wait for growth.");
            }
        } else {    
            System.out.println("Cannot water: Orchard is not in a plantable state");
        }
    }

    public void updateGrowth() {
        if (phase.equals("empty") || tree == null || !watered) return;

        long now = System.currentTimeMillis();
        long elapsed = now - phaseStartTimes[currentPhaseIndex];

        if (currentPhaseIndex == 0 && elapsed >= PHASE_DURATIONS[0]) {
            // Move to sprout phase, but require watering again
            phase = "sprout";
            img = phaseSprites[1];
            currentPhaseIndex = 1;
            watered = false;
            System.out.println("Phase changed to sprout! Water again to continue growth.");
        } else if (currentPhaseIndex == 1 && elapsed >= PHASE_DURATIONS[1]) {
            // Move to tree phase
            phase = "tree";
            img = this.tree.image;
            watered = false;
            System.out.println("Tree fully grown!");
        }
    }
}