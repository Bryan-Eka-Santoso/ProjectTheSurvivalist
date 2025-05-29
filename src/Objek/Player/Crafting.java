package Objek.Player;

import java.util.*;
import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Items.Buildings.Bed;
import Objek.Items.Buildings.Chest;
import Objek.Items.Buildings.Furnace;
import Objek.Items.StackableItem.Materials.Crystal;
import Objek.Items.StackableItem.Materials.Gem;
import Objek.Items.StackableItem.Materials.MetalFrame;
import Objek.Items.StackableItem.Materials.MetalIngot;
import Objek.Items.StackableItem.Materials.MetalNails;
import Objek.Items.StackableItem.Materials.MetalSheet;
import Objek.Items.StackableItem.Materials.Stone;
import Objek.Items.StackableItem.Materials.SwordHandle;
import Objek.Items.StackableItem.Materials.ToolHandle;
import Objek.Items.StackableItem.Materials.Wool;
import Objek.Items.StackableItem.Materials.Fuels.Wood;
import Objek.Items.Unstackable.Arsenals.WindAxe;
import Objek.Items.Unstackable.Arsenals.WoodenClub;
import Objek.Items.Unstackable.Arsenals.LightweightAxe;
import Objek.Items.Unstackable.Arsenals.LightweightPickaxe;
import Objek.Items.Unstackable.Arsenals.MetalClub;
import Objek.Items.Unstackable.Arsenals.SpikedMetalClub;
import Objek.Items.Unstackable.Arsenals.SpikedWoodenClub;
import Objek.Items.Unstackable.Arsenals.FlimsyAxe;
import Objek.Items.Unstackable.Arsenals.IcePickaxe;

public class Crafting {
    public LinkedHashMap<List<Item>, Item> currentRecipe = new LinkedHashMap<>();
    public LinkedHashMap<List<Item>, Item> smallRecipes = new LinkedHashMap<>();
    public LinkedHashMap<List<Item>, Item> recipes = new LinkedHashMap<>();
    public GamePanel gp;

    public Crafting(GamePanel gp) {
        smallRecipes = fillSmallRecipes();
        recipes = fillRecipes();
        this.gp = gp;
    }

    private LinkedHashMap<List<Item>, Item> fillSmallRecipes() {
        LinkedHashMap<List<Item>, Item> r = new LinkedHashMap<>();

        List<Item> recipe1 = Arrays.asList(new MetalSheet(1), new MetalFrame(1));
        r.put(recipe1, new SwordHandle(1));

        List<Item> recipe2 = Arrays.asList(new Wood(2), new MetalSheet(1));
        r.put(recipe2, new ToolHandle(1));

        List<Item> recipe3 = Arrays.asList(new MetalIngot(1));
        r.put(recipe3, new MetalNails(1));

        List<Item> recipe4 = Arrays.asList(new WoodenClub(), new MetalNails(1));
        r.put(recipe4, new SpikedWoodenClub());

        List<Item> recipe5 = Arrays.asList(new MetalClub(), new MetalNails(1));
        r.put(recipe5, new SpikedMetalClub());

        List<Item> recipe6 = Arrays.asList(new MetalIngot(1));
        r.put(recipe6, new MetalSheet(2));

        List<Item> recipe9 = Arrays.asList(new MetalIngot(1), new MetalNails(2));
        r.put(recipe9, new MetalFrame(1));

        return r;
    }

    private LinkedHashMap<List<Item>, Item> fillRecipes() {
        LinkedHashMap<List<Item>, Item> r = new LinkedHashMap<>();

        r.putAll(smallRecipes);

        List<Item> recipe1 = Arrays.asList(new ToolHandle(1), new Gem(2));
        r.put(recipe1, new WindAxe());

        List<Item> recipe2 = Arrays.asList(new ToolHandle(1), new MetalSheet(2));
        r.put(recipe2, new LightweightAxe());

        List<Item> recipe3 = Arrays.asList(new ToolHandle(1), new Wood(4));
        r.put(recipe3, new FlimsyAxe());

        List<Item> recipe4 = Arrays.asList(new ToolHandle(1), new MetalSheet(2));
        r.put(recipe4, new LightweightPickaxe());

        List<Item> recipe5 = Arrays.asList(new ToolHandle(1), new Crystal(1), new MetalSheet(2));
        r.put(recipe5, new IcePickaxe());

        List<Item> recipe6 = Arrays.asList(new Wood(3));
        r.put(recipe6, new WoodenClub());

        List<Item> recipe7 = Arrays.asList(new MetalIngot(3));
        r.put(recipe7, new MetalClub());

        List<Item> recipe8 = Arrays.asList(new Wood(3), new Wool(3));
        r.put(recipe8, new Bed(gp, 1, 0));

        List<Item> recipe9 = Arrays.asList(new Stone(8));
        r.put(recipe9, new Furnace(gp, 1, 0));

        List<Item> recipe10 = Arrays.asList(new Wood(8));
        r.put(recipe10, new Chest(gp, 1, 0));

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
