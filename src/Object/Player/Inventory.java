package Object.Player;
import Object.Items.Item;
import Object.Items.ConsumableItem.*;

public class Inventory {
    public Item slots[];

    public Inventory(int n) {
        this.slots = new Item[n];
    }

    public void addItem(Item newItem) {
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null && newItem.name.equals(slots[i].name)) {
                if(slots[i].currentStack < newItem.maxStack && newItem instanceof Stackable) {
                    slots[i].currentStack++;
                    
                    System.out.println("Added " + newItem.name + " to inventory.");
                    break;
                } 
                
                if (i < slots.length - 1) {
                    continue;
                } else {
                    System.out.println("Inventory full.");
                }
            }
            
            if(slots[i] == null) {
                slots[i] = newItem.clone();    
                slots[i].currentStack++;
                System.out.println("Added " + newItem.name + " to inventory.");
                break;    
            } 
            
            if(i == slots.length - 1) {
                System.out.println("Inventory full.");
            }
        }
    }

    public void addItems(Item newItem) {
        int temp = newItem.currentStack;
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null && newItem.name.equals(slots[i].name)) {
                if(slots[i].currentStack < newItem.maxStack && newItem instanceof Stackable) {
                    while(slots[i].currentStack < newItem.maxStack && newItem.currentStack > 0) {
                        slots[i].currentStack++;
                        newItem.currentStack--;
                    }
                } 
                
                if (i < slots.length - 1) {
                    continue;
                }
            }
            
            if(slots[i] == null && newItem.currentStack > 0) {
                if (newItem.currentStack > newItem.maxStack) {
                    newItem.currentStack -= newItem.maxStack;
                    slots[i] = newItem.clone();
                    slots[i].currentStack = newItem.maxStack;    
                } else {
                    slots[i] = newItem.clone(); 
                    newItem.currentStack = 0;   
                }
                continue;  
            } 
        }
        if (newItem.currentStack > 0) {
            System.out.println("Inventory full. " + newItem.name + " x" + newItem.currentStack + " not added to inventory.");
        } else {
            System.out.println("Added " + temp + " " + newItem.name + " to inventory.");
        }
    }

    public void showInventory() {
        System.out.println("=======My Inventory========");
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null) {
                System.out.println(i + 1 + " " + slots[i].name + " x" + slots[i].currentStack);
                continue;
            } 
            System.out.println(i + 1 + " Empty");
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                return false;
            }
        }
        return true;
    }

    public boolean hasItem(String itemName) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].name.equals(itemName)) {
                return true;
            }
        }
        return false;
    }

    public void removeItem(String itemName) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].name.equals(itemName)) {
                if (slots[i].currentStack > 1) {
                    slots[i].currentStack--;
                    System.out.println("Removed one " + itemName + " from inventory.");
                } else {
                    slots[i] = null;
                    System.out.println("Removed " + itemName + " from inventory.");
                }
                return;
            }
        }
        System.out.println(itemName + " not found in inventory.");
    }
}
