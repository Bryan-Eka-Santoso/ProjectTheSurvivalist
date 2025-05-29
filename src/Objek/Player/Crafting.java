package Objek.Player;

import java.util.*;
import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.*;
import Objek.Items.StackableItem.Foods.*;
import Objek.Items.StackableItem.Materials.*;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.Arsenals.*;
import Objek.Items.Unstackable.*;
import Objek.Items.Unstackable.Armor.Chestplate.*;
import Objek.Items.Unstackable.Armor.Helmet.*;
import Objek.Items.Unstackable.Armor.Leggings.*;
import Objek.Items.Unstackable.Armor.Boots.*;

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

        List<Item> recipe0 = Arrays.asList(new Wood(4));
        r.put(recipe0, new CraftingTable(gp, 1));

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

        List<Item> recipe10 = Arrays.asList(new Guava(1));
        r.put(recipe10, new GuavaSeeds(2));

        List<Item> recipe11 = Arrays.asList(new Mango(1));
        r.put(recipe11, new MangoSeeds(2));

        List<Item> recipe12 = Arrays.asList(new Coconut(1));
        r.put(recipe12, new CoconutSeeds(2));
        
        return r;
    }

    private LinkedHashMap<List<Item>, Item> fillRecipes() {
        LinkedHashMap<List<Item>, Item> r = new LinkedHashMap<>();

        r.putAll(smallRecipes);

        r.put(Arrays.asList(new GoldIngot(1), new MetalSheet(2), new Crystal(1)), new Lantern(gp));

        List<Item> recipe1 = Arrays.asList(new ToolHandle(1), new Gem(2));
        r.put(recipe1, new WindAxe());

        List<Item> recipe2 = Arrays.asList(new ToolHandle(1), new Wood(1), new MetalSheet(2));
        r.put(recipe2, new LightweightAxe());

        List<Item> recipe3 = Arrays.asList(new ToolHandle(1), new Wood(1), new Stone(2));
        r.put(recipe3, new FlimsyAxe());

        List<Item> recipe4 = Arrays.asList(new ToolHandle(1), new MetalIngot(2));
        r.put(recipe4, new LightweightPickaxe());

        List<Item> recipe5 = Arrays.asList(new ToolHandle(1), new Crystal(1), new MetalSheet(2));
        r.put(recipe5, new IcePickaxe());

        r.put(Arrays.asList(new SwordHandle(1), new GoldIngot(2)), new GoldSword());

        r.put(Arrays.asList(new SwordHandle(1), new MetalIngot(2)), new MetalSword());

        List<Item> recipe6 = Arrays.asList(new Wood(3));
        r.put(recipe6, new WoodenClub());

        List<Item> recipe7 = Arrays.asList(new MetalIngot(3));
        r.put(recipe7, new MetalClub());

        r.put(Arrays.asList(new Wheat(3)), new Bread(1));

        List<Item> recipe8 = Arrays.asList(new Wood(3), new Wool(3));
        r.put(recipe8, new Bed(gp, 1));

        List<Item> recipe9 = Arrays.asList(new Stone(8));
        r.put(recipe9, new Furnace(gp, 1));

        List<Item> recipe10 = Arrays.asList(new Wood(8));
        r.put(recipe10, new Chest(gp, 1));

        List<Item> recipe11 = Arrays.asList(new Seeds(3), new Stone(3));
        r.put(recipe11, new GardenPatch(gp, 1));

        List<Item> recipe12 = Arrays.asList(new GuavaSeeds(1), new MangoSeeds(1), new CoconutSeeds(1), new Wood(3));
        r.put(recipe12, new Orchard(gp, 1));

        List<Item> recipe13 = Arrays.asList(new MetalSheet(3));
        r.put(recipe13, new Bucket(1));

        List<Item> recipe14 = Arrays.asList(new MetalIngot(5));
        r.put(recipe14, new MetalHelmet());

        List<Item> recipe15 = Arrays.asList(new MetalIngot(8));
        r.put(recipe15, new MetalChestplate());

        List<Item> recipe16 = Arrays.asList(new MetalIngot(7));
        r.put(recipe16, new MetalLeggings());

        List<Item> recipe17 = Arrays.asList(new MetalIngot(4));
        r.put(recipe17, new MetalBoots());

        List<Item> recipe18 = Arrays.asList(new MetalIngot(5));
        r.put(recipe18, new MetalHelmet());

        List<Item> recipe19 = Arrays.asList(new MetalIngot(8));
        r.put(recipe19, new MetalChestplate());

        List<Item> recipe20 = Arrays.asList(new MetalIngot(7));
        r.put(recipe20, new MetalLeggings());

        List<Item> recipe21 = Arrays.asList(new MetalIngot(4));
        r.put(recipe21, new MetalBoots());

        List<Item> recipe22 = Arrays.asList(new GoldIngot(5));
        r.put(recipe22, new GoldHelmet());

        List<Item> recipe23 = Arrays.asList(new GoldIngot(8));
        r.put(recipe23, new GoldChestplate());

        List<Item> recipe24 = Arrays.asList(new GoldIngot(7));
        r.put(recipe24, new GoldLeggings());

        List<Item> recipe25 = Arrays.asList(new GoldIngot(4));
        r.put(recipe25, new GoldBoots());

        List<Item> recipe26 = Arrays.asList(new Wood(5));
        r.put(recipe26, new WateringCan());

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
