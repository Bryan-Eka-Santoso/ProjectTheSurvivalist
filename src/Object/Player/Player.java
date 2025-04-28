package Object.Player;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.Unstackable.Buildings.CowCage;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Item;
import Object.Items.Unstackable.Buildings.Kandang;
import Object.Items.Unstackable.Buildings.KandangAyam;
import Object.Items.Unstackable.Buildings.PigCage;
import Object.Items.Unstackable.Buildings.SheepCage;

import java.util.ArrayList;

import Object.Entity.Animal;
import Object.Entity.Chicken;
import Object.Entity.Cow;
import Object.Entity.Pig;
import Object.Entity.Sheep;

public class Player {
    public String name;
    public int x, y; // Player position
    public int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;
    public Island island;
    public int itemIndex; // Index of the selected item in the inventory
    Item selectedItem; // Currently selected item
    public String lastMove;
    public Animal grabbedAnimal;
    ArrayList<Kandang> kandang= new ArrayList<>(); // List of cages owned by the player



    public Player(String name, Island island) {
        this.island = island;
        this.name = name;
        this.x = 5;
        this.y = 5;
        this.health = 100;
        this.thirst = 100;
        this.hunger = 100;
        this.exp = 0;
        this.level = 1;
        this.inventory = new Inventory(10);
        this.island.world[this.x][this.y] = 'P'; 
        this.grabbedAnimal= null; 
        this.kandang = new ArrayList<>(); 
    }

   public void move(int dx, int dy) {
        if (island.world[y + dy][x + dx] == ' ' ) {
            

            if (dx > 0) lastMove = "d";
            else if (dx < 0) lastMove = "a";
            else if (dy > 0) lastMove = "s";
            else if (dy < 0) lastMove = "w";

            island.world[y][x] = ' ';
            x += dx;
            y += dy;
            island.world[y][x] = 'P';
        }
    }
    public boolean isAnimal(char tile) {
        return tile == 'A' || tile == 'P' || tile == 'C' || tile == 'S';
    }

    public void handleGrabAction() {
        if (grabbedAnimal == null) {
            Animal nearbyAnimal = findNearbyAnimal();
            if (nearbyAnimal != null) {
                grabAnimal(nearbyAnimal);
            }
        } else {  
            unGrabAnimal();
        }
    }

    public Animal findNearbyAnimal() {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int checkX = x + dx;
                int checkY = y + dy;
                if (isAnimal(island.world[checkY][checkX])) {
                    return island.getAnimalAt(checkX, checkY);
                }
            }
        }
        return null;
    }

    public void grabAnimal(Animal animal) {
        if (selectedItem != null) {
            System.out.println("Cannot grab animal while holding an item!");
            return;
        }
        grabbedAnimal = animal;
        island.removeAnimal(animal);
        System.out.println("Grabbed " + animal.getName());
    }
    public void displayStatus() {
        displayStats(this);
        if (grabbedAnimal != null) {
            System.out.println("Currently holding: " + grabbedAnimal.getName());
        }
    }
    public void unGrabAnimal() {
        Kandang nearbyKandang = findNearbyKandang();
        int newX = x, newY = y;
        switch (lastMove) {
            case "w": newY--; break;
            case "s": newY++; break;
            case "a": newX--; break;
            case "d": newX++; break;
        }
        if (nearbyKandang != null) {
            
            boolean isCorrectCage = (grabbedAnimal instanceof Chicken && nearbyKandang instanceof KandangAyam) ||
            (grabbedAnimal instanceof Pig && nearbyKandang instanceof PigCage) ||
            (grabbedAnimal instanceof Cow && nearbyKandang instanceof CowCage) ||
            (grabbedAnimal instanceof Sheep && nearbyKandang instanceof SheepCage);
            
            if (!isCorrectCage) {
                System.out.println("Wrong cage type for this animal!");
                return;
            }

            if (nearbyKandang.getCurrentCapacity() < nearbyKandang.getMaxCapacity()) {
                boolean added = false;
                if (grabbedAnimal instanceof Chicken) {
                    added = ((KandangAyam)nearbyKandang).addAnimal((Chicken)grabbedAnimal);
                } else if (grabbedAnimal instanceof Pig ) {
                    added = ((PigCage)nearbyKandang).addAnimal((Pig)grabbedAnimal);
                } else if (grabbedAnimal instanceof Cow ) {
                    added = ((CowCage)nearbyKandang).addAnimal((Cow)grabbedAnimal);
                } else if (grabbedAnimal instanceof Sheep ) {
                    added = ((SheepCage)nearbyKandang).addAnimal((Sheep)grabbedAnimal);
                }
                
                if (added) {
                    System.out.println("Added " + grabbedAnimal.getName() + " to kandang");
                    // Spawn new animal of same type
                    if (grabbedAnimal instanceof Chicken) island.spawnChicken();
                    else if (grabbedAnimal instanceof Pig) island.spawnPig();
                    else if (grabbedAnimal instanceof Cow) island.spawnCow();
                    else if (grabbedAnimal instanceof Sheep) island.spawnSheep();
                    grabbedAnimal = null;
                    return;
                }
            } else {
                System.out.println("Kandang is full!");
                return;
            }
        }else{
            if (island.world[newY][newX] == ' ') {
                placeAnimalNearby();
                System.out.println("Released " + grabbedAnimal.getName());
                grabbedAnimal = null;
                return;
            } else {
                System.out.println("Cannot release animal here - space is occupied!");
                return;
            }
        }
    }

    public void placeAnimalNearby() {
        int newX = x, newY = y;
        switch (lastMove) {
            case "w": newY--; break;
            case "s": newY++; break;
            case "a": newX--; break;
            case "d": newX++; break;
        }
        
        if (island.world[newY][newX] == ' ') {
            island.placeAnimal(grabbedAnimal, newX, newY);
        }
    }
    public Kandang findNearbyKandang() {
        for (Buildings building : island.buildings) {
            if (building instanceof Kandang) {
                if (Math.abs(building.getX() - x) <= 1 && Math.abs(building.getY() - y) <= 1) {
                    return (Kandang) building;
                }
            }
        }
        return null;
    }

    public boolean isHoldingAnimal() {
        return grabbedAnimal != null;
    }

    public void selectItem(int index) {
        selectedItem = inventory.slots[index]; 
        this.itemIndex = index;
    }

    public void useItem() {
        if (isHoldingAnimal()) {
            System.out.println("Cannot use items while holding an animal!");
            return;
        }
        selectItem(itemIndex);
        if (selectedItem != null && selectedItem.name != null) {
            if (selectedItem instanceof Material) {
                Material material = (Material) selectedItem;
                System.out.println("Using material: " + material.name);
            } else if (selectedItem instanceof Buildings) {
                Buildings building = (Buildings) selectedItem;
                System.out.println("Using building: " + building.name);
            } else if (selectedItem instanceof Food) {
                Food food = (Food) selectedItem;
                System.out.println("Using food: " + food.name);
                food.eat(this); 
                inventory.removeItem(selectedItem.name); 
            } else {
                System.out.println("Unknown item type!"); 
            }
        } else {
            System.out.println("No item selected!"); 
        }
    }

    public String displayStats(Player player) {
        return  "<html> ======Player stats====== <br>" +
                "Player name: " + player.name + "<br>" +
                "Health: " + player.health + "<br>" +
                "Thirst: " + player.thirst + "<br>" +
                "Hunger: " + player.hunger + "<br>" +
                "Experience: " + player.exp + "<br>" +
                "Level: " + player.level + "</html>";
    }
    
}
