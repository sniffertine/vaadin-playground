package com.vaadin.example;

import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.LinkedHashMap;
import java.util.Map;

@Route("")
public class MainView extends VerticalLayout {

    private final Map<ObservableValue, ChangeListener> listeners;

    public MainView() {
        listeners = new LinkedHashMap<>();

        Button button = new Button("Click me",
                event -> Notification.show("Clicked!"));
        add(button);

        TextField text = new TextField();
        text.getStyle().set("width", "100%");
        add(text);

        addChangeListener(ExternalAppState.INSTANCE.numberOfPeopleProperty(), new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                text.setValue(String.format("Currently there are %s people in the building.", newValue));
            }
        });
    }

    private <T> void addChangeListener(ObservableValue<T> observedValue, ChangeListener<? super T> listener) {
        observedValue.addListener(listener);
        synchronized (listeners) {
            listeners.put(observedValue, listener);
        }
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        synchronized (listeners) {
            listeners.forEach(ObservableValue::removeListener);
        }
    }

}

