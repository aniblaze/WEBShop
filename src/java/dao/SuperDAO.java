package dao;

import hibernate.HibernateFactory;
import hibernate.DataAccessLayerException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;

import java.util.List;

/*
 * This super class holds all of the actions for saving, updating, creating
 * or deleting items in the database. It does so by reading the passed
 * object in the functions below, and assigning them to their respective
 * tables.
 */
public class SuperDAO {

    private Session session;
    private Transaction tx;
    
    public SuperDAO() {
        // Creates a session from the HibernateFactory class if needed
        HibernateFactory.buildIfNeeded();
    }

    /*
     * Updates an object, and then commits it to the database
     */
    protected void update(Object obj) {
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Saves or updates the object
            session.update(obj);
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        }
    }

    /*
     * Create an object, and then commits it to the database
     */
    protected void create(Object obj) {
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Saves or updates the object
            session.saveOrUpdate(obj);
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        }
    }

    /*
     * Empty an object, and then delete it from the database
     */
    protected void delete(Object obj) {
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Deletes the object
            session.delete(obj);
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        }
    }
    /*
     * Finds the requested row, requests it using the id as a parameter
     * for the resultset, and then saves it to an object
     */
    protected Object fetchRow(Class clazz, int id) {
        // Create an empty object
        Object obj = null;
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Assigns the requested database object to it
            obj = session.load(clazz, id);
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        }
        // And finally, it returns the object
        return obj;
    }
    /*
     * Returns a list of all objects found in a list
     */
    protected List fetchAll(Class clazz) {
        // Create an empty list
        List objects = null;
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Creates a HQL query
            Query query = session.createQuery("from " + clazz.getName());
            // And puts a list of objects into the earlier created list
            objects = query.list();
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        }
        // And finally, it returns the object
        return objects;
    }


    /*
     * Returns a list of all objects found in a list
     */
    protected List manualQuery(String HQL) {
        // Create an empty list
        List objects = null;
        try {
            // Opens up a session, and allows for a transaction
            startOperation();
            // Creates a HQL query
            Query query = session.createQuery(HQL);
            // And puts a list of objects into the earlier created list
            objects = query.list();
            // And then commits it
            tx.commit();
        } catch (HibernateException e) {
            // Prints the exception, and does a rollback if possible
            handleException(e);
        } finally {
            // After which, it closes the connection
            HibernateFactory.close(session);
        }
        // And finally, it returns the object
        return objects;
    }

    /*
     * Handles actions done when an exception occurs
     */
    protected void handleException(HibernateException e) throws DataAccessLayerException {
        // Attempts to do a rollback
        HibernateFactory.rollback(tx);
        // And then throws the exception
        throw new DataAccessLayerException(e);
    }

    /*
     * Opens a new hibernating session, and allows the beginning of a
     * transaction between the database and hibernation layer
     */
    protected void startOperation() throws HibernateException {
        // Create a new session
        session = HibernateFactory.openSession();
        // Begin a fresh transaction
        tx = session.beginTransaction();
    }
}