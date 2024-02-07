package kg.kstu.production.service;

import kg.kstu.production.entity.ProductSale;

import java.util.List;

public interface ProductSalesService {
    List<ProductSale> getAll();

    String create(ProductSale sale);
}
