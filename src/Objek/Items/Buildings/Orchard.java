package Objek.Items.Buildings;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import Objek.Controller.GamePanel;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Plant.*;

public class Orchard extends Buildings {
    public Seeds seed;
    public Tree tree;
    public BufferedImage treeImg;
    public String phase; // e.g., "seed", "sprout", "grown", "tree"
    public long plantedTime;
    private BufferedImage[] phaseSprites; // Or BufferedImage[]
    // Increase durations to make changes more noticeable
    private static final long[] PHASE_DURATIONS = {10_000, 20_000}; // 10 seconds for seed, 20 seconds for sprout

    public Orchard(GamePanel gp, int currentStack) {
        super("Orchard", 15, currentStack, gp, new java.awt.Rectangle(9, 9, 30, 30), 48, 48);
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/orchard.png"));
            System.out.println("Orchard base image loaded successfully");
        } catch (Exception e) {
            System.err.println("Error loading orchard image: " + e.getMessage());
            e.printStackTrace();
        }
        this.seed = null;
        this.tree = null;
        this.treeImg = null;
        this.phase = "empty";
        this.phaseSprites = new BufferedImage[2]; // Adjust size as needed
        
        // Load your sprites here (placeholder for now)
        try {
            phaseSprites[0] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/phase1.png"));
            System.out.println("Phase 1 image loaded successfully");
        } catch (Exception e) {
            System.err.println("Error loading phase1 image: " + e.getMessage());
            e.printStackTrace();
        }
        try {
            phaseSprites[1] = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/phase2.png"));
            System.out.println("Phase 2 image loaded successfully");
        } catch (Exception e) {
            System.err.println("Error loading phase2 image: " + e.getMessage());
            e.printStackTrace();
        }
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
                this.plantedTime = System.currentTimeMillis();
                System.out.println("Successful! Seed will soon grow into a " + this.tree.getClass().getSimpleName());
            } else {
                System.out.println("Failed to plant: Unknown seed type");
            }
        } else {
            System.out.println("Cannot plant: Orchard is not empty");
        }
    }

    public void water(){
        if (phase.equals("seed") || phase.equals("sprout")) {
            System.out.println("Watering the orchard...");
            
        } else {
            System.out.println("Cannot water: Orchard is not in a plantable state");
        }
    }

    public void updateGrowth() {
        if (phase.equals("empty") || tree == null) return;

        long elapsed = System.currentTimeMillis() - plantedTime;

        if (elapsed < PHASE_DURATIONS[0]) {
            // Seed phase
            phase = "seed";
            img = phaseSprites[0];
        } else if (elapsed < PHASE_DURATIONS[0] + PHASE_DURATIONS[1]) {
            // Sprout phase
            phase = "sprout";
            img = phaseSprites[1];
        } else {
            // Fully grown tree
            phase = "tree";
            this.img = this.tree.image;
            // You may want to cache this image instead of loading every frame!
        }
        System.out.println("Current phase: " + phase);

    }

    // public void drawTree(Graphics2D g2) {
    //     // Draw orchard base
    //     if (img != null) {
    //         g2.drawImage(img, worldX, worldY, null);
    //     }
        
    //     if (phase.equals("empty")) {
    //         // Just show the empty orchard
    //     } else if (phase.equals("seed") || phase.equals("sprout")) {
    //         // Draw growth phase sprite
    //         if (currentPhaseIndex < phaseSprites.length && phaseSprites[currentPhaseIndex] != null) {
    //             g2.drawImage(phaseSprites[currentPhaseIndex], worldX, worldY, null);
    //         } else {
    //             System.out.println("Warning: Missing phase sprite for index " + currentPhaseIndex);
    //         }
    //     } else if (phase.equals("tree")) {
    //         // Draw the mature tree on top of the orchard
    //         if (tree != null && tree.image != null) {
    //             // Adjust tree position as needed - may need tuning
    //             g2.drawImage(tree.image, worldX, worldY - 24, null);
    //         } else {
    //             System.out.println("Warning: Tree image is null");
    //         }
    //     }
    // }
}