package dao;

import hibernate.DataAccessLayerException;
import model.hibernate.Customer;

import java.util.List;


public class CustomerDAO extends SuperDAO {

    public CustomerDAO() {
        super();
    }

    /**
     * Insert a new customer into the database.
     * @param customer
     * @return
     */
    public void create(Customer customer) throws DataAccessLayerException {
        super.create(customer);
    }


    /**
     * Delete a detached customer from the database.
     * @param customer
     * @return
     */
    public void delete(Customer customer) throws DataAccessLayerException {
        super.delete(customer);
    }

    /**
     * Find a user by its primary key.
     * @param UID
     * @return Customer
     */
    public Customer fetchRow(int UID) throws DataAccessLayerException {
        return (Customer) super.fetchRow(Customer.class, UID);
    }


    /**
     * Return a Customer object if the credentials match a record.
     * @param username, password
     * @return Customer
     */
    public Customer login(String username, String password) throws DataAccessLayerException {
        List <Customer> list = super.manualQuery("from Customer WHERE customerUsername = '" + username + "' AND customerPassword = '" + password + "'");
        if(list.size() > 0)
            return list.get(0);
        else
            return null;
    }


    /**
     * Return true when a username exists, or false otherwise
     * @param username
     * @return boolean
     */
    public boolean usernameExists(String username) {
        List <Customer> list = super.manualQuery("from Customer WHERE customerUsername = '" + username + "'");
        if(list.size() > 0)
            return true;
        else
            return false;
    }

    /**
     * Updates the state of a detached customer.
     * @param customer
     * @return
     */
    public void update(Customer customer) throws DataAccessLayerException {
        super.update(customer);
    }

    /**
     * Finds all customer in the database.
     * @param 
     * @return List
     */
    public List <Customer> fetchAll() throws DataAccessLayerException{
        return super.fetchAll(Customer.class);
    }

    /**
     * Finds all customer in the database with the given parameter.
     * @param string
     * @return List
     */
    public List <Customer> manualQuery(String HQL) throws DataAccessLayerException{
        return super.manualQuery(HQL);
    }

}