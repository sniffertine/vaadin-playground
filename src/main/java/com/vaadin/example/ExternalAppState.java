package com.vaadin.example;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ExternalAppState {

    public static final ExternalAppState INSTANCE = new ExternalAppState();

    private IntegerProperty numberOfPeople = new SimpleIntegerProperty(0);

    public int getNumberOfPeople() {
        return numberOfPeople.get();
    }

    public IntegerProperty numberOfPeopleProperty() {
        return numberOfPeople;
    }

}
