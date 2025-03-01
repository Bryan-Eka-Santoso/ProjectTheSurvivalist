package Object;

public class Player {
    public String name;
    int health, thirst, hunger, exp, level; // Player stats
    public Inventory inventory;

    public Player(String name) {
        this.name = name;
        this.health = 100;
        this.thirst = 100;
        this.hunger = 100;
        this.exp = 0;
        this.level = 1;
        this.inventory = new Inventory(10);
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
