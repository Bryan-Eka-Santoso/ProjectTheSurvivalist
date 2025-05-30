package Objek.Controller;

import java.io.File;
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
import Objek.Items.StackableItem.Foods.Berries;
import Objek.Items.StackableItem.Foods.Carrot;
import Objek.Items.StackableItem.Foods.Coconut;
import Objek.Items.StackableItem.Foods.Food;
import Objek.Items.StackableItem.Foods.Guava;
import Objek.Items.StackableItem.Foods.Mango;
import Objek.Items.StackableItem.Foods.Potato;
import Objek.Items.StackableItem.Foods.RawFoods.RawChicken;
import Objek.Items.StackableItem.Foods.RawFoods.RawMeat;
import Objek.Items.StackableItem.Foods.RawFoods.RawMutton;
import Objek.Items.StackableItem.Foods.RawFoods.RawPork;
import Objek.Items.StackableItem.Materials.Crystal;
import Objek.Items.StackableItem.Materials.Feather;
import Objek.Items.StackableItem.Materials.Gem;
import Objek.Items.StackableItem.Materials.Gold;
import Objek.Items.StackableItem.Materials.GoldIngot;
import Objek.Items.StackableItem.Materials.Material;
import Objek.Items.StackableItem.Materials.Metal;
import Objek.Items.StackableItem.Materials.MetalIngot;
import Objek.Items.StackableItem.Materials.Stone;
import Objek.Items.StackableItem.Materials.Wheat;
import Objek.Items.StackableItem.Materials.WolfHide;
import Objek.Items.StackableItem.Materials.Fuels.Wood;
import Objek.Items.StackableItem.Seeds.CoconutSeeds;
import Objek.Items.StackableItem.Seeds.GuavaSeeds;
import Objek.Items.StackableItem.Seeds.MangoSeeds;
import Objek.Items.StackableItem.Seeds.Seeds;
import Objek.Items.Unstackable.Lantern;
import Objek.Items.Unstackable.WateringCan;
import Objek.Items.Unstackable.Armor.Boots.Boots;
import Objek.Items.Unstackable.Armor.Chestplate.Chestplate;
import Objek.Items.Unstackable.Armor.Helmet.Helmet;
import Objek.Items.Unstackable.Armor.Leggings.Leggings;
import Objek.Items.Unstackable.Arsenals.Arsenal;
import Objek.Items.Unstackable.Arsenals.Axe;
import Objek.Items.Unstackable.Arsenals.Club;
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
            if (selectedItem instanceof Helmet) {
                gp.player.helmet = (Helmet) selectedItem.clone();
                player.inventory.removeItem(selectedItem, 1); // Remove helmet from inventory
                System.out.println("Equipping helmet: " + selectedItem.name);

            } else if (selectedItem instanceof Chestplate){
                gp.player.chestplate = (Chestplate) selectedItem.clone();
                player.inventory.removeItem(selectedItem, 1); // Remove chestplate from inventory
                System.out.println("Equipping chestplate: " + selectedItem.name);

            } else if (selectedItem instanceof Leggings){
                gp.player.leggings = (Leggings) selectedItem.clone();
                player.inventory.removeItem(selectedItem, 1); // Remove chestplate from inventory
                System.out.println("Equipping chestplate: " + selectedItem.name);

            } else if (selectedItem instanceof Boots){
                gp.player.boots = (Boots) selectedItem.clone();
                player.inventory.removeItem(selectedItem, 1); // Remove chestplate from inventory
                System.out.println("Equipping chestplate: " + selectedItem.name);

            } else if (selectedItem instanceof Bucket) { 
                Bucket bucket = (Bucket) selectedItem;
                if (gp.player.isNearWater()) {
                    Bucket waterBucket = new Bucket(1, gp);
                    waterBucket.fillWater();
                    gp.player.inventory.addItems(waterBucket); // Add water bucket to inventory
                    gp.player.inventory.removeItem(bucket, 1); // Remove empty bucket from inventory
                } else if (bucket.status.equals("water")) {
                    System.out.println("Drinking water from the bucket.");
                    bucket.drink();
                } else if (bucket.status.equals("milk")) {
                    System.out.println("Drinking milk from the bucket.");
                    bucket.drink();
                } else {
                    System.out.println("Bucket is already filled with " + bucket.status + ".");
                }

            } else if (selectedItem instanceof Wood || selectedItem instanceof Stone || selectedItem instanceof GoldIngot || selectedItem instanceof MetalIngot) {
                System.out.println("Using material: " + selectedItem.name);
            } else if (selectedItem instanceof WateringCan) {
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
                    } else if (player.gp.buildings.get(player.buildingIndex) instanceof GardenPatch) {
                        GardenPatch gardenPatch = (GardenPatch) player.gp.buildings.get(player.buildingIndex);
                        if (gardenPatch.seed != null) {
                            System.out.println("Watering the seed: " + gardenPatch.seed.name);
                            gardenPatch.water();
                        } else {
                            System.out.println("No seed planted in the garden patch!");
                        }
                    } else {
                        System.out.println("No orchard/garden patch selected to use the watering can on!");
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
                if (player.plantIndex != -1 && gp.currentMap == 0) {
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
                        } else if (plant instanceof BerryBush) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX + 20, plant.worldY, Berries.createBerries(rand.nextInt(3) + 1, rand.nextInt(2) + 1), gp));
                        }

                        if (plant instanceof Tree) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Wood(rand.nextInt(4) + 4), gp));
                        } else if (plant instanceof Bush && rand.nextInt(5) == 0) {
                            player.gp.droppedItems.add(new ItemDrop(plant.worldX - 20, plant.worldY, new Seeds(1), gp));
                        }
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

                    if (selectedItem instanceof Axe || selectedItem instanceof Pickaxe){
                        System.out.println("Using axe/pickaxe: " + arsenal.name);
                        arsenal.durability -= 3;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    } else {
                        System.out.println("Using sword/club: " + arsenal.name);
                        arsenal.durability--;
                        System.out.println("Arsenal durability: " + arsenal.durability);
                    }
                    if(monster instanceof Bat){
                        Bat bat = (Bat)monster;
                        bat.hp -= damage;
                        System.out.println("Hit bat: " + bat.hp + "/" + 30);
                        if(bat.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(monster.worldX, monster.worldY, new RawMeat(1), gp));
                            player.gp.monsters.remove(player.monsterIndex);
                            player.gainExp(rand.nextInt(10) + 9);
                            player.monsterIndex = -1;
                        }
                    }else if (monster instanceof Golem){
                        Golem golem = (Golem)monster;
                        golem.hp -= damage;
                        System.out.println("Hit golem: " + golem.hp + "/" + 200);
                        if(golem.hp <= 0) {
                            player.gp.droppedItems.add(new ItemDrop(monster.worldX, monster.worldY, new MetalIngot(rand.nextInt(2) + 1), gp));
                            player.gp.monsters.remove(player.monsterIndex);
                            player.gainExp(rand.nextInt(10) + 15);
                            player.monsterIndex = -1;
                        }
                    }
                } else if (player.buildingIndex != -1 && !(player.gp.buildings.get(player.buildingIndex) instanceof Shop) && !(player.gp.buildings.get(player.buildingIndex) instanceof Cave)) {
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
                } else if (gp.player.oreIndex != -1){
                    if(player.inventory.getSelectedItem() instanceof Pickaxe) {
                        Pickaxe pickaxe = (Pickaxe)player.inventory.getSelectedItem();
                        Ore ore = gp.ores.get(player.oreIndex);
                        
                        ore.hp -= pickaxe.damage;
                        pickaxe.durability--;
                        
                        if(pickaxe.durability <= 0) {
                            player.inventory.removeItem(pickaxe, 1);
                        }
                        
                        if(ore.hp <= 0) {
                            if(ore instanceof GoldOre) {
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Gold(1), gp));
                            } else if(ore instanceof MetalOre) {
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Metal(1), gp));
                            } else if (ore instanceof CrystalOre){
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Crystal(1), gp));
                            } else if (ore instanceof GemOre) {
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Gem(1), gp));
                            } else if(ore instanceof Rock) {
                                gp.droppedItems.add(new ItemDrop(ore.worldX, ore.worldY, new Stone(1), gp));
                            }
                            gp.ores.remove(player.oreIndex);
                            player.oreIndex = -1;
                        }
                    }
                } else if (player.buildingIndex != -1 && player.gp.buildings.get(player.buildingIndex) instanceof Shop) {
                    System.out.println("Using arsenal on shop is not allowed!");
                } else if (player.buildingIndex != -1 && player.gp.buildings.get(player.buildingIndex) instanceof Shop) {
                    System.out.println("Using arsenal on shop is not allowed!");
                } else if (player.buildingIndex != -1 && player.gp.buildings.get(player.buildingIndex) instanceof Cave) {
                    System.out.println("Using arsenal on cave is not allowed!");
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
