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
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 *
 * @author Stefan
 */
@Entity
@Table(name = "product")
public class Product implements Serializable  {

    // Declare the key of the table
    @Id
    // Declare that the key for this table must generated, no manual input
    @GeneratedValue
    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "product_id", length=9)
    private Integer productId;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "product_name", nullable=false, length=50)
    private String  productName;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "product_description", nullable=false, length=90)
    private String  productDescription;

    // Set the name and prerequisites in the 'column' annotation
    @Column(name = "product_price", nullable=false, length=8)
    private float   productPrice;
    
    /*
     * Create an empty constructor, so you can create an empty object
     */
    public Product() {
    }

    /*
     * Create a constructor with parameters, so you can create a filled object
     */
    public Product(String productName, String productDescription, float productPrice) {

       this.productName         = productName;
       this.productDescription  = productDescription;
       this.productPrice        = productPrice;

    }

    /**
     * @return the productId
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * @param productId the productId to set
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return the productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName the productName to set
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return the productDescription
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * @param productDescription the productDescription to set
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    /**
     * @return the productPrice
     */
    public float getProductPrice() {
        return productPrice;
    }

    /**
     * @param productPrice the productPrice to set
     */
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

}
