package Object;

public class Inventory {
    public Item slots[] = new Item[10];
    public final int MAX_STACK = 16;

    public void addItem(Item newItem) {
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null && newItem.name.equals(slots[i].name)) {
                if(slots[i].currentStack < MAX_STACK && newItem instanceof Consumable) {
                    slots[i].currentStack++;
                    System.out.println(slots[i].name + " x" + slots[i].currentStack);
                    break;
                } else {
                    continue;
                }
            } else {
                if(slots[i] == null) {
                    slots[i] = new Consumable(newItem.name);
                    slots[i].currentStack++;
                    System.out.println(slots[i].name + " x" + slots[i].currentStack);
                    break;
                }
            }
        }
    }

    public void showInventory() {
        System.out.println("Inventory:");
        for (int i = 0; i < slots.length; i++) {
            if(slots[i] != null) {
                System.out.println(i + 1 + " " + slots[i].name + " x" + slots[i].currentStack);
            }
        }
    }
}
