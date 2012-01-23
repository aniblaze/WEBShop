/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import bean.CartBean;
import dao.OrderDAO;
import dao.OrderProductDAO;
import model.hibernate.Order;
import model.hibernate.OrderProduct;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Stefan
 */
public class CartController extends HttpServlet {

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

        // Set an attribute containing the cart bean
        request.setAttribute("cartBean", cartBean);
        // Set a session attribute containing the cart bean
        this.session.setAttribute("cartBean", cartBean);
        // Stel de pagina in die bij deze controller hoort
        String address = "cart.jsp";
        // Doe een verzoek naar het adres
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        // Stuur door naar bovenstaande adres
        dispatcher.forward(request, response);

    }

    /* HTTP POST request */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // Set a variable for the address to the JSP file that'll be shown at runtime
        String address;

        // Create an instance of the order/orderProduct DAO & Model
        OrderDAO        orderDao        = new OrderDAO();
        OrderProductDAO orderProductDao = new OrderProductDAO();
        Order           order;
        OrderProduct    orderProduct;

        /* Call the session, or create it of it does not exist */
        this.session = request.getSession(true);

        // Check if there is already an instance of the cartBean in the session
        if(this.session.getAttribute("cartBean") != null)
            // And assign it if there is
            cartBean = (CartBean) this.session.getAttribute("cartBean");

        /*
         * Check if a customer is logged in prior to placing the order, and make
         * sure products are present in the cart.
         */

        if(
           cartBean.getProduct().size() > 0 &&
           cartBean.getCustomer() != null
          ) {
            order = new Order();
            order.setCustomer(cartBean.getCustomer());
            order.setOrderDate(new Date());
            orderDao.create(order);
            List orderProductList = new LinkedList();
            for(int i = 0; i < cartBean.getProduct().size(); i++) {
                orderProduct = new OrderProduct();
                orderProduct.setOrder(order);
                orderProduct.setProduct(cartBean.getProduct().get(i).getProduct());
                orderProduct.setProductQuantity(cartBean.getProduct().get(i).getProductAmount());
                orderProductDao.create(orderProduct);
                orderProductList.add(orderProduct);
            }
            // Assign a new empty vector to the product vector
            cartBean.setProduct(new Vector());
            // Set the page that will be shown when the POST is made
            address = "order_success.jsp";
        } else {
            // Set the page that will be shown when the POST is made
            address = "order_failure.jsp";
        }

        // Set an attribute containing the cart bean
        request.setAttribute("cartBean", cartBean);
        // Set a session attribute containing the cart bean
        this.session.setAttribute("cartBean", cartBean);
        // Doe een verzoek naar het adres
        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        // Stuur door naar bovenstaande adres
        dispatcher.forward(request, response);

    }

}