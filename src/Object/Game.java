package Object;
import Object.Player.CraftingTable;
import Object.Player.Player;
import Object.Items.UnconsumableItem.Unstackable;
import Object.Items.ConsumableItem.Stackable;
// import Object.Player.Crafting;
// import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.List;
// import javax.swing.*;
// import java.awt.*;

public class Game {
    
    public void Run() {
        Player player = new Player("Player1");
        player.inventory.addItems(new Stackable("Wood", 10, 13));
        player.inventory.addItems(new Stackable("Wood", 10, 13));
        // player.inventory.addItems(new Stackable("Apple", 10, 18));
        // player.inventory.addItems(new Stackable("Wood", 10, 18));
        player.inventory.addItems(new Unstackable("Sword"));
        player.inventory.addItems(new Unstackable("Sword"));
        player.inventory.addItems(new Unstackable("Wooden Sword"));
        // player.inventory.showInventory();
        CraftingTable craftingTable = new CraftingTable();
        // craftingTable.showRecipes();
        craftingTable.craft(player, "Wooden Sword");
        craftingTable.craft(player, "Wooden Sword");
        craftingTable.craft(player, "Wooden Sword");
        craftingTable.craft(player, "Wooden Sword");
        player.inventory.showInventory();
    }

}
