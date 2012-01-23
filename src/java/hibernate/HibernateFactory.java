package hibernate;

import hibernate.DataAccessLayerException;
import org.hibernate.*;
import org.hibernate.cfg.AnnotationConfiguration;
import org.apache.commons.logging.*;

/*
 * The hibernate factory will be responsible for the configuration and upholding
 * of hibernate connections to the database.
 */
public class HibernateFactory {

    // Create a static connection for the session
    private static SessionFactory sessionFactory;

    // Create a static log variable for holding the log
    private static Log log = LogFactory.getLog(HibernateFactory.class);

    /**
     * Constructs a new Singleton SessionFactory
     * @return
     * @throws HibernateException
     */
    public static SessionFactory buildSessionFactory() throws HibernateException {
        if (sessionFactory != null) {
            closeFactory();
        }
        return configureSessionFactory();
    }

    /**
     * Builds a Session factory, if it hasn't been already. (Singleton)
     */
    public static SessionFactory buildIfNeeded() throws DataAccessLayerException{
        // Check if there is already an instance available
        if (sessionFactory != null) {
            // And if so, return it
            return sessionFactory;
        }
        try {
            // Try to return the configuration of the session
            return configureSessionFactory();
        } catch (HibernateException e) {
            // Or throw an exception
            throw new DataAccessLayerException(e);
        }
    }

    /*
     * Return the session factory
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /*
     * Open a new session
     */
    public static Session openSession() throws HibernateException {
        // First build a new session factory if needed
        buildIfNeeded();
        // Then, return a new open session
        return sessionFactory.openSession();
    }

    /*
     * Close the factory
     */
    public static void closeFactory() {
        // If the session factory does not have an instance
        if (sessionFactory != null) {
            try {
                // Close the session factory
                sessionFactory.close();
            } catch (HibernateException ignored) {
                // Or throw an exception of being unable to close it
                log.error("Couldn't close SessionFactory", ignored);
            }
        }
    }

    /*
     * Close a specific session
     */
    public static void close(Session session) {
        // If the session instance exists
        if (session != null) {
            try {
                // Close the session
                session.close();
            } catch (HibernateException ignored) {
                // Or throw an exception of being unable to close it
                log.error("Couldn't close Session", ignored);
            }
        }
    }

    /*
     * Revert a transaction (query) prior to committing it
     */
    public static void rollback(Transaction tx) {
        try {
            // If there is an instance of a transaction
            if (tx != null) {
                // Do a rollback on it
                tx.rollback();
            }
        } catch (HibernateException ignored) {
            // Or throw an exception of being unable to do a rollback
            log.error("Couldn't rollback Transaction", ignored);
        }
    }
    /**
     *
     * @return
     * @throws HibernateException
     */
    private static SessionFactory configureSessionFactory() throws HibernateException {
        // Create a new configuration
        AnnotationConfiguration configuration = new AnnotationConfiguration();
        // And configure it with the standard template
        configuration.configure();
        // Assign it to the session factory with a prebuild
        sessionFactory = configuration.buildSessionFactory();
        // And return it
        return sessionFactory;
    }
}