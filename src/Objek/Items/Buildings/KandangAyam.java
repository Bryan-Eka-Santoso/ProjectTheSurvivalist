package Objek.Items.Buildings;

import java.util.ArrayList;
import java.util.Scanner;
import Objek.Controller.GamePanel;
import Objek.Animal.Chicken;
import Objek.Animal.TameAnimal;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KandangAyam extends Kandang{
   
    private static final int MAX_CAPACITY = 10;
    public ArrayList<Chicken> chickensInCage;
    Scanner scanner = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);

    public KandangAyam(GamePanel gp, int buildingMap) {
        super("Kandang Ayam", gp, buildingMap);
        this.chickensInCage = new ArrayList<>();
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/coop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Chicken)) {
            return false;
        }
        if(chickensInCage.size() >= MAX_CAPACITY) {
            return false;
        }

        Chicken chicken = (Chicken)animal;
        chickensInCage.add(chicken);
        return true;
    }
    

    public int getCurrentCapacity() {
        return chickensInCage.size();
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}