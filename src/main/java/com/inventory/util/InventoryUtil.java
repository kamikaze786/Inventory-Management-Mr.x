package com.inventory.util;

import com.inventory.backend.service.ItemService;
import com.inventory.enums.CommandsEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class InventoryUtil {

    public static void showMenu() {
        int exit = 1;
        System.out.println("Welcome to Mr. X inventory management!");
        System.out.println("Write one of the following commands with the required parameters\n");
        do {

            Scanner sc = new Scanner(System.in);

            System.out.println("1. Create new item -> create itemName costPrice sellingPrice");
            System.out.println("2. Delete existing item -> delete itemName");
            System.out.println("3. Update Buy item quantity -> updateBuy itemName quantity");
            System.out.println("4. Update Sell item quantity -> updateSell itemName quantity");
            System.out.println("5. Report -> report");
            System.out.println("0. Exit -> exit\n");
            System.out.println("Your command: ");

            String command = sc.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                exit = 0;
                System.out.println("Bye!");
            } else if (!command.contains(CommandsEnum.CREATE.getCommandName())
                    && !command.contains(CommandsEnum.DELETE.getCommandName())
                    && !command.contains(CommandsEnum.UPDATE_BUY.getCommandName())
                    && !command.contains(CommandsEnum.UPDATE_SELL.getCommandName())
                    && !command.contains(CommandsEnum.REPORT.getCommandName())) {
                logMessage(InventoryUtil.class.getSimpleName(),"error", "Invalid Command. Try again!");
            } else {
                if (command.contains(CommandsEnum.CREATE.getCommandName())) {
                    ItemService.create(command);
                } else if (command.contains(CommandsEnum.DELETE.getCommandName())) {
                    ItemService.delete(command);
                } else if (command.contains(CommandsEnum.UPDATE_BUY.getCommandName())) {
                    ItemService.updateBuy(command);
                } else if (command.contains(CommandsEnum.UPDATE_SELL.getCommandName())) {
                    ItemService.updateSell(command);
                } else if (command.contains(CommandsEnum.REPORT.getCommandName())) {
                    ItemService.report();
                }
            }
        } while (exit != 0);
    }

    public static void logMessage(String className, String type, String msg) {
        Logger logger = LoggerFactory.getLogger(className);
        if (type.equalsIgnoreCase("info" )) {
            logger.info(msg);
        } else
        if (type.equalsIgnoreCase("error" )) {
            logger.error(msg);
        }
    }

}
