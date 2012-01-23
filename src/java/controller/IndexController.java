/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.CartBean;
import dao.ProductDAO;
import java.io.*;
import java.util.List;
import javax.servlet.*;
import javax.servlet.http.*;
import model.hibernate.Product;

/**
 *
 * @author Stefan
 */
public class IndexController extends HttpServlet {

    CartBean cartBean = new CartBean();
    ProductDAO pDao = new ProductDAO();
    HttpSession session;

    /* HTTP GET request */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        /* Call the session, or create it of it does not exist */
        this.session = request.getSession(true);

        /* Create a few generic products, if they didn't exist so already */
        List<Product> products = pDao.fetchAll();
        // Calculate the amount of products that exist, and what should be created
        int topSize = (products.size() > 8) ? 8 : (8 - (8 - products.size())) + 1;
        // Loop through the product creation process
        for (int i = topSize; i < 9; i++) {
            Product product = new Product();
            product.setProductName("Product " + i);
            product.setProductDescription("Generic product with followup number " + i);
            product.setProductPrice((float) (i + 8.95));
            pDao.create(product);
        }

        // Check if there is already an instance of the cartBean in the session
        if (this.session.getAttribute("cartBean") != null) // And assign it if there is
        {
            cartBean = (CartBean) this.session.getAttribute("cartBean");
        }

        // Set an attribute containing the cart bean
        request.setAttribute("cartBean", cartBean);
        // Set a session attribute containing the cart bean
        this.session.setAttribute("cartBean", cartBean);
        // Set the page belonging to this controller
        String address = "index.jsp";
        // Perform a request to the previously defined page
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        // Stuur door naar bovenstaande adres
        dispatcher.forward(request, response);
    }
}
