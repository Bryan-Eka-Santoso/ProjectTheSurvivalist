package Objek.Items.Buildings;

import Objek.Controller.GamePanel;
import Objek.Items.Item;
import Objek.Items.StackableItem.Foods.Bread;
import Objek.Items.StackableItem.Foods.RawMutton;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import javax.imageio.ImageIO;

public class Furnace extends Buildings {
    public LinkedHashMap<Item, Item> recipe = new LinkedHashMap<>();
    public Item[] rawMaterial;
    public Item[] fuelMaterial;
    public Item[] cookedMaterial;

    public Furnace(GamePanel gp, int currentStack) {
        super("Furnace", 10, currentStack, gp, new Rectangle(9, 9, 30, 30), 48, 48);
        rawMaterial = new Item[1];
        fuelMaterial = new Item[1];
        cookedMaterial = new Item[1];
        try {
            this.img = ImageIO.read(new File("ProjectTheSurvivalist/res/Items/Buildings/smelter.png"));
        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        recipe = fillRecipes();
    }

    public void cook(Item rawItem, Item fuel) {
        rawMaterial[0] = rawItem;
        fuelMaterial[0] = fuel;
        for (int i = 0; i < rawMaterial[0].currentStack; i++) {
            if (rawMaterial[0].currentStack > 0 && fuelMaterial[0].currentStack > 0) {
                Item tempItem = rawMaterial[0].clone();
                tempItem.currentStack = 1;
                Item result = recipe.get(tempItem);
                if (result != null && cookedMaterial[0] == null) {
                    cookedMaterial[0] = result.clone();
                    cookedMaterial[0].currentStack = 1;
                    rawMaterial[0].currentStack--;
                    fuelMaterial[0].currentStack--;
                    System.out.println("Cooking " + rawItem.name + " with " + fuel.name + " to make " + cookedMaterial[0].name);
                } else if (result != null && cookedMaterial[0] != null) {
                    if (result.name.equals(cookedMaterial[0].name)) {
                        cookedMaterial[0].currentStack++;
                        System.out.println("Cooking " + rawItem.name + " with " + fuel.name + " to make " + cookedMaterial[0].name);
                    } else {
                        System.out.println("Cannot cook " + rawItem.name + " with " + fuel.name + " because it will produce " + result.name + " instead of " + cookedMaterial[0].name);
                    }
                } else {
                    System.out.println("No recipe found for " + rawItem.name);
                }
            } else {
                System.out.println("Not enough fuel");
            }
        }
    }

    private LinkedHashMap<Item, Item> fillRecipes() {
        LinkedHashMap<Item, Item> r = new LinkedHashMap<>();
        r.put(new RawMutton(1), new Bread(1));
        return r;
    }


}
