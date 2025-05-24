package Objek.Player;
import java.util.*;

import Objek.Items.Item;
import Objek.Items.StackableItem.Materials.Gem;
import Objek.Items.StackableItem.Materials.MetalFrame;
import Objek.Items.StackableItem.Materials.MetalSheet;
import Objek.Items.StackableItem.Materials.SwordHandle;
import Objek.Items.StackableItem.Materials.ToolHandle;
import Objek.Items.StackableItem.Materials.Wood;
import Objek.Items.Unstackable.Arsenals.WindAxe;
import Objek.Items.Unstackable.Arsenals.LightweightAxe;
import Objek.Items.Unstackable.Arsenals.FlimsyAxe;

public class Crafting {
    public LinkedHashMap<List<Item>, Item> currentRecipe = new LinkedHashMap<>();
    public LinkedHashMap<List<Item>, Item> smallRecipes = new LinkedHashMap<>();
    public LinkedHashMap<List<Item>, Item> recipes = new LinkedHashMap<>();

    public Crafting() {
        smallRecipes = fillSmallRecipes();
        recipes = fillRecipes();
    }

    private LinkedHashMap<List<Item>, Item> fillSmallRecipes() {
        LinkedHashMap<List<Item>, Item> r = new LinkedHashMap<>();

        List<Item> recipe1 = Arrays.asList(new MetalSheet(1), new MetalFrame(1));
        r.put(recipe1, new SwordHandle(1));

        List<Item> recipe2 = Arrays.asList(new Wood(2), new MetalSheet(1));
        r.put(recipe2, new ToolHandle(1));

        return r;
    }

    private LinkedHashMap<List<Item>, Item> fillRecipes() {
        LinkedHashMap<List<Item>, Item> r = new LinkedHashMap<>();

        r.putAll(smallRecipes);

        List<Item> recipe1 = Arrays.asList(new Wood(3), new Gem(2));
        r.put(recipe1, new WindAxe());

        List<Item> recipe2 = Arrays.asList(new Wood(3), new MetalSheet(2));
        r.put(recipe2, new LightweightAxe());

        List<Item> recipe3 = Arrays.asList(new Wood(4));
        r.put(recipe3, new FlimsyAxe());

        return r;
    }
    
    public void craft(Player player, String itemName) {
        for (Map.Entry<List<Item>, Item> entry : currentRecipe.entrySet()) {
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
