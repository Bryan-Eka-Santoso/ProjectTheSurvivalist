package Object;
import Object.Player.CraftingTable;
import Object.Player.Island;
import Object.Player.Player;
import Object.Entity.Tiger;
import Object.Items.StackableItem.*;
import Object.Items.Unstackable.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game { 

    public static Scanner getString = new Scanner(System.in);
    public static Scanner getInt = new Scanner(System.in);
    
    public Game() {

        Island island = new Island();
        Player player = new Player("Player1", island);

        JLabel label = new JLabel("Status Bar");
        label.setBounds(10, 10, 100, 30);
        label.setForeground(Color.BLACK);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setBackground(Color.LIGHT_GRAY);
        label.setOpaque(true);
        label.setText(player.displayStats(player));

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel.setBounds(125, 550, 800, 100);

        JPanel statusBar = new JPanel();
        statusBar.setBackground(Color.LIGHT_GRAY);
        statusBar.setBounds(10, 10, 200, 150);
        statusBar.add(label);

        JPanel map = new JPanel();
        map.setBackground(Color.MAGENTA);
        map.setBounds(10, 170, 200, 370);

        JPanel maiPanel = new JPanel();
        maiPanel.setBackground(Color.BLUE);
        maiPanel.setBounds(0, 0, 1080, 720);
        maiPanel.setLayout(null);
        maiPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);
        frame.setTitle("The Survivalist Game");
        frame.setVisible(true);
        frame.setLayout(null);
        frame.setResizable(false);

        maiPanel.add(statusBar);
        maiPanel.add(map);
        maiPanel.add(inventoryPanel);
        frame.add(maiPanel);

    }

    // public void Run() {
        // Island island = new Island();
        // Player player = new Player("Player1", island);
    //     Tiger tiger = new Tiger("Tiger", 10, 7);
    //     player.island.world[tiger.y][tiger.x] = 'T'; // Display tiger on the island
    //     player.inventory.addItems(new Material("Wood", 10, 13));
    //     player.inventory.addItems(new Material("Wood", 10, 13));
    //     player.inventory.addItems(new Material("Stick", 10, 13));
    //     player.inventory.addItems(new Sword("Sword"));
    //     player.inventory.addItems(new Bread());
    //     CraftingTable craftingTable = new CraftingTable();
    //     craftingTable.showRecipes();
    //     player.inventory.showInventory();
    //     boolean isRunning = true;
    //     while (isRunning) {
    //         player.displayStats(player);
    //         player.island.showWorld(player, tiger);
    //         System.out.println("Use WASD to move, Q to quit.");
    //         String input = getString.nextLine().toLowerCase();
    //         switch (input) {
    //             case "w":
    //                 player.move(0, -1);
    //                 break;
    //             case "s":
    //                 player.move(0, 1);
    //                 break;
    //             case "a":
    //                 player.move(-1, 0);
    //                 break;
    //             case "d":
    //                 player.move(1, 0);
    //                 break;
    //             case "q":
    //                 isRunning = false;
    //                 break;
    //             case "i":
    //                 player.inventory.showInventory();
    //                 break;
    //             case "c":
    //                 craftingTable.showRecipes();
    //                 System.out.println("Enter the item name to craft: ");
    //                 String itemName = getString.nextLine();
    //                 craftingTable.craft(player, itemName);
    //                 break;
    //             case "e":
    //                 player.useItem();
    //                 break;
    //             case "h":
    //                 System.out.println("Enter the index of the item to use: ");
    //                 int index = getInt.nextInt();
    //                 player.selectItem(index - 1);
    //             default:
    //                 System.out.println("Invalid input! Use WASD to move or Q to quit.");
    //                 break;
    //         }
    //         tiger.chasePrey(player); // Tiger chases the player if nearby
    //     }

    // }
}
