package Objek.Items.Buildings;

import java.util.ArrayList;
import java.util.Scanner;
import Objek.Controller.GamePanel;
import Objek.Animal.Chicken;
import Objek.Animal.TameAnimal;
import Objek.Player.Player;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class KandangAyam extends Kandang{
   
    private static final int MAX_CAPACITY = 10;
    ArrayList<Chicken> chickensInCage;
    Scanner scanner = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);

    public KandangAyam(GamePanel gp) {
        super("Kandang Ayam", gp );
        this.gp = gp;
        this.chickensInCage = new ArrayList<>();
        this.worldX = gp.player.worldX;
        this.worldY = gp.player.worldY;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        this.chickensInCage = new ArrayList<>();
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/coop.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Chicken)) {
            System.out.println("Only chickens can be added to chicken coop!");
            return false;
        }
        Chicken chicken = (Chicken)animal;
        if (chickensInCage.size() < MAX_CAPACITY) {
            System.out.print("Give a name to your chicken: ");
            String name = scannerStr.nextLine();
            chicken.setName(name);
            chickensInCage.add(chicken);
            System.out.println(name + " has been added to the kandang!");
            return true;
        }
        System.out.println("Kandang is full! Cannot add more chickens.");
        return false;
    }
    @Override
    public void interact(Player player, GamePanel gp) {
        System.out.println("\nKandang Status:");
        System.out.println("Current chickens: " + getCurrentCapacity() + "/" + MAX_CAPACITY);
        while (true) {
            System.out.println("\nKandang Menu:");
            System.out.println("1. Get Item");
            System.out.println("2. Breeding");
            System.out.println("3. List Chickens");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleGetItem(player);
                break;
                case 2:
                    handleBreeding(gp);
                    break;
                case 3:
                    listChickens();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void handleGetItem(Player player) {
        if (chickensInCage.isEmpty()) {
            System.out.println("No chickens in kandang!");
            return;
        }
        System.out.println("\nChickens ready to collect eggs:");
        boolean found = false;
        for (int i = 0; i < chickensInCage.size(); i++) {
            Chicken chicken = chickensInCage.get(i);
            if (chicken.isReadyGetItem()) {
                System.out.println((i+1) + ". " + chicken.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No chickens ready to lay eggs!");
            return;
        }
        int choice;
        do{
            do{

                System.out.print("Choose chicken (0 to cancel): ");
                
                choice = scanner.nextInt() - 1;
            }while(choice < 0 || choice > chickensInCage.size());
            if (choice > 0 && choice <= chickensInCage.size()) {
                Chicken chicken = chickensInCage.get(choice-1);
                if (chicken.isReadyGetItem()) {
                    chicken.getItem(player);
                    System.out.println("Collected egg from " + chicken.getName());
                } else {
                    System.out.println("This chicken is not ready!");
                }
            }
        }while(choice != 0);
        
    }
   public void listChickens() {
        if (chickensInCage.isEmpty()) {
            System.out.println("No chickens in kandang!");
            return;
        }
        System.out.println("\nChickens in Kandang:");
        for (Chicken chicken : chickensInCage) {
            System.out.println("- " + chicken.getName() + " (" + chicken.getGender() + ")" +
            "\n  Breeding: " + (chicken.isReadyBreeding() ? "Ready" : "Not Ready") +
            "\n  Collect Egg: " + (chicken.isReadyGetItem() ? "Ready" : "Not Ready"));
        }
    }
    public void handleBreeding(GamePanel gp) {
        if (chickensInCage.size() < 2) {
            System.out.println("Need at least 2 chickens to breed!");
            return;
        }
        if (chickensInCage.size() >= MAX_CAPACITY) {
            System.out.println("Kandang is full!");
            return;
        }
        boolean foundMale = false;
        boolean foundFemale = false;
        System.out.println("\nAvailable Chickens:");
        for (int i = 0; i < chickensInCage.size(); i++) {
            System.out.println((i + 1) + ". " + chickensInCage.get(i).getName() + " (" + chickensInCage.get(i).getGender() + ")");
            if(chickensInCage.get(i).getGender().equals("Male")){
                foundMale = true;
            }else if(chickensInCage.get(i).getGender().equals("Female")){
                foundFemale = true;
            }
        }
        if(!foundMale || !foundFemale) {
            System.out.println("need at least 1 male and 1 female to breed!");
            return;
        }
        System.out.print("Select male chicken (1-" + chickensInCage.size() + "): ");
        int firstChoice = scanner.nextInt() - 1;
        System.out.print("Select female chicken (1-" + chickensInCage.size() + "): ");
        int secondChoice = scanner.nextInt() - 1;
       
        if (firstChoice < 0 || firstChoice >= chickensInCage.size() ||
            secondChoice < 0 || secondChoice >= chickensInCage.size() ||
            firstChoice == secondChoice) {
            System.out.println("Invalid selection!");
            return;
        }

       
        Chicken baby = chickensInCage.get(firstChoice).breeding(chickensInCage.get(secondChoice), gp);
        if (baby != null) {
            System.out.print("Give a name to the baby chicken: ");
            String name = scannerStr.nextLine();
            baby.setName(name);
            chickensInCage.add(baby);
            System.out.println("New chicken " + name + " born in kandang!");

        }
    }

    public int getCurrentCapacity() {
        return chickensInCage.size();
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}