package com.kaushik.simplestore.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.kaushik.simplestore.domain.Customer;
import com.kaushik.simplestore.domain.CustomerOrder;
import com.kaushik.simplestore.domain.Product;
import com.kaushik.simplestore.service.Cart;
import com.kaushik.simplestore.service.CategoryService;
import com.kaushik.simplestore.service.CustomerService;
import com.kaushik.simplestore.service.OrderService;
import com.kaushik.simplestore.service.OrderedProductService;
import com.kaushik.simplestore.service.ProductService;
import com.kaushik.simplestore.util.RegionHashMap;

@Controller 
@SessionAttributes("customerSession")
public class FrontStoreController {

    @Autowired
    private Cart cart;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderedProductService orderedProductService;

    @Autowired
    private OrderService orderService;


    /**
     * Category
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(ModelMap mm) {
        mm.put("categoryList", categoryService.getAll());
        return "front_store/home";
    }

    @RequestMapping(value = "/category", method = RequestMethod.GET)
    public String category(@RequestParam("id") Byte id, ModelMap mm) {
        mm.put("productList", productService.getByCategoryId(id));
        mm.put("categoryList", categoryService.getAll());
        mm.put("id", id);
        return "front_store/category";
    }

    /**
     * CRUD on shopping cart
     */
    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String cart(ModelMap mm) {
        Map<Product, Integer> itemMap = cart.getItems();
        double subTotal = cart.calculateSubTotal();
        Integer numOfItems = cart.sumQuantity();
        mm.put("itemMap", itemMap);
        mm.put("numOfItems", numOfItems);
        mm.put("subTotal", subTotal);
        return "front_store/cart";
    }

    @RequestMapping(value = "/addToCart", method = RequestMethod.GET)
    public
    @ResponseBody
    String addToCart(@RequestParam("id") Integer id) {
        Product product = productService.getById(id);
        cart.addItem(product);
        return cart.sumQuantity().toString();
    } // Response for AJAX request

    @RequestMapping(value = "/clearCart", method = RequestMethod.GET)
    public String clearCart() {
        cart.clear();
        return "redirect:/cart";
    }

    @RequestMapping(value = "/updateCart", method = RequestMethod.POST)
    public String updateCart(@RequestParam("id") Integer id,
                             @RequestParam("quantity") Integer quantity) {
        cart.updateItem(id, quantity);
        return "redirect:/cart";
    }

    /**
     * Checkout
     */
    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public String checkout(Customer customer, ModelMap mm) {
        double subTotal = cart.calculateSubTotal();
        if (subTotal == 0) {
            mm.put("subTotal", subTotal);
        } else {
            double total = cart.calculateTotal(subTotal);
            RegionHashMap regionHashMap = new RegionHashMap();
            Map<String, String> regions = regionHashMap.getCityRegion();
            mm.put("subTotal", subTotal);
            mm.put("total", total);
            mm.put("regions", regions);
        }
        return "front_store/checkout";
    }

    @RequestMapping(value = "/purchaseGuest", method = RequestMethod.POST)
    public String purchaseGuest(@ModelAttribute("customer") Customer customer, ModelMap mm) {
        // 1. Save customer (customer)
		Integer customerId = customerService.save(customer);

        // 2. Save order (customer_order)
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        CustomerOrder order = orderService.save(customerId, total);

        // 3. Save order details (ordered_product)
        Map<Product, Integer> itemMap = new HashMap<Product, Integer>(cart.getItems());
        orderedProductService.save(order, itemMap);

        mm.put("subTotal", subTotal);
        mm.put("total", total);
        mm.put("order", order);
        mm.put("customer", customer);
        mm.put("itemMap", itemMap);

        cart.clear();
        return "front_store/confirmation";
    }

    @RequestMapping(value ="/login", method = RequestMethod.GET)
    public String login () {
    		return "front_store/login";
   
    }
    
    @RequestMapping(value ="/register", method = RequestMethod.GET)
    public String register (ModelMap mm) {
    		RegionHashMap regionHashMap = new RegionHashMap();
	        Map<String, String> regions = regionHashMap.getCityRegion();
	        mm.put("regions", regions);
    		return "front_store/register";
   
    }
    
    @RequestMapping(value = "/registerMember", method = RequestMethod.POST)
    public ModelAndView	registerMember(HttpSession session, @ModelAttribute("customer") Customer customer ) {
    	System.out.println(customer.getAddress() + customer.getCcNumber() + customer.getEmail());
    	customerService.save(customer);
    	session.setAttribute("username",customer.getName());
        return new ModelAndView("redirect:/");
    }
    
    
    @RequestMapping(value = "/loginMember", method = RequestMethod.POST)
    public ModelAndView	 loginMember(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session) {
        // 1. Get id
        Customer customer = customerService.getByEmailAndPassword(email,password);
        if (customer != null) {
        	session.setAttribute("username",customer.getName()); 
        	return new ModelAndView("redirect:/");
        }
        return new ModelAndView("redirect:/login");
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {
        // 1. Get id
        session.invalidate();
        
        return new ModelAndView("redirect:/");
    }
    
    @RequestMapping(value = "/purchaseMember", method = RequestMethod.POST)
    public String purchaseMember(@RequestParam("email") String email, @RequestParam("password") String password, ModelMap mm) {
        // 1. Get id
        Customer customer = customerService.getByEmailAndPassword(email,password);
        Integer customerId = customer.getId();

        // 2. Save order (customer_order)
        double subTotal = cart.calculateSubTotal();
        double total = cart.calculateTotal(subTotal);
        CustomerOrder order = orderService.save(customerId, total);

        // 3. Save order details (ordered_product)
        Map<Product, Integer> itemMap = new HashMap<Product, Integer>(cart.getItems());
        orderedProductService.save(order, itemMap);

        mm.put("subTotal", subTotal);
        mm.put("total", total);
        mm.put("order", order);
        mm.put("customer", customer);
        mm.put("itemMap", itemMap);

        cart.clear();
        return "front_store/confirmation";
    }

    /**
     * AJAX service
     */
    @RequestMapping(value = "/validateMember", method = RequestMethod.POST)
    @ResponseBody
    public String validateMember(@RequestParam("email") String email, @RequestParam("password") String password) {
        Customer customer = customerService.getByEmailAndPassword(email, password);
        if (customer != null) {
            return "true";
        }
        return "false";
    }

    @RequestMapping(value = "/getCartSize", method = RequestMethod.GET)
    @ResponseBody
    public String getCartSize() {
        return cart.sumQuantity().toString();
    }

}

