package dao;

import hibernate.DataAccessLayerException;
import model.hibernate.OrderProduct;

import java.util.List;


public class OrderProductDAO extends SuperDAO {
    public OrderProductDAO() {
        super();
    }

    /**
     * Insert a new order-product combination into the database.
     * @param order
     */
    public void create(OrderProduct orderProduct) throws DataAccessLayerException {
        super.create(orderProduct);
    }


    /**
     * Delete a detached order-product combination from the database.
     * @param order
     */
    public void delete(OrderProduct orderProduct) throws DataAccessLayerException {
        super.delete(orderProduct);
    }

    /**
     * Find an order-product combination by its primary key.
     * @param UID
     * @return
     */
    public OrderProduct fetchRow(int UID) throws DataAccessLayerException {
        return (OrderProduct) super.fetchRow(OrderProduct.class, UID);
    }

    /**
     * Updates the state of a detached order-product combination.
     *
     * @param order
     */
    public void update(OrderProduct orderProduct) throws DataAccessLayerException {
        super.update(orderProduct);
    }

    /**
     * Finds all order-product combinations in the database.
     * @return
     */
    public List <OrderProduct> fetchAll() throws DataAccessLayerException{
        return super.fetchAll(OrderProduct.class);
    }

    /**
     * Finds all order-product combinations in the database.
     * @param int OID
     * @return List
     */
    public List <OrderProduct> getOrderProducts(int OID) {
        List <OrderProduct> list = super.manualQuery("from OrderProduct WHERE order.orderId = '" + OID + "'");
        return list;
    }

}