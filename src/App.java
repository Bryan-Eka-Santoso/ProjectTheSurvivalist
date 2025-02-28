import Object.*;

public class App {
    public static void main(String[] args) throws Exception {
        Inventory inventory = new Inventory();
        Consumable Apple = new Consumable("Apple");
        Consumable Fish = new Consumable("Fish");
        Unconsumable Sword = new Unconsumable("Sword"); 

        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Fish);
        inventory.addItem(Sword);
        inventory.addItem(Sword);
        inventory.addItem(Apple);
        inventory.addItem(Apple);
        inventory.addItem(Fish);
        
        inventory.showInventory();
    }
}
