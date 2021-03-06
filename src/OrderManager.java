import java.util.ArrayList;

public class OrderManager {

    private static ArrayList<Order> orders = new ArrayList<Order>();

    /**
	 * Returns the order with the provided ID
	 * @param orderID		The ID of the order to be returned
	 * @return				The order with the provided ID
	 */
	private static Order getOrder(String orderID) {
    	for (Order order : orders) {
    		if (order.getOrderID() == orderID) { return order; }
    	}
    	return null;
    }

    /**
    * Add an order to the ArrayList orders
    * @param tableValues 	An array of values to be added to the order
    */
    public static void addOrder(String tableValues[]) {
    	OrderDB.insert(tableValues);
    }
    
    /**
     * Update an order
     * @param orderID 		The ID of the order to be updated in the ArrayList of orders
     * @param tableValues 	An array of values to update the order
     */
    public static void updateOrder(String orderID, String tableValues[]) {
    	if (orderID != null) {
    		OrderDB.modify(new Integer(orderID), tableValues);
    	}
    }
    
    /**
    * Delete an order from the ArrayList orders
    * @param orderID 		The ID of the order to be deleted from the ArrayList orders
    */
    public static void removeOrder(String orderID) {
    	Order order = getOrder(orderID);
    	if (order != null) {
    		OrderDB.remove(new Integer(orderID));
    	}
    }
    
    /**
     * Set the status of the Order
     * @param orderID		The ID of the order to be changed
     * @param status		The new status of the order
     */
    public static void setStatus(String orderID, String status) {
    	Order order = getOrder(orderID);
    	if (order != null) {
    		OrderDB.singleModify(new Integer(orderID), "order_status", status);
    	}
    }
    
    /**
     * Returns all of the Order
     * @return				All of the Orders in the ArrayList
     */
    public static Object[][] getOrders() {
    	orders = OrderDB.getAll();
    	Object[][] tempOrders = new Object[orders.size()][6];
    	int count = 0;
    	for (Order order : orders) {
    		tempOrders[count++] = new Object[]{order.getOrderID(), order.getOrderTableNo(), order.getOrderInfo(), order.getOrderTime(), order.getOrderNotes(), order.getStatus()};
    	}
    	if (count == 0) return new Object[][]{};
    	return tempOrders;
    }
}
