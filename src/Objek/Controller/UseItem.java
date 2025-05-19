package Objek.Controller;

import java.util.Random;
import Objek.Animal.Animal;
import Objek.Animal.Chicken;
import Objek.Animal.Cow;
import Objek.Animal.Pig;
import Objek.Animal.Sheep;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Guava;
import Objek.Items.StackableItem.Foods.RawChicken;
import Objek.Items.StackableItem.Foods.RawMeat;
import Objek.Items.StackableItem.Foods.RawMutton;
import Objek.Items.StackableItem.Foods.RawPork;
import Objek.Items.StackableItem.Materials.Feather;
import Objek.Items.StackableItem.Materials.Material;
import Objek.Items.StackableItem.Materials.Wood;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.Arsenals.Arsenal;
import Objek.Items.Unstackable.Arsenals.Axe;
import Objek.Items.Unstackable.Arsenals.Club;
import Objek.Items.Unstackable.Arsenals.Sword;
import Objek.Plant.*;
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
            if (selectedItem instanceof Seeds){
                if (gp.buildings.get(gp.player.buildingIndex) instanceof Orchard) {
                    Orchard orchard = (Orchard) gp.buildings.get(gp.player.buildingIndex);
                    if (orchard.tree == null) {
                        if (selectedItem instanceof GuavaSeeds) {
                            GuavaSeeds seeds = (GuavaSeeds) selectedItem;
                            orchard.tree = new GuavaTree(0, 0, gp);
                            player.inventory.removeItem(seeds, 1);
                            System.out.println("Planted a " + seeds.name + "!");
                        } else if (selectedItem instanceof MangoSeeds) {
                            MangoSeeds seeds = (MangoSeeds) selectedItem;
                            orchard.tree = new MangoTree(0, 0, gp);
                            player.inventory.removeItem(seeds, 1);
                            System.out.println("Planted a " + seeds.name + "!");
                        } else if (selectedItem instanceof CoconutSeeds) {
                            CoconutSeeds seeds = (CoconutSeeds) selectedItem;
                            orchard.tree = new PalmTree(0, 0, gp);
                            player.inventory.removeItem(seeds, 1);
                            System.out.println("Planted a " + seeds.name + "!");
                        } else {
                            System.out.println("You cannot plant this seed here!");
                        }
                    } else {
                        System.out.println("You already planted a tree here!");
                    }
                } else {
                    System.out.println("You need to be at an orchard to plant seeds!");
                }
            }
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
            } else if (selectedItem instanceof Arsenal){
                Arsenal arsenal = (Arsenal) selectedItem;
                player.isCutting = true;
                player.cutting();
                if (player.plantIndex != -1) {
                    Plant plant = player.gp.plants.get(player.plantIndex);

                    plant.hp -= arsenal.damage;
                    if (selectedItem instanceof Axe){
                        System.out.println("Using sword: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (selectedItem instanceof Sword){
                        System.out.println("Using sword: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (selectedItem instanceof Club){
                        System.out.println("Using club: " + arsenal.name);
                        arsenal.durability -= 7;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    System.out.println("Plant HP: " + plant.hp);
                    if (plant.hp <= 0) {
                        if (plant instanceof GuavaTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX, plant.worldY, new Guava(rand.nextInt(2) + 1), gp));
                        } else if (plant instanceof GuavaTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX, plant.worldY, new Guava(rand.nextInt(2) + 1), gp));
                        }
                        player.gp.droppedItems.add(new ItemDrop(plant.worldX, plant.worldY, new Wood(rand.nextInt(4) + 4), gp));
                        player.gp.plants.remove(player.plantIndex);
                        player.plantIndex = -1; 
                    }
                    playSE(6);
                } else if (player.animalIndex != -1) {
                    Animal animal = player.gp.animals.get(player.animalIndex);
                    animal.hp -= arsenal.damage;
                    int damage = arsenal.damage;

                    if (selectedItem instanceof Axe){
                        System.out.println("Using sword: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (selectedItem instanceof Sword || selectedItem instanceof Club){
                        System.out.println("Using sword/club: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (animal instanceof Chicken) {
                        Chicken chicken = (Chicken)animal;
                        chicken.hp -= damage;
                        System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
                        if(chicken.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawChicken(1), gp));
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new Feather(rand.nextInt(3) + 1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 5);
                            player.animalIndex = -1;
                            player.gp.checkAndRespawnAnimals();
                        }
                    }
                    else if (animal instanceof Pig) {
                        Pig pig = (Pig)animal;
                        pig.hp -= damage;
                        System.out.println("Hit pig: " + pig.hp + "/" + 80);
                        if(pig.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawPork(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 7);
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
                            player.gainExp(rand.nextInt(10) + 8);
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
                            player.gainExp(rand.nextInt(10) + 9);
                            player.animalIndex = -1;
                        }
                    }
                    playSE(4);
                } else {
                    System.out.println("No plant or animal selected to use the arsenal on!");
                }
                if (arsenal.durability <= 0) {
                    player.inventory.removeItem(arsenal, 1); // Remove item from inventory
                }
            }
        //     } else if (selectedItem instanceof Axe) {
        //         Axe axe = (Axe) selectedItem;
        //         player.isCutting = true;
        //         player.cutting();
        //         if (player.plantIndex != -1) {
        //             player.gp.plants.get(player.plantIndex).hp -= 20;
        //             System.out.println("Using axe: " + axe.name);
        //             if(player.gp.plants.get(player.plantIndex).hp <= 0) {
        //                 player.gp.droppedItems.add(new ItemDrop(player.gp.plants.get(player.plantIndex).worldX, player.gp.plants.get(player.plantIndex).worldY, new Wood(1), gp));
        //                 player.gp.plants.remove(player.plantIndex);
        //                 player.plantIndex = -1; 
        //             }
        //             playSE(6); // Play axe sound effect
        //         } else {
        //             System.out.println("No plant selected to use the axe on!");
        //         }
        //     } else if (selectedItem instanceof Sword) {
        //         Sword sword = (Sword) selectedItem;
        //         player.isCutting = true;
        //         player.cutting();
        //         if (player.animalIndex != -1) {
        //             int damage = sword.damage;
        //             System.out.println(sword.damage);
        //             Animal animal = player.gp.animals.get(player.animalIndex);
        //             if(animal instanceof Chicken) {
        //                 Chicken chicken = (Chicken)animal;
        //                 chicken.hp -= damage;
        //                 System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
        //                 if(chicken.hp <= 0) {
        //                     player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawChicken(1), gp));
        //                     player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new Feather(rand.nextInt(3) + 1), gp));
        //                     player.gp.animals.remove(player.animalIndex);
        //                     player.gainExp(rand.nextInt(10) + 5);
        //                     player.animalIndex = -1;
        //                 }
        //             }
        //             else if(animal instanceof Pig) {
        //                 Pig pig = (Pig)animal;
        //                 pig.hp -= damage;
        //                 System.out.println("Hit pig: " + pig.hp + "/" + 80);
        //                 if(pig.hp <= 0) {
        //                     player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawPork(1), gp));
        //                     player.gp.animals.remove(player.animalIndex);
        //                     player.gainExp(rand.nextInt(10) + 7);
        //                     player.animalIndex = -1;
        //                 }
        //             }
        //             else if(animal instanceof Sheep) {
        //                 Sheep sheep = (Sheep)animal;
        //                 sheep.hp -= damage;
        //                 System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
        //                 if(sheep.hp <= 0) {
        //                     player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMutton(1), gp));
        //                     player.gp.animals.remove(player.animalIndex);
        //                     player.gainExp(rand.nextInt(10) + 8);
        //                     player.animalIndex = -1;
        //                 }
        //             }
        //             else if(animal instanceof Cow) {
        //                 Cow cow = (Cow)animal;
        //                 cow.hp -= damage;
        //                 System.out.println("Hit cow: " + cow.hp + "/" + 100);
        //                 if(cow.hp <= 0) {
        //                     player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMeat(1), gp));
        //                     player.gp.animals.remove(player.animalIndex);
        //                     player.gainExp(rand.nextInt(10) + 9);
        //                     player.animalIndex = -1;
        //                 }
        //             }
        //             playSE(4); // Play sword sound effect
        //         }
            else {
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
