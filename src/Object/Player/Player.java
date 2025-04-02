package Object.Player;

public class Player {
    public String name;
    public int x, y; // Player position
    int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;
    public Island island;

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
        this.island.world[this.x][this.y] = 'P'; // Set player position on the island
    }

    public void move(int dx, int dy) {
        // Move the player in the specified direction
        if (island.world[y + dy][x + dx] != '#') { // Check if the next position is not a wall
            island.world[y][x] = ' '; // Clear the current position
            x += dx; // Update player position
            y += dy; // Update player position
            island.world[y][x] = 'P'; // Set new player position on the island
        } else {
            System.out.println("You can't move there!"); // Invalid move
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
