package Objek.Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import Objek.Animal.Animal;
import Objek.Animal.Chicken;
import Objek.Animal.Cow;
import Objek.Animal.Pig;
import Objek.Animal.Sheep;
import Objek.Animal.Wolf;
import Objek.Enemy.Monster;
import Objek.Items.Item;
import Objek.Items.Buildings.*;
import Objek.Items.StackableItem.Bucket;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Fruits.Berries;
import Objek.Items.StackableItem.Foods.Fruits.Coconut;
import Objek.Items.StackableItem.Foods.Fruits.Guava;
import Objek.Items.StackableItem.Foods.Fruits.Mango;
import Objek.Items.StackableItem.Foods.Other.Carrot;
import Objek.Items.StackableItem.Foods.RawFoods.Potato;
import Objek.Items.StackableItem.Foods.RawFoods.RawChicken;
import Objek.Items.StackableItem.Foods.RawFoods.RawMeat;
import Objek.Items.StackableItem.Foods.RawFoods.RawMutton;
import Objek.Items.StackableItem.Foods.RawFoods.RawPork;
import Objek.Items.StackableItem.Materials.Material;
import Objek.Items.StackableItem.Materials.Wheat;
import Objek.Items.StackableItem.Materials.AnimalDrops.Feather;
import Objek.Items.StackableItem.Materials.AnimalDrops.WolfHide;
import Objek.Items.StackableItem.Materials.AnimalDrops.Wool;
import Objek.Items.StackableItem.Materials.ForgedComponents.GoldIngot;
import Objek.Items.StackableItem.Materials.ForgedComponents.MetalIngot;
import Objek.Items.StackableItem.Materials.Fuels.Wood;
import Objek.Items.StackableItem.Materials.OreDrops.Crystal;
import Objek.Items.StackableItem.Materials.OreDrops.Gem;
import Objek.Items.StackableItem.Materials.OreDrops.Gold;
import Objek.Items.StackableItem.Materials.OreDrops.Metal;
import Objek.Items.StackableItem.Materials.OreDrops.Stone;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.Lantern;
import Objek.Items.Unstackable.WateringCan;
import Objek.Items.Unstackable.WinterCrown;
import Objek.Items.Unstackable.Armor.Boots.Boots;
import Objek.Items.Unstackable.Armor.Chestplate.Chestplate;
import Objek.Items.Unstackable.Armor.Helmet.Helmet;
import Objek.Items.Unstackable.Armor.Leggings.Leggings;
import Objek.Items.Unstackable.Arsenals.Arsenal;
import Objek.Items.Unstackable.Arsenals.Axe;
import Objek.Items.Unstackable.Arsenals.Club;
import Objek.Items.Unstackable.Arsenals.FlimsyPickaxe;
import Objek.Items.Unstackable.Arsenals.HaasClaws;
import Objek.Items.Unstackable.Arsenals.Pickaxe;
import Objek.Items.Unstackable.Arsenals.Sword;
import Objek.Ore.CrystalOre;
import Objek.Ore.GemOre;
import Objek.Ore.GoldOre;
import Objek.Ore.MetalOre;
import Objek.Ore.Ore;
import Objek.Ore.Rock;
import Objek.Enemy.Bat;
import Objek.Enemy.Golem;
import Objek.Plant.*;
import Objek.Plant.Bushes.BerryBush;
import Objek.Plant.Bushes.Bush;
import Objek.Plant.Trees.GuavaTree;
import Objek.Plant.Trees.MangoTree;
import Objek.Plant.Trees.PalmTree;
import Objek.Plant.Trees.Tree;
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
            if (selectedItem instanceof WinterCrown && (gp.currentMap == 0 || gp.currentMap == 2)) {
                if (!player.isFrozen){
                    player.isFrozen = true;
                    int radius = 210;
                    int wolves = 0;
                    for (Animal animal : new ArrayList<>(gp.animals)) {
                        int dx = animal.worldX - gp.player.worldX;
                        int dy = animal.worldY - gp.player.worldY;
                        if (dx * dx + dy * dy <= radius * radius && animal instanceof Wolf) {
                            wolves++;
                        }
                    }
                    if (wolves == 3){
                        gp.player.hasDodgedWolves = true;
                    }
                } else {
                    player.isFrozen = false;
                }
            } else if (selectedItem instanceof Helmet) {
                if (gp.player.helmet != null){
                    Helmet tempHelmet = gp.player.helmet;
                    gp.player.helmet = (Helmet) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1);
                    gp.player.inventory.slots[gp.ui.selectedIndex] = tempHelmet.clone(); // Update selected slot
                } else {
                    gp.player.helmet = (Helmet) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1); // Remove helmet from inventory
                }
                playSE(8); // Play armor equip sound
                System.out.println("Equipping helmet: " + selectedItem.name);

            } else if (selectedItem instanceof Chestplate){
                if (gp.player.chestplate != null){
                    Chestplate tempChestplate = gp.player.chestplate;
                    gp.player.chestplate = (Chestplate) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1);
                    gp.player.inventory.slots[gp.ui.selectedIndex] = tempChestplate.clone(); // Update selected slot
                } else {
                    gp.player.chestplate = (Chestplate) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1); // Remove chestplate from inventory
                }
                playSE(8);
                System.out.println("Equipping chestplate: " + selectedItem.name);

            } else if (selectedItem instanceof Leggings){
                if (gp.player.leggings != null){
                    Leggings tempLeggings = gp.player.leggings;
                    gp.player.leggings = (Leggings) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1);
                    gp.player.inventory.slots[gp.ui.selectedIndex] = tempLeggings.clone(); // Update selected slot
                } else {
                    gp.player.leggings = (Leggings) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1); // Remove leggings from inventory
                }
                playSE(8);
                System.out.println("Equipping leggings: " + selectedItem.name);

            } else if (selectedItem instanceof Boots){
                if (gp.player.boots != null){
                    Boots tempBoots = gp.player.boots;
                    gp.player.boots = (Boots) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1);
                    gp.player.inventory.slots[gp.ui.selectedIndex] = tempBoots.clone(); // Update selected slot
                } else {
                    gp.player.boots = (Boots) selectedItem.clone();
                    player.inventory.removeItem(selectedItem, 1); // Remove boots from inventory
                }
                playSE(8);
                System.out.println("Equipping boots: " + selectedItem.name);

            } else if (selectedItem instanceof Bucket) { 
                Bucket bucket = (Bucket) selectedItem;
                if (!bucket.status.equals("empty")) {
                    System.out.println("Drinking from the bucket.");
                    bucket.drink();
                    if (!bucket.status.equals("water")) {
                        playSE(17); // Play drinking sound
                    }
                } else if (gp.player.isNearWater()) {
                    bucket.fillWater();
                } else if (gp.player.isNearMilkableAnimal() && bucket.status.equals("empty")) {
                    bucket.fillMilk();
                } else {
                    System.out.println("Bucket is already filled with " + bucket.status + ".");
                }

            } else if (selectedItem instanceof Wood || selectedItem instanceof Stone || selectedItem instanceof GoldIngot || selectedItem instanceof MetalIngot) {
                System.out.println("Using material: " + selectedItem.name);
            } else if (selectedItem instanceof WateringCan) {
                WateringCan wateringCan = (WateringCan) selectedItem;
                if (player.buildingIndex != -1){
                    System.out.println("Using unstackable item: " + wateringCan.name);
                    if (player.gp.buildings.get(player.buildingIndex) instanceof Orchard) {
                        Orchard orchard = (Orchard) player.gp.buildings.get(player.buildingIndex);
                        if (orchard.seed != null) {
                            wateringCan.durability -= 5;

                            System.out.println("Watering the seed: " + orchard.seed.name);
                            orchard.water();
                        } else {
                            System.out.println("No seed planted in the orchard!");
                        }
                    } else if (player.gp.buildings.get(player.buildingIndex) instanceof GardenPatch) {
                        GardenPatch gardenPatch = (GardenPatch) player.gp.buildings.get(player.buildingIndex);
                        if (gardenPatch.seed != null) {
                            wateringCan.durability -= 5;
                            System.out.println("Watering the seed: " + gardenPatch.seed.name);
                            gardenPatch.water();
                        } else {
                            System.out.println("No seed planted in the garden patch!");
                        }
                    } else {
                        System.out.println("No orchard/garden patch selected to use the watering can on!");
                    }

                    if (wateringCan.durability <= 0) {
                        System.out.println("Watering can is broken!");
                        player.inventory.removeItem(wateringCan, 1); // Remove the watering can from inventory
                        return;
                    }
                }
            } else if (selectedItem instanceof Lantern) {
                Lantern torch = (Lantern) selectedItem;
                System.out.println("Using torch: " + torch.name);
                torch.isLit = !torch.isLit; // Toggle the torch state
                try {
                    torch.img = torch.isLit 
                        ? ImageIO.read(new File("ProjectTheSurvivalist/res/Items/lightItems/lanternon.png")) 
                        : ImageIO.read(new File("ProjectTheSurvivalist/res/Items/lightItems/lanternoff.png"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            } else if (selectedItem instanceof Seeds && gp.player.buildingIndex != -1 && gp.buildings.get(gp.player.buildingIndex) instanceof Orchard) {
                Seeds seed = (Seeds) selectedItem;
                Orchard orchard = (Orchard) gp.buildings.get(gp.player.buildingIndex);
                if (orchard.seed != null) {
                    System.out.println("Orchard already has a seed planted!");
                    return;
                }
                if (seed instanceof Seeds){
                    System.out.println("You cannot plant seeds in an orchard! Please use a fruit seed.");
                    return;
                }
                orchard.plant(seed);
                System.out.println("Using seed: " + seed.name);
                gp.player.inventory.removeItem(seed, 1); // Remove seed from inventory
            } else if ((selectedItem instanceof Seeds || selectedItem instanceof Carrot || selectedItem instanceof Potato) && gp.player.buildingIndex != -1) {
                if (gp.buildings.get(gp.player.buildingIndex) instanceof GardenPatch){
                    Item seed = selectedItem;
                    playSE(23);
                    GardenPatch gardenPatch = (GardenPatch) gp.buildings.get(gp.player.buildingIndex);
                    if (gardenPatch.seed != null) {
                        System.out.println("Garden patch already has a seed planted!");
                        return;
                    }
                    gardenPatch.plant(seed);
                    System.out.println("Using seed: " + seed.name);
                    gp.player.inventory.removeItem(seed, 1); // Remove seed from inventory
                }
            } else if (selectedItem instanceof Material) {
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
                food.eat(player);
                playSE(1);
                selectedItem.currentStack--;
                if (selectedItem.currentStack <= 0) {
                    player.inventory.removeItem(selectedItem, 1); // Remove item from inventory
                }
            } else if (selectedItem instanceof Arsenal){
                Arsenal arsenal = (Arsenal) selectedItem;
                player.isCutting = true;
                player.cutting();
                
                if (player.plantIndex != -1 && gp.currentMap == 0) {
                    Plant plant = player.gp.plants.get(player.plantIndex);

                    plant.hp -= arsenal.damage;
                    if (selectedItem instanceof Axe){
                        System.out.println("Using sword: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (selectedItem instanceof Sword || selectedItem instanceof Pickaxe || selectedItem instanceof HaasClaws){
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
                        if (plant instanceof Tree){
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Wood(rand.nextInt(4) + 4), gp));
                            if (plant instanceof GuavaTree) {
                                player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Guava(rand.nextInt(2) + 1), gp));
                            } else if (plant instanceof MangoTree) {
                                player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Mango(rand.nextInt(2) + 1), gp));
                            } else if (plant instanceof PalmTree) {
                                player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Coconut(rand.nextInt(2) + 1), gp));
                            }
                            player.totalTreesCut++;
                        }

                        if (plant instanceof Bush){
                            if (rand.nextInt(5) == 0) {
                                player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Seeds(1), gp));
                            } if (plant instanceof BerryBush) {
                                player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, Berries.createBerries(rand.nextInt(3) + 1, rand.nextInt(2) + 1), gp));
                            }
                            player.totalBushesCut++;
                        }
                        player.gp.plants.remove(player.plantIndex);
                        player.plantIndex = -1; 
                    }
                    playSE(6);
                } else if (player.animalIndex != -1) {
                    Animal animal = player.gp.animals.get(player.animalIndex);
                    animal.hp -= arsenal.damage;
                    int damage = arsenal.damage;

                    if (selectedItem instanceof Pickaxe){
                        System.out.println("Using pickaxe: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else if (selectedItem instanceof HaasClaws){
                        System.out.println("Using Haas Claws");
                        if (animal instanceof Wolf) {
                            player.health += arsenal.damage / 2;
                        } else {
                            player.health += 2;
                        }
                        if (player.health > player.maxHealth) {
                            player.health = player.maxHealth;
                        }
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else {
                        System.out.println("Using axe/sword/club: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }

                    if (animal instanceof Chicken) {
                        Chicken chicken = (Chicken)animal;
                        chicken.hp -= damage;
                        System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
                        if(chicken.hp <= 0) {
                            gp.player.totalAnimalsKilled++;
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
                            gp.player.totalAnimalsKilled++;
                            player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60), animal.worldY + 64, new RawPork(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 7);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Sheep) {
                        Sheep sheep = (Sheep)animal;
                        sheep.hp -= damage;
                        System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
                        if(sheep.hp <= 0) {
                            gp.player.totalAnimalsKilled++;
                            player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60) - 15, animal.worldY + 64, new RawMutton(1), gp));
                            player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60) + 15, animal.worldY + 64, new Wool(rand.nextInt(2) + 1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 8);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Cow) {
                        Cow cow = (Cow)animal;
                        cow.hp -= damage;
                        System.out.println("Hit cow: " + cow.hp + "/" + 100);
                        if(cow.hp <= 0) {
                            gp.player.totalAnimalsKilled++;
                            player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60), animal.worldY + 64, new RawMeat(1), gp));
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.animalIndex = -1;
                        }
                    } else if(animal instanceof Wolf) {
                        Wolf wolf = (Wolf)animal;
                        wolf.hp -= damage;
                        System.out.println("Hit wolf: " + wolf.hp + "/" + 100);
                        if(wolf.hp <= 0) {
                            gp.player.totalAnimalsKilled++;
                            if (rand.nextInt(10) < 2) {
                                player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new WolfHide(1), gp));
                            }
                            player.gp.animals.remove(player.animalIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.animalIndex = -1;
                        }
                    } 
                    playSE(4);
                } else if (player.monsterIndex != -1){
                    Monster monster = player.gp.monsters.get(player.monsterIndex);
                    monster.hp -= arsenal.damage;
                    int damage = arsenal.damage;

                    if (selectedItem instanceof HaasClaws){
                        System.out.println("Using Haas Claws");
                        player.health += arsenal.damage / 2;
                        
                        if (player.health > player.maxHealth) {
                            player.health = player.maxHealth;
                        }
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else if (selectedItem instanceof Pickaxe){
                        System.out.println("Using pickaxe: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else {
                        System.out.println("Using axe/sword/club: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }
                    if(monster instanceof Bat){
                        Bat bat = (Bat)monster;
                        bat.hp -= damage;
                        System.out.println("Hit bat: " + bat.hp + "/" + 30);
                        if(bat.hp <= 0) {
                            // player.gp.droppedItems.add(new ItemDrop(monster.worldX, monster.worldY, new RawMeat(1), gp));
                            gp.player.totalMonstersKilled++;
                            player.gp.monsters.remove(player.monsterIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.monsterIndex = -1;
                        }
                    } else if (monster instanceof Golem){
                        Golem golem = (Golem)monster;
                        golem.hp -= damage;
                        System.out.println("Hit golem: " + golem.hp + "/" + 300);
                        if(golem.hp <= 0) {
                            gp.player.totalMonstersKilled++;
                            player.gp.droppedItems.add(new ItemDrop(monster.worldX + 64, monster.worldY + 64, new MetalIngot(rand.nextInt(2) + 1), gp));
                            player.gp.monsters.remove(player.monsterIndex);
                            player.gainExp(rand.nextInt(20) + 35);
                            player.monsterIndex = -1;
                        }
                    }
                    playSE(4);
                } else if (player.buildingIndex != -1 && player.gp.buildings.get(player.buildingIndex).isBreakable) {
                    Buildings building = player.gp.buildings.get(player.buildingIndex);

                    if (building instanceof KandangAyam){
                        KandangAyam k = (KandangAyam) building;

                        if (k.chickensInCage.size() > 0){
                            System.out.println("Unable to use arsenal on Kandang Ayam with chickens inside!");
                            return;
                        }
                    }

                    if (building instanceof PigCage){
                        PigCage p = (PigCage) building;

                        if (p.pigsInCage.size() > 0){
                            System.out.println("Unable to use arsenal on Pig Cage with pigs inside!");
                            return;
                        }
                    }

                    if (building instanceof SheepCage){
                        SheepCage s = (SheepCage) building;

                        if (s.sheepsInCage.size() > 0){
                            System.out.println("Unable to use arsenal on Sheep Cage with sheeps inside!");
                            return;
                        }
                    }

                    if (building instanceof CowCage){
                        CowCage c = (CowCage) building;

                        if (c.cowsInCage.size() > 0){
                            System.out.println("Unable to use arsenal on Cow Cage with cows inside!");
                            return;
                        }
                    }

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
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Chest(gp, 1, 0), gp));
                            Chest chest = (Chest) building;
                            for (int i = 0; i < chest.inventory.slots.length; i++) {
                                int scatterX = rand.nextInt(75) - 30;
                                int scatterY = rand.nextInt(30) - 30;
                                if (chest.inventory.slots[i] != null) {
                                    player.gp.droppedItems.add(new ItemDrop(building.worldX + scatterX, building.worldY + scatterY, chest.inventory.slots[i], gp));
                                    chest.inventory.slots[i] = null;
                                }
                            }
                        } else if (building instanceof Kandang){
                            if (building instanceof KandangAyam) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new KandangAyam(gp, 0), gp));
                            } else if (building instanceof PigCage) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new PigCage(gp, 0), gp));
                            } else if (building instanceof SheepCage) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new SheepCage(gp, 0), gp));
                            } else if (building instanceof CowCage) {
                                player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new CowCage(gp, 0), gp));
                            }
                        } else if (building instanceof CraftingTable) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new CraftingTable(gp, 1, 0), gp));
                        } else if (building instanceof Orchard) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Orchard(gp, 1, 0), gp));
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
                        } else if (building instanceof GardenPatch) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new GardenPatch(gp, 1, 0), gp));
                            GardenPatch gardenPatch = (GardenPatch) building;
                            if (gardenPatch.seed != null) {
                                if (gardenPatch.seed instanceof Seeds){
                                    if (gardenPatch.phase.equals("crops")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Wheat(1), gp));
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Seeds(rand.nextInt(2) + 1), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Seeds(1), gp));
                                    }
                                } else if (gardenPatch.seed instanceof Potato){
                                    if (gardenPatch.phase.equals("crops")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Potato(rand.nextInt(2) + 2), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Potato(1), gp));
                                    }
                                } else if (gardenPatch.seed instanceof Carrot){
                                    if (gardenPatch.phase.equals("crops")) {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX - 20, building.worldY, new Carrot(rand.nextInt(2) + 2), gp));
                                    } else {
                                        player.gp.droppedItems.add(new ItemDrop(building.worldX + 20, building.worldY, new Carrot(1), gp));
                                    }
                                }
                            }
                        } else if (building instanceof Bed) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Bed(gp, 1, 0), gp));
                        } else if (building instanceof Furnace) {
                            player.gp.droppedItems.add(new ItemDrop(building.worldX, building.worldY, new Furnace(gp, 1, 0), gp));
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
                } else if (gp.player.oreIndex != -1 && gp.ores.size() > 0) {
                    if(player.inventory.getSelectedItem() instanceof Pickaxe) {
                        Pickaxe pickaxe = (Pickaxe)player.inventory.getSelectedItem();
                        Ore ore = gp.ores.get(player.oreIndex);
                        
                        if (ore instanceof CrystalOre || ore instanceof GemOre){
                            if (pickaxe instanceof FlimsyPickaxe){
                                System.out.println("You need a Lightweight Pickaxe to mine Crystal or Gem Ore! (Or a stronger pickaxe)");
                                return;
                            }
                        }

                        ore.hp -= pickaxe.damage;
                        pickaxe.durability--;
                        
                        if(pickaxe.durability <= 0) {
                            player.inventory.removeItem(pickaxe, 1);
                        }
                        
                        if(ore.hp <= 0) {
                            if(ore instanceof Rock) {
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Stone(rand.nextInt(3) + 2), gp));
                            } else {
                                if(ore instanceof GoldOre) {
                                    gp.droppedItems.add(new ItemDrop(ore.worldX - 10, ore.worldY, new Gold(rand.nextInt(2) + 1), gp));
                                } else if(ore instanceof MetalOre) {
                                    gp.droppedItems.add(new ItemDrop(ore.worldX - 10, ore.worldY, new Metal(rand.nextInt(2) + 1), gp));
                                } else if (ore instanceof CrystalOre){
                                    gp.droppedItems.add(new ItemDrop(ore.worldX - 10, ore.worldY, new Crystal(rand.nextInt(2) + 1), gp));
                                } else if (ore instanceof GemOre) {
                                    gp.droppedItems.add(new ItemDrop(ore.worldX - 10, ore.worldY, new Gem(rand.nextInt(2) + 1), gp));
                                }
                                gp.droppedItems.add(new ItemDrop(ore.worldX + 10, ore.worldY, new Stone(rand.nextInt(2) + 1), gp));
                            }
                            player.totalOresMined++;
                            gp.ores.remove(player.oreIndex);
                            player.oreIndex = -1;
                        }
                    }
                } else {
                    System.out.println("No plant or animal selected to use the arsenal on!");
                }
                if (arsenal.durability <= 0) {
                    player.inventory.removeItem(arsenal, 1); // Remove item from inventory
                }
            } else {
                System.out.println("Unknown item type!"); 
            }
        } else if (selectedItem == null && gp.currentMap == 0) {
            // Punching with bare hands
            int fistDamage = 4;
            if (player.plantIndex != -1) {
                Plant plant = player.gp.plants.get(player.plantIndex);
                plant.hp -= fistDamage;
                System.out.println("Punching plant with bare hands! Plant HP: " + plant.hp);
                if (plant.hp <= 0) {
                    if (plant instanceof Tree){
                        player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Wood(rand.nextInt(4) + 4), gp));
                        if (plant instanceof GuavaTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Guava(rand.nextInt(2) + 1), gp));
                        } else if (plant instanceof MangoTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Mango(rand.nextInt(2) + 1), gp));
                        } else if (plant instanceof PalmTree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, new Coconut(rand.nextInt(2) + 1), gp));
                        }
                        player.totalTreesCut++;
                    }

                    if (plant instanceof Bush){
                        if (rand.nextInt(5) == 0) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Seeds(1), gp));
                        } if (plant instanceof BerryBush) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, Berries.createBerries(rand.nextInt(3) + 1, rand.nextInt(2) + 1), gp));
                        }
                        player.totalBushesCut++;
                    }
                    player.gp.plants.remove(player.plantIndex);
                    player.plantIndex = -1; 
                }
                playSE(6);
            } else if (player.animalIndex != -1) {
                Animal animal = player.gp.animals.get(player.animalIndex);
                if (animal instanceof Chicken) {
                    Chicken chicken = (Chicken)animal;
                    chicken.hp -= fistDamage;
                    System.out.println("Hit chicken: " + chicken.hp + "/" + 60);
                    if(chicken.hp <= 0) {
                        player.totalAnimalsKilled++;
                        player.gp.droppedItems.add(new ItemDrop(animal.worldX + 20, animal.worldY, new RawChicken(1), gp));
                        player.gp.droppedItems.add(new ItemDrop(animal.worldX - 20, animal.worldY, new Feather(rand.nextInt(3) + 1), gp));
                        player.gp.animals.remove(player.animalIndex);
                        player.gainExp(rand.nextInt(10) + 5);
                        player.animalIndex = -1;
                    }
                } else if (animal instanceof Pig) {
                    Pig pig = (Pig)animal;
                    pig.hp -= fistDamage;
                    System.out.println("Hit pig: " + pig.hp + "/" + 80);
                    if(pig.hp <= 0) {
                        player.totalAnimalsKilled++;
                        player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60), animal.worldY + 64, new RawPork(1), gp));
                        player.gp.animals.remove(player.animalIndex);
                        player.gainExp(rand.nextInt(10) + 7);
                        player.animalIndex = -1;
                    }
                } else if(animal instanceof Sheep) {
                    Sheep sheep = (Sheep)animal;
                    sheep.hp -= fistDamage;
                    System.out.println("Hit sheep: " + sheep.hp + "/" + 70);
                    if(sheep.hp <= 0) {
                        player.totalAnimalsKilled++;
                        player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60) - 15, animal.worldY + 64, new RawMutton(1), gp));
                        player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60) + 15, animal.worldY + 64, new Wool(rand.nextInt(2) + 1), gp));
                        player.gp.animals.remove(player.animalIndex);
                        player.gainExp(rand.nextInt(10) + 8);
                        player.animalIndex = -1;
                    }
                } else if(animal instanceof Cow) {
                    Cow cow = (Cow)animal;
                    cow.hp -= fistDamage;
                    System.out.println("Hit cow: " + cow.hp + "/" + 100);
                    if(cow.hp <= 0) {
                        player.totalAnimalsKilled++;
                        player.gp.droppedItems.add(new ItemDrop((animal.worldX + 60), animal.worldY + 64, new RawMeat(1), gp));
                        player.gp.animals.remove(player.animalIndex);
                        player.gainExp(rand.nextInt(10) + 9);
                        player.animalIndex = -1;
                    }
                } else if (animal instanceof Wolf) {
                    Wolf wolf = (Wolf)animal;
                    wolf.hp -= fistDamage;
                    System.out.println("Hit wolf: " + wolf.hp + "/" + 100);
                    if(wolf.hp <= 0) {
                        player.totalAnimalsKilled++;
                        if (rand.nextInt(10) < 2) {
                            player.gp.droppedItems.add(new ItemDrop(animal.worldX, animal.worldY, new WolfHide(1), gp));
                        }
                        player.gp.animals.remove(player.animalIndex);
                        player.gainExp(rand.nextInt(10) + 9);
                        player.animalIndex = -1;
                    }
                }
                playSE(4);
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
