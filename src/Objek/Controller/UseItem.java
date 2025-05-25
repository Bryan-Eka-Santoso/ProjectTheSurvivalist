package Objek.Controller;

import java.util.Random;
import Objek.Animal.Animal;
import Objek.Animal.Chicken;
import Objek.Animal.Cow;
import Objek.Animal.Pig;
import Objek.Animal.Sheep;
import Objek.Animal.Wolf;
import Objek.Enemy.Monster;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Foods.Coconut;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Guava;
import Objek.Items.StackableItem.Foods.Mango;
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
import Objek.Items.Unstackable.Torch;
import Objek.Items.Unstackable.WateringCan;
import Objek.Items.Unstackable.Arsenals.Arsenal;
import Objek.Items.Unstackable.Arsenals.Axe;
import Objek.Items.Unstackable.Arsenals.Club;
import Objek.Items.Unstackable.Arsenals.Pickaxe;
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
            if (selectedItem instanceof WateringCan) {
                System.out.println("Using unstackable item: " + selectedItem.name);
                if (player.buildingIndex != -1){
                    if (player.gp.buildings.get(player.buildingIndex) instanceof Orchard) {
                        Orchard orchard = (Orchard) player.gp.buildings.get(player.buildingIndex);
                        if (orchard.seed != null) {
                            System.out.println("Watering the seed: " + orchard.seed.name);
                            orchard.water();
                        } else {
                            System.out.println("No seed planted in the orchard!");
                        }
                    } else {
                        System.out.println("No orchard selected to use the watering can on!");
                    }
                }
            } else if (selectedItem instanceof Torch) {
                Torch torch = (Torch) selectedItem;
                System.out.println("Using torch: " + torch.name);
                
            }
            if (selectedItem instanceof Seeds && gp.buildings.get(gp.player.buildingIndex) instanceof Orchard) {
                Seeds seed = (Seeds) selectedItem;
                Orchard orchard = (Orchard) gp.buildings.get(gp.player.buildingIndex);
                if (orchard.seed != null) {
                    System.out.println("Orchard already has a seed planted!");
                    return;
                }
                orchard.plant(seed);
                System.out.println("Using seed: " + seed.name);
                gp.player.inventory.removeItem(seed, 1); // Remove seed from inventory
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

                    if (selectedItem instanceof Sword || selectedItem instanceof Pickaxe){
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
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Guava(rand.nextInt(2) + 1), gp));
                        } else if (plant instanceof MangoTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Mango(rand.nextInt(2) + 1), gp));
                        } else if (plant instanceof PalmTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Coconut(rand.nextInt(2) + 1), gp));
                        }
                        player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Wood(rand.nextInt(4) + 4), gp));
                        player.gp.plants.remove(player.plantIndex);
                        player.plantIndex = -1; 
                    }
                    playSE(6);
                } else if (player.animalIndex != -1) {
                    Animal animal = player.gp.animals.get(player.animalIndex);
                    animal.hp -= arsenal.damage;
                    int damage = arsenal.damage;

                    if (selectedItem instanceof Axe || selectedItem instanceof Pickaxe){
                        System.out.println("Using axe/pickaxe: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else {
                        System.out.println("Using sword/club: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (animal instanceof Chicken) {
                        Chicken chicken = (Chicken)animal;
                        chicken.hp -= damage;
                        System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
                        if(chicken.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX + 20, animal.worldY, new RawChicken(1), gp));
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX - 20, animal.worldY, new Feather(rand.nextInt(3) + 1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 5);
                            player.animalIndex = -1;
                            player.gp.checkAndRespawnAnimals();
                        }
                    } else if (animal instanceof Pig) {
                        Pig pig = (Pig)animal;
                        pig.hp -= damage;
                        System.out.println("Hit pig: " + pig.hp + "/" + 80);
                        if(pig.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawPork(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 7);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Sheep) {
                        Sheep sheep = (Sheep)animal;
                        sheep.hp -= damage;
                        System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
                        if(sheep.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMutton(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 8);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Cow) {
                        Cow cow = (Cow)animal;
                        cow.hp -= damage;
                        System.out.println("Hit cow: " + cow.hp + "/" + 100);
                        if(cow.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMeat(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Wolf) {
                        Wolf wolf = (Wolf)animal;
                        wolf.hp -= damage;
                        System.out.println("Hit wolf: " + wolf.hp + "/" + 100);
                        if(wolf.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new RawMeat(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.animalIndex = -1;
                        }
                    }
                    playSE(4);
                } else if (player.buildingIndex != -1 && !(player.gp.buildings.get(player.buildingIndex) instanceof Shop)) {
                    Buildings building = player.gp.buildings.get(player.buildingIndex);
                    building.hp -= arsenal.damage;
                    System.out.println("Using arsenal on building: " + arsenal.name);
                    System.out.println("Building HP: " + building.hp);

                    if (selectedItem instanceof Pickaxe){
                        System.out.println("Using pickaxe: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else if (selectedItem instanceof Axe && !(building instanceof Furnace)) {
                        System.out.println("Using axe: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else {
                        System.out.println("Using sword/club: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (building.hp <= 0) {
                        if (building instanceof Chest) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Chest(gp, 1), gp));
                            Chest chest = (Chest) building;
                            for (int i = 0; i < chest.inventory.slots.length; i++) {
                                int scatterX = rand.nextInt(75) - 30;
                                int scatterY = rand.nextInt(30) - 30;
                                if (chest.inventory.slots[i] != null) {
                                    player.gp.droppedItems.add(new ItemDrop(building.worldX + scatterX, building.worldY + scatterY, chest.inventory.slots[i], gp));
                                    chest.inventory.slots[i] = null;
                                }
                            }
                        } else if (building instanceof CraftingTable) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new CraftingTable(gp, 1), gp));
                        } else if (building instanceof Orchard) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Orchard(gp, 1), gp));
                            Orchard orchard = (Orchard) building;
                            if (orchard.seed != null) {
                                if (orchard.seed instanceof GuavaSeeds){
                                    if (orchard.phase.equals("tree")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Guava(rand.nextInt(1) + 1), gp));
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Wood(rand.nextInt(4) + 4), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new GuavaSeeds(rand.nextInt(1) + 1), gp));
                                    }
                                } else if (orchard.seed instanceof CoconutSeeds){
                                    if (orchard.phase.equals("tree")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Coconut(rand.nextInt(1) + 1), gp));
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Wood(rand.nextInt(4) + 4), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new CoconutSeeds(rand.nextInt(1) + 1), gp));
                                    }
                                } else if (orchard.seed instanceof MangoSeeds){
                                    if (orchard.phase.equals("tree")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Mango(rand.nextInt(1) + 1), gp));
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Wood(rand.nextInt(4) + 4), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new MangoSeeds(rand.nextInt(1) + 1), gp));
                                    }
                                }
                            }
                        } else if (building instanceof Bed) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Bed(gp, 1), gp));
                        } else if (building instanceof Furnace) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Furnace(gp, 1), gp));
                            Furnace furnace = (Furnace) building;
                            if (furnace.rawMaterial[0] != null) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX + (rand.nextInt(40) - 20), building.worldY, furnace.rawMaterial[0], gp));
                            }
                            if (furnace.fuelMaterial[0] != null) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX + (rand.nextInt(40) - 20), building.worldY, furnace.fuelMaterial[0], gp));
                            }
                            if (furnace.cookedMaterial[0] != null) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX + (rand.nextInt(40) - 20), building.worldY, furnace.cookedMaterial[0], gp));
                            }
                        }
                        player.gp.buildings.remove(player.buildingIndex);
                        player.buildingIndex = -1; 
                    } 
                    playSE(6);
                } else if (player.monsterIndex != -1) { 
                    Monster monster = player.gp.monsters.get(player.monsterIndex);
                    monster.hp -= arsenal.damage;
                    if (monster.hp <= 0) {
                        System.out.println("Monster defeated: " + monster.name);
                        player.gp.monsters.remove(player.monsterIndex);
                        player.gainExp(rand.nextInt(10) + 5);
                        player.monsterIndex = -1;
                        
                    } else {
                        System.out.println("Using arsenal on monster: " + arsenal.name);
                        System.out.println("Monster HP: " + monster.hp);
                    }
                    playSE(4);
                } else {
                    System.out.println("No plant or animal selected to use the arsenal on!");
                }
                if (arsenal.durability <= 0) {
                    player.inventory.removeItem(arsenal, 1); // Remove item from inventory
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
