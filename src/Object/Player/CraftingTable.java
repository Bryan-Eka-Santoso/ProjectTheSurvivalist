package Object.Player;
import Object.Items.Item;
import Object.Items.UnconsumableItem.Unstackable;

import java.util.*;

public class CraftingTable {
    HashMap<List<String>, Item> recipes = new HashMap<>();

    public CraftingTable() {
        // Example recipes
        List<String> recipe1 = Arrays.asList("Wood", "Wood", "Wood");
        recipes.put(recipe1, new Unstackable ("Wooden Sword", 1));
        
        List<String> recipe2 = Arrays.asList("Stone", "Stone", "Stone");
        recipes.put(recipe2, new Unstackable ("Stone Sword", 1));
        
        List<String> recipe3 = Arrays.asList("Stick", "Iron", "Iron");
        recipes.put(recipe3, new Unstackable ("Iron Sword", 1));
    }

    public void showRecipes() {
        System.out.println("Available Recipes:");
        for (Map.Entry<List<String>, Item> entry : recipes.entrySet()) {
            System.out.println("Recipe: " + entry.getKey() + " -> Item: " + entry.getValue().name);
        }
    }

    public void craft(Player player, String itemName) {
        player.inventory.addItem(recipes.get(Arrays.asList("Wood", "Wood", "Wood")));
        System.out.println(player.inventory.slots[4].currentStack);
    }

    public boolean checkIngredients(Player player, List<String> ingredients) {
        for (String ingredient : ingredients) {
            if (!player.inventory.hasItem(ingredient)) {
                return false;
            }
        }
        return true;
    }
}
