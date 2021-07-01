package com.example.onlineshoppingsystem.controller;

import java.security.Principal;
import com.example.onlineshoppingsystem.models.AppUser;
import com.example.onlineshoppingsystem.models.AppUserRepository;
import com.example.onlineshoppingsystem.models.Cart;
import com.example.onlineshoppingsystem.models.Product;
import com.example.onlineshoppingsystem.models.ProductSize;
import com.example.onlineshoppingsystem.models.Receipt;
import com.example.onlineshoppingsystem.repository.CartRepository;
import com.example.onlineshoppingsystem.repository.ProductRepository;
import com.example.onlineshoppingsystem.repository.ProductSizeRepository;
// import com.example.onlineshoppingsystem.repository.ProductSizeRepository;
import com.example.onlineshoppingsystem.repository.ReceiptRepository;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelExtensionsKt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;

@RequestMapping("/product")
@Controller
@RequiredArgsConstructor
public class ProductController {
 
    private final ProductRepository pRep;
    private final ProductSizeRepository psRep;
    private final AppUserRepository userRep;
    private final CartRepository cartRep;
    private final ReceiptRepository recRep;
        
    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("product", pRep.findAll());
        return "product/index";
    }

    @GetMapping("/findby/women")
    public String findbywomen(Model model) {
        model.addAttribute("product", pRep.findBycustomertype("women"));
        return "product/index";
    }

    @GetMapping("/findby/men")
    public String findbymen(Model model) {
        model.addAttribute("product", pRep.findBycustomertype("men"));
        return "product/index";
    }

    @GetMapping("/findby/baby")
    public String findbybaby(Model model) {
        model.addAttribute("product", pRep.findBycustomertype("Kids/Baby"));
        return "product/index";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model){
       model.addAttribute("product", pRep.findById(id).get());
        return "product/detail";
    }

    @PostMapping("/{id}/cart")
    public String detail(@ModelAttribute Cart cart,@PathVariable Long id, Principal principal,
    @RequestParam("quantity") Integer quantity,Model model){
        AppUser user = userRep.findByUsername(principal.getName());
        //model.addAttribute("product", pRep.findById(id).get());
        Product product = pRep.findById(id).get();
        System.out.println(product);

        int prices = product.getPrice();
        int total_price = prices * quantity;
        System.out.println(cart.getQuantity());
        System.out.println(total_price);

        ProductSize productSize = psRep.findById(id).get();
        String sizeValue = productSize.getSize_text();
        System.out.println(productSize.getSize_text());
        
        Cart c=new Cart();
        c.setAppUser(user);
        c.setQuantity(quantity);
        c.setSize_value(sizeValue);
        c.setTotal_price(total_price);
        c.setProduct(product);
        cartRep.save(c);
        return "redirect:/product/index";
        
    } 

    @GetMapping("/cart")
    public String cartList(Model model,Principal principal){
        AppUser user1 = userRep.findByUsername(principal.getName());
        int user = userRep.findByUsername(principal.getName()).getId();
        model.addAttribute("carts", cartRep.findByAppUserId(user));

        Integer final_total = recRep.selectSumPrice(userRep.findByUsername(principal.getName()).getId());
        System.out.println(final_total);
        Receipt r=new Receipt();
        r.setTotal_price(final_total);
        r.setAppUser(user1);
        recRep.save(r);

        return "product/cart";
    }
    
    @GetMapping("/cart/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Cart c = cartRep.findById(id).get();
        model.addAttribute("cart", c);    
        return "product/edit";
    }
  
    @PostMapping("/cart/{id}/update")
    public String update( @PathVariable Long id,@ModelAttribute Product product,
    @RequestParam("quantity") Integer quantity,BindingResult result,Model model) {
        // model.addAttribute("product", pRep.getById(id).getPrice());
        // System.out.println(product);
        Product p = pRep.findById(id).get();
        int prices = p.getPrice();

        ProductSize ps = psRep.findById(id).get();
        String sizes = ps.getSize_text();

        int total_prices = prices * quantity;       
        if (result.hasErrors()) {
            return "product/edit";
        }
        Cart c=new Cart();
        c.setQuantity(quantity);
        c.setSize_value(sizes);
        c.setTotal_price(total_prices);
        cartRep.save(c);
        return "/product/cart";
    }

    @GetMapping("/receipt")
    public String receipt( Model model,Principal principal){
        //AppUser user1 = userRep.findByUsername(principal.getName());
        int user = userRep.findByUsername(principal.getName()).getId();
        Receipt receipt = recRep.findByAppUserId(user);
        model.addAttribute("receipts", receipt);
        model.addAttribute("carts", cartRep.findByAppUserId(user));

       
        System.out.println("LastTesttttttt");
        // System.out.println(recRep.findByAppUserId(user).size());
        System.out.println(user);
        return "product/receipt";
    }

    @DeleteMapping("/done")
    public String buypage(Model model,Principal principal) {
        int user = userRep.findByUsername(principal.getName()).getId();
        System.out.println("dfdjkfdjfkdlkf");
        System.out.println(user);
        recRep.deleteContributeur(user);
        cartRep.deleteCartUser(user);
        return "redirect:/product/index";
    }
}
