package com.inventory.test.unit;

import com.inventory.backend.service.ItemService;
import com.inventory.exceptions.ItemException;

import org.junit.Test;

public class DoNotRequireBeforeItemTest {


    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenCreateNewItemHasWrongParams() {
        ItemService.create("create WrongParamsPassed");
    }

    @Test(expected = NumberFormatException.class)
    public void shouldThrownNumberFormatExceptionWhenCreateNewItemHasWrongParams() {
        ItemService.create("create Wrong Params Passed");
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenUpdateBuyItemDoesNotExists() {
        ItemService.updateBuy("updateBuy NonExistingItem 10");
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenUpdateBuyPassesNoExistingItem() {
        ItemService.updateBuy("updateBuy WrongParamsPassed");
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenUpdateSellItemDoesNotExists() {
        ItemService.updateSell("updateSell NonExistingItem 2");
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenUpdateSellPassesNoExistingItem() {
        ItemService.updateSell("updateSell WrongParamsPassed");
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenNoDataIsRegisteredOnShowingReport() {
        ItemService.report();
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenDeleteItemIsNotRegistered() {
        ItemService.delete("delete Test01");
    }
}
