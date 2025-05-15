package Objek.Items.Buildings;
// package Object.Items.Unstackable.Buildings;
// import java.awt.Rectangle;
// import java.util.ArrayList;
// import java.util.Scanner;

// import Object.GamePanel;
// import Object.Animal.Chicken;
// import Object.Player.Island;
// import Object.Player.Player;
// public class KandangAyam extends Kandang{
   
//     private static final int MAX_CAPACITY = 10;
//     ArrayList<Chicken> chickensInCage;
//     Scanner scanner = new Scanner(System.in);
//     Scanner scannerStr = new Scanner(System.in);

//     public KandangAyam(String name, int x, int y, GamePanel gp) {
//         super(name, x, y, gp, new Rectangle(0, 0, 0, 0));
//         this.chickensInCage = new ArrayList<Chicken>();
//     }   
//     public boolean addAnimal(Chicken chicken) {
//         if (chickensInCage.size() < MAX_CAPACITY) {
//             System.out.print("Give a name to your chicken: ");
//             String name = scanner.nextLine();
//             chicken.setName(name);
//             chickensInCage.add(chicken);
//             System.out.println(name + " has been added to the kandang!");
//             return true;
//         }
//         System.out.println("Kandang is full! Cannot add more chickens.");
//         return false;
//     }
//     public void interact(Player player, Island island) {
//         System.out.println("\nKandang Status:");
//         System.out.println("Current chickens: " + getCurrentCapacity() + "/" + MAX_CAPACITY);
//         while (true) {
//             System.out.println("\nKandang Menu:");
//             System.out.println("1. Get Item");
//             System.out.println("2. Breeding");
//             System.out.println("3. List Chickens");
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
//                     listChickens();
//                     break;
//                 case 4:
//                     return;
//                 default:
//                     System.out.println("Invalid option!");
//             }
//         }
//     }

//     private void handleGetItem(Player player) {
//         if (chickensInCage.isEmpty()) {
//             System.out.println("No chickens in kandang!");
//             return;
//         }

//         System.out.println("\nChickens that can lay eggs:");
//         for (int i = 0; i < chickensInCage.size(); i++) {
//             Chicken chicken = chickensInCage.get(i);
//             System.out.println((i + 1) + ". " + chicken.getName() + " (" + chicken.getGender() + ")"); 
//         }

//         System.out.print("Select chicken to get egg from (1-" + chickensInCage.size() + "): ");
//         int choice = scanner.nextInt() - 1;

//         if (choice >= 0 && choice < chickensInCage.size()) {
//             Chicken selectedChicken = chickensInCage.get(choice);
//             if (selectedChicken.isReadyGetItem()) {
//                 selectedChicken.getItem(player);
//                 selectedChicken.setReadyGetItem(false);
//             } else {
//                 System.out.println("This chicken cannot lay eggs right now!");
//             }
//         } else {
//             System.out.println("Invalid selection!");
//         }
//     }
//    public void listChickens() {
//         if (chickensInCage.isEmpty()) {
//             System.out.println("No chickens in kandang!");
//             return;
//         }
//         System.out.println("\nChickens in Kandang:");
//         for (int i = 0; i < chickensInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + chickensInCage.get(i).getName());
//         }
//     }
//     public void handleBreeding(Island island) {
//         if (chickensInCage.size() < 2) {
//             System.out.println("Need at least 2 chickens to breed!");
//             return;
//         }
//         if (chickensInCage.size() >= MAX_CAPACITY) {
//             System.out.println("Kandang is full!");
//             return;
//         }
//         boolean foundMale = false;
//         boolean foundFemale = false;
//         System.out.println("\nAvailable Chickens:");
//         for (int i = 0; i < chickensInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + chickensInCage.get(i).getName() + " (" + chickensInCage.get(i).getGender() + ")");
//             if(chickensInCage.get(i).getGender().equals("Male")){
//                 foundMale = true;
//             }else if(chickensInCage.get(i).getGender().equals("Female")){
//                 foundFemale = true;
//             }
//         }
//         if(!foundMale || !foundFemale) {
//             System.out.println("need at least 1 male and 1 female to breed!");
//             return;
//         }
//         System.out.print("Select male chicken (1-" + chickensInCage.size() + "): ");
//         int firstChoice = scanner.nextInt() - 1;
//         System.out.print("Select female chicken (1-" + chickensInCage.size() + "): ");
//         int secondChoice = scanner.nextInt() - 1;
       
//         if (firstChoice < 0 || firstChoice >= chickensInCage.size() ||
//             secondChoice < 0 || secondChoice >= chickensInCage.size() ||
//             firstChoice == secondChoice) {
//             System.out.println("Invalid selection!");
//             return;
//         }

       
//         Chicken baby = chickensInCage.get(firstChoice).breeding(chickensInCage.get(secondChoice), gp);
//         if (baby != null) {
//             System.out.print("Give a name to the baby chicken: ");
//             String name = scannerStr.nextLine();
//             baby.setName(name);
//             chickensInCage.add(baby);
//             System.out.println("New chicken " + name + " born in kandang!");
//             // island.spawnChicken(); 
//         }
//     }

//     public int getCurrentCapacity() {
//         return chickensInCage.size();
//     }

//     public int getMaxCapacity() {
//         return MAX_CAPACITY;
//     }
// }