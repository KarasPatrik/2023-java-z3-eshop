package sk.stuba.fei.uim.oop.assignment3.cart.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemStack {

    private Long productId;

    private int amount;

    public ItemStack(Long productId, int amount){
        this.productId = productId;
        this.amount = amount;
    }

}
