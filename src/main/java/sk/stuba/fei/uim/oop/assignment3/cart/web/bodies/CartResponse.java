package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;


import lombok.Getter;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ItemStack;

import java.util.List;

@Getter
@Setter
public class CartResponse {
    private long id;
    private List<ItemStack> shoppingList;
    private boolean payed;

    public CartResponse(Cart Cart) {
        this.id = Cart.getId();
        this.payed = Cart.isPayed();
        this.shoppingList = Cart.getShoppingList();
    }
}
