package Objek.Items.Buildings;

import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Items.StackableItem.Bucket;
import Objek.Items.StackableItem.Foods.CookedFoods.Bacon;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedArwana;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedBelida;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedChicken;
import Objek.Items.StackableItem.Foods.CookedFoods.CookedMutton;
import Objek.Items.StackableItem.Foods.CookedFoods.Steak;
import Objek.Items.StackableItem.Foods.Other.BakedPotato;
import Objek.Items.StackableItem.Materials.ForgedComponents.GoldIngot;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalIngot;
import Objek.Items.StackableItem.Materials.Fuels.Fuel;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import javax.imageio.ImageIO;

public class Furnace extends Buildings {
    public LinkedHashMap<String, Item> recipe = new LinkedHashMap<>();
    public Item[] rawMaterial;
    public Item[] fuelMaterial;
    public Item[] cookedMaterial;

    public Furnace(GamePanel gp, int currentStack, int buildingMap) {
        super("Furnace", 10, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48, buildingMap);
        rawMaterial = new Item[1];
        fuelMaterial = new Item[1];
        cookedMaterial = new Item[1];
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/smelter.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        recipe = fillRecipes();
    }

    public void cook() {
        if (!(fuelMaterial[0] instanceof Fuel)) {
            System.out.println("Please put fuel in the furnace");
            return;
        }
        for (int i = 0; i < fuelMaterial[0].currentStack; i++) {
            if (rawMaterial[0].currentStack > 0 && fuelMaterial[0].currentStack > 0) {
                Item tempItem = rawMaterial[0];
                Item result = recipe.get(tempItem.name);
                if (result != null && cookedMaterial[0] == null) {
                    cookedMaterial[0] = result.clone();
                    cookedMaterial[0].currentStack = result.currentStack;
                    rawMaterial[0].currentStack--;
                    fuelMaterial[0].currentStack--;
                    if (rawMaterial[0].currentStack == 0) {
                        rawMaterial[0] = null;
                        System.out.println("No more " + cookedMaterial[0].name + " left");
                    }
                    if (fuelMaterial[0].currentStack == 0) {
                        fuelMaterial[0] = null;
                        System.out.println("No more " + cookedMaterial[0].name + " left");
                    }
                    System.out.println("Cooking " + rawMaterial[0].name + " with " + fuelMaterial[0].name + " to make " + cookedMaterial[0].name);
                } else if (result != null && cookedMaterial[0] != null) {
                    if (result.name.equals(cookedMaterial[0].name)) {
                        if (cookedMaterial[0].currentStack < cookedMaterial[0].maxStack) {
                            rawMaterial[0].currentStack--;
                            fuelMaterial[0].currentStack--;
                            cookedMaterial[0].currentStack += result.currentStack;
                            if (rawMaterial[0].currentStack == 0) {
                                rawMaterial[0] = null;
                                System.out.println("No more " + cookedMaterial[0].name + " left");
                            }
                            if (fuelMaterial[0].currentStack == 0) {
                                fuelMaterial[0] = null;
                                System.out.println("No more " + cookedMaterial[0].name + " left");
                            }
                            System.out.println("Cooking " + rawMaterial[0].name + " with " + fuelMaterial[0].name + " to make " + cookedMaterial[0].name);
                        } else {
                            System.out.println("Cannot cook " + rawMaterial[0].name + " with " + fuelMaterial[0].name + " because it will produce " + result.name + " instead of " + cookedMaterial[0].name);
                            System.out.println("Inventory is full, please remove some items");
                            return;
                        }
                    } else {
                        System.out.println("Cannot cook " + rawMaterial[0].name + " with " + fuelMaterial[0].name + " because it will produce " + result.name + " instead of " + cookedMaterial[0].name);
                    }
                } else {
                    System.out.println("No recipe found for " + rawMaterial[0].name);
                }
            } else {
                System.out.println("Not enough fuel");
            }
        }
    }

    private LinkedHashMap<String, Item> fillRecipes() {
        LinkedHashMap<String, Item> r = new LinkedHashMap<>();
        
        r.put("Raw Arwana", new CookedArwana(1));
        r.put("Raw Belida", new CookedBelida(1));
        r.put("Raw Meat", new Steak(1));
        r.put("Raw Chicken", new CookedChicken(2));
        r.put("Raw Mutton", new CookedMutton(1));
        r.put("Raw Pork", new Bacon(3));
        r.put("Potato", new BakedPotato(1));
        r.put("Metal", new MetalIngot(1));
        r.put("Gold", new GoldIngot(1));
        r.put("Water Bucket", Bucket.getCleansedWaterBucket(gp));

        return r;
    }
}
