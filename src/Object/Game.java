package Object;
import Object.Player.CraftingTable;
import Object.Player.Island;
import Object.Player.Player;
import Object.Items.StackableItem.*;
import Object.Items.Unstackable.*;
import java.util.*;

public class Game {

    public static Scanner getString = new Scanner(System.in);
    public static Scanner getInt = new Scanner(System.in);
    
    public void Run() {
        Island island = new Island();
        Player player = new Player("Player1", island);
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Stick", 10, 13));
        player.inventory.addItems(new Sword("Sword"));
        player.inventory.addItems(new Bread());
        CraftingTable craftingTable = new CraftingTable();
        craftingTable.showRecipes();
        player.inventory.showInventory();
        boolean isRunning = true;
        while (isRunning) {
            player.displayStats(player);
            player.island.showWorld();
            System.out.println("Use WASD to move, Q to quit.");
            String input = getString.nextLine().toLowerCase();
            switch (input) {
                case "w":
                    player.move(0, -1);
                    break;
                case "s":
                    player.move(0, 1);
                    break;
                case "a":
                    player.move(-1, 0);
                    break;
                case "d":
                    player.move(1, 0);
                    break;
                case "q":
                    isRunning = false;
                    break;
                case "i":
                    player.inventory.showInventory();
                    break;
                case "c":
                    craftingTable.showRecipes();
                    System.out.println("Enter the item name to craft: ");
                    String itemName = getString.nextLine();
                    craftingTable.craft(player, itemName);
                    break;
                case "e":
                    player.useItem();
                    break;
                case "h":
                    System.out.println("Enter the index of the item to use: ");
                    int index = getInt.nextInt();
                    player.selectItem(index - 1);
                default:
                    System.out.println("Invalid input! Use WASD to move or Q to quit.");
            }
        }

    }
}
