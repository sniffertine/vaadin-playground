package com.vaadin.example;

import com.helger.commons.concurrent.ExecutorServiceHelper;
import com.vaadin.flow.server.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationServiceListener implements VaadinServiceInitListener, ServiceDestroyListener, SessionInitListener, SessionDestroyListener {

    public static final List<VaadinSession> sessions = new CopyOnWriteArrayList<>();

    private ExecutorService threadPool;
    private ExternalApp app;

    public ApplicationServiceListener() {
        threadPool = Executors.newSingleThreadExecutor();
    }

    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addServiceDestroyListener(this);
        serviceInitEvent.getSource().addSessionInitListener(this);
        serviceInitEvent.getSource().addSessionDestroyListener(this);

        app = new ExternalApp();
        threadPool.submit(app);
    }

    @Override
    public void serviceDestroy(ServiceDestroyEvent serviceDestroyEvent) {
        app.stop();
        ExecutorServiceHelper.shutdownAndWaitUntilAllTasksAreFinished(threadPool);
    }

    @Override
    public void sessionInit(SessionInitEvent sessionInitEvent) throws ServiceException {
        sessions.add(sessionInitEvent.getSession());
    }

    @Override
    public void sessionDestroy(SessionDestroyEvent sessionDestroyEvent) {
        sessions.remove(sessionDestroyEvent.getSession());
    }
}
