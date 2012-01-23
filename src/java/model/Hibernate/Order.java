/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package model.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author Stefan
 */
// Tell the application a new database entity is being noted
@Entity
// Define the table name
@Table(name="orders")
public class Order implements Serializable  {

    // Declare the key of the table
    @Id
    // Declare that the key for this table must generated, no manual input
    @GeneratedValue
    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "orders_id", length=9)
    private Integer orderId;

    // Many to one relationship for the customer ID
    @ManyToOne
    // Set the join column to customer ID
    @JoinColumn(name="customer_id")
    private Customer customer;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "order_date", nullable=false)
    // And set the type of date next
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date orderDate;

    // One to many relationship to the orderProduct table
    @OneToMany
    // Set lazy to false, to avoid a closed connection before retrieval
    @LazyCollection(LazyCollectionOption.FALSE)
    // Set the join column to order ID
    @JoinColumn(name="order_id")
    @OrderBy("product")
    private List <OrderProduct> orderProduct;

    /*
     * Create an empty constructor, so you can create an empty object
     */
    public Order() {
    }

    /*
     * Create a constructor with parameters, so you can create a filled object
     */
    public Order(Customer customer, Date orderDate) {

       this.customer    = customer;
       this.orderDate   = orderDate;

    }

    /**
     * @return the orderId
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
     * @return the orderDate
     */
    public Date getOrderDate() {
        return orderDate;
    }

    /**
     * @param orderDate the orderDate to set
     */
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * @return the orderProduct
     */
    public List<OrderProduct> getOrderProduct() {
        return orderProduct;
    }

    /**
     * @param orderProduct the orderProduct to set
     */
    public void setOrderProduct(List<OrderProduct> orderProduct) {
        this.orderProduct = orderProduct;
    }
    
}
