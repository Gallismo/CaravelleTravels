package ru.almaz.CaravelleTravels.entities;

public enum BookingState {
    NONE, DATE, FROM, TO, NAME, PHONE, PASSENGERS;

    private static final BookingState[] values = values();

    public BookingState next() {
        return values[(this.ordinal() + 1) % values.length];
    }
}
