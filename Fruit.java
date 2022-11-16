package com.rbc.shopping.domain;

import com.rbc.shopping.exception.InvalidFruitException;
import java.math.BigDecimal;
import static java.util.stream.Stream.of;

public enum Fruit {
    APPLE("Apple", BigDecimal.valueOf(0.70)),
    BANANA("Banana", BigDecimal.valueOf(0.60)),
    ORANGE("Orange", BigDecimal.valueOf(0.40)),
    PEACH("Peach", BigDecimal.valueOf(0.20)),
    LEMON("Lemon", BigDecimal.valueOf(0.10)),;

    private String name;
    private BigDecimal price;

    Fruit(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public static Fruit getFruit(String fruitName) {
        return of(Fruit.values())
                .filter(fruit -> fruit.getName().equalsIgnoreCase(fruitName))
                .findFirst()
                .orElseThrow(() -> new InvalidFruitException("Invalid Fruit " + fruitName));
    }
}
