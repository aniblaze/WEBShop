package dao;

import hibernate.DataAccessLayerException;
import model.hibernate.Product;

import java.util.List;


public class ProductDAO extends SuperDAO {
    public ProductDAO() {
        super();
    }

    /**
     * Insert a new product into the database.
     * @param product
     */
    public void create(Product product) throws DataAccessLayerException {
        super.create(product);
    }


    /**
     * Delete a detached product from the database.
     * @param product
     */
    public void delete(Product product) throws DataAccessLayerException {
        super.delete(product);
    }

    /**
     * Find a product by its primary key.
     * @param UID
     * @return
     */
    public Product fetchRow(int UID) throws DataAccessLayerException {
        return (Product) super.fetchRow(Product.class, UID);
    }

    /**
     * Updates the state of a detached product.
     *
     * @param product
     */
    public void update(Product product) throws DataAccessLayerException {
        super.update(product);
    }

    /**
     * Finds all products in the database.
     * @return
     */
    public List <Product> fetchAll() throws DataAccessLayerException{
        return super.fetchAll(Product.class);
    }

}