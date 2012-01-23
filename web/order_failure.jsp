<%--
    Document   : index
    Created on : 29-nov-2010, 0:51:36
    Author     : Stefan
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="cartBean" class="bean.CartBean" scope="session"/>
<jsp:setProperty name="cartBean" property="*" />
<fmt:setLocale value="nl_NL"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--<meta HTTP-EQUIV="REFRESH" content="0; url=customer">-->
        <link rel="stylesheet" type="text/css" href="css/default.css" />
        <script type="text/javascript" src="lib/jquery-1.4.4.js"></script>
        <script type="text/javascript" src="js/Shop.js"></script>
        <script type="text/javascript">
            var shopObj = new Shop("/web_appv2/", "/web_appv2/image/");
            $(document).ready(function() {
                shopObj.getSessionDetails();
                shopObj.initiateEvents();
                $(".container_product").fadeIn("slow");

                $("#container_user_details_user").click(function() {
                    shopObj.openLoginBox();
                });

            });
        </script>
    </head>
    <body>
        <div class="login_box_container" style="display: none;">
        </div>
        <div id="box_login" style="display: none;">
            <label for="username">Username: </label><input class="input_field_style" name="username" id="input_username" type="text" value=""><br />
            <label for="password">Password: </label><input class="input_field_style" name="password" id="input_password" type="password" value=""><br />
            <div id="box_error_message"></div>
            <input type="button" id="submit_login" value="Login"><br />
            <div id="login_box_register" class="box_text_register-login">
                Don't have an account?<br />Click here to register.
            </div>
        </div>
        <div id="box_register" style="display: none;">
            <label for="username">Username: </label><input class="input_field_style" name="username" id="register_input_username" type="text" value=""><br />
            <label for="password">Password: </label><input class="input_field_style" name="password" id="register_input_password" type="password" value=""><br />
            <label for="name">Name: </label><input class="input_field_style" name="name" id="register_input_name" type="name" value=""><br />
            <label for="address">Address: </label><input class="input_field_style" name="address" id="register_input_address" type="address" value=""><br />
            <div id="box_error_message"></div>
            <input type="button" id="submit_register" value="Sign up"><br />
            <div id="login_box_login" class="box_text_register-login">
                Already have an account?<br />Click to here to log in.
            </div>
        </div>
        <div class="container_header">
            <div class="container_user_details">
                <div id="container_user_details_user">
                    Welcome guest, click here to log in.
                </div>
                <div class="container_user_details_cart">
                    In cart: <span id="details_cart_product_amount"><jsp:getProperty name="cartBean" property="totalProductAmount" /> product(s)</span><br />
                    Total price: <span id="details_cart_total"><jsp:getProperty name="cartBean" property="totalProductPriceString" /></span>
                </div>
                <div class="container_user_details_image_holder">
                    <img class="container_user_details_image" src="image/default/anonymous.png" />
                </div>
            </div>
            <div class="container_menu">
                <div class="container_menu_button">
                    <a href="index"><img style="border: 0px;" src="image/default/home.png"></a>
                </div>
                <div class="container_menu_button">
                    <a href="product"><img style="border: 0px;" src="image/default/products.png"></a>
                </div>
                <div class="container_menu_button">
                    <a href="customer"><img style="border: 0px;" src="image/default/account.png"></a>
                </div>
                <div class="container_menu_button">
                    <a href="cart"><img style="border: 0px;" src="image/default/shopping_cart.png"></a>
                </div>
            </div>
        </div>
        <div class="container_middle">
            Your order could not be completed. Please check to see if you are logged in,
            and ensure you have products in your cart.
        </div>
        <div class="container_footer">

        </div>
    </body>
</html>