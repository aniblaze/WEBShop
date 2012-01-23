package dao;

import hibernate.DataAccessLayerException;
import model.hibernate.Order;

import java.util.List;


public class OrderDAO extends SuperDAO {
    public OrderDAO() {
        super();
    }

    /**
     * Insert a new order into the database.
     * @param order
     */
    public void create(Order order) throws DataAccessLayerException {
        super.create(order);
    }


    /**
     * Delete a detached order from the database.
     * @param order
     */
    public void delete(Order order) throws DataAccessLayerException {
        super.delete(order);
    }

    /**
     * Find an order by its primary key.
     * @param UID
     * @return
     */
    public Order fetchRow(int UID) throws DataAccessLayerException {
        return (Order) super.fetchRow(Order.class, UID);
    }

    /**
     * Updates the state of a detached order.
     *
     * @param order
     */
    public void update(Order order) throws DataAccessLayerException {
        super.update(order);
    }

    /**
     * Finds all orders in the database.
     * @return List
     */
    public List <Order> fetchAll() throws DataAccessLayerException{
        return super.fetchAll(Order.class);
    }
    
    /**
     * Finds all orders from a specific user
     * @param int UID
     * @return List
     */
    public List <Order> getCustomerOrders(int UID) {
        List <Order> list = super.manualQuery("from Order WHERE customer.customerId = '" + UID + "'");
        return list;
    }

}