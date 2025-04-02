package Object.Player;
import Object.Items.Item;
import Object.Items.StackableItem.*;
import Object.Items.Unstackable.*;
import java.util.*;

public class CraftingTable {
    HashMap<List<Item>, Item> recipes = new HashMap<>();

    public CraftingTable() {
        List<Item> recipe1 = Arrays.asList(new Material("Wood", 2), new Material("Stick", 1));
        recipes.put(recipe1, new Sword("Wooden Sword"));
    }

    public void showRecipes() {
        System.out.println("Available Recipes:");
        for (Map.Entry<List<Item>, Item> entry : recipes.entrySet()) {
            List<Item> ingredients = entry.getKey();
            Item result = entry.getValue();
            System.out.print("Ingredients: ");
            for (Item ingredient : ingredients) {
                System.out.print(ingredient.name + " x" + ingredient.currentStack + ", ");
            }
            System.out.println("=> Result: " + result.name);
        }
    }

    public void craft(Player player, String itemName) {
        for (Map.Entry<List<Item>, Item> entry : recipes.entrySet()) {
            List<Item> ingredients = entry.getKey();
            Item result = entry.getValue().clone();
            if (result.name.equals(itemName)) {
                if (canCraft(player, ingredients)) {
                    for (Item ingredient : ingredients) {
                        for (int i = 0; i < player.inventory.slots.length; i++) {
                            if (player.inventory.slots[i] != null && player.inventory.slots[i].name.equals(ingredient.name)) {
                                player.inventory.slots[i].currentStack -= ingredient.currentStack;
                                if (player.inventory.slots[i].currentStack <= 0) {
                                    player.inventory.slots[i] = null;
                                }
                                break;
                            }
                        }
                    }
                    player.inventory.addItems(result);
                } else {
                    System.out.println("Not enough ingredients to craft " + itemName);
                }
                return;
            }
        }
        System.out.println("Recipe for " + itemName + " not found.");
    }

    public boolean canCraft(Player player, List<Item> ingredients) {
        Item[] temp = new Item[player.inventory.slots.length];
    
        for (Item item : player.inventory.slots) {
            if (item == null) continue;
    
            for (int i = 0; i < temp.length; i++) {
                if (temp[i] == null) {
                    temp[i] = item.clone();
                    break;
                }
                if (temp[i].name.equals(item.name)) { // No need to check null again
                    temp[i].currentStack += item.currentStack;
                    break;
                }
            }
        }

        boolean[] validate = new boolean[ingredients.size()];
        Arrays.fill(validate, false);
    
        for (Item ingredient : ingredients) {
            for (Item item : temp) {
                if (item == null) continue; 
    
                if (item.name.equals(ingredient.name) && item.currentStack >= ingredient.currentStack) {
                    validate[ingredients.indexOf(ingredient)] = true;
                    break;
                }
            }
        }

        for (boolean valid : validate) {
            if (!valid) return false;
        }
        return true;
    }
    

    public static Item[] cloneArray(Item[] originalArray) {
        if (originalArray == null) {
            return null;
        }
    
        Item[] clonedArray = new Item[originalArray.length];
        for (int i = 0; i < originalArray.length; i++) {
            if (originalArray[i] != null) {
                clonedArray[i] = originalArray[i].clone(); // Clone setiap elemen
            }
        }
        return clonedArray;
    }
}
