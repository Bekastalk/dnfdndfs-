package kg.kstu.production.service;

import kg.kstu.production.entity.MaterialPurchase;

import java.util.List;

public interface MaterialPurchaseService {
    List<MaterialPurchase> getAll();

    String create(MaterialPurchase materialPurchase);
}
