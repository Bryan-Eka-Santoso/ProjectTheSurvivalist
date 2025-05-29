package Objek.Items.Buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Animal.Pig;
import Objek.Animal.TameAnimal;

public class PigCage extends Kandang {
    private static final int MAX_CAPACITY = 10;
    public ArrayList<Pig> pigsInCage;
    
    public PigCage(GamePanel gp, int buildingMap) {
        super("Pig Cage", gp, buildingMap);
        this.gp = gp;
        this.pigsInCage = new ArrayList<>();
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/pigpen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    
    @Override
    public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Pig)) {
            return false;
        }
        
        if(pigsInCage.size() >= MAX_CAPACITY) {
            return false;
        }

        Pig pig = (Pig)animal;
        pigsInCage.add(pig);
        return true;
    }

    @Override
    public int getCurrentCapacity() {
        return pigsInCage.size();
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}