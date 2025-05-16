package Object.Items.Unstackable.Buildings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

import Object.Controller.GamePanel;
import Object.Animal.Pig;
import Object.Animal.TameAnimal;
import Object.Player.Player;

public class PigCage extends Kandang {
    private static final int MAX_CAPACITY = 10;
    ArrayList<Pig> pigsInCage;
    Scanner scanner = new Scanner(System.in);
    Scanner scannerStr = new Scanner(System.in);
    
    public PigCage(GamePanel gp) {
        super("Pig Pen", gp );
        this.gp = gp;
        this.worldX = gp.player.worldX;
        this.worldY = gp.player.worldY;
        this.solidAreaDefaultX = solidArea.x;
        this.solidAreaDefaultY = solidArea.y;
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/pigpen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }  
    
   public boolean addAnimal(TameAnimal animal) {
        if(!(animal instanceof Pig)) {
            System.out.println("Only pigs can be added to pig pen!");
            return false;
        }
        Pig pig = (Pig)animal;
        if (pigsInCage.size() < MAX_CAPACITY) {
            System.out.print("Give a name to your pig: ");
            String name = scanner.nextLine();
            pig.setName(name);
            pigsInCage.add(pig);
            System.out.println(name + " has been added to the kandang!");
            return true;
        }
        System.out.println("Kandang is full! Cannot add more pigs.");
        return false;
    }

    public void interact(Player player,GamePanel gp) {
        System.out.println("\nKandang Status:");
        System.out.println("Current pigs: " + getCurrentCapacity() + "/" + MAX_CAPACITY);
        while (true) {
            System.out.println("\nKandang Menu:");
            System.out.println("1. Get Item");
            System.out.println("2. Breeding");
            System.out.println("3. List pigs");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    handleGetItem(player);
                break;
                case 2:
                    handleBreeding(island);
                    break;
                case 3:
                    listpigs();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void handleGetItem(Player player) {
       System.out.println("tidak menghasilakn apapun");//mboh babi ngehasilno opo
    }

   public void listpigs() {
        if (pigsInCage.isEmpty()) {
            System.out.println("No pigs in kandang!");
            return;
        }
        System.out.println("\npigs in Kandang:");
        for (int i = 0; i < pigsInCage.size(); i++) {
            System.out.println((i + 1) + ". " + pigsInCage.get(i).getName());
        }
    }

    public void handleBreeding(GamePanel gp) {
        if (pigsInCage.size() < 2) {
            System.out.println("Need at least 2 pigs to breed!");
            return;
        }
        if (pigsInCage.size() >= MAX_CAPACITY) {
            System.out.println("Kandang is full!");
            return;
        }
        boolean foundMale = false;
        boolean foundFemale = false;
        System.out.println("\nAvailable pigs:");
        for (int i = 0; i < pigsInCage.size(); i++) {
            System.out.println((i + 1) + ". " + pigsInCage.get(i).getName() + " (" + pigsInCage.get(i).getGender() + ")");
            if(pigsInCage.get(i).getGender().equals("Male")){
                foundMale = true;
            }else if(pigsInCage.get(i).getGender().equals("Female")){
                foundFemale = true;
            }
        }
        if(!foundMale || !foundFemale) {
            System.out.println("need at least 1 male and 1 female to breed!");
            return;
        }
        System.out.print("Select male pig (1-" + pigsInCage.size() + "): ");
        int firstChoice = scanner.nextInt() - 1;
        System.out.print("Select female pig (1-" + pigsInCage.size() + "): ");
        int secondChoice = scanner.nextInt() - 1;
       
        if (firstChoice < 0 || firstChoice >= pigsInCage.size() ||
            secondChoice < 0 || secondChoice >= pigsInCage.size() ||
            firstChoice == secondChoice) {
            System.out.println("Invalid selection!");
            return;
        }
        Pig baby = pigsInCage.get(firstChoice).breeding(pigsInCage.get(secondChoice));
        if (baby != null) {
            System.out.print("Give a name to the baby pig: ");
            String name = scannerStr.nextLine();
            baby.setName(name);
            pigsInCage.add(baby);
            System.out.println("New pig " + name + " born in kandang!");
            island.spawnPig(); 
        }
    }

    public int getCurrentCapacity() {
        return pigsInCage.size();
    }

    public int getMaxCapacity() {
        return MAX_CAPACITY;
    }
}