(function ($) {
	
	/*
	 * slider
	 */
	
	

	/* Navbar: shopping cart items */
	$.ajax({
        url: $('#getCartSize').val(),
        success: function(response) {
            $("#nav-cart-size").html(response);
        }
    });
	
	/* Customer checkout tab */
	$('#checkout-tab a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	/* Add to cart AJAX action */
	$("a[title^=addItem]").click(function(e) {
		e.preventDefault();
		$.ajax({
			url: $(this).attr('href'),
			success: function(response) {
	            $("#nav-cart-size").html(response);
	        }
		});
	});
	
	/* Forms validation */
	$("form[id*='purchase-']").each(function() {
		$(this).validate({
			rules: {
	            name: "required",
	            email: {
	                required: true,
	                email: true
	            },
	            phone: "required",
	            address: "required",
	            ccNumber: {
	                required: true,
	                creditcard: true
	            }
	        },
	        submitHandler: function(form) {
	        	$(form).ajaxSubmit({
	        		url: $("#validateUrl").val(),
	        		data: "email=" + $(form).find('#email').val(),
	        		type: "POST",
	        		success: function(resValid) {
	        			var content = '';
	        			if ($(form).attr("id") === 'purchase-guest'){
	        				if (resValid === 'true') {
	        					content += '<div class="alert alert-danger">The provided email was already used. You can purchase as our member.</div>';
	    						$('#valid-guest').html(content);
	        				} else {
	        					form.submit();
	        				}
	        			} else {
	        				if (resValid === 'true') {
	        					form.submit();
	        				} else {
	        					content = '<div class="alert alert-danger">The provided email or password is not valid, If you have registered before check your email and password else register</div>';
	        					$('#valid-member').html(content);
	        				} 
	        			}
	        		}
	        	});
	        }
		});
	});

})(jQuery);