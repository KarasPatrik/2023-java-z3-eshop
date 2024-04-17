package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ItemStack;

@Getter
@Setter
@NoArgsConstructor
public class CartAddRequest {
    private Long productId;

    private int amount;

    public CartAddRequest(ItemStack product) {
        this.productId = product.getProductId();
        this.amount = product.getAmount();
    }
}

