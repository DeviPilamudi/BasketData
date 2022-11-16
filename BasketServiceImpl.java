package com.rbc.shopping.basket;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.BiFunction;

import com.rbc.shopping.domain.Basket;
import com.rbc.shopping.exception.InvalidBasketException;

public class BasketServiceImpl implements BasketService {

    private BiFunction<Map<String, Integer>, String, BigDecimal> calculatePrice =
            (items, fruit) -> getPrice(fruit).multiply(valueOf(items.get(fruit)));

    @Override
    public BigDecimal generateBill(Basket basket) {
        basketValidation(basket);
        return getBasketPrice(basket.getItems());
    }

    private BigDecimal getBasketPrice(Map<String, Integer> items) {
        return items.keySet()
                .stream()
                .map(fruit -> calculatePrice.apply(items, fruit))
                .reduce(ZERO, BigDecimal::add);
    }

    private static void basketValidation(Basket basket) {
        if (isNull(basket.getItems())) {
            throw new InvalidBasketException("Invalid Basket");
        }
    }

	private BigDecimal getPrice(String fruit) {
        return getFruit(fruit).getPrice().setScale(2, ROUND_HALF_UP);
    }
}
