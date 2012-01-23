/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.bean;

import model.hibernate.Product;

/**
 * Create a cart object which will in turn hold the product, and it's amount
 * @author Stefan
 */
public class Cart {

    // Define the product & product amount variables
    private Product product;
    private int productAmount;

    public Cart() {
        
    }

    /*
     * Setter for the product
     */
    public void setProduct(Product productObj) {
        this.product = productObj;
    }

    /*
     * Setter for the product amount
     */
    public void setProductAmount(int amount) {
        this.productAmount = amount;
    }


    /*
     * Getter for the product
     */
    public Product getProduct() {
        return this.product;
    }

    /*
     * Getter for the product amount
     */
    public int getProductAmount() {
        return this.productAmount;
    }

}
