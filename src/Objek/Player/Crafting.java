package Objek.Player;
import java.util.*;

import Objek.Items.Item;
import Objek.Items.StackableItem.*;
import Objek.Items.Unstackable.*;

public class Crafting {
    public HashMap<List<Item>, Item> recipes = new HashMap<>();

    public Crafting() {
        List<Item> recipe1 = Arrays.asList(new Wood("Wood", 10, 2));
        recipes.put(recipe1, new Sword("Sword1", 20, 30));

        List<Item> recipe2 = Arrays.asList(new Wood("Wood", 10, 2));
        recipes.put(recipe2, new Sword("Sword2", 20, 30));

        List<Item> recipe3 = Arrays.asList(new Wood("Wood", 10, 2), new Wood("Wood", 10, 2));
        recipes.put(recipe3, new Sword("Sword3", 20, 30));

        List<Item> recipe4 = Arrays.asList(new Wood("Wood", 10, 2));
        recipes.put(recipe4, new Sword("Sword4", 20, 30));

        List<Item> recipe5 = Arrays.asList(new Wood("Wood", 10, 2));
        recipes.put(recipe5, new Sword("Sword5", 20, 30));

        List<Item> recipe6 = Arrays.asList(new Wood("Wood", 10, 2));
        recipes.put(recipe6, new Bread(5));
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
