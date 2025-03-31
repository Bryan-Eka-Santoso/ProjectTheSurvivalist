package Object;
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
                if(slots[i].currentStack < newItem.maxStack && newItem instanceof Consumable) {
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
                slots[i] = new Item(newItem.name, newItem.maxStack);    
                slots[i].currentStack++;
                System.out.println("Added " + newItem.name + " to inventory.");
                break;    
            } 
            
            if(i == slots.length - 1) {
                System.out.println("Inventory full.");
            }
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
}
