package Object.Player;

import Object.Animal.Wolf;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.Unstackable.Buildings.CowCage;
import Object.Items.Unstackable.Buildings.KandangAyam;
import Object.Items.Unstackable.Buildings.PigCage;
import Object.Items.Unstackable.Buildings.SheepCage;

import java.util.ArrayList;
import java.util.Random;

public class Island {
    // public char[][] world;
    // public ArrayList<Buildings> buildings; // List of buildings on the island
    // Random rand = new Random();
    // private ArrayList<Animal> animals;
    // private static final int INITIAL_CHICKENS = 10;
    // private static final int INITIAL_PIGS = 5;
    // private static final int INITIAL_COWS = 5;
    // private static final int INITIAL_SHEEP = 5;
    
    // char[][] island1 = 
    // {
    //     {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    // };

    // char[][] island2 = 
    // {
    //     {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
    //     {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    // };

    // // public Island() {
    // //     // Constructor
    // //     this.world = randomizeIsland();
    // //     this.buildings = new ArrayList<>(); // Initialize the buildings list
    // //     this.animals = new ArrayList<>();
    //     // spawnChickens(INITIAL_CHICKENS);
    // // }

    // // public char[][] randomizeIsland() {
    // //     // Randomly choose between island1 and island2
    // //     if (Math.random() < 0.5) {
    // //         return island1;
    // //     } else {
    // //         return island2;
    // //     }
    // // }

    // public void showWorld(Player player, Wolf tiger) {
    //     // Display the world
    //     for (int i = 0; i < world.length; i++) {
    //         for (int j = 0; j < world[i].length; j++) {
    //             // System.out.print(world[i][j]);
    //             // // System.out.print(" " + world[i][j] + " ");
    //             if (Math.pow((j - tiger.x), 2) + Math.pow((i - tiger.y), 2) <= Math.pow(tiger.radius, 2)) {
    //                 System.out.print(" X ");
    //             } else {
    //                 System.out.print(" " + world[i][j] + " ");
    //             }
    //         }
    //         System.out.println();
    //     }
    // }


    // // private ArrayList<Animal> animals;
    // // private static final int INITIAL_CHICKENS = 10;
    // // public void spawnChicken() {
    // //     int randomX, randomY;
    // //     do {
    // //         randomX = rand.nextInt(world[0].length - 2) + 1;
    // //         randomY = rand.nextInt(world.length - 2) + 1;
    // //     } while (world[randomY][randomX] != ' ');
        
    // //     Chicken chicken = new Chicken("Chicken" , randomX, randomY);
    // //     animals.add(chicken);
    // //     world[randomY][randomX] = 'A';
    // // }
    // // public void spawnChickens(int count) {
    // //     for (int i = 0; i < count; i++) {
    // //         spawnChicken();
    // //     }
    // // }
    // // public Animal getAnimalAt(int x, int y) {
    // //     for (Animal animal : animals) {
    // //         if (animal.getX() == x && animal.getY() == y) {
    // //             return animal;
    // //         }
    // //     }
    // //     return null;
    // // }
    // // public void removeAnimal(Animal animal) {
    // //     animals.remove(animal);
    // //     world[animal.getY()][animal.getX()] = ' ';
    // // }
    // // public void placeAnimal(Animal animal, int x, int y) {
    // //     animal.setPosition(x, y);
    // //     animals.add(animal);
    // //     world[y][x] = getAnimalSymbol(animal);
    // // }
    // // public char getAnimalSymbol(Animal animal) {
    // //     if (animal instanceof Chicken) return 'A';
    // //     return '?';
    // // }


}
