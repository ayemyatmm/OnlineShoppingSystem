package com.example.onlineshoppingsystem.controller;

import java.security.Principal;
import java.util.List;

import com.example.onlineshoppingsystem.models.AppUser;
import com.example.onlineshoppingsystem.models.AppUserRepository;
import com.example.onlineshoppingsystem.models.Cart;
import com.example.onlineshoppingsystem.models.Product;
import com.example.onlineshoppingsystem.models.Receipt;
import com.example.onlineshoppingsystem.repository.CartRepository;
import com.example.onlineshoppingsystem.repository.ProductRepository;
import com.example.onlineshoppingsystem.repository.ProductSizeRepository;
import com.example.onlineshoppingsystem.repository.ReceiptRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    private final AppUserRepository userRep;
    private final CartRepository cartRep;
    private final ReceiptRepository recRep;
        
    // 全部のProduct表示する
    @GetMapping("/index")
    public String index(@ModelAttribute Receipt receipt,Model model,
    Principal principal) {
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //Productデーブルからデータを取得する
        model.addAttribute("product", pRep.findAll());
        return "product/index";
    }

    //女性Productだけ表示する
    @GetMapping("/findby/women")
    public String findbywomen(Model model,Principal principal) {
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //ProductデーブルからWomenデータを取得する
        model.addAttribute("product", pRep.findBycustomertype("women"));
        return "product/index";
    }

    //男性Productだけ表示する
    @GetMapping("/findby/men")
    public String findbymen(Model model,Principal principal) {
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //ProductデーブルからMenデータを取得する
        model.addAttribute("product", pRep.findBycustomertype("men"));
        return "product/index";
    }

    //子供Productだけ表示する
    @GetMapping("/findby/baby")
    public String findbybaby(Model model,Principal principal) {
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //ProductデーブルからKidsBabyデータを取得する
        model.addAttribute("product", pRep.findBycustomertype("Kids/Baby"));
        return "product/index";
    }

    //選択したProduct詳細を表示する
    @GetMapping("/{id}")
    public String detail(@Validated @ModelAttribute Cart cart, BindingResult result,
    @PathVariable Integer id,RedirectAttributes attrs, Model model,Principal principal){
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //Productデーブルからデータを取得する
        model.addAttribute("product", pRep.findById(id).get());
        return "product/detail";
    }

    //選択したのをCartに入れて保存する
    @PostMapping("/{id}/cart")
    public String addtoCart(@ModelAttribute Cart cart,@PathVariable Integer id, Principal principal,
    @RequestParam("quantity") Integer quantity,@RequestParam("productsize") String productsize,
    RedirectAttributes attrs,Model model){
        //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
         //Productデーブルからデータを取得する
        Product product = pRep.findById(id).get();
        //ProductにもともあるのAmountより買いたいのAmountが大きくなら、このエラーを表示する
        if (product.getHaving_amount() < quantity ) {
            attrs.addFlashAttribute("error", "Sorry!!!Stock is not enough in my shop..");
            return "redirect:/product/"+ id;
        }
        int prices = product.getPrice();
        int total_price = prices * quantity;
        String sizeValue = productsize;

        //買いたい物をCartに保存する
        Cart c=new Cart();
        c.setAppUser(user);
        c.setQuantity(quantity);
        c.setSize_value(sizeValue);
        c.setTotal_price(total_price);
        c.setProduct(product);
        cartRep.save(c);
        return "redirect:/product/index";
        
    } 

    //Cartに入れたProductを表示する
    @GetMapping("/cart")
    public String cartList(Model model,Principal principal){
        //ログインしたユーザー名を習得する
        AppUser user1 = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user1); 
        //ログインしたユーザーIDを習得する
        int user = userRep.findByUsername(principal.getName()).getId();
        //Cartデーブルからデータを取得する
        model.addAttribute("carts", cartRep.findByAppUserId(user));
        return "product/cart";
    }
    
    //Cartに入れたProductを変更する
    @GetMapping("/cart/{id}")
    public String edit(@PathVariable Integer id, Model model,Principal principal) {
         //ログインしたユーザー名を習得する
        AppUser user = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user); 
        //Cartデーブルからデータを取得する
        Cart c = cartRep.findById(id).get();
        model.addAttribute("cart", c);    
        return "product/edit";
    }
  
    //Cartに入れたProductを変更する
    @PostMapping("/cart/{id}/update")
    public String update( @PathVariable Integer id,@ModelAttribute Cart cart,
    @RequestParam("productsize") String productsize,
    Principal principal,Model model) {
         //ログインしたユーザー名を習得する
        AppUser user1 = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user1); 
         //Cartデーブルからデータを取得する
        Cart c = cartRep.findById(id).get();
        int prices =c.getProduct().getPrice();
        int total_prices = prices * cart.getQuantity(); 
        int quantity = cart.getQuantity();
        //変更したのをCartに保存する
        Cart ct=new Cart();
        ct.setId(id);
        ct.setQuantity(quantity);
        ct.setTotal_price(total_prices);
        ct.setSize_value(productsize);
        ct.setProduct(c.getProduct());
        ct.setAppUser(user1);
        cartRep.save(ct);
        return "redirect:/product/cart";
    }

    //Cartに入れたProductを削除する
    @DeleteMapping("/cart/{id}/remove")
    public String deleteCartlist(@PathVariable Integer id,@ModelAttribute Cart cart,Model model) {
        Cart c = cartRep.findById(id).get();
        System.out.println("mainfdfd"+  c.getId());

        cartRep.deleteById(id);
        System.out.println("idddd"+ id);
        return "redirect:/product/cart";
    }

    //最高に買ったのProductをReceiptで表示する
    @GetMapping("/receipt")
    public String receipt(Model model,Principal principal){
        //ログインしたユーザーIDを習得する
        int user = userRep.findByUsername(principal.getName()).getId();
        //ログインしたユーザー名を習得する
        AppUser user1 = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user1);
        Integer final_total = recRep.selectSumPrice(user);
        //Productを買ったのユーザー名と価格をReceiptデーブルに保存する
        Receipt r=new Receipt();
        r.setTotal_price(final_total);
        r.setAppUser(user1);
        r.setStatus(0);
        recRep.save(r); 
        
        model.addAttribute("carts", cartRep.findByAppUserId(user));
        //同じお客様がも一回買うならも、出来るようにStatusでチェックする
        Receipt status_result = recRep.selectStatus(user);
        model.addAttribute("receipts", status_result);

        //Productを買ったらもともあるのAmountにStockは減るのチェック
        List<Cart> carts = cartRep.findByAppUser(user1);
        for (int i = 0; i < carts.size(); i++) {
            pRep.updateAmount( 
            carts.get(i).getQuantity(), 
            carts.get(i).getProduct().getId()
            );
        }
        return "product/receipt";
    }

    //Shoppingするのは終わったらCartにProductを削除する
    @DeleteMapping("/finishshopping")
    public String buypage(Model model,Principal principal) {
        //ログインしたユーザーIDを習得する
        int user = userRep.findByUsername(principal.getName()).getId();
        //ログインしたユーザー名を習得する
        AppUser user1 = userRep.findByUsername(principal.getName());
        model.addAttribute("appuser", user1); 
        //Shoppingするのは終わったらCartにProductを削除する
        cartRep.deleteCartUser(user);
        //同じお客様がも一回買うならも、出来るようにStatusを変更する
        Integer orderid = recRep.selectStatus(user).getId();
        Integer final_total = recRep.selectPrice(user);
        Receipt rec = new Receipt();
        rec.setId(orderid);
        rec.setStatus(1);
        rec.setTotal_price(final_total);
        rec.setAppUser(user1);
        recRep.save(rec);
        return "redirect:/product/index";
    }
}
