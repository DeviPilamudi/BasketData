package com.rbc.shopping.basket;

import com.rbc.shopping.domain.Basket;

import java.math.BigDecimal;

public interface BasketService {
   public BigDecimal generateBill(Basket basket);
}