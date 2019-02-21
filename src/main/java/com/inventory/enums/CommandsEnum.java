package com.inventory.enums;

public enum CommandsEnum {

    CREATE(1, "create"),
    DELETE(2, "delete"),
    UPDATE_BUY(3, "updateBuy"),
    UPDATE_SELL(4, "updateSell"),
    REPORT(5, "report");

    private final int id;
    private final String commandName;

    CommandsEnum(int id, String commandName) {
        this.id = id;
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
