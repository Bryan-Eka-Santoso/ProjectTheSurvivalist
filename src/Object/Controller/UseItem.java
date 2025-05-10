package Object.Controller;

import Object.Items.Item;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Unstackable.Axe;
import Object.Items.Unstackable.Sword;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Player.Player;

public class UseItem {
    GamePanel gp;
    Sound sound = new Sound();
    public UseItem(GamePanel gp) {
        this.gp = gp;
    }

    public void useItem(Item selectedItem, Player player) {
        if (selectedItem != null && selectedItem.name != null) {
            if (selectedItem instanceof Material) {
                Material material = (Material) selectedItem;
                System.out.println("Using material: " + material.name);
            } else if (selectedItem instanceof Buildings) {
                if (!player.isBuild) {
                    Buildings building = (Buildings) selectedItem;
                    player.isBuild = true; // Set the player to building mode
                    gp.gameState = gp.BUILDING_STATE; // Change game state to building
                    System.out.println("Using building: " + building.name);
                } else {
                    player.isBuild = false; // Set the player to not building mode
                    gp.gameState = gp.PLAY_STATE; // Change game state back to play
                }
            } else if (selectedItem instanceof Food) {
                Food food = (Food) selectedItem;
                System.out.println("Using food: " + food.name);
                food.eat(player); // 
                playSE(1);
                selectedItem.currentStack--;
                if (selectedItem.currentStack <= 0) {
                    player.inventory.removeItem(selectedItem, 1); // Remove item from inventory
                }
            } else if (selectedItem instanceof Axe) {
                Axe axe = (Axe) selectedItem;
                if (player.plantIndex != -1) {
                    System.out.println("Using axe: " + axe.name);
                    player.gp.plants.remove(player.plantIndex);
                    player.plantIndex = -1; 
                    playSE(6); // Play axe sound effect
                } else {
                    System.out.println("No plant selected to use the axe on!");
                }
            } else if (selectedItem instanceof Sword) {
                Sword sword = (Sword) selectedItem;
                System.out.println("Using sword: " + sword.name);
                playSE(4);
            } else {
                System.out.println("Unknown item type!"); 
            }
        } else {
            System.out.println("No item selected!"); 
        }
    }

    public void playSE(int i) {
        sound.setFile(i);
        sound.play();
    }

}
