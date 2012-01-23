/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bean.CartBean;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import model.hibernate.Customer;
import dao.CustomerDAO;
import dao.OrderDAO;
import dao.OrderProductDAO;
import java.util.List;
import model.hibernate.Order;
import model.hibernate.OrderProduct;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author Stefan
 */
public class CustomerController extends HttpServlet {

    OrderDAO orderDao   = new OrderDAO();
    CartBean cartBean   = new CartBean();
    List <Order> orders;
    HttpSession session;
    

    /* HTTP GET request */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        /* Call the session, or create it of it does not exist */
        this.session = request.getSession(true);

        // Check if there is already an instance of the cartBean in the session
        if(this.session.getAttribute("cartBean") != null)
            // And assign it if there is
            cartBean = (CartBean) this.session.getAttribute("cartBean");

        if(cartBean.getCustomer() != null) {
            orders    = orderDao.getCustomerOrders(cartBean.getCustomer().getCustomerId());
        } else {
            orders    = null;
        }
        

        // Set an attribute containing the customer details
        request.setAttribute("orders", orders);
        // Set an attribute containing the cart bean
        request.setAttribute("cartBean", cartBean);
        // Set a session attribute containing the cart bean
        this.session.setAttribute("cartBean", cartBean);
        // Set the page belonging to this controller
        String address = "customer.jsp";
        // Perform a request to the previously defined page
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        // Stuur door naar bovenstaande adres
        dispatcher.forward(request, response);
    }

}
