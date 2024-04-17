package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.Cart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ICartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ItemStack;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartAddRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository repository;

    @Autowired
    private IProductService productService;

    @Override
    public Cart create() {
        return this.repository.save(new Cart());
    }

    @Override
    public Cart getById(long id) throws NotFoundException {
        Cart cart = this.repository.findCartById(id);
        if (cart == null) {
            throw new NotFoundException();
        }
        return cart;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public Cart addToCart(long id, CartAddRequest product) throws NotFoundException, IllegalOperationException {
        Cart cart = this.getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        this.productService.removeProductAmount(product.getProductId() ,product.getAmount());
        for (int i = 0; i < cart.getShoppingList().size(); i++){
            if (cart.getShoppingList().get(i).getProductId() == product.getProductId()){
                int newAmount = cart.getShoppingList().get(i).getAmount() + product.getAmount();
                cart.getShoppingList().get(i).setAmount(newAmount);
                return this.repository.save(cart);
            }
        }
        cart.getShoppingList().add(new ItemStack(product.getProductId(), product.getAmount()));
        return this.repository.save(cart);
    }

    @Override
    public double payForCart(long id) throws NotFoundException, IllegalOperationException {
        Cart cart = this.getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        double price = 0;
        Long actualID;
        int actualAmount;
        double priceOfActualProduct;
        for (int i = 0; i < cart.getShoppingList().size(); i++){
            actualID = cart.getShoppingList().get(i).getProductId();
            actualAmount = cart.getShoppingList().get(i).getAmount();
            priceOfActualProduct = productService.getById(actualID).getPrice();
            price = price + priceOfActualProduct*actualAmount;
        }
        cart.setPayed(true);
        this.repository.save(cart);
        return price;
    }

}
