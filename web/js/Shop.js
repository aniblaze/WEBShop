/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function Shop(targetUrl, imagePath) {

    this._baseUrl		= targetUrl;
    this._imageUrl              = imagePath;
    this._customer              = {};

    /**
     * Get the current session details from the AjaxController, by calling the
     * values present in the cart bean.
     * @param   int PID
     * @return
     */
    this.getSessionDetails = function() {
        $.ajax({
            type: 'POST',
            url: this._baseUrl + "ajax",
            async: false,
            data: {
                functionCall: 104
            },
            success: function(json) {
                returnedJSON = jQuery.parseJSON(json);
                if(returnedJSON.customer != null) {
                    $("#container_user_details_user").html("Welcome " + returnedJSON.customer.customer_name + ", click here to log out.");
                    $("#container_user_details_user").unbind();
                    $("#container_user_details_user").click(function() {
                        shopObj.logout();
                    });
                }
            }
        });
    }

    /**
     * Add a product to the cart by passing a product ID to it.
     * @param   int PID
     * @return
     */
    this.addProductToCart   = function(pid) {
        $.ajax({
            type: 'POST',
            url: this._baseUrl + "ajax",
            async: false,
            data: {
                functionCall: 101,
                productId: pid
            },
            success: function(json) {
                $("#details_cart_product_amount").fadeOut("fast");
                $("#details_cart_total").fadeOut("fast");
                returnedJSON = jQuery.parseJSON(json);
                $("#details_cart_product_amount").fadeIn("fast");
                $("#details_cart_product_amount").html(returnedJSON.cart_total_amount + " product(s)");
                $("#details_cart_total").fadeIn("fast");
                $("#details_cart_total").html(returnedJSON.cart_total_price);
            }
        });
    }

    /**
     * Pseudo event listener which will look at the buttons, and call the
     * appropriate actions linked to it.
     * @param   int PID
     * @return
     */
    this.initiateEvents = function() {
        /*
         * Event for the 'Not a customer yet?' text in the login box,
         * which will switch screens between logging in and registration
         */
        $("#login_box_register").click(function(){
            $("#box_error_message").html("");
            $("#box_login").slideUp("slow", function() {
                $("#register_input_username").val("");
                $("#register_input_password").val("");
                $("#register_input_name").val("");
                $("#register_input_address").val("");
                $("#box_register").slideDown("slow");
            });
        });

        /*
         * Event for the 'Already a customer?' text in the register box,
         * which will switch screens between registration and logging in
         */
        $("#login_box_login").click(function(){
            $("#box_error_message").html("");
            $("#box_register").slideUp("slow", function() {
                $("#input_username").val("");
                $("#input_password").val("");
                $("#box_login").slideDown("slow");
            });
        });

        /*
         * Event for the login button, which will call the ajax function to
         * login the user with the given user details.
         */
        $("#submit_login").click(function() {
            var json = shopObj.login(false, false);
            if(json.allowContinue) {
                $("#box_login").slideUp("slow");
                $("#box_register").slideUp("slow");
                $(".login_box_container").fadeOut("fast");
            } else {
                $("#box_error_message").html(json.result);
            }
        });

        $("#submit_register").click(function() {
            var json = shopObj.register();
            if(json.allowContinue) {
                $("#box_login").slideUp("slow");
                $("#box_register").slideUp("slow");
                $(".login_box_container").fadeOut("fast", function() {
                    shopObj.login($("#register_input_username").val(), $("#register_input_password").val());
                });
            } else {
                $("#box_error_message").html(json.result);
            }
        });
    }

    /**
     * Logs a user in using the optional username and password (if not set, it
     * will attempt to retrieve the username & password values from the form
     * elements.
     * @param  String username, String password
     * @return JSON
     */
    this.login = function(username, password) {
        username = ((username == false) ? $("#input_username").val() : username);
        password = ((password == false) ? $("#input_password").val() : password);
        var returnedJSON;
        $.ajax({
            type: 'POST',
            url: this._baseUrl + "ajax",
            async: false,
            data: {
                functionCall: 201,
                username: username,
                password: password
            },
            success: function(json) {
                returnedJSON = jQuery.parseJSON(json);
                if(returnedJSON.name != null)
                    $("#container_user_details_user").html("Welcome " + returnedJSON.name + ", click here to log out.");
                window.location.reload();
            }
        });
        return returnedJSON;
    }

    /**
     * Logs out a user
     * @param
     * @return JSON
     */
    this.logout = function() {
        var returnedJSON;
        $.ajax({
            type: 'POST',
            url: this._baseUrl + "ajax",
            async: false,
            data: {
                functionCall: 204
            },
            success: function(json) {
                returnedJSON = jQuery.parseJSON(json);
                if(returnedJSON.name != null)
                    $("#container_user_details_user").html("Welcome guest, click here to log in.");
                window.location.reload();
            }
        });
        return returnedJSON;
    }

    /**
     * Sets the events for opening or closing the login box.
     * @param
     * @return
     */
    this.openLoginBox = function() {
        $(".login_box_container").fadeIn("fast", function() {
            $("#box_login").slideDown("slow");
        });
        $(".login_box_container").click(function() {
            $("#box_login").slideUp("slow");
            $("#box_register").slideUp("slow");
            $(".login_box_container").fadeOut("fast", function() {
                $(".login_box_container").unbind();
            });
        });
    }

    /**
     * Registers a user, by retrieving the form values after the submit button
     * has been pressed.
     * @param
     * @return JSON
     */
    this.register = function() {
        var returnedJSON;
        $.ajax({
            type: 'POST',
            url: this._baseUrl + "ajax",
            async: false,
            data: {
                functionCall: 202,
                username: $("#register_input_username").val(),
                password: $("#register_input_password").val(),
                name: $("#register_input_name").val(),
                address: $("#register_input_address").val()
            },
            success: function(json) {
                returnedJSON = jQuery.parseJSON(json);
                console.log(returnedJSON);
            }
        });
        return returnedJSON;
    }

}