package Objek.Items.Buildings;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import Objek.Controller.GamePanel;
import Objek.Animal.Sheep;
import Objek.Animal.TameAnimal;

public class SheepCage extends Kandang {
    private static final int MAX_CAPACITY = 10;
    public ArrayList<Sheep> sheepsInCage;
    
    public SheepCage(GamePanel gp) {
        super("Sheep Cage", gp);
        this.gp = gp;
        this.sheepsInCage = new ArrayList<>();
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/sheepfold.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    
    @Override
    public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Sheep)) {
            return false;
        }
        
        if(sheepsInCage.size() >= MAX_CAPACITY) {
            return false;
        }

        Sheep sheep = (Sheep)animal;
        sheepsInCage.add(sheep);
        return true;
    }

    @Override
    public int getCurrentCapacity() {
        return sheepsInCage.size();
    }

    @Override
    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}