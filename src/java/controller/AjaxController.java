/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.hibernate.Product;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.*;
import java.util.ArrayList;
import bean.CartBean;
import java.security.MessageDigest;
import model.hibernate.Customer;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 *
 * @author Stefan
 */
public class AjaxController extends HttpServlet {

    /*
     * Constants which hold codes corresponding with the code sent through
     * the AJAX call.
     */
    // Basket static switch-case codes
    public static final int AJAX_ADD_PRODUCT_TO_BASKET = 101;
    public static final int AJAX_CHANGE_PRODUCT_AMOUNT_BASKET = 102;
    public static final int AJAX_REMOVE_PRODUCT_FROM_BASKET = 103;
    public static final int AJAX_GET_SESSION = 104;
    // User static switch-case codes
    public static final int AJAX_USER_LOGIN = 201;
    public static final int AJAX_USER_CREATE = 202;
    public static final int AJAX_USER_USERNAME_EXISTS = 203;
    public static final int AJAX_USER_LOGOUT = 204;
    /* Create instances of the models for the different tables used in the
    POST and GET handlers */
    Product product;
    Customer customer;
    ProductDAO productDAO = new ProductDAO();
    CustomerDAO customerDAO = new CustomerDAO();

    /* Create an instance of the session bean */
    CartBean cartBean = new CartBean();

    /* Load other instances of different objects used in the GET and POST
    handlers */
    JSONObject json = new JSONObject();
    HttpSession session;
    ArrayList<int[]> productList = new ArrayList();


    /* HTTP GET request */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        /* Call the session, or create it of it does not exist */
        this.session = request.getSession(true);

        // Check if there is already an instance of the cartBean in the session
        if(this.session.getAttribute("cartBean") != null)
            // And assign it if there is
            cartBean = (CartBean) this.session.getAttribute("cartBean");

        /* Get the POST parameter which tells us what function to call */
        int functionCall = Integer.parseInt(request.getParameter("functionCall"));

        /* Retrieves the POST codes, and lets the switch case below handle the
        request made accordingly */
        switch (functionCall) {

            /* Case for adding a product to the basket */
            case AJAX_ADD_PRODUCT_TO_BASKET:
                json = this.addProductJSON(Integer.parseInt(request.getParameter("productId")));
                break;
            /* Case for adding a product to the basket */
            case AJAX_CHANGE_PRODUCT_AMOUNT_BASKET:

                break;
            /* Case for removing a product from the basket */
            case AJAX_REMOVE_PRODUCT_FROM_BASKET:
                json = this.removeProductJSON(Integer.parseInt(request.getParameter("productId")));
                break;
            /* Case for getting the session details */
            case AJAX_GET_SESSION:
                json = this.getSession();
                break;


            /* Case to log in a user */
            case AJAX_USER_LOGIN:
                json = this.loginJSON(
                        request.getParameter("username"),
                        request.getParameter("password"));
                break;

            /* Case to log in a user */
            case AJAX_USER_LOGOUT:
                json = this.logoutJSON();
                break;
            /* Case to create a user */
            case AJAX_USER_CREATE:
                json = this.createCustomerJSON(
                        request.getParameter("username"),
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("address"));
                break;

        }

