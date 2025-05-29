package Objek.Items.Buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Animal.Cow;
import Objek.Animal.TameAnimal;

public class CowCage extends Kandang {
    private static final int MAX_CAPACITY = 10;
    public ArrayList<Cow> cowsInCage;
    
    public CowCage(GamePanel gp, int buildingMap) {
        super("Cow Cage", gp, buildingMap);
        this.gp = gp;
        this.cowsInCage = new ArrayList<>();
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/cowshed.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    
    @Override
    public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Cow)) {
            return false;
        }
        
        if(cowsInCage.size() >= MAX_CAPACITY) {
            return false;
        }

        Cow cow = (Cow)animal;
        cowsInCage.add(cow);
        return true;
    }

    @Override
    public int getCurrentCapacity() {
        return cowsInCage.size();
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}