package Object.Controller;

import Object.Items.Item;
import Object.Items.StackableItem.Food;
import Object.Items.StackableItem.Material;
import Object.Items.Unstackable.Axe;
import Object.Items.Unstackable.Sword;
import Object.Items.Unstackable.Buildings.Buildings;
import Object.Player.Player;
import Object.Animal.Animal;
import Object.Animal.Chicken;
import Object.Animal.Cow;
import Object.Animal.Pig;
import Object.Animal.Sheep;

public class UseItem {

    Sound sound = new Sound();
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
                playSE(1);
                selectedItem.currentStack--;
                if (selectedItem.currentStack <= 0) {
                    player.inventory.removeItem(selectedItem, 1); // Remove item from inventory
                }
            } else if (selectedItem instanceof Axe) {
                Axe axe = (Axe) selectedItem;
                player.isCutting = true;
                player.cutting();
                if (player.plantIndex != -1) {
                    player.gp.plants.get(player.plantIndex).hp -= 20;
                    System.out.println("Using axe: " + axe.name);
                    if(player.gp.plants.get(player.plantIndex).hp <= 0) {
                       
                        player.gp.plants.remove(player.plantIndex);
                        player.plantIndex = -1; 
                    }
                    playSE(6); // Play axe sound effect
                } else {
                    System.out.println("No plant selected to use the axe on!");
                }
            } else if (selectedItem instanceof Sword) {
                Sword sword = (Sword) selectedItem;
                player.isCutting = true;
                player.cutting();
                if (player.animalIndex != -1) {
                    int damage = sword.getDamage();
                    Animal animal = player.gp.animals.get(player.animalIndex);
                    if(animal instanceof Chicken) {
                        Chicken chicken = (Chicken)animal;
                        chicken.hp -= damage;
                        System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
                        if(chicken.hp <= 0) {
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    else if(animal instanceof Pig) {
                        Pig pig = (Pig)animal;
                        pig.hp -= damage;
                        System.out.println("Hit pig: " + pig.hp + "/" + 80);
                        if(pig.hp <= 0) {
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    else if(animal instanceof Sheep) {
                        Sheep sheep = (Sheep)animal;
                        sheep.hp -= damage;
                        System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
                        if(sheep.hp <= 0) {
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    else if(animal instanceof Cow) {
                        Cow cow = (Cow)animal;
                        cow.hp -= damage;
                        System.out.println("Hit cow: " + cow.hp + "/" + 100);
                        if(cow.hp <= 0) {
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    playSE(4); // Play axe sound effect
                }
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
