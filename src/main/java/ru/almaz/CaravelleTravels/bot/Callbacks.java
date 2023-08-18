package ru.almaz.CaravelleTravels.bot;

public enum Callbacks {
    CANCEL_BOOKING("/cancel"), BACK("/back");

    private final String command;

    Callbacks(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
