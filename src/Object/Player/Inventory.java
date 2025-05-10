package Object.Player;
import Object.Controller.GamePanel;
import Object.Items.Item;
import Object.Items.StackableItem.*;

public class Inventory {
    public Item slots[];
    public GamePanel gp;

    public Inventory(int n, GamePanel gp) {
        this.slots = new Item[n];
        this.gp = gp;
    }

    public void addItems(Item newItem) {
        int temp = newItem.currentStack;
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && newItem.name.equals(slots[i].name)) {
                if (slots[i].currentStack < newItem.maxStack && newItem instanceof Stackable) {
                    while(slots[i].currentStack < newItem.maxStack && newItem.currentStack > 0) {
                        slots[i].currentStack++;
                        newItem.currentStack--;
                    }
                } 
                
                if (i < slots.length - 1) {
                    continue;
                }
            }
            if (slots[i] == null && newItem.currentStack > 0) {
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
            gp.player.dropItem(newItem);
        } else {
            System.out.println("Added " + temp + " " + newItem.name + " to inventory.");
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

    public void removeItem(Item selecItem, int amount) {
        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null && slots[i].equals(selecItem)) {
                if (slots[i].currentStack > 1) {
                    slots[i].currentStack -= amount;
                    System.out.println("Removed one " + selecItem.name + " from inventory.");
                } 
                if (slots[i].currentStack <= 1) {
                    slots[i] = null;
                    System.out.println("Removed " + selecItem.name + " from inventory.");
                }
                return;
            }
        }
        System.out.println(selecItem.name + " not found in inventory.");
    }

    public void swapItems(int index1, int index2) {
        Item temp = slots[index1];
        slots[index1] = slots[index2];
        slots[index2] = temp;
    }
    public Item getSelectedItem() {
        return slots[gp.ui.selectedIndex];
    }
}
