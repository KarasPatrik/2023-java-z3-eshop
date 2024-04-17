package sk.stuba.fei.uim.oop.assignment3.product.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.IProductRepository;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductUpdateRequest;

import java.util.List;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository repository;

    @Override
    public List<Product> getAll(){return this.repository.findAll();}

    @Override
    public Product create(ProductRequest request) {
        return this.repository.save(new Product(request));
    }

    @Override
    public Product getById(long id) throws NotFoundException {
        Product product = this.repository.findProductById(id);
        if (product == null) {
            throw new NotFoundException();
        }
        return product;
    }

    @Override
    public Product update(long id, ProductUpdateRequest request) throws NotFoundException {
        Product product = this.getById(id);
        if (request.getName() != null) {
            product.setName(request.getName());
        }
        if (request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        return this.repository.save(product);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public int getProductAmount(long id) throws NotFoundException{
        Product product = this.getById(id);
        return product.getAmount();
    }

    @Override
    public int addProductAmount(long id, int amount) throws NotFoundException {
        Product product = this.getById(id);
        int newamount = product.getAmount() + amount;
        product.setAmount(newamount);
        this.repository.save(product);
        return product.getAmount();

    }
    @Override
    public void removeProductAmount(long id, int amount) throws NotFoundException, IllegalOperationException {
        Product product = this.getById(id);
        if (product.getAmount() < amount) {
            throw new IllegalOperationException();
        }
        int newamount = product.getAmount() - amount;
        product.setAmount(newamount);
        this.repository.save(product);
    }

}
