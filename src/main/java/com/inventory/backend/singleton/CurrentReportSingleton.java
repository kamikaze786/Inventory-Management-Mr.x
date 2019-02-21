package com.inventory.backend.singleton;

import com.inventory.backend.model.Item;

import java.util.HashMap;
import java.util.Map;

/** A singleton class may act as a persistent data object for the purposes of this challenge **/
public class CurrentReportSingleton {

    private static CurrentReportSingleton instance = null;
    public Map<String, Item> currentItemMap = new HashMap<>(); // HashMap used for saving current data report
    public Double currentTotal = 0.0;
    public Double currentProfit = 0.0;

    private CurrentReportSingleton() { }

    /** Ensuring to have a thread-safe singleton instance **/
    public static synchronized CurrentReportSingleton getInstance() {

        if (instance == null) {
            instance = new CurrentReportSingleton();
        }
        return instance;
    }

}
