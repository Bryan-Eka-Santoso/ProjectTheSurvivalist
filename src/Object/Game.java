package Object;
import Object.Player.CraftingTable;
import Object.Player.Player;
import Object.Items.StackableItem.*;
import Object.Items.Unstackable.*;

public class Game {
    
    public void Run() {
        Player player = new Player("Player1");
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Wood", 10, 13));
        player.inventory.addItems(new Material("Stick", 10, 13));
        player.inventory.addItems(new Sword("Sword"));
        CraftingTable craftingTable = new CraftingTable();
        craftingTable.showRecipes();
        craftingTable.craft(player, "Wooden Sword");
        player.inventory.showInventory();
    }

}
