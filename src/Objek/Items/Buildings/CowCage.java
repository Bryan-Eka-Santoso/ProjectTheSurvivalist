// package Objek.Items.Buildings;

// import java.io.File;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Scanner;
// import javax.imageio.ImageIO;
// import Objek.Animal.TameAnimal;
// import Objek.Controller.GamePanel;
// import Objek.Animal.Cow;
// import Objek.Player.Player;

// public class CowCage extends Kandang {
//     private static final int MAX_CAPACITY = 10;
//     ArrayList<Cow> cowsInCage;
//     Scanner scanner = new Scanner(System.in);
//     Scanner scannerStr = new Scanner(System.in);
//     public CowCage(GamePanel gp) {
//         super("cowshed", gp );
//         this.gp = gp;
//         this.worldX = gp.player.worldX;
//         this.worldY = gp.player.worldY;
//         this.solidAreaDefaultX = solidArea.x;
//         this.solidAreaDefaultY = solidArea.y;
//         try {
//             this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/cowshed.png"));
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }  
//     public boolean addAnimal(TameAnimal animal) {
//         if(!(animal instanceof Cow)) {
//             System.out.println("Only cows can be added to cow cage!");
//             return false;
//         }
//         Cow cow = (Cow)animal;
//         if (cowsInCage.size() < MAX_CAPACITY) {
//             System.out.print("Give a name to your cow: ");
//             String name = scanner.nextLine();
//             cow.setName(name);
//             cowsInCage.add(cow);
//             System.out.println(name + " has been added to the kandang!");
//             return true;
//         }
//         System.out.println("Kandang is full! Cannot add more cows.");
//         return false;
//     }

//     public void interact(Player player, GamePanel gp) {
//         System.out.println("\nKandang Status:");
//         System.out.println("Current cows: " + getCurrentCapacity() + "/" + MAX_CAPACITY);
//         while (true) {
//             System.out.println("\nKandang Menu:");
//             System.out.println("1. Get Item");
//             System.out.println("2. Breeding");
//             System.out.println("3. List cows");
//             System.out.println("4. Exit");
//             System.out.print("Choose option: ");
            
//             int choice = scanner.nextInt();
//             switch (choice) {
//                 case 1:
//                     handleGetItem(player);
//                 break;
//                 case 2:
//                     handleBreeding(island);
//                     break;
//                 case 3:
//                     listcows();
//                     break;
//                 case 4:
//                     return;
//                 default:
//                     System.out.println("Invalid option!");
//             }
//         }
//     }

//     private void handleGetItem(Player player) {
//         if (cowsInCage.isEmpty()) {
//             System.out.println("No cows in kandang!");
//             return;
//         }

//         System.out.println("\ncows that can lay milks:");
//         for (int i = 0; i < cowsInCage.size(); i++) {
//             Cow cow = cowsInCage.get(i);
//             System.out.println((i + 1) + ". " + cow.getName() + " (" + cow.getGender() + ")"); 
//         }

//         System.out.print("Select cow to get milk from (1-" + cowsInCage.size() + "): ");
//         int choice = scanner.nextInt() - 1;

//         if (choice >= 0 && choice < cowsInCage.size()) {
//             Cow selectedcow = cowsInCage.get(choice);
//             if (selectedcow.isReadyGetItem()) {
//                 selectedcow.getItem(player);
//                 selectedcow.setReadyGetItem(false);
//             } else {
//                 System.out.println("This cow cannot lay milks right now!");
//             }
//         } else {
//             System.out.println("Invalid selection!");
//         }
//     }

//    public void listcows() {
//         if (cowsInCage.isEmpty()) {
//             System.out.println("No cows in kandang!");
//             return;
//         }
//         System.out.println("\ncows in Kandang:");
//         for (int i = 0; i < cowsInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + cowsInCage.get(i).getName());
//         }
//     }

//     public void handleBreeding(GamePanel gp) {
//         if (cowsInCage.size() < 2) {
//             System.out.println("Need at least 2 cows to breed!");
//             return;
//         }
//         if (cowsInCage.size() >= MAX_CAPACITY) {
//             System.out.println("Kandang is full!");
//             return;
//         }
//         boolean foundMale = false;
//         boolean foundFemale = false;
//         System.out.println("\nAvailable cows:");
//         for (int i = 0; i < cowsInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + cowsInCage.get(i).getName() + " (" + cowsInCage.get(i).getGender() + ")");
//             if(cowsInCage.get(i).getGender().equals("Male")){
//                 foundMale = true;
//             }else if(cowsInCage.get(i).getGender().equals("Female")){
//                 foundFemale = true;
//             }
//         }
//         if(!foundMale || !foundFemale) {
//             System.out.println("need at least 1 male and 1 female to breed!");
//             return;
//         }
//         System.out.print("Select male cow (1-" + cowsInCage.size() + "): ");
//         int firstChoice = scanner.nextInt() - 1;
//         System.out.print("Select female cow (1-" + cowsInCage.size() + "): ");
//         int secondChoice = scanner.nextInt() - 1;
       
//         if (firstChoice < 0 || firstChoice >= cowsInCage.size() ||
//             secondChoice < 0 || secondChoice >= cowsInCage.size() ||
//             firstChoice == secondChoice) {
//             System.out.println("Invalid selection!");
//             return;
//         }

//         Cow baby = cowsInCage.get(firstChoice).breeding(cowsInCage.get(secondChoice));
//         if (baby != null) {
//             System.out.print("Give a name to the baby cow: ");
//             String name = scannerStr.nextLine();
//             baby.setName(name);
//             cowsInCage.add(baby);
//             System.out.println("New cow " + name + " born in kandang!");
//             island.spawnCow(); 
//         }
//     }

//     public int getCurrentCapacity() {
//         return cowsInCage.size();
//     }

//     public int getMaxCapacity() {
//         return MAX_CAPACITY;
//     }
// }