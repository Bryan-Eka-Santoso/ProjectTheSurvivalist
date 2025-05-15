package Objek.Items.Buildings;
// package Object.Items.Unstackable.Buildings;
// import java.util.ArrayList;
// import java.util.Scanner;
// import Object.Entity.Sheep;
// import Object.Player.Island;
// import Object.Player.Player;

// public class SheepCage extends Kandang {
//     private static final int MAX_CAPACITY = 10;
//     ArrayList<Sheep> sheepsInCage;
//     Scanner scanner = new Scanner(System.in);
//     Scanner scannerStr = new Scanner(System.in);
    
//     public SheepCage(String name, int x, int y) {
//         super(name, x, y);
//         this.sheepsInCage = new ArrayList<>();
//     }

//      public boolean addAnimal(Sheep sheep) {
//         if (sheepsInCage.size() < MAX_CAPACITY) {
//             System.out.print("Give a name to your sheep: ");
//             String name = scanner.nextLine();
//             sheep.setName(name);
//             sheepsInCage.add(sheep);
//             System.out.println(name + " has been added to the kandang!");
//             return true;
//         }
//         System.out.println("Kandang is full! Cannot add more sheeps.");
//         return false;
//     }

//     public void interact(Player player, Island island) {
//         System.out.println("\nKandang Status:");
//         System.out.println("Current sheeps: " + getCurrentCapacity() + "/" + MAX_CAPACITY);
//         while (true) {
//             System.out.println("\nKandang Menu:");
//             System.out.println("1. Get Item");
//             System.out.println("2. Breeding");
//             System.out.println("3. List sheeps");
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
//                     listsheeps();
//                     break;
//                 case 4:
//                     return;
//                 default:
//                     System.out.println("Invalid option!");
//             }
//         }
//     }

//     private void handleGetItem(Player player) {
//         if (sheepsInCage.isEmpty()) {
//             System.out.println("No sheeps in kandang!");
//             return;
//         }
//         System.out.println("\nsheeps that can lay wools:");
//         for (int i = 0; i < sheepsInCage.size(); i++) {
//             Sheep sheep = sheepsInCage.get(i);
//             System.out.println((i + 1) + ". " + sheep.getName() + " (" + sheep.getGender() + ")"); 
//         }

//         System.out.print("Select sheep to get wool from (1-" + sheepsInCage.size() + "): ");
//         int choice = scanner.nextInt() - 1;

//         if (choice >= 0 && choice < sheepsInCage.size()) {
//             Sheep selectedsheep = sheepsInCage.get(choice);
//             if (selectedsheep.isReadyGetItem()) {
//                 selectedsheep.getItem(player);
//                 selectedsheep.setReadyGetItem(false);
//             } else {
//                 System.out.println("This sheep cannot lay wools right now!");
//             }
//         } else {
//             System.out.println("Invalid selection!");
//         }
//     }

//    public void listsheeps() {
//         if (sheepsInCage.isEmpty()) {
//             System.out.println("No sheeps in kandang!");
//             return;
//         }
//         System.out.println("\nsheeps in Kandang:");
//         for (int i = 0; i < sheepsInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + sheepsInCage.get(i).getName());
//         }
//     }

//     public void handleBreeding(Island island) {
//         if (sheepsInCage.size() < 2) {
//             System.out.println("Need at least 2 sheeps to breed!");
//             return;
//         }
//         if (sheepsInCage.size() >= MAX_CAPACITY) {
//             System.out.println("Kandang is full!");
//             return;
//         }
//         boolean foundMale = false;
//         boolean foundFemale = false;
//         System.out.println("\nAvailable sheeps:");
//         for (int i = 0; i < sheepsInCage.size(); i++) {
//             System.out.println((i + 1) + ". " + sheepsInCage.get(i).getName() + " (" + sheepsInCage.get(i).getGender() + ")");
//             if(sheepsInCage.get(i).getGender().equals("Male")){
//                 foundMale = true;
//             }else if(sheepsInCage.get(i).getGender().equals("Female")){
//                 foundFemale = true;
//             }
//         }
//         if(!foundMale || !foundFemale) {
//             System.out.println("need at least 1 male and 1 female to breed!");
//             return;
//         }
//         System.out.print("Select male sheep (1-" + sheepsInCage.size() + "): ");
//         int firstChoice = scanner.nextInt() - 1;
//         System.out.print("Select female sheep (1-" + sheepsInCage.size() + "): ");
//         int secondChoice = scanner.nextInt() - 1;
       
//         if (firstChoice < 0 || firstChoice >= sheepsInCage.size() ||
//             secondChoice < 0 || secondChoice >= sheepsInCage.size() ||
//             firstChoice == secondChoice) {
//             System.out.println("Invalid selection!");
//             return;
//         }
//         Sheep baby = sheepsInCage.get(firstChoice).breeding(sheepsInCage.get(secondChoice));
//         if (baby != null) {
//             System.out.print("Give a name to the baby sheep: ");
//             String name = scannerStr.nextLine();
//             baby.setName(name);
//             sheepsInCage.add(baby);
//             System.out.println("New sheep " + name + " born in kandang!");
//             island.spawnSheep(); 
//         }
//     }

//     public int getCurrentCapacity() {
//         return sheepsInCage.size();
//     }

//     public int getMaxCapacity() {
//         return MAX_CAPACITY;
//     }
// }
