import Object.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Player player = new Player("Player1");
        Consumable item1 = new Bread();
        Unconsumable item2 = new Sword(); 
        Scanner pick = new Scanner(System.in);
        int choice = 0;
        while(choice != 5) {
            System.out.println("======The Survivalists Test=======");
            System.out.println("Player name: " + player.name);
            System.out.println("1. Display stats");
            System.out.println("2. Display inventory");
            System.out.println("3. Use item");
            System.out.println("4. Add item to inventory");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            choice = pick.nextInt();
            switch(choice) {
                case 1:
                    player.displayStats(player);
                    break;
                case 2:
                    player.inventory.showInventory();
                    break;
                case 3:
                    System.out.println("Enter slot number to use item: ");
                    int slot = pick.nextInt();
                    player.inventory.use(slot - 1);
                    break;
                case 4:
                    System.out.println("Enter item to add to inventory: ");
                    String newItem = pick.next();
                    if(newItem.equals("Bread")) {
                        player.inventory.addItem(item1);
                    } else if(newItem.equals("Sword")) {
                        player.inventory.addItem(item2);
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
