package Object;

public class Inventory {
    public Item slots[];
    public int MAX_STACK = 10;

    public Inventory(int n) {
        this.slots = new Item[n];
    }

    public void addItem(Item newItem) {
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null && newItem.name.equals(slots[i].name)) {
                if(slots[i].currentStack < MAX_STACK && newItem instanceof Consumable) {
                    slots[i].currentStack++;
                    System.out.println("Added " + newItem.name + " to inventory.");
                    break;
                } else if (i < slots.length - 1) {
                    continue;
                } else {
                    System.out.println("Inventory full.");
                }
            } else if(slots[i] == null) {
                slots[i] = new Item(newItem.name);
                slots[i].currentStack++;
                System.out.println("Added " + newItem.name + " to inventory.");
                break;    
            } else if(i == slots.length - 1) {
                System.out.println("Inventory full.");
            }
        }
    }

    public void showInventory() {
        System.out.println("=======My Inventory========");
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null) {
                System.out.println(i + 1 + " " + slots[i].name + " x" + slots[i].currentStack);
            } else {
                System.out.println(i + 1 + " Empty");
            }
        }
    }

    public void use(int slot) {
        if(slots[slot] != null && slots[slot] instanceof Consumable) {
            slots[slot].currentStack--;
            if(slots[slot].currentStack == 0) {
                slots[slot] = null;
            }
        }
    }
}
