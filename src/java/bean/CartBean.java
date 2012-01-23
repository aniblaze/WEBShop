/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.Vector;
import model.bean.Cart;
import model.hibernate.Product;
import dao.ProductDAO;
import java.text.NumberFormat;
import model.hibernate.Customer;

/**
 *
 * @author:      Stefan van Beusekom
 * @Created on:  10-03-2011
 * @Description: This class holds all the data required for the shopping cart
 * @return:      void;
 */
public class CartBean {

    // Class variables
    private Customer customer = null;
    private int totalProductAmount = 0;
    private double totalProductPrice = 0;
    private String totalProductPriceString = "€ 0,00";
    private Vector <Cart> product = new Vector();
    Product productObject;
    ProductDAO productDAO;

    // Class constructor
    public CartBean() {
    }

    public int getTotalProductAmount() {
        return this.totalProductAmount;
    }

    public double getTotalProductPrice() {
        return this.totalProductPrice;
    }

    public void setTotalProductAmount() {
        int totalAmount = 0;

        for(int i = 0; i < this.product.size(); i++)
            totalAmount += this.product.get(i).getProductAmount();
        
        this.totalProductAmount = totalAmount;
    }

    public void setTotalProductPrice() {
        double totalPrice = 0;
        
        for(int i = 0; i < this.product.size(); i++)
            totalPrice += (((double)this.product.get(i).getProduct().getProductPrice()) * this.product.get(i).getProductAmount());

        this.totalProductPrice = totalPrice;
    }

    public Vector <Cart> getProduct() {
        return this.product;
    }

    /*
     * @author:      Stefan van Beusekom
     * @Created on:  10-03-2011
     * @Description: This function either adds a new product object to the
     *               product vector, or increments the product amount if
     *               a product object with a similar product ID already exists.
     * @return:      void;
     */
    public void addProduct(int PID) {
        // Create a variable which will tell us if a new product object needs to be created
        boolean newProductEntry = true;
        // Loop through the product vector
        for(int i = 0; i < this.product.size(); i++) {
            Cart tempProd = this.product.get(i);
            // If the product ID of the current CartProduct object is identical
            if(tempProd.getProduct().getProductId() == PID) {
                // Increment the product amount by one, instead of adding it again
                this.product.get(i).setProductAmount((tempProd.getProductAmount()+1));
                // And set the product entry variable to false
                newProductEntry = false;
            }
        }

        // If the product is new to the vector
        if(newProductEntry) {
            // Retrieve the product data, which will be used to fill the CartProduct object
            productObject = new Product();
            productDAO = new ProductDAO();
            productObject = productDAO.fetchRow(PID);
            // Create a new CartProduct object
            Cart newProduct = new Cart();
            // Set the product price
            newProduct.setProduct(productObject);
            // Set the product amount
            newProduct.setProductAmount(1);
            // And finally, add it to the vector
            this.product.add(newProduct);
        }

    }

    /*
     * @author:      Stefan van Beusekom
     * @Created on:  10-03-2011
     * @Description: This function removes a product object from the vector.
     * @return:      void;
     */
    public void removeProduct(int PID) {
        // Loop through the product vector
        for(int i = 0; i < this.product.size(); i++)
            // If the product ID of the current product object is identical
            if(this.product.get(i).getProduct().getProductId() == PID)
                // Remove it from the product vector
                this.product.remove(i);
    }

    /*
     * @author:      Stefan van Beusekom
     * @Created on:  10-03-2011
     * @Description: This function sets the product amount.
     * @return:      void;
     */
    public void changeProductAmount(int PID, int amount) {
        // Loop through the product vector
        for(int i = 0; i < this.product.size(); i++)
            // If the product ID of the current product object is identical
            if(this.product.get(i).getProduct().getProductId() == PID)
                // Change the product amount
                this.product.get(i).setProductAmount(amount);
    }
    
    /**
     * @return the customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return the totalProductPriceString
     */
    public String getTotalProductPriceString() {
        double payment = this.getTotalProductPrice();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        return nf.format(payment);
    }

    /**
     * @param totalProductPriceString the totalProductPriceString to set
     */
    public void setTotalProductPriceString(String totalProductPriceString) {
        this.totalProductPriceString = totalProductPriceString;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Vector<Cart> product) {
        this.product = product;
    }

}