        /* Turn the JSON object into a string */
        String returnJSON = json.toString();
        /* And make that JSON available to the view as an attribute */
        request.setAttribute("returnJSON", returnJSON);
        /* And make the cartBean available to the current page */
        request.setAttribute("cartBean", cartBean);
        /* And add the updated cartBean to the session */
        this.session.setAttribute("cartBean", cartBean);
        /* Define the JSP belonging to this controller (where to dispatch to) */
        String address = "ajax.jsp";
        /* Make a request to the address */
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        /* Dispatch to the address assigned above */
        dispatcher.forward(request, response);

    }


    /**
     * "Get the current session, and return it in JSON format."
     * @param
     * @return JSONObject
     */
    public JSONObject getSession() {
        // Define a new JSON object
        JSONObject jsonObject;
        // Define a new JSON array
        JSONArray jsonArray = new JSONArray();
        // Define a new JSON object, and create a new instance
        JSONObject customerJsonObject = new JSONObject();

        // If a customer is present in the cart bean
        if(cartBean.getCustomer() != null) {
            // Fill it with customer data
            customerJsonObject.put("customer_id", cartBean.getCustomer().getCustomerId());
            customerJsonObject.put("customer_name", cartBean.getCustomer().getCustomerName());
            customerJsonObject.put("customer_username", cartBean.getCustomer().getCustomerUsername());
        }
        // Loop through all of the products present in the cart bean
        for (int i = 0; i < cartBean.getProduct().size(); i++) {
            // Create a new JSONObject instance
            jsonObject = new JSONObject();
            // And fill the JSON object with product data
            jsonObject.put("product_id", cartBean.getProduct().get(i).getProduct().getProductId());
            jsonObject.put("product_amount", cartBean.getProduct().get(i).getProductAmount());
            jsonObject.put("product_price", cartBean.getProduct().get(i).getProduct().getProductPrice());
            // And put it in the JSON array
            jsonArray.put(jsonObject);
        }
        // Create another JSON object
        jsonObject = new JSONObject();
        // This time, put the JSON array with products in it
        jsonObject.put("products", jsonArray);
        // If a customer exists, put the customer JSON into the main JSON object
        if(cartBean.getCustomer() != null)
            jsonObject.put("customer", customerJsonObject);
        // Recalculate the total product amount
        cartBean.setTotalProductAmount();
        // And put the new total product amount in the JSON object
        jsonObject.put("cart_total_amount", cartBean.getTotalProductAmount());
        // Recalculate the total product price
        cartBean.setTotalProductPrice();
        // And put the new total product price in the JSON object
        jsonObject.put("cart_total_price", cartBean.getTotalProductPriceString());
        // Finally, return the JSON object
        return jsonObject;
    }

    /**
     * "Add a product to the cartBean, and return a JSONObject with the new cart
     * bean data in it."
     * @param int PID
     * @return JSONObject
     */
    public JSONObject addProductJSON(int PID) {
        cartBean.addProduct(PID);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (int i = 0; i < cartBean.getProduct().size(); i++) {
            jsonObject = new JSONObject();
            jsonObject.put("product_id", cartBean.getProduct().get(i).getProduct().getProductId());
            jsonObject.put("product_amount", cartBean.getProduct().get(i).getProductAmount());
            jsonObject.put("product_price", cartBean.getProduct().get(i).getProduct().getProductPrice());
            jsonArray.put(jsonObject);
        }
        jsonObject = new JSONObject();
        jsonObject.put("products", jsonArray);
        cartBean.setTotalProductAmount();
        jsonObject.put("cart_total_amount", cartBean.getTotalProductAmount());
        cartBean.setTotalProductPrice();
        jsonObject.put("cart_total_price", cartBean.getTotalProductPriceString());
        return jsonObject;
    }

    /**
     * "remove a product from the cartBean, and return a JSONObject with the
     * new cart bean data in it."
     * @param int PID
     * @return JSONObject
     */
    public JSONObject removeProductJSON(int PID) {
        cartBean.removeProduct(PID);
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject;
        for (int i = 0; i < cartBean.getProduct().size(); i++) {
            jsonObject = new JSONObject();
            jsonObject.put("product_id", cartBean.getProduct().get(i).getProduct().getProductId());
            jsonObject.put("product_name", cartBean.getProduct().get(i).getProduct().getProductName());
            jsonObject.put("product_amount", cartBean.getProduct().get(i).getProductAmount());
            jsonObject.put("product_price", cartBean.getProduct().get(i).getProduct().getProductPrice());
            jsonArray.put(jsonObject);
        }
        jsonObject = new JSONObject();
        jsonObject.put("products", jsonArray);
        jsonObject.put("cart_total_amount", cartBean.getTotalProductAmount());
        jsonObject.put("cart_total_price", cartBean.getTotalProductPriceString());
        return jsonObject;
    }

    /**
     * "Log in a user, and return the user details in a JSONObject"
     * @param String username, String password
     * @return JSONObject
     */
    public JSONObject loginJSON(String username, String password) {
        MessageDigest hashDigest = null;
        try {
            hashDigest = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AjaxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        hashDigest.update(password.getBytes());
        byte[] hash = hashDigest.digest();
        String processedPass = new String();
        for (int i = 0; i < hash.length; i++) {
            processedPass = processedPass + Integer.toHexString(hash[i]);
        }
        customer = new Customer();
        customer = customerDAO.login(username.toLowerCase(), processedPass);
        JSONObject jsonObject = new JSONObject();

        if (customer != null) {
            cartBean.setCustomer(customer);
            jsonObject.put("name", customer.getCustomerName());
            jsonObject.put("result", "You have been logged in");
            jsonObject.put("allowContinue", true);
        } else {
            jsonObject.put("result", "Invalid user credentials");
            jsonObject.put("allowContinue", false);
        }
        return jsonObject;
    }

    /**
     * "Create a customer, and return the customer data in a JSONObject"
     * @param String username, String password, String name, String address
     * @return JSONObject
     */
    public JSONObject createCustomerJSON(String username, String password, String name, String address) {
        JSONObject jsonObject = new JSONObject();
        MessageDigest hashDigest = null;
        try {
            hashDigest = java.security.MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AjaxController.class.getName()).log(Level.SEVERE, null, ex);
        }
        hashDigest.update(password.getBytes());
        byte[] hash = hashDigest.digest();
        String processedPass = new String();
        for (int i = 0; i < hash.length; i++) {
            processedPass = processedPass + Integer.toHexString(hash[i]);
        }

        if (!customerDAO.usernameExists(username.toLowerCase())) {

            if (this.validate(username, "^[a-zA-Z0-9 _]+$") && this.validate(password, "^[a-zA-Z0-9 _]+$") && this.validate(name, "^[a-zA-Z0-9 _]+$") && this.validate(address, "^[a-zA-Z0-9 _]+$")) {
                customer = new Customer();
                customer.setCustomerUsername(username.toLowerCase());
                customer.setCustomerPassword(processedPass);
                customer.setCustomerName(name);
                customer.setCustomerAddress(address);
                customerDAO.create(customer);
                jsonObject.put("result", username.toLowerCase());
                jsonObject.put("allowContinue", true);
            } else {
                jsonObject.put("result", "One or more fields are filled out incorrectly");
                jsonObject.put("allowContinue", false);
            }
        } else {
            jsonObject.put("result", "Username already exists");
            jsonObject.put("allowContinue", false);
        }
        return jsonObject;
    }

    public JSONObject logoutJSON() {
        cartBean.setCustomer(null);
        return this.getSession();
    }

    /**
     * "Validate a string value using a regular expression"
     * @param String target, String regex
     * @return boolean
     */
    public boolean validate(String target, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(target);
        return m.matches();
    }

    /**
     * "Check if a username already exists"
     * @param String username
     * @return JSONObject
     */
    public JSONObject usernameExistsJSON(String username) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", customerDAO.usernameExists(username));
        return jsonObject;
    }
}
