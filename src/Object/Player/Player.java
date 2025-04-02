package Object.Player;
import Object.Items.Unstackable.Buildings.Chest;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Item;

public class Player {
    public String name;
    public int x, y; // Player position
    public int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;
    public Island island;
    public int itemIndex; // Index of the selected item in the inventory
    Item selectedItem; // Currently selected item

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
    }

    public void move(int dx, int dy) {
        if (island.world[y + dy][x + dx] != '#') { 
            island.world[y][x] = ' '; 
            x += dx; 
            y += dy; 
            island.world[y][x] = 'P'; 
        } else {
            System.out.println("You can't move there!"); 
        }
    }

    public void selectItem(int index) {
        selectedItem = inventory.slots[index]; 
        this.itemIndex = index;
    }

    public void useItem() {
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

    public void displayStats(Player player) {
        System.out.println("========Player stats======");
        System.out.println("Player name: " + player.name);
        System.out.println("Health: " + player.health);
        System.out.println("Thirst: " + player.thirst);
        System.out.println("Hunger: " + player.hunger);
        System.out.println("Experience: " + player.exp);
        System.out.println("Level: " + player.level);
    }
}
