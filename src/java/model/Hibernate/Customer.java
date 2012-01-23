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
import javax.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    // Declare the key of the table
    @Id
    // Declare that the key for this table must generated, no manual input
    @GeneratedValue
    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "customer_id", length=9)
    private Integer customerId;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "customer_name", nullable=false, length=50)
    private String  customerName;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "customer_address", nullable=false, length=75)
    private String  customerAddress;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "customer_password", nullable=false)
    private String  customerPassword;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "customer_username", nullable=false, length=50)
    private String  customerUsername;

    /*
     * Create an empty constructor, so you can create an empty object
     */
    public Customer() {
    }

    /*
     * Create a constructor with parameters, so you can create a filled object
     */
    public Customer(String customerName, String customerAddress, String customerPassword, String customerUsername) {

       this.customerName        = customerName;
       this.customerAddress     = customerAddress;
       this.customerPassword    = customerPassword;
       this.customerUsername    = customerUsername;

    }

    /**
     * @return the customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the customerAddress
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * @param customerAddress the customerAddress to set
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * @return the customerPassword
     */
    public String getCustomerPassword() {
        return customerPassword;
    }

    /**
     * @param customerPassword the customerPassword to set
     */
    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    /**
     * @return the customerUsername
     */
    public String getCustomerUsername() {
        return customerUsername;
    }

    /**
     * @param customerUsername the customerUsername to set
     */
    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

}
