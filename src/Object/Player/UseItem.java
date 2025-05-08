package Object.Player;

import Object.Items.Item;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Unstackable.Axe;
import Object.Items.Unstackable.Sword;
import Object.Items.Unstackable.Buildings.Buildings;

public class UseItem {
    
    public void useItem(Item selectedItem, Player player) {
        if (selectedItem != null && selectedItem.name != null) {
            if (selectedItem instanceof Material) {
                Material material = (Material) selectedItem;
                System.out.println("Using material: " + material.name);
            } else if (selectedItem instanceof Buildings) {
                Buildings building = (Buildings) selectedItem;
                System.out.println("Using building: " + building.name);
            } else if (selectedItem instanceof Food) {
                Food food = (Food) selectedItem;
                System.out.println("Using food: " + food.name);
                food.eat(player); // 
                player.inventory.removeItem(selectedItem); 
            } else if (selectedItem instanceof Axe) {
                Axe axe = (Axe) selectedItem;
                if (player.plantIndex != -1) {
                    System.out.println("Using axe: " + axe.name);
                    player.gp.plants.remove(player.plantIndex);
                    player.plantIndex = -1; 
                } else {
                    System.out.println("No plant selected to use the axe on!");
                }
            } else if (selectedItem instanceof Sword) {
                Sword sword = (Sword) selectedItem;
                System.out.println("Using sword: " + sword.name);
            } else {
                System.out.println("Unknown item type!"); 
            }
        } else {
            System.out.println("No item selected!"); 
        }
    }

}
