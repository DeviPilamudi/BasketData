package com.rbc.shopping.basket;
import com.rbc.shopping.basket.BasketService;
import com.rbc.shopping.basket.BasketServiceImpl;
import com.rbc.shopping.domain.Basket;
import com.rbc.shopping.exception.InvalidBasketException;
import com.rbc.shopping.exception.InvalidFruitException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.valueOf;
import static java.util.Collections.emptyMap;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BasketServiceTest {
	
	    private BasketService BasketService;
	    private Basket basket;
	    @Rule
	    public ExpectedException expectedException = ExpectedException.none();

	    @Before
	    public void setUp() {
	        BasketService = new BasketServiceImpl();
	    }

	    @Test
	    public void shouldReturnZero_whenCheckout_givenEmptyBasket() {
	        basket = new Basket(emptyMap());
	        BigDecimal actualAmount = BasketService.generateBill(basket);
	        assertThat(actualAmount, is(BigDecimal.ZERO));
	    }

	    @Test
	    public void shouldThrowException_whenCheckout_givenInvalidBasket() {
	        basket = new Basket(null);
	        expectedException.expect(InvalidBasketException.class);
	        expectedException.expectMessage("Invalid Basket");
	        BasketService.generateBill(basket);
	    }

	    @Test
	    public void shouldReturnAmount_whenCheckout_givenABasketWithInvalidItem() {
	        Map<String, Integer> items = new HashMap<>();
	        items.put("Kiwi", 1);
	        basket = new Basket(items);
	        expectedException.expect(InvalidFruitException.class);
	        expectedException.expectMessage("Invalid Fruit Kiwi");
	        BasketService.generateBill(basket);
	    }

	    @Test
	    public void shouldReturnAmount_whenCheckout_givenABasketWithOneItem() {
	        Map<String, Integer> items = new HashMap<>();
	        items.put("Apple", 1);
	        basket = new Basket(items);
	        BigDecimal actualAmount = BasketService.generateBill(basket);
	        assertThat(actualAmount, is(getFormattedAmount(0.60)));
	    }

	    @Test
	    public void shouldReturnAmount_whenCheckout_givenABasketWithOneItemAndMultipleQuantity() {
	        Map<String, Integer> items = new HashMap<>();
	        items.put("Orange", 5);
	        basket = new Basket(items);
	        BigDecimal actualAmount = BasketService.generateBill(basket);
	        assertThat(actualAmount, is(getFormattedAmount(2.50)));
	    }

	    @Test
	    public void shouldReturnAmount_whenCheckout_givenABasketWithMultipleItemsAndOneQuantity() {
	        Map<String, Integer> items = new HashMap<>();
	        items.put("Apple", 1);
	        items.put("Orange", 1);
	        items.put("Peach", 1);
	        items.put("Lemon", 1);
	        items.put("Banana", 1);
	        basket = new Basket(items);
	        BigDecimal actualAmount =BasketService.generateBill(basket);
	        assertThat(actualAmount, is(getFormattedAmount(2.00)));
	    }

	    @Test
	    public void shouldReturnAmount_whenCheckout_givenABasketWithMultipleItemsAndMultipleQuantity() {
	        Map<String, Integer> items = new HashMap<>();
	        items.put("Apple", 2);
	        items.put("Banana", 6);
	        items.put("Orange", 3);
	        items.put("Peach", 5);
	        items.put("Lemon", 2);
	        basket = new Basket(items);
	        BigDecimal actualAmount = BasketService.generateBill(basket);
	        assertThat(actualAmount, is(getFormattedAmount(7.00)));
	    }

	    private BigDecimal getFormattedAmount(double amount) {
	        return valueOf(amount).setScale(2, RoundingMode.HALF_UP);
	    }
	}

