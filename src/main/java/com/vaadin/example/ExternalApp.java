package com.vaadin.example;

import com.vaadin.flow.server.Command;
import com.vaadin.flow.server.VaadinSession;

import java.util.Random;
import java.util.concurrent.Callable;

public class ExternalApp implements Callable<Void> {

    private final Random random = new Random();
    private boolean alive = true;

    @Override
    public Void call() throws Exception {
        while (alive) {
            for (VaadinSession session : ApplicationServiceListener.sessions) {
                session.access(new Command() {
                    @Override
                    public void execute() {
                        ExternalAppState.INSTANCE.numberOfPeopleProperty().setValue(random.nextInt(1000));
                    }
                });
            }

            Thread.sleep(3000);
        }

        return null;
    }

    public void stop() {
        alive = false;
    }
}
