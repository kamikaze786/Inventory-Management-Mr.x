package com.inventory.test.unit;

import com.inventory.backend.repositories.ItemSingleton;
import com.inventory.backend.service.ItemService;
import com.inventory.exceptions.ItemException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RequireBeforeItemTest {

    private ItemSingleton itemSingleton;

    @Before
    public void init() {
        itemSingleton = ItemSingleton.getInstance();
        createMockData();
    }

    @After
    public void finish() {
        deleteMockData();
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenCreateNewItemHasExistingItem() {
        ItemService.create("create Test01 20.0 22.5");
    }

    @Test
    public void shouldUpdateBuySuccessfully() {
        ItemService.updateBuy("updateBuy Test01 10");
        Assert.assertEquals(10, itemSingleton.currentItemMap.get("Test01").getQuantity());
    }

    @Test
    public void shouldUpdateSellSuccessfully() {
        ItemService.updateBuy("updateBuy Test01 10");
        Assert.assertEquals(10, itemSingleton.currentItemMap.get("Test01").getQuantity());
        ItemService.updateSell("updateSell Test01 10");
        Assert.assertEquals(0, itemSingleton.currentItemMap.get("Test01").getQuantity());
    }

    @Test(expected = ItemException.class)
    public void shouldThrownItemExceptionWhenUpdateSellQuantityIsGreaterThanExistingStock() {
        ItemService.updateBuy("updateBuy Test01 10");
        Assert.assertEquals(10, itemSingleton.currentItemMap.get("Test01").getQuantity());
        ItemService.updateSell("updateSell Test01 11");
    }

    @Test
    public void shouldShowReportSuccessfully() {
        ItemService.updateBuy("updateBuy Test01 10");
        ItemService.updateBuy("updateBuy Test02 50");
        ItemService.updateBuy("updateBuy Test03 80");
        ItemService.report();
        ItemService.updateSell("updateSell Test01 5");
        ItemService.updateSell("updateSell Test02 25");
        ItemService.updateSell("updateSell Test03 40");
        ItemService.report();
    }

    private void createMockData() {
        ItemService.create("create Test01 10.0 15.0");
        ItemService.create("create Test02 8.0 13.0");
        ItemService.create("create Test03 4.0 7.0");
    }

    private void deleteMockData() {
        ItemService.delete("delete Test01");
        ItemService.delete("delete Test02");
        ItemService.delete("delete Test03");
    }
}
