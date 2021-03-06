import java.util.ArrayList;

public class InventoryManager {

	private static ArrayList<InventoryItem> inventoryItems = new ArrayList<InventoryItem>();
	
	/**
	 * Returns the inventory item with the provided ID
	 * @param itemID		The ID of the item to be returned
	 * @return				The inventory item with the provided ID
	 */
	private static InventoryItem getItem(String itemID) {
    	for (InventoryItem item : inventoryItems) {
    		if (item.getItemID() == itemID) { return item; }
    	}
    	return null;
    }

	/**
	 * Add an item to the ArrayList of items
	 * @param tableValues 	An array of values for the item being added
	 */
    public static void addInventoryItem(String tableValues[]) {
        InventoryDB.insert(tableValues);
    }
    
    /**
     * Update an item in the ArrayList of items
     * @param itemID 		The ID of the item to be updated
     * @param tableValues 	An array of values for updating the item
     */
    public static void updateItem(String itemID, String tableValues[]) {
    	InventoryItem item = getItem(itemID);
    	if (item != null) {
    		InventoryDB.modify(new Integer(itemID), tableValues);
    	}
    }
    
    /**
     * Remove an item from the ArrayList of items
     * @param itemID 		The ID of the item to be removed
     */
    public static void removeInventoryItem(String itemID) {
    	InventoryItem item = getItem(itemID);
        if (item != null) {
        	InventoryDB.remove(new Integer(itemID));
        }
    }
    
    /**
     * Set the status of the item
     * @param itemID		The ID of the item to be changed
     * @param status		The new status of the item
     */
    public static void setStatus(String itemID, String status) {
    	InventoryItem item = getItem(itemID);
    	if (item != null) {
    		InventoryDB.singleModify(new Integer(itemID), "status", status);
    	}
    }
    
    /**
     * Returns all of the InventoryItems
     * @return				All of the InventoryItems in the ArrayList
     */
    public static Object[][] getInventoryItems() {
    	inventoryItems = InventoryDB.getAll();
    	Object[][] items = new Object[inventoryItems.size()][6];
    	int count = 0;
    	for (InventoryItem item : inventoryItems) {
    		items[count++] = new Object[]{item.getItemID(), item.getItemName(), item.getItemQty(), item.getItemStock(), item.getItemNotes(), item.getStatus()};
    	}
    	if (count == 0) return new Object[][]{};
    	return items;
    }
}
