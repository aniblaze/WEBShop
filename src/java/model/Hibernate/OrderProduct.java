/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hibernate;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Stefan
 */
// Declare a new hibernate entity
@Entity
// Declare the table name in the database
@Table(name = "order_product")
public class OrderProduct implements Serializable  {

    // Declare the key of the table
    @Id
    // Declare that the key for this table must generated, no manual input
    @GeneratedValue
    // Declare the column name, and the length of the column
    @Column(name = "order_product_id", length=9)
    private Integer orderProductId;
    // A many to one relationship with the order table (one order can have multiple products)
    @ManyToOne
    // Join this table with the order table, on the orders_id column
    @JoinColumn(name="order_id")
    private Order order;
    // A many to one relationship with the product table (one order can have multiple products)
    @ManyToOne
    // Join this table with the product table, on the products_id column
    @JoinColumn(name="product_id")
    private Product product;
    // Declare the column name, if the column can be null or not, and the length of the column
    @Column(name = "product_quantity", nullable=false, length=9)
    private Integer  productQuantity;

    /*
     * Create an empty constructor, so you can create an empty object
     */
    public OrderProduct() {
    }

    /*
     * Create a constructor with parameters, so you can create a filled object
     */
    public OrderProduct(Order order, Product product, Integer productQuantity) {

       this.order           = order;
       this.product         = product;
       this.productQuantity = productQuantity;

    }

    /**
     * @return the orderProductId
     */
    public Integer getOrderProductId() {
        return orderProductId;
    }

    /**
     * @param orderProductId the orderProductId to set
     */
    public void setOrderProductId(Integer orderProductId) {
        this.orderProductId = orderProductId;
    }

    /**
     * @return the order
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * @param product the product to set
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     * @return the productQuantity
     */
    public Integer getProductQuantity() {
        return productQuantity;
    }

    /**
     * @param productQuantity the productQuantity to set
     */
    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

}
