package Objek.Controller;

import java.util.Random;

import Objek.Animal.Animal;
import Objek.Animal.Chicken;
import Objek.Animal.Cow;
import Objek.Animal.Pig;
import Objek.Animal.Sheep;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Feather;
import Objek.Items.StackableItem.Food;
import Objek.Items.StackableItem.Material;
import Objek.Items.StackableItem.RawChicken;
import Objek.Items.StackableItem.RawMeat;
import Objek.Items.StackableItem.RawMutton;
import Objek.Items.StackableItem.RawPork;
import Objek.Items.StackableItem.Wood;
import Objek.Items.Unstackable.Axe;
import Objek.Items.Unstackable.Sword;
import Objek.Player.Player;

public class UseItem {
    GamePanel gp;
    Sound sound = new Sound();
    Random rand = new Random();
    
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
                player.isCutting = true;
                player.cutting();
                if (player.plantIndex != -1) {
                    player.gp.plants.get(player.plantIndex).hp -= 20;
                    System.out.println("Using axe: " + axe.name);
                    if(player.gp.plants.get(player.plantIndex).hp <= 0) {
                        player.gp.droppedItems.add(new ItemDrop(player.gp.plants.get(player.plantIndex).worldX, player.gp.plants.get(player.plantIndex).worldY, new Wood(1), gp));
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
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawChicken(1), gp));
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new Feather(rand.nextInt(3) + 1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                            player.gp.checkAndRespawnAnimals();
                        }
                    }
                    else if(animal instanceof Pig) {
                        Pig pig = (Pig)animal;
                        pig.hp -= damage;
                        System.out.println("Hit pig: " + pig.hp + "/" + 80);
                        if(pig.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawPork(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    else if(animal instanceof Sheep) {
                        Sheep sheep = (Sheep)animal;
                        sheep.hp -= damage;
                        System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
                        if(sheep.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMutton(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.animalIndex = -1;
                        }
                    }
                    else if(animal instanceof Cow) {
                        Cow cow = (Cow)animal;
                        cow.hp -= damage;
                        System.out.println("Hit cow: " + cow.hp + "/" + 100);
                        if(cow.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMeat(1), gp));
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
