package Object.Player;
import Object.Entity.Animal;
import Object.Entity.Chicken;
import Object.Entity.Cow;
import Object.Entity.Pig;
import Object.Entity.Sheep;
import Object.Entity.Tiger;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.Unstackable.Buildings.CowCage;
import Object.Items.Unstackable.Buildings.KandangAyam;
import Object.Items.Unstackable.Buildings.PigCage;
import Object.Items.Unstackable.Buildings.SheepCage;

import java.util.ArrayList;
import java.util.Random;

public class Island {
    public char[][] world;
    public ArrayList<Buildings> buildings; // List of buildings on the island
    Random rand = new Random();
    private ArrayList<Animal> animals;
    private static final int INITIAL_CHICKENS = 10;
    private static final int INITIAL_PIGS = 5;
    private static final int INITIAL_COWS = 5;
    private static final int INITIAL_SHEEP = 5;
    
    char[][] island1 = 
    {
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    char[][] island2 = 
    {
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'},
        {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}
    };

    public Island() {
        // Constructor
        this.world = randomizeIsland();
        this.buildings = new ArrayList<>(); // Initialize the buildings list
        this.animals = new ArrayList<>();
        spawnChickens(INITIAL_CHICKENS);
        spawnPigs(INITIAL_PIGS);
        spawnCows(INITIAL_COWS);
        spawnSheep(INITIAL_SHEEP);
    }

    public char[][] randomizeIsland() {
        // Randomly choose between island1 and island2
        if (Math.random() < 0.5) {
            return island1;
        } else {
            return island2;
        }
    }

    public void showWorld(Player player, Tiger tiger) {
        // Display the world
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[i].length; j++) {
                // System.out.print(world[i][j]);
                // // System.out.print(" " + world[i][j] + " ");
                if (Math.pow((j - tiger.x), 2) + Math.pow((i - tiger.y), 2) <= Math.pow(tiger.radius, 2)) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" " + world[i][j] + " ");
                }
            }
            System.out.println();
        }
    }


    public void spawnPig() {
        int randomX, randomY;
        do {
            randomX = rand.nextInt(world[0].length - 2) + 1;
            randomY = rand.nextInt(world.length - 2) + 1;
        } while (world[randomY][randomX] != ' ');
        String gender = (Math.random() < 0.5) ? "Male" : "Female";
        Pig pig = new Pig("Pig", randomX, randomY, gender);
        animals.add(pig);
        world[randomY][randomX] = 'P';
    }
    public void spawnCow() {
        int randomX, randomY;
        do {
            randomX = rand.nextInt(world[0].length - 2) + 1;
            randomY = rand.nextInt(world.length - 2) + 1;
        } while (world[randomY][randomX] != ' ');
        String gender = (Math.random() < 0.5) ? "Male" : "Female";
        Cow cow = new Cow("Cow", randomX, randomY, gender);
        animals.add(cow);
        world[randomY][randomX] = 'C';
    }
    public void spawnChicken() {
        int randomX, randomY;
        do {
            randomX = rand.nextInt(world[0].length - 2) + 1;
            randomY = rand.nextInt(world.length - 2) + 1;
        } while (world[randomY][randomX] != ' ');
        String gender = (Math.random() < 0.5) ? "Male" : "Female";
        Chicken chicken = new Chicken("Chicken" , randomX, randomY,gender);
        animals.add(chicken);
        world[randomY][randomX] = 'A';
    }
    public void spawnSheep() {
        int randomX, randomY;
        do {
            randomX = rand.nextInt(world[0].length - 2) + 1;
            randomY = rand.nextInt(world.length - 2) + 1;
        } while (world[randomY][randomX] != ' ');
        String gender = (Math.random() < 0.5) ? "Male" : "Female";
        Sheep sheep = new Sheep("Sheep", randomX, randomY, gender);
        animals.add(sheep);
        world[randomY][randomX] = 'S';
    }
    public void spawnPigs(int count) {
        for (int i = 0; i < count; i++) {
            spawnPig();
        }
    }
    public void spawnCows(int count) {
        for (int i = 0; i < count; i++) {
            spawnCow();
        }
    }
    public void spawnSheep(int count) {
        for (int i = 0; i < count; i++) {
            spawnSheep();
        }
    }
    public void spawnChickens(int count) {
        for (int i = 0; i < count; i++) {
            spawnChicken();
        }
    }
    public Animal getAnimalAt(int x, int y) {
        for (Animal animal : animals) {
            if (animal.getX() == x && animal.getY() == y) {
                return animal;
            }
        }
        return null;
    }
    public void removeAnimal(Animal animal) {
        animals.remove(animal);
        world[animal.getY()][animal.getX()] = ' ';
    }
    public void placeAnimal(Animal animal, int x, int y) {
        animal.setPosition(x, y);
        animals.add(animal);
        world[y][x] = getAnimalSymbol(animal);
    }
    public char getAnimalSymbol(Animal animal) {
        if (animal instanceof Chicken) return 'A';
         if (animal instanceof Pig) return 'P';
        if (animal instanceof Cow) return 'C';
        if (animal instanceof Sheep) return 'S';
        return '?';
    }
      public char getBuildingSymbol(Buildings building) {
        if (building instanceof KandangAyam) return '=';
        if (building instanceof PigCage) return '*';
        if (building instanceof CowCage) return '@';
        if (building instanceof SheepCage) return '-';
        return '?';
    }


}
