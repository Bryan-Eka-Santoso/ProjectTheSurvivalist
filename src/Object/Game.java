package Object;
import Object.Player.CraftingTable;
import Object.Player.Island;
import Object.Player.Player;
import Object.Entity.Tiger;
import Object.Items.StackableItem.*;
import Object.Items.Unstackable.*;
import Object.Items.Unstackable.Buildings.KandangAyam;
import Object.Items.Unstackable.Buildings.CowCage;
import Object.Items.Unstackable.Buildings.PigCage;
import Object.Items.Unstackable.Buildings.SheepCage;
import Object.Items.Unstackable.Buildings.Kandang;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game extends JPanel implements KeyListener { 

    public static Scanner getString = new Scanner(System.in);
    public static Scanner getInt = new Scanner(System.in);

    char key = ' ';
    final int HEIGHT = 1080;
    final int WIDTH = 2160;
    final int SPEED = 50;
    int posX = 0;
    int posY = 0;

    // public Game() {
        
    //     Island island = new Island();
    //     Player player = new Player("Player1", island);
        
    //     JLabel label = new JLabel("Status Bar");
    //     label.setBounds(10, 10, 100, 30);
    //     label.setForeground(Color.BLACK);
    //     label.setFont(new Font("Arial", Font.BOLD, 16));
    //     label.setHorizontalAlignment(SwingConstants.CENTER);
    //     label.setVerticalAlignment(SwingConstants.CENTER);
    //     label.setBackground(Color.LIGHT_GRAY);
    //     label.setOpaque(true);
    //     label.setText(player.displayStats(player));
        
    //     JPanel inventoryPanel = new JPanel();
    //     inventoryPanel.setBackground(Color.LIGHT_GRAY);
    //     inventoryPanel.setBounds(125, 550, 800, 100);

    //     JPanel statusBar = new JPanel();
    //     statusBar.setBackground(Color.LIGHT_GRAY);
    //     statusBar.setBounds(10, 10, 200, 150);
    //     statusBar.add(label);
        
    //     JPanel inventory = new JPanel();
    //     inventory.setBackground(Color.YELLOW);
    //     inventory.setBounds(0, 0, 1080, 720);
    //     inventory.setLayout(null);
        
    //     DrawingPanel gamePanel = new DrawingPanel();
    //     gamePanel.setBackground(Color.BLUE);
    //     gamePanel.setBounds(posX, posY, WIDTH, HEIGHT);
    //     gamePanel.setLayout(null);

    //     Rectangle playerRect = new Rectangle(500, 310, 50, 50);
    //     Rectangle enemyRect = new Rectangle(550, 510, 50, 50);
    //     gamePanel.addRectangle(playerRect);
    //     gamePanel.addRectangle(enemyRect);

    //     JPanel maiPanel = new JPanel();
    //     maiPanel.setBackground(Color.BLUE);
    //     maiPanel.setBounds(0, 0, 1080, 720);
    //     maiPanel.setLayout(null);

    //     JFrame frame = new JFrame();
    //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //     frame.setSize(1080, 720);
    //     frame.setTitle("The Survivalist Game");
    //     frame.setVisible(true);
    //     frame.setLayout(null);
    //     frame.setResizable(false);
        
    //     boolean isInvOpen = false;
    //     maiPanel.add(statusBar);
    //     maiPanel.add(inventoryPanel);
    //     maiPanel.add(gamePanel);

    //     while (true) {
    //         if (key == 'i') {
    //             isInvOpen = !isInvOpen;
    //             System.out.println("Inventory Opened: " + isInvOpen);
    //             key = ' ';
    //         } 
    //         if (key == 'w' && posY - SPEED >= 0) {
    //             // playerRect.setBounds(posX, posY - SPEED, 50, 50);
    //             gamePanel.setBounds(posX, posY - SPEED, WIDTH, HEIGHT);
    //             System.out.println("Player moved up");
    //             key = ' ';
    //         } 
    //         if (key == 's' && posY + SPEED <= HEIGHT) {
    //             // playerRect.setBounds(posX, posY + SPEED, 50, 50);
    //             gamePanel.setBounds(posX, posY + SPEED, WIDTH, HEIGHT);
    //             System.out.println("Player moved down");
    //             key = ' ';
    //         } 
    //         if (key == 'a' && posX - SPEED >= 0) {
    //             // playerRect.setBounds(posX - SPEED, posY, 50, 50);
    //             gamePanel.setBounds(posX - SPEED, posY, WIDTH, HEIGHT);
    //             System.out.println("Player moved left");
    //             key = ' ';
    //         } 
    //         if (key == 'd' && posX + SPEED <= WIDTH) {
    //             // playerRect.setBounds(posX + SPEED, posY, 50, 50);
    //             gamePanel.setBounds(posX + SPEED, posY, WIDTH, HEIGHT);
    //             System.out.println("Player moved right");
    //             key = ' ';
    //         }
    //         if (isInvOpen) {
    //             inventoryPanel.setVisible(false);
    //             inventory.setVisible(true);
    //             gamePanel.setVisible(false);
    //             maiPanel.setVisible(false);
    //             frame.add(inventory);
    //             frame.remove(maiPanel);
    //         } else {
    //             gamePanel.setVisible(true);
    //             inventoryPanel.setVisible(true);
    //             inventory.setVisible(false);
    //             maiPanel.setVisible(true);
    //             frame.remove(inventory);
    //         }

    //         frame.addKeyListener(this);
    //         frame.add(maiPanel);
    //     }
    // }

    // Method dipanggil saat tombol ditekan
    public void keyPressed(KeyEvent e) {
    }

    // Method dipanggil saat tombol dilepas
    public void keyReleased(KeyEvent e) {
        // Bisa ditambahkan aksi lain
    }

    // Method dipanggil saat tombol diketik (pressed + released)
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w':
                key = 'w';
                break;
            case 's':
                key = 's';
                break;
            case 'a':
                key = 'a';
                break;
            case 'd':
                key = 'd';
                break;
            case 'i':
                key = 'i';
                break;
        }
    }

    public void Run() {
        Island island = new Island();
        Player player = new Player("Player1", island);
        Tiger tiger = new Tiger("Tiger", 10, 7,"Male");
        player.island.world[tiger.y][tiger.x] = 'T'; // Display tiger on the island
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Stick", 10, 13));
        player.inventory.addItems(new Sword("Sword"));
        player.inventory.addItems(new Bread());
        CraftingTable craftingTable = new CraftingTable();
        craftingTable.showRecipes();
        player.inventory.showInventory();
        boolean isRunning = true;

        //buat test doang
        KandangAyam kandang = new KandangAyam("Chicken Coop", 7, 7);
        PigCage pigCage = new PigCage("Pig Cage", 7, 9);
        CowCage cowCage = new CowCage("Cow Cage", 9, 7);
        SheepCage sheepCage = new SheepCage("Sheep Cage", 9, 9);
        island.buildings.add(kandang);
        island.buildings.add(pigCage);
        island.buildings.add(cowCage);
        island.buildings.add(sheepCage);
        
        island.world[7][7] = '='; // Chicken coop
        island.world[7][9] = '*';  // Pig cage
        island.world[9][7] = '@';  // Cow cage
        island.world[9][9] = '-';  // Sheep cage
        //======================================

        while (isRunning) {
            // player.displayStats(player);
            player.displayStats(player);
            player.island.showWorld(player, tiger);
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
                    handleInteraction(player);//useitem jadi satu di procedure ini
                    break;
                case "h":
                    System.out.println("Enter the index of the item to use: ");
                    int index = getInt.nextInt();
                    player.selectItem(index - 1);
                case "g":
                    player.handleGrabAction();
                    break;
                
                default:
                    System.out.println("Invalid input! Use WASD to move or Q to quit.");
                    break;
            }
            // tiger.chasePrey(player); // Tiger chases the player if nearby
        }

    }
    public void handleInteraction(Player player) {
        Kandang nearbyKandang = player.findNearbyKandang();
        if (nearbyKandang != null) {
            
            nearbyKandang.interact(player, player.island);
        } else {
            player.useItem();
        }
    }

}
