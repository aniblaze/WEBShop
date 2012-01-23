/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import dao.ProductDAO;
import java.util.List;
import bean.CartBean;

/**
 *
 * @author Stefan
 */
public class ProductController extends HttpServlet {

    // Create a new DAO instance
    ProductDAO prodD   = new ProductDAO();
    CartBean cartBean   = new CartBean();
    HttpSession session;

    /* HTTP GET request */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        /* Call the session, or create it of it does not exist */
        this.session = request.getSession(true);

        // Check if there is already an instance of the cartBean in the session
        if(this.session.getAttribute("cartBean") != null)
            // And assign it if there is
            cartBean = (CartBean) this.session.getAttribute("cartBean");

        // Fetch all products from the database
        List products = prodD.fetchAll();

        // Set an attribute containing the products
        request.setAttribute("products", products);
        // Set an attribute containing the cart bean
        request.setAttribute("cartBean", cartBean);
        // Set a session attribute containing the cart bean
        this.session.setAttribute("cartBean", cartBean);
        // Stel de pagina in die bij deze controller hoort
        String address = "product.jsp";
        // Doe een verzoek naar het adres
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        // Stuur door naar bovenstaande adres
        dispatcher.forward(request, response);

    }

}